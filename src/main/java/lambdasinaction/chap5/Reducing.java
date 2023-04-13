package lambdasinaction.chap5;

import lambdasinaction.bean.Dish;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static lambdasinaction.bean.Dish.menu;

public class Reducing {

    public static void main(String... args) {

        List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2, 1, 6, 6, 7);

        System.out.println("求和 -> " + numbers.stream().reduce(0, (a, b) -> a + b));
        System.out.println("求和 -> " + numbers.stream().reduce(0, Integer::sum));

        System.out.println("最大值 -> " + numbers.stream().reduce(0, (a, b) -> Integer.max(a, b)));
        numbers.stream().collect(Collectors.maxBy(Comparator.comparing(t -> t))).ifPresent(v -> System.out.println("最大值 -> " + v));
        numbers.stream().max(Comparator.comparingInt(o -> o)).ifPresent(v -> System.out.println("最大值 -> " + v));

        numbers.stream().reduce(Integer::min).ifPresent(v -> System.out.println("最小值 -> " + v));

        int calories = menu.stream()
                .map(Dish::getCalories)
                .reduce(0, Integer::sum);
        System.out.println("卡路里总量:" + calories);

    }
}
