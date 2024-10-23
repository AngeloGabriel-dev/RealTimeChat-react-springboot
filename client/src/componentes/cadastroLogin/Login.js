import styles from './Login.module.css';
import LoginForm from './LoginForm';
import { Link, useNavigate } from 'react-router-dom';

function Login(){
    const navigate = useNavigate()

    function logarUsuario(usuario){
        console.log(usuario)
        fetch("http://localhost:8080/api/v1/auth",{
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
            <img src="https://storage.googleapis.com/realtimechat-6bf77.appspot.com/users/user_breakingbad%40email.com/profile_pic?X-Goog-Algorithm=GOOG4-RSA-SHA256&X-Goog-Credential=firebase-adminsdk-mg5bw%40realtimechat-6bf77.iam.gserviceaccount.com%2F20241022%2Fauto%2Fstorage%2Fgoog4_request&X-Goog-Date=20241022T203816Z&X-Goog-Expires=1200&X-Goog-SignedHeaders=host&X-Goog-Signature=703345bf98e33a8a55e3309b57694bad983eb3f985f65cd470b010b28d15b81409229cec605bd7a9f163dcbb2bda46932b60b7042eb0698ecfc7706280dea7ca2153df7983c11d164b9aa15d05fba29a5382d9019c7e9dd90181d8ca10a3adea285bd4bf3f75ea7ed43848e64a6040a0c16253bd0d13d341fd808a3806969990f9b797c00a96be1e8a67a970445540fc4f4326d2f2c26702267b30cbb6e42499e230cdca207b2241bb50860f73fcc565725e53248e883c4a6687525449708e36a50effec914a02e93d105ded496959658e1fe955ba86d8574fc4c9f69c8958381bea34f381f4dbebd08a0fcf5eac469a78bb8f50d194880bd082351f26c3b1ef"/>
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