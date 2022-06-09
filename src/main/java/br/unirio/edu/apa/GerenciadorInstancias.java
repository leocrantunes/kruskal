package br.unirio.edu.apa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class GerenciadorInstancias {
    private final String NOME_PASTA_GRAFOS_COMPLETOS = "grafos_completos";
    private final String NOME_PASTA_GRAFOS_ESPARSOS = "grafos_esparsos";

    private Instancia[] instanciasGrafosCompletos;
    private Instancia[] instanciasGrafosEsparsos;

    public GerenciadorInstancias() {
        this(true, true);
    }

    public GerenciadorInstancias(Boolean inicializarGrafosCompletos, Boolean inicializarGrafosEsparsos) {
        String[] arquivosGrafosCompletos = this.obtemArquivosInstanciasGrafosCompletos();
        this.instanciasGrafosCompletos = new Instancia[arquivosGrafosCompletos.length];
        if (inicializarGrafosCompletos) {
            this.inicializaInstancias(
                    this.instanciasGrafosCompletos,
                    NOME_PASTA_GRAFOS_COMPLETOS,
                    arquivosGrafosCompletos);
        }

        String[] arquivosGrafosEsparsos = this.obtemArquivosInstanciasGrafosEsparsos();
        this.instanciasGrafosEsparsos = new Instancia[arquivosGrafosEsparsos.length];
        if (inicializarGrafosEsparsos) {
            this.inicializaInstancias(
                    this.instanciasGrafosEsparsos,
                    NOME_PASTA_GRAFOS_ESPARSOS,
                    arquivosGrafosEsparsos);
        }
    }

    public Instancia obtemInstanciaGrafoCompleto(int indice) {
        return this.instanciasGrafosCompletos[indice];
    }

    public Grafo obtemGrafoCompleto(int indiceInstancia) {
        return this.instanciasGrafosCompletos[indiceInstancia].obtemPrimeiroGrafo();
    }

    public Instancia obtemInstanciaGrafoEsparso(int indice) {
        return this.instanciasGrafosEsparsos[indice];
    }

    public void inicializaInstancias(Instancia[] instancias, String nomePasta, String[] nomesArquivos) {
        for (int i = 0; i < instancias.length; i++) {
            String nomeArquivo = nomesArquivos[i];
            File arquivoInstancia = this.obtemArquivoInstancia(nomePasta + "/" + nomeArquivo);

            try (BufferedReader reader = new BufferedReader(new FileReader(arquivoInstancia))) {
                while (reader.ready()) {
                    int numGrafos = obtemNumGrafos(reader.readLine().trim());
                    Instancia instancia = new Instancia(nomeArquivo, numGrafos);

                    for (int j = 0; j < numGrafos; j++) {
                        Grafo grafo = criaGrafo(reader);
                        instancia.adicionaGrafo(j, grafo);
                    }

                    instancias[i] = instancia;

                    reader.readLine(); // linha final "END"
                    reader.readLine(); // linha autor do arquivo
                }

                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Grafo criaGrafo(BufferedReader reader) throws IOException {
        reader.readLine(); // linha tipo de grafo
        int numVertices = obtemNumVertices(reader.readLine().trim());
        int numArestas = obtemNumArestas(reader.readLine().trim());
        reader.readLine(); // linha lista de arestas e custos

        Grafo grafo = new Grafo(numVertices, numArestas);

        // itera pelas linhas que declaram as arestas e adiona ao grafo
        for (int i = 0; i < numArestas; i++) {
            adicionaAresta(reader.readLine().trim(), grafo, i);
        }

        return grafo;
    }

    private int obtemNumGrafos(String linha) {
        String[] arrayNumGrafos = linha.split("\\s{2,}");

        if (arrayNumGrafos.length < 2) {
            throw new IllegalArgumentException(linha
                    + " não contém os dois elementos necessários (NB_GRAPHS X) para incluir o número de grafos");
        }

        return Integer.parseInt(arrayNumGrafos[1]);
    }

    private int obtemNumVertices(String linha) {
        String[] arrayNumVertices = linha.split("\\s{2,}");

        if (arrayNumVertices.length < 2) {
            throw new IllegalArgumentException(linha
                    + " não contém os dois elementos necessários (NB_NODES X) para incluir o número de vértices");
        }

        return Integer.parseInt(arrayNumVertices[1]);
    }

    private int obtemNumArestas(String linha) {
        String[] arrayNumVertices = linha.split("\\s{2,}");

        if (arrayNumVertices.length < 2) {
            throw new IllegalArgumentException(linha
                    + " não contém os dois elementos necessários (NB_EDGES X) para incluir o número de arestas");
        }

        return Integer.parseInt(arrayNumVertices[1]);
    }

    private void adicionaAresta(String linha, Grafo grafo, int indice) {
        String[] arrayArestas = linha.split("\\s{2,}");

        if (arrayArestas.length < 3) {
            throw new IllegalArgumentException(linha
                    + " não contém os três elementos necessários (origem, destino, custo) para incluir aresta");
        }

        int origem = Integer.parseInt(arrayArestas[0]);
        int destino = Integer.parseInt(arrayArestas[1]);
        int custo = Integer.parseInt(arrayArestas[2]);

        grafo.adicionaAdjacencia(origem, destino, custo);
        grafo.adicionaAresta(indice, origem, destino, custo);
    }

    private File obtemArquivoInstancia(final String nomeArquivo) {
        URL url = this.getClass()
                .getClassLoader()
                .getResource(nomeArquivo);

        if (url == null) {
            throw new IllegalArgumentException(nomeArquivo + " não foi encontrado");
        }

        return new File(url.getFile());
    }

    private String[] obtemArquivosInstanciasGrafosCompletos() {
        return new String[] {
                "inst_v100.dat",
                "inst_v200.dat",
                "inst_v300.dat",
                "inst_v400.dat",
                "inst_v500.dat",
                "inst_v600.dat",
                "inst_v700.dat",
                "inst_v800.dat",
                "inst_v900.dat",
                "inst_v1000.dat",
        };
    }

    private String[] obtemArquivosInstanciasGrafosEsparsos() {
        return new String[] {
                "i100gs.txt",
                "i200gs.txt",
                "i300gs.txt",
                "i400gs.txt",
                "i500gs.txt",
                "i600gs.txt",
                "i700gs.txt",
                "i800gs.txt",
                "i900gs.txt",
                "i1000gs.txt",
        };
    }
}
