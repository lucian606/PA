import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    static class Task {
        public static final String INPUT_FILE = "in";
        public static final String OUTPUT_FILE = "out";
        public static final int NMAX = 200005;

        int n;
        int m;

        public class Edge implements Comparable<Edge> {
            public int node;
            public int cost;

            Edge(int _node, int _cost) {
                node = _node;
                cost = _cost;
            }

            public int compareTo(Edge e) {
                return cost - e.cost;
            }
        }

        @SuppressWarnings("unchecked")
        ArrayList<Edge> adj[] = new ArrayList[NMAX];

        private void readInput() {
            try {
                Scanner sc = new Scanner(new BufferedReader(new FileReader(
                                INPUT_FILE)));
                n = sc.nextInt();
                m = sc.nextInt();

                for (int i = 1; i <= n; i++)
                    adj[i] = new ArrayList<>();
                for (int i = 1; i <= m; i++) {
                    int x, y, w;
                    x = sc.nextInt();
                    y = sc.nextInt();
                    w = sc.nextInt();
                    adj[x].add(new Edge(y, w));
                    adj[y].add(new Edge(x, w));
                }
                sc.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void writeOutput(int result) {
            try {
                PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
                pw.printf("%d\n", result);
                pw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private int getResult() {
            /*
            TODO: Calculati costul minim al unui arbore de acoperire
            folosind algoritmul lui Prim.
            */
            int cost = 0;
            int[] d = new int[n + 1];
            int[] p = new int[n + 1];
            boolean[] viz = new boolean[n + 1];
            for(int u = 1; u <= n; u++) {
                d[u] = Integer.MAX_VALUE;
                p[u] = -1;
                viz[u] = false;
            }
            d[1] = 0;
            viz[1] = true;
            PriorityQueue<Edge> H = new PriorityQueue<>();
            for(Edge e : adj[1])
                H.add(e);
            while(!H.isEmpty()) {
                Edge edg = H.poll();
                int u = edg.node;
                for(Edge e : adj[u]) {
                    if(e.cost < d[e.node]) {
                        d[e.node] = e.cost;
                        p[e.node] = u;
                        H.add(e);
                    }
                }
            }
            return cost;
        }

        public void solve() {
            readInput();
            writeOutput(getResult());
        }
    }

    public static void main(String[] args) {
        new Task().solve();
    }
}
