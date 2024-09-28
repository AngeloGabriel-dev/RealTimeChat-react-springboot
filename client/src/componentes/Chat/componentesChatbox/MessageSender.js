import { useState } from 'react';
import styles from './MessageSender.module.css';
//import { sendMessage } from './client';

function MessageSender({onSendMessage}){
    const [chatMessage, setChatMessage] = useState('')

    function handleMessage(e){
        e.preventDefault()
        setChatMessage(e.target.value)
        
    }

    function sendMessage(e){
        e.preventDefault()
        document.getElementById('message').value = ''
        onSendMessage(chatMessage)
        
    }

    return(
        <div className={styles.sender}>
            <textarea onChange={handleMessage} id="message" placeholder='Envie uma mensagem' rows={3}/>
            <button onClick={sendMessage}>SEND</button>
        </div>
    )
}

export default MessageSender