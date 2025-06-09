package esqueletos;

import javax.swing.*;
import java.util.Random;

public class WorkerMontecarlo extends SwingWorker<Object, Double> {

    private int iteraciones;

    private Panel panel;

    private int nPuntosCirculo = 0;

    private double resPI = 0.0;

    public WorkerMontecarlo(int item, Panel panel) {
        this.iteraciones = item;
        this.panel = panel;
    }

    protected Double doInBackground(){
        Random x = new Random();
        Random y = new Random();
        Double r = 1.0; // Radio del círculo concéntrico
        for (int i = 0; i < iteraciones; i++) {
            calculaPuntos(x.nextDouble(r), y.nextDouble(r), r);
        }
        resPI = (4.0 * (nPuntosCirculo)) / (double) iteraciones;
        return resPI;
    }

    public void calculaPuntos(double x, double y, double r) {
        if ((x*x + y*y) <= Math.pow(r, 2)) {
            nPuntosCirculo++;
        }
    }

    public void done(){
        panel.escribePI1(resPI);
    }
}
