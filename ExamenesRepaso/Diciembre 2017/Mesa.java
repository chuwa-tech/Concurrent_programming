package juego;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
public class Mesa {
    //0 - piedra, 1 - papel, 2 - tijeras

	int[] elecciones = new int[3];

	private int jugadaGanadora;

	private int numJugadoresQueYaJugaron = 0;

	Semaphore deboEsperar = new Semaphore(0, true);
	Semaphore mutex = new Semaphore(1,true);
	Semaphore puedoJugar = new Semaphore(1, true);

	/**
	 * 
	 * @param jug jugador que llama al m�todo (0,1,2)
	 * @param juego jugada del jugador (0-piedra,1-papel, 2-tijeras)
	 * @return  si ha habido un ganador en esta jugada se devuelve 
	 *          la jugada ganadora
	 *         o -1, si no ha habido ganador
	 * @throws InterruptedException
	 * 
	 * El jugador que llama a este m�todo muestra su jugada, y espera a que 
	 * est�n la de los otros dos. 
	 * Hay dos condiciones de sincronizaci�n
	 * CS1- Un jugador espera en el m�todo hasta que est�n las tres jugadas
	 * CS2- Un jugador tiene que esperar a que finalice la jugada anterior para
	 *     empezar la siguiente
	 * 
	 */
	public int nuevaJugada(int jug,int juego) throws InterruptedException{
		puedoJugar.acquire();
		mutex.acquire();
		numJugadoresQueYaJugaron++;
		elecciones[jug] = juego;
		if(numJugadoresQueYaJugaron == 3){
			// Compruebo quien ha ganado
			if(elecciones[0] == 0 && elecciones[1] == 2 && elecciones[2] == 2 || elecciones[0] == 2 && elecciones[1] == 2 && elecciones[2] == 0 || elecciones[0] == 2 && elecciones[1] == 0 && elecciones[2] == 2){
				jugadaGanadora = 0;
			}else if(elecciones[0] == 0 && elecciones[1] == 0 && elecciones[2] == 1 || elecciones[0] == 1 && elecciones[1] == 0 && elecciones[2] == 0 || elecciones[0] == 0 && elecciones[1] == 1 && elecciones[2] == 0){
				jugadaGanadora = 1;
			}else if(elecciones[0] == 2 && elecciones[1] == 1 && elecciones[2] == 1 || elecciones[0] == 1 && elecciones[1] == 1 && elecciones[2] == 2 || elecciones[0] == 1 && elecciones[1] == 2 && elecciones[2] == 1){
				jugadaGanadora = 2;
			}else{
				jugadaGanadora = -1;
				System.out.println("Empate");
			}
			deboEsperar.release();
		}else{
			puedoJugar.release();
		}
		mutex.release();
		deboEsperar.acquire();
		numJugadoresQueYaJugaron--;
		if(numJugadoresQueYaJugaron > 0){
			deboEsperar.release();
		}else{
			puedoJugar.release();
		}
		return jugadaGanadora;
	}
}
