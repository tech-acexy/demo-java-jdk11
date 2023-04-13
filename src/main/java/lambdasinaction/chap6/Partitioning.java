package lambdasinaction.chap6;

import com.thankjava.toolkit3d.core.fastjson.FastJson;
import lambdasinaction.bean.Dish;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;
import static lambdasinaction.bean.Dish.menu;

public class Partitioning {

    public static void main(String... args) {

        // 计算menu集合的大小
        System.out.println(menu.stream().collect(counting()));
        System.out.println(menu.stream().collect(Collectors.counting()));
        System.out.println(menu.stream().count());
        System.out.println(menu.size());

        // partitioningBy(Predicate) 将数据按照指定谓词操作进行true/false进行分区 返回 Map<Boolean,List<T>>
        System.out.println("按照是否为素食分区数据:\n" + FastJson.toFormatJSONString(menu.stream().collect(partitioningBy(Dish::isVegetarian))));

        System.out.println("将菜品按照所属类型分类并分区是否为素食:\n" + FastJson.toFormatJSONString(menu.stream().collect(
                partitioningBy(
                        Dish::isVegetarian, // 分区的健对应的值
                        groupingBy(Dish::getType) // 将menu 先通过type进行分组
                )
        )));

        // 由于当前数据里面基本类型和是否是素食是对应的，分区的键值(是否为素食)刚好囊括了一类
        // 添加一个特殊的菜品，让他破坏这个结构，让他既是素食，但是又不处于全是素食的某一种类型
        var newMenu = new ArrayList<>(menu);
        newMenu.add(new Dish("special", true, 10, Dish.Type.MEAT));
        System.out.println("添加特殊菜品后将菜品按照所属类型分类并分区是否为素食:\n" + FastJson.toFormatJSONString(newMenu.stream().collect(
                partitioningBy(
                        Dish::isVegetarian, // 分区的健对应的值
                        groupingBy(Dish::getType) // 将menu 先通过type进行分组
                )
        )));


        Comparator<Dish> comparator = comparingInt(Dish::getCalories);
        System.out.println("热量最高的 " + menu.stream().collect(maxBy(comparator)).orElse(null));
        System.out.println("热量最高的 " + menu.stream().max(comparator).orElse(null));
        System.out.println("热量最高的 " + menu.stream().collect(reducing((dish1, dish2) -> dish1.getCalories() > dish2.getCalories() ? dish1 : dish2)).orElse(null));
        System.out.println();

        System.out.println("素食/肉食中热量最高的: " + menu.stream().collect(
                partitioningBy(
                        Dish::isVegetarian,
                        collectingAndThen(
                                maxBy(comparingInt(Dish::getCalories)),
                                Optional::get))));

        System.out.println("菜单总热量 " + menu.stream().mapToInt(Dish::getCalories).sum());
        System.out.println("菜单总热量 " + menu.stream().collect(summingInt(Dish::getCalories)));

        //                               menu.parallelStream() 使用并行流 触发 reduce 的 combiner参数函数执行，结果不一样(在combiner中做了减10操作)
        System.out.println("菜单总热量 " + menu.stream().reduce(0, new BiFunction<Integer, Dish, Integer>() {
            @Override
            public Integer apply(Integer identity, Dish dish) {
                return identity + dish.getCalories(); // 初始值与dish之间的处理
            }
        }, new BinaryOperator<Integer>() { // combiner仅在并行流中运行，用于指定处理并行结果为最终结果
            @Override
            public Integer apply(Integer intA, Integer intB) {
                return intA + intB - 10;
            }
        }));
        System.out.println("菜单总热量 " + menu.stream().collect(reducing(0, Dish::getCalories, (a, b) -> a + b)));


        System.out.println("菜单平均热量 " + menu.stream().mapToInt(Dish::getCalories).average().orElse(-1));
        System.out.println("菜单平均热量 " + menu.stream().collect(averagingDouble(Dish::getCalories)));

        System.out.println("菜单热量常用统计 " + menu.stream().collect(summarizingDouble(Dish::getCalories)));

        System.out.println("所有菜名 " + menu.stream().map(Dish::getName).collect(joining(",")));
    }
}

