import ChatBox from "./ChatBox";
import ListaAmigos from "./ListaAmigos";
import { useEffect, useState } from "react";
import styles from './PaginaUsuarioContent.module.css';
import Perfil from "../usuario/Perfil";
import CreateRoomMenu from "../room/CreateRoomMenu";
import Modal from 'react-modal';

function PaginaUsuarioContent({amigos, usuario, token, roomsPictures, rooms}){
    const [room, setRoom] = useState({})
    const [mensagensChat, setMensagensChat] = useState(null)
    const [roomSelecionadaId, setRoomSelecionadaId] = useState(null)
    const [carregandoMensagens, setCarregandoMensagens] = useState(false)

    const [showMenu, setShowMenu] = useState(false);
    const [showCreateRoom, setShowCreateRoom] = useState(false);

    

    function pegarIdRoom(id){
        console.log(id)
        setMensagensChat(null)
        setCarregandoMensagens(false)
        setRoomSelecionadaId(id)     
    }

    const toggleMenu = () => {
        setShowMenu(!showMenu);  
    }

    const toggleCreateRoom = (e) => {
        e.preventDefault()
        setIsOpen(true)
    }

    useEffect(()=>{
        if(roomSelecionadaId  && mensagensChat === null){  
            setRoom(rooms.filter(room => room.id === roomSelecionadaId)[0])
            console.log(room.users)
            fetch(`http://localhost:8080/api/v1/chatMessages/${roomSelecionadaId}`, {
                headers:{
                    'Authorization': `Bearer ${token}`,
                    'Content-Type':'application/json'
                },
                method:'GET'
            })
            .then(resp => resp.json())
            .then(data => {
                //console.log(data)
                setMensagensChat(data)
            })
            .catch(err => console.log(err))
        }
        
    }, [roomSelecionadaId])

    useEffect(()=>{
        if(mensagensChat !== null && (mensagensChat.length == 0 || mensagensChat[0].room_id == roomSelecionadaId)){
            setCarregandoMensagens(true)
        }
        
    }, [mensagensChat])


    const [isOpen, setIsOpen] = useState(false);
    return(
        <div className={styles.content}>
            <Modal
                isOpen={isOpen}
                onRequestClose={() => setIsOpen(false)}
                contentLabel="Example Modal"
            >
                <button onClick={()=>setIsOpen(false)}>x</button>
                <CreateRoomMenu amigos={amigos} usersPictures={roomsPictures} token={token}/>
            </Modal>
            {showMenu ? 
                <Perfil 
                    usuario={usuario} 
                    onToggleMenu={toggleMenu} 
                    token={token}
                />
                :
                <ListaAmigos
                    handleRoomId={pegarIdRoom}
                    usuario={usuario} 
                    handleToggleMenu={toggleMenu} 
                    handleToggleCreateRoomMenu={toggleCreateRoom} 
                    roomsPictures={roomsPictures}
                    users={room.users}
                    rooms={rooms}
                />
            }
            {carregandoMensagens ? 
                <ChatBox 
                    mensagens={mensagensChat} 
                    usuario={usuario} 
                    room={room} 
                    roomsPicture={roomsPictures}
                />
                :
                null
            }
        </div>
    )
}

export default PaginaUsuarioContent