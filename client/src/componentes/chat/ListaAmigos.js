import { useState } from 'react'
import AmigoContainer from './componentesListaAmigos/AmigoContainer'
import BarraPesquisa from './componentesListaAmigos/BarraPesquisa'
import UserMenu from './UserMenu.js'
import styles from './ListaAmigos.module.css'
import Perfil from '../usuario/Perfil.js'

function ListaAmigos({amigos, handleId, usuario, handleToggleMenu, handleToggleCreateRoomMenu, usersPictures}){
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
                    selecionado={amigoSelecionado === amigo.id}
                    handleOnClick={usuarioSelecionado}
                    userPicture={usersPictures[amigo.id]}
                />)
            }
        </div>
    )
}

export default ListaAmigos