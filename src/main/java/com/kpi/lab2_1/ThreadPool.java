package com.kpi.lab2_1;

public class ThreadPool {
    private final TaskQueue taskQueue = new TaskQueue();
    private final Thread[] threads;
    private volatile boolean isPaused = false;

    public ThreadPool(int threadCount) {
        threads = new Thread[threadCount];

//        for (int i = 0; i < threadCount; i++) {
//            threads[i] = new WorkerThread();
//            threads[i].start();
//        }
    }

    public void start() {
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new WorkerThread();
            threads[i].start();
        }
    }

    public void pause() {
        isPaused = true;
    }

    public void resume() {
        System.out.println(taskQueue);
        isPaused = false;

    }

    public void addTask(Runnable task) {
        taskQueue.addTask(task);
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
        taskQueue.clear();
    }

    private class WorkerThread extends Thread {
        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    Runnable task = taskQueue.remove();
                    if (!isPaused) {
                        task.run();
                    }
                } catch (InterruptedException e) {
                    interrupt();
                }
            }
        }
    }
}