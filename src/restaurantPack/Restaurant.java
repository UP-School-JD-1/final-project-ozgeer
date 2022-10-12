package restaurantPack;
import entity.Chef;
import entity.Client;
import entity.Table;
import entity.Waiter;
import menu.Food;
import menu.Beverage;
import exceptions.NoOrderException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * Bu class restorana ait tüm metodları ve değişkenleri tutuyor.Metodların zincirleme çağrılması durumu var
 * ve bunların hangi sıra ile çağrılacağı,threadlerin nerede bekleyecekleri boolean tipinde tanımlanılan
 * flag ler ile kontrol edildi.
 * @author ozgee
 * @version ?
 * @since 09.10.2022
 */

public class Restaurant {
    public boolean orderMadeByClient;
    public boolean orderTaken;
    public boolean orderReady;
    public boolean orderReceived;
    public boolean isEmpty;
    /**
     * Restoranın içindeki müşteri sayısı,kuyrukta bekleyenler,masa no,sipariş no ve masa kapasitesi durumlara göre
     * ilgili metodlar içinde artırılıp azaltıldı.
     */
    public long waitingClient;
    public long numberOfClient;
    private long orderId ;
    private long tableId;
    private long tableCapacity;


    /**
     * Kuyrukta bekleyenler queue içinde tutuldu FIFO ile çalıştığından. Remove kullanıldığında en öndeki gitsin diye.
     */
    public static double bill = 0;
    Queue clients=new LinkedList();

    public Restaurant() {
    }

    public long getTableId() {
        return tableId;
    }
    public long getTableCapacity() {
        return tableCapacity;
    }

    /**
     * makeRestaurant metod içinde client,waiter,chef ve table nesneleri for döngüsü içinde oluşturulup threadlere geçildi.
     * Threadleri tek tek oluşturmamak için ExecutorService kullanılarak gerekli olan sayıda thread üretildi.
     * Nesnelerin oluşturulması için Factory method düşünüldüyse de class arasında inheritance yapısı olmadığı için
     * kullanılmadı.
     */

    public void makeRestaurant() {
        // masalar
        ExecutorService table = Executors.newFixedThreadPool(4);
        for (int i = 0; i <4; i++) {
            tableCapacity++;
            Thread threadtable = new Thread(new Table(this));
            table.execute(threadtable);
        }

        ExecutorService client = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 4; i++) {
            clients.add(new Client(this));
            waitingClient++; // singleton yapı kullanılablir. veriye her yerden erişmek için -- getter setter ile de erişim olabilirdi
            Thread threadclient = new Thread(new Client(this));
            client.execute(threadclient);
        }

        ExecutorService waiter = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            Thread threadwaiter = new Thread(new Waiter(this));
            waiter.execute(threadwaiter);
        }

        ExecutorService chefs = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            Thread threadchef = new Thread(new Chef(this));
            chefs.execute(threadchef);
        }
//
//        System.out.println("masalarin sayisi:"+tableCapacity);
//        System.out.println("kuyruktakiler:"+waitingClient);
    }
    /** Table class ile ile ilgili metodlar.
     * @return masa no */
    public synchronized long getTable() {
        while (!isEmpty) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isEmpty = false;
        notifyAll();
        return tableId;
    }
    public synchronized void putTable(long tableId) {
        while (isEmpty) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
            isEmpty = true;
            tableId=(long) (10 * Math.random());
            // orderMadeByClient=true;
            this.tableId = tableId;
            numberOfClient++;
            waitingClient--;
            tableCapacity--;
            clients.remove();
            notifyAll();
            System.out.println("2-Musteri iceri girdi");
        }

    /**
     * Waiter class ile ilişkili metodlar. "orderMadeByClient" flag ile kontrol ediliyor.
     * @return sipariş no
     */
    public synchronized long getOrderMadeByClient() {
        while (!orderMadeByClient) {
            try {
                wait();
//                System.out.println(Thread.currentThread());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        orderMadeByClient = false;
        notifyAll();
        System.out.println("8-Musteriden alinan siparis: #" + orderId );
        return orderId;
    }

    public synchronized void putOrderMadeByClient(long ordered) {
        while (orderMadeByClient) {
            try {
                wait(); // garsonun siparis alısı bekleniyor.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        orderMadeByClient = true;
        orderId = ordered;
        notifyAll();
        System.out.println("5-Musteriden alinan siparis #" + orderId);
    }

    /**
     * Chef class ile ilişkili metodlar."orderTaken" flag ile kontrol ediliyor.
     * @return sipariş no
     */
    public synchronized long getOrder() {
        while (!orderTaken) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        orderTaken = false;
        orderId=tableId;
        notifyAll();
        System.out.println("11-Yemek yukleniyor... #" + orderId);
        return orderId;
    }

    public synchronized void putOrder(long ordered) {
        while (orderTaken) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        orderTaken = true;
        orderId = ordered;
        notifyAll();
        System.out.println("9-Garson siparisi tutuyor #" + orderId);
    }
    /** Garson yemeği alıyor.*/
    public synchronized long getFood() {
        while (!orderReady) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        orderReady = false;
        notifyAll();
        System.out.println("14-Sefin hazirladigi siparis #" + tableId);
        return tableId;
    }
      /** Şef yemeği hazırlıyor*/
    public synchronized void putFood(long ordered) {
        while (orderReady) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        orderReady = true;
        this.orderId = ordered;
        notifyAll();
        System.out.println("12-Sef hazirladigi siparis #" + ordered);
    }

    public synchronized long getOrderReceived() {
        while (!orderReceived) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        orderReceived = false;
        notifyAll();
        System.out.println("17-Garson musteriye yemegi verdi #" + orderId);
        return orderId;
    }

    public synchronized void putOrderReceived(long ordered) {
        while (orderReceived) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        orderReceived = true;
        this.orderId = ordered;
        notifyAll();
        System.out.println("15-Garsonun musteriye verecegi tabak elinde #" + ordered);
    }

    /**
     * Siparişe göre hesabı hesaplıyor. Sipariş girilmemiş ise exception paket içindeki "NoOrderException" fırlatılıyor.
     * @return bill
     */
    public double payBill(Order order) {
        try {

            Food firstfood = order.getFood();
            Food secondfood = order.getSecondfood();
            Beverage beverage = order.getBeverage();

            if (firstfood != null && secondfood != null && beverage != null) // iki yemek bir içecek
                bill = firstfood.price + secondfood.price + beverage.price;

            else if (firstfood != null && secondfood != null) //iki yemek
                bill = firstfood.price + secondfood.price;

            else if (firstfood != null && beverage != null) // bir yemek-bir içecek
                bill = firstfood.price + beverage.price;

            else if (beverage != null) //sadece içecek
                bill += beverage.price;

            else if (firstfood != null) //sadece yiyecek
                bill += firstfood.price;

            else {
                throw new NoOrderException("Siparis yok-Default constructor calisiyor.");
            }
            numberOfClient--;
            tableCapacity++;

        } catch (NoOrderException e) {
            e.printStackTrace();
        }
        return bill;
    }
}






































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































