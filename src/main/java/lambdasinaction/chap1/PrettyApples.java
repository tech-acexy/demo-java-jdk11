package lambdasinaction.chap1;

import lambdasinaction.bean.Apple;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class PrettyApples {

    public static class SimplePretty implements IPrettyApples {

        @Override
        public String pretty(Apple apple) {
            return String.format("apple %s", apple.getColor());
        }
    }

    public static class DetailPretty implements IPrettyApples {
        @Override
        public String pretty(Apple apple) {
            return apple.toString();
        }
    }


    public static void doPretty(List<Apple> apples, IPrettyApples prettyApples) {
        apples.forEach(apple -> System.out.println(prettyApples.pretty(apple)));
    }

    public static class SimplePrettyFun implements Consumer<Apple> {
        @Override
        public void accept(Apple apple) {
            System.out.printf("apple %s%n", apple.getColor());
        }
    }

    public static class DetailPrettyFun implements Consumer<Apple> {
        @Override
        public void accept(Apple apple) {
            System.out.printf("apple %s%n", apple.toString());
        }
    }

    public static <T> void pretty(List<T> list, Consumer<T> consumer) {
        list.forEach(consumer);
    }

    public static void main(String[] args) {

        List<Apple> apples = Arrays.asList(
                new Apple(80, "green"),
                new Apple(155, "green"),
                new Apple(120, "red")
        );

        doPretty(apples, new DetailPretty());
        doPretty(apples, new SimplePretty());

        var simple = new SimplePrettyFun();
        var detail = new DetailPrettyFun();

        apples.forEach(detail);
        apples.forEach(simple);

        pretty(apples, detail);
        pretty(apples, simple);
    }

}
