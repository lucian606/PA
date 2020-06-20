import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    static class Homework {
        public int deadline;
        public int score;

        public Homework() {
            deadline = 0;
            score = 0;
        }
    }

    static class Task {
        public final static String INPUT_FILE = "in";
        public final static String OUTPUT_FILE = "out";

        int n;
        Homework[] hws;

        private void readInput() {
            try {
                Scanner sc = new Scanner(new File(INPUT_FILE));
                n = sc.nextInt();
                hws = new Homework[n];
                for (int i = 0; i < n; i++) {
                    hws[i] = new Homework();
                    hws[i].deadline = sc.nextInt();
                    hws[i].score = sc.nextInt();
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
            // TODO: Aflati punctajul maxim pe care il puteti obtine
            // planificand optim temele.
            int score=0;
            int max_weeks=0;
            for(int i=0; i < n; i++) {
                if(max_weeks < hws[i].deadline)
                    max_weeks=hws[i].deadline;
            }   //Aflam pana in ce saptamana putem face teme
            int[] done_hws=new int[max_weeks];    //new
            Arrays.sort(hws, new Comparator<Homework>() {
                @Override
                public int compare(Homework o1, Homework o2) {
                    return o1.score-o2.score;
                }
            });
            /*for(int j=max_weeks; j >= 1; j--) { //Plec din ultima saptamana si iau tema cu scor maxim
            //care se poate face atunci
                int index = -1;
                int max_score = 0;
                for (int i = n - 1; i >= 0; i--) {
                    if (hws[i].deadline >= j) {
                        index = i;
                        max_score = hws[i].score;
                        break;
                    }
                }
                if (index >= 0) {
                    score += hws[index].score;
                    hws[index].deadline = -1;   //O marchez ca fiind facuta
                }
            }*/ //Varianta 1
            for(int i=n-1; i >= 0; i--) {
                for (int j = hws[i].deadline-1; j >= 0; j--) {
                    if (done_hws[j] == 0) {
                        score += hws[i].score;
                        done_hws[j] = i;
                        break;
                    }
                }
            }   //Varianta 2
            return score;
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
