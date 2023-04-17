package com.kpi.lab3;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class ArrayProcessor {
    public static int countEvenNumbers(int[] array) {
        int count = 0;
        for (int num : array) {
            if (num % 2 == 0) {
                count++;
            }
        }
        return count;
    }

    public static int findLargestEvenNumber(int[] array) {
        int max = Integer.MIN_VALUE;
        for (int num : array) {
            if (num % 2 == 0 && num > max) {
                max = num;
            }
        }
        return max;
    }

    public static void main(String[] args) throws InterruptedException {
        // Генеруємо масив з випадкових чисел
        int[] array = new int[1000];
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(1000) + 1;
        }

        doOneThreadVersion(array);

        ParallelEvenNumberCounter counter = new ParallelEvenNumberCounter(array, 4);
        long startTime = System.nanoTime(); // Запам'ятовуємо початковий час
        counter.countEvenNumbers();
        long endTime = System.nanoTime(); // Запам'ятовуємо кінцевий час
        long elapsedTime = endTime - startTime; // Розраховуємо час виконання завдання
        System.out.println("Кількість парних чисел: " + counter.getEvenCount());
        System.out.println("Найбільше парне число: " + counter.getMaxEven());
        System.out.println("Час виконання завдання: " + elapsedTime + " мс");
        doAtomicVersion(array);
    }
    public static void doOneThreadVersion(int[] array){
        int count; // Змінна для підрахунку кількості парних елементів
        int maxEven; // Змінна для зберігання найбільшого парного числа
        long startTime = System.nanoTime(); // Запам'ятовуємо початковий час
        count = countEvenNumbers(array);
        maxEven = findLargestEvenNumber(array);
        long endTime = System.nanoTime(); // Запам'ятовуємо кінцевий час
        long elapsedTime = endTime - startTime; // Розраховуємо час виконання завдання
        System.out.println("Кількість парних елементів: " + count);
        System.out.println("Найбільше парне число: " + maxEven);
        System.out.println("Час виконання завдання: " + elapsedTime + " мс");
    }
    public static void doAtomicVersion(int[] array){
        int count;
        int maxEven;
        long startTime = System.nanoTime(); // Запам'ятовуємо початковий час
        count = countEvenElementsAtomic(array);
        maxEven = findMaxEvenAtomic(array);
        long endTime = System.nanoTime(); // Запам'ятовуємо кінцевий час
        long elapsedTime = endTime - startTime; // Розраховуємо час виконання завдання
        System.out.println("Кількість парних елементів: " + count);
        System.out.println("Найбільше парне число: " + maxEven);
        System.out.println("Час виконання завдання: " + elapsedTime + " мс");
    }
    public static int countEvenElementsAtomic(int[] array) {
        AtomicInteger count = new AtomicInteger(0); // Атомарна змінна для підрахунку кількості парних елементів
        for (int num : array) {
            if (num % 2 == 0) {
                count.incrementAndGet(); // Збільшуємо лічильник атомарно
            }
        }
        return count.get();
    }

    public static int findMaxEvenAtomic(int[] array) {
        AtomicInteger maxEven = new AtomicInteger(Integer.MIN_VALUE); // Атомарна змінна для збереження найбільшого парного числа
        for (int num : array) {
            if (num % 2 == 0 && num > maxEven.get()) {
                // Виконуємо CAS операцію для зміни значення атомарно, якщо знайдене число більше поточного максимуму
                maxEven.compareAndSet(maxEven.get(), num);
            }
        }
        return maxEven.get();
    }
}
