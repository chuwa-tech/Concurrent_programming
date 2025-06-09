package agua;


import java.util.concurrent.*;

public class GestorAgua {

	Semaphore mutexH = new Semaphore(1,true);
	Semaphore puedeUnirseOxigeno = new Semaphore(0, true);
	Semaphore puedeUnirseHidrogeno = new Semaphore(1, true);

	private int numMolH = 0;


	// Cuando la molécula de H este lista para unirse llama a este método
	public void hListo(int id) throws InterruptedException{
		puedeUnirseHidrogeno.acquire();
		mutexH.acquire();
		numMolH++;
		System.out.println("La molécula de H con id " + id + " quiere formar una molécula de agua: N" + numMolH);
		mutexH.release();
		if(numMolH < 2){
			puedeUnirseHidrogeno.release();
		} else puedeUnirseOxigeno.release();
	}

	// Cuando la molécula de O este lista para unirse llama a este método

	public void oListo(int id) throws InterruptedException{
		puedeUnirseOxigeno.acquire();
		mutexH.acquire();
		System.out.println("La molécula de O con id " + id + " quiere formar una molécula de agua: N" + numMolH + "O");
		numMolH = 0;
		mutexH.release();
		puedeUnirseHidrogeno.release();
	}
}