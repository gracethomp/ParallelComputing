package com.kpi.lab3;

import java.util.Random;

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
        int[] array = new int[500000000];
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(1000000000) + 1;
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

        EvenNumberCounterWithAtomic counter1 = new EvenNumberCounterWithAtomic(array, 4);
        long startTime1 = System.nanoTime(); // Запам'ятовуємо початковий час
        counter1.countEvenNumbers();
        long endTime1 = System.nanoTime(); // Запам'ятовуємо кінцевий час
        long elapsedTime1 = endTime1 - startTime1; // Розраховуємо час виконання завдання
        System.out.println("Кількість парних чисел: " + counter1.getEvenCount());
        System.out.println("Найбільше парне число: " + counter1.getMaxEven());
        System.out.println("Час виконання завдання: " + elapsedTime1 + " мс");
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
}
