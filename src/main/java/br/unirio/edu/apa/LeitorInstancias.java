package br.unirio.edu.apa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.stream.Stream;

public class LeitorInstancias {
    private String[] arqGrafosCompletos;
    private String[] arqGrafosEsparsos;

    public LeitorInstancias() {
        arqGrafosCompletos = new String[] {
                "inst_v100.dat",
                // "inst_v200.dat",
                // "inst_v300.dat",
                // "inst_v400.dat",
                // "inst_v500.dat",
                // "inst_v600.dat",
                // "inst_v700.dat",
                // "inst_v800.dat",
                // "inst_v900.dat",
                // "inst_v1000.dat",
        };
        arqGrafosEsparsos = new String[] {
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

    public Grafo[] obtemGrafosCompletos() {
        int numInstancias = this.arqGrafosCompletos.length;
        Grafo[] grafosCompletos = new Grafo[numInstancias];

        for (int i = 0; i < numInstancias; i++) {
            String nomeArquivo = this.arqGrafosCompletos[i];
            File arquivoInstancia = this.obtemArquivoInstancia("grafos_completos/" + nomeArquivo);

            try (BufferedReader reader = new BufferedReader(new FileReader(arquivoInstancia))) {
                while (reader.ready()) {
                    int numGrafos = obtemNumGrafos(reader.readLine().trim());
                    for (int j = 0; j < numGrafos; j++) {
                        reader.readLine(); // linha tipo de grafo
                        int numVertices = obtemNumVertices(reader.readLine().trim());
                        Grafo grafo = new Grafo(numVertices);
                        int numArestas = obtemNumVertices(reader.readLine().trim());
                        reader.readLine(); // linha lista de arestas e custos
                        for (int k = 0; k < numArestas; k++) {
                            adicionaAresta(reader.readLine().trim(), grafo);
                        }
                        grafosCompletos[i] = grafo;
                    }
                    reader.readLine(); // linha final "END"
                    reader.readLine(); // linha autor do arquivo
                }

                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return grafosCompletos;
    }

    private int obtemNumGrafos(String linha) {
        String[] arrayNumGrafos = linha.split("\\s{2,}");
        return Integer.parseInt(arrayNumGrafos[1]);
    }

    private int obtemNumVertices(String linha) {
        String[] arrayNumVertices = linha.split("\\s{2,}");
        return Integer.parseInt(arrayNumVertices[1]);
    }

    private void adicionaAresta(String linha, Grafo grafo) {
        String[] arrayArestas = linha.split("\\s{2,}");
        int origem = Integer.parseInt(arrayArestas[0]);
        int destino = Integer.parseInt(arrayArestas[1]);
        int custo = Integer.parseInt(arrayArestas[2]);
        grafo.adicionaAresta(origem, destino, custo);
    }

    private File obtemArquivoInstancia(final String fileName) {
        URL url = this.getClass()
                .getClassLoader()
                .getResource(fileName);

        if (url == null) {
            throw new IllegalArgumentException(fileName + " not found");
        }

        File arquivoInstancia = new File(url.getFile());

        return arquivoInstancia;
    }
}
