package com.kpi.lab3;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class ParallelEvenNumberCounter {
    private final int[] arr; // масив
    private final int numThreads; // кількість потоків
    private int evenCount; // лічильник парних чисел
    private int maxEven; // найбільше парне число
    private final Lock lock; // блокування

    public ParallelEvenNumberCounter(int[] arr, int numThreads) {
        this.arr = arr;
        this.numThreads = numThreads;
        this.evenCount = 0;
        this.maxEven = Integer.MIN_VALUE;
        this.lock = new ReentrantLock();
    }

    public void countEvenNumbers() throws InterruptedException {
        Thread[] threads = new Thread[numThreads];
        int chunkSize = arr.length / numThreads;

        for (int i = 0; i < numThreads; i++) {
            int startIndex = i * chunkSize;
            int endIndex = (i == numThreads - 1) ? arr.length : startIndex + chunkSize;
            threads[i] = new Thread(new CountEvenTask(startIndex, endIndex));
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            threads[i].join();
        }
    }

    public int getEvenCount() {
        return evenCount;
    }

    public int getMaxEven() {
        return maxEven;
    }

    private class CountEvenTask implements Runnable {
        private final int startIndex;
        private final int endIndex;

        public CountEvenTask(int startIndex, int endIndex) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        @Override
        public void run() {
            int localEvenCount = 0;
            int localMaxEven = Integer.MIN_VALUE;

            for (int i = startIndex; i < endIndex; i++) {
                if (arr[i] % 2 == 0) {
                    localEvenCount++;

                    // Оновлюємо максимальне парне число, якщо поточне число більше
                    if (arr[i] > localMaxEven) {
                        localMaxEven = arr[i];
                    }
                }
            }

            // Захищаємо доступ до спільних змінних за допомогою блокування
            lock.lock();
            try {
                evenCount += localEvenCount;
                if (localMaxEven > maxEven) {
                    maxEven = localMaxEven;
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
