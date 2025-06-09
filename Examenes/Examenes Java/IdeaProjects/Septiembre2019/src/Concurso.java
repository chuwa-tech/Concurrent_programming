



public class Concurso {

	private final int MAX_TIRADAS = 20;

	// Con monitores
	// En este array guardamos el número de caras que le sale a cada uno.
	int[] conteoDeCaras = {0,0};
	// Guardo las partidas que gana cada uno.
	int[] partidasGanadas = {0,0};

	// Compruebo si ya han tirado
	boolean haTiradoJugador0 = false;
	boolean haTiradoJugador1 = false;
	// Compruebo si termina el concurso
	boolean terminaConcurso = false;

	private int numTiradas = 0;
	public synchronized void tirarMoneda(int id,boolean cara) throws InterruptedException {
		// Miro quien quiere tirar
		if(id == 0){
			// Mientras no sea mi turno espero.
			while(!haTiradoJugador1){
				wait();
			}
			// Como es mi turno, debo mirar que ha salido
			// Solo si saco cara incremento su contador
			if(cara){
				conteoDeCaras[id]++;
			}
		}else{
			// Igual que antes, pero para el jugador1
			while(!haTiradoJugador0){
				wait();
			}
			if(cara){
				conteoDeCaras[id]++;
			}
		}
		numTiradas++;
		System.out.println("Jugador " + id + " lleva " + conteoDeCaras[id] + " caras.");
		if(numTiradas < MAX_TIRADAS){
			if(id == 0){
				haTiradoJugador1 = false;
				haTiradoJugador0 = true;
				notifyAll();
			}else{
				haTiradoJugador0 = false;
				haTiradoJugador1 = true;
				notifyAll();
			}
		}else{
			// Miro quien tiene más caras
			if(id == 0){
				if(conteoDeCaras[id] == conteoDeCaras[id+1]){
					System.out.println("");
				}else if(conteoDeCaras[id] < conteoDeCaras[id+1]){

				}else{

				}
			}else{
				if(conteoDeCaras[id] == conteoDeCaras[id+1]){

				}else if(conteoDeCaras[id] < conteoDeCaras[id+1]){

				}else{

				}
			}
		}
	}	
	
	public synchronized boolean concursoTerminado() {
		return terminaConcurso;
	}
}