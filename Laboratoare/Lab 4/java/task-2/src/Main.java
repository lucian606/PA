import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    static class Task {
        public final static String INPUT_FILE = "in";
        public final static String OUTPUT_FILE = "out";

        private final static int MOD = 1000000007;

        int n;
        char[] expr;

        private void readInput() {
            try {
                Scanner sc = new Scanner(new File(INPUT_FILE));
                n = sc.nextInt();
                String s = sc.next().trim();
                s = " " + s;
                expr = s.toCharArray();
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

        public boolean isOperand(char c) {
            if(c == 'T' || c == 'F')
                return true;
                else return false;
        }

        private int getValueOf(char c) {
            if(c == 'T') return 1;
            else if(c == 'F') return 0;
            else return -1;
        }

        private int getResult() {
            // TODO: Calculati numarul de moduri in care se pot aseza
            // parantezele astfel incat rezultatul expresiei sa fie TRUE.
            // Numarul de moduri se va calcula modulo MOD (1000000007).
            int m=(n/2)+1;
            int j;
            char[] termeni=new char[m];
            char[] operatii=new char[(n-m) + 1];
            int count=0;
            for(int i=0; i <= n; i++) {
                if(isOperand(expr[i])) {
                    termeni[count] = expr[i];
                    count++;
                }
            }
            count=0;
            for(int i=1; i < n; i++) {
                if(!isOperand(expr[i])) {
                    operatii[count]=expr[i];
                    count++;
                }
            }

            long f[][] = new long[m][m];
            long t[][] = new long[m][m];

            for(int i=0; i < m; i++) {
                f[i][i]=1 - getValueOf(termeni[i]);
                t[i][i]=getValueOf(termeni[i]);
            }

            for(int p =1; p < m; p++) {
                for(int i=0; i < m; i++) {
                    j=i+p;
                    if(j < m) {
                        for(int k = i; k < j; k++) {
                            if(operatii[k] == '&') {
                                t[i][j] = (t[i][j] + 1l * t[i][k] * t[k + 1][j]) % MOD;
                                f[i][j] = (f[i][j] + 1l * t[i][k] * f[k + 1][j]) % MOD;
                                f[i][j] = (f[i][j] + 1l * f[i][k] * t[k + 1][j]) % MOD;
                                f[i][j] = (f[i][j] + 1l * f[i][k] * f[k + 1][j]) % MOD;
                            }
                            else if(operatii[k] == '|') {
                                t[i][j] = (t[i][j] + 1l * f[i][k] * t[k + 1][j]) % MOD;
                                t[i][j] = (t[i][j] + 1l * t[i][k] * t[k + 1][j]) % MOD;
                                t[i][j] = (t[i][j] + 1l * t[i][k] * f[k + 1][j]) % MOD;
                                f[i][j] = (f[i][j] + 1l * f[i][k] * f[k + 1][j]) % MOD;

                            }
                            else if(operatii[k] == '^') {
                                t[i][j] = (t[i][j] + 1l * t[i][k] * f[k + 1][j]) % MOD;
                                t[i][j] = (t[i][j] + 1l * f[i][k] * t[k + 1][j]) % MOD;
                                f[i][j] = (f[i][j] + 1l * f[i][k] * f[k + 1][j]) % MOD;
                                f[i][j] = (f[i][j] + 1l * t[i][k] * t[k + 1][j]) % MOD;
                            }
                        }
                    }
                }
            }
            return (int) t[0][m-1];
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
