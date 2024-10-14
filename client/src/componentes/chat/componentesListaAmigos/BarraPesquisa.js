import { faSearch } from "@fortawesome/free-solid-svg-icons"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import styles from './BarraPesquisa.module.css'

function BarraPesquisa(){
    return(
        <div className={styles.barraPesquisa}>
            <input/>
            <button><FontAwesomeIcon icon={faSearch}/></button>
        </div>
    )
}

export default BarraPesquisa