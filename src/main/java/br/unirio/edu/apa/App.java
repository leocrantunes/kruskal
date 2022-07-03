package br.unirio.edu.apa;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.TimeUnit;

public final class App {
    private App() {
    }

    public static void main(String[] args) {
        GerenciadorInstancias gerenciadorInstancias = new GerenciadorInstancias();
        int numCompletos = gerenciadorInstancias.obtemNumInstanciasGrafosCompletos();

        Kruskal kruskal = new Kruskal();
        KruskalOtimizado kruskalOtimizado = new KruskalOtimizado();

        for (int i = 0; i < numCompletos; i++) {
            Grafo g = gerenciadorInstancias.obtemGrafoCompleto(i);

            long inicio = cpuTime();
            long resultado = kruskal.executar(g);
            long fim = cpuTime();
            long tempoTotalCpuMicrosegundos = calcularTempoCpuEmMicrosegundos(inicio, fim);
            System.out.println((i + 1) + ",1," + resultado + "," + tempoTotalCpuMicrosegundos);

            inicio = cpuTime();
            resultado = kruskalOtimizado.executar(g);
            fim = cpuTime();
            tempoTotalCpuMicrosegundos = calcularTempoCpuEmMicrosegundos(inicio, fim);
            System.out.println((i + 1) + ",1," + resultado + "," + tempoTotalCpuMicrosegundos);
        }

        int numEsparsos = gerenciadorInstancias.obtemNumInstanciasGrafosEsparsos();

        for (int i = 0; i < numEsparsos; i++) {
            int numGrafos = gerenciadorInstancias.obtemInstanciaGrafoEsparso(i).obtemNumGrafos();
            for (int j = 0; j < numGrafos; j++) {
                Grafo g = gerenciadorInstancias.obtemGrafoEsparso(i, j);

                long inicio = cpuTime();
                long resultado = kruskal.executar(g);
                long fim = cpuTime();
                long tempoTotalCpuMicrosegundos = calcularTempoCpuEmMicrosegundos(inicio, fim);
                System.out.println((i + 1) + "," + (j + 1) + "," + resultado + "," + tempoTotalCpuMicrosegundos);

                inicio = cpuTime();
                resultado = kruskalOtimizado.executar(g);
                fim = cpuTime();
                tempoTotalCpuMicrosegundos = calcularTempoCpuEmMicrosegundos(inicio, fim);
                System.out.println((i + 1) + "," + (j + 1) + "," + resultado + "," + tempoTotalCpuMicrosegundos);
            }
        }
    }

    private static long calcularTempoCpuEmMicrosegundos(long inicio, long fim) {
        // medindo tempo em CPU-time
        long tempoTotalCpuNanosegundos = fim - inicio;
        long tempoTotalCpuMicrosegundos = TimeUnit.MICROSECONDS.convert(
                tempoTotalCpuNanosegundos, TimeUnit.NANOSECONDS);

        return tempoTotalCpuMicrosegundos;
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
