import ChatBox from "./ChatBox";
import ListaAmigos from "./ListaAmigos";
import { useEffect, useState } from "react";
import styles from './PaginaUsuarioContent.module.css';
import Perfil from "../usuario/Perfil";

function PaginaUsuarioContent({amigos, usuario, token, usersPictures}){
    const [amigoChat, setAmigoChat] = useState(null)
    const [room, setRoom] = useState({})
    const [mensagensChat, setMensagensChat] = useState([])
    const [amigoSelecionadoId, setAmigoSelecionadoId] = useState(null)
    const [carregandoMensagens, setCarregandoMensagens] = useState(false)
    const [showMenu, setShowMenu] = useState(false);

    function pegarIdAmigo(id){
        setAmigoSelecionadoId(id)
        setCarregandoMensagens(false)
    }

    const toggleMenu = () => {
        setShowMenu(!showMenu);
        
    }

    useEffect(()=>{
        fetch(`http://localhost:8080/api/v1/rooms/room_friends/${amigoSelecionadoId}`, {
            headers:{
                'Authorization': `Bearer ${token}`,
                'Content-Type':'application/json'
            },
            method:'GET'
        })
        .then(resp => resp.json())
        .then(data => {
            setRoom(data)
        })
        .catch(err => console.log(err))
    }, [amigoSelecionadoId, amigos])

    useEffect(()=>{
        if (amigoSelecionadoId){
            setAmigoChat(amigos.filter((amigo) => amigo.id === amigoSelecionadoId)[0])
            fetch(`http://localhost:8080/api/v1/chatMessages/${room.id}`, {
                headers:{
                    'Authorization': `Bearer ${token}`,
                    'Content-Type':'application/json'
                },
                method:'GET'
            })
            .then(resp => resp.json())
            .then(data => {
                console.log(data)
                setMensagensChat(data)
                setCarregandoMensagens(true)
            })
            .catch(err => console.log(err))
        }
        
    }, [room, amigos])

    return(
        <div className={styles.content}>
            {showMenu ? 
                <Perfil usuario={usuario} onToggleMenu={toggleMenu} token={token}/>
                :
                <ListaAmigos amigos={amigos} handleId={pegarIdAmigo} usuario={usuario} handleToggleMenu={toggleMenu} usersPictures={usersPictures}/>
            }
            {carregandoMensagens ? <ChatBox amigo={amigoChat} mensagens={mensagensChat} usuario={usuario} room={room}/>:null}
        </div>
    )
}

export default PaginaUsuarioContent