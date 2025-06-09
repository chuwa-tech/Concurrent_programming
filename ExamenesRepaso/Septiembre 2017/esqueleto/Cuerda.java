package esqueleto.esqueleto;


import java.util.concurrent.Semaphore;

public class Cuerda {

	// Número de babuinos que han cruzado la cuerda desde el norte
	int numBabuinosNS = 0;
	// Número de babuinos que han cruzado la cuerda desde el sur
	int numBabuinosSN = 0;

	// Número máximo de babuinos que pueden estar colgados de la cuerda
	int maxBabuinos = 3;
	int turno = 0; // 0 para norte, 1 para sur

	// Controlo la subida de babuinos por el norte
	Semaphore puedoPasarNS = new Semaphore(1,true);
	// Controlo la subida de babuinos por el sur
	Semaphore puedoPasarSN = new Semaphore(1,true);
	// Controlo si hay espacio en la cuerda
	Semaphore hayEspacio = new Semaphore(1,true);

	/**
	 * Utilizado por un babuino cuando quiere cruzar el cañón colgándose de la
	 * cuerda en dirección Norte-Sur
	 * Cuando el método termina, el babuino está en la cuerda y deben satisfacerse
	 * las dos condiciones de sincronización
	 * @param id del babuino que entra en la cuerda
	 * @throws InterruptedException
	 */
	public  void entraDireccionNS(int id) throws InterruptedException{
		if(numBabuinosNS == 0 && turno == 1){
			puedoPasarSN.acquire();
		}
		puedoPasarNS.acquire();
        hayEspacio.acquire();
        numBabuinosNS++;
        System.out.println("Babuino " + id + " entra por el norte");
		hayEspacio.release();
		if(numBabuinosNS < maxBabuinos){
			puedoPasarNS.release();
        }
	}
	/**
	 * Utilizado por un babuino cuando quiere cruzar el cañón  colgándose de la
	 * cuerda en dirección Norte-Sur
	 * Cuando el método termina, el babuino está en la cuerda y deben satisfacerse
	 * las dos condiciones de sincronización
	 * @param id del babuino que entra en la cuerda
	 * @throws InterruptedException
	 */
	public  void entraDireccionSN(int id) throws InterruptedException{
		if(numBabuinosSN == 0 && turno == 0){
			puedoPasarNS.acquire();
		}
		puedoPasarSN.acquire();
		hayEspacio.acquire();
		numBabuinosSN++;
		System.out.println("Babuino " + id + " entra por el sur");
		hayEspacio.release();
		if(numBabuinosSN < maxBabuinos){
			puedoPasarSN.release();
		}
	}
	/**
	 * Utilizado por un babuino que termina de cruzar por la cuerda en dirección Norte-Sur
	 * @param id del babuino que sale de la cuerda
	 * @throws InterruptedException
	 */
	public  void saleDireccionNS(int id) throws InterruptedException{
        numBabuinosNS--;
        System.out.println("Babuino " + id + " sale por el sur");
        if(numBabuinosNS == 0){
			puedoPasarSN.release();
			turno = 1;
        }
	}
	
	/**
	 * Utilizado por un babuino que termina de cruzar por la cuerda en dirección Sur-Norte
	 * @param id del babuino que sale de la cuerda
	 * @throws InterruptedException
	 */
	public  void saleDireccionSN(int id) throws InterruptedException{
		numBabuinosSN--;
		System.out.println("Babuino " + id + " sale por el norte");
		if(numBabuinosSN == 0){
			puedoPasarNS.release();
			turno = 0;
		}
	}	
		
}
