import { useState } from 'react'
import AmigoContainer from './componentesListaAmigos/AmigoContainer'
import BarraPesquisa from './componentesListaAmigos/BarraPesquisa'
import styles from './ListaAmigos.module.css'

function ListaAmigos({amigos, handleId}){
    const [amigoSelecionado, setAmigoSelecionado] = useState()

    function usuarioSelecionado(id){
        setAmigoSelecionado(id)
        handleId(id)
    }

    return(
        <div className={styles.lista}>
            <BarraPesquisa/>
            {
                amigos.map((amigo) => 
                <AmigoContainer 
                    amigo={amigo} 
                    selecionado={amigoSelecionado === amigo.id}
                    handleOnClick={usuarioSelecionado}/>
            )}
        </div>
    )
}

export default ListaAmigos