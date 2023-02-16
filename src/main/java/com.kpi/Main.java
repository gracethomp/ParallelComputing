package com.kpi;

import java.util.Scanner;

public class Main {
    private static volatile long sum = 0;

    public static void main(String[] args) throws InterruptedException {
        int n = 150, m = 150, numberOfThreads = 1;
        Scanner sc = new Scanner(System.in);
        //n = sc.nextInt();
        //m = sc.nextInt();
        long[][] array = new long[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                array[i][j] = (long) ((Math.random() * (1000 - 1)) + 1);
            }
        }
        for (int i = 0; i < n; i++) {
            //System.out.println(Arrays.toString(array[i]));
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                sum += array[i][j];
            }
        }
        System.out.println(sum);
        sum = 0;
        SumThread[] sumThread = new SumThread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            sumThread[i] = new SumThread(array, m/numberOfThreads * i,
                    i == (numberOfThreads - 1) ? m : m / numberOfThreads * (i + 1));
            sumThread[i].start();
        }
        for (int i = 0; i < numberOfThreads; i++) {
            sumThread[i].join();
        }
        System.out.println(sum);
    }

    public static synchronized void increaseSum(long a) {
        sum += a;
    }
}
