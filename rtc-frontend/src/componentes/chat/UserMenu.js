import img from '../../imagens/userImage.jpg'
import styles from "./UserMenu.module.css"
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faUser } from '@fortawesome/free-solid-svg-icons'
import { useUserStore } from '../utils/UseUserStore.js'
import { shallow } from "zustand/shallow";



function UserMenu({onToggleMenu, onToggleCreateRoomMenu}){
    const usuario = useUserStore(state => state.usuario);
    
    const token = localStorage.getItem("token")

    const API_URL = process.env.REACT_APP_API_URL;
    

    const sendFriendRequest = (e) => {
        const username = prompt("Type an username to add a new friend")
        
        fetch(`${API_URL}/api/v1/amizades/${username}`,{
            headers: {
                "Authorization": `Bearer ${token}`
            },
            method: "POST"
        })
        .catch(err => console.log(err))
    }

    return (
        <div className={styles.container}>
            <img 
                //src={localStorage.getItem("img_profile_url"+usuario.id) ? localStorage.getItem("img_profile_url"+usuario.id) : img} 
                src={img}
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