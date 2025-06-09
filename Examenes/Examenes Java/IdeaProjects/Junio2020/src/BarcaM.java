import java.util.ArrayDeque;
import java.util.Queue;

public class BarcaM {

    // Controlo la gente que este esperando en las orillas
    Queue<Integer> colaEsperaOrilla0 = new ArrayDeque<>();
    Queue<Integer> colaEsperaOrilla1 = new ArrayDeque<>();

    boolean puedoBajar = false;
    boolean puedeIniciarViaje = false;
    boolean viajeTerminado = false;
    boolean lleno = false;
    private int nPasajeros = 0;
    private int orilla;


    public synchronized void subir(int id,int pos) throws InterruptedException{
        if(pos == 0){
            colaEsperaOrilla0.add(id);
            while (lleno || (!colaEsperaOrilla0.isEmpty() && colaEsperaOrilla0.peek() != id) || orilla == 1){
                wait();
            }
            colaEsperaOrilla0.remove();
        }else{
            colaEsperaOrilla1.add(id);
            while (lleno || (!colaEsperaOrilla1.isEmpty() && colaEsperaOrilla1.peek() != id) || orilla == 0){
                wait();
            }
            colaEsperaOrilla1.remove();
        }
        nPasajeros++;
        System.out.println("Pasajero " + id + " sube de la orilla " + pos + ". Hay " + nPasajeros + " pasajeros en la barca.");
        if(nPasajeros == 3){
            lleno = true;
            puedeIniciarViaje = true;
        }
        notifyAll();
    }

    /*
     * Cuando el viaje ha terminado, el Pasajero que esta en la barca se baja
     */
    public synchronized int bajar(int id) throws InterruptedException{
        while(!puedoBajar){
            wait();
        }
        nPasajeros--;
        System.out.println("Pasajero " + id + " ha bajado de la barca. Quedan " + nPasajeros + " pasajeros en la barca");
        if(nPasajeros == 0){
            puedoBajar = false;
            lleno = false;
            orilla = (orilla + 1) % 2;
        }
        notifyAll();
        return orilla;
    }
    /*
     * El Capitan espera hasta que se suben 3 pasajeros para comenzar el viaje
     */
    public synchronized void esperoSuban() throws InterruptedException{
        while(!puedeIniciarViaje){
            wait();
        }
        System.out.println("El Capitan ha comenzado el viaje");
        puedeIniciarViaje = false;
        viajeTerminado = true;
        notifyAll();
    }

    /*
     * El Capitan indica a los pasajeros que el viaje ha terminado y tienen que bajarse
     */
    public synchronized void finViaje() throws InterruptedException{
        while(!viajeTerminado){
            wait();
        }
        System.out.println("El Capitan ha terminado el viaje");
        puedoBajar = true;
        viajeTerminado = false;
        notifyAll();
    }
}
