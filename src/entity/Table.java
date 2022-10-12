package entity;
import exceptions.NotRunningTable;
import restaurantPack.Restaurant;

/** Masa threadleri sırası ile buraya gelip sitDown() u run() içinde çağırıyorlar. sitDown() hiç çalışmazsa
 * exception paket içindeki "NoRunninTable" exception fırlatılıyor.
 * @author ozgee
 * @since 09.10.2022
 */
public class Table implements Runnable {

    /** Restaurant class için bir referans tanımlanıyor. sitDown () çalışıp çalışmadığı anlaşılsın diye "variable" değişkeni
     * kullanılıyor.*/
    private Restaurant restaurant;
    private int variable;

    public Table(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void sitDown() throws InterruptedException {
        synchronized (restaurant) {
            restaurant.notifyAll();
            System.out.println("1-musteri geldi");
            Thread.sleep(100);
            restaurant.putTable(restaurant.getTableId());
            System.out.println("3-musteri masaya yerlesti #"+restaurant.getTableId());
            restaurant.numberOfClient++;
            variable++;
       }
    }

    public void run() {
        try {
            sitDown();
            if(variable==0){
                throw new NotRunningTable("Restaurant bugun kapali.");
            }
        }
        catch (NotRunningTable exception){
            exception.printStackTrace();
            exception.getMessage();
        }
        catch(InterruptedException exception){
            exception.printStackTrace();
        }
    }
}
