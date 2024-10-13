import styles from "./Perfil.module.css"

function Perfil({usuario}){
    return(
        <div>
            <span>{usuario.nome}</span>
        </div>
    )
}

export default Perfil