import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class P1 {
	static class Task {
		public static final String INPUT_FILE = "p1.in";
		public static final String OUTPUT_FILE = "p1.out";
		public static final int NMAX = 100005;

		int n;
		int m;
		int k;
		boolean[] blocked; //blocked[i] tells us if we can visit that node

		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] adj = new ArrayList[NMAX];
		ArrayList<Integer> attacked_cities = new ArrayList<>();
		ArrayList<Integer> permutation = new ArrayList<>();

		private void readInput() {
			try {
				BufferedReader bf = new BufferedReader(new FileReader(
						INPUT_FILE));
				String line = bf.readLine();
				String[] info = line.split(" ");
				n = Integer.parseInt(info[0].toString());
				m = Integer.parseInt(info[1].toString());
				k = Integer.parseInt(info[2].toString());
				blocked = new boolean[n + 1];
				for (int i = 1; i <= n; i++) {
					adj[i] = new ArrayList<>();
					blocked[i] = false;
				}
				line = bf.readLine();
				info = line.split(" ");
				for (String s : info) {
					int city = Integer.parseInt(s.toString());
					attacked_cities.add(city);
				}
				line = bf.readLine();
				info = line.split(" ");
				for (String s : info) {
					int c = Integer.parseInt(s.toString());
					permutation.add(c);
				}
				for (int i = 1; i <= m; i++) {
					line = bf.readLine();
					info = line.split(" ");
					int x, y;
					x = Integer.parseInt(info[0].toString());
					y = Integer.parseInt(info[1].toString());
					adj[x].add(y);
					adj[y].add(x);
				}
				bf.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(int result) {
			try {
				StringBuilder sb = new StringBuilder();
				sb.append(result);
				sb.append('\n');
				BufferedWriter bw = new BufferedWriter(new FileWriter(
						OUTPUT_FILE));
				sb.append('\n');
				bw.write(sb.toString());
				bw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public int[] bfs() { //Function for bfs traversal
			int[] d = new int[n + 1];
			int[] p = new int[n + 1];
			int[] c = new int[n + 1];
			for (int i = 1; i <= n; i++) {
				d[i] = -1;
				p[i] = -1;
				c[i] = 0;
			}
			d[1] = 0;
			c[1] = 1;
			Queue<Integer> q = new LinkedList<>();
			q.add(1);
			while (!q.isEmpty()) {
				int u = q.poll();
				for (Integer v : adj[u]) {
					if (c[v] == 0  && !blocked[v]) {
						d[v] = d[u] + 1;
						p[v] = u;
						c[v] = 1;
						q.add(v);
					} //We visit the neighbour only if it isn't blocked/visited
				}
				c[u] = 2;
			}
			return d;
		}

		private int getResult() {
			int left = 0;
			int right = permutation.size() - 1;
			while (left <= right) { //We find the amount of cities to block using bsearch
				int mid = (left + right) / 2;
				for (int j = left; j < mid; j++) {
					blocked[permutation.get(j)] = true;
				} //We block all the cities from left to mid
				int[] d = bfs(); //Do a bfs from 1 (Robin's city)
				int ok = 1; //ok = 1, means attackeres can't get to 1
				for (Integer v : attacked_cities) {
					if (d[v] != -1) {
						ok = 0;
						break;
					}
				} //Checking if enemies can get to node 1 from every attacked_city
				if (ok == 1) { //If enemies can't get to 1, we try to block less cities
					right = mid - 1;
					for (int j = mid; j >= (mid - 1 + left) / 2; j--) {
						blocked[permutation.get(j)] = false;
					}
				} else { //If enemies can get to 1, then we try to block more cities
					blocked[permutation.get(mid)] = true;
					left = mid + 1;
				}
			}
			return right + 1; //We return the number of cities that needs to be blocked
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
