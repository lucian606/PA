import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
	static class Task {
		public static final String INPUT_FILE = "in";
		public static final String OUTPUT_FILE = "out";
		public static final int NMAX = 1005;

		int n;
		int m;

		@SuppressWarnings("unchecked")
		ArrayList<Integer> adj[] = new ArrayList[NMAX];
		int C[][];

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
				n = sc.nextInt();
				m = sc.nextInt();

				C = new int[n + 1][n + 1];
				for (int i = 1; i <= n; i++) {
					adj[i] = new ArrayList<>();
				}
				for (int i = 1; i <= m; i++) {
					int x, y, z;
					x = sc.nextInt();
					y = sc.nextInt();
					z = sc.nextInt();
					adj[x].add(y);
					adj[y].add(x);
					C[x][y] += z;
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(int result) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				pw.printf("%d\n", result);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private boolean bfs(int s, int t, int[] parent) {
			boolean[] viz = new boolean[n + 1];
			for(int i = 1; i <= n; i++)
				viz[i] = false;
			Queue<Integer> q = new LinkedList<>();
			q.add(s);
			viz[s] = true;
			parent[s] = -1;
			while (!q.isEmpty()) {
				int u = q.poll();
				for (int v = 1; v <=n; v++) {
					if(!viz[v] && C[u][v] > 0) {
						q.add(v);
						parent[v] = u;
						viz[v] = true;
					}
				}
			}
			return viz[t];
		}

		private int getResult() {
			// TODO: Calculati fluxul maxim pe graful orientat dat.
			// Sursa este nodul 1.
			// Destinatia este nodul n.
			//
			// In adj este stocat graful neorientat obtinut dupa ce se elimina orientarea
			// arcelor, iar in C sunt stocate capacitatile arcelor.
			// De exemplu, un arc (x, y) de capacitate z va fi tinut astfel:
			// C[x][y] = z, adj[x] contine y, adj[y] contine x.
			int flow = 0;
			int[] parent = new int[n + 1];
			while (bfs(1, n, parent)) {
				int path_flow = Integer.MAX_VALUE;
				int u;
				for(int v = n; v!=1; v =parent[v]) {
					u = parent[v];
					path_flow = Math.min(path_flow, C[u][v]);
				}
				for(int v = n; v != 1; v = parent[v]) {
					u = parent[v];
					C[u][v] -= path_flow;
					C[v][u] += path_flow;
				}
				flow+=path_flow;
			}
			return flow;
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
