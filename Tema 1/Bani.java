import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Bani {
	static class Task {
		public final String Input_file = "bani.in";
		public final String Output_file = "bani.out";

		private final int Mod = 1000000007;
		int n, tip;
		long[] dp;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(Input_file));
				tip = sc.nextInt();
				n = sc.nextInt();
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
			if (tip == 1) { //Matematic 5*2*2*2..., dupa orice bancnota am doua optiuni
				if (n == 1) {
					return 5; //Pot pune orice bancnota pe prima pozitie
				} else { 
					long result = 5; //Pe prima pozitie pot pune orice
					int cat = n / 29;  //Vad de cate ori trebuie sa impart la Mod
					for (int i = 1; i <= cat; i++) { //Dupa orice bancnota pot pune doua tipuri
						result = (result % Mod) << 29; //Shiftez ca e mai rapid decat inmultirile
					} //Dupa 29 de inmultiri cu 2 impart la mod, pentru ca mod e egal cu numarul
					int rest = n % 29;
					result = (result % Mod) << (rest - 1); //Fac restul de inmultiri
					return (int) (result % Mod);
				}
			} else { //Aici nu mai merge matematic pentru ca depinde de ce bancnota am avut inainte
				long[][] dp = new long[5][n]; //Liniile is tipurile de bani, iar n este numarul lor
				for (int i = 0; i < 5; i++) {
					dp[i][0] = 1;
				} //Pot pune orice bancnota pe prima pozitie
				for (int i = 1; i < n; i++) {
					dp[0][i] = dp[1][i - 1] % Mod + dp[2][i - 1] % Mod + dp[4][i - 1] % Mod;
					dp[1][i] = dp[0][i - 1] % Mod + dp[3][i - 1] % Mod;
					dp[2][i] = dp[0][i - 1] % Mod + dp[2][i - 1] % Mod + dp[3][i - 1] % Mod;
					dp[3][i] = dp[1][i - 1] % Mod + dp[4][i - 1] % Mod;
					dp[4][i] = dp[3][i - 1] % Mod;
				}
				long rez = dp[0][n - 1] % Mod + dp[1][n - 1] % Mod;
				rez += dp[2][n - 1] % Mod + dp[3][n - 1] % Mod + dp[4][n - 1] % Mod;
				return (int) (rez % Mod);
			}
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