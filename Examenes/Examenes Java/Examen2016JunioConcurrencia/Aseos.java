import java.util.concurrent.Semaphore;

public class Aseos {
	Semaphore cliente = new Semaphore(1, true); // Controlar número de clientes
	Semaphore limpieza = new Semaphore(1, true); // Controlar el permiso para limpiar o no
	Semaphore esperaCliente = new Semaphore(1, true); // Para la espera del cliente
	Semaphore esperaLimpieza = new Semaphore(1, true); // Se controla la espera del equipo de limpieza
	int numClientsDentro = 0; // clientes dentro del baño
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
		// Puede o no entrar un cliente
		esperaLimpieza.acquire();
		esperaCliente.acquire();
		cliente.acquire();
		if(numClientsDentro == 0){
			limpieza.acquire();
		}
		numClientsDentro++;
		System.out.println("El cliente " + id + " ha entrado en el baño." + "Clientes en el aseo: " + numClientsDentro);
		// Cuando sale el cliente se liberan permisos
		cliente.release();
		esperaCliente.release();
		esperaLimpieza.release();
	}

	/**
	 * Utilizado por el cliente id cuando sale de los aseos
	 *
	 * @throws InterruptedException
	 *
	 */
	public void salgoAseo(int id) throws InterruptedException {
		// Se espera a que salga el cliente
		cliente.acquire();
		numClientsDentro--;
		System.out.println("El cliente " + id + " ha salido del baño."
				+ "Clientes en el aseo: " + numClientsDentro);
		// Si el baño está vacío, el equipo de limpieza puede entrar
		if(numClientsDentro == 0){
			limpieza.release();
		}
		cliente.release();
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
		esperaCliente.acquire();
		System.out.println("						El equipo de limpieza quiere entrar");
		limpieza.acquire();
		System.out.println("El equipo de limpieza esta trabajando.");
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
		esperaCliente.release();
		limpieza.release();
	}
}
