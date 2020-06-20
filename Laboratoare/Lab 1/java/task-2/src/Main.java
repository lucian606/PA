import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    static class Task {
        public final static String INPUT_FILE = "in";
        public final static String OUTPUT_FILE = "out";

        double n;

        private void readInput() {
            try {
                Scanner sc = new Scanner(new File(INPUT_FILE));
                n = sc.nextDouble();
                sc.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void writeOutput(double x) {
            try {
                PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
                pw.printf("%.4f\n", x);
                pw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private double computeSqrt() {
            // TODO: Calculeaza sqrt(n) cu o precizie de 10^-3.
            // Precizie de 10^(-x) inseamna |valoarea_ta - valoarea_reala| < 10^(-x).
            double st=0;
            double dr=n;
            double ans=0;
            while (st <= dr) {
                double m=(st+dr)/2;
                if(m*m == n) {
                    ans = m;
                    return m;
                }
                if(m * m < n) {
                    ans=m;
                    st=m+1;
                }
                else
                    dr=m-1;
            }

            int i;
            float decimal=0.1f;
            while (decimal > 0.00001) {
                if((decimal+ans)*(decimal+ans) >= n) {
                    decimal/=10;
                }
                else
                    ans+=decimal;
            }
            return ans;
        }

        public void solve() {
            readInput();
            writeOutput(computeSqrt());
        }
    }

    public static void main(String[] args) {
        new Task().solve();
    }
}
