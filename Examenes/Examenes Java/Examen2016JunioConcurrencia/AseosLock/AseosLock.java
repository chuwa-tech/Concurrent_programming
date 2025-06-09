package AseosLock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class AseosLock {

    private int nClientes = 0;
    private boolean limpiando = false;
    private ReentrantLock l = new ReentrantLock();
    private Condition okLimpiar = l.newCondition();
    private Condition okEntrar = l.newCondition();

    /**
     * Utilizado por el cliente id cuando quiere entrar en los aseos
     * CS Version injusta: El cliente espera si el equipo de limpieza est� trabajando
     * CS Version justa: El cliente espera si el equipo de limpieza est� trabajando o
     * est� esperando para poder limpiar los aseos
     * @throws InterruptedException
     *
     */
    public void entroAseo(int id) throws InterruptedException{

        l.lock();

        try{
            while(limpiando){
                okEntrar.await();
            }
            nClientes ++;
            System.out.println("El cliente " + id + " ha entrado en el baño." + " Clientes en el aseo: " + nClientes);
        }finally{
            l.unlock();
        }

    }

    /**
     * Utilizado por el cliente id cuando sale de los aseos
     * @throws InterruptedException
     *
     */
    public void salgoAseo(int id) throws InterruptedException{

        l.lock();

        try{
            nClientes --;
            System.out.println("El cliente " + id + " ha salido del baño." + " Clientes en el aseo: " + nClientes);
            if (nClientes == 0) {
                okLimpiar.signal();
            }
        }finally{
            l.unlock();
        }

    }

    /**
     * Utilizado por el Equipo de Limpieza cuando quiere entrar en los aseos
     * CS: El equipo de trabajo est� solo en los aseos, es decir, espera hasta que no
     * haya ning�n cliente.
     * @throws InterruptedException
     *
     */
    public void entraEquipoLimpieza() throws InterruptedException{

        l.lock();

        try{
            while(nClientes > 0 || limpiando){

                okLimpiar.await();
            }
            limpiando = true;
            System.out.println("					El equipo de limpieza está trabajando.");
        }finally{
            l.unlock();
        }

    }

    /**
     * Utilizado por el Equipo de Limpieza cuando  sale de los aseos
     * @throws InterruptedException
     *
     *
     */
    public void saleEquipoLimpieza() throws InterruptedException{

        l.lock();
        try{
            System.out.println("					El equipo de limpieza ha terminado.");
            limpiando = false;
            okEntrar.signalAll();
        }finally{
            l.unlock();
        }
    }
}


// 	private int nClientes = 0;
// 	private int nEquipos = 0;
// 	private boolean limpiando = false;
// 	private ReentrantLock l = new ReentrantLock();
// 	private Condition okLimpiar = l.newCondition();
// 	private Condition okEntrar = l.newCondition();

// 	/**
// 	 * Utilizado por el cliente id cuando quiere entrar en los aseos
// 	 * CS Version injusta: El cliente espera si el equipo de limpieza est� trabajando
// 	 * CS Version justa: El cliente espera si el equipo de limpieza est� trabajando o
// 	 * est� esperando para poder limpiar los aseos
// 	 * @throws InterruptedException
// 	 *
// 	 */
// 	public void entroAseo(int id) throws InterruptedException{

// 		l.lock();

// 		try{
// 			while(limpiando || nEquipos > 0){
// 				okEntrar.await();
// 			}
// 			nClientes ++;
// 			System.out.println("El cliente " + id + " ha entrado en el baño." + " Clientes en el aseo: " + nClientes);
// 		}finally{
// 			l.unlock();
// 		}

// 	}

// 	/**
// 	 * Utilizado por el cliente id cuando sale de los aseos
// 	 * @throws InterruptedException
// 	 *
// 	 */
// 	public void salgoAseo(int id) throws InterruptedException{

// 		l.lock();

// 		try{
// 			nClientes --;
// 			System.out.println("El cliente " + id + " ha salido del baño." + " Clientes en el aseo: " + nClientes);
// 			if (nClientes == 0) {
// 				okLimpiar.signal();
// 			}
// 		}finally{
// 			l.unlock();
// 		}

// 	}

// 	/**
// 	 * Utilizado por el Equipo de Limpieza cuando quiere entrar en los aseos
// 	 * CS: El equipo de trabajo est� solo en los aseos, es decir, espera hasta que no
// 	 * haya ning�n cliente.
// 	 * @throws InterruptedException
// 	 *
// 	 */
//     public void entraEquipoLimpieza() throws InterruptedException{

// 		l.lock();

// 		try{
// 			nEquipos ++;
// 			System.out.println("					El equipo de limpieza quiere entrar.");
// 			while(nClientes > 0 || limpiando){

// 				okLimpiar.await();
// 			}
// 			limpiando = true;
// 			System.out.println("					El equipo de limpieza está trabajando.");
// 		}finally{
// 			l.unlock();
// 		}

// 	}

//     /**
// 	 * Utilizado por el Equipo de Limpieza cuando  sale de los aseos
//      * @throws InterruptedException
// 	 *
// 	 *
// 	 */
//     public void saleEquipoLimpieza() throws InterruptedException{

// 		l.lock();
// 		try{
// 			System.out.println("					El equipo de limpieza ha terminado.");
// 			limpiando = false;
// 			nEquipos --;
// 			if (nEquipos > 0) {
// 				okLimpiar.signal();
// 			}else{
// 				okEntrar.signalAll();
// 			}
// 		}finally{
// 			l.unlock();
// 		}
// 	}
// }