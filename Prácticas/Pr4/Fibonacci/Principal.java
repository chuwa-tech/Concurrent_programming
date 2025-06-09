package Fibonacci;

public class Principal {
    public static void main(String args[]) throws InterruptedException{
        int n = 6;

        Fibonacci res = new Fibonacci(n);
        Thread v = new Thread(res);

        v.start();
        v.join();

        System.out.println("El valor de la sucesión de Fibonacci para " + n + " es "+ res.getVal());
    }
}
