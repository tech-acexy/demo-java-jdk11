package lambdasinaction.chap10;

import lambdasinaction.bean.Apple;

import java.util.*;

import static java.util.Optional.of;
import static java.util.Optional.empty;

public class OperationsWithOptional {

    public static void main(String... args) {

        List<Apple> apples = null;
        System.out.println(Optional.ofNullable(apples).map(Object::toString).orElse("null"));
        apples = new ArrayList<>();
        apples.add(new Apple(130, "red"));
        apples.add(new Apple(100, "green"));
        System.out.println(Optional.ofNullable(apples).map(Object::toString).orElse("null"));

        System.out.println(max(of(3), of(5)));
        System.out.println(max(empty(), of(5)));

        Optional<Integer> opt1 = of(5);
        Optional<Integer> opt2 = opt1.or(() -> of(4));

        System.out.println(
                of(5).or(() -> of(4))
        );


        Boolean flag = true;

        System.out.println(Optional.ofNullable(flag).orElse(false));
    }

    public static final Optional<Integer> max(Optional<Integer> i, Optional<Integer> j) {
        return i.flatMap(a -> j.map(b -> Math.max(a, b)));
    }
}
