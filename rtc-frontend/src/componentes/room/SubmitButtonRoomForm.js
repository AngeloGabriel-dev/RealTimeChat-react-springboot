import styles from './SubmitButtonRoomForm.module.css'

function SubmitButtonRoomForm({text}){
    return(
        <div>
            <input className={styles.btn}
                type="submit"
                value={text}
            />
        </div>
    )
}

export default SubmitButtonRoomForm