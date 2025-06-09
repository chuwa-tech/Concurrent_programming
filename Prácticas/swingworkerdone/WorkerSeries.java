package esqueletos;

import javax.swing.*;

public class WorkerSeries extends SwingWorker<Object, Double> {
    private int iteraciones;

    private Panel panel;

    private double resPI = 0.0;

    public WorkerSeries(int iter, Panel panel) {
        this.iteraciones = iter;
        this.panel = panel;
    }

    protected Double doInBackground() {
        int iteración = 0;
        for (int i = 1; i < iteraciones; i += 2) {
            if ((iteración % 2) != 0) {
                resPI -= 4.0 / i;
            } else {
                resPI += 4.0 / i;
            }
            iteración++;
        }
        return resPI;
    }

    public void done(){
        panel.escribePI2(resPI);
    }
}
