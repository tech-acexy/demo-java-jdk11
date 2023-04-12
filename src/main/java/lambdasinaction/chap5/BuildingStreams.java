package lambdasinaction.chap5;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.stream.*;
import java.nio.charset.Charset;
import java.nio.file.*;

public class BuildingStreams {

    private static final AtomicInteger atomicInteger = new AtomicInteger(0);

    public static int getThreadSafetyIncrement() {
        return atomicInteger.getAndIncrement();
    }


    public static void main(String... args) throws Exception {

        // 显示生成流
        Stream<String> stream = Stream.of("Java 8 ", "Lambdas ", "In ", "Action ");
        // 流中的元素分别大写输出
        stream.map(String::toUpperCase).forEach(System.out::print);
        System.out.println();

        // 空的字符串流
        Stream<String> emptyStream = Stream.empty();
        emptyStream.forEach(System.out::println);

        // Arrays.stream 静态方法从Array中创建流
        int[] numbers = {2, 3, 5, 7, 11, 13};
        // 将数组中值大于5的全部相加
        System.out.println("将数组中值大于5的全部相加 " + Arrays.stream(numbers).filter(value -> value > 5).sum());

        // Stream.iterate （起始值,在起始值上的操作函数） 静态构建 可以生成无限流 (应当使用limit来加以限制)
        Stream.iterate(0, n -> n + 2)
                .limit(10)
                .forEach(v -> System.out.print(v + ","));
        System.out.println();

        // 生成10组斐波那契数列
        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .limit(10)
                .forEach(t -> System.out.print("(" + t[0] + ", " + t[1] + "),"));
        System.out.println();

        // 出去前10个数组的第一个元素
        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .limit(10)
                .map(t -> t[0])
                .forEach(v -> System.out.print(v + ","));
        System.out.println();

        // generate会无效触发指定的生成器规则得到无限的无序流
        DoubleStream.generate(Math::random).limit(10).forEach(v -> System.out.print(v + ","));

        //  生成恒定的值
        IntStream.generate(() -> 1).limit(5).forEach(v -> System.out.print(v + ","));
        System.out.println();

        IntStream.generate(new IntSupplier() {
            public int getAsInt() {
                return 2;
            }
        }).limit(5).forEach(v -> System.out.print(v + ","));
        System.out.println();


        IntSupplier threadUnSafetyIncrement = new IntSupplier() {
            private int number = 0;

            public int getAsInt() {
                return number++;
            }
        };

        System.out.print("非线程安全且带有状态的元素创建者: ");
        IntStream.generate(threadUnSafetyIncrement).limit(10).forEach(v -> System.out.print(v + ","));
        System.out.println();

        System.out.print("线程安全且带有状态的元素创建者: ");
        Stream.generate(BuildingStreams::getThreadSafetyIncrement).limit(10).forEach(v -> System.out.print(v + ","));
        System.out.println();


        long uniqueWords = Files.lines(Paths.get("src/main/resources/lambdasinaction/chap5/data.txt"), Charset.defaultCharset())
                .flatMap(line -> Arrays.stream(line.split(" ")))
                .distinct()
                .count();

        System.out.println("There are " + uniqueWords + " unique words in data.txt");
    }
}
