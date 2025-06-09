package lago;

public class Rio implements Runnable {
    private int id;
    private Lago l;
    private int verter;

    public Rio(int id, Lago l, int verter) {
        this.id = id;
        this.l = l;
        this.verter = verter;
    }

    public void run() {
        try{
            for (int i = 0; i < verter; i++)
                if (id == 0)
                    l.incRio0();
                else
                    l.incRio1();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
