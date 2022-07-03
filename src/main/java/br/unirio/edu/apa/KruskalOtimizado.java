package br.unirio.edu.apa;

import java.util.Arrays;

public class KruskalOtimizado {

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
        int aresta = 0;

        // obtém lista de arestas e ordena pelo menor custo
        Aresta[] arestas = grafo.obtemListaArestas();
        Arrays.sort(arestas);

        // inicializa array de componentes com os objetos de componentes
        for (int i = 0; i < numVertices; i++) {
            componentes[i] = new Componente(i);
        }

        // itera pelo conjunto de arestas e executa as operações union e find
        for (int i = 0; i < arestas.length; i++) {
            int conjunto1 = find(componentes, arestas[i].getOrigem());
            int conjunto2 = find(componentes, arestas[i].getDestino());

            // une os conjuntos somente se eles forem diferentes
            if (conjunto1 != conjunto2) {
                arvoreGeradoraMinima[aresta++] = arestas[i];
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
    int find(Componente subsets[], int i) {
        if (subsets[i].getVerticePai() != i) {
            int verticePai = find(subsets, subsets[i].getVerticePai());
            subsets[i].setVerticePai(verticePai);
        }
        return subsets[i].getVerticePai();
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

        if (componentes[paiConjunto1].getRank() < componentes[paiConjunto2].getRank())
            componentes[paiConjunto1].setVerticePai(paiConjunto2);
        else if (componentes[paiConjunto1].getRank() > componentes[paiConjunto2].getRank())
            componentes[paiConjunto2].setVerticePai(paiConjunto1);
        else {
            componentes[paiConjunto2].setVerticePai(paiConjunto1);
            componentes[paiConjunto1].setRank(componentes[paiConjunto1].getRank() + 1);
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

        for (int i = 0; i < arvoreGeradoraMinima.length; i++) {
            if (arvoreGeradoraMinima[i] != null)
                custoMinimo += arvoreGeradoraMinima[i].getCusto();
        }

        return custoMinimo;
    }
}
