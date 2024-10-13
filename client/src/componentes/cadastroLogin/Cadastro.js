import styles from './Cadastro.module.css';
import SubmitButton from './form_components/SubmitButton';
import FormCadastro from './FormCadastro';
import { Link, useNavigate } from 'react-router-dom';


function Cadastro(){
    const navigate = useNavigate()

    function cadastrarUsuario(usuario){
        console.log(usuario)
        fetch("http://localhost:8080/api/v1/usuarios",{
            method: 'POST',
            headers: {
                'Content-type': 'application/json',
            },
            body: JSON.stringify(usuario),
        })
        .then(resp => resp.json())
        .then(data => {
            navigate('/', {state:{message:"Conta criada com sucesso!"}})
        })
        .catch(err => console.log(err))
    }

    return(       
        <div className={styles.pagina_cadastro}>
            <Link className={styles.link} to="/">
                Login
            </Link>
            <div className={styles.form}>
                <FormCadastro handleSubmit={cadastrarUsuario}/>
            </div>
        </div>
    )
}

export default Cadastro