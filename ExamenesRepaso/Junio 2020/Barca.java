import java.util.concurrent.Semaphore;

public class Barca {

	private final int MAX_PASAJEROS = 3;

	private int pasajerosAbordo = 0;

	private int orilla = 0;

	Semaphore puedoSubirSur = new Semaphore(1, true);
	Semaphore puedoSubirNorte = new Semaphore(1, true);
	Semaphore puedoIniciarViaje = new Semaphore(0, true);
	Semaphore puedoBajar = new Semaphore(0, true);
	Semaphore mutex = new Semaphore(1, true);
	/*
	 * El Pasajero id quiere darse una vuelta en la barca desde la orilla pos
	 */
	public void subir(int id,int pos) throws InterruptedException{
		//TODO
		if(pasajerosAbordo == 0){
			if(orilla == 0){
				puedoSubirNorte.acquire();
			}else{
				puedoSubirSur.acquire();
			}
		}
		if(pos == 0){
			puedoSubirSur.acquire();
		}else {
			puedoSubirNorte.acquire();
		}
		mutex.acquire();
		pasajerosAbordo++;
		System.out.println("Pasajero " + id + " sube a la barca desde la orilla " + pos + ". Hay " + pasajerosAbordo + "pasajeros abordo");
		mutex.release();
		if(pasajerosAbordo < 3 && pos == 0){
			puedoSubirSur.release();
		}else if(pasajerosAbordo < 3 && pos == 1){
			puedoSubirNorte.release();
		}else{
			puedoIniciarViaje.release();
		}
	}
	
	/*
	 * Cuando el viaje ha terminado, el Pasajero que esta en la barca se baja
	 */
	public int bajar(int id) throws InterruptedException{
		//TODO
		return 0;
	}
	/*
	 * El Capitan espera hasta que se suben 3 pasajeros para comenzar el viaje
	 */
	public void esperoSuban() throws InterruptedException{
		//TODO
		puedoIniciarViaje.acquire();
		System.out.println("El capitan inicia el viaje");
	}
	/*
	 * El Capitan indica a los pasajeros que el viaje ha terminado y tienen que bajarse
	 */
	public void finViaje() throws InterruptedException{
		//TODO
		System.out.println("El capitan indica a los pasajeros que el viaje ha terminado y tienen que bajarse");
	}
}
