import styles from './ChatCard.module.css'
import img from '../../../imagens/userImage.jpg'
import { useUserStore } from '../../utils/UseUserStore.js'

function SolicitationCard({solicitation}){
    const usuario = useUserStore(state => state.usuario);
    const addNewFriend = useUserStore(state => state.addNewFriend);
    const addNewRoom = useUserStore(state => state.addNewRoom);
    const acceptFriendship = useUserStore(state => state.acceptFriendship);

    const API_URL = process.env.REACT_APP_API_URL;
    const token = localStorage.getItem('token')


    const accept_friendship = () => {
        fetch(`${API_URL}/api/v1/amizades/${solicitation.id}`, {
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            method: "PATCH"
        })
        .then(resp => resp.json())
        .then(data => {
            acceptFriendship({
                amigo: data.friend,
                room: data.room,
                solicitationId: solicitation.id
            });
        })
        .catch(err => console.log(err))
    }

    return(
        <div>
            <div 
                className={styles.container} 
            > 
                <img src={img} className={styles.component} alt={img}/>
                <span>
                    {solicitation.usuarioNome}
                </span>
                <button onClick={accept_friendship}>Sim</button>
                <button>Não</button>
                
            </div>
        </div>
    )
}

export default SolicitationCard