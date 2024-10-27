import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CPU extends Thread implements ClockObserver{
    private Scheduler scheduler;
    private List<Process> processes;
    private boolean running;
    private Process runningProcess;
    private Clock clock;

    public CPU(String processFile, int timeQuantum){
        this.processes = readProcessesFromCSV(processFile);
        this.scheduler = new Scheduler(processes,getHighestPriority(processes));
        this.clock = Clock.getInstance(timeQuantum);
        clock.addObserver(this);
        clock.startClock();
    }

    private int getHighestPriority(List<Process> processes) {
        if (processes == null || processes.isEmpty()) {
            throw new IllegalArgumentException("Process list cannot be null or empty");
        }

        int highestPriority = Integer.MAX_VALUE;
        for (Process process : processes) {
            if (process.getPriority() < highestPriority) {
                highestPriority = process.getPriority();
            }
        }
        return highestPriority;
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

    @Override
    public void onUpdate(int time) {
        if(runningProcess == null){
            runningProcess = scheduler.getNext();
            lock = true;
        } else if () {

        }
    }
}


