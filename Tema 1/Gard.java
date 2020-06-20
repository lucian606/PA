import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;


public class Gard {
	static class Scandura implements Comparable<Scandura> {
		int x_start;
		int x_final;

		public Scandura(int x_start, int x_final) {
			this.x_start = x_start;
			this.x_final = x_final;
		}

		public int compareTo(Scandura s) {
			if (x_start == s.x_start) {
				return s.x_final - x_final;
			}
			return x_start - s.x_start;
		} //Comparator pentru sortarea scandurilor dupa start crescator, si finish descrescator 
	}

	static class Task {
		public final String Input_file = "gard.in";
		public final String Output_file = "gard.out";

		int n;
		Scandura[] v;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(Input_file));
				n = sc.nextInt();
				v = new Scandura[n];
				for (int i = 0; i < n; i++) {
					int x_start = sc.nextInt();
					int x_final = sc.nextInt();
					v[i] = new Scandura(x_start, x_final);
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(int result) {
			try {
				PrintWriter pw = new PrintWriter(new File(Output_file));
				pw.printf("%d\n", result);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private int getResult() {
			int redundant = 0; //Numarul de garduri redundante
			Arrays.sort(v);
			int index = 0; //Retin indexul ultimului gard plasat/neredundant
			for (int i = 1; i < n; i++) {
				if (v[i].x_final <= v[index].x_final) {
					redundant++; //Daca e redundant cresc contorul
				} else {
					index = i; //Daca nu e redundant atunci fac comparatiile fata de el
				}
			}
			return redundant;
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