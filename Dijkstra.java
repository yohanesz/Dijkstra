import java.util.*;

public class Dijkstra {

    private Map<Integer, List<Aresta>> grafo = new HashMap<>();
    private Map<Integer, Integer> distancias = new HashMap<>();
    private Map<Integer, Integer> anterior = new HashMap<>();
    private Set<Integer> visitados = new HashSet<>();

    private class Aresta {
        int destino;
        int peso;

        Aresta(int destino, int peso) {
            this.destino = destino;
            this.peso = peso;
        }
    }

    public void adicionarVertice(int numVertice) {
        grafo.putIfAbsent(numVertice, new ArrayList<>());
    }

    public void interligarComPeso(int origem, int destino, int peso) {
        grafo.get(origem).add(new Aresta(destino, peso));
    }

    public void analisar(int origem) {
        PriorityQueue<Aresta> filaPrioridade = new PriorityQueue<>(Comparator.comparingInt(a -> a.peso));
        filaPrioridade.add(new Aresta(origem, 0));
        distancias.put(origem, 0);

        for (int vertice : grafo.keySet()) {
            if (vertice != origem) distancias.put(vertice, Integer.MAX_VALUE);
            anterior.put(vertice, null);
        }

        while (!filaPrioridade.isEmpty()) {
            Aresta atual = filaPrioridade.poll();
            if (visitados.contains(atual.destino)) continue;

            visitados.add(atual.destino);
            for (Aresta aresta : grafo.get(atual.destino)) {
                if (visitados.contains(aresta.destino)) continue;

                int novaDistancia = distancias.get(atual.destino) + aresta.peso;
                if (novaDistancia < distancias.get(aresta.destino)) {
                    distancias.put(aresta.destino, novaDistancia);
                    anterior.put(aresta.destino, atual.destino);
                    filaPrioridade.add(new Aresta(aresta.destino, novaDistancia));
                }
            }
        }
    }

    public void imprimirTabela() {
        System.out.println("Vértice\tDistância\tCaminho");
        for (int vertice : distancias.keySet()) {
            System.out.print(vertice + "\t" + distancias.get(vertice) + "\t\t");
            imprimirCaminho(vertice);
            System.out.println();
        }
    }

    private void imprimirCaminho(int destino) {
        if (anterior.get(destino) != null) {
            imprimirCaminho(anterior.get(destino));
        }
        System.out.print(destino + " ");
    }

    public static void main(String[] args) {
        Dijkstra dijkstra = new Dijkstra();
        dijkstra.adicionarVertice(1);
        dijkstra.adicionarVertice(2);
        dijkstra.adicionarVertice(3);
        dijkstra.adicionarVertice(4);
        dijkstra.adicionarVertice(5);
        dijkstra.adicionarVertice(6);


        dijkstra.interligarComPeso(1, 2, 15);
        dijkstra.interligarComPeso(1, 3, 9);
        dijkstra.interligarComPeso(2, 4, 2);
        dijkstra.interligarComPeso(3, 2, 4);
        dijkstra.interligarComPeso(3, 4, 3);
        dijkstra.interligarComPeso(3, 5, 16);
        dijkstra.interligarComPeso(4, 5, 6);
        dijkstra.interligarComPeso(4, 6, 21);
        dijkstra.interligarComPeso(5, 6, 7);


        dijkstra.analisar(1);
        dijkstra.imprimirTabela();
    }
}
