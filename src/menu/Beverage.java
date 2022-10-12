package menu;

public class Beverage extends Menu {

    public Beverage(){

    }

    public Beverage(String name,double price){
        super(name,price);
    }

    public static Beverage ayran=new Beverage("Ayran",10);
    public static Beverage salgamSuyu=new Beverage("Salgam Suyu",10);
    public static Beverage kola=new Beverage("Kola",15);
    public static Beverage fanta=new Beverage("Fanta",15);
    public static Beverage sprite=new Beverage("Sprite",15);
    public static Beverage narSuyu=new Beverage("Nar Suyu",25);


}
