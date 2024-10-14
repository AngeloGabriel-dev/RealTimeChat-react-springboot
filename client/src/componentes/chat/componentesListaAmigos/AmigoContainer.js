import styles from './AmigoContainer.module.css'
import img from '../../../imagens/userImage.jpg'

function AmigoContainer({amigo, handleOnClick, selecionado}){
    const retornaId = (e)=>{
        e.preventDefault()
        handleOnClick(amigo.id)
    }
    
    return(
        <div>
            <div className={selecionado? styles.container_selecionado:styles.container} onClick={retornaId}> 
                <img src={img} className={styles.component}/>
                <span>{amigo.nome}</span>
            </div>
        </div>
    )
}

export default AmigoContainer