import img from '../../imagens/logo192.png'
import styles from "./UserMenu.module.css"

function UserMenu({usuario}){
    return (
        <div className={styles.container}>
            <img src={img} className={styles.component}/>
            <span>{usuario.nome}</span>
        </div>
    )
}

export default UserMenu