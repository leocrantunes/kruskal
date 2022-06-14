package br.unirio.edu.apa;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.TimeUnit;

public final class App {
    private App() {
    }

    public static void main(String[] args) {
        long inicio = System.nanoTime();
        long inicioCpu = cpuTime();
        GerenciadorInstancias gerenciadorInstancias = new GerenciadorInstancias();
        long fim = System.nanoTime();
        long fimCpu = cpuTime();

        // medindo tempo em clock-time
        long tempoTotal = fim - inicio;
        long tempoTotalSegundos = TimeUnit.MILLISECONDS.convert(tempoTotal, TimeUnit.NANOSECONDS);
        System.out.println(tempoTotalSegundos + " MILLISECONDS");

        // medindo tempo em CPU-time
        long tempoTotalCpu = fimCpu - inicioCpu;
        long tempoTotalCpuSegundos = TimeUnit.MILLISECONDS.convert(tempoTotalCpu, TimeUnit.NANOSECONDS);
        System.out.println(tempoTotalCpuSegundos + " MILLISECONDS");
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
