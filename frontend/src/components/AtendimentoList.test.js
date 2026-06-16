import React from 'react';
import '@testing-library/jest-dom';
import { render, screen, fireEvent, waitFor, act } from '@testing-library/react';
// Importamos o MemoryRouter para simular a navegação para os componentes <Link>
import { MemoryRouter } from 'react-router-dom';
import AtendimentoList from './AtendimentoList';

// 1. MOCK DA API
jest.mock('../services/api', () => ({
    atendimentoService: {
        listar: jest.fn(),
        deletar: jest.fn()
    }
}));

import { atendimentoService } from '../services/api';

describe('Testes do Componente AtendimentoList', () => {

    beforeEach(() => {
        jest.clearAllMocks();
        // Limpamos o mock do window.confirm se ele foi usado
        jest.restoreAllMocks();
    });

    test('deve exibir a mensagem de carregando e depois renderizar a lista com dados', async () => {
        // Preparação: Criamos dados falsos para a API devolver
        const dadosFalsos = [
            { id: 1, data: '2024-12-25', horario: '14:30', problema: 'Dor de cabeça', receita: { receita: 'Paracetamol' } },
            { id: 2, data: '2024-12-26', horario: '09:00', problema: 'Dor nas costas', receita: null }
        ];
        atendimentoService.listar.mockResolvedValue({ data: dadosFalsos });

        // Envolvemos no act por causa do useEffect e no MemoryRouter por causa dos <Link>
        await act(async () => {
            render(
                <MemoryRouter>
                    <AtendimentoList />
                </MemoryRouter>
            );
        });

        // Verifica se os dados apareceram na tabela
        expect(screen.getByText('2024-12-25')).toBeInTheDocument();
        expect(screen.getByText('Dor de cabeça')).toBeInTheDocument();
        expect(screen.getByText('Paracetamol')).toBeInTheDocument();

        // Verifica o segundo item (que não tem receita, então deve mostrar '-')
        expect(screen.getByText('2024-12-26')).toBeInTheDocument();
        expect(screen.getAllByText('-').length).toBeGreaterThan(0);
    });

    test('deve renderizar a mensagem de lista vazia quando não houver dados', async () => {
        // Preparação: API devolve uma lista vazia
        atendimentoService.listar.mockResolvedValue({ data: [] });

        await act(async () => {
            render(
                <MemoryRouter>
                    <AtendimentoList />
                </MemoryRouter>
            );
        });

        // Verifica se a mensagem de "nenhum atendimento" apareceu
        expect(screen.getByText('Nenhum atendimento cadastrado.')).toBeInTheDocument();
    });

    test('deve deletar um atendimento ao confirmar a exclusão', async () => {
        // Preparação: API devolve um item
        const dadosFalsos = [
            { id: 99, data: '2024-12-31', horario: '10:00', problema: 'Retorno', receita: null }
        ];
        atendimentoService.listar.mockResolvedValue({ data: dadosFalsos });
        atendimentoService.deletar.mockResolvedValue({}); // O deletar não retorna nada normalmente

        // Simulamos que o usuário clicou em "OK/Sim" no alerta do navegador
        jest.spyOn(window, 'confirm').mockReturnValue(true);

        await act(async () => {
            render(
                <MemoryRouter>
                    <AtendimentoList />
                </MemoryRouter>
            );
        });

        // Achamos o botão de excluir
        const botaoExcluir = screen.getByRole('button', { name: 'Excluir' });

        // Clicamos no botão
        await act(async () => {
            fireEvent.click(botaoExcluir);
        });

        // Verificamos se a API foi chamada com o ID 99
        expect(atendimentoService.deletar).toHaveBeenCalledWith(99);

        // Como a função deletar chama o carregarAtendimentos() de novo, listar deve ter sido chamado 2 vezes (1 no load, 1 após deletar)
        expect(atendimentoService.listar).toHaveBeenCalledTimes(2);
    });

});