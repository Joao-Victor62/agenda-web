import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
// Lembre-se de criar esse serviço no seu arquivo api.js!
import { profissionalService } from '../services/api';

function ProfissionalForm() {
  const navigate = useNavigate();
  const { id } = useParams();

  // Adicionamos o campo categoria no estado inicial
  const [profissional, setProfissional] = useState({
    nome: '', telefone: '', email: '', endereco: '', categoria: ''
  });

  useEffect(() => {
    if (id) {
      profissionalService.buscar(id).then(res => {
        const dados = res.data;
        // Preenche o formulário com os dados que vieram do banco
        setProfissional({
          nome: dados.nome || '',
          telefone: dados.telefone || '',
          email: dados.email || '',
          endereco: dados.endereco || '',
          // O backend envia a Categoria com 'C' maiúsculo e como um objeto
          categoria: dados.Categoria?.categoria || ''
        });
      });
    }
  }, [id]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Precisamos montar o objeto (payload) exatamente como o ProfissionalDaSaudeRequest do backend exige
    const payload = {
      nome: profissional.nome,
      telefone: profissional.telefone,
      email: profissional.email,
      endereco: profissional.endereco,
      categoria: {
        categoria: profissional.categoria
      }
    };

    try {
      if (id) {
        await profissionalService.atualizar(id, payload);
      } else {
        await profissionalService.criar(payload);
      }
      navigate('/profissionais'); // Redireciona para a nova rota da lista
    } catch (error) {
      console.error('Erro ao salvar profissional:', error);
    }
  };

  return (
    <div>
      <h2>{id ? 'Editar Profissional' : 'Novo Profissional'}</h2>
      <form onSubmit={handleSubmit} className="form">
        <div className="form-group">
          <label htmlFor="nome">Nome *</label>
          <input id="nome" type="text" value={profissional.nome} required
            onChange={e => setProfissional({ ...profissional, nome: e.target.value })} />
        </div>

        <div className="form-group">
          <label htmlFor="categoria">Categoria *</label>
          <input id="categoria" type="text" placeholder="Ex: Cardiologista, Pediatra..." value={profissional.categoria} required
            onChange={e => setProfissional({ ...profissional, categoria: e.target.value })} />
        </div>

        <div className="form-group">
          <label htmlFor="telefone">Telefone</label>
          <input id="telefone" type="text" value={profissional.telefone}
            onChange={e => setProfissional({ ...profissional, telefone: e.target.value })} />
        </div>
        <div className="form-group">
          <label htmlFor="email">Email</label>
          <input id="email" type="email" value={profissional.email}
            onChange={e => setProfissional({ ...profissional, email: e.target.value })} />
        </div>
        <div className="form-group">
          <label htmlFor="endereco">Endereço</label>
          <input id="endereco" type="text" value={profissional.endereco}
            onChange={e => setProfissional({ ...profissional, endereco: e.target.value })} />
        </div>
        <button type="submit" className="btn btn-primary">Salvar</button>
        <button type="button" className="btn" onClick={() => navigate('/profissionais')}>Cancelar</button>
      </form>
    </div>
  );
}

export default ProfissionalForm;