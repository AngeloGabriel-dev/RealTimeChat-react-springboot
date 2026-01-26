import { useEffect, useState } from 'react'
import ChatBox from './ChatBox.js'
import ListaAmigos from './ListaAmigos.js'
import styles from './PaginaUsuario.module.css'
import { useNavigate } from 'react-router-dom'
import PaginaUsuarioContent from './PaginaUsuarioContent.js'
import img from '../../imagens/userImage.jpg'

function downloadProfilePicture(token, id) {
    fetch("http://localhost:8080/api/v1/storage/downloadProfilePicture", {
    headers: {
        "Authorization": `Bearer ${token}`,
    },
    method: "GET"
    })
    .then(resp => resp.text())
    .then(data => {
        if(data === "Erro ao baixar image"){
            localStorage.setItem("img_profile_url"+id, img)
        }
        else{
            localStorage.setItem("img_profile_url"+id, data)
        }
        
    })
    //localStorage.setItem("img_profile_url"+id, response)
    //return url
}

function downloadFriendsProfilePictures(token){
    const pictures = fetch("http://localhost:8080/api/v1/storage/downloadFriendsProfilePicture", {
        headers: {
            "Authorization": `Bearer ${token}`
        },
        method: "GET"
    })
    .then(resp => resp.json())
    .catch(error => console.log(error))

    return pictures
}


function PaginaUsuario(){
    const navigate = useNavigate();
    const [usuario, setUsuario] = useState({})
    const [rooms, setRooms] = useState([])
    const [carregou, setCarregou] = useState(false)
    const token = localStorage.getItem('token')
    const [usersPictures, setUsersPictures] = useState({})
    

    useEffect(()=>{
        fetch("http://localhost:8080/api/v1/usuarios", {
            headers:{
                'Authorization': `Bearer ${token}`,
                'Content-Type':'application/json'
            },
            method:'GET'
        })
        .then(resp => resp.json())
        .then(data => {
            setUsuario(data);
            downloadProfilePicture(token, data.id)
            //downloadFriendsProfilePictures(token).then(data => setUsersPictures(data))
            //console.log(downloadFriendsProfilePictures(token))
        })
        .catch(err => {
            console.log('Seu token expirou!')
            navigate('/')
        })
    }, [])

    useEffect(() => {
        if (usuario.id != null){
            fetch("http://localhost:8080/api/v1/amizades", {
                headers:{
                    'Authorization': `Bearer ${token}`,
                    'Content-Type':'application/json'
                },
                method:'GET'
            })
            .then(resp => resp.json())
            .then(data => {
                setUsuario({...usuario, ['amigos']:data})

            })
            .catch(err => console.log(err))
        }
    }, [usuario.id])

    useEffect(()=>{
        if(usuario.id !== null){
            fetch("http://localhost:8080/api/v1/rooms", {
                headers:{
                    'Authorization': `Bearer ${token}`,
                    'Content-Type':'application/json'
                },
                method: "GET"
            })
            .then(resp => resp.json())
            .then(data => {
                setRooms(data)  
                setCarregou(true)
            })
            .catch(err => console.log(err))
        }
    }, [usuario.amigos])

    return(
        <div>
            {
            carregou ? 
            <PaginaUsuarioContent 
                amigos={usuario.amigos} 
                usuario={usuario} 
                token={token} 
                roomsPictures={usersPictures}
                rooms={rooms}
            /> 
            : 
            <p>Carregando...</p>
            }
        </div>
    )
}

export default PaginaUsuario