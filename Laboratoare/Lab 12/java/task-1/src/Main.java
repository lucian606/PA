import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.*;

/**
 * Generic class to represent a regular pair (C++ style).
 *
 * Use it for storing the indexes of a grid cell like this <row, col>.
 */
class Pair<T, U> {
    public T first;
    public U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object other) {
        Pair<T, U> p = (Pair<T, U>) other;

        return this.first.equals(p.first) && this.second.equals(p.second);
    }

    @Override
    public int hashCode() {
        int result = this.first == null ? 0 : this.first.hashCode();
        result = result * 31 + (this.second == null ? 0 : this.second.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "<" + this.first + ", " + this.second + ">";
    }
}

public class Main {

    static int[][] f;
    static int[][] h;
    static int[][] g;
    static Pair[][] parinti;
    /**
     * Generic method to print the path to a goal state.
     *
     * Each element in the path is a pair containing a matrix cell's row and column.
     *
     * @param path        The path to a goal state.
     */
    public static <T, U> void printPath(List<Pair<T, U>> path) {
        System.out.println(path.size() - 1);
        for (int i = path.size() - 1; i >= 0; --i) {
            System.out.println(path.get(i).first + " " + path.get(i).second);
        }
    }

    static class PairSorter implements Comparator<Pair> {
        public int compare(Pair p1, Pair p2) {
            return (f[(int)p1.first][(int)p1.second] - f[(int)p2.first][(int)p2.second]);
        }
    }
    /**
     * Method to implement the A* algorithm.
     *
     * @param M             Encoding of the grid.
     * @param rowPacman     Pacman's starting row.
     * @param colPacman     Pacman's starting column.
     * @param rowFood       Food row.
     * @param colFood       Fool column.
     *
     * @return              The shortest path between Pacman and food, determined with A*.
     *                      If no such path exists, returns an empty path.
     */
    public static List<Pair<Integer, Integer>> astar(List<List<Character>> M,
                                                     int rowPacman, int colPacman,
                                                     int rowFood, int colFood) {
        List<Pair<Integer, Integer>> path = new ArrayList<>();

        Pair<Integer, Integer> start_nod = new Pair(rowPacman, colPacman);
        boolean[][] vizitat = new boolean[M.size()][M.get(0).size()];
        PriorityQueue<Pair<Integer, Integer>> q = new PriorityQueue(new PairSorter());
        // TODO: Implement A*.
        
        q.add(start_nod);
        
        h[rowPacman][colPacman] = Math.abs(rowPacman - rowFood) + Math.abs(colPacman - colFood);
        f[rowPacman][colPacman] = h[rowPacman][colPacman];
        g[rowPacman][colPacman] = 0;
        
        parinti[rowPacman][colPacman] = new Pair(-1,-1);
        boolean food_found = false;
        
        while(!q.isEmpty()) {
        
            Pair<Integer, Integer> curr_cell = q.poll();
            int x =(int) curr_cell.first;
            int y =(int) curr_cell.second;
            if(x == rowFood && y == colFood) {
                food_found = true;
                break;
            }
            
            if(!vizitat[x][y]) {    
            
                for(Pair<Integer,Integer> vecin : getNeigh(M, curr_cell)) {
                    
                    int a = (int) vecin.first;
                    int b = (int) vecin.second;
                    
                    if(!vizitat[vecin.first][vecin.second] && g[a][b] > g[x][y] + 1) {
                     g[a][b] = g[x][y] + 1;
                     h[a][b] = Math.abs(a - rowFood) + Math.abs(b - colFood);
                     parinti[a][b] = curr_cell;
                     f[a][b] = g[a][b] + h[a][b];
                     q.add(vecin);
                    }
                }

                vizitat[x][y] = true;
            }
        }

        if(food_found) {
            
            int linii, col;
            linii = rowFood;
            col = colFood;
            
            while(linii != -1 && col != -1) {
                int old = linii;
                path.add(new Pair(linii, col));
                linii =(int) parinti[linii][col].first;
                col =(int) parinti[old][col].second;
            }
        }
        return path;
    }

    public static ArrayList<Pair> getNeigh(List<List<Character>> M, Pair p) {
        ArrayList<Pair> vecini = new ArrayList();
        int x =(int) p.first;
        int y =(int) p.second;
        if(M.get(x-1).get(y) != '%')
            vecini.add(new Pair(x-1, y));
        if(M.get(x+1).get(y) != '%')
            vecini.add(new Pair(x+1, y));
        if(M.get(x).get(y-1) != '%')
            vecini.add(new Pair(x, y - 1));
        if(M.get(x).get(y+1) != '%')
            vecini.add(new Pair(x, y+1));
        return vecini;
    }

    public static void main(String[] args) {
        List<List<Character>> M = new ArrayList<>();
        List<Pair<Integer, Integer>> path;
        int rowPacman, colPacman;
        int rowFood, colFood;
        int numRows, numCols;
        char c;

        Scanner s = new Scanner(System.in);

        rowPacman = s.nextInt();
        colPacman = s.nextInt();
        rowFood = s.nextInt();
        colFood = s.nextInt();
        numRows = s.nextInt();
        numCols = s.nextInt();

        M = new ArrayList<>();
        f = new int[numRows][numCols];
        h = new int[numRows][numCols];
        g = new int[numRows][numCols];
        parinti = new Pair[numRows][numCols];
        
        for (int i = 0; i < numRows; ++i) {
            List<Character> currentRow = new ArrayList<>();
            String nextRow = s.next();
            for (int j = 0; j < numCols; ++j) {
                currentRow.add(nextRow.charAt(j));
                parinti[i][j] = new Pair(-1,-1);
                f[i][j] = Integer.MAX_VALUE;
                g[i][j] = Integer.MAX_VALUE;
            }
            M.add(currentRow);
        }

        s.close();

        path = astar(M, rowPacman, colPacman, rowFood, colFood);
        printPath(path);
    }
}

