package com.spring_boot.caching;
import java.util.List;

public class Generic {
    public static List<Integer> getList(List<String> names) {
        return names.stream().map(String::length).toList();
    }
    public static List<Integer> getEvenNumber(List<Integer> numbers) {
        return numbers.stream().filter(number -> number % 2 == 0).toList();
    }
}
