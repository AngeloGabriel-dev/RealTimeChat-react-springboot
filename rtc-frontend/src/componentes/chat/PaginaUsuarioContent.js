import ChatBox from "./ChatBox";
import ListaAmigos from "./ListaAmigos";
import { useEffect, useState } from "react";
import styles from './PaginaUsuarioContent.module.css';
import Perfil from "../usuario/Perfil";
import CreateRoomMenu from "../room/CreateRoomMenu";
import Modal from 'react-modal';
import { useUserStore } from '../utils/UseUserStore.js'



function PaginaUsuarioContent(){
    const roomSelecionadaId = useUserStore(state => state.roomSelecionadaId);

    const [showMenu, setShowMenu] = useState(false);
    const [showCreateRoom, setShowCreateRoom] = useState(false);

    const toggleMenu = () => {
        setShowMenu(!showMenu);  
    }

    const toggleCreateRoom = (e) => {
        e.preventDefault()
        setIsOpen(true)
    }

    const [isOpen, setIsOpen] = useState(false);
    return(
        <div className={styles.content}>
            <Modal
                isOpen={isOpen}
                onRequestClose={() => setIsOpen(false)}
                contentLabel="Example Modal"
            >
                <button onClick={()=>setIsOpen(false)}>x</button>
                <CreateRoomMenu />
            </Modal>
            {showMenu ? 
                <Perfil 
                    onToggleMenu={toggleMenu} 
                />
                :
                <ListaAmigos
                    handleToggleMenu={toggleMenu} 
                    handleToggleCreateRoomMenu={toggleCreateRoom} 
                />
            }
            {roomSelecionadaId ? 
                <ChatBox />
                :
                null
            }
        </div>
    )
}

export default PaginaUsuarioContent