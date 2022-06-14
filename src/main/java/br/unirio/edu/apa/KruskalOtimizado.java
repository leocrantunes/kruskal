package br.unirio.edu.apa;

import java.util.Arrays;

public class KruskalOtimizado {

    class subset {
        int parent, rank;
    };

    int find(subset subsets[], int i) {
        if (subsets[i].parent != i)
            subsets[i].parent = find(subsets, subsets[i].parent);
        return subsets[i].parent;
    }

    void union(subset subsets[], int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        if (subsets[xroot].rank < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank > subsets[yroot].rank)
            subsets[yroot].parent = xroot;
        else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }

    public long executar(Grafo grafo) {
        int numVertices = grafo.getNumVertices();

        Aresta result[] = new Aresta[numVertices];
        int e = 0;
        int i = 0;
        for (i = 0; i < numVertices; ++i)
            result[i] = new Aresta();

        // Sorting the edges
        Aresta[] arestas = grafo.obtemListaArestas();
        Arrays.sort(arestas);
        subset subsets[] = new subset[numVertices];
        for (i = 0; i < numVertices; ++i)
            subsets[i] = new subset();

        for (int v = 0; v < numVertices; ++v) {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }
        i = 0;
        while (e < numVertices - 1) {
            Aresta next_edge = new Aresta();
            next_edge = arestas[i++];
            int x = find(subsets, next_edge.getOrigem());
            int y = find(subsets, next_edge.getDestino());
            if (x != y) {
                result[e++] = next_edge;
                union(subsets, x, y);
            }
        }

        int minimumCost = 0;
        for (i = 0; i < result.length; ++i) {
            minimumCost += result[i].getCusto();
        }

        return minimumCost;
    }
}
