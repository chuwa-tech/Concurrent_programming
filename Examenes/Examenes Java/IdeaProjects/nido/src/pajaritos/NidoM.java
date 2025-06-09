package pajaritos;

import java.util.concurrent.*;

public class NidoM {
    private int B = 10; // Número máximo de bichos
    private int bichitos; // puede tener de 0 a B bichitos

    public synchronized void come(int id) throws InterruptedException{
        while(bichitos == 0) wait();
        bichitos--;
        System.out.println("El bebé " + id + " ha comido un bichito. Quedan " + bichitos);
        if(bichitos == 9) notifyAll();
    }

    public synchronized void nuevoBichito(int id) throws InterruptedException {
        while(bichitos == B) wait();
        bichitos++;
        System.out.println("El papá " + id + " ha añadido un bichito. Hay " + bichitos);
        if(bichitos == 1) notifyAll();
    }
}