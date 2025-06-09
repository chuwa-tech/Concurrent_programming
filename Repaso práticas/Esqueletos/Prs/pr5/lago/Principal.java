package lago;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Lago l = new Lago(0);
		Thread presa_0 = new Thread(new Presa(0, l, 1000));
		Thread presa_1 = new Thread(new Presa(1, l, 1000));
		Thread rio_0 = new Thread(new Rio(0, l, 1000));
		Thread rio_1 = new Thread(new Rio(1, l, 1000));

		presa_0.start();
		presa_1.start();
		rio_0.start();
		rio_1.start();
		try {
			presa_0.join();
			presa_1.join();
			rio_0.join();
			rio_1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("El nivel es " + l.nivel());
	}

}
