package br.unirio.edu.apa;

public class Kruskal {

    public long executar(Grafo grafo) {
        Aresta[] arestas = grafo.obtemListaArestas();
        int numVertices = grafo.getNumVertices();
        int mincost = 0; // Cost of min MST.

        int[] parent = new int[numVertices];

        // Initialize sets of disjoint sets.
        for (int i = 0; i < numVertices; i++)
            parent[i] = i;

        // Include minimum weight edges one by one
        int edge_count = 0;
        while (edge_count < numVertices - 1) {
            int min = Integer.MAX_VALUE, a = -1, b = -1;
            for (int i = 0; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    if (find(parent, i) != find(parent, j) && arestas[i].getCusto() < min) {
                        min = arestas[i].getCusto();
                        a = i;
                        b = j;
                    }
                }
            }

            union(parent, a, b);

            edge_count++;
            mincost += min;
        }

        return mincost;
    }

    private int find(int[] parent, int i) {
        while (parent[i] != i)
            i = parent[i];
        return i;
    }

    private void union(int[] parent, int i, int j) {
        int a = find(parent, i);
        int b = find(parent, j);
        parent[a] = b;
    }
}