import { useEffect, useState } from 'react'
import ChatBox from './ChatBox.js'
import ListaAmigos from './ListaAmigos.js'
import styles from './PaginaUsuario.module.css'
import { useNavigate } from 'react-router-dom'
import PaginaUsuarioContent from './PaginaUsuarioContent.js'

function PaginaUsuario(){
    const navigate = useNavigate();
    const [usuario, setUsuario] = useState({})
    const [carregou, setCarregou] = useState(false)
    const token = localStorage.getItem('token')
    

    useEffect(()=>{
        setTimeout(()=>{
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
            })
            .catch(err => {
                console.log('Seu token expirou!')
                navigate('/')
            })
        }, 1000)
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
                setCarregou(true)
            })
            .catch(err => console.log(err))
        }
        
    }, [usuario.id])

    return(
        <div>
            {carregou ? <PaginaUsuarioContent amigos={usuario.amigos} usuario={usuario} token={token}/> : <p>Carregando...</p>}
        </div>
    )
}

export default PaginaUsuario