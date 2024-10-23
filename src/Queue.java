public class Queue{
    QNode front, rear;

    public Queue(){
        front = null;
        rear = null;
    }

    boolean isEmpty(){
        return front == null && rear == null;
    }

    void enqueue(int pid){
        QNode node = new QNode(pid);
        if(rear == null){
            front = node;
            rear = node;
        }
        rear.setNext(node);
        rear = node;
    }

    int dequeue(){
        if(isEmpty()){
            System.out.println("L");
            return -1;
        }
        int toReturn = front.getPid();
        front = front.getNext();

        if(front == null){
            rear = null;
        }
        return toReturn;
    }

    int getFront(){
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return -1;
        }
        return front.getPid();
    }

    int getRear(){
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return -1;
        }
        return rear.getPid();
    }

}
class QNode{
    private int pid;
    private QNode next;

    public QNode(int pid){
        this.pid = pid;
        this.next = null;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public QNode getNext() {
        return next;
    }

    public void setNext(QNode next) {
        this.next = next;
    }
}