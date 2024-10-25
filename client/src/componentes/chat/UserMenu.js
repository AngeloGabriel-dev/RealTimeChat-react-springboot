import img from '../../imagens/userImage.jpg'
import styles from "./UserMenu.module.css"
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faUser } from '@fortawesome/free-solid-svg-icons'

function UserMenu({usuario, onToggleMenu, onToggleCreateRoomMenu}){
    
    const token = localStorage.getItem("token")

    const sendFriendRequest = (e) => {
        const username = prompt("Type an username to add a new friend")
        
        fetch(`http://localhost:8080/api/v1/amizades/${username}`,{
            headers: {
                "Authorization": `Bearer ${token}`
            },
            method: "GET"
        })
        .catch(err => console.log(err))
    }

    return (
        <div className={styles.container}>
            <img 
                src={localStorage.getItem("img_profile_url"+usuario.id)} 
                className={styles.component}
                onClick={onToggleMenu}
            />
            <span>{usuario.nome}</span>
            <button onClick={sendFriendRequest} className={styles.add_friend_button}> {<FontAwesomeIcon icon={faUser}/>}</button>
            <button className={styles.create_group_button} onClick={onToggleCreateRoomMenu}>+</button>
            
        </div>
    )
}

export default UserMenu