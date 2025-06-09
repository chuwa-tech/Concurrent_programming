package pajaritos;

import java.util.concurrent.*;

public class Nido {
	private int B = 10; // Número máximo de bichos
	private int bichitos; // puede tener de 0 a B bichitos

	// Controlo cuando comen las crías
	Semaphore puedoComer = new Semaphore(0, true);

	// Controlo si pueden poner más bichitos en el plato
	Semaphore puedoPoner = new Semaphore(1, true);

	// Controlo el número de bichos que están en el plato
	Semaphore mutex = new Semaphore(1, true);

	public void come(int id) throws InterruptedException {
		puedoComer.acquire();
		mutex.acquire();
		bichitos--;
		System.out.println("El bebé " + id + " ha comido un bichito. Quedan " + bichitos);
		if(bichitos == B-1){
			puedoPoner.release();
		}else if(bichitos > 0){
			puedoComer.release();
		}
		mutex.release();
	}

	public void nuevoBichito(int id) throws InterruptedException {
		// el papa/mama id deja un nuevo bichito en el nido
		puedoPoner.acquire();
		mutex.acquire();
		bichitos++;
		System.out.println("El papá " + id + " ha añadido un bichito. Hay " + bichitos);
		if(bichitos == 1){
			puedoComer.release();
		}
		if(bichitos < B){
			puedoPoner.release();
		}else{
			puedoComer.release();
		}
		mutex.release();
	}
}

// CS-Bebe-i: No puede comer del nido si está vacío
// CS-Papa/Mama: No puede poner un bichito en el nido si está lleno
