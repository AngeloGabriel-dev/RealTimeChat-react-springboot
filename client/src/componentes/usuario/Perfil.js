import styles from "./Perfil.module.css"
import img from "../../imagens/userImage.jpg"
import InputFileButton from "../utils/InputFileButton"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faCamera, faClose } from "@fortawesome/free-solid-svg-icons"
import { useState } from "react"

function Perfil({usuario, onToggleMenu}){
    const [profileImage, setProfileImage] = useState(usuario.profileImage)

    const onHandleFile = (e) => {
        console.log(e.target.value)
        const file = e.target.files[0]
        if (file){
            const reader = new FileReader();
            reader.onloadend = () => {
                setProfileImage(reader.result)
            }
            reader.readAsDataURL(file)
        }
    }

    return(
        <div className={styles.perfil}>
            <button onClick={onToggleMenu}>
                {<FontAwesomeIcon icon={faClose}/>}
            </button>
            <div className={styles.perfil_components}>
                <span className={styles.name}>{usuario.nome}</span>
                {profileImage ? 
                    <img className={styles.profile_img} src={profileImage}/>
                    :
                    <img className={styles.profile_img} src={img}/>
                }
                
                <InputFileButton typeFile={"image"} icon={faCamera} handleFile={onHandleFile}/>
            </div>
            
        </div>
    )
}

export default Perfil