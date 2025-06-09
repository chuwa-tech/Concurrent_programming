package esqueletos;

import javax.swing.*;
import java.util.List;
import java.util.Random;

public class WorkerMontecarlo extends SwingWorker<Void, Double> {

    private int iteraciones;

    private Panel panel;

    private int nPuntosCirculo = 0;

    public WorkerMontecarlo(int item, Panel panel) {
        this.iteraciones = item;
        this.panel = panel;
    }

    protected Void doInBackground(){
        try {
            Thread.sleep(1000);
            Random x = new Random();
            Random y = new Random();
            Double r = 1.0; // Radio del círculo concéntrico
            for (int i = 0; i < iteraciones && !isCancelled(); i++) {
                calculaPuntos(x.nextDouble(r), y.nextDouble(r), r);
                publish((4.0 * (nPuntosCirculo)) / (double) iteraciones);
                this.setProgress((((i + 1)*100) / iteraciones));
                Thread.sleep(100);
            }
        }catch(InterruptedException e1){
            e1.printStackTrace();
        }
        return null;
    }

    public void calculaPuntos(double x, double y, double r) {
        if ((x*x + y*y) <= Math.pow(r, 2)) {
            nPuntosCirculo++;
        }
    }

    protected void process(List<Double> chunks) {
        if(!isCancelled()){
            panel.escribePI1(chunks.get(chunks.size()-1));
        }
    }
}
