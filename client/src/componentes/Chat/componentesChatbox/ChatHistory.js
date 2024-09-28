import styles from './ChatHistory.module.css';
import ChatMessage from './ChatMessage';
import MessageSender from './MessageSender';


function ChatHistory({mensagens, usuario, amigo}){

    function converteTimestamp(seconds, timestamp){
        if (seconds !== undefined){
            const data_atual = new Date(seconds*1000)
            console.log(data_atual)
            return data_atual.toString()
        }
        const data_atual = new Date(timestamp*1000)
        console.log(data_atual)
        return data_atual.toString()
        
    }

    return(    
        <div className={styles.chat_history}>
            {mensagens.map((mensagem) => (
                
                <ChatMessage conteudo={mensagem.content} data_mensagem={converteTimestamp(mensagem.timestamp.seconds, mensagem.timestamp)} nome={usuario.id === mensagem.sender_id? usuario.nome:amigo.nome} eh_minha={usuario.id === mensagem.sender_id}/>
            ))}
        </div>
    )
}

export default ChatHistory;