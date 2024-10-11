import { useEffect, useState } from 'react';
import styles from './ChatBox.module.css';
import ChatHeader from './componentesChatbox/ChatHeader';
import ChatHistory from './componentesChatbox/ChatHistory';
import MessageSender from './componentesChatbox/MessageSender';
import SockJS from "sockjs-client"
import Stomp from "stompjs"
//import client from 'react-stomp';
//import { connect } from './componentesChatbox/client'



function ChatBox({amigo, mensagens, usuario, room}){
    const [messages, setMessages] = useState(mensagens)
    const [stompClient, setStompClient] = useState(null)

    useEffect(()=>{
        const socket = new SockJS('http://localhost:8080/chat')
        const client = Stomp.over(socket);
        client.connect({}, (frame) => {
            client.subscribe(`/topic/room/${room.id}`, (message) => {
                const receivedMessage = JSON.parse(message.body)
                console.log(receivedMessage)
                setMessages((prevMessage)=>[...prevMessage, receivedMessage])
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
                receiver_id: amigo.id,
                content: message,
            }
            console.log(chatMessage)
            stompClient.send('/app/chat.sendMessage', {}, JSON.stringify(chatMessage))

            sendMessage('')
        }
    }

    return(
    <div className={styles.chat}>
        <ChatHeader nome={amigo.nome} qtd_mensagens={mensagens.length}/>
        <ChatHistory mensagens={messages} usuario={usuario} amigo={amigo}/>
        <MessageSender onSendMessage={sendMessage}/>
    </div>
    )
}

export default ChatBox