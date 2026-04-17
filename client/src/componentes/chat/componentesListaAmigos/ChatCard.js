import styles from './ChatCard.module.css'
import img from '../../../imagens/userImage.jpg'
import { useUserStore } from '../../utils/UseUserStore.js'


function ChatCard({room, selecionado, handleOnClick}){
    const usuario = useUserStore(state => state.usuario);

    
    const retornaId = (e) => {
        handleOnClick(room.id)    
    }

    return(
        <div>
            <div 
                className={selecionado ? styles.container_selecionado : styles.container} 
                onClick={retornaId}
            > 
                <img src={img} className={styles.component} alt={img}/>
                <span>{room.type === "DIRECT"
                        ? room.users.filter((user)=>user.id !== usuario.id)[0].nome 
                        : 
                        room.nome}
                </span>
                
            </div>
        </div>
    )
}

export default ChatCard