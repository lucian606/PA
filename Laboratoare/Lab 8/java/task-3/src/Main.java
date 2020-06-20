import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
	static class Task {
		public static final String INPUT_FILE = "in";
		public static final String OUTPUT_FILE = "out";
		public static final int NMAX = 100005; // 10^5

		int n;
		int m;
		int timp = 0;

		@SuppressWarnings("unchecked")
		ArrayList<Integer> adj[] = new ArrayList[NMAX];

		class Edge {
			int x;
			int y;

			public Edge(int x, int y) {
				this.x = x;
				this.y = y;
			}
		}

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
				n = sc.nextInt();
				m = sc.nextInt();

				for (int i = 1; i <= n; i++)
					adj[i] = new ArrayList<>();
				for (int i = 1; i <= m; i++) {
					int x, y;
					x = sc.nextInt();
					y = sc.nextInt();
					adj[x].add(y);
					adj[y].add(x);
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(ArrayList<Edge> result) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				pw.printf("%d\n", result.size());
				for (Edge e : result) {
					pw.printf("%d %d\n", e.x, e.y);
				}
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void dfsB(int v, boolean[] visited, int[] idx, int[] low, int[] parent, ArrayList<Edge> sol) {
			idx[v] = timp;
			low[v] = timp;
			visited[v] = true;
			timp++;
			for (Integer u : adj[v]) {
				if (parent[v] != u) {
					if (visited[u] == false) {
						dfsB(u, visited, idx, low, parent, sol);
						low[v] = Math.min(low[v], low[u]);
						if(low[u] > idx[v])
							sol.add(new Edge(v, u));
					}
					else
						low[v] = Math.min(low[v], idx[u]);
				}
			}
		}

		public void APutil(int start, boolean[] visited, int[] disc, int[] low, int[] parent, boolean[] added,ArrayList<Integer> ap) {
			int children = 0;
			visited[start] = true;
			disc[start] = low[start] = timp;
			timp++;
			for(Integer vecin : adj[start]) {
				if(!visited[vecin]) {
					children++;
					parent[vecin] = start;
					APutil(vecin, visited, disc, low, parent, added, ap);
					low[start] = Math.min(low[start], low[vecin]);
					if(parent[start] == -1 && children > 1 && added[start] == false) {
						ap.add(start);
						added[start] = true;
					}
					if(parent[start] != -1 && low[vecin] >= disc[start] && added[start] == false) {
						ap.add(start);
						added[start] = true;
					}
				}
				else if(vecin != parent[start])
					low[start] = Math.min(low[start], disc[vecin]);
			}
		}

		private ArrayList<Edge> getResult() {
			// TODO: Gasiti muchiile critice ale grafului neorientat stocat cu liste
			// de adiacenta in adj.
			ArrayList<Integer> sol = new ArrayList<>();
			ArrayList<Edge> cutEdges = new ArrayList<>();
			boolean[] visited = new boolean[n + 1];
			int[] low = new int[n + 1];
			int[] parent = new int[n + 1];
			boolean[] added = new boolean[n + 1];
			int[] disc = new int[n +1];
			for (int i = 1; i <= n; i++) {
				visited[i] = false;
				parent[i] = -1;
				low[i] = disc[i] = 0;
				added[i] = false;
			}
			timp = 0;
			for(int i = 1; i <= n; i++)
				if(visited[i] == false)
					APutil(i, visited, disc, low, parent, added, sol);
			for (int i = 1; i <= n; i++) {
				visited[i] = false;
				low[i] = disc[i] = 0;
				added[i] = false;
			}

			timp = 0;
			for(int i = 1; i <= n; i++)
				if(visited[i] == false)
					dfsB(i, visited, disc, low, parent, cutEdges);

			return cutEdges;
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
