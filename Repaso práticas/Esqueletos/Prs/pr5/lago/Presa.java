package lago;

public class Presa implements Runnable {
	private int id;
	private Lago l;
	private int drenar;

	public Presa(int id, Lago l, int drenar) {
		this.id = id;
		this.l = l;
		this.drenar = drenar;
	}

	public void run() {
		try{
			for (int i = 0; i < drenar; i++)
				if (id == 0)
					l.decPresa0();
				else
					l.decPresa1();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}
