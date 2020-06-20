import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

class Matrice {
    int[][] m;
    int counter;
    public Matrice(int size) {
        m=new int[size][size];
        counter=1;
    }
}

public class Main {
    static class Task {
        public final static String INPUT_FILE = "in";
        public final static String OUTPUT_FILE = "out";

        int n, x, y;

        private void readInput() {
            try {
                Scanner sc = new Scanner(new File(INPUT_FILE));
                n = sc.nextInt();
                x = sc.nextInt();
                y = sc.nextInt();
                sc.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void writeOutput(int answer) {
            try {
                PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
                pw.printf("%d\n", answer);
                pw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void ZTraversal(Matrice M, int x_start, int x_end, int y_start, int y_end) {
            if(x_start == x_end && y_start == y_end) {
                M.m[x_start][y_start]=M.counter;
                M.counter++;
            }
            int xm=(x_start+x_end)>>1;
            int ym=(y_start+y_end)>>1;
            ZTraversal(M,x_start,xm,y_start,ym);
            ZTraversal(M,x_start,xm,ym+1,y_end);
            ZTraversal(M,xm+1,x_end,y_start,ym);
            ZTraversal(M,xm+1,x_end,ym+1,y_end);
        }

        private int getAnswer(int n, int x, int y) {
            // TODO: Calculati valoarea de pe pozitia (x, y) din matricea de dimensiune
            // 2^N * 2^N.
            if(n==0)
                return 1;
            int dim=(int) Math.pow(2,n); //2^N LINII SI COLOANE
            int size=dim*dim;   //4^N
            int mid=dim/2;  //MIJLOC (2^N-1)
            if(x <= mid) {
                if(y <= mid)
                    return getAnswer(n/2,x,y);
                else
                    return size/4 + getAnswer(n/2,x,y-mid);
            }
            else {
                if(y <= mid)
                    return (((size)/4) *2)+ getAnswer(n/2,x-mid,y);
                else
                    return (((size)/4) *3)+ getAnswer(n/2,x-mid,y-mid);
            }
        }

        public void solve() {
            readInput();
            writeOutput(getAnswer(n, x, y));
        }
    }

    public static void main(String[] args) {
        new Task().solve();
    }
}
