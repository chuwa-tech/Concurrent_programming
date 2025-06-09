import java.util.concurrent.Semaphore;

public class Cuerda {

	Semaphore turno = new Semaphore(1,true); // Quien se queda con el turno de la cuerda
	Semaphore puedePasarDeNS = new Semaphore(1,true); // Controlo el número de monos que van de norte a sur
	Semaphore puedePasarDeSN = new Semaphore(1,true); // Controlo el número de monos que van de sur a norte

	int num_monos = 0;

	/**
	 * Utilizado por un babuino cuando quiere cruzar el cañón colgándose de la
	 * cuerda en dirección Norte-Sur
	 * Cuando el método termina, el babuino está en la cuerda y deben satisfacerse
	 * las dos condiciones de sincronización
	 * @param id del babuino que entra en la cuerda
	 * @throws InterruptedException
	 */
	public  void entraDireccionNS(int id) throws InterruptedException{
		// Pregunto si tengo el turno
		turno.acquire();
		// Incremento el número de monos
		puedePasarDeNS.acquire();
		num_monos++;
		System.out.println("El babuino " + id + " va a cruzar. Hay " + num_monos + " en la cuerda");
		if(num_monos == 1){
			puedePasarDeSN.acquire();
		}
		if(num_monos < 3){
			puedePasarDeNS.release();
		}
		turno.release();
	}
	/**
	 * Utilizado por un babuino cuando quiere cruzar el cañón colgándose de la
	 * cuerda en dirección Norte-Sur
	 * Cuando el método termina, el babuino está en la cuerda y deben satisfacerse
	 * las dos condiciones de sincronización
	 * @param id del babuino que entra en la cuerda
	 * @throws InterruptedException
	 */
	public  void entraDireccionSN(int id) throws InterruptedException{
	}
	/**
	 * Utilizado por un babuino que termina de cruzar por la cuerda en dirección Norte-Sur
	 * @param id del babuino que sale de la cuerda
	 * @throws InterruptedException
	 */
	public  void saleDireccionNS(int id) throws InterruptedException{
		puedePasarDeNS.release();
		if(num_monos > 0){
			num_monos--;
		}
		if(num_monos == 0){
			puedePasarDeSN.release();
		}
	}
	
	/**
	 * Utilizado por un babuino que termina de cruzar por la cuerda en dirección Sur-Norte
	 * @param id del babuino que sale de la cuerda
	 * @throws InterruptedException
	 */
	public  void saleDireccionSN(int id) throws InterruptedException{
	}	
		
}
