import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Vector;

public class Sala {

	static class Gantera implements Comparable<Gantera> {
		int greutate;
		int repetari;

		public Gantera(int greutate, int repetari) {
			this.greutate = greutate;
			this.repetari = repetari;
		}

		public int compareGreutati(Gantera g1) {
			if (greutate != g1.greutate) {
				return g1.greutate - greutate;
			}
			return g1.repetari - repetari;
		} //Compara ganterele dupa greutati

		public int compareTo(Gantera g1) {
			if (g1.repetari != repetari) {
				return repetari - g1.repetari;
			}
			return greutate - g1.greutate;
		} //Compara ganterele dupa repetari
	}

	static class Task {
		public final String Input_file = "sala.in";
		public final String Output_file = "sala.out";

		int n, m;
		Gantera[] g;

		private void readInput() {
			try {
				BufferedReader bReader = new BufferedReader(new FileReader(Input_file));
				String line = bReader.readLine();
				String[] info = line.split(" ");
				n = Integer.parseInt(info[0].toString());
				m = Integer.parseInt(info[1].toString());
				g = new Gantera[n];
				for (int i = 0; i < n; i++) {
					line = bReader.readLine();
					info = line.split(" ");
					int greutate = Integer.parseInt(info[0].toString());
					int repetari = Integer.parseInt(info[1].toString());
					g[i] = new Gantera(greutate, repetari);
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(long result) {
			try {
				PrintWriter pw = new PrintWriter(new File(Output_file));
				pw.printf("%d\n", result);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private long getResult() {
			PriorityQueue<Gantera> gantere_alese = new PriorityQueue<>(); //Initializez coada

			Arrays.sort(g, Gantera::compareGreutati);	//Sortez vectorul descrescator dupa greutati
			long max = g[0].repetari * g[0].greutate; //Initializez maximul de repetari
			long repetari = g[0].repetari; //Numarul total de repetari al ganterelor din coada
			gantere_alese.add(g[0]); //Aleg prima gantera

			for (int i = 1; i < m; i++) { //Bag primele m gantere pe coada
				gantere_alese.add(g[i]); //Aleg gantera
				repetari += g[i].repetari; //Cresc repetarile totale
				long randament = repetari * g[i].greutate;
				if (randament > max) { //Verific daca imi creste maximul
					max = randament;
				}
			}
			for (int i = m; i < n; i++) {
				Gantera scoasa = gantere_alese.poll(); //Scot gantera cu repetari minime
				gantere_alese.add(g[i]); //O inlocuiesc cu cea curenta
				repetari = g[i].repetari + repetari - scoasa.repetari; //Scad mini adun curent
				long randament = repetari * g[i].greutate;
				if (randament > max) { //Verific daca prin inlocuirea ei imi creste maximul
					max = randament;
				}
			}
			return max;
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