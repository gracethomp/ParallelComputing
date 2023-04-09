package com.kpi.lab2;

import java.util.Random;

public class ExampleUsage {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(4);
        long countOfThreads = 0;
        long timeTaskExecuting = 0;
        long currentTime = System.nanoTime();
        threadPool.start();
        do {
            Task task = new Task();
            threadPool.addTask(new Task());
            countOfThreads++;
            timeTaskExecuting += task.getDuration();
        } while (System.nanoTime() - currentTime < 60000000000L);
        threadPool.shutdown();
        System.out.println("Testing is ended. The count of threads: " + countOfThreads);
        System.out.println("The average time of executing tasks: " + ((double) timeTaskExecuting/countOfThreads));
        System.out.println("Queue size: " + threadPool.getTaskQueue().getMainBuffer().size());
        System.exit(0);
    }

    private static void test1() {
        Random random = new Random();
        TaskQueue tasks = new TaskQueue();
        tasks.setOpenForAdding(true);
        for (int i = 0; i < 10; i++) {
            //Thread.sleep(4000);
            tasks.addTask(new Task());
        }
        System.out.println("Main: " + tasks.getMainBuffer());
        System.out.println("Second: " + tasks.getSecondBuffer());
        tasks.startWork();
        //Thread.sleep(45000);
        tasks.setOpenForAdding(false);
        for (int i = 0; i < 10; i++) {
            int duration = random.nextInt(7) + 4; // between 4-10 seconds
            //Thread.sleep(4000);
            tasks.addTask(new Task());
        }
        System.out.println("Main: " + tasks.getMainBuffer());
        System.out.println("Second: " + tasks.getSecondBuffer());
    }

    private static void test2() throws InterruptedException {
        Random random = new Random();
        TaskQueue tasks = new TaskQueue();
        tasks.start();
        for (int i = 0; i < 10; i++) {
            int duration = random.nextInt(7) + 4; // between 4-10 seconds
            Thread.sleep(6000);
            System.out.println("task added");
            tasks.addTask(new Task());
        }
        Thread.sleep(5000);
        tasks.addTask(new Task());
        System.out.println("Second: " + tasks.getSecondBuffer());
        Thread.sleep(20000);
        System.out.println("Main: " + tasks.getMainBuffer());
    }
}
