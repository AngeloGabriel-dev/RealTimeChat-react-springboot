import logo from '../../../imagens/logo192.png';
import styles from './ChatHeader.module.css';

function ChatHeader({img_src, nome}){
    return (
        <div className={styles.header}>
            <img src={logo} alt='slar'/>
            <div className={styles.chat_about}>
                <span>{nome}</span>
                <br/>
                <span id="qtd_mensagens" className={styles.num_messages}></span>
            </div>
            
        </div>
    )
}

export default ChatHeader