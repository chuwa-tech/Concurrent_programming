package impresoras;

import java.util.*;

public class SalaImpresorasML implements SalaImpresoras{
    // Una cola para guardar los clientes que quieren imprimir
    Queue<Integer> colaEspera = new ArrayDeque<>();

    // ¿Están todas ocupadas?
    boolean todasOcupadas = false;
    // Número de impresoras totales
    private int nImpresoras;
    // Número de impresoras ocupadas
    private int nOcupadas = 0;
    // Id impresora que está siendo utilizada
    private int idImpresora;

    // Lista de ids libres
    List<Integer> idsLibres = new LinkedList<>(Arrays.asList(0,1,2));
    public SalaImpresorasML(int N){
        nImpresoras = N;
    }

    // Monitores
    @Override
    public synchronized int quieroImpresora(int id) throws InterruptedException {
        colaEspera.add(id);
        System.out.println("El cliente " + id + " quiere imprimir");
        while(todasOcupadas || (!colaEspera.isEmpty() && colaEspera.peek() != id)) wait();
        colaEspera.remove();
        // Una vez eliminada la cabeza de la cola
        // incrementamos el número de impresoras ocupadas
        nOcupadas++;
        System.out.println("        El cliente " + id + " ha cogido la impresora " + idsLibres.get(0) + ". Hay " + nOcupadas + " impresoras ocupadas");
        // Espero a que esten todas ocupadas? El enunciado no dice nada, entonces seguimos
        if(nOcupadas == nImpresoras) todasOcupadas = true;
        idImpresora = idsLibres.get(0);
        idsLibres.remove(0);
        notifyAll();
        return idImpresora;
    }

    @Override
    public synchronized void devuelvoImpresora(int id, int n) throws InterruptedException {
        idsLibres.add(n);
        nOcupadas--;
        todasOcupadas = false;
        System.out.println("        El cliente " + id + " ha dejado la impresora. Hay " + nOcupadas + " impresoras ocupadas");
        notifyAll();
    }
}
