package br.unirio.edu.apa;

public class Grafo {
    private int numVertices;
    private int numArestas;
    private Adjacencia[] adjacencias;

    public Grafo(int numVertices, int numArestas) {
        this.numVertices = numVertices;
        this.numArestas = numArestas;
        this.inicializaListaAdjacencias();
    }

    public int getNumVertices() {
        return this.numVertices;
    }

    public int getNumArestas() {
        return this.numArestas;
    }

    public void adicionaAresta(int origem, int destino, int custo) {
        adicionaAresta(this.adjacencias[origem], destino, custo);
        adicionaAresta(this.adjacencias[destino], origem, custo);
    }

    public void adicionaAresta(Adjacencia origem, int destino, int custo) {
        Adjacencia novaAresta = new Adjacencia(destino, custo, origem.getProxima());
        origem.setProxima(novaAresta);
    }

    public void imprimeGrafo() {
        for (int i = 0; i < this.getNumVertices(); i++) {
            System.out.println("Vertice " + i + ":");
            Adjacencia adjacencia = this.adjacencias[i];
            while (adjacencia != null) {
                System.out.print(" -> " + adjacencia.getVertice() + "(" + adjacencia.getCusto() + ")");
                adjacencia = adjacencia.getProxima();
            }
            System.out.println();
        }
    }

    private void inicializaListaAdjacencias() {
        this.adjacencias = new Adjacencia[this.numVertices];
        for (int i = 0; i < this.numVertices; i++) {
            this.adjacencias[i] = new Adjacencia(i, 0, null);
        }
    }
}
