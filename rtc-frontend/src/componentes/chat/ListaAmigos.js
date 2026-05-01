import { useState } from 'react'
import BarraPesquisa from './componentesListaAmigos/BarraPesquisa.js'
import UserMenu from './UserMenu.js'
import styles from './ListaAmigos.module.css'
import Perfil from '../usuario/Perfil.js'
import img from '../../imagens/userImage.jpg'
import ChatCard from './componentesListaAmigos/ChatCard.js'
import SolicitationCard from './componentesListaSolicitacoes/SolicitationCard.js'
import { useUserStore } from '../utils/UseUserStore.js'
import { shallow } from "zustand/shallow";



function ListaAmigos({handleToggleMenu, handleToggleCreateRoomMenu}){
    const rooms = useUserStore(state => state.rooms);
    const amigos  = useUserStore(state => state.amigos);
    const roomSelecionadaId = useUserStore(state => state.roomSelecionadaId);
    const messagesByRoom = useUserStore(state => state.messagesByRoom);
    const setRoomSelecionadaId = useUserStore(state => state.setRoomSelecionadaId);
    const setMessagesByRoom = useUserStore(state => state.setMessagesByRoom);
    const receivedSolicitations = useUserStore(state => state.receivedSolicitations);
    const addReceivedSolicitation = useUserStore(state => state.addReceivedSolicitation);

    const API_URL = process.env.REACT_APP_API_URL;

    
    const pegarIdRoom = async (id) => {
        console.log(id)
        if(messagesByRoom[id] === undefined){
            const messagesResp = await fetch(`${API_URL}/api/v1/chatMessages/${id}`, {
                headers:{
                    'Authorization': `Bearer ${localStorage.getItem("token")}`,
                    'Content-Type':'application/json'
                },
                method:'GET'
            })
            const messages = await messagesResp.json()
            setMessagesByRoom(id, messages)
        }
        setRoomSelecionadaId(id)     
    }

    return(
        <div className={styles.lista}>
            <UserMenu 
                onToggleMenu={handleToggleMenu} 
                onToggleCreateRoomMenu={handleToggleCreateRoomMenu}
            />
            <BarraPesquisa/>
            {
                rooms.map((room) => 
                <ChatCard
                    room={room} 
                    handleOnClick={pegarIdRoom}
                    selecionado={roomSelecionadaId === room.id}
                />)
            }
            <br/>
            <span>Solicitações Pendentes</span>
            {
                receivedSolicitations.map((solicitation) => 
                <SolicitationCard
                    solicitation={solicitation} 
                />)
            }
        </div>
    )
}

export default ListaAmigos