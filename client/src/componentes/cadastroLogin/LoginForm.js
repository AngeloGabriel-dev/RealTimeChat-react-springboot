import Input from './form_components/Input';
import styles from './LoginForm.module.css';
import SubmitButton from './form_components/SubmitButton';
import { useState } from 'react';

function LoginForm({handleSubmit}){
    const [usuarioLogin, setUsuarioLogin] = useState({})

    const submit = (e) =>{
        e.preventDefault()
        handleSubmit(usuarioLogin)
    }

    function handleChange(e){
        setUsuarioLogin({...usuarioLogin, [e.target.name]:e.target.value})
    }
    
    return(
        <form onSubmit={submit} className={styles.login_form}>
            <h2 style={{textAlign:'center', padding:'10px'}}>Login</h2>
            <Input 
                type="email"
                text="Username"
                name="username"
                placeholder="Digite o seu username"
                handleOnChange={handleChange}
            />
            <Input 
                type="password"
                text="Senha"
                name="password"
                placeholder="Digite a sua senha"
                handleOnChange={handleChange}
            />
            <SubmitButton text='Login'/>
        </form>
    )
}

export default LoginForm