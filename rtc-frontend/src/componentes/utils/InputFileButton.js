import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import styles from './InputFileButton.module.css'


function InputFileButton({typeFile, icon, handleFile}){
    return(
        <div className={styles.upload_file_container}>
            <label htmlFor="file-input" className="upload-file-button">
                <FontAwesomeIcon className={styles.button_icon} icon={icon}/>
            </label>
            <input 
            id="file-input" 
            type="file" 
            accept={`${typeFile}/*`}
            style={{ display: 'none' }} 
            onChange={handleFile}
            />
        </div>
    )
}

export default InputFileButton