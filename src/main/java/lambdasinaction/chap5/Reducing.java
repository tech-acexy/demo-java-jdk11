package lambdasinaction.chap5;

import lambdasinaction.bean.Dish;

import java.util.*;

import static lambdasinaction.bean.Dish.menu;

public class Reducing {

    public static void main(String... args) {

        List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);
        int sum = numbers.stream().reduce(0, (a, b) -> a + b);
        System.out.println(sum);

        int sum2 = numbers.stream().reduce(0, Integer::sum);
        System.out.println(sum2);

        int max = numbers.stream().reduce(0, (a, b) -> Integer.max(a, b));
        System.out.println("reduce max -> " + max);

        var maxOpt = numbers.stream().max(Comparator.comparingInt(o -> o));
        maxOpt.ifPresent(v -> System.out.println("stream max -> " + v));

        var minOpt = numbers.stream().reduce(Integer::min);
        minOpt.ifPresent(System.out::println);

        int calories = menu.stream()
                .map(Dish::getCalories)
                .reduce(0, Integer::sum);
        System.out.println("Number of calories:" + calories);

    }
}
