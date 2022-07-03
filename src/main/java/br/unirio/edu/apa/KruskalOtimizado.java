package br.unirio.edu.apa;

import java.util.Arrays;

public class KruskalOtimizado {

    /**
     * Estrutura auxiliar para armazenar subconjuntos de componentes
     */
    class Componente {
        int verticePai, rank;
    }

    /**
     * Executa algoritmo de Kruskal utilizando estrutura de dados otimizada
     * 
     * @param grafo grafo
     * @return custo da árvore geradora mínima
     */
    public long executar(Grafo grafo) {
        // declara vetores para armazenar componentes e AGM
        int numVertices = grafo.getNumVertices();
        Aresta arvoreGeradoraMinima[] = new Aresta[numVertices];
        Componente[] componentes = new Componente[numVertices];
        int aresta = 0, i = 0;

        // inicializa árvore geradora mínima
        for (i = 0; i < numVertices; ++i)
            arvoreGeradoraMinima[i] = new Aresta();

        // obtém lista de arestas e ordena pelo menor custo
        Aresta[] arestas = grafo.obtemListaArestas();
        Arrays.sort(arestas);

        // inicializa array de componentes com os objetos de componentes
        for (i = 0; i < numVertices; i++) {
            componentes[i] = new Componente();
            componentes[i].verticePai = i;
            componentes[i].rank = 0;
        }

        // itera pelo conjunto de arestas e executa as operações union e find
        i = 0;
        while (aresta < numVertices - 1) {
            Aresta proximaAresta = arestas[i++];
            int conjunto1 = find(componentes, proximaAresta.getOrigem());
            int conjunto2 = find(componentes, proximaAresta.getDestino());
            if (conjunto1 != conjunto2) {
                arvoreGeradoraMinima[aresta++] = proximaAresta;
                union(componentes, conjunto1, conjunto2);
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
    int find(Componente componentes[], int i) {
        if (componentes[i].verticePai != i)
            componentes[i].verticePai = find(componentes, componentes[i].verticePai);
        return componentes[i].verticePai;
    }

    /**
     * Operação que une os conjuntos `c1` e `c2` em um único conjunto
     * 
     * @param componentes array de componentes
     * @param c1          conjunto 1
     * @param c2          conjunto 2
     * @param numVertices número de vértices
     */
    void union(Componente componentes[], int c1, int c2) {
        int paiConjunto1 = find(componentes, c1);
        int paiConjunto2 = find(componentes, c2);

        if (componentes[paiConjunto1].rank < componentes[paiConjunto2].rank)
            componentes[paiConjunto1].verticePai = paiConjunto2;
        else if (componentes[paiConjunto1].rank > componentes[paiConjunto2].rank)
            componentes[paiConjunto2].verticePai = paiConjunto1;
        else {
            componentes[paiConjunto2].verticePai = paiConjunto1;
            componentes[paiConjunto1].rank++;
        }
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
