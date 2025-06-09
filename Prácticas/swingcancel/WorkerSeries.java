package esqueletos;

import javax.swing.*;
import java.util.List;

public class WorkerSeries extends SwingWorker<Void, Double> {
    private int iteraciones;

    private Panel panel;

    private double resPI = 0.0;

    public WorkerSeries(int iter, Panel panel) {
        this.iteraciones = iter;
        this.panel = panel;
    }

    protected Void doInBackground() {
        try {
            Thread.sleep(1000);
            int iteración = 0;
            for (int i = 1; i < iteraciones && !isCancelled(); i += 2) {
                if ((iteración % 2) != 0) {
                    resPI -= 4.0 / i;
                } else {
                    resPI += 4.0 / i;
                }
                this.setProgress(((i)*100 / iteraciones));
                publish(resPI);
                iteración++;
                Thread.sleep(100);
            }
            this.setProgress(100);
        }catch(InterruptedException e1){
            e1.printStackTrace();
        }
        return null;
    }

    protected void process(List<Double> chunks) {
        if(!isCancelled()){
            panel.escribePI2(chunks.get(chunks.size()-1));
        }
    }
}
