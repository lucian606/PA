import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

public class Main {
	static class Task {
		public static final String INPUT_FILE = "in";
		public static final String OUTPUT_FILE = "out";
		public static final int NMAX = 100005; // 10^5

		int n;
		int m;

		@SuppressWarnings("unchecked")
		ArrayList<Integer> adj[] = new ArrayList[NMAX];

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
				for (int i = 0; i < result.size(); i++) {
					pw.printf("%d ", result.get(i));
				}
				pw.printf("\n");
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private ArrayList<Integer> getResult() {
			// TODO: Faceti sortarea topologica a grafului stocat cu liste de
			// adiacenta in adj.
			// *******
			// ATENTIE: nodurile sunt indexate de la 1 la n.
			// *******
			ArrayList<Integer> topsort = new ArrayList<>();
			int[] in_deg = new int[n + 1];
			Vector<Integer> s = new Vector<>();
			for (int i = 1; i <= n; i++)
				in_deg[i] = 0;
			for (int i = 1; i <= n ;i++)
				for (Integer v : adj[i])
					in_deg[v]++;
			for (int i = 1; i <= n; i++)
				if(in_deg[i] == 0)
					s.add(i);
			while(s.size() != 0) {
				int random = (int) (Math.random() * s.size());
				int u = s.get(random);
				s.remove((Integer) u);
				topsort.add(u);

				for(Integer v : adj[u]) {
					in_deg[v]--;
					if(in_deg[v] == 0)
						s.add(v);
				}
				adj[u].clear();
			}
			return topsort;
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
