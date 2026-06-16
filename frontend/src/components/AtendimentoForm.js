import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
// Alteramos os serviços para os novos endpoints
import { atendimentoService, receitaService } from '../services/api';

function AtendimentoForm() {
  const navigate = useNavigate();
  const { id } = useParams();

  // Atualizamos as chaves para bater com AtendimentoRequest.java
  const [atendimento, setAtendimento] = useState({
    data: '', horario: '', problema: '', receita: null
  });

  // O Atendimento agora é vinculado a uma Receita (e não mais a um Contato)
  const [receitas, setReceitas] = useState([]);

  useEffect(() => {
    // Carrega a lista de receitas para o campo de seleção (select)
    receitaService.listar().then(res => setReceitas(res.data));

    if (id) {
      atendimentoService.buscar(id).then(res => setAtendimento(res.data));
    }
  }, [id]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (id) {
        await atendimentoService.atualizar(id, atendimento);
      } else {
        await atendimentoService.criar(atendimento);
      }
      navigate('/atendimentos'); // Redireciona para a nova rota
    } catch (error) {
      console.error('Erro ao salvar atendimento:', error);
    }
  };

  return (
    <div>
      <h2>{id ? 'Editar Atendimento' : 'Novo Atendimento'}</h2>
      <form onSubmit={handleSubmit} className="form">

        <div className="form-group">
          <label htmlFor="data">Data *</label>
          <input id="data" type="date" value={atendimento.data} required
            onChange={e => setAtendimento({ ...atendimento, data: e.target.value })} />
        </div>

        <div className="form-group">
          <label htmlFor="horario">Horário *</label>
          <input id="horario" type="time" value={atendimento.horario} required
            onChange={e => setAtendimento({ ...atendimento, horario: e.target.value })} />
        </div>

        <div className="form-group">
          <label htmlFor="problema">Problema</label>
          <textarea id="problema" value={atendimento.problema}
            onChange={e => setAtendimento({ ...atendimento, problema: e.target.value })} />
        </div>

        <div className="form-group">
          <label htmlFor="receita">Receita vinculada</label>
          <select id="receita" value={atendimento.receita?.id || ''}
            onChange={e => setAtendimento({
              ...atendimento,
              receita: e.target.value ? { id: parseInt(e.target.value) } : null
            })}>
            <option value="">Selecione uma receita</option>
            {receitas.map(r => (
              <option key={r.id} value={r.id}>Receita #{r.id}</option>
            ))}
          </select>
        </div>

        <button type="submit" className="btn btn-primary">Salvar</button>
        <button type="button" className="btn" onClick={() => navigate('/atendimentos')}>Cancelar</button>
      </form>
    </div>
  );
}

export default AtendimentoForm;