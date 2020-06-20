import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	static class Task {
		public final static String INPUT_FILE = "in";
		public final static String OUTPUT_FILE = "out";

		int n, k;
		char[] caractere;
		int[] freq;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				n = sc.nextInt();
				k = sc.nextInt();
				String s = sc.next().trim();
				s = " " + s;
				caractere = s.toCharArray();
				freq = new int[n + 1];
				for (int i = 1; i <= n; i++) {
					freq[i] = sc.nextInt();
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void generate(ArrayList<ArrayList<Character>> all,ArrayList<Character> solution, int aparitii) {
			if(aparitii > k)
				return;

			boolean are_chars_left = false;
			for (int i = 1; i <=n; i++) {
				if(freq[i] != 0)
					are_chars_left=true;
			}
			if(!are_chars_left) {
				all.add((ArrayList<Character>) solution.clone());
				return;
			}
			for(int i=1;i<=n;i++) {
				if(freq[i] != 0) {
					int aparitie = 1;
					if(solution.size() > 0 && solution.get(solution.size()-1).equals(caractere[i]))
						aparitie=aparitii+1;
					freq[i]--;
					solution.add(caractere[i]);
					generate(all,solution,aparitie);
					freq[i]++;
					solution.remove(solution.size()-1);
				}
				else
					continue;
			}
		}

		private void writeOutput(ArrayList<ArrayList<Character>> result) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%d\n", result.size());
				for (ArrayList<Character> arr : result) {
					for (int i = 0; i < arr.size(); i++) {
						pw.printf("%c", arr.get(i));
					}
					pw.printf("\n");
				}
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private ArrayList<ArrayList<Character>> getResult() {
			ArrayList<ArrayList<Character>> all = new ArrayList<>();
			ArrayList<Character> solution = new ArrayList<>();
			// TODO: Construiti toate sirurile cu caracterele in stringul
			// caractere (indexat de la 1 la n), si frecventele in vectorul freq
			// (indexat de la 1 la n), stiind ca nu pot fi mai mult de K
			// aparitii consecutive ale aceluiasi caracter.

			// Pentru a adauga un nou sir:
			//   ArrayList<Character> sir;
			//   all.add(sir);
			generate(all,solution,0);
			return all;
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
