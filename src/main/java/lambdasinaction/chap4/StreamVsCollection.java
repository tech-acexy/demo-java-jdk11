package lambdasinaction.chap4;

import java.util.*;
import java.util.stream.*;

import static java.util.stream.Collectors.toList;


public class StreamVsCollection {

    public static void main(String... args) {
        List<String> names = Arrays.asList("Java8", "Lambdas", "In", "Action");
        Stream<String> s = names.stream();

        names.forEach(System.out::println);


        s.forEach(System.out::println);

        // s Stream重复消费触发异常
        s.forEach(System.out::println);

    }
}