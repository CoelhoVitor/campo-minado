package br.com.cod3r.cm.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.cod3r.cm.excecao.ExplosaoException;

class CampoTeste {

	private Campo campo;
	
	@BeforeEach
	void iniciarCampo () {
		campo = new Campo(3, 3);
	}
	
	// 0,0 | 1,0 | 2,0 | 3,0
	// 0,1 | 1,1 | 2,1 | 3,1
	// 0,2 | 1,2 | 2,2 | 3,2
	// 0,3 | 1,3 | 2,3 | 3,3
	
	@Test
	void testeVizinhoDistanciaPerpendicularEsquerda() {
		Campo vizinho = new Campo(3, 2);		
		boolean resultado = campo.adicionarVizinho(vizinho);		
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoDistanciaPerpendicularDireita() {
		Campo vizinho = new Campo(3, 4);		
		boolean resultado = campo.adicionarVizinho(vizinho);		
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoDistanciaPerpendicularCima() {
		Campo vizinho = new Campo(2, 3);		
		boolean resultado = campo.adicionarVizinho(vizinho);		
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoDistanciaPerpendicularBaixo() {
		Campo vizinho = new Campo(4, 3);		
		boolean resultado = campo.adicionarVizinho(vizinho);		
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoDistanciaDiagonalEsquerdaCima() {
		Campo vizinho = new Campo(2, 2);		
		boolean resultado = campo.adicionarVizinho(vizinho);		
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoDistanciaDiagonalDireitaCima() {
		Campo vizinho = new Campo(2, 4);		
		boolean resultado = campo.adicionarVizinho(vizinho);		
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoDistanciaDiagonalEsquerdaBaixo() {
		Campo vizinho = new Campo(4, 2);		
		boolean resultado = campo.adicionarVizinho(vizinho);		
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoDistanciaDiagonalDireitaBaixo() {
		Campo vizinho = new Campo(4, 4);		
		boolean resultado = campo.adicionarVizinho(vizinho);		
		assertTrue(resultado);
	}
	
	@Test
	void testeNaoVizinho() {
		Campo vizinho = new Campo(1, 1);		
		boolean resultado = campo.adicionarVizinho(vizinho);		
		assertFalse(resultado);
	}

	@Test
	void testeValorPadraoAtributoMarcado() {
		assertFalse(campo.isMarcado());
	}
	
	@Test
	void testeAlternarMarcacao() {
		campo.alternarMarcacao();
		assertTrue(campo.isMarcado());
	}
	
	@Test
	void testeAlternarMarcacaoDuasChamadas() {
		campo.alternarMarcacao();
		campo.alternarMarcacao();
		assertFalse(campo.isMarcado());
	}
	
	@Test
	void testeAbrirNaoMinadoNaoMarcado() {
		assertTrue(campo.abrir());
	}
	
	@Test
	void testeAbrirNaoMinadoMarcado() {
		campo.desminar();
		campo.alternarMarcacao();
		assertFalse(campo.abrir());
	}
	
	@Test
	void testeAbrirMinadoMarcado() {
		campo.minar();
		campo.alternarMarcacao();
		assertFalse(campo.abrir());
	}
	
	@Test
	void testeAbrirMinadoNãoMarcado() {
		campo.minar();
		
		assertThrows(ExplosaoException.class, () -> {
			campo.abrir();
		});
	}
	
	@Test
	void testeAbrirComVizinhosSemMina() {
		Campo campo11 = new Campo(1, 1);
		
		Campo campo22 = new Campo(2, 2);
		campo22.adicionarVizinho(campo11);
		
		campo.adicionarVizinho(campo22);
		campo.abrir();
		
		assertTrue(campo22.isAberto() && campo11.isAberto());
	}
	
	@Test
	void testeAbrirComVizinhosComMina() {
		Campo campo11 = new Campo(1, 1);
		Campo campo12 = new Campo(1, 2);
		campo12.minar();
		
		Campo campo22 = new Campo(2, 2);
		campo22.adicionarVizinho(campo11);
		campo22.adicionarVizinho(campo12);
		
		campo.adicionarVizinho(campo22);
		campo.abrir();
		
		assertTrue(campo22.isAberto() && campo11.isFechado());
	}

	@Test
	void testeLinhaColuna() {
		assertEquals(3, campo.getLinha());
		assertEquals(3, campo.getColuna());
	}
	
	@Test
	void testeObjetivosAlcançados() {
//		campo, campo11, campo22 = !minado aberto
//		campo12 = minado marcado
		
		Campo campo11 = new Campo(1, 1);
		Campo campo12 = new Campo(1, 2);
		campo12.minar();
		campo12.alternarMarcacao();
		
		Campo campo22 = new Campo(2, 2);
		campo22.adicionarVizinho(campo11);
		campo22.adicionarVizinho(campo12);
		
		campo.adicionarVizinho(campo22);
		campo.abrir();
		
		assertTrue(campo.objetivoAlcancado());
		assertTrue(campo11.objetivoAlcancado());
		assertTrue(campo12.objetivoAlcancado());
		assertTrue(campo22.objetivoAlcancado());
	}
	
	@Test
	void testeObjetivosNaoAlcançados() {
//		campo, campo22 = !minado aberto
//		campo11 = !minado !aberto
//		campo12 = minado !marcado
		
		Campo campo11 = new Campo(1, 1);
		Campo campo12 = new Campo(1, 2);
		campo12.minar();
		
		Campo campo22 = new Campo(2, 2);
		campo22.adicionarVizinho(campo11);
		campo22.adicionarVizinho(campo12);
		
		campo.adicionarVizinho(campo22);
		campo.abrir();
		
		assertTrue(campo.objetivoAlcancado());
		assertFalse(campo11.objetivoAlcancado());
		assertFalse(campo12.objetivoAlcancado());
		assertTrue(campo22.objetivoAlcancado());
	}
	
	@Test
	void testeMinasNaVizinhanca() {		
		Campo campo12 = new Campo(1, 2);
		campo12.minar();
		
		Campo campo22 = new Campo(2, 2);
		campo22.adicionarVizinho(campo12);
		
		campo.adicionarVizinho(campo22);
		
		assertEquals(0, campo.minasNaVizinhanca());
		assertEquals(1, campo22.minasNaVizinhanca());
	}
	
	@Test
	void testeReiniciar() {		
		campo.minar();
		campo.alternarMarcacao();
		
		campo.reiniciar();
		
		assertFalse(campo.isAberto());
		assertFalse(campo.isMarcado());
		assertFalse(campo.isMinado());		
	}
	
	@Test
	void testeToString() {
		assertEquals("?", campo.toString());
		
		campo.abrir();
		assertEquals(" ", campo.toString());
		
//		campo.reiniciar();
//		campo.minar();
//		campo.abrir();
//		assertEquals("*", campo.toString());
		
		Campo campo22 = new Campo(2, 2);
		campo22.minar();
		campo.adicionarVizinho(campo22);
		campo.abrir();
		assertEquals("1", campo.toString());
		
		campo.reiniciar();
		campo.alternarMarcacao();
		assertEquals("x", campo.toString());
	}
	
}
