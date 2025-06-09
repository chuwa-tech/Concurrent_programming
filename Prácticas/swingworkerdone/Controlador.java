package esqueletos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controlador implements ActionListener {

    private Panel panel;

    int numIteraciones;
    WorkerMontecarlo workerM;
    WorkerSeries workerS;

    public Controlador(Panel panel) {
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        panel.limpia1();
        panel.limpia2();
        if(e.getActionCommand().equals("COMENZAR")){
            try {
                numIteraciones = panel.getIteraciones();
                workerM = new WorkerMontecarlo(numIteraciones, panel);
                workerS = new WorkerSeries(numIteraciones, panel);
            }catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Introduce un numero entero");
            }

            workerM.execute();
            workerS.execute();
        }
    }

}
