class Statistics{
    private double avgTurnaround;
    private double avgWaitTime;
    private double avgResponseTime;
    private double throughput;
    private double cpuUtilization;
    private double totalActiveTime;
    private int numProcesses, totalContextSwitches, timeQuantum;

    public Statistics(int quantum){
        this.timeQuantum = quantum;
    }
    //averages can be calculated with (currentaverages/numprocesses) + newprocessinfo/numProcesses
    public synchronized void saveProcessInfo(Process process, long clockTime, int totalContextSwitches){
        numProcesses++;
        totalActiveTime += process.getElapsed();
        updateTurnaround(process);
        updateWaitTime(process);
        updateContextSwitches(totalContextSwitches);
        updateThroughput(clockTime);
        updateUtilization(clockTime);
        updateResponseTime(process);
    }

    private void updateUtilization(long clockTIme) {
        cpuUtilization = totalActiveTime/clockTIme;
    }

    private void updateResponseTime(Process process){
        long responseTime = process.getResponseTime();
        avgResponseTime = ((((avgResponseTime*1000)* numProcesses-1) + responseTime) / numProcesses)/1000;
    }
    private void updateThroughput(long clockTime) {
        throughput = ((double) numProcesses / clockTime) * 60000;
    }

    private void updateContextSwitches(int numContextSwitches) {
        totalContextSwitches = numContextSwitches;
    }

    private void updateWaitTime(Process process) {
        double processWaitTime = process.getWaitTime();
        avgWaitTime = (((avgWaitTime*1000 * (numProcesses - 1)) + processWaitTime) / numProcesses)/1000;
    }

    private void updateTurnaround(Process process) {
        double processTurnaround = process.getTurnaroundTime();
        avgTurnaround = ((((avgTurnaround*1000) * (numProcesses - 1)) + processTurnaround) / numProcesses)/1000;
    }
    public double truncate(double num){
        return Math.floor(num * 1000) / 1000;
    }
    public String toString(){
        return "Cpu Statistics\nQuantum: " + timeQuantum + "\nAverage Turnaround Time: " +
                truncate(avgTurnaround) + "\nAverage Waiting Time: " + truncate(avgWaitTime) + "\nAverage Response Time: " +
                truncate(avgResponseTime) + "\nCPU Utilization: " + truncate(cpuUtilization)*100 +
                "%\nThroughput: " + truncate(throughput) + " processes/min\nTotal Context Switches: " + totalContextSwitches;
    }
}