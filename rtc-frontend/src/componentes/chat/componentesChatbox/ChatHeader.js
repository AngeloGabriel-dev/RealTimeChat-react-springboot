import img from '../../../imagens/userImage.jpg';
import styles from './ChatHeader.module.css';

function ChatHeader({nome, qtd_mensagens}){
    return (
        <div className={styles.header}>
            <img src={img} alt='slar'/>
            <div className={styles.chat_about}>
                <span>{nome}</span>
                <br/>
                <span className={styles.num_messages}>{qtd_mensagens}</span>
            </div>
            
        </div>
    )
}

export default ChatHeader