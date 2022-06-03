package br.unirio.edu.apa;

public class Aresta {
    private int vertice;
    private int custo;
    private Aresta proxima;

    public Aresta(int vertice, int custo, Aresta proxima) {
        this.setVertice(vertice);
        this.setCusto(custo);
        this.setProxima(proxima);
    }

    public int getVertice() {
        return vertice;
    }

    public void setVertice(int vertice) {
        this.vertice = vertice;
    }

    public int getCusto() {
        return custo;
    }

    private void setCusto(int custo) {
        this.custo = custo;
    }

    public Aresta getProxima() {
        return this.proxima;
    }

    public void setProxima(Aresta proxima) {
        this.proxima = proxima;
    }
}
