import java.util.Scanner;

public class Solution {

        public static int minPathSum(int[][] grid) {
            int m, n, i, j;
            m=grid.length;
            n=grid[0].length;
            int[][] dp=new int[m][n];
            dp[0][0]=grid[0][0];
            for(i=1; i < n; i++)
                dp[0][i]=dp[0][i-1]+grid[0][i]; //Ca sa raman sus pot merge doar dreapta
            for(i=1; i < m; i++)
                dp[i][0]=dp[i-1][0]+grid[i][0]; //Ca sa raman stanga pot merge doar jos
            for(i=1; i < m; i++)
                for(j=1; j < n; j++)
                    dp[i][j]=grid[i][j]+Math.min(dp[i-1][j],dp[i][j-1]);
            return dp[m-1][n-1];
        }

    public static void main(String[] args) {
        int[][] grid;
        int i,j;
        Scanner input=new Scanner(System.in);
        int m=input.nextInt();
        int n=input.nextInt();
        grid=new int[m][n];
        for (i=0; i < m; i++)
            for (j=0; j < n; j++) {
                grid[i][j]=input.nextInt();
            }
        System.out.println(minPathSum(grid));
    }
}
