package br.unirio.edu.apa;

public class Grafo {
    private int numVertices;
    private Vertice[] vertices;

    public Grafo(int numVertices) {
        this.numVertices = numVertices;
        inicializaVertices();
    }

    public int getNumVertices() {
        return this.numVertices;
    }

    public Vertice getVertice(int i) {
        return this.vertices[i];
    }

    public void adicionaAresta(int verticeOrigem, int verticeDestino, int custo) {
        this.vertices[verticeOrigem].adicionaAresta(verticeDestino, custo);
        this.vertices[verticeDestino].adicionaAresta(verticeOrigem, custo);
    }

    private void inicializaVertices() {
        this.vertices = new Vertice[this.numVertices];
        for (int i = 0; i < this.numVertices; i++) {
            this.vertices[i] = new Vertice(i);
        }
    }
}
