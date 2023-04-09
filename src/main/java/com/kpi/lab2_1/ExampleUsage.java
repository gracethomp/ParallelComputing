package com.kpi.lab2_1;

import java.util.Random;

public class ExampleUsage {
    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        TaskQueue tasks = new TaskQueue();
        tasks.start();
        for (int i = 0; i < 10; i++) {
            int duration = random.nextInt(7) + 4; // between 4-10 seconds
            Thread.sleep(4000);
            System.out.println("task added");
            tasks.addTask(() -> {
                try {
                    System.out.println("Executing task for " + duration + " seconds");
                    Thread.sleep(duration * 1000);
                    System.out.println("Task with duration " + duration + " is executed");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        Thread.sleep(5000);
        tasks.addTask(() -> {
            int duration = random.nextInt(7) + 4; // between 4-10 seconds
            try {
                System.out.println("Executing task for " + duration + " seconds");
                Thread.sleep(duration * 1000);
                System.out.println("Task with duration " + duration + " is executed");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        System.out.println("Second: " + tasks.getSecondBuffer());
        Thread.sleep(20000);
        System.out.println("Main: " + tasks.getMainBuffer());

    }

    private static void test1() {
        Random random = new Random();
        TaskQueue tasks = new TaskQueue();
        tasks.setOpenForAdding(true);
        for (int i = 0; i < 10; i++) {
            int duration = random.nextInt(7) + 4; // between 4-10 seconds
            //Thread.sleep(4000);
            tasks.addTask(() -> {
                try {
                    System.out.println("Executing task for " + duration + " seconds");
                    Thread.sleep(duration * 1000);
                    System.out.println("Task with duration " + duration + " is executed");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        System.out.println("Main: " + tasks.getMainBuffer());
        System.out.println("Second: " + tasks.getSecondBuffer());
        tasks.startWork();
        //Thread.sleep(45000);
        tasks.setOpenForAdding(false);
        for (int i = 0; i < 10; i++) {
            int duration = random.nextInt(7) + 4; // between 4-10 seconds
            //Thread.sleep(4000);
            tasks.addTask(() -> {
                try {
                    System.out.println("Executing task for " + duration + " seconds");
                    Thread.sleep(duration * 1000);
                    System.out.println("Task with duration " + duration + " is executed");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        System.out.println("Main: " + tasks.getMainBuffer());
        System.out.println("Second: " + tasks.getSecondBuffer());
    }
}
