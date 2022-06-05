package br.unirio.edu.apa;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.TimeUnit;

public final class App {
    private App() {
    }

    public static void main(String[] args) {
        long inicio = System.nanoTime();
        GerenciadorInstancias gerenciadorInstancias = new GerenciadorInstancias();
        long fim = System.nanoTime();

        // medindo tempo em clock-time
        long tempoTotal = fim - inicio;
        long tempoTotalSegundos = TimeUnit.MILLISECONDS.convert(tempoTotal, TimeUnit.NANOSECONDS);
        System.out.println(tempoTotalSegundos + " MILLISECONDS");

        // medindo tempo em CPU-time
        // precisamos dividir em threads para remover o tempo de leitura de instâncias
        tempoTotal = cpuTime(Thread.currentThread());
        tempoTotalSegundos = TimeUnit.MILLISECONDS.convert(tempoTotal, TimeUnit.NANOSECONDS);
        System.out.println(tempoTotalSegundos + " MILLISECONDS");
    }

    // referência:
    // https://www.javarticles.com/2016/02/java-thread-determining-cpu-time.html
    private static long cpuTime(Thread thr) {
        ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
        if (mxBean.isThreadCpuTimeSupported()) {
            try {
                return mxBean.getThreadCpuTime(thr.getId());
            } catch (UnsupportedOperationException e) {
                System.out.println(e.toString());
            }
        } else {
            System.out.println("Not supported");
        }
        return 0;
    }
}
