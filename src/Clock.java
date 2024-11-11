import java.util.ArrayList;
import java.util.List;

public class Clock extends Thread {
    private static Clock instance;
    private int quantum, quantumCount;
    private int time;
    private long startTime;
    private boolean running;
    private List<ClockObserver> observers;
    private static int TIME_QUANTUM; // 2 second

    public Clock(int timeQuantum) {
        this.quantum = 0;
        this.quantumCount = 0;
        this.time = 0;
        this.running = false;
        this.observers = new ArrayList<>();
        TIME_QUANTUM = timeQuantum;
    }

    public static synchronized Clock getInstance() {
        return instance;
    }

    public static synchronized Clock getInstance(int timeQuantum) {
        if (instance == null) {
            instance = new Clock(timeQuantum);
        }
        return instance;
    }

    public int getQuantum() {
        return TIME_QUANTUM;
    }

    public int getTime() {
        return time;
    }

    public synchronized long getElapsedTime() {
        return System.currentTimeMillis() - startTime;
    }

    public void addObserver(ClockObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ClockObserver observer) {
        observers.remove(observer);
    }
    private void quantum(int quantum){
        for (ClockObserver observer : observers) {
            observer.notifyQuantum(quantum);
        }
    }
    private void notifyObservers() {
        for (ClockObserver observer : observers) {
            observer.onUpdate(time);
        }
    }

    public void startClock() {
        this.startTime = System.currentTimeMillis();
        notifyObservers();
        quantum(0);
        running = true;
        this.start();
    }

    public void stopClock() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000);
                quantum++;
                time++;
                notifyObservers();
                if(quantum == TIME_QUANTUM){
                    quantumCount++;
                    quantum(quantumCount);
                    quantum = 0;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void resetQuantum() {
        quantum = 0;
    }
}