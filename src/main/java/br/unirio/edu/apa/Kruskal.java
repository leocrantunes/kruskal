package br.unirio.edu.apa;

import java.util.Arrays;

public class Kruskal {

    /**
     * Executa algoritmo de Kruskal utilizando estrutura de dados simples
     * 
     * @param grafo grafo
     * @return custo da árvore geradora mínima
     */
    public long executar(Grafo grafo) {
        // declara vetores para armazenar componentes e AGM
        int numVertices = grafo.getNumVertices();
        Aresta arvoreGeradoraMinima[] = new Aresta[numVertices];
        int[] componentes = new int[numVertices];
        int aresta = 0, i = 0;

        // inicializa árvore geradora mínima
        for (i = 0; i < numVertices; ++i)
            arvoreGeradoraMinima[i] = new Aresta();

        // obtém lista de arestas e ordena pelo menor custo
        Aresta[] arestas = grafo.obtemListaArestas();
        Arrays.sort(arestas);

        // inicializa array de componentes com os vértices
        for (i = 0; i < numVertices; i++)
            componentes[i] = i;

        // itera pelo conjunto de arestas e executa as operações union e find
        i = 0;
        while (aresta < numVertices - 1) {
            Aresta proximaAresta = arestas[i++];
            int conjunto1 = find(componentes, proximaAresta.getOrigem());
            int conjunto2 = find(componentes, proximaAresta.getDestino());
            if (conjunto1 != conjunto2) {
                arvoreGeradoraMinima[aresta++] = proximaAresta;
                union(componentes, conjunto1, conjunto2, numVertices);
            }
        }

        return calculaCustoMinimo(arvoreGeradoraMinima);
    }

    /**
     * Operação que retorna o nome do conjunto que contém o vértice
     * 
     * @param componentes array de componentes
     * @param vertice     vértice
     * @return nome do conjunto que contém o vértice
     */
    int find(int componentes[], int vertice) {
        return componentes[vertice];
    }

    /**
     * Operação que une os conjuntos `c1` e `c2` em um único conjunto
     * 
     * @param componentes array de componentes
     * @param c1          conjunto 1
     * @param c2          conjunto 2
     * @param numVertices número de vértices
     */
    void union(int componentes[], int c1, int c2, int numVertices) {
        for (int i = 0; i < numVertices; i++)
            if (componentes[i] == c2)
                componentes[i] = c1;
    }

    /**
     * Calcula o custo da árvore geradora mínima
     * 
     * @param arvoreGeradoraMinima árvore geradora mínima
     * @return custo da árvore geradora mínima
     */
    int calculaCustoMinimo(Aresta[] arvoreGeradoraMinima) {
        int custoMinimo = 0;

        for (int i = 0; i < arvoreGeradoraMinima.length; i++)
            custoMinimo += arvoreGeradoraMinima[i].getCusto();

        return custoMinimo;
    }
}