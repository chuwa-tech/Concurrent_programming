package pajaritos;

import java.util.concurrent.*;

public class Nido {
	private int B = 10; // Número máximo de bichos
	private int bichitos; // puede tener de 0 a B bichitos

	Semaphore puedoDepositar = new Semaphore(1,true);
	Semaphore puedoComer = new Semaphore(0,true);
	Semaphore mutexBichos = new Semaphore(1,true);

	public void come(int id) throws InterruptedException{
		// Si hay comida puedo comer. Pido permiso
		puedoComer.acquire();
		// Como
		mutexBichos.acquire();
		bichitos--;
		System.out.println("El bebé " + id + " ha comido un bichito. Quedan " + bichitos);
		if(bichitos == 9) puedoDepositar.release();
		mutexBichos.release();
	}

	public void nuevoBichito(int id) throws InterruptedException {
		// el papa/mama id deja un nuevo bichito en el nido
		// Pido permiso para depositar
		puedoDepositar.acquire();
		// Si puedo depositar, deposito
		mutexBichos.acquire();
		bichitos++;
		System.out.println("El papá " + id + " ha añadido un bichito. Hay " + bichitos);
		if(bichitos > 0) puedoComer.release();
		if(bichitos < B) puedoDepositar.release();
		mutexBichos.release();
	}
}

// CS-Bebe-i: No puede comer del nido si está vacío
// CS-Papa/Mama: No puede poner un bichito en el nido si está lleno
