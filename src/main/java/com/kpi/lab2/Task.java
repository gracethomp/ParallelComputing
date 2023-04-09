package com.kpi.lab2;

import java.util.Random;

public class Task implements Runnable{
    private final Random random = new Random();
    private final int duration =  random.nextInt(7) + 4;

    @Override
    public void run() {
        try {
            System.out.println("Executing task for " + duration + " seconds");
            Thread.sleep(duration * 1000L);
            System.out.println("Task with duration " + duration + " is executed");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public int getDuration() {
        return duration;
    }
}
