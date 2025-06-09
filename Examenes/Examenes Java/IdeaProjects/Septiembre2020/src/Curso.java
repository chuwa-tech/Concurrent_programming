import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Curso {

	//Numero maximo de alumnos cursando simultaneamente la parte de iniciacion
	private final int MAX_ALUMNOS_INI = 10;
	
	//Numero de alumnos por grupo en la parte avanzada
	private final int ALUMNOS_AV = 3;

	// Con locks

	Lock l = new ReentrantLock();

	// Una cola de espera para los que deben entrar al grupo de inicialización
	Condition puedeIniciarse = l.newCondition();

	// Una cola de espera para entrar a la parte avanzada
	Condition puedeAvanzarAv = l.newCondition();
	// Controlar la espera al inicio de la fase avanzada
	Condition puedoContinuarAv = l.newCondition();
	// Cola para el control de la salida de los alumnos de avanzada
	Condition puedeSalirAv = l.newCondition();


	// Grupo iniciación lleno
	boolean llenoInit = false;
	// Grupo avanzado lleno
	boolean llenoAv = false;
	// Grupo avanzado termina
	boolean terminanAv = false;

	private int numParticipantesInit = 0;
	private int numParticipantesAv = 0;

	private int totalAlumnosCursados = 0;
	
	//El alumno tendra que esperar si ya hay 10 alumnos cursando la parte de iniciacion
	public void esperaPlazaIniciacion(int id) throws InterruptedException{
		l.lock();
		try{
			//Espera si ya hay 10 alumnos cursando esta parte
			while(llenoInit) puedeIniciarse.await();
			//Mensaje a mostrar cuando el alumno pueda conectarse y cursar la parte de iniciacion
			numParticipantesInit++;
			System.out.println("PARTE INICIACION: Alumno " + id + " cursa parte iniciacion");
			if(numParticipantesInit == MAX_ALUMNOS_INI){
				llenoInit = true;
			}
		}finally {
			l.unlock();
		}
	}

	//El alumno informa que ya ha terminado de cursar la parte de iniciacion
	public void finIniciacion(int id) throws InterruptedException{
		l.lock();
		try{
			numParticipantesInit--;
			//Mensaje a mostrar para indicar que el alumno ha terminado la parte de principiantes
			System.out.println("PARTE INICIACION: Alumno " + id + " termina parte iniciacion");
			//Libera la conexion para que otro alumno pueda usarla
			if(numParticipantesInit == 9) puedeIniciarse.signalAll();
			llenoInit = false;
		}finally{
			l.unlock();
		}
	}
	
	/* El alumno tendra que esperar:
	 *   - si ya hay un grupo realizando la parte avanzada
	 *   - si todavia no estan los tres miembros del grupo conectados
	 */
	public void esperaPlazaAvanzado(int id) throws InterruptedException{
		l.lock();
		try{
			while(llenoAv) puedeAvanzarAv.await();
			numParticipantesAv++;
			//Mensaje a mostrar si el alumno tiene que esperar al resto de miembros en el grupo
			System.out.println("PARTE AVANZADA: Alumno " + id + " espera a que haya " + ALUMNOS_AV + " alumnos");
			if(numParticipantesAv == ALUMNOS_AV){
				llenoAv = true;
			}
			while(!llenoAv) puedoContinuarAv.await();
			puedoContinuarAv.signalAll();
			//Mensaje a mostrar cuando el alumno pueda empezar a cursar la parte avanzada
			System.out.println("PARTE AVANZADA: Hay " + ALUMNOS_AV + " alumnos. Alumno " + id + " empieza el proyecto");
		}finally {
			l.unlock();
		}
	}
	
	/* El alumno:
	 *   - informa que ya ha terminado de cursar la parte avanzada 
	 *   - espera hasta que los tres miembros del grupo hayan terminado su parte 
	 */ 
	public void finAvanzado(int id) throws InterruptedException{
		l.lock();
		try{
			//Espera a que los 3 alumnos terminen su parte avanzada
			numParticipantesAv--;
			//Mensaje a mostrar si el alumno tiene que esperar a que los otros miembros del grupo terminen
			System.out.println("PARTE AVANZADA: Alumno " +  id + " termina su parte del proyecto. Espera al resto");
			if(numParticipantesAv == 0){
				terminanAv = true;
				llenoAv = false;
				puedeAvanzarAv.signalAll();
			}
			totalAlumnosCursados++;
			while(!terminanAv) puedeSalirAv.await();
			//Mensaje a mostrar cuando los tres alumnos del grupo han terminado su parte
			System.out.println("PARTE AVANZADA: LOS " + ALUMNOS_AV + " ALUMNOS HAN TERMINADO EL CURSO");
			System.out.println("PARTE AVANZADA: TOTAL ALUMNOS CURSADOS: " + totalAlumnosCursados);
			puedeSalirAv.signalAll();
			terminanAv = false;
		}finally {
			l.unlock();
		}
	}
}
