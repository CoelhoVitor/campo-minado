package br.com.cod3r.cm.modelo;

import java.util.ArrayList;
import java.util.List;

import br.com.cod3r.cm.excecao.ExplosaoException;

public class Campo {

	private final int linha;
	private final int coluna;
	
	private boolean aberto;
	private boolean minado;
	private boolean marcado;
	
	private List<Campo> vizinhos = new ArrayList<>();
	
	Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	
	boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDiferente = vizinho.linha != linha;
		boolean colunaDiferente = vizinho.coluna != coluna;
		boolean ehDiagonal = linhaDiferente && colunaDiferente;
		
		int deltaLinha = Math.abs(vizinho.linha - linha); 
		int deltaColuna = Math.abs(vizinho.coluna - coluna);			
		int deltaAbsoluta = Math.abs(deltaLinha + deltaColuna);
		
		boolean ehVizinhoDiagonal = ehDiagonal && deltaAbsoluta == 2;
		boolean ehVizinhoPerpendicular = !ehDiagonal && deltaAbsoluta == 1;
		
		if (ehVizinhoDiagonal || ehVizinhoPerpendicular) {
			vizinhos.add(vizinho);
			return true; 
		}
		
		return false;
	}

	void alternarMarcacao() {
		if (!aberto) marcado = !marcado;
	}
	
	boolean abrir() {		
		if (!aberto && !marcado) {
			aberto = true;
			
			if (minado) {
				throw new ExplosaoException();
			}
			
			if (vizinhancaSegura()) {
				vizinhos.forEach(v -> v.abrir());;
			}
			
			return true;
		}
		
		return false;
	}
	
	boolean vizinhancaSegura() {
		return vizinhos.stream().filter(v -> v.minado).allMatch(v -> v.marcado);
	}
	
	void minar() {
		minado = true;	
	}
	
	void desminar() {
		minado = false;
	}
	
	public boolean isMarcado() {
		return marcado;
	}
	
	void setAberto(boolean aberto) {
		this.aberto = aberto;
	}

	public boolean isAberto() {
		return aberto;
	}
	
	public boolean isFechado() {
		return !aberto;
	}
	
	public boolean isMinado() {
		return minado;
	}
	
	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	
	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido;
	}
	
	long minasNaVizinhanca() {
		return vizinhos.stream().filter(v -> v.minado).count();
	}
	
	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
	}
	
	public String toString() {
		if (marcado) {
			return "x";
		} 
		
		if (aberto && minado) {
			return "*";
		} 
		
		if (aberto && minasNaVizinhanca() > 0) {
			return Long.toString(minasNaVizinhanca());
		} 
		
		if (aberto ) {
			return " ";
		} 
		
		return "?";
	}
}
