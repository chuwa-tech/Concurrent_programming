package guarderia;


import java.util.concurrent.Semaphore;

public class Guarderia {

	int numBebes = 0;

	int numAdultos = 0;

	Semaphore puedoEntrarBebe = new Semaphore(0, true);

	Semaphore puedoSalirAdulto = new Semaphore(0, true);

	Semaphore mutex = new Semaphore(1, true);
	/**
	 * Un bebe que quiere entrar en la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro entrar, es decir, hasta que 
	 * cuado entre haya, al menos, 1 adulto por cada 3 bebes
	 * 
	 */
	public void entraBebe(int id) throws InterruptedException{
		puedoEntrarBebe.acquire();
		mutex.acquire();
		numBebes++;
		System.out.println("		Bebe " + id + " entra");
		if(numBebes+1 <= 3*numAdultos){
			puedoEntrarBebe.release();
		}
		mutex.release();
	}
	/**
	 * Un bebe que quiere irse de la guarderia llama a este metodo * 
	 */
	public void saleBebe(int id) throws InterruptedException{
		mutex.acquire();
		numBebes--;
		System.out.println("		Bebe " + id + " sale de la guardería");
		if(numBebes <= 3*(numAdultos-1)){
			puedoSalirAdulto.release();
		}
		mutex.release();
	}
	/**
	 * Un adulto que quiere entrar en la guarderia llama a este metodo * 
	 */
	public void entraAdulto(int id) throws InterruptedException{
		mutex.acquire();
		numAdultos++;
		System.out.println("Adulto " + id + " entra en la guardería");
		puedoEntrarBebe.release();
		mutex.release();
	}
	
	/**
	 * Un adulto que quiere irse  de la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro salir, es decir, hasta que
	 * cuando se vaya haya, al menos, 1 adulto por cada 3 bebes
	 * 
	 */
	public void saleAdulto(int id) throws InterruptedException{
		puedoSalirAdulto.acquire();
		mutex.acquire();
		numAdultos--;
		System.out.println("Adulto " + id + " sale de la guardería");
		if(numBebes <= 3*numAdultos){
			puedoSalirAdulto.release();
		}
		mutex.release();
	}

}
