package com.kpi;

public class SumThread extends Thread{
    private long[][] matrixA;
    private int startIndex;
    private int endIndex;
    public SumThread(){}
    public SumThread(long[][] matrixA, int startIndex, int endIndex){
        this.matrixA = matrixA;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void run() {
        for (int i = startIndex; i < endIndex; i++) {
            for (int j = 0; j < matrixA.length; j++){
                Main.increaseSum(matrixA[i][j]);
            }
        }
    }
}
