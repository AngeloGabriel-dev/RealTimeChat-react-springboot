import styles from './Login.module.css';
import LoginForm from './LoginForm';
import { Link, useNavigate } from 'react-router-dom';

function Login(){
    const navigate = useNavigate()
    const API_URL = process.env.REACT_APP_API_URL;

    function logarUsuario(usuario){
        console.log(usuario)
        fetch(`${API_URL}/api/v1/auth`,{
            method: 'POST',
            headers: {
                'Content-type': 'application/json',

            },
            body: JSON.stringify(usuario),
        })
        .then(resp => resp.json())
        .then(data => {
            localStorage.setItem('token', data.token)
            navigate('/pagina_usuario')
        })
        .catch(err => console.log(err))
    }

    return(
        <div className={styles.pagina_login}>
            <Link className={styles.link} to="/cadastro">
                Cadastro
            </Link>
            <div className={styles.form}>
                <LoginForm handleSubmit={logarUsuario}/>
            </div>
        </div>
    )
}

export default Login