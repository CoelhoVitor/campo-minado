package br.com.cod3r.cm.modelo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.cod3r.cm.excecao.ExplosaoException;

class TabuleiroTeste {
	
	private Tabuleiro tabuleiro;
	private int linhas = 6;
	private int colunas = 6;
	private int minas = 6;
	
	@BeforeEach
	void iniciarTabuleiro() {
		tabuleiro = new Tabuleiro(linhas, colunas, minas);
	}

	@Test
	void testeCampos() {
		assertEquals(linhas, tabuleiro.getLinhas());
		assertEquals(colunas, tabuleiro.getColunas());
		assertEquals(minas, tabuleiro.getMinas());
		assertEquals(linhas * colunas, tabuleiro.getCampos().size());
	}
	
	@Test
	void testeToString() {		
		String tabuleiroEsperado = 
				  "   0  1  2  3  4  5 \n"
				+ "0  ?  ?  ?  ?  ?  ? \n"
				+ "1  ?  ?  ?  ?  ?  ? \n"
				+ "2  ?  ?  ?  ?  ?  ? \n"
				+ "3  ?  ?  ?  ?  ?  ? \n"
				+ "4  ?  ?  ?  ?  ?  ? \n"
				+ "5  ?  ?  ?  ?  ?  ? \n";
		
		assertEquals(tabuleiroEsperado, tabuleiro.toString());
	}
	
	@Test
	void testeMarcacao() {
		String tabuleiroEsperado = 
				  "   0  1  2  3  4  5 \n"
			    + "0  ?  ?  ?  ?  ?  ? \n"
				+ "1  ?  ?  x  ?  ?  ? \n"
				+ "2  ?  ?  ?  ?  ?  ? \n"
				+ "3  ?  ?  x  ?  ?  ? \n"
				+ "4  ?  ?  ?  ?  x  ? \n"
				+ "5  ?  ?  ?  ?  ?  ? \n";
		
		tabuleiro.alternarMarcacao(1, 2);
		tabuleiro.alternarMarcacao(3, 2);
		tabuleiro.alternarMarcacao(4, 4);
		
		assertEquals(tabuleiroEsperado, tabuleiro.toString());
	}
	
	@Test
	void testeAbrirMinado() {
		tabuleiro.getCampos().get(0).minar();
		
		assertThrows(ExplosaoException.class, () -> {	
			tabuleiro.abrir(0, 0);
		});
	}
	
	@Test
	void testeAbrirNaoMinado() {
		tabuleiro.getCampos().get(0).desminar();
		
		assertDoesNotThrow(() -> tabuleiro.abrir(0, 0));
		
		assertNotEquals("?", tabuleiro.getCampos().get(0));
	}
	
	@Test
	void testeReiniciar() {		
		tabuleiro.getCampos().get(0).desminar();
		tabuleiro.abrir(0, 0);
		
		tabuleiro.reiniciar();
		
		String tabuleiroEsperado = 
				  "   0  1  2  3  4  5 \n"
				+ "0  ?  ?  ?  ?  ?  ? \n"
				+ "1  ?  ?  ?  ?  ?  ? \n"
				+ "2  ?  ?  ?  ?  ?  ? \n"
				+ "3  ?  ?  ?  ?  ?  ? \n"
				+ "4  ?  ?  ?  ?  ?  ? \n"
				+ "5  ?  ?  ?  ?  ?  ? \n";
		
		assertEquals(tabuleiroEsperado, tabuleiro.toString());
	}
	
	@Test
	void testeObjetivoAlcancado() {
		tabuleiro.getCampos().get(0).desminar();
		
		tabuleiro.getCampos().stream()
			.filter(c -> c.isMinado())
			.forEach(c -> c.alternarMarcacao());
		
		tabuleiro.abrir(0, 0);
		
		assertEquals(true, tabuleiro.objetivoAlcancado());
	}
}
