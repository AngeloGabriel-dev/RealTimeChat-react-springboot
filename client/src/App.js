import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Cadastro from './componentes/cadastroLogin/Cadastro';
import Login from './componentes/cadastroLogin/Login';
import PaginaUsuario from './componentes/chat/PaginaUsuario';

function App() {
  return (
    <div>
      <Router>
        <Routes>
          <Route path='/pagina_usuario' element={<PaginaUsuario/>}/>
          <Route path='/cadastro' element={<Cadastro/>}/>
          <Route exact path='/' element={<Login/>}/>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
