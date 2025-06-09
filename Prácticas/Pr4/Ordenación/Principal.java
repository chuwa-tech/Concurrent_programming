package Ordenación;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Principal {
    public static void main(String args[]) throws InterruptedException{
        Random r = new Random();
        List<Integer> lista = new ArrayList<>();
        
        for(int i = 0; i < 10; i++){
            lista.add(r.nextInt(20));
        }

        Nodo n = new Nodo(lista);

        Thread h = new Thread(n);

        h.start();
        h.join();

        System.out.println("La lista ordenada: " + Arrays.toString(n.getList().toArray()));
    }
}
