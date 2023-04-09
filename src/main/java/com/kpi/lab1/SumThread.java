package com.kpi.lab1;

public class SumThread extends Thread{
    private short[][] matrixA;
    private long sum;
    private int startIndex;
    private int endIndex;
    public SumThread(){}
    public SumThread(short[][] matrixA, int startIndex, int endIndex){
        this.matrixA = matrixA;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void run() {
        for (int i = startIndex; i < endIndex; i++) {
            for (int j = 0; j < matrixA[i].length; j++) {
                sum += matrixA[i][j];
            }
        }
    }

    public long getSum() {
        return sum;
    }
}
