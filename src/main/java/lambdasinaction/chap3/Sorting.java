package lambdasinaction.chap3;

import lambdasinaction.bean.Apple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.util.Comparator.comparing;

public class Sorting {

    public static void main(String... args) {
        List<Apple> inventory = new ArrayList<>(Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red")));
        // 通过 Function 接口函数 指定创建Apple的方式
        Function<Integer, Apple> createApple = Apple::new;
        BiFunction<Integer, String, Apple> createApple2 = Apple::new;
        Apple newApple = createApple.apply(125);
        System.out.println(newApple);
        System.out.println(createApple2.apply(123, "black"));

        // A 继承Comparator 实现compare接口完成排序
        System.out.println("通过实现compare完成排序");
        inventory.sort(new AppleComparator());
        System.out.println(inventory);

        // 将第二个苹果的信息进行更新
        inventory.set(1, new Apple(30, "green"));

        // 通过匿名函数完成排序
        inventory.sort(new Comparator<Apple>() {
            public int compare(Apple a2, Apple a1) {
                return a1.getWeight().compareTo(a2.getWeight());
            }
        });
        System.out.println("通过匿名函数实现compare完成排序");
        System.out.println(inventory);

        // 将第二个苹果的信息进行更新
        inventory.set(1, new Apple(20, "red"));

        // 通过lambda表达式精简匿名函数完成排序
        inventory.sort((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));
        System.out.println("通过lambda表达式精简你们函数完成排序");
        System.out.println(inventory);

        // 将第二个苹果的信息进行更新
        inventory.set(1, new Apple(10, "red"));

        // 通过 Comparator.comparing 辅助函数创建 Comparator.compare行为 (将苹果的getWeight做为compareTo的两个参数) 并返回Comparator对象
        // Comparator<Apple> comparator = Comparator.comparing(Apple::getWeight);
        inventory.sort(comparing(Apple::getWeight));
        System.out.println(inventory);
    }

    static class AppleComparator implements Comparator<Apple> {
        public int compare(Apple a1, Apple a2) {
            return a1.getWeight().compareTo(a2.getWeight());
        }
    }
}
