import java.util.concurrent.Semaphore;

public class Barca {

	Semaphore puedoSubir0 = new Semaphore(1, true); // Puede subir desde la orilla 0
	Semaphore puedoSubir1 = new Semaphore(0, true); // Puede subir desde la orilla 1
	Semaphore puedoBajar = new Semaphore(0, true); // Puede bajar de la barca
	Semaphore espera = new Semaphore(0, true); // Debe esperar capitan
	Semaphore finViaje = new Semaphore(0, true); // Ha terminado el viaje
	Semaphore mutexPasajeros = new Semaphore(1, true);

	private int orilla;

	private int nPasajeros = 0;

	/*
	 * El Pasajero id quiere darse una vuelta en la barca desde la orilla pos
	 */
	public  void subir(int id,int pos) throws InterruptedException{
		// Bloqueo la cola de puedoSubir1 si estoy desde la playa 0 y viceversa. Además, bloqueo la cola de bajada
		// Incremento el número de pasajeros
		if(pos == 0){
			puedoSubir0.acquire();
		}else{
			puedoSubir1.acquire();
		}

		mutexPasajeros.acquire();

		nPasajeros++;

		System.out.println("Pasajero " + id + " sube de la orilla " + pos + ". Hay " + nPasajeros + " pasajeros en la barca.");

		if(nPasajeros < 3){
			if(pos == 0){
				puedoSubir0.release();
			}else {
				puedoSubir1.release();
			}
		}else{
			espera.release();
		}

		mutexPasajeros.release();
	}
	
	/*
	 * Cuando el viaje ha terminado, el Pasajero que esta en la barca se baja
	 */
	public int bajar(int id) throws InterruptedException{
		// Si termina entonces bajo pasajeros. Pregunto si pueden bajar
		puedoBajar.acquire();
		mutexPasajeros.acquire();
		nPasajeros--;
		System.out.println("Pasajero " + id + " ha bajado de la barca. Quedan " + nPasajeros + " pasajeros en la barca");
		// Cuando el número de pasajeros es 0, la barca está vacía

		if(nPasajeros == 0){
			if(orilla == 0){
				// Pueden subir pasajeros
				puedoSubir1.release();
			}else if(orilla == 1){
				puedoSubir0.release();
			}
		}

		if(nPasajeros > 0){
			puedoBajar.release();
		}else{
			orilla = (orilla + 1) % 2;
		}
		mutexPasajeros.release();
		return orilla;
	}
	/*
	 * El Capitan espera hasta que se suben 3 pasajeros para comenzar el viaje
	 */
	public void esperoSuban() throws InterruptedException{
		espera.acquire(); // Espero hasta que este lleno
		System.out.println("El Capitan ha comenzado el viaje");
		finViaje.release();
	}

	/*
	 * El Capitan indica a los pasajeros que el viaje ha terminado y tienen que bajarse
	 */
	public void finViaje() throws InterruptedException{
		// El viaje ya ha terminado
		finViaje.acquire();
		System.out.println("El Capitan ha terminado el viaje");
		puedoBajar.release();
	}

}
