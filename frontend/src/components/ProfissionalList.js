import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { profissionalService } from '../services/api';

function ProfissionalList() {
  const [profissionais, setProfissionais] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    carregarProfissionais();
  }, []);

  const carregarProfissionais = async () => {
    try {
      const response = await profissionalService.listar();
      setProfissionais(response.data);
    } catch (error) {
      console.error('Erro ao carregar profissionais:', error);
    } finally {
      setLoading(false);
    }
  };

  const deletarProfissional = async (id) => {
    if (!id) {
      alert("Erro de integração: O backend não devolveu o ID deste profissional!");
      return;
    }
    if (window.confirm('Tem certeza que deseja excluir este profissional?')) {
      try {
        await profissionalService.deletar(id);
        carregarProfissionais();
      } catch (error) {
        console.error('Erro ao deletar profissional:', error);
      }
    }
  };

  if (loading) return <p>Carregando...</p>;

  return (
    <div>
      <div className="header">
        <h2>📋 Profissionais da Saúde</h2>
        <Link to="/profissionais/novo" className="btn btn-primary">+ Novo Profissional</Link>
      </div>

      <table className="table">
        <thead>
          <tr>
            <th>Nome</th>
            <th>Categoria</th>
            <th>Telefone</th>
            <th>Email</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {profissionais.map((prof, index) => (
            // Usa o index como fallback caso o backend esqueça de mandar o id
            <tr key={prof.id || index}>
              <td>{prof.nome}</td>
              <td>{prof.Categoria?.categoria || '-'}</td>
              <td>{prof.telefone}</td>
              <td>{prof.email}</td>
              <td>
                <Link to={`/profissionais/editar/${prof.id}`} className="btn btn-sm">Editar</Link>
                <button onClick={() => deletarProfissional(prof.id)} className="btn btn-danger btn-sm">
                  Excluir
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {profissionais.length === 0 && <p className="empty">Nenhum profissional cadastrado.</p>}
    </div>
  );
}

export default ProfissionalList;