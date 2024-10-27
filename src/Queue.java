public class Queue{
    QNode[] fronts, rears;
    private final int prioLevels;

    public Queue(int numPrioLevels){
        fronts = new QNode[numPrioLevels];
        rears = new QNode[numPrioLevels];
        prioLevels = numPrioLevels;
    }

    boolean isEmpty(){
        for (int i = 0; i < prioLevels; i++) {
            if (fronts[i] != null) return false;
        }
        return true;
    }

    void enqueue(Process process, int priority) {
        if (priority >= prioLevels) {
            return;
        }
        QNode node = new QNode(process);
        if (rears[priority] == null) {
            fronts[priority] = node;
        } else {
            rears[priority].setNext(node);
        }
        rears[priority] = node;
    }

    Process dequeue() {
        for (int i = 0; i < prioLevels; i++) {
            if (fronts[i] != null) {
                Process toReturn = fronts[i].getProcess();
                fronts[i] = fronts[i].getNext();
                if (fronts[i] == null) {
                    rears[i] = null;
                }
                return toReturn;
            }
        }
        return null;
    }

    Process getFront() {
        for (int i = 0; i < prioLevels; i++) {
            if (fronts[i] != null) {
                return fronts[i].getProcess();
            }
        }
        System.out.println("Queue is empty");
        return null;
    }

    Process getRear(int priority) {
        if (priority < prioLevels && rears[priority] != null) {
            return rears[priority].getProcess();
        }
        System.out.println("Invalid priority or queue is empty at this level");
        return null;
    }

}
class QNode{
    private Process process;
    private QNode next;

    public QNode(Process process){
        this.process = process;
        this.next = null;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public QNode getNext() {
        return next;
    }

    public void setNext(QNode next) {
        this.next = next;
    }
}