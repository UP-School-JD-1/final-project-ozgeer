package menu;

public class Food extends Menu {

    public Food(){

    }
    public Food(String name, double price){
       super(name,price);
    }

    public static Food sarma=new Food("Sarma",10);
    public static Food cokertmeKebabi=new Food("Kebap",30);
    public static Food urfaKebabi=new Food("Urfa Kebabi",70);
    public static Food adanaKebabi=new Food("Adana Kebabi",70);
    public static Food icliKofteTabagi=new Food("Icli kofte tabagi",35);
    public static Food iskender=new Food("Iskender",65);
    public static Food lahmacun=new Food("Lahmacun",18);


}
