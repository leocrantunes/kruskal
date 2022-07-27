package br.unirio.edu.apa;

public class Resultado {
    int rodada;
    int numVertices;
    double numArestas;
    double resultado;
    double tempoTotalCpuNanosegundos;
    String tipoGrafo;
    String algo;

    public Resultado(int rodada, int numVertices, double numArestas, double resultado, double tempoTotalCpuNanosegundos,
            String tipoGrafo, String algo) {
        this.rodada = rodada;
        this.numVertices = numVertices;
        this.numArestas = numArestas;
        this.resultado = resultado;
        this.tempoTotalCpuNanosegundos = tempoTotalCpuNanosegundos;
        this.tipoGrafo = tipoGrafo;
        this.algo = algo;
    }
}
