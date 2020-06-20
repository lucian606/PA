import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Bomboane {

	static class Elev {
		int x;
		int y;

		public Elev(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	static class Task {
		public final String Input_file = "bomboane.in";
		public final String Output_file = "bomboane.out";

		private final int Mod = 1000000007;
		int n, m;
		Elev[] e;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(Input_file));
				n = sc.nextInt();
				m = sc.nextInt();
				e = new Elev[n];
				for (int i = 0; i < n; i++) {
					int x = sc.nextInt();
					int y = sc.nextInt();
					e[i] = new Elev(x, y);
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
			long[][] dp = new long[n][m + 1];
			for (int i = 0; i <= m; i++) {
				if (i >= e[0].x && i <= e[0].y) {
					dp[0][i] = 1; //Pot scapa de cele j bomboane daca i le dau primului copil
				} else {
					dp[0][i] = 0; //Nu pot scapa de cele j bomboane daca i le dau primului copil
				}
			}
			for (int i = 1; i < n; i++) {
				for (int j = 0; j <= m; j++) {
					dp[i][j] = 0;
					int min = e[i].x; //Capatul mic al intervalului
					int max = e[i].y; //Capatul mare al intervalului
					for (int k = j - max; k <= j - min; k++) { //Ma uit pe linia anterioara
						if (k >= 0) {
							dp[i][j] = dp[i][j] + dp[i - 1][k] % Mod;
						} //Adun aranjarile bomboanelor ramase daca ii dau lui i k bomboane 
					} 
				}
			}
			return (int) (dp[n - 1][m] % Mod);
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