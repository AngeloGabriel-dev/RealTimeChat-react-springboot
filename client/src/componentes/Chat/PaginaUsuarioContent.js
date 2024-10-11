import ChatBox from "./ChatBox";
import ListaAmigos from "./ListaAmigos";
import { useEffect, useState } from "react";
import styles from './PaginaUsuarioContent.module.css';

function PaginaUsuarioContent({amigos, usuario, token}){
    const [amigoChat, setAmigoChat] = useState(null)
    const [room, setRoom] = useState({})
    const [mensagensChat, setMensagensChat] = useState([])
    const [amigoSelecionadoId, setAmigoSelecionadoId] = useState(null)
    const [carregandoMensagens, setCarregandoMensagens] = useState(false)

    function pegarIdAmigo(id){
        setAmigoSelecionadoId(id)
        setCarregandoMensagens(false)
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
            <ListaAmigos amigos={amigos} handleId={pegarIdAmigo} usuario={usuario}/>
            {carregandoMensagens ? <ChatBox amigo={amigoChat} mensagens={mensagensChat} usuario={usuario} room={room}/>:null}
        </div>
    )
}

export default PaginaUsuarioContent