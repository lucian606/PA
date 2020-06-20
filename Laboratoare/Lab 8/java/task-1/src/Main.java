import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Main {
	static class Task {
		public static final String INPUT_FILE = "in";
		public static final String OUTPUT_FILE = "out";
		public static final int NMAX = 100005; // 10^5
		public static final int ALB = 0;
		public static final int GRI = 1;
		public static final int NEGRU = 2;

		int n;
		int m;
		int[] colors;

		@SuppressWarnings("unchecked")
		ArrayList<Integer> adj[] = new ArrayList[NMAX];
		@SuppressWarnings("unchecked")
		ArrayList<Integer> adjt[] = new ArrayList[NMAX];

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
				n = sc.nextInt();
				m = sc.nextInt();
				colors = new int[n + 1];
				for (int i = 1; i <= n; i++) {
					adj[i] = new ArrayList<>();
					adjt[i] = new ArrayList<>();
				}
				for (int i = 1; i <= m; i++) {
					int x, y;
					x = sc.nextInt();
					y = sc.nextInt();
					adj[x].add(y);
					adjt[y].add(x);
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(ArrayList<ArrayList<Integer>> result) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				pw.printf("%d\n", result.size());
				for (ArrayList<Integer> ctc : result) {
					for (int nod : ctc) {
						pw.printf("%d ", nod);
					}
					pw.printf("\n");
				}
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public void DFS(int start, boolean[] visited,ArrayList<Integer> solution) {
			visited[start] = true;
			for(Integer j : adj[start]) {
				if (visited[j] == false)
					DFS(j, visited, solution);
			}
			solution.add(start);
		}

		public void DFS_T(int start, boolean[] visited, ArrayList<Integer> solution_t) {
			visited[start] =true;
			solution_t.add(start);
			for(Integer j : adjt[start]) {
				if (visited[j] == false) {
					DFS_T(j, visited, solution_t);
				}
			}
		}

		private ArrayList<ArrayList<Integer>> getResult() {
			// TODO: Gasiti componentele tare conexe ale grafului orientat cu
			// n noduri, stocat in adj. Rezultatul se va returna sub forma
			// unui ArrayList, ale carui elemente sunt componentele tare conexe
			// detectate. Nodurile si componentele tare conexe pot fi puse in orice
			// ordine in arraylist.
			//
			// Atentie: graful transpus este stocat in adjt.
			ArrayList<Integer> s = new ArrayList<Integer>();
			ArrayList<ArrayList<Integer>> sol = new ArrayList<>();
			boolean[] visited = new boolean[n + 1];

			for(int i = 1; i <= n; i++) {
				visited[i] = false;
				colors[i] = ALB;
			}

			for(int i = 1; i <= n; i++) {
				if(visited[i] == false) {
					DFS(i, visited, s);
				}
			}

			for(int i = 1; i <= n; i++)
				visited[i] = false;

			while(s.isEmpty() == false) {
				int nr = s.get(s.size() - 1);
				ArrayList<Integer> trans = new ArrayList<Integer>();
				s.remove(s.size() - 1);
				if(visited[nr] == true)
					continue;
				DFS_T(nr, visited, trans);
				sol.add(trans);
			}

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
