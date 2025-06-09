import java.util.*;
import java.util.concurrent.*;
public class Mesa {
	List<Integer> ids = new ArrayList<>();
	List<Boolean> respuestas = new ArrayList<>();

	boolean jueganTodos = false;

	private int ganador;
	private int personasJugando = 0;
	private int nCaras = 0;
	private int nCruces = 0;
	private int numMaxJugadores;
	public Mesa(int N){
	 	this.numMaxJugadores = N;
	}
	
	/**
	 * 
	 * @param id del jugador que llama al m�todo
	 * @param cara : true si la moneda es cara, false en otro caso
	 * @return un valor entre 0 y N. Si devuelve N es que ning�n jugador 
	 * ha ganado y hay que repetir la partida. Si  devuelve un n�mero menor que N, es el id del
	 * jugador ganador.
	 * 
	 * Un jugador llama al m�todo nuevaJugada cuando quiere poner
	 * el resultado de su tirada (cara o cruz) sobre la mesa.
	 * El jugador deja su resultado y, si no es el �ltimo, espera a que el resto de 
	 * los jugadores pongan sus jugadas sobre la mesa.
	 * El �ltimo jugador comprueba si hay o no un ganador, y despierta
	 * al resto de jugadores
	 *  
	 */
	public synchronized int nuevaJugada(int id,boolean cara) throws InterruptedException{
		while(!ids.isEmpty() && ids.contains(id)) wait();
		if(cara){
			System.out.println("Jugador " + id + " echa cara");
			nCaras++;
		} else {
			System.out.println("Jugador " + id + " echa cruz");
			nCruces++;
		}
		personasJugando++;
		ids.add(id);
		respuestas.add(cara);
		if(personasJugando == numMaxJugadores){
			int i = 0;
			Boolean ok = false;
			if(nCaras == 1){
				while(i < respuestas.size() && !ok){
					if(respuestas.get(i) == true){
						ok = true;
						ganador = ids.get(i);
					}
					i++;
				}
				System.out.println("Ha ganado " + ganador);
			}else if(nCruces == 1){
				while(i < respuestas.size() && !ok){
					if(respuestas.get(i) == false){
						ok = true;
						ganador = ids.get(i);
					}
					i++;
				}
				System.out.println("Ha ganado " + ganador);
			}else {
				System.out.println("No gana nadie");
				ganador = numMaxJugadores;
			}
			i = 0;
			jueganTodos = true;
			notifyAll();
		}
		while(!jueganTodos) wait();
		personasJugando--;
		if(personasJugando == 0){
			nCaras = 0;
			nCruces = 0;
			jueganTodos = false;
			ids.clear();
			respuestas.clear();
			notifyAll();
		}
		return ganador;
	}
}
