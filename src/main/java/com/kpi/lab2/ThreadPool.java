package com.kpi.lab2;

import java.util.ArrayList;
import java.util.List;

public class ThreadPool extends Thread{
    private final TaskQueue taskQueue = new TaskQueue();
    private final List<Thread> threads;
    private volatile boolean isPaused = false;

    public ThreadPool(int threadCount) {
        threads = new ArrayList<>(threadCount);

        for (int i = 0; i < threadCount; i++) {
            threads.add(new WorkerThread());
        }
    }

    @Override
    public void run() {
        taskQueue.start();
        threads.forEach(Thread::start);

        threads.forEach(worker -> {
            try {
                worker.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void addTask(Runnable task) {
        taskQueue.addTask(task);
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    public TaskQueue getTaskQueue() {
        return taskQueue;
    }

    private class WorkerThread extends Thread {
        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    Runnable task = taskQueue.remove();
                    task.run();
                } catch (InterruptedException e) {
                    interrupt();
                }
            }
        }
    }
}