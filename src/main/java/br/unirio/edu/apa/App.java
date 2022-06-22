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
            long tempoTotalCpuSegundos = calcularTempoCpuEmMilisegundos(inicio, fim);
            System.out.println((i + 1) + ",1," + resultado + "," + tempoTotalCpuSegundos);

            inicio = cpuTime();
            resultado = kruskalOtimizado.executar(g);
            fim = cpuTime();
            tempoTotalCpuSegundos = calcularTempoCpuEmMilisegundos(inicio, fim);
            System.out.println((i + 1) + ",1," + resultado + "," + tempoTotalCpuSegundos);
        }

        int numEsparsos = gerenciadorInstancias.obtemNumInstanciasGrafosEsparsos();

        for (int i = 0; i < numEsparsos; i++) {
            int numGrafos = gerenciadorInstancias.obtemInstanciaGrafoEsparso(i).obtemNumGrafos();
            for (int j = 0; j < numGrafos; j++) {
                Grafo g = gerenciadorInstancias.obtemGrafoEsparso(i, j);

                long inicio = cpuTime();
                long resultado = kruskal.executar(g);
                long fim = cpuTime();
                long tempoTotalCpuSegundos = calcularTempoCpuEmMilisegundos(inicio, fim);
                System.out.println((i + 1) + "," + (j + 1) + "," + resultado + "," + tempoTotalCpuSegundos);

                inicio = cpuTime();
                resultado = kruskalOtimizado.executar(g);
                fim = cpuTime();
                tempoTotalCpuSegundos = calcularTempoCpuEmMilisegundos(inicio, fim);
                System.out.println((i + 1) + "," + (j + 1) + "," + resultado + "," + tempoTotalCpuSegundos);
            }
        }
    }

    private static long calcularTempoCpuEmMilisegundos(long inicio, long fim) {
        // medindo tempo em CPU-time
        long tempoTotalCpu = fim - inicio;
        long tempoTotalCpuSegundos = TimeUnit.MICROSECONDS.convert(tempoTotalCpu, TimeUnit.NANOSECONDS);

        return tempoTotalCpuSegundos;
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
