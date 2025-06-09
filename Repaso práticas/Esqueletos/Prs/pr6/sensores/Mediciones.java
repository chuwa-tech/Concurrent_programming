package sensores;

import java.util.concurrent.Semaphore;

public class Mediciones {

    // Un semáforo para controlar cuando puede trabajar el trabajador
    Semaphore puedoTrabajar = new Semaphore(0, true);

    // Debo controlar que no se me cuelen el mismo sensor
    Semaphore[] controlSensores = {new Semaphore(1,true), new Semaphore(1,true), new Semaphore(1,true)};

    private int totalMediciones = 0;
    public Mediciones() {}

    /**
     * El sensor id deja su medición y espera hasta que el trabajador
     * ha terminado sus tareas
     * 
     * @param id
     * @throws InterruptedException
     */

    public void nuevaMedicion(int id) throws InterruptedException {
        controlSensores[id].acquire();
        System.out.println("Sensor " + id + " deja sus mediciones.");
        totalMediciones++;
        if(totalMediciones == 3){
            puedoTrabajar.release();
        }
    }

    /***
     * El trabajador espera hasta que están las tres mediciones
     * 
     * @throws InterruptedException
     */
    public void leerMediciones() throws InterruptedException {
        puedoTrabajar.acquire();
        System.out.println("El trabajador tiene sus mediciones...y empieza sus tareas");
    }
    /**
     * El trabajador indica que ha terminado sus tareas
     */

    public void finTareas() throws InterruptedException {
        System.out.println("El trabajador ha terminado sus tareas");
        totalMediciones = 0;
        for(int i = 0; i < controlSensores.length; i++){
            controlSensores[i].release();
        }
    }
}
