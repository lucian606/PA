import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static class Task {
        public final static String INPUT_FILE = "in";
        public final static String OUTPUT_FILE = "out";

        int n;

        private void readInput() {
            try {
                Scanner sc = new Scanner(new File(INPUT_FILE));
                n = sc.nextInt();
                sc.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void writeOutput(ArrayList<ArrayList<Integer>> result) {
            try {
                PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
                pw.printf("%d\n", result.size());
                for (ArrayList<Integer> arr : result) {
                    for (int i = 0; i < arr.size(); i++) {
                        pw.printf("%d%c", arr.get(i), i + 1 == arr.size() ?
                                '\n' : ' ');
                    }
                }
                pw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        void submultimi(int step, int stop,ArrayList<ArrayList<Integer>> all, ArrayList<Integer> solution, ArrayList<Integer> visited) {
                all.add((ArrayList<Integer>) solution.clone());
                if(step == stop)
                    return;
                int index_start;
                if(step == 0)
                    index_start=0;
                else
                    index_start=solution.get(solution.size()-1);
            for(int i = index_start+1; i <= n ;i++) {
                if(visited.contains(i))
                    continue;
                visited.add(i);
                solution.add(i);
                submultimi(step+1, stop, all, solution, visited);
                solution.remove((Integer)(i));
                visited.remove((Integer)(i));
            }
        }

        private ArrayList<ArrayList<Integer>> getResult() {
            ArrayList<ArrayList<Integer>> all = new ArrayList<>();

            // TODO: Construiti toate submultimele multimii {1, ..., N}.

            // Pentru a adauga o noua submultime:
            //   ArrayList<Integer> submultime;
            //   all.add(submultime);
            ArrayList<Integer> vizitat=new ArrayList<>();
            ArrayList<Integer>  solutie=new ArrayList<>();
            submultimi(0,n, all,solutie,vizitat);
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
