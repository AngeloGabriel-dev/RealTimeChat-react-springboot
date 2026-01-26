import Input from '../cadastroLogin/form_components/Input'
import styles from './CreateRoomMenu.module.css'
import AmigoContainer from './AmigoContainer'
import SubmitButtonRoomForm from './SubmitButtonRoomForm'
import ClosePageButton from '../utils/ClosePageButton'
import { useState } from 'react'
import { json } from 'react-router-dom'

function CreateRoomMenu({amigos, usersPictures, token}){
    const [room, setRoom] = useState({'users_id':[]})

    function handleChange(e){
        setRoom({...room, [e.target.name]:e.target.value})
        console.log(room)
    }

    function adicionarAmigo(id){
        setRoom((prevRoom)=>{
            if(!prevRoom.users_id.includes(id)){
                return {...room, users_id: [...room.users_id, id]}
            }
            else{
                return {...room, users_id: room.users_id.filter((amigoId) => amigoId !== id)}
            }
        })
        console.log(room)
    }

    function submit(e){
        e.preventDefault()
        //setRoom({...room, users_id:listaAmigos})
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
        {console.log(amigos)}
        {amigos.map((amigo)=>
            <AmigoContainer 
                selecionado={room['users_id'].includes(amigo.id)}
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