package br.unirio.edu.apa;

public class Aresta {
    private int origem;
    private int destino;
    private int custo;

    public Aresta(int origem, int destino, int custo) {
        this.origem = origem;
        this.destino = destino;
        this.custo = custo;
    }

    public int getOrigem() {
        return this.origem;
    }

    public int getDestino() {
        return this.destino;
    }

    public int getCusto() {
        return this.custo;
    }
}
