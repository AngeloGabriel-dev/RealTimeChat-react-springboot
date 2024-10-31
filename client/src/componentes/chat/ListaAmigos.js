import { useState } from 'react'
import BarraPesquisa from './componentesListaAmigos/BarraPesquisa.js'
import UserMenu from './UserMenu.js'
import styles from './ListaAmigos.module.css'
import Perfil from '../usuario/Perfil.js'
import ChatCard from './componentesListaAmigos/ChatCard.js'

function ListaAmigos({handleRoomId, 
                    usuario, 
                    handleToggleMenu, 
                    handleToggleCreateRoomMenu, 
                    roomsPictures, 
                    rooms
                    }){
    const [roomSelecionado, setRoomSelecionado] = useState(null)

    function roomSelecionada(id){
        setRoomSelecionado(id)
        handleRoomId(id)
    }
    return(
        <div className={styles.lista}>
            <UserMenu 
                usuario={usuario} 
                onToggleMenu={handleToggleMenu} 
                onToggleCreateRoomMenu={handleToggleCreateRoomMenu}
            />
            <BarraPesquisa/>
            {
                rooms.map((room) => 
                <ChatCard
                    usuario={usuario}
                    room={room} 
                    handleOnClick={roomSelecionada}
                    selecionado={roomSelecionado === room.id}
                    roomPicture={room.nome === null ? roomsPictures[room.users.filter((user)=>user.id !== usuario.id)[0].id] : roomsPictures[room.id]} 
                />)
            }
        </div>
    )
}

export default ListaAmigos