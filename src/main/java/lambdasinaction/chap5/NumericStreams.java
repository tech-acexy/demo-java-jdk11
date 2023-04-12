package lambdasinaction.chap5;

import lambdasinaction.bean.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static lambdasinaction.bean.Dish.menu;

public class NumericStreams {

    public static void main(String... args) {

        // 遍历 List 元素
        List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);
        Arrays.stream(numbers.toArray()).forEach(v -> System.out.print(v + ","));
        System.out.println();

        System.out.println("计算所有菜品卡路里总和: " + menu.stream()
                .mapToInt(Dish::getCalories)
                .sum());

        // 尝试获取菜单中最大卡路里
        System.out.println("菜品中最大卡路里: " + menu.stream()
                .mapToInt(Dish::getCalories)
                .max().orElse(-1));

        // 找出 [1,100] 之间能被2整除的数(偶数)  IntStream.range(a,b) 含前不含后 [a,b)
        IntStream evenNumbers = IntStream.rangeClosed(1, 100).filter(n -> n % 2 == 0);
        evenNumbers.forEach(v -> System.out.print(v + ","));
        System.out.println();


        // 勾股定理计算， 三角形两个边a b分别在[1,100)]区间，求该情况下的所有满足勾股定理的三角形三个边的长度(c边长为整数)

        System.out.println("plan A");
        // A. 组合所有的可能，判断是否正确，将正确的数据整合成需要的数据
        IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(a ->  // a边 1-100
                        IntStream.rangeClosed(a, 100) // 为了防止 3 4 5, 4 3 5 其实是一种三角形， b 边长的起始值要 >= a边长
                                .filter(b -> {
                                    // a * a + b * b = c * c
                                    // c = 平方根下 a * a + b * b
                                    var c = Math.sqrt(a * a + b * b);
                                    return Math.round(c) == c; // 判断开方后的值为整数才符合
                                })
                                .mapToObj(b -> new Integer[]{a, b, (int) Math.sqrt(a * a + b * b)})
                ).forEach(v -> System.out.println("a=" + v[0] + " b=" + v[1] + " c=" + v[2]));


        System.out.println("plan B");
        // B. 将所有的可能组合成可能需要的结果，过滤出正确的结果
        IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100).boxed().map(b -> new Double[]{Double.valueOf(a), Double.valueOf(b), Math.sqrt(a * a + b * b)})).filter(v -> v[2] % 1 == 0)
                .forEach(v -> System.out.println("a=" + v[0] + " b=" + v[1] + " c=" + v[2]));
    }

}
