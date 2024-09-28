import ChatBox from "./ChatBox";
import ListaAmigos from "./ListaAmigos";
import { useEffect, useState } from "react";
import styles from './PaginaUsuarioContent.module.css';
import { initializeApp } from "firebase/app";
import { getFirestore, collection, getDocs, query, where, getDoc } from "firebase/firestore";



const firebase_config = {
    apiKey: "Removido",
    authDomain: "realtimechat-6bf77.firebaseapp.com",
    projectId: "realtimechat-6bf77",
    storageBucket: "realtimechat-6bf77.appspot.com",
    messagingSenderId: "29576079403",
    appId: "1:29576079403:web:aa33894926c0369754a26e",
    measurementId: "G-2TJS8NERQQ"
}

const app = initializeApp(firebase_config)
const db = getFirestore(app)

async function buscarMensagensEntreAmigos(amigoId1, amigoId2){
    const mensagensRef = collection(db, "mensagens")
    const q1 = query(
        mensagensRef,
        where("sender_id", "==", amigoId1),
        where("receiver_id", "==", amigoId2)
    );
    const q2 = query(
        mensagensRef,
        where("sender_id", "==", amigoId2),
        where("receiver_id", "==", amigoId1)
    );

    const [snapshot1, snapshot2] = await Promise.all([
        getDocs(q1),
        getDocs(q2)
    ])

    const mensagens = [...snapshot1.docs, ...snapshot2.docs].map(doc => doc.data())
    return mensagens
}

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
            buscarMensagensEntreAmigos(amigoSelecionadoId, usuario.id).then(mensagens => {
                setMensagensChat(mensagens)
                setCarregandoMensagens(true)
            });
        }
        
    }, [room, amigos])

    return(
        <div className={styles.content}>
            <ListaAmigos amigos={amigos} handleId={pegarIdAmigo}/>
            {carregandoMensagens ? <ChatBox amigo={amigoChat} mensagens={mensagensChat} usuario={usuario} room={room}/>:null}
        </div>
    )
}

export default PaginaUsuarioContent