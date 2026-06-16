import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
// Importamos o novo serviço que você vai configurar no api.js
import { atendimentoService } from '../services/api';

function AtendimentoList() {
  const [atendimentos, setAtendimentos] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    carregarAtendimentos();
  }, []);

  const carregarAtendimentos = async () => {
    try {
      const response = await atendimentoService.listar();
      setAtendimentos(response.data);
    } catch (error) {
      console.error('Erro ao carregar atendimentos:', error);
    } finally {
      setLoading(false);
    }
  };

  const deletarAtendimento = async (id) => {
    if (window.confirm('Tem certeza que deseja excluir este atendimento?')) {
      try {
        await atendimentoService.deletar(id);
        carregarAtendimentos();
      } catch (error) {
        console.error('Erro ao deletar atendimento:', error);
      }
    }
  };

  if (loading) return <p>Carregando...</p>;

  return (
    <div>
      <div className="header">
        <h2>📅 Atendimentos</h2>
        {/* Atualizamos o link para a nova rota */}
        <Link to="/atendimentos/novo" className="btn btn-primary">+ Novo Atendimento</Link>
      </div>

      <table className="table">
        <thead>
          <tr>
            {/* Colunas atualizadas de acordo com o banco de dados */}
            <th>Data</th>
            <th>Horário</th>
            <th>Problema</th>
            <th>Receita</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {atendimentos.map(atendimento => (
            <tr key={atendimento.id}>
              {/* Variaveis ajustadas para bater com o AtendimentoGetResponse.java do backend */}
              <td>{atendimento.data}</td>
              <td>{atendimento.horario}</td>
              <td>{atendimento.problema}</td>
              <td>{atendimento.receita?.receita || '-'}</td>
              <td>
                {/* Atualizamos o link de edição para a nova rota */}
                <Link to={`/atendimentos/editar/${atendimento.id}`} className="btn btn-sm">Editar</Link>
                <button onClick={() => deletarAtendimento(atendimento.id)} className="btn btn-danger btn-sm">
                  Excluir
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {atendimentos.length === 0 && <p className="empty">Nenhum atendimento cadastrado.</p>}
    </div>
  );
}

export default AtendimentoList;