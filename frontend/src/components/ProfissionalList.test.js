import React from 'react';
import '@testing-library/jest-dom';
import { render, screen, fireEvent, waitFor, act } from '@testing-library/react';
// Novamente usamos o MemoryRouter porque a tabela possui botões com <Link>
import { MemoryRouter } from 'react-router-dom';
import ProfissionalList from './ProfissionalList';

// 1. MOCK DA API
jest.mock('../services/api', () => ({
    profissionalService: {
        listar: jest.fn(),
        deletar: jest.fn()
    }
}));

import { profissionalService } from '../services/api';

describe('Testes do Componente ProfissionalList', () => {

    beforeEach(() => {
        jest.clearAllMocks();
        // Limpamos não apenas os mocks do Jest, mas também limpamos se tivermos
        // espionado funções do navegador (window.confirm e window.alert)
        jest.restoreAllMocks();
    });

    test('deve exibir a mensagem de carregando e depois renderizar a lista com dados', async () => {
        // Preparação: Criamos dados falsos para a API devolver (incluindo o formato de Categoria com 'C' maiúsculo)
        const dadosFalsos = [
            { id: 1, nome: 'Dr. João', Categoria: { categoria: 'Cardiologista' }, telefone: '31999999999', email: 'joao@email.com' },
            { id: 2, nome: 'Dra. Ana', Categoria: null, telefone: '31888888888', email: 'ana@email.com' }
        ];
        profissionalService.listar.mockResolvedValue({ data: dadosFalsos });

        await act(async () => {
            render(
                <MemoryRouter>
                    <ProfissionalList />
                </MemoryRouter>
            );
        });

        // Verifica se os dados do primeiro médico apareceram na tabela
        expect(screen.getByText('Dr. João')).toBeInTheDocument();
        expect(screen.getByText('Cardiologista')).toBeInTheDocument();

        // Verifica se os dados do segundo médico apareceram e se o fallback da categoria (o traço '-') funcionou
        expect(screen.getByText('Dra. Ana')).toBeInTheDocument();
        expect(screen.getByText('-')).toBeInTheDocument();
    });

    test('deve renderizar a mensagem de lista vazia quando não houver dados', async () => {
        profissionalService.listar.mockResolvedValue({ data: [] });

        await act(async () => {
            render(
                <MemoryRouter>
                    <ProfissionalList />
                </MemoryRouter>
            );
        });

        expect(screen.getByText('Nenhum profissional cadastrado.')).toBeInTheDocument();
    });

    test('deve deletar um profissional ao confirmar a exclusão', async () => {
        const dadosFalsos = [
            { id: 10, nome: 'Dr. Marcos', telefone: '111111', email: 'marcos@email.com' }
        ];
        profissionalService.listar.mockResolvedValue({ data: dadosFalsos });
        profissionalService.deletar.mockResolvedValue({});

        // Simulamos que o usuário clicou em "OK/Sim" no alerta
        jest.spyOn(window, 'confirm').mockReturnValue(true);

        await act(async () => {
            render(
                <MemoryRouter>
                    <ProfissionalList />
                </MemoryRouter>
            );
        });

        const botaoExcluir = screen.getByRole('button', { name: 'Excluir' });

        await act(async () => {
            fireEvent.click(botaoExcluir);
        });

        // Verificamos se perguntou pro usuário e se a exclusão foi feita no ID correto
        expect(window.confirm).toHaveBeenCalledWith('Tem certeza que deseja excluir este profissional?');
        expect(profissionalService.deletar).toHaveBeenCalledWith(10);

        // Lista carrega 1x no começo e 1x após excluir
        expect(profissionalService.listar).toHaveBeenCalledTimes(2);
    });

    test('deve exibir um alerta de erro e cancelar a exclusão se o ID estiver ausente', async () => {
        // Preparação: Enviamos dados com falha (o objeto não tem a propriedade 'id')
        const dadosFalsosSemId = [
            { nome: 'Dr. Sem ID', telefone: '222222', email: 'sem@email.com' }
        ];
        profissionalService.listar.mockResolvedValue({ data: dadosFalsosSemId });

        // Finge a existência do alert para o Jest conseguir interceptar a mensagem
        jest.spyOn(window, 'alert').mockImplementation(() => { });
        jest.spyOn(window, 'confirm');

        await act(async () => {
            render(
                <MemoryRouter>
                    <ProfissionalList />
                </MemoryRouter>
            );
        });

        const botaoExcluir = screen.getByRole('button', { name: 'Excluir' });

        await act(async () => {
            fireEvent.click(botaoExcluir);
        });

        // Verifica se o alert foi acionado com a frase exata que você programou
        expect(window.alert).toHaveBeenCalledWith("Erro de integração: O backend não devolveu o ID deste profissional!");

        // Garante que o sistema travou a operação aí mesmo (não pediu confirmação e não chamou a API de exclusão)
        expect(window.confirm).not.toHaveBeenCalled();
        expect(profissionalService.deletar).not.toHaveBeenCalled();
    });

});