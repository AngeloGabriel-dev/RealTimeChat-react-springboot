import styles from './SubmitButton.module.css'

function SubmitButton({text}){
    return(
        <div>
            <input className={styles.btn}
                type="submit"
                value={text}
            />
        </div>
    )
}

export default SubmitButton