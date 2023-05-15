package com.kpi.lab1;

import java.util.Random;

public class Main {
    private static long sum = 0;

    public static void main(String[] args) throws InterruptedException {
        int n = 45000, m = 45000, numberOfThreads = 128;
        int[][] array = new int[n][m];
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                array[i][j] = (short) rand.nextInt(1000);
            }
        }
        long currentTime = System.nanoTime();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                sum += array[i][j];
            }
        }
        System.out.println(System.nanoTime() - currentTime);
        System.out.println("Single thread sum: " + sum);
        sum = 0;
        SumThread[] sumThread = new SumThread[numberOfThreads];
        currentTime = System.nanoTime();
        int numRowsPerThread = array.length / numberOfThreads;
        for (int i = 0; i < numberOfThreads; i++) {
            sumThread[i] = new SumThread(array, i * numRowsPerThread,
                    (i == numberOfThreads - 1) ? array.length : (i + 1) * numRowsPerThread);
            sumThread[i].start();
        }
        for (int i = 0; i < numberOfThreads; i++) {
            sumThread[i].join();
            sum += sumThread[i].getSum();
        }
        System.out.println(System.nanoTime() - currentTime);
        System.out.println("Multithreading sum: " + sum);
    }
}
