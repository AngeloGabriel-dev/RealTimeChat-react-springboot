import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faClose } from "@fortawesome/free-solid-svg-icons"

function ClosePageButton({onToggleCreateRoom}){
    return(
        <div>
            <button onClick={onToggleCreateRoom}>
                {<FontAwesomeIcon icon={faClose}/>}
            </button>
        </div>
    )
}

export default ClosePageButton