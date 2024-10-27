import { useState } from 'react'
import AmigoContainer from './componentesListaAmigos/AmigoContainer'
import BarraPesquisa from './componentesListaAmigos/BarraPesquisa'
import UserMenu from './UserMenu.js'
import styles from './ListaAmigos.module.css'
import Perfil from '../usuario/Perfil.js'

function ListaAmigos({amigos, handleId, usuario, handleToggleMenu, handleToggleCreateRoomMenu, usersPictures, rooms}){
    const [amigoSelecionado, setAmigoSelecionado] = useState()

    function usuarioSelecionado(id){
        setAmigoSelecionado(id)
        handleId(id)
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
                amigos.map((amigo) => 
                <AmigoContainer 
                    amigo={amigo} 
                    handleOnClick={usuarioSelecionado}
                    selecionado={amigoSelecionado === amigo.id}
                    userPicture={usersPictures[amigo.id]}
                />)
            }
            {
                rooms.map((room)=>
                <AmigoContainer
                    amigo={room}
                    handleOnClick={usuarioSelecionado}
                    selecionado={amigoSelecionado === room.id}
                />)
            }
        </div>
    )
}

export default ListaAmigos