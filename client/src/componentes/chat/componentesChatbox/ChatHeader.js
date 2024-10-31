import img from '../../../imagens/userImage.jpg';
import styles from './ChatHeader.module.css';

function ChatHeader({nome, roomPicture}){
    return (
        <div className={styles.header}>
            <img src={roomPicture ? roomPicture:img} alt='slar'/>
            <div className={styles.chat_about}>
                <span>{nome}</span>
                <br/>
                <span id="qtd_mensagens" className={styles.num_messages}></span>
            </div>
            
        </div>
    )
}

export default ChatHeader