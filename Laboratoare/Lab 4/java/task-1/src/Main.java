import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    static class Task {
        public final static String INPUT_FILE = "in";
        public final static String OUTPUT_FILE = "out";

        int n;
        int[] v;

        private final static int MOD = 1000000007;

        private void readInput() {
            try {
                Scanner sc = new Scanner(new File(INPUT_FILE));
                n = sc.nextInt();
                v = new int[n + 1];
                for (int i = 1; i <= n; i++) {
                    v[i] = sc.nextInt();
                }
                sc.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void writeOutput(int result) {
            try {
                PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
                pw.printf("%d\n", result);
                pw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private int getResult() {
            // TODO: Aflati numarul de subsiruri (ale sirului stocat in v,
            // indexat de la 1 la n), nevide cu suma numerelor para.
            // Rezultatul se va intoarce modulo MOD (1000000007).
            long[][] dp = new long[2][n+1];
            dp[0][0]=0;
            dp[1][0]=0;
            for(int i = 1; i <=n; i++) {
                if(v[i-1] % 2 == 0) {
                    dp[0][i]=(2*dp[0][i-1]+1)%1000000007;
                    dp[1][i]=(2*dp[1][i-1])%1000000007;
                }
                else {
                    dp[0][i]=(dp[0][i-1]+dp[1][i-1])%1000000007;
                    dp[1][i]=(dp[1][i-1]+dp[0][i-1]+1)%1000000007;
                }
            }
            return (int)(dp[0][n]);
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
