import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.*;
public class Mesa {
    //0 - piedra, 1 - papel, 2 - tijeras
	private int numJugadas = 0;
	boolean lleno = false;
	private int resPartida = -1;

	int[] elecciones = new int[3];

	Queue<Integer> colaEspera = new ArrayDeque<>();
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
	public synchronized int nuevaJugada(int jug,int juego) throws InterruptedException{
		while(!colaEspera.isEmpty() && colaEspera.contains(jug)) wait();
		numJugadas++;
		elecciones[numJugadas-1] = juego;
		colaEspera.add(jug);
		if(numJugadas == 3){
			if(elecciones[0] == 1 && elecciones[1] == 1 && elecciones[2] == 2 || elecciones[0] == 1 && elecciones[2] == 1 && elecciones[1] == 2 || elecciones[1] == 1 && elecciones[2] == 1 && elecciones[0] == 2){
				resPartida = 2;
			}else if(elecciones[0] == 2 && elecciones[1] == 2 && elecciones[2] == 0 || elecciones[0] == 2 && elecciones[2] == 2 && elecciones[1] == 0 || elecciones[1] == 2 && elecciones[2] == 2 && elecciones[0] == 0){
				resPartida = 0;
			}else if(elecciones[0] == 0 && elecciones[1] == 0 && elecciones[2] == 1 || elecciones[0] == 0 && elecciones[2] == 0 && elecciones[1] == 1 || elecciones[1] == 0 && elecciones[2] == 0 && elecciones[0] == 1){
				resPartida = 1;
			}else{
				System.out.println("Empate");
			}
			lleno = true;
			notifyAll();
		}
		while(!lleno) wait();
		numJugadas--;
		if(numJugadas == 0){
			lleno = false;
			colaEspera.clear();
		}
		notifyAll();
		return resPartida;
	}
}
