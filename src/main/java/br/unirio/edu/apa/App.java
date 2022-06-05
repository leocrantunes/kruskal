package br.unirio.edu.apa;

import java.util.concurrent.TimeUnit;

public final class App {
    private App() {
    }

    public static void main(String[] args) {
        long inicio = System.nanoTime();
        GerenciadorInstancias leitorInstancias = new GerenciadorInstancias();
        // Grafo grafoCompleto = leitorInstancias.obtemGrafoCompleto(0);
        // grafoCompleto.imprimeGrafo();
        long fim = System.nanoTime();

        long tempoTotal = fim - inicio;

        // TimeUnit
        long tempoTotalSegundos = TimeUnit.MILLISECONDS.convert(tempoTotal, TimeUnit.NANOSECONDS);

        System.out.println(tempoTotalSegundos + " MILLISECONDS");
    }
}
