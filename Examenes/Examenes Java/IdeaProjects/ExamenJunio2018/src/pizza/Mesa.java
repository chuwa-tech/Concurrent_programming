package pizza;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Mesa {

	Queue<Integer> colaEspera = new ArrayDeque<>();
	// ¿Puedo coger una pizza?
	boolean puedoCogerPizza = false;

	boolean soyPrimero = false;

	// ¿Han pagado?
	boolean puedoPagar = true;

	// ¿Puedo hacer una pizza?
	boolean puedoHacerPizza = false;

	// Máximo de trozos a vender
	private int numTrozos = 0;

	/**
	 * 
	 * @param id
	 * El estudiante id quiere una ración de pizza. 
	 * Si hay una ración la coge y sigue estudiante.
	 * Si no hay y es el primero que se da cuenta de que la mesa está vacía
	 * llama al pizzero y
	 * espera hasta que traiga una nueva pizza. Cuando el pizzero trae la pizza
	 * espera hasta que el estudiante que le ha llamado le pague.
	 * Si no hay pizza y no es el primer que se da cuenta de que la mesa está vacía
	 * espera hasta que haya un trozo para él.
	 * @throws InterruptedException 
	 * 
	 */
	public synchronized void nuevaRacion(int id) throws InterruptedException{
		colaEspera.add(id);
		while(!puedoCogerPizza || (!soyPrimero && colaEspera.peek() != id)) wait();
		if(numTrozos == 8) System.out.println("Estudiante " + id + " paga.");
		numTrozos--;
		System.out.println("Estudiante " + id + " coge una ración de pizza. Hay " + numTrozos + " trozos.");
		colaEspera.remove();
		if(numTrozos == 0){
			puedoCogerPizza = false;
			puedoPagar = true;
			notify();
		}
	}


	/**
	 * El pizzero entrega la pizza y espera hasta que le paguen para irse
	 * @throws InterruptedException 
	 */
	public synchronized void nuevoCliente() throws InterruptedException{
		soyPrimero = true;
		while(!puedoPagar) wait();
		if(numTrozos == 0){
			puedoHacerPizza = true;
			System.out.println("Cliente llama a pizzero");
		}
		puedoPagar = false;
		notify();
	}
	

/**
	 * El pizzero espera hasta que algún cliente le llama para hacer una pizza y
	 * llevársela a domicilio
	 * @throws InterruptedException 
	 */
	public synchronized void nuevaPizza() throws InterruptedException{
		System.out.println("Pizzero prepara una pizza.");
		while(!puedoHacerPizza) wait();
		System.out.println("Pizzero entrega una pizza.");
		numTrozos = 8;
		puedoCogerPizza = true;
		puedoHacerPizza = false;
		soyPrimero = false;
		notifyAll();
    }
}
