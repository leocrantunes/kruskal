package br.unirio.edu.apa;

import java.util.Arrays;
import java.util.Comparator;

public class Grafo {
    private int numVertices;
    private int numArestas;
    private Adjacencia[] adjacencias;
    private Aresta[] arestas;

    public Grafo(int numVertices, int numArestas) {
        this.numVertices = numVertices;
        this.numArestas = numArestas;
        this.inicializaListaAdjacencias();
        this.arestas = new Aresta[this.numArestas];
    }

    public int getNumVertices() {
        return this.numVertices;
    }

    public int getNumArestas() {
        return this.numArestas;
    }

    public void adicionaAresta(int indice, int origem, int destino, int custo) {
        this.arestas[indice] = new Aresta(origem, destino, custo);
    }

    public void adicionaAdjacencia(int origem, int destino, int custo) {
        this.adicionaAdjacencia(this.adjacencias[origem], destino, custo);
        this.adicionaAdjacencia(this.adjacencias[destino], origem, custo);
    }

    public void adicionaAdjacencia(Adjacencia origem, int destino, int custo) {
        Adjacencia novaAresta = new Adjacencia(destino, custo, origem.getProxima());
        origem.setProxima(novaAresta);
    }

    public void imprimeGrafo() {
        for (int i = 0; i < this.getNumVertices(); i++) {
            System.out.println("Vertice " + i + ":");
            Adjacencia adjacencia = this.adjacencias[i].getProxima();
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

    /*
     * Como ordenar sem criar uma lista de arestas (origem, destino, custo)?
     * E continuar utilizando a lista de adjacencias...
     */

    public Aresta[] gerarListaArestas() {
        int indiceAresta = 0;
        Aresta[] listaArestas = new Aresta[this.getNumArestas()];
        for (int i = 0; i < numVertices; i++) {
            Adjacencia adj = this.adjacencias[i].getProxima();
            while (adj != null) {
                // verificar se aresta já existe no conjunto
                if (adj.getVertice() > i) {
                    // adicionar aresta no conjunto
                    int origem = this.adjacencias[i].getVertice();
                    int destino = adj.getVertice();
                    int custo = adj.getCusto();
                    listaArestas[indiceAresta++] = new Aresta(origem, destino, custo);
                }

                adj = adj.getProxima();
            }
        }

        ordenar(listaArestas);

        return listaArestas;
    }

    public void ordenar(Aresta[] arestas) {
        // Java Merge Sort O(m log(m)), m -> número de arestas
        Arrays.sort(arestas, new Comparator<Aresta>() {
            @Override
            public int compare(Aresta a1, Aresta a2) {
                return Integer.compare(a1.getCusto(), a2.getCusto());
            }
        });
    }
}
