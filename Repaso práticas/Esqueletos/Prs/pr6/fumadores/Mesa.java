package fumadores;

import java.util.concurrent.*;

public class Mesa {

	// esta es una implementación pasiva para los fumadores
	// los van a despertar cuando tengan que fumar.

	// El agente quiere poner ingredientes en la mesa
	Semaphore puedoPonerIngredientes = new Semaphore(1, true);
	// Cada fumador tiene su propio semáforo
	Semaphore[] puedoFumarIndividual = {new Semaphore(0, true), new Semaphore(0, true), new Semaphore(0, true)};

	public Mesa() {}

	public void qFumar(int id) throws InterruptedException {
		puedoFumarIndividual[id].acquire();
		System.out.println("Fumador " + id + " coge los ingredientes");
	}

	public void finFumar(int id) throws InterruptedException {
		System.out.println("Fumador " + id + " ha terminado de fumar");
		puedoPonerIngredientes.release();
	}

	public void nuevosIng(int ing) throws InterruptedException { // se pasa el ingrediente que no se pone
		puedoPonerIngredientes.acquire();
		System.out.println("El agente ha puesto los ingredientes ");
		puedoFumarIndividual[ing].release();
	}

}

// CS-Fumador i: No puede fumar hasta que el fumador anterior no ha terminado
// de fumar y sus ingredientes están sobre la mesa
// CS-Agente: no puede poner nuevos ingredientes hasta que el fumador anterior
// no ha terminado de fumar
