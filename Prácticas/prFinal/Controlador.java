package esqueletos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Controlador implements ActionListener, PropertyChangeListener {

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
        panel.setProgresoMonteCarlo(0);
        panel.limpia2();
        panel.setProgresoLeibniz(0);
        if(e.getActionCommand().equals("COMENZAR")){
            try {
                numIteraciones = panel.getIteraciones();

                workerM = new WorkerMontecarlo(numIteraciones, panel);
                workerS = new WorkerSeries(numIteraciones, panel);

                workerS.addPropertyChangeListener(this);
                workerM.addPropertyChangeListener(this);

            }catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Introduce un numero entero");
            }

            workerM.execute();
            workerS.execute();
        }else if(e.getActionCommand().equals("CANCELAR")){
            if(workerM == null && workerS == null){
                JOptionPane.showMessageDialog(panel, "No hay nada que cancelar");
            }else{
                if(workerM.cancel(true)){
                    System.out.println("Cancelado MonteCarlo");
                }else{
                    System.out.println("No se pudieron cancelar las operaciones");
                }
                if(workerS.cancel(true)){
                    System.out.println("Cancelado Leibniz");
                }else{
                    System.out.println("No se pudieron cancelar las operaciones");
                }
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("progress")){
            if(evt.getSource() == workerM){
                panel.setProgresoMonteCarlo((int) evt.getNewValue());
            }else if(evt.getSource() == workerS){
                panel.setProgresoLeibniz((int) evt.getNewValue());
            }
        }
    }
}
