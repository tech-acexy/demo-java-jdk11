package lambdasinaction.chap5;

import lambdasinaction.bean.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static lambdasinaction.bean.Dish.menu;

public class Mapping {

    public static void main(String... args) {

        System.out.println(menu);

        // 找出是素食的所有菜单名
        List<String> dishNames = menu.stream()
                .filter(Dish::isVegetarian)
                .map(Dish::getName)
                .collect(toList());
        System.out.println(dishNames);

        // 计算hello world各有多少个单词 返回 list
        List<String> words = Arrays.asList("Hello", "World", "!");
        List<Integer> wordLengths = words.stream()
                .map(String::length)
                .collect(toList());
        System.out.println(wordLengths);

        // 计算hello world各有多少个单词 返回 map
        Map<String, Integer> rs = words.stream().collect(toMap(s -> s, String::length));
        System.out.println(rs);

        // flatMap
        words.stream()
                .flatMap((String line) -> Arrays.stream(line.split("")))
                .distinct()
                .map(s -> s + ",")
                .forEach(System.out::print);

        System.out.println();

        words.stream().flatMap(String::lines).forEach(System.out::println);


        // flatMap
        List<Integer> numbers1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> numbers2 = Arrays.asList(6, 7, 8);
        List<int[]> pairs =
                numbers1.stream()
                        .flatMap((Integer i) -> numbers2.stream()
                                .map((Integer j) -> new int[]{i, j})
                        )
                        .filter(pair -> (pair[0] + pair[1]) % 3 == 0)
                        .collect(toList());
        pairs.forEach(pair -> System.out.println("(" + pair[0] + ", " + pair[1] + ")"));
    }

}
