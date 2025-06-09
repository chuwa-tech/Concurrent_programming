package esqueletos;

import javax.swing.*;

public class Principal {

    public static void crearGUI(){
        JFrame ventana = new JFrame("Montecarlo");
        Panel panel = new Panel();
        Controlador ctr = new Controlador(panel);
        panel.setControlador(ctr);
        ventana.setContentPane(panel);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.pack();
        ventana.setVisible(true);
    }

    public static void main(String[] args) {
        crearGUI();
    }
}
