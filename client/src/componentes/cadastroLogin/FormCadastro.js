import Input from './form_components/Input';
import styles from './FormCadastro.module.css';
import SubmitButton from './form_components/SubmitButton';
import { useState } from 'react';

function FormCadastro({handleSubmit}){
    const [usuario, setUsuario] = useState({})

    const submit = (e) =>{
        e.preventDefault()
        console.log(usuario)
        handleSubmit(usuario)
    }

    function handleChange(e){
        setUsuario({...usuario, [e.target.name]:e.target.value})
        console.log(usuario)
    }

    return(
        <form onSubmit={submit} className={styles.form_cadastro}>
            <h2>Cadastrar novo usuário</h2>
            <Input
                type="text"
                text="Nome do usuário"
                name="nome"
                placeholder="Digite o seu nome"
                handleOnChange={handleChange}
            />
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
            <SubmitButton text='Cadastrar'/>
        </form>
    )
}

export default FormCadastro