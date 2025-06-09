package Ordenación;

import java.util.ArrayList;
import java.util.List;

public class Nodo implements Runnable{
    
    private List<Integer> lista;

    public Nodo(List<Integer> lista){
        this.lista = lista;
    }

    public List<Integer> getList(){
        return this.lista;
    }

    public void anaide(List<Integer> l, int desde, int hasta){
        int it = desde;
        while(it < hasta){
            l.add(this.lista.get(it));
            it++;
        }
    }

    public void mezcla(List<Integer> l1, List<Integer> l2){
        List<Integer> aux = new ArrayList<>();
        int l1_size = 0;
        int l2_size = 0;
        while (l1_size < l1.size() || l2_size < l2.size()){
            if(l1_size >= l1.size()){
                aux.add(l2.get(l2_size));
                l2_size++;
            }else if (l2_size >= l2.size()){
                aux.add(l1.get(l1_size));
                l1_size++;
            }else{
                if(l1.get(l1_size) <= l2.get(l2_size)){
                    aux.add(l1.get(l1_size));
                    l1_size++;
                }else{
                    aux.add(l2.get(l2_size));
                    l2_size++;
                }
            }
        }
        this.lista = aux;
    }

    public void run(){
        if(this.lista.size() > 1){
            List<Integer> v1 = new ArrayList<>();
            anaide(v1, 0, lista.size()/2);
            Nodo n1 = new Nodo(v1);
            
            List<Integer> v2 = new ArrayList<>();
            anaide(v2, lista.size()/2, lista.size());
            Nodo n2 = new Nodo(v2);

            Thread h1 = new Thread(n1);
            Thread h2 = new Thread(n2);

            h1.start();
            h2.start();

            try{
                h1.join();
                h2.join();
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            mezcla(n1.getList(), n2.getList());
        }
    }

}
