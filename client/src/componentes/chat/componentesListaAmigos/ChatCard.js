import styles from './ChatCard.module.css'
import img from '../../../imagens/userImage.jpg'

function ChatCard({usuario, room, selecionado, roomPicture, handleOnClick}){
    const retornaId = (e) => {
        handleOnClick(room.id)    
    }

    return(
        <div>
            <div 
                className={selecionado? styles.container_selecionado:styles.container} 
                onClick={retornaId}
            > 
                <img src={roomPicture ? roomPicture : img} className={styles.component}/>
                <span>{room.nome === null
                        ? room.users.filter((user)=>user.id !== usuario.id)[0].nome 
                        : 
                        room.nome}
                </span>
                
            </div>
        </div>
    )
}

export default ChatCard