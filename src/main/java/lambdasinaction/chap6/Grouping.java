package lambdasinaction.chap6;

import com.thankjava.toolkit3d.core.fastjson.FastJson;
import lambdasinaction.bean.Dish;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static lambdasinaction.bean.Dish.dishTags;
import static lambdasinaction.bean.Dish.menu;

public class Grouping {

    enum CaloricLevel {DIET, NORMAL, FAT}


    public static void main(String... args) {

        System.out.println("将菜品按照类型分组: " + FastJson.toFormatJSONString(groupDishesByType()));
        System.out.println("将菜品名称按照类型分组: " + FastJson.toFormatJSONString(groupDishNamesByType()));
        System.out.println("将菜品按照类型分组后，每个分类有哪些标签: " + FastJson.toFormatJSONString(groupDishTagsByType()));
        System.out.println("将菜品按照类型分组后，每种类型有哪些菜品名和对应的菜品标签: " + FastJson.toFormatJSONString(groupDishTagsAndNameByType()));

        System.out.println("Caloric dishes grouped by type: " + groupCaloricDishesByType());
        System.out.println("Dishes grouped by caloric level: " + groupDishesByCaloricLevel());
        System.out.println("Dishes grouped by type and caloric level: " + groupDishedByTypeAndCaloricLevel());
        System.out.println("Count dishes in groups: " + countDishesInGroups());
        System.out.println("Most caloric dishes by type: " + mostCaloricDishesByType());
        System.out.println("Most caloric dishes by type: " + mostCaloricDishesByTypeWithoutOprionals());
        System.out.println("Sum calories by type: " + sumCaloriesByType());
        System.out.println("Caloric levels by type: " + caloricLevelsByType());
    }

    private static Map<Dish.Type, List<Dish>> groupDishesByType() {
        return menu.stream().collect(groupingBy(Dish::getType));
    }

    private static Map<Dish.Type, List<String>> groupDishNamesByType() {
        // 先执行分类，再执行归约收集
        return menu.stream().collect(groupingBy(Dish::getType, mapping(Dish::getName, Collectors.toList())));
    }

    private static Map<Dish.Type, Set<String>> groupDishTagsByType() {
        // 先按照类型分类，再通过对应的菜单获取相关联的标签 (一个菜品可能有多个标签)
        return menu.stream().collect(
                groupingBy(Dish::getType, flatMapping(dish -> dishTags.get(dish.getName()).stream(), toSet())));
    }

    private static Map<Dish.Type, Map<String, List<String>>> groupDishTagsAndNameByType() {
        return menu.stream().collect(
                groupingBy(
                        Dish::getType,
                        mapping(
                                dish -> dish,
                                toMap(
                                        Dish::getName,
                                        dish -> dishTags.get(dish.getName())
                                )
                        )
                )
        );
    }


    private static Map<Dish.Type, List<Dish>> groupCaloricDishesByType() {
        return menu.stream().filter(dish -> dish.getCalories() > 500).collect(groupingBy(Dish::getType));
//        return menu.stream().collect(groupingBy(Dish::getType, filtering(dish -> dish.getCalories() > 500, toList())));
    }

    private static Map<CaloricLevel, List<Dish>> groupDishesByCaloricLevel() {
        return menu.stream().collect(
                groupingBy(dish -> {
                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                    else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                }));
    }

    private static Map<Dish.Type, Map<CaloricLevel, List<Dish>>> groupDishedByTypeAndCaloricLevel() {
        return menu.stream().collect(
                groupingBy(Dish::getType,
                        groupingBy((Dish dish) -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        })
                )
        );
    }

    private static Map<Dish.Type, Long> countDishesInGroups() {
        return menu.stream().collect(groupingBy(Dish::getType, counting()));
    }

    private static Map<Dish.Type, Optional<Dish>> mostCaloricDishesByType() {
        return menu.stream().collect(
                groupingBy(Dish::getType,
                        reducing((Dish d1, Dish d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)));
    }

    private static Map<Dish.Type, Dish> mostCaloricDishesByTypeWithoutOprionals() {
        return menu.stream().collect(
                groupingBy(Dish::getType,
                        collectingAndThen(
                                reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2),
                                Optional::get)));
    }

    private static Map<Dish.Type, Integer> sumCaloriesByType() {
        return menu.stream().collect(groupingBy(Dish::getType,
                summingInt(Dish::getCalories)));
    }

    private static Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType() {
        return menu.stream().collect(
                groupingBy(Dish::getType, mapping(
                        dish -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        },
                        toSet())));
    }
}
