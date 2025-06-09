package aseos;

import java.util.concurrent.Semaphore;

public class Aseos {

	private int numClientes = 0;

	private boolean limpiando = false;

	Semaphore tieneQueEntrarEquipoLimpieza = new Semaphore(1, true);

	// Controlo la entrada de los clientes
	Semaphore puedoEntrarBaño = new Semaphore(1, true);
	// Controlo la entrada del equipo de limpieza
	Semaphore puedoEntrarEquipoLimpieza = new Semaphore(1, true);

	Semaphore mutex = new Semaphore(1, true);
	/**
	 * Utilizado por el cliente id cuando quiere entrar en los aseos
	 * CS Version injusta: El cliente espera si el equipo de limpieza está
	 * trabajando
	 * CS Version justa: El cliente espera si el equipo de limpieza está trabajando
	 * o
	 * está esperando para poder limpiar los aseos
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public void entroAseo(int id) throws InterruptedException {
		// Versión injusta
		// Versión justa: Si el equipo de limpieza está esperando, el cliente que quiera entrar debe esperarse
		tieneQueEntrarEquipoLimpieza.acquire();
		if(numClientes == 0 || limpiando) {
			puedoEntrarBaño.acquire();
		}
		mutex.acquire();
		numClientes++;
		System.out.println("El cliente " + id + " ha entrado en el baño."
				+ "Clientes en el aseo: " + numClientes);
		if(numClientes == 1){
			puedoEntrarEquipoLimpieza.acquire();
		}
		mutex.release();
		tieneQueEntrarEquipoLimpieza.release();
	}

	/**
	 * Utilizado por el cliente id cuando sale de los aseos
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public void salgoAseo(int id) throws InterruptedException {
		mutex.acquire();
		numClientes--;
		System.out.println("El cliente " + id + " ha salido del baño."
				+ "Clientes en el aseo: " + numClientes);
		if(numClientes == 0){
			puedoEntrarEquipoLimpieza.release();
			puedoEntrarBaño.release();
		}
		mutex.release();
	}

	/**
	 * Utilizado por el Equipo de Limpieza cuando quiere entrar en los aseos
	 * CS: El equipo de trabajo está solo en los aseos, es decir, espera hasta que
	 * no
	 * haya ningún cliente.
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public void entraEquipoLimpieza() throws InterruptedException {
		tieneQueEntrarEquipoLimpieza.acquire();
		puedoEntrarBaño.acquire();
		puedoEntrarEquipoLimpieza.acquire();
		System.out.println("El equipo de limpieza está trabajando.");
		limpiando = true;
		tieneQueEntrarEquipoLimpieza.release();
	}

	/**
	 * Utilizado por el Equipo de Limpieza cuando sale de los aseos
	 * 
	 * @throws InterruptedException
	 * 
	 * 
	 */
	public void saleEquipoLimpieza() throws InterruptedException {
		System.out.println("El equipo de limpieza ha terminado.");
		limpiando = false;
		puedoEntrarBaño.release();
		puedoEntrarEquipoLimpieza.release();
	}
}
