package lambdasinaction.chap5;

import lambdasinaction.bean.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static lambdasinaction.bean.Dish.menu;

public class Mapping {

    public static void main(String... args) {

        // 找出是素食的所有菜单名
        List<String> dishNames = menu.stream()
                .filter(Dish::isVegetarian)
                .map(Dish::getName)
                .collect(toList());
        System.out.println("素食菜单: " + dishNames);

        // 计算hello world各有多少个单词 返回 list
        List<String> words = Arrays.asList("hello", "world");
        List<Integer> wordLengths = words.stream()
                .map(String::length)
                .collect(toList());
        System.out.println("单词长度: " + wordLengths);

        // 计算hello world各有多少个单词 返回 map
        Map<String, Integer> rs = words.stream().collect(toMap(s -> s, String::length));
        System.out.println("单词长度: " + rs);

        // 计算hello world有多少个不重复的单词
        System.out.print("不重复的单词: ");
        words.stream()
                .flatMap(s -> Arrays.stream(s.split(""))) // 将hello和world 分别变成每个字母的数组 (["h","e","l","l","o"],["w","o","r","l","d"]) 再通过flatmap展开 ["h","e","l","l","o","w","o","r","l","d"]
                .distinct()
                .map(s -> s + ",")
                .forEach(System.out::print);
        System.out.println();

        words.stream().flatMap(String::lines).forEach(System.out::print);
        System.out.println();
        words.forEach(System.out::print);
        System.out.println();

        // flatMap numbers1 与 numbers2 每个元素分别配对一次形成新元素
        List<Integer> numbers1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> numbers2 = Arrays.asList(6, 7, 8);

        List<int[]> pairs = numbers1.stream()
                .flatMap((Integer i) -> numbers2.stream().map((Integer j) -> new int[]{i, j}))
//                        .filter(pair -> (pair[0] + pair[1]) % 3 == 0) // 两者相加能被3整除的情况
                .collect(toList());
        pairs.forEach(pair -> System.out.println("(" + pair[0] + ", " + pair[1] + ")"));
    }

}
