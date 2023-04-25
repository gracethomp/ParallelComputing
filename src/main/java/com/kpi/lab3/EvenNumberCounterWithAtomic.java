package com.kpi.lab3;

import java.util.concurrent.atomic.AtomicInteger;

public class EvenNumberCounterWithAtomic {
    private final int[] arr; // масив
    private final int numThreads; // кількість потоків
    private final AtomicInteger evenCount; // атомік для лічильника парних чисел
    private final AtomicInteger maxEven; // атомік для найбільшого парного числа

    public EvenNumberCounterWithAtomic(int[] arr, int numThreads) {
        this.arr = arr;
        this.numThreads = numThreads;
        this.evenCount = new AtomicInteger(0);
        this.maxEven = new AtomicInteger(Integer.MIN_VALUE);
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
        return evenCount.get();
    }

    public int getMaxEven() {
        return maxEven.get();
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

            int oldValue;
            int newValue;
            do {
                oldValue = evenCount.get();
                newValue = oldValue + localEvenCount;
            } while (!evenCount.compareAndSet(oldValue, newValue));
            do {
                oldValue = maxEven.get();
                newValue = Math.max(oldValue, localMaxEven);
            } while (!maxEven.compareAndSet(oldValue, newValue));
        }
    }
}
