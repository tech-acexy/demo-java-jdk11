package lambdasinaction.chap1;

import lambdasinaction.bean.Apple;

import java.util.*;
import java.util.function.Predicate;

public class FilteringApples {

    static final CreateApple<Integer, String, Apple> creator = Apple::new;

    public static void main(String... args) {

        // Arrays.asList => ArrayList
        List<Apple> inventory = Arrays.asList(
                new Apple(80, "green"),
                new Apple(155, "green"),
                new Apple(120, "red"),
                creator.apply(100, "yellow"),
                creator.apply(130, "green")
        );


        // 原始形式迭代遍历判断
        System.out.println("原始形式迭代查找结果 :" + filterGreenApples(inventory));

        // 谓词形式
        List<Apple> greenApples = filterApples(inventory, new Predicate<Apple>() {
            @Override
            public boolean test(Apple apple) {
                return apple.getColor().equals("green");
            }
        });
        System.out.println(greenApples);

        // 谓词形式 + Lambda 匿名函数表达式
        List<Apple> greenApples2 = filterApples(inventory, (Apple a) -> "green".equals(a.getColor()));
        System.out.println(greenApples2);

        // 原始形式遍历判断
        System.out.println("原始形式迭代查找结果 :" + filterHeavyApples(inventory));

        // 谓词形式 + 函数
        List<Apple> heavyApples = filterApples(inventory, FilteringApples::isHeavyApple);
        System.out.println(heavyApples);

        // 谓词形式
        heavyApples = filterApples(inventory, new Predicate<Apple>() {
            @Override
            public boolean test(Apple apple) {
                return apple.getWeight() > 150;
            }
        });
        System.out.println(heavyApples);


        // 谓词形式 + Lambda 匿名函数表达式
        List<Apple> heavyApples2 = filterApples(inventory, (Apple a) -> a.getWeight() > 150);
        System.out.println(heavyApples2);

        // 查找小于130或者为红色
        List<Apple> wantedApples = filterApples(inventory, (Apple a) -> a.getWeight() < 130 || "green".equals(a.getColor()));
        System.out.println(wantedApples);


        Predicate<Apple> greenApple = (a) -> a.getColor().equals("green");
        System.out.println("绿色苹果 " + filterApples(inventory, greenApple));

        Predicate<Apple> notGreenApple = greenApple.negate();
        System.out.println("非绿色苹果 " + filterApples(inventory, notGreenApple));

        // 绿/黄或者重量大于100的苹果
        Predicate<Apple> p = greenApple.or(a -> a.getWeight() > 100).or(a -> a.getColor().equals("yellow"));
        System.out.println("绿/黄或者重量大于100的苹果 " + filterApples(inventory, p));
    }

    @FunctionalInterface
    public interface CreateApple<H, C, R> {
        R apply(H h, C c);

    }

    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if ("green".equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterHeavyApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getWeight() > 150) {
                result.add(apple);
            }
        }
        return result;
    }

    public static boolean isGreenApple(Apple apple) {
        return "green".equals(apple.getColor());
    }

    public static boolean isHeavyApple(Apple apple) {
        return apple.getWeight() > 150;
    }

    public static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }


}
