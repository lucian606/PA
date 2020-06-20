import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class P2 {
	static class Task {
		public static final String INPUT_FILE = "p2.in";
		public static final String OUTPUT_FILE = "p2.out";
		public static final int NMAX = 100005;

		int n;
		int m;
		int source;
		int dest;

		public class Edge implements Comparable<Edge> {
			public int node;
			public int cost;

			Edge(int _node, int _cost) {
				node = _node;
				cost = _cost;
			}

			public int compareTo(Edge e1) {
				return cost - e1.cost;
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
				line = bf.readLine();
				info = line.split(" ");
				source = Integer.parseInt(info[0].toString());
				dest = Integer.parseInt(info[1].toString());

				for (int i = 1; i <= n; i++) {
					adj[i] = new ArrayList<>();
				}
				for (int i = 1; i <= m; i++) {
					line = bf.readLine();
					info = line.split(" ");
					int x, y, w;
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

		private void writeOutput(int result) {
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(
						OUTPUT_FILE));
				StringBuilder sb = new StringBuilder();
				sb.append(result);
				sb.append('\n');
				bw.write(sb.toString());
				bw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private int getResult() {
			int[] d = new int[n + 1]; //Distance from source to other nodes
			int[] p = new int[n + 1]; //Parents of each node
			for (int u = 1; u <= n; u++) {
				d[u] = Integer.MAX_VALUE;
				p[u] = -1;
			}
			d[source] = 0; //Distance source-source is 0
			PriorityQueue<Edge> H = new PriorityQueue<>();
			for (Edge e : adj[source]) {
				H.add(e);
				d[e.node] = e.cost;
			} //Adding all neighbours to PQueue
			while (!H.isEmpty()) {
				Edge edg = H.poll(); //Popping the PQueue
				int u = edg.node;
				for (Edge e : adj[u]) { //We look at each neighbour
					if (e.cost + d[u] < d[e.node]) {
						d[e.node] = e.cost + d[u];
						p[e.node] = u;
						H.add(e);
					} //We update the distance from source with minimum distance
				}
			}
			return d[dest]; //Returning the distance from source to dest
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
