package com.company;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Main {

    static class Difference {
        int index;
        int difference;
        public Difference() {
            index=0;
            difference=0;
        }
    }


    public static void main(String[] args) {
	// write your code here
        Scanner input=new Scanner(System.in);
        int n,k;
        int cost=0;
        int bought_products=0;
        n=input.nextInt();
        k=input.nextInt();
        int a[], b[];
        a=new int[n];
        b=new int[n];
        Difference[] diffs=new Difference[n];
        for(int i=0; i <n; i++) {
            a[i] = input.nextInt();
            diffs[i]=new Difference();
        }
        for(int i=0; i<n; i++) {
            b[i] = input.nextInt();
            diffs[i].difference = a[i] - b[i];
            diffs[i].index = i;
        }
        Arrays.sort(diffs, new Comparator<Difference>() {
            @Override
            public int compare(Difference o1, Difference o2) {
                return o1.difference-o2.difference;
            }
        });
        for(int i=0; i < n; i++) {
            if(bought_products < k) {
                cost += a[diffs[i].index];
                bought_products++;
            }
            else if(diffs[i].difference < 0)
                cost+=a[diffs[i].index];
            else
                cost+=b[diffs[i].index];
        }
        System.out.println(cost);
    }
}
