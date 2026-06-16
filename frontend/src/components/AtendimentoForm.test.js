import React from 'react';
// Adicionamos o 'act' na importação abaixo
import { render, screen, fireEvent, waitFor, act } from '@testing-library/react';
import AtendimentoForm from './AtendimentoForm';

jest.mock('../services/api', () => ({
    atendimentoService: {
        criar: jest.fn(),
        atualizar: jest.fn(),
        buscar: jest.fn()
    },
    receitaService: {
        listar: jest.fn()
    }
}));

const mockNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
    useNavigate: () => mockNavigate,
    useParams: () => ({ id: undefined })
}));

import { atendimentoService, receitaService } from '../services/api';

describe('Testes do Componente AtendimentoForm', () => {

    beforeEach(() => {
        jest.clearAllMocks();
    });

    test('deve preencher o formulário e chamar a função de criar atendimento', async () => {
        receitaService.listar.mockResolvedValue({ data: [] });
        atendimentoService.criar.mockResolvedValue({});

        // O 'act' avisa ao React para aguardar as atualizações de estado do useEffect
        await act(async () => {
            render(<AtendimentoForm />);
        });

        const inputData = screen.getByLabelText('Data *');
        const inputHorario = screen.getByLabelText('Horário *');
        const inputProblema = screen.getByLabelText('Problema');
        const botaoSalvar = screen.getByRole('button', { name: 'Salvar' });

        fireEvent.change(inputData, { target: { value: '2024-12-25' } });
        fireEvent.change(inputHorario, { target: { value: '14:30' } });
        fireEvent.change(inputProblema, { target: { value: 'Dor de cabeça forte' } });

        fireEvent.click(botaoSalvar);

        await waitFor(() => {
            expect(atendimentoService.criar).toHaveBeenCalledTimes(1);
            expect(atendimentoService.criar).toHaveBeenCalledWith({
                data: '2024-12-25',
                horario: '14:30',
                problema: 'Dor de cabeça forte',
                receita: null
            });
            expect(mockNavigate).toHaveBeenCalledWith('/atendimentos');
        });
    });

    test('deve chamar a navegação de cancelar ao clicar no botão Cancelar', async () => {
        receitaService.listar.mockResolvedValue({ data: [] });

        // Novamente, aguardamos a renderização completa
        await act(async () => {
            render(<AtendimentoForm />);
        });

        const botaoCancelar = screen.getByRole('button', { name: 'Cancelar' });
        fireEvent.click(botaoCancelar);

        expect(mockNavigate).toHaveBeenCalledWith('/atendimentos');
    });

});