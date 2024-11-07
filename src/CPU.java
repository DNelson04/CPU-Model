import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CPU extends Thread implements ProcessObserver{
    private Scheduler scheduler;
    private boolean lock;
    private Process runningProcess;
    private Clock clock;
    private Statistics statistics;

    public CPU(String processFile, int timeQuantum){
        this.clock = Clock.getInstance(timeQuantum);
        List<Process> processes = readProcessesFromCSV(processFile);
        this.scheduler = new Scheduler(processes,getHighestPriority(processes));
        clock.addObserver(scheduler);
    }

    public void run(){
        clock.startClock();
    }


    public boolean isLocked() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    private int getHighestPriority(List<Process> processes) {
        return processes.stream()
                .map(Process::returnPriority)
                .max(Integer::compareTo)
                .orElseThrow(() -> new IllegalArgumentException("Empty process list"));
    }

    public static List<Process> readProcessesFromCSV(String filePath) {
        List<Process> processes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int pid = Integer.parseInt(values[0]);
                int arrivalTime = Integer.parseInt(values[1]);
                int burstTime = Integer.parseInt(values[2]);
                int priority = Integer.parseInt(values[3]);
                processes.add(new Process(pid, arrivalTime, burstTime, priority));
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null;
        }
        return processes;
    }
    //Logic for thread handling
    public void initiateProcess(){
        runningProcess.setObserver(this);
        if (runningProcess.isStarted()){
            runningProcess.resumeProcess();
        }else {
            runningProcess.start();
        }
    }
    @Override
    public void finishProcess() {
        clock.resetQuantum();
        setLock(false);
        runningProcess = scheduler.dequeue();
        if (runningProcess != null) {
            initiateProcess();
            setLock(true);
        }
    }

    class Scheduler implements ClockObserver {
        private final Queue queue;
        private final List<Process> processes;


        public Scheduler(List<Process> processList, int numPriorityLevels){
            this.queue = new Queue(numPriorityLevels);
            this.processes = processList;
        }
        @Override
        public void onUpdate(int time) {
            System.out.println("time: " + time);
            for(Process process : processes){
                if(process.getArrivalTime() == time){
                    System.out.println("Process arriving: " + process.getProcessID());
                    queue.enqueue(process);
                }
            }
            System.out.println("onUpdate() : running process " + (runningProcess != null ? runningProcess.getProcessID() : "None")
                    + ", remaining time: " + (runningProcess != null ? runningProcess.getBurstTime() : "N/A"));
        }

        @Override
        public void notifyQuantum(int quantum) {
            // Check if the CPU is currently locked on a running process
            if (isLocked() && runningProcess != null) {
                System.out.println("Stopping current process: " + runningProcess.getProcessID());
                runningProcess.pauseProcess();
                queue.enqueue(runningProcess); // Re-enqueue the current process
                runningProcess = null;
                setLock(false); // Release the lock
            }

            // Dequeue the next process if available
            runningProcess = dequeue();

            if (runningProcess != null) {
                System.out.println("notifyQuantum() : process switched to " + runningProcess.getProcessID()
                        + ", remaining time: " + runningProcess.getBurstTime());
                initiateProcess(); // Process the dequeued process
                setLock(true); // Lock the CPU
            } else {
                System.out.println("No process to switch to. Queue is empty.");
                setLock(false);
                clock.stopClock();
            }
        }
        public Process dequeue(){
            return queue.dequeue();
        }
        public void enQueue(Process process){
            queue.enqueue(process);
        }
    }

    class Statistics{
        private double avgTurnaround;
        private double avgWaitTime;
        private double avgContextSwitches;
        private double throughput;
        private double cpuUtilization;
        private int numProcesses;

        public Statistics(){

        }
        //averages can be calculated with (currentaverages/numprocesses) + newprocessinfo/numProcesses
        private void saveProcessInfo(Process process){
            numProcesses++;
            updateTurnaround(process);
            updateWaitTime(process);
            updateContextSwitches(process);
            updateThroughput(process);
            updateUtilization(process);
        }

        private void updateUtilization(Process process) {

        }

        private void updateThroughput(Process process) {

        }

        private void updateContextSwitches(Process process) {

        }

        private void updateWaitTime(Process process) {

        }

        private void updateTurnaround(Process process) {

        }
    }
}


