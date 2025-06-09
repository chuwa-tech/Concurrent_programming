package mrusa;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.*;

public class Coche implements Runnable {
	// Con semáforos binarios
	
	private int tam;
	private int pasajerosSubidos = 0;

	Semaphore p = new Semaphore(1, true); // Permiso de entrada de los pasajeros
	Semaphore c = new Semaphore(0, true); // Permiso para que el coche de la vuelta;
	public Coche(int tam) {
		this.tam = tam;
	}

	public void subir(int id) throws InterruptedException {
		// id del pasajero que se sube al coche
		p.acquire();
		if(pasajerosSubidos > tam){
			c.release();
		}else{
			pasajerosSubidos++;
		}

		System.out.println("Va a entrar el pasajero con id " + id + ". Número de pasajeros: " + pasajerosSubidos);
		p.release();
	}

	public void bajar(int id) throws InterruptedException {
		// id del pasajero que se baja del coche
		p.acquire(); // pido permiso para ver si se puede bajar un pasajeros

		if(pasajerosSubidos == 0){
			
		}

		p.release();
	}

	private void esperaLleno() throws InterruptedException {
		// el coche espera a que este lleno para dar una vuelta
		
	}

	public void run() {
		while (true)
			try {
				this.esperaLleno();
				Thread.sleep(200);

			} catch (InterruptedException ie) {
			}

	}
}
// tam pasajeros se suben al coche->el coche da un viaje
// ->tam pasajeros se bajan del coche->......

// CS-Coche: Coche no se pone en marcha hasta que no está lleno
// CS-Pas1: Pasajero no se sube al coche hasta que no hay sitio para el.
// CS-Pas2: Pasajero no se baja del coche hasta que no ha terminado el viaje
