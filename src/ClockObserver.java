public interface ClockObserver {
    void onUpdate(int time);
    void notifyQuantum(int quantum);
}
