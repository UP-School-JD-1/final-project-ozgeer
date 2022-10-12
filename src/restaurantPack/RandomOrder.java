package restaurantPack;

import menu.Beverage;
import menu.Food;

public class RandomOrder {
    public Order getRandomOrder() {
        Order order=null;
        int i = (int) (3 * Math.random());

        switch (i) {
            case 0:
                order = new Order(Food.urfaKebabi);
                break;
            case 1:
                order = new Order(Food.adanaKebabi, Food.iskender, Beverage.fanta);
                break;
            case 2:
                order=new Order(Food.icliKofteTabagi, Beverage.ayran);
                break;
            case 3:
                order = new Order(Beverage.narSuyu);
                break;
            case 4:
                order=new Order(Food.lahmacun,Food.sarma,Beverage.salgamSuyu);
                break;
        }
        System.out.println(order);
        return order;
    }

//    public Order getOrderr(Order order2) {
//        order2.toString();
//        return order2;
//    }
}


