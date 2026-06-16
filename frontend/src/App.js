import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';

// Importando os novos componentes
import ProfissionalList from './components/ProfissionalList';
import ProfissionalForm from './components/ProfissionalForm';
import AtendimentoList from './components/AtendimentoList';
import AtendimentoForm from './components/AtendimentoForm';

import './App.css';

function App() {
  return (
    <Router>
      <div className="App">
        <nav className="navbar">
          <h1>🩺 Agenda Médica Web</h1> {/* Atualizamos o título */}
          <div className="nav-links">
            <Link to="/profissionais">Profissionais</Link>
            <Link to="/atendimentos">Atendimentos</Link>
          </div>
        </nav>

        <main className="container">
          <Routes>
            <Route path="/" element={<ProfissionalList />} />

            {/* Rotas de Profissionais */}
            <Route path="/profissionais" element={<ProfissionalList />} />
            <Route path="/profissionais/novo" element={<ProfissionalForm />} />
            <Route path="/profissionais/editar/:id" element={<ProfissionalForm />} />

            {/* Rotas de Atendimentos */}
            <Route path="/atendimentos" element={<AtendimentoList />} />
            <Route path="/atendimentos/novo" element={<AtendimentoForm />} />
            <Route path="/atendimentos/editar/:id" element={<AtendimentoForm />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;