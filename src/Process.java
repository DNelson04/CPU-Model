public class Process extends Thread implements ClockObserver{
    private int burstTime, priority, priorityEscalation;
    private long lifetime;
    private final int arrivalTime, processID;
    private ProcessObserver observer;
    private Clock clock;
    private boolean running, started = false;

//implement lifetime by getting currenttime - arrivaltime, update priority accordingly
    public Process(int pid, int arrivalTime, int burstTime, int priority){
        this.processID = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.priorityEscalation = 0;
        clock = Clock.getInstance();
    }
    public void process(){
        while (started){
            running = true;
            long startTime = clock.getElapsedTime();
            while (running) {
                long elapsed = clock.getElapsedTime() - startTime;
                if (elapsed > 1000 + startTime) {
                    decrementBurstTime();
                    startTime = elapsed;
                }
                if (burstTime == 0) {
                    finishProcess();
                    running = false;
                }
                try {
                    Thread.sleep(50); // Reduce CPU usage by pausing briefly
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Process " + getProcessID() + " interrupted.");
                }
            }
            while(!running){

            }
        }
    }
    public void run(){
        started = true;
        process();
    }

    public void halt(){
        running = false;
    }

    private void finishProcess() {
        observer.finishProcess();
    }

    public void setObserver(ProcessObserver observer) {
        this.observer = observer;
    }

    public synchronized void decrementBurstTime() {
        this.burstTime--;
    }

    public synchronized int getBurstTime() {
        return this.burstTime;
    }

    public int retPriority() {
        return priority;
    }

    public void increasePriority() {
        if(priority > 1) {
            this.priority = priority--;
        }
    }

    public long getLifetime() {
        return lifetime;
    }

    public void setLifetime(long lifetime) {
        this.lifetime = lifetime;
    }

    public int getProcessID() {
        return processID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    @Override
    public void onUpdate(int time) {
        this.lifetime = clock.getElapsedTime() - arrivalTime;
        priorityEscalation++;
        if(priorityEscalation > 15){
            increasePriority();
            priorityEscalation = 0;
        }
    }

    @Override
    public void notifyQuantum(int quantum) {

    }

    @Override
    public String toString() {
        return "Process{" +
                "processID=" + processID +
                ", arrivalTime=" + arrivalTime +
                ", burstTime=" + burstTime +
                ", priority=" + priority +
                '}';
    }

    public boolean isStarted() {
        return started;
    }
}
