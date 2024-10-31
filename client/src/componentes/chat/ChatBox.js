import { useEffect, useState } from 'react';
import styles from './ChatBox.module.css';
import ChatHeader from './componentesChatbox/ChatHeader';
import ChatHistory from './componentesChatbox/ChatHistory';
import MessageSender from './componentesChatbox/MessageSender';
import SockJS from "sockjs-client"
import Stomp from "stompjs"
//import client from 'react-stomp';
//import { connect } from './componentesChatbox/client'



function ChatBox({mensagens, usuario, room, roomsPicture}){
    const [messages, setMessages] = useState(mensagens)
    let qtd_mensagens = mensagens.length
    const [stompClient, setStompClient] = useState(null)

    useEffect(()=>{
        document.getElementById("qtd_mensagens").innerHTML = qtd_mensagens + " mensagens"
        const socket = new SockJS('http://localhost:8080/chat')
        const client = Stomp.over(socket);
        client.connect({}, (frame) => {
            client.subscribe(`/topic/room/${room.id}`, (message) => {
                const receivedMessage = JSON.parse(message.body)
                console.log(receivedMessage)
                setMessages((prevMessage)=>[...prevMessage, receivedMessage])
                qtd_mensagens++
                document.getElementById("qtd_mensagens").innerHTML = qtd_mensagens + " mensagens"

            });
        });
        setStompClient(client)
        // return () => {
        //     client.disconnect()
        // }
    }, [])

    const sendMessage = (message) => {
        if(message.trim()){
            const chatMessage = {
                room_id: room.id,
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
            nome={room.nome === null 
                    ? room.users.filter((user)=>user.id !== usuario.id)[0].nome 
                    : 
                    room.nome} 
            qtd_mensagens={mensagens.length}
            roomPicture={room.nome === null ? 
            roomsPicture[room.users.filter((user)=>user.id !== usuario.id)[0].id] 
            : 
            roomsPicture[room.id]}/>
        <ChatHistory mensagens={messages} usuario={usuario} room={room}/>
        <MessageSender onSendMessage={sendMessage}/>
    </div>
    )
}

export default ChatBox