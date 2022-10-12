package entity;
import exceptions.NotRunningWaiter;
import restaurantPack.Restaurant;

    /** Waiter threadleri sırası ile buraya gelip run() çalıştırıyorlar. makeServe(), run() içinde çağrılıyor.
        *  makeServe() hiç çalışmazsa exception paket içindeki "NoRunningWaiter" exception fırlatılıyor.
        *  * @author ozgee
        *  * @version ?
        *  * @since 09.10.2022
        */
public class Waiter implements Runnable {
    /** Restaurant class için bir referans tanımlanıyor. makeServe() çalışıp çalışmadığı anlaşılsın diye "variable" değişkeni
     * kullanılıyor.*/
    private Restaurant restaurant;
    private int variable;

    public Waiter(Restaurant restaurant) {
        this.restaurant=restaurant;
    }

    public void makeServe() throws InterruptedException {
        synchronized (restaurant) {
       while(!restaurant.orderMadeByClient)
                restaurant.wait();
            System.out.println("7-GARSON GELDI ");
            Thread.sleep(1000);
            restaurant.putOrder(restaurant.getOrderMadeByClient()); //musterinin verdigi siparisi sefe bildiriyorum.
//            System.out.println("Siparis sefe bildirildi ");
            while(!restaurant.orderReady)  // sefin hazırlaması bekleniyor.
                restaurant.wait();
            restaurant.putOrderReceived(restaurant.getFood());
            System.out.println("16-GARSON GITTI ");
            variable++;
        }
    }

    @Override
    public void run() {
        try {
            makeServe();
            if(variable==0){
                throw new NotRunningWaiter("Garsonlar ise gelmemis");
            }
        }
        catch(NotRunningWaiter exception){
            exception.getMessage();
            exception.printStackTrace();
        }

        catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}