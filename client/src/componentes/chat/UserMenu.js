import img from '../../imagens/userImage.jpg'
import styles from "./UserMenu.module.css"

function UserMenu({usuario, onToggleMenu}){
    return (
        <div className={styles.container}>
            <img 
                src={img} 
                className={styles.component}
                onClick={onToggleMenu}
            />
            <span>{usuario.nome}</span>
        </div>
    )
}

export default UserMenu