package restaurantPack;

import entity.Client;
import menu.*;

public class Order {
    Food food;
    Food secondfood;
    Beverage beverage;
    Client client;

    public Order(Food food){
        this.food=food;
    }

    public Order(Beverage beverage){
        this.beverage=beverage;
    }

    public Order(Food food, Food secondfood){
        this.food=food;
        this.secondfood=secondfood;
    }

    public Order(Food food, Beverage beverage){
        this.food=food;
        this.beverage=beverage;
    }

    public Order(Food food, Food secondfood, Beverage beverage){
        this.food=food;
        this.secondfood=secondfood;
        this.beverage=beverage;
    }


    public String toString(){
        if (getFood()==null && getSecondfood()==null){
            return "Siparisler:\n"+beverage.name;
        }
        else if(secondfood==null && beverage==null){
            return "Siparisler:\n"+food.name;
        }
        else if(beverage==null){
            return "Siparisler:\n"+food.name+"\n"+secondfood.name;
        }
        else if(secondfood==null){
            return "Siparisler:\n"+food.name+"\n"+beverage.name;
        }
        else
        return "Siparisler:\n"+food.name+"\n"+secondfood.name+"\n"+beverage.name;
    }

    public Food getFood(){
        return food;
    }
    public Food getSecondfood(){
        return secondfood;
    }
    public Beverage getBeverage(){
        return beverage;
    }



}
