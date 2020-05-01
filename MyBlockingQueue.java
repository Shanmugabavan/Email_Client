import java.util.LinkedList;
import java.util.List;

public class MyBlockingQueue {
    private List queue = new LinkedList();
    private int  limit;

    public MyBlockingQueue(){
        this.limit = 10;
    }
    public void setLimit(int limit){
        this.limit=limit;
    }


    public synchronized void enqueue(Received_Mail mail)
            throws InterruptedException  {
        while(this.queue.size() == this.limit) {
            wait();
        }
        this.queue.add(mail);
        if(this.queue.size() == 1) {
            notifyAll();
        }
    }


    public synchronized Received_Mail dequeue()
            throws InterruptedException{
        while(this.queue.size() == 0){
            wait();
        }
        if(this.queue.size() == this.limit){
            notifyAll();
        }
        return (Received_Mail)this.queue.remove(0);
    }
}
