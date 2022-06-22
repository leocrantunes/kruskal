package br.unirio.edu.apa;

public class Instancia {
    private String nome;
    private int numGrafos;
    private Grafo[] grafos;

    public Instancia(String nome, int numGrafos) {
        this.nome = nome;
        this.numGrafos = numGrafos;
        this.grafos = new Grafo[numGrafos];
    }

    public String obtemNome() {
        return this.nome;
    }

    public int obtemNumGrafos() {
        return this.numGrafos;
    }

    public void adicionaGrafo(int index, Grafo grafo) {
        this.grafos[index] = grafo;
    }

    public Grafo obtemPrimeiroGrafo() {
        return this.grafos[0];
    }

    public Grafo obtemGrafo(int i) {
        return this.grafos[i];
    }
}
