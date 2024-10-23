import styles from "./Perfil.module.css"
import img from "../../imagens/userImage.jpg"
import InputFileButton from "../utils/InputFileButton"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faCamera, faClose } from "@fortawesome/free-solid-svg-icons"
import { useState } from "react"

function Perfil({usuario, onToggleMenu, token}){
    const [profileImage, setProfileImage] = useState(usuario.profileImage)

    const onHandleFile = (e) => {
        const file = e.target.files[0]
        if (file){
            const reader = new FileReader();
            reader.onloadend = () => {
                setProfileImage(reader.result)
                const formData = new FormData();
                formData.append("file", file)
                fetch("http://localhost:8080/api/v1/storage/updateProfilePicture", {
                    headers:
                        {
                            'Authorization': `Bearer ${token}`
                        },
                    method:"POST",
                    body: formData
                })
                .then((response) => response.json())
                .then((data) => console.log('Sucesso ' + data))
                .catch(error => console.log('Erro ' + error))
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
                <img id="profile_image" className={styles.profile_img} src={localStorage.getItem("img_profile_url"+usuario.id)}/>
                
                
                <InputFileButton typeFile={"image"} icon={faCamera} handleFile={onHandleFile}/>
            </div>
            
        </div>
    )
}

export default Perfil