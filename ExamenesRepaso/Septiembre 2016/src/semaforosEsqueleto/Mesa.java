package semaforosEsqueleto;

import java.util.concurrent.*;
public class Mesa {

	// Semáforos

	// Máximo de jugadores
	int maxJugadores;
	// Conteo caras
	int numCaras = 0;
	// Conteo cruces
	int numCruces = 0;
	// Jugadores que ya han jugado
	int jugadoresQueYaJugaron = 0;
	// Jugador ganador
	int ganador;

	boolean tiradaGanadora;
	// Tiradas de los jugadores
	boolean[] tiradas;

	// Controlo el inicio de una nueva partida
	Semaphore puedoJugador = new Semaphore(1, true);
	// Controlo la espera de los jugadores
	Semaphore deboEsperar = new Semaphore(0, true);
	// Semáforo para controlar el número de caras y cruces
	Semaphore mutex = new Semaphore(1, true);
	public Mesa(int N){
		maxJugadores = N;
		tiradas = new boolean[N];
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
	public int nuevaJugada(int id,boolean cara) throws InterruptedException{
		// Miro si puedo jugar
		puedoJugador.acquire();
		mutex.acquire();
		jugadoresQueYaJugaron++;
		if(cara){
			numCaras++;
		}else{
			numCruces++;
		}
		tiradas[id] = cara;
		System.out.println("Jugador " + id + " ha tirado");
		// Si es el último jugador, compruebo si hay ganador
		if(jugadoresQueYaJugaron == maxJugadores){
			if(numCruces == 1 || numCaras == 1){
				if(numCaras == 1){
					tiradaGanadora = true;
				}else if(numCruces == 1){
					tiradaGanadora = false;
				}
				int i = 0;
				boolean ok = false;
				while(i < tiradas.length && !ok){
					if(tiradas[i] == tiradaGanadora){
						ganador = i;
						ok = true;
					}
					i++;
				}
				System.out.println("Ha ganado el jugador " + ganador);
			}else{
				ganador = maxJugadores;
				System.out.println("Empate");
			}
			deboEsperar.release();
		}else{
			puedoJugador.release();
		}
		mutex.release();
		// Si no es el último jugador, se espera.
		deboEsperar.acquire();
		jugadoresQueYaJugaron--;
		if(jugadoresQueYaJugaron > 0){
			deboEsperar.release();
		}else{
			puedoJugador.release();
			numCaras = 0;
			numCruces = 0;
		}
		return ganador;
	}
}
