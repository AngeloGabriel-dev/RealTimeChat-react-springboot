import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import img from '../../imagens/userImage.jpg'

import SockJS from "sockjs-client"
import Stomp from "stompjs"

import styles from './PaginaUsuario.module.css'

import ChatBox from './ChatBox.js'
import ListaAmigos from './ListaAmigos.js'
import PaginaUsuarioContent from './PaginaUsuarioContent.js'
import { useUserStore } from '../utils/UseUserStore.js'


function downloadProfilePicture(token, id) {
    const API_URL = process.env.REACT_APP_API_URL;

    fetch(`${API_URL}/api/v1/storage/downloadProfilePicture`, {
    headers: {
        "Authorization": `Bearer ${token}`,
    },
    method: "GET"
    })
    .then(resp => resp.text())
    .then(data => localStorage.setItem("img_profile_url"+id, data)
    )
        
    
    //localStorage.setItem("img_profile_url"+id, response)
    //return url
}

function downloadFriendsProfilePictures(token){
    const API_URL = process.env.REACT_APP_API_URL;

    const pictures = fetch(`${API_URL}/api/v1/storage/downloadFriendsProfilePicture`, {
        headers: {
            "Authorization": `Bearer ${token}`
        },
        method: "GET"
    })
    .then(resp => resp.json())
    
    return pictures
}


function PaginaUsuario(){
    const navigate = useNavigate();
    const setStompClient = useUserStore(state => state.setStompClient)
    const setUsuario = useUserStore(state => state.setUsuario)
    const setAmigos = useUserStore(state => state.setAmigos)
    const setRooms = useUserStore(state => state.setRooms)
    const setReceivedSolicitations = useUserStore(state => state.setReceivedSolicitations)
    const setUsersPictures= useUserStore(state => state.setUsersPictures)
    const [carregou, setCarregou] = useState(false)
    const token = localStorage.getItem('token')

    const API_URL = process.env.REACT_APP_API_URL;

    
    const carregarDados = async () => {
        try {
            const userResp = await fetch(`${API_URL}/api/v1/usuarios`, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });

            const usuarioData = await userResp.json();

            const [amizadesResp, roomsResp, receivedSolicitationsResp] = await Promise.all([
                fetch(`${API_URL}/api/v1/amizades`, {
                    headers: { 'Authorization': `Bearer ${token}` }
                }),
                fetch(`${API_URL}/api/v1/rooms`, {
                    headers: { 'Authorization': `Bearer ${token}` }
                }),
                fetch(`${API_URL}/api/v1/amizades/getPendingReceivedFriendships`, {
                    headers: {'Authorization': `Bearer ${token}`}
                })
            ]);

            const amigosData = await amizadesResp.json();
            const roomsData = await roomsResp.json();
            const receivedSolicitationsData = await receivedSolicitationsResp.json();

            //downloadProfilePicture(token)
            const usersPicturesData = await downloadFriendsProfilePictures(token);

            
            setUsuario(usuarioData);
            setAmigos(amigosData);
            setRooms(roomsData);
            setUsersPictures(usersPicturesData);
            setReceivedSolicitations(receivedSolicitationsData);

            setCarregou(true);

        } catch (err) {
            console.log("Token expirado");
            navigate('/');
        }
    }

    useEffect(() => {
        const socket = new SockJS(`${API_URL}/chat`);
        const client = Stomp.over(socket);

        client.connect({}, () => {
            setStompClient(client);
        });

        return () => {
            client.disconnect();
        };
    }, []);

    useEffect(() => { carregarDados() }, [])

    return(
        <div>
            {
            carregou ? 
            <PaginaUsuarioContent/> 
            : 
            <p>Carregando...</p>
            }
        </div>
    )
}

export default PaginaUsuario