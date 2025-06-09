package lago;

import java.util.concurrent.Semaphore;
import java.util.stream.Stream;

public class Lago {
	private volatile int nivel = 0;

	// ¿Quién puede actuar?
	Semaphore turno = new Semaphore(1, true);
	Semaphore puedoRio0 = new Semaphore(1, true);
	Semaphore puedoRio1 = new Semaphore(1, true);
	Semaphore puedoPresa0 = new Semaphore(0, true);
	Semaphore puedoPresa1 = new Semaphore(0, true);

	boolean llenando = false;
	public Lago(int valorInicial) {
		nivel = valorInicial;
	}

	// f0IncDec, f0Inc
	public void incRio0() throws InterruptedException{
		System.out.println("El rio 0 quiere llenar el lago");
		turno.acquire();
		// ¿Puede verter este río?
		if(nivel == 0) puedoPresa0.acquire();
		nivel++;
		System.out.println("Nivel del lago " + nivel);
	}

	// f0IncDec, f1Inc
	public void incRio1() throws InterruptedException{
		// ¿Puede verter este río?
		puedoRio1.acquire();
		nivel++;
	}

	// f1IncDec, f0Dec
	public void decPresa0() throws InterruptedException{
		if(nivel == 0){
			puedoPresa0.acquire();
		}else{
			turno.acquire();
			puedoPresa1.acquire();
		}
		nivel--;
		if(nivel == 0){

		}
	}

	// f1IncDec, f1Dec
	public void decPresa1() throws InterruptedException{
		nivel--;
	}

	public int nivel() {
		return nivel;
	}
}
