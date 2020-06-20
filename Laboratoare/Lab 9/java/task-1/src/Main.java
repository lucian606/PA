import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
	static class Task {
		public static final String INPUT_FILE = "in";
		public static final String OUTPUT_FILE = "out";
		public static final int NMAX = 50005;

		int n;
		int m;
		int source;

		public class Edge implements Comparable<Edge> {
			public int node;
			public int cost;

			Edge(int _node, int _cost) {
				node = _node;
				cost = _cost;
			}

			public int compareTo(Edge e1) {
				return cost-e1.cost;
			}
		}

		@SuppressWarnings("unchecked")
		ArrayList<Edge> adj[] = new ArrayList[NMAX];

		private void readInput() {
			try {
				BufferedReader bf = new BufferedReader(new FileReader(
								INPUT_FILE));
				String line = bf.readLine();
				String[] info = line.split(" ");
				n = Integer.parseInt(info[0].toString());
				m = Integer.parseInt(info[1].toString());
				source = Integer.parseInt(info[2].toString());

				for (int i = 1; i <= n; i++) {
					adj[i] = new ArrayList<>();
				}
				for (int i = 1; i <= m; i++) {
					int x, y, w;
					line = bf.readLine();
					info = line.split(" ");
					x = Integer.parseInt(info[0].toString());
					y = Integer.parseInt(info[1].toString());
					w = Integer.parseInt(info[2].toString());
					adj[x].add(new Edge(y, w));
				}
				bf.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(ArrayList<Integer> result) {
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(
								OUTPUT_FILE));
				StringBuilder sb = new StringBuilder();
				for (int i = 1; i <= n; i++) {
					sb.append(result.get(i)).append(' ');
				}
				sb.append('\n');
				bw.write(sb.toString());
				bw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private ArrayList<Integer> getResult() {
			// TODO: Gasiti distantele minime de la nodul source la celelalte noduri
			// folosind Dijkstra pe graful orientat cu n noduri, m arce stocat in adj.
			//	d[node] = costul minim / lungimea minima a unui drum de la source la
			//	nodul node;
			//	d[source] = 0;
			//	d[node] = -1, daca nu se poate ajunge de la source la node.
			// Atentie:
			// O muchie este tinuta ca o pereche (nod adiacent, cost muchie):
			//	adj[x].get(i).node = nodul adiacent lui x,
			//	adj[x].get(i).cost = costul.
			ArrayList<Integer> d = new ArrayList<>(n+1);
			int[] p = new int[n + 1];
			PriorityQueue<Edge> q = new PriorityQueue<>();
			boolean[] selected = new boolean[n + 1];
			for (int i = 0; i <= n; i++) {
				d.add(NMAX);
				p[i] = 0;
				selected[i] = false;
				if (i == source)
					d.set(i, 0);
			}
			q.add(new Edge(source, 0));
			while(!q.isEmpty()) {
				Edge muchie = q.poll();
				int u = muchie.node;
				selected[u] = true;
				for (Edge e : (ArrayList<Edge>) adj[u]) {
					int nod = e.node;
					if (selected[nod] == false && d.get(nod) > d.get(u) + e.cost) {
						d.set(nod, d.get(u) + e.cost);
						p[nod] = u;
						q.remove(e);
						q.add(new Edge(nod, d.get(nod)));
					}
				}
			}
			for(int i = 0; i < d.size(); i++) {
				if(d.get(i) == NMAX)
					d.set(i, -1);
			}
      		return d;
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
