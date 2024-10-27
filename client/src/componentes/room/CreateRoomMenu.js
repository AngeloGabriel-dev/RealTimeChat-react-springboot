import Input from '../cadastroLogin/form_components/Input'
import styles from './CreateRoomMenu.module.css'
import AmigoContainer from '../chat/componentesListaAmigos/AmigoContainer'
import SubmitButtonRoomForm from './SubmitButtonRoomForm'
import ClosePageButton from '../utils/ClosePageButton'
import { useState } from 'react'
import { json } from 'react-router-dom'

function CreateRoomMenu({amigos, usersPictures, token}){
    const [room, setRoom] = useState({})
    const [listaAmigos, setListaAmigos] = useState([])

    function handleChange(e){
        setRoom({...room, [e.target.name]:e.target.value})
    }

    function adicionarAmigo(id){
        setListaAmigos((prevLista)=>{
            if(!prevLista.includes(id)){
                return [...listaAmigos, id]
            }
            else{
                return listaAmigos.filter((amigoId) => amigoId !== id)
            }
        })
    }

    function submit(e){
        e.preventDefault()
        setRoom({...room, users_id:listaAmigos})
        fetch("http://localhost:8080/api/v1/rooms", {
            method: "POST",
            headers:{
                'Authorization': `Bearer ${token}`,
                'Content-Type':'application/json'
            },
            body: JSON.stringify(room)
        })
        .then(resp => resp.json())
        .then(data => console.log(data))
        .catch(err => console.log(err))
    }


    return (
    <form onSubmit={submit} className={styles.create_room_form}>
        <span className={styles.page_title}>Criar novo grupo</span>
        <Input className={styles.input_name}
            type="text" 
            text={"Nome do grupo"} 
            name={"nome"}
            placeholder={"Digite o nome do grupo"}
            handleOnChange={handleChange}
        />
        {amigos.map((amigo)=>
            <AmigoContainer 
                selecionado={listaAmigos.includes(amigo.id)}
                amigo={amigo} 
                userPicture={usersPictures[amigo.id]}        
                handleOnClick={adicionarAmigo}
            />
        )}
        <SubmitButtonRoomForm className={styles.submit_button} text={"Criar Grupo"}/>
    </form>
    )
}

export default CreateRoomMenu