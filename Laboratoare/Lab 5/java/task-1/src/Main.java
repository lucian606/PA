import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static class Task {
        public final static String INPUT_FILE = "in";
        public final static String OUTPUT_FILE = "out";

        int n, k;

        private void readInput() {
            try {
                Scanner sc = new Scanner(new File(INPUT_FILE));
                n = sc.nextInt();
                k = sc.nextInt();
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

        private void back(int step, ArrayList<ArrayList<Integer>> all,ArrayList<Integer> solution, ArrayList<Integer> visited) {
            if(step == k) {
                all.add((ArrayList<Integer>) solution.clone());
                return;
            }
            for(int i = 0 ; i < n; i++) {
                if(visited.contains(i+1))
                    continue;
                visited.add(i+1);
                solution.add(i+1);
                back(step+1,all,solution,visited);
                solution.remove((Integer)(i+1));
                visited.remove((Integer)(i+1));
            }
        }

        private ArrayList<ArrayList<Integer>> getResult() {
            ArrayList<ArrayList<Integer>> all = new ArrayList<>();

            // TODO: Construiti toate aranjamentele de N luate cate K ale
            // multimii {1, ..., N}.

            // Pentru a adauga un nou aranjament:
            //   ArrayList<Integer> aranjament;
            //   all.add(aranjament);
            ArrayList<Integer>  solutie=new ArrayList<>();
          //  ArrayList<Integer> domain=new ArrayList<>();
            ArrayList<Integer> vizitat=new ArrayList<>();
            //for(int i = 0; i < n;i++)
              //  domain.add(i+1);
            back(0, all,solutie,vizitat);
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
