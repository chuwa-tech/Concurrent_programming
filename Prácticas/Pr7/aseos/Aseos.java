package aseos;

import java.util.concurrent.Semaphore;

public class Aseos {
	Semaphore puedoLimpiar = new Semaphore(1,true);
	Semaphore clientes = new Semaphore(1,true);
	int numClients = 0; // clientes
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
		clientes.acquire();
		numClients++;

		if(numClients == 1){
			puedoLimpiar.acquire();
		}
		System.out.println("El cliente " + id + " ha entrado en el baño."
				+ "Clientes en el aseo: " + numClients);
			
		clientes.release();
	}

	/**
	 * Utilizado por el cliente id cuando sale de los aseos
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public void salgoAseo(int id) throws InterruptedException {
		clientes.acquire();
		
		numClients--;
		System.out.println("El cliente " + id + " ha salido del baño."
				+ "Clientes en el aseo: " + numClients);
		
		if(numClients == 0){
			puedoLimpiar.release();
		}

		clientes.release();
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
	 	puedoLimpiar.acquire();
		System.out.println("El equipo de limpieza está trabajando.");
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
		puedoLimpiar.release();
	}
}
