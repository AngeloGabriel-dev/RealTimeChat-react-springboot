import { useEffect, useState } from 'react';
import styles from './ChatBox.module.css';
import ChatHeader from './componentesChatbox/ChatHeader';
import ChatHistory from './componentesChatbox/ChatHistory';
import MessageSender from './componentesChatbox/MessageSender';
import SockJS from "sockjs-client"
import Stomp from "stompjs"
import { useUserStore } from '../utils/UseUserStore.js'

//import client from 'react-stomp';
//import { connect } from './componentesChatbox/client'

function ChatBox(){
    const usuario = useUserStore(state => state.usuario);
    const messagesByRoom = useUserStore(state => state.messagesByRoom);
    const rooms = useUserStore(state => state.rooms);
    const roomSelecionadaId = useUserStore(state => state.roomSelecionadaId);
    const addMessageToRoom = useUserStore(state => state.addMessageToRoom);

    const API_URL = process.env.REACT_APP_API_URL;


    const room = rooms.find(r => r.id === roomSelecionadaId);

    let qtd_mensagens = messagesByRoom[roomSelecionadaId].length
    const [stompClient, setStompClient] = useState(null)

    useEffect(()=>{
        const socket = new SockJS(`${API_URL}/chat`)
        const client = Stomp.over(socket);
        client.connect({}, (frame) => {
            client.subscribe(`/topic/room/${roomSelecionadaId}`, (message) => {
                const receivedMessage = JSON.parse(message.body)
                console.log(receivedMessage)
                addMessageToRoom(roomSelecionadaId, receivedMessage)

            });
        });
        setStompClient(client)
        // return () => {
        //     client.disconnect()
        // }
    }, [roomSelecionadaId])

    const sendMessage = (message) => {
        if(message.trim()){
            const chatMessage = {
                room_id: roomSelecionadaId,
                sender_id: usuario.id,
                content: message,
            }
            console.log(chatMessage)
            stompClient.send('/app/chat.sendMessage', {}, JSON.stringify(chatMessage))

            sendMessage('')
        }
    }

    return(
    <div className={styles.chat}>
        <ChatHeader 
            nome={room.type === "DIRECT" 
                    ? room.users.find((user)=>user.id !== usuario.id).nome 
                    : 
                    room.nome} 
            qtd_mensagens={qtd_mensagens}/>
        <ChatHistory />
        <MessageSender onSendMessage={sendMessage}/>
    </div>
    )
}

export default ChatBox