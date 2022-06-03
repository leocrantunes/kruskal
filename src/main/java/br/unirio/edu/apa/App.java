package br.unirio.edu.apa;

public final class App {
    private App() {
    }

    public static void main(String[] args) {
        Grafo grafo = new Grafo(5);

        grafo.adicionaAresta(1, 0, 5);
        grafo.adicionaAresta(2, 0, 4);
        grafo.adicionaAresta(2, 1, 67);
        grafo.adicionaAresta(3, 0, 26);
        grafo.adicionaAresta(3, 2, 75);
        grafo.adicionaAresta(3, 2, 38);
        grafo.adicionaAresta(4, 0, 43);
        grafo.adicionaAresta(4, 1, 43);
        grafo.adicionaAresta(4, 2, 48);
        grafo.adicionaAresta(4, 3, 6);

        imprimeGrafo(grafo);
    }

    static void imprimeGrafo(Grafo grafo) {
        for (int i = 0; i < grafo.getNumVertices(); i++) {
            System.out.println("Vertice " + i + ":");
            Aresta aresta = grafo.getVertice(i).getArestaRaiz();
            while (aresta != null) {
                System.out.print(" -> " + aresta.getVertice() + "(" + aresta.getCusto() + ")");
                aresta = aresta.getProxima();
            }
            System.out.println();
        }
    }
}
