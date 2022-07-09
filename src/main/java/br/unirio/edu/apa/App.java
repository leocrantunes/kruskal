package br.unirio.edu.apa;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class App {
    private App() {
    }

    public static void main(String[] args) {
        GerenciadorInstancias gerenciadorInstancias = new GerenciadorInstancias();
        int numCompletos = gerenciadorInstancias.obtemNumInstanciasGrafosCompletos();

        Kruskal kruskal = new Kruskal();
        KruskalOtimizado kruskalOtimizado = new KruskalOtimizado();

        for (int k = 1; k <= 10; k++) {
            BufferedWriter bw = null;
            try {
                File arquivo = new File("rodada" + k + ".txt");
                Files.deleteIfExists(Paths.get(arquivo.getAbsolutePath()));
                arquivo.createNewFile();

                FileWriter fw = new FileWriter(arquivo);
                bw = new BufferedWriter(fw);

                for (int i = 0; i < numCompletos; i++) {
                    Grafo g = gerenciadorInstancias.obtemGrafoCompleto(i);

                    long inicio = cpuTime();
                    long resultado = kruskal.executar(g);
                    long fim = cpuTime();
                    long tempoTotalCpuNanosegundos = calcularTempoCpuEmNanosegundos(inicio, fim);
                    bw.write((i + 1) + ";1;" + resultado + ";" + tempoTotalCpuNanosegundos + "\n");

                    inicio = cpuTime();
                    resultado = kruskalOtimizado.executar(g);
                    fim = cpuTime();
                    tempoTotalCpuNanosegundos = calcularTempoCpuEmNanosegundos(inicio, fim);
                    bw.write((i + 1) + ";1;" + resultado + ";" + tempoTotalCpuNanosegundos + "\n");
                }

                int numEsparsos = gerenciadorInstancias.obtemNumInstanciasGrafosEsparsos();

                for (int i = 0; i < numEsparsos; i++) {
                    int numGrafos = gerenciadorInstancias.obtemInstanciaGrafoEsparso(i).obtemNumGrafos();
                    for (int j = 0; j < numGrafos; j++) {
                        Grafo g = gerenciadorInstancias.obtemGrafoEsparso(i, j);

                        long inicio = cpuTime();
                        long resultado = kruskal.executar(g);
                        long fim = cpuTime();
                        long tempoTotalCpuNanosegundos = calcularTempoCpuEmNanosegundos(inicio, fim);
                        bw.write((i + 1) + ";" + (j + 1) + ";" + resultado + ";" + tempoTotalCpuNanosegundos + "\n");

                        inicio = cpuTime();
                        resultado = kruskalOtimizado.executar(g);
                        fim = cpuTime();
                        tempoTotalCpuNanosegundos = calcularTempoCpuEmNanosegundos(inicio, fim);
                        bw.write((i + 1) + ";" + (j + 1) + ";" + resultado + ";" + tempoTotalCpuNanosegundos + "\n");
                    }
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                try {
                    if (bw != null)
                        bw.close();
                } catch (Exception ex) {
                    System.out.println("Error in closing the BufferedWriter" + ex);
                }
            }
        }
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
