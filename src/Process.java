public class Process implements ClockObserver{
    private int burstTime, priority, lifetime;
    private final int arrivalTime, processID;
    private boolean running = false;

//implement lifetime by getting currenttime - arrivaltime, update priority accordingly
    public Process(int pid, int arrivalTime, int burstTime, int priority){
        this.processID = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }

    public void run(){
        running = true;
        while(running){

        }
    }
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getLifetime() {
        return lifetime;
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getProcessID() {
        return processID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    @Override
    public void onUpdate(int time) {

    }

}
