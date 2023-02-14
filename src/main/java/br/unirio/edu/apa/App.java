package br.unirio.edu.apa;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class App {
    // Creating a logger
    private static Logger logger = LogManager.getLogger();

    private App() {
    }

    public static void main(String[] args) {
        logger.info("Log started.");

        GerenciadorInstancias gerenciadorInstancias = new GerenciadorInstancias();
        int numCompletos = gerenciadorInstancias.obtemNumInstanciasGrafosCompletos();

        Kruskal kruskal = new Kruskal();
        KruskalOtimizado kruskalOtimizado = new KruskalOtimizado();

        BufferedWriter bw = null;
        try {
            File arquivo = new File("compilado.txt");
            Files.deleteIfExists(Paths.get(arquivo.getAbsolutePath()));
            arquivo.createNewFile();

            FileWriter fw = new FileWriter(arquivo);
            bw = new BufferedWriter(fw);

            // escrever cabeçalho
            bw.write("rodada;numVertices;numArestas;resultado;tempoTotalCpuNanosegundos;tipoGrafo;algo\n");

            for (int k = 0; k < 60; k++) {
                int numVertices = 0;
                int numArestas = 0;
                for (int i = 0; i <= numCompletos; i++) {
                    Grafo g = gerenciadorInstancias.obtemGrafoCompleto(i);
                    numVertices = g.getNumVertices();
                    numArestas = g.getNumArestas();

                    long inicio = cpuTime();
                    long resultado = kruskal.executar(g);
                    long fim = cpuTime();
                    long tempoTotalCpuNanosegundos = calcularTempoCpuEmNanosegundos(inicio, fim);
                    bw.write((k + 1) + ";" + numVertices + ";" + numArestas + ";" + resultado + ";"
                            + tempoTotalCpuNanosegundos + ";C;N\n");

                    inicio = cpuTime();
                    resultado = kruskalOtimizado.executar(g);
                    fim = cpuTime();
                    tempoTotalCpuNanosegundos = calcularTempoCpuEmNanosegundos(inicio, fim);
                    bw.write((k + 1) + ";" + numVertices + ";" + numArestas + ";" + resultado + ";"
                            + tempoTotalCpuNanosegundos + ";C;O\n");
                }

                int numEsparsos = gerenciadorInstancias.obtemNumInstanciasGrafosEsparsos();

                for (int i = 0; i < numEsparsos; i++) {
                    int numGrafos = gerenciadorInstancias.obtemInstanciaGrafoEsparso(i).obtemNumGrafos();
                    double somaArestas = 0.0;
                    double somaResultadoNaoOtimizado = 0.0;
                    double somaResultadoOtimizado = 0.0;
                    double somaTempoNaoOtimizado = 0.0;
                    double somaTempoOtimizado = 0.0;
                    numVertices = 0;

                    for (int j = 0; j < numGrafos; j++) {
                        Grafo g = gerenciadorInstancias.obtemGrafoEsparso(i, j);
                        somaArestas += g.getNumArestas();
                        numVertices = g.getNumVertices();

                        long inicio = cpuTime();
                        somaResultadoNaoOtimizado += kruskal.executar(g);
                        long fim = cpuTime();
                        somaTempoNaoOtimizado += calcularTempoCpuEmNanosegundos(inicio, fim);

                        inicio = cpuTime();
                        somaResultadoOtimizado += kruskalOtimizado.executar(g);
                        fim = cpuTime();
                        somaTempoOtimizado += calcularTempoCpuEmNanosegundos(inicio, fim);
                    }

                    double mediaArestas = somaArestas / numGrafos;

                    double mediaResultadoNaoOtimizado = somaResultadoNaoOtimizado / numGrafos;
                    double mediaResultadoOtimizado = somaResultadoOtimizado / numGrafos;

                    double mediaTempoNaoOtimizado = somaTempoNaoOtimizado / numGrafos;
                    double mediaTempoOtimizado = somaTempoOtimizado / numGrafos;

                    bw.write((k + 1) + ";" + numVertices + ";" + mediaArestas + ";" + mediaResultadoNaoOtimizado + ";"
                            + mediaTempoNaoOtimizado + ";E;N\n");
                    bw.write((k + 1) + ";" + numVertices + ";" + mediaArestas + ";" + mediaResultadoOtimizado + ";"
                            + mediaTempoOtimizado + ";E;O\n");
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            logger.error("Something happened", e);
        }finally {
            try {
                if (bw != null)
                    bw.close();
            } catch (Exception ex) {
                System.out.println("Error in closing the BufferedWriter" + ex);
            }
        }

        logger.info("Log finished.");
    }

    private static long calcularTempoCpuEmNanosegundos(long inicio, long fim) {
        // medindo tempo em CPU-time
        long tempoTotalCpuNanosegundos = fim - inicio;

        return tempoTotalCpuNanosegundos;
    }

    // referência:
    // https://www.javarticles.com/2016/02/java-thread-determining-cpu-time.html
    private static long cpuTime() {
        ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
        if (mxBean.isThreadCpuTimeSupported()) {
            try {
                return mxBean.getCurrentThreadCpuTime();
            } catch (UnsupportedOperationException e) {
                System.out.println(e.toString());
            }
        } else {
            System.out.println("Not supported");
        }
        return 0;
    }
}
