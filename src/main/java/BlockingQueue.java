import javafx.concurrent.*;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by cs.ucu.edu.ua on 13.05.2017.
 */
public class BlockingQueue {
    private LinkedBlockingQueue<Task> queue = new LinkedBlockingQueue<Task>();


    public synchronized void add(Task ClientData)throws InterruptedException{
            queue.put(ClientData);

    }
    public synchronized Task remove()throws InterruptedException{
       return queue.poll();

    }
    public synchronized boolean isEmpty(){
        if (queue.size() == 0){
            return true;
        }
        return false;
    }
}
