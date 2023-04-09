package com.kpi.lab2;

import java.util.concurrent.LinkedBlockingQueue;

public class TaskQueue extends Thread{
    private final LinkedBlockingQueue<Runnable> mainBuffer;
    private final LinkedBlockingQueue<Runnable> secondBuffer;
    private volatile boolean isOpenForAdding = true;

    public TaskQueue() {
        mainBuffer = new LinkedBlockingQueue<>();
        secondBuffer = new LinkedBlockingQueue<>();
    }

    public void addTask(Runnable task) {
        if(isOpenForAdding)
            mainBuffer.add(task);
        else secondBuffer.add(task);
    }

    public Runnable remove() throws InterruptedException {
        return mainBuffer.take();
    }

    public void swapBuffers(){
        LinkedBlockingQueue<Runnable> tempQueue = new LinkedBlockingQueue<>(secondBuffer);
        secondBuffer.clear();
        mainBuffer.addAll(tempQueue);
    }

    public void setOpenForAdding(boolean openForAdding) {
        isOpenForAdding = openForAdding;
    }

    public LinkedBlockingQueue<Runnable> getMainBuffer() {
        return mainBuffer;
    }

    public LinkedBlockingQueue<Runnable> getSecondBuffer() {
        return secondBuffer;
    }

    public void startWork() {
        while (!mainBuffer.isEmpty()) {
            try {
                mainBuffer.take().run();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void clear() {
        mainBuffer.clear();
        secondBuffer.clear();
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                System.out.println("you can add tasks");
                Thread.sleep(45000);
                isOpenForAdding = false;
                System.out.println("you can't add tasks");
                System.out.println("tasks are executing");
                //System.out.println(mainBuffer);
                while (!mainBuffer.isEmpty()){
                }
                mainBuffer.addAll(secondBuffer);
                secondBuffer.clear();
                isOpenForAdding = true;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
