public class CPU {
    private Process process;
    private Scheduler scheduler;
    private Clock clock;

    public CPU(){
        clock = new Clock();

    }

}

class Clock extends Thread{
    private int time;

    public Clock(){
        this.time = 0;
    }

    @Override
    public void start(){
        while(time < 100){
            try {
                Thread.sleep(Scheduler.timeQuantum);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            time++;
        }
    }
}