package lambdasinaction.chap5;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PerformanceTesting {

    public static class IntWrapper {

        private final int value;

        public IntWrapper(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private final static Random random = new Random();


    public static void main(String[] args) {

        var currentTms = System.currentTimeMillis();
        var list = Stream.generate(
                        () -> new IntWrapper(random.nextInt()))
                .limit(10000000).collect(Collectors.toList());

        // 此处暗含装箱操作 int -> Integer 会消耗额外性能
        var res = list.stream().map(IntWrapper::getValue).reduce(Integer::sum);
        System.out.println("包含装箱操作 结果: " + res + " 耗时: " + (System.currentTimeMillis() - currentTms) + "ms");

        currentTms = System.currentTimeMillis();
        list.stream().mapToInt(IntWrapper::getValue).sum();
        System.out.println("不含装箱操作 结果: " + res + " 耗时: " + (System.currentTimeMillis() - currentTms) + "ms");
        System.out.println();

        testParallel(10000);
        testParallel(100000);
        testParallel(1000000);
        testParallel(10000000);
        testParallel(100000000);

    }

    private static void testParallel(int listSize) {
        List<IntWrapper> list = Stream.generate(() -> new IntWrapper(random.nextInt())).limit(listSize).collect(Collectors.toList());
        var currentTms = System.currentTimeMillis();
        var res = list.parallelStream().mapToInt(IntWrapper::getValue).sum();
        System.out.println("并行流耗时 (size " + listSize + ") 结果: " + res + " 耗时: " + (System.currentTimeMillis() - currentTms) + "ms");

        currentTms = System.currentTimeMillis();
        res = list.stream().mapToInt(IntWrapper::getValue).sum();
        System.out.println("普通流耗时 (size " + listSize + ") 结果: " + res + " 耗时: " + (System.currentTimeMillis() - currentTms) + "ms");
        System.out.println();
    }
}
