import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import styles from './ChatMessage.module.css';
import { faCircle } from '@fortawesome/free-solid-svg-icons';

function ChatMessage({conteudo, data_mensagem, nome, eh_minha}){
    return (
        <div>
            {
                eh_minha ? 
                <div>
                    <div className={styles.message_data}>
                        <FontAwesomeIcon icon={faCircle} className={styles.me}/>
                        <span>{nome}</span>
                        <span className={styles.message_data_time}>{data_mensagem}</span>
                    </div>
                    <div className={styles.message}>
                        <p>{conteudo}</p>
                    </div>
                </div>
                :
                <div>
                    <div className={styles.other_message_data}>
                        <FontAwesomeIcon icon={faCircle} className={styles.other_me}/>  
                        <span className={styles.other_message_data_time}>{data_mensagem}</span>
                        <span>{nome}</span>
                         
                    </div>
                    <div className={styles.other_message}>
                        <p>{conteudo}</p>
                    </div>
                </div>
            }
            
            
            
        </div>
    )
}

export default ChatMessage