import React from 'react';
import '@testing-library/jest-dom';
import { render, screen, fireEvent, waitFor, act } from '@testing-library/react';
import ProfissionalForm from './ProfissionalForm';

// 1. MOCK DO REACT ROUTER
const mockNavigate = jest.fn();
// Criamos a variável let para os parâmetros para podermos simular
// a página de "Novo" (sem id) e a página de "Editar" (com id)
let mockParams = { id: undefined };

jest.mock('react-router-dom', () => ({
    useNavigate: () => mockNavigate,
    useParams: () => mockParams
}));

// 2. MOCK DA API
jest.mock('../services/api', () => ({
    profissionalService: {
        criar: jest.fn(),
        atualizar: jest.fn(),
        buscar: jest.fn()
    }
}));

import { profissionalService } from '../services/api';

describe('Testes do Componente ProfissionalForm', () => {

    beforeEach(() => {
        jest.clearAllMocks();
        mockParams = { id: undefined }; // Por padrão, começa como Novo Profissional
    });

    test('deve preencher o formulário e chamar a função de criar montando o payload corretamente', async () => {
        profissionalService.criar.mockResolvedValue({});

        await act(async () => {
            render(<ProfissionalForm />);
        });

        // Achar os campos pelo label
        const inputNome = screen.getByLabelText('Nome *');
        const inputCategoria = screen.getByLabelText('Categoria *');
        const inputTelefone = screen.getByLabelText('Telefone');

        // Simular digitação
        fireEvent.change(inputNome, { target: { value: 'Dra. Ana' } });
        fireEvent.change(inputCategoria, { target: { value: 'Pediatra' } });
        fireEvent.change(inputTelefone, { target: { value: '31999999999' } });

        // Salvar
        const botaoSalvar = screen.getByRole('button', { name: 'Salvar' });
        fireEvent.click(botaoSalvar);

        await waitFor(() => {
            expect(profissionalService.criar).toHaveBeenCalledTimes(1);

            // Verifica se o payload foi montado exatamente como o backend exige
            expect(profissionalService.criar).toHaveBeenCalledWith({
                nome: 'Dra. Ana',
                telefone: '31999999999',
                email: '',
                endereco: '',
                categoria: {
                    categoria: 'Pediatra'
                }
            });
            expect(mockNavigate).toHaveBeenCalledWith('/profissionais');
        });
    });

    test('deve carregar os dados de um profissional para edição e chamar o atualizar', async () => {
        // Simulamos que estamos na rota /editar/1
        mockParams = { id: '1' };

        // Simulamos os dados como eles chegam do backend (Categoria com C maiúsculo)
        const dadosDoBackend = {
            nome: 'Dr. João',
            telefone: '31888888888',
            email: 'joao@email.com',
            endereco: 'Rua Principal, 100',
            Categoria: {
                categoria: 'Cardiologista'
            }
        };

        profissionalService.buscar.mockResolvedValue({ data: dadosDoBackend });
        profissionalService.atualizar.mockResolvedValue({});

        await act(async () => {
            render(<ProfissionalForm />);
        });

        // Verifica se os campos foram preenchidos corretamente ao abrir a tela
        const inputNome = screen.getByLabelText('Nome *');
        const inputCategoria = screen.getByLabelText('Categoria *');

        expect(inputNome.value).toBe('Dr. João');
        expect(inputCategoria.value).toBe('Cardiologista');

        // Altera apenas o nome e clica em salvar
        fireEvent.change(inputNome, { target: { value: 'Dr. João Silva' } });
        fireEvent.click(screen.getByRole('button', { name: 'Salvar' }));

        await waitFor(() => {
            // Verifica se a função de atualização foi chamada com o ID 1 e os dados modificados
            expect(profissionalService.atualizar).toHaveBeenCalledWith('1', {
                nome: 'Dr. João Silva',
                telefone: '31888888888',
                email: 'joao@email.com',
                endereco: 'Rua Principal, 100',
                categoria: {
                    categoria: 'Cardiologista'
                }
            });
            expect(mockNavigate).toHaveBeenCalledWith('/profissionais');
        });
    });

});