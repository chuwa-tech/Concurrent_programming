package mrusa;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.*;

public class Coche implements Runnable {
	private int tam; // Tamaño del coche

	private int numPasajerosAbordo = 0; // Numero de pasajeros que estan abordo

	//Controlo la subida al coche
	Semaphore puedoSubir = new Semaphore(1, true);
	//Controlo la bajada del coche
	Semaphore puedoBajar = new Semaphore(0, true);
	//Controlo el inicio del viaje
	Semaphore puedoIniciarViaje = new Semaphore(0, true);
	//Controlo la modificación del número de pasajeros
	Semaphore mutex = new Semaphore(1, true);
	public Coche(int tam) {
		this.tam = tam;
	}

	public void subir(int id) throws InterruptedException {
		// id del pasajero que se sube al coche
		puedoSubir.acquire();
		mutex.acquire();
		numPasajerosAbordo++;
		System.out.println("Pasajero " + id + " abordo. Hay " + numPasajerosAbordo + " pasajeros en el coche.");
		mutex.release();
		if(numPasajerosAbordo < tam){
			puedoSubir.release();
		}else{
			puedoIniciarViaje.release();
		}
	}

	public void bajar(int id) throws InterruptedException {
		// id del pasajero que se baja del coche
		puedoBajar.acquire();
		mutex.acquire();
		numPasajerosAbordo--;
		System.out.println("Pasajero " + id + " bajo.");
		mutex.release();
		if(numPasajerosAbordo > 0){
			puedoBajar.release();
		}else {
			puedoSubir.release();
		}
	}

	private void esperaLleno() throws InterruptedException {
		// el coche espera a que este lleno para dar una vuelta
		puedoIniciarViaje.acquire();
		System.out.println("El coche ya esta lleno. Se inicia el viaje");
		puedoBajar.release();
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
