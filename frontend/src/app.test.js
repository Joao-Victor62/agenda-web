import { render, screen } from '@testing-library/react';
import App from './App';

test('garante que a tela inicial renderiza sem quebrar', () => {
    render(<App />);
    // Procura pela palavra "Agenda" que colocamos na Navbar
    const elementoTitulo = screen.getByText(/Agenda/i);
    expect(elementoTitulo).toBeInTheDocument();
});