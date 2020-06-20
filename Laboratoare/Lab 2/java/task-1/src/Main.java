import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    static class Obj implements Comparable {
        public int weight;
        public int price;

        public Obj() {
            weight = 0;
            price = 0;
        }

        public int compareTo(Object o) {
            if(o instanceof Obj) {
            double r1=(float) price/weight;
            double r2=(float) ((Obj) o).price/((Obj) o).weight;
            if(r1 > r2)
                return 1;
            if(r1 == r2)
                return 0;
            else
                return -1;
            }
            return 0;
        }
    };


    static class Task {
        public final static String INPUT_FILE = "in";
        public final static String OUTPUT_FILE = "out";

        int n, w;
        Obj[] objs;


        private void readInput() {
            try {
                Scanner sc = new Scanner(new File(INPUT_FILE));
                n = sc.nextInt();
                w = sc.nextInt();
                objs = new Obj[n];
                for (int i = 0; i < n; i++) {
                    objs[i] = new Obj();
                    objs[i].weight = sc.nextInt();
                    objs[i].price = sc.nextInt();
                }
                sc.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void writeOutput(double result) {
            try {
                PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
                pw.printf("%.4f\n", result);
                pw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private double getResult() {
            // TODO: Aflati profitul maxim care se poate obtine cu
            // obiectele date.
             Arrays.sort(objs, Obj::compareTo);
            double profit=0;
            for(int i=n-1; i >= 0; i--) {
                    if(w >= objs[i].weight) {
                        w-=objs[i].weight;
                        profit=profit+objs[i].price;
                    }
                    else {
                        double ratio = ((double) w) / ((double) objs[i].weight);
                        System.out.println(ratio);
                        w = 0;
                        profit =profit+ ( ratio * (objs[i].price));
                    }
                    if(w == 0)
                        break;
            }
            return profit;
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
