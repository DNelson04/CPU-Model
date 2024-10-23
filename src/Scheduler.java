public class Scheduler {
    static public int timeQuantum;
    private Queue priority1,priority2,priority3,priority4;

    public Scheduler(int timeQuantum){
        Scheduler.timeQuantum = timeQuantum;
        priority1 = new Queue();
        priority2 = new Queue();
        priority3 = new Queue();
        priority4 = new Queue();
    }

    public Scheduler(){
        this(10);
    }


}

