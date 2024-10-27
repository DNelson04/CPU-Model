import java.util.ArrayList;
import java.util.List;

public class Clock extends Thread {
    private static Clock instance;
    private int time;
    private boolean running;
    private List<ClockObserver> observers;
    private static int TIME_QUANTUM; // 2 second

    public Clock(int timeQuantum) {
        this.time = 0;
        this.running = true;
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

    public int getTime() {
        return time;
    }

    public void addObserver(ClockObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ClockObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (ClockObserver observer : observers) {
            observer.onUpdate(time);
        }
    }

    public void startClock() {
        this.start();
    }

    public void stopClock() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(TIME_QUANTUM);
                time++;
                System.out.println("Clock time: " + time);
                notifyObservers(); // Notify all observers of the time update
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}