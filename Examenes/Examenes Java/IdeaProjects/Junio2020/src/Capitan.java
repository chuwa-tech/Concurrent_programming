import java.util.Random;

public class Capitan extends Thread{
	
	private BarcaM barca;
	private static Random r = new Random();
	public Capitan(BarcaM barca){
		this.barca = barca;
	}
	
	public void run(){
		while (true){
			try {				   
				barca.esperoSuban();
				Thread.sleep(r.nextInt(500));
				barca.finViaje();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
