package Fibonacci;

public class Fibonacci implements Runnable{

    private int n;
    private int val;
    private int valAnt;

    public Fibonacci(int i){
        // f1 = ant;
        // f2 = antP;
        n = i;
    }

    public int getVal(){
        return this.val;
    }

    public int getValAnt(){
        return this.valAnt;
    }

    public void run(){
        if(n > 2){

            Fibonacci ant = new Fibonacci(n-1);
          
            // ant.run();
            // antP.run();

            Thread h1 = new Thread(ant);
       
            h1.start();
        
            try{
                h1.join();
            }catch(InterruptedException e){
                e.printStackTrace();
            }


            this.val = ant.getVal() + ant.getValAnt();

            this.valAnt = ant.val;

        }else if (n == 2){
            this.val = 1;
            this.valAnt = 1;
        }else{
            if(n == 1){
                this.val = 1;
                this.valAnt = 0;
            }else{
                System.out.println("Numero debe ser mayor igual a uno");
            }
        }
    }
}