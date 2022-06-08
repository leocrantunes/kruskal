package br.unirio.edu.apa;

public class Adjacencia {
    private int vertice;
    private int custo;
    private Adjacencia proxima;

    public Adjacencia(int vertice, int custo, Adjacencia proxima) {
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

    public Adjacencia getProxima() {
        return this.proxima;
    }

    public void setProxima(Adjacencia proxima) {
        this.proxima = proxima;
    }
}
