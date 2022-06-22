package br.unirio.edu.apa;

import java.util.Arrays;

public class Kruskal {
    public long executar(Grafo grafo) {
        int numVertices = grafo.getNumVertices();
        Aresta result[] = new Aresta[numVertices];

        int[] belongs = new int[numVertices];
        int cno1, cno2;

        // Sorting the edges
        Aresta[] arestas = grafo.obtemListaArestas();
        Arrays.sort(arestas);

        for (int i = 0; i < numVertices; i++)
            belongs[i] = i;

        int t = 0;
        for (int i = 0; i < arestas.length; i++) {
            cno1 = find(belongs, arestas[i].getOrigem());
            cno2 = find(belongs, arestas[i].getDestino());

            if (cno1 != cno2) {
                result[t] = arestas[i];
                t++;
                union(belongs, cno1, cno2, numVertices);
            }
        }

        int minimumCost = 0;
        for (int i = 0; i < result.length; i++) {
            if (result[i] != null)
                minimumCost += result[i].getCusto();
        }

        return minimumCost;
    }

    int find(int belongs[], int vertexno) {
        return (belongs[vertexno]);
    }

    void union(int belongs[], int c1, int c2, int numVertices) {
        for (int i = 0; i < numVertices; i++)
            if (belongs[i] == c2)
                belongs[i] = c1;
    }
}