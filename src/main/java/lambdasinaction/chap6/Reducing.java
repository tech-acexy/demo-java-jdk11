package lambdasinaction.chap6;

import lambdasinaction.bean.Dish;

import static java.util.stream.Collectors.reducing;
import static lambdasinaction.bean.Dish.menu;

public class Reducing {

    public static void main(String... args) {
        System.out.println("计算菜单总卡路里: "
                + calculateTotalCalories1() + " "
                + calculateTotalCalories2() + " "
                + calculateTotalCalories3() + " "
                + calculateTotalCalories4() + " "
                + calculateTotalCalories5() + " "
                + calculateTotalCalories6());
    }

    private static int calculateTotalCalories1() {
        return menu.stream().map(Dish::getCalories).reduce(0, Integer::sum);
    }

    private static int calculateTotalCalories2() {
        return menu.stream().mapToInt(Dish::getCalories).sum();
    }

    private static int calculateTotalCalories3() {
        return menu.stream().mapToInt(Dish::getCalories).reduce(Math::max).orElse(0);
    }

    private static int calculateTotalCalories4() {
        return menu.stream().collect(reducing(0, Dish::getCalories, (Integer i, Integer j) -> i + j));
    }

    private static int calculateTotalCalories5() {
        return menu.stream().collect(reducing(0, Dish::getCalories, Integer::sum));
    }

    private static int calculateTotalCalories6() {
        return menu.stream().map(Dish::getCalories).reduce(Integer::sum).get();
    }

}