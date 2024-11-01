public class Process extends Thread implements ClockObserver{
    private int burstTime, priority, priorityEscalation;
    private long lifetime,currentElapsed,finishTime, accumulatedTime, startTime, idleTime;
    private final int arrivalTime, processID;
    private ProcessObserver observer;
    private Clock clock;
    private boolean started, paused = false;


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
        started = true;
        startTime = clock.getElapsedTime();
        accumulatedTime = 0;
        long stopTime;
        while (started) {
            synchronized (this) {
                while (paused) {
                    try {
                        stopTime = clock.getElapsedTime();
                        wait();
                        idleTime += clock.getElapsedTime()-stopTime;
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
            synchronized (this) {
                currentElapsed = clock.getElapsedTime() - idleTime - startTime;
                if (currentElapsed > 1000+accumulatedTime) {
                    decrementBurstTime();
                    accumulatedTime += 1000;
                    System.out.println("Processing... Remaining burst time: " + burstTime);
                }
                if (burstTime == 0) {
                    finishProcess();
                    started = false;
                    System.out.println("Process " + processID + " finished");
                    finishTime = clock.getElapsedTime();
                }
                try {
                    Thread.sleep(50); // Reduce CPU usage by pausing briefly
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Process " + getProcessID() + " interrupted.");
                }
            }
        }
    }
    public void run(){
        process();
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

    public synchronized void decElapsedInSec() {
        this.currentElapsed -= 1000;
    }

    public synchronized long getElapsed(){
        return currentElapsed;
    }

    public int getBurstTime() {
        return this.burstTime;
    }

    //getPriority() is taken by thread

    public int returnPriority() {
        return priority;
    }
    public void increasePriority() {
        if(priority > 1) {
            this.priority = priority--;
        }
    }

    public boolean isStarted() {
        return started;
    }

    public synchronized void pauseProcess(){
        paused = true;
    }

    public synchronized void resumeProcess(){
        paused = false;
        notify();
    }
    public long getLifetime() {
        return lifetime;
    }

    public void setLifetime(long lifetime) {
        this.lifetime = lifetime;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
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

}
