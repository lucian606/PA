import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Vector;

public class Bonus {

    static class Point {
        int x;
        int y;
        int distance;

        public Point(int x, int y, int distance) {
            this.x = x;
            this.y = y;
            this.distance = distance;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        int n;
        File f = new File("labirint.in");
        Scanner input = new Scanner(f);
        n = input.nextInt();
        int[][] labirint = new int[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            labirint[0][i] = 1;
            labirint[i][0] = 1;
        }
        for (int i = 1; i <= n; i++) {
            for(int j = 1; j <= n; j++) {
                int nr = input.nextInt();
                labirint[i][j] = nr;
                System.out.print(labirint[i][j]);
            }
            System.out.println();
        }
        int start_x, start_y;
        int min_dist = -1;
        start_x = input.nextInt();
        start_y = input.nextInt();
        System.out.println("Start pos: " + start_x+" "+start_y);
        Queue<Point> q = new LinkedList<>();
        labirint[start_x][start_y] = 3; //3 -> Vizitat
        q.add(new Point(start_x, start_y, 0));
        while (!q.isEmpty()) {
            Point p = q.peek();
            if (labirint[p.x][p.y] == 2) {
                min_dist = p.distance;
                break;
            }
            q.remove();
            if (labirint[p.x][p.y - 1] % 2 == 0) //Celula libera/nevizitata
                q.add(new Point(p.x, p.y - 1, p.distance + 1)); //Sus
            if (labirint[p.x + 1][p.y] % 2 == 0)
                q.add(new Point(p.x + 1, p.y, p.distance+1)); //Dreapta
            if (labirint[p.x][p.y + 1] % 2 == 0)
                q.add(new Point(p.x, p.y + 1, p.distance + 1)); //Jos
            if (labirint[p.x - 1][p.y] % 2 == 0)
                q.add(new Point(p.x - 1, p.y, p.distance + 1)); //Stanga
        }
        System.out.println("lungime:" + min_dist);
    }
}
