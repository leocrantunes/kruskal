package br.unirio.edu.apa;

public class Grafo {
    private int numVertices;
    private Vertice[] vertices;

    public Grafo(int numVertices) {
        this.numVertices = numVertices;
        this.inicializaVertices();
    }

    public int obtemNumVertices() {
        return this.numVertices;
    }

    public Vertice obtemVertice(int i) {
        return this.vertices[i];
    }

    public void adicionaAresta(int verticeOrigem, int verticeDestino, int custo) {
        this.vertices[verticeOrigem].adicionaAresta(verticeDestino, custo);
        this.vertices[verticeDestino].adicionaAresta(verticeOrigem, custo);
    }

    public void imprimeGrafo() {
        for (int i = 0; i < this.obtemNumVertices(); i++) {
            System.out.println("Vertice " + i + ":");
            Aresta aresta = this.obtemVertice(i).getArestaRaiz();
            while (aresta != null) {
                System.out.print(" -> " + aresta.getVertice() + "(" + aresta.getCusto() + ")");
                aresta = aresta.getProxima();
            }
            System.out.println();
        }
    }

    private void inicializaVertices() {
        this.vertices = new Vertice[this.numVertices];
        for (int i = 0; i < this.numVertices; i++) {
            this.vertices[i] = new Vertice(i);
        }
    }
}
