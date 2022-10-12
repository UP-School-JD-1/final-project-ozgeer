package entity;
import exceptions.NotRunningChef;
import restaurantPack.Restaurant;

/** Chef threadleri sırası ile buraya gelip run() çalıştırıyorlar. makeFood(), run() içinde çağrılıyor.
 *  makeFood() hiç çalışmazsa exception paket içindeki "NoRunningChef" exception fırlatılıyor.
 *  * @author ozgee
 *  * @version ?
 *  * @since 09.10.2022
 */

public class Chef implements Runnable {
    /** Restaurant class için bir referans tanımlanıyor. makeFood() çalışıp çalışmadığı anlaşılsın diye variable değişkeni
     * kullanılıyor.
     */
    private Restaurant restaurant;
    private int variable;

    public Chef(Restaurant r) {
        this.restaurant=r;
    }

    public void makeFood() throws InterruptedException {
        synchronized (restaurant) {
            restaurant.notifyAll();
            while(!restaurant.orderTaken)
                restaurant.wait();
            System.out.println("10-Sef yemegi hazirlamaya basladi ");
            Thread.sleep(1000);
            restaurant.putFood(restaurant.getOrder());
            System.out.println("13-Yemegin yapimi bitti ");
            variable++;
        }
    }

    @Override
    public void run() {
        try {
            makeFood();
            if(variable==0){
                throw new NotRunningChef("Chefler bugun ise gelmemis.");
            }
        }
        catch (NotRunningChef exception){
            exception.getMessage();
            exception.printStackTrace();
        }
        catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}