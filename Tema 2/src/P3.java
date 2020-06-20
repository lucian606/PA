import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class P3 {
	static class Task {
		public static final String INPUT_FILE = "p3.in";
		public static final String OUTPUT_FILE = "p3.out";
		public static final int NMAX = 100005;

		int n;
		int m;
		int e;
		double energy_left;

		public class Edge implements Comparable<Edge> {
			public int node;
			public double cost;

			Edge(int _node, double _cost) {
				node = _node;
				cost = _cost;
			}

			public int compareTo(Edge e1) {
				if (cost > e1.cost) {
					return 1;
				} else {
					return -1;
				}
			}
		}

		@SuppressWarnings("unchecked")
		ArrayList<Edge>[] adj = new ArrayList[NMAX];

		private void readInput() {
			try {
				BufferedReader bf = new BufferedReader(new FileReader(
						INPUT_FILE));
				String line = bf.readLine();
				String[] info = line.split(" ");
				n = Integer.parseInt(info[0].toString());
				m = Integer.parseInt(info[1].toString());
				e = Integer.parseInt(info[2].toString());
				for (int i = 1; i <= n; i++) {
					adj[i] = new ArrayList<>();
				}
				for (int i = 1; i <= m; i++) {
					line = bf.readLine();
					info = line.split(" ");
					int x, y, p;
					x = Integer.parseInt(info[0].toString());
					y = Integer.parseInt(info[1].toString());
					p = Integer.parseInt(info[2].toString());
					adj[x].add(new Edge(y, (double) p));
				}
				bf.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(ArrayList<Integer> result) {
			try {
				StringBuilder sb = new StringBuilder();
				sb.append(energy_left);
				sb.append('\n');
				for (int i = 0; i < result.size(); i++) {
					sb.append(result.get(i)).append(' ');
				}
				BufferedWriter bw = new BufferedWriter(new FileWriter(
						OUTPUT_FILE));
				sb.append('\n');
				bw.write(sb.toString());
				bw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private ArrayList<Integer> getResult() {
			double[] energies = new double[n + 1]; //Energy left after getting to each node
			int[] p  = new int[n + 1]; //The parents of each node
			PriorityQueue<Edge> q = new PriorityQueue<>(); //PQueue to store edges
			boolean[] selected = new boolean[n + 1]; //Visited nodes
			for (int i = 0; i <= n; i++) {
				energies[i] = 0d;
				p[i] = 0;
				selected[i] = false;
			}
			energies[1] = e;
			q.add(new Edge(1, 0d));
			while (!q.isEmpty()) {
				Edge adj_e = q.poll(); //Getting the edge with the lowest energy cost
				int u = adj_e.node;
				selected[u] = true;
				for (Edge edge : adj[u]) { //We look at each neighbour
					int v = edge.node;
					if (!selected[v]
							&& energies[v] < energies[u] * (1 - (double) edge.cost / 100)) {
						//Updating the energy if necessary
						energies[v] = energies[u] * (1 - (double) edge.cost / 100);
						p[v] = u;
						q.remove(edge);
						q.add(new Edge(v, (1 - (energies[v] / (double) e)) * 100));
					}
				}
			}

			//Energy after getting to the destination
			energy_left = energies[n];

			//Building the path from source to destination through parents
			ArrayList<Integer> path = new ArrayList<>();
			int nod = p[n];
			path.add(n);
			while (nod != 0) {
				path.add(0, nod);
				nod = p[nod];
			}

			return path;
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
