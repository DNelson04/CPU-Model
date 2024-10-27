import java.util.List;

public class Scheduler implements ClockObserver {
    private Queue queue;


    public Scheduler(List<Process> processList, int numPriorityLevels){
        this.queue = new Queue(numPriorityLevels);
        for(Process process : processList){

        }
    }
    @Override
    public void onUpdate(int time) {

    }


    public Process getNext(){

    }
}
