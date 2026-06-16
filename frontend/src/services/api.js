import axios from 'axios';

const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_URL,
  headers: { 'Content-Type': 'application/json' }
});

// O backend manteve a rota /contatos para os profissionais
export const profissionalService = {
  listar: () => api.get('/contatos'),
  buscar: (id) => api.get(`/contatos/${id}`),
  criar: (profissional) => api.post('/contatos', profissional),
  atualizar: (id, profissional) => api.put(`/contatos/${id}`, profissional),
  deletar: (id) => api.delete(`/contatos/${id}`)
};

// Nova rota de Atendimentos
export const atendimentoService = {
  listar: () => api.get('/atendimentos'),
  buscar: (id) => api.get(`/atendimentos/${id}`),
  criar: (atendimento) => api.post('/atendimentos', atendimento),
  atualizar: (id, atendimento) => api.put(`/atendimentos/${id}`, atendimento),
  deletar: (id) => api.delete(`/atendimentos/${id}`)
};

// Rota de Receitas (apenas para listar no formulário)
export const receitaService = {
  listar: () => api.get('/receitas')
};

export default api;