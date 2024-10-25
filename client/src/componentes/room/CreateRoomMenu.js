import Input from '../cadastroLogin/form_components/Input'
import styles from './CreateRoomMenu.module.css'
import AmigoContainer from '../chat/componentesListaAmigos/AmigoContainer'
import SubmitButtonRoomForm from './SubmitButtonRoomForm'
import ClosePageButton from '../utils/ClosePageButton'

function CreateRoomMenu({amigos, usersPictures, handleToggleCreateRoom}){
    return (
        <form className={styles.create_room_form}>
            <span className={styles.page_title}>Criar novo grupo</span>
            <Input className={styles.input_name}
                type="text" 
                text={"Digite o nome do grupo"} 
                name={"nome_grupo"}
            />
            {amigos.map((amigo)=>
                <AmigoContainer 
                    amigo={amigo} 
                    userPicture={usersPictures[amigo.id]}        
                />
            )}
            <SubmitButtonRoomForm className={styles.submit_button} text={"Criar Grupo"}/>
        </form>
    )
}

export default CreateRoomMenu