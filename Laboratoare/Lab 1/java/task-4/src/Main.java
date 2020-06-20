import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    static class Task {
        public final static String INPUT_FILE = "in";
        public final static String OUTPUT_FILE = "out";

        int base;
        int exponent;
        int mod;

        private void readInput() {
            try {
                Scanner sc = new Scanner(new File(INPUT_FILE));
                base = sc.nextInt();
                exponent = sc.nextInt();
                mod = sc.nextInt();
                sc.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void writeOutput(long result) {
            try {
                PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
                pw.printf("%d\n", result);
                pw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private int fastPow(int base, int exponent, int mod) {
            // TODO: Calculati (base^exponent) modulo mod in O(log exponent).
            if(exponent == 0)
                return (1%mod);
            if(exponent == 1)
                return (base%mod);
            else {
                if (exponent % 2 == 0)
                    return (int) ((1L * fastPow(base, exponent / 2, mod)* fastPow(base, exponent / 2, mod))%mod);
                else{
                    return (int) ((1L * (base % mod) * ((fastPow(base, exponent / 2, mod) * fastPow(base, exponent / 2, mod)) % mod)) % mod);
                }
            }

        }



        public void solve() {
            readInput();
            long result = fastPow(base, exponent, mod);
            writeOutput(result);
        }
    }

    public static void main(String[] args) {
        new Task().solve();
    }
}
