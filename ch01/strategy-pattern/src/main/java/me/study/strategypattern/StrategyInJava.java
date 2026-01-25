package me.study.strategypattern;

import java.util.*;

public class StrategyInJava {

    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(10);
        numbers.add(5);
        System.out.println(numbers); // 10, 5

        Collections.sort(numbers);
        System.out.println(numbers); // 5, 10

        Collections.sort(numbers, Collections.reverseOrder());
        System.out.println(numbers); // 10, 5

        Collections.sort(numbers, new  Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        System.out.println(numbers); // 5, 10
    }
}
