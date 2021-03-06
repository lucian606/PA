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

		int time = 0;
		int n;
		int m;

		@SuppressWarnings("unchecked")
		ArrayList<Integer> adj[] = new ArrayList[NMAX];
		int[] idx = new int[n + 1];
		int[] low = new int[n + 1];

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

		private void writeOutput(ArrayList<Integer> result) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				for (int node : result) {
					pw.printf("%d ", node);
				}
				pw.printf("\n");
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public void APutil(int start, boolean[] visited, int[] disc, int[] low, int[] parent, boolean[] added,ArrayList<Integer> ap) {
			int children = 0;
			visited[start] = true;
			disc[start] = low[start] = time;
			time++;
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

		private ArrayList<Integer> getResult() {
			// TODO: Gasiti nodurile critice ale grafului neorientat stocat cu liste
			// de adiacenta in adj.
			ArrayList<Integer> sol = new ArrayList<>();
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
			time = 0;
			for(int i = 1; i <= n; i++)
				if(visited[i] == false)
					APutil(i, visited, disc, low, parent, added, sol);
			return sol;
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
