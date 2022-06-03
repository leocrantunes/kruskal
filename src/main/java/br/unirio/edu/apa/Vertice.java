package br.unirio.edu.apa;

public class Vertice {
    private int valor;
    private Aresta arestaRaiz;

    public Vertice(int i) {
        this.setValor(i);
        this.setArestaRaiz(null);
    }

    public Aresta getArestaRaiz() {
        return arestaRaiz;
    }

    private void setArestaRaiz(Aresta aresta) {
        this.arestaRaiz = aresta;
    }

    public int getValor() {
        return valor;
    }

    private void setValor(int valor) {
        this.valor = valor;
    }

    public void adicionaAresta(int verticeDestino, int custo) {
        Aresta arestaRaiz = this.getArestaRaiz();
        Aresta novaAresta = new Aresta(verticeDestino, custo, arestaRaiz);

        this.setArestaRaiz(novaAresta);
    }
}
