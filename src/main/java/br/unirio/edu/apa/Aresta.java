package br.unirio.edu.apa;

public class Aresta implements Comparable<Aresta> {
    private int origem;
    private int destino;
    private int custo;

    /**
     * Inicializa aresta padrão com custo `0`.
     */
    public Aresta() {
        this(0, 0, 0);
    }

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

    /**
     * Utilizado quando o método sort é utilizado para comparar dois elementos
     * Arestas com custo menor tem preferência.
     */
    public int compareTo(Aresta compareEdge) {
        return this.custo - compareEdge.custo;
    }

}
