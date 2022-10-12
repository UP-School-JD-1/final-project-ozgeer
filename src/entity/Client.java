package entity;
import exceptions.NotRunningClient;
import restaurantPack.Order;
import restaurantPack.RandomOrder;
import restaurantPack.Restaurant;

    /**Müşteri threadleri sırası ile buraya gelip run () içinde makeOrder() u çağırıyor. makeOrder() çalışmazsa
     * exception paket içinde bulunan "NoRunningClient" exception fırlatılıyor.
    * @author ozgee
    * @since 09.10.2022
    */

public class Client implements Runnable {
    /** Restaurant class için bir referans tanımlanıyor. makeOrder() çalışıp çalışmadığı anlaşılsın diye variable değişkeni
     * kullanılıyor.Siparişlerin ekrana yazdırılması için Order ın toString() ine erişim için order referans tanımlanıyor.*/
    private Restaurant restaurant;
    private int variable;
    Order order;

    RandomOrder randomOrder=new RandomOrder();

    public Client(Restaurant restaurant) {
        this.restaurant=restaurant;
    }

    public void makeOrder() throws InterruptedException {
        synchronized (restaurant) {
            while (restaurant.numberOfClient<=restaurant.getTableCapacity()) {
                System.out.println("4-Musteri siparis vermeye baslar.");
                Thread.sleep(200);
                restaurant.putOrderMadeByClient(restaurant.getTable());
                randomOrder.getRandomOrder();
                System.out.println("6-Siparis verme olayi bitti ");
                while (!restaurant.orderReceived)
                    restaurant.wait();
                restaurant.getOrderReceived();
                Thread.sleep(1000);
                restaurant.payBill(randomOrder.getRandomOrder());
                System.out.println("Hesap:" + Restaurant.bill);
                variable++;
            }
        }
    }
    public Order getOrderr() {
        order.toString();
        return order;
    }
    /** */
    public void run() {
        try {
            makeOrder();
            if(variable==0){
                throw new NotRunningClient("Lokanta boykot ediliyor.");
            }
        }
        catch(NotRunningClient exception){
            exception.getMessage();
            exception.printStackTrace();
        }
        catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}