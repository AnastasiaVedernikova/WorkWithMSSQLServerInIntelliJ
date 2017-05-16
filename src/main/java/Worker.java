import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by cs.ucu.edu.ua on 14.05.2017.
 */
public class Worker implements Runnable {

    private BlockingQueue blockingQueue;
    private Integer last_id;
    private ParallelDataProcessing parallelDataProcessing = new ParallelDataProcessing();
    private WriteResult writeResult = new WriteResult();
    private NewTaskToDB newTaskToDB = new NewTaskToDB();

    public Worker(BlockingQueue blockingQueue, Integer last_id){
        this.blockingQueue = blockingQueue;
        this.last_id = last_id;

    }
    public int getID(){
        return last_id;
    }
    public void run(){
    try {
        while (!blockingQueue.isEmpty()) {
            final File folder = new File("C:/Users/cs.ucu.edu.ua/IdeaProjects/papochka1");
            Task task = parallelDataProcessing.FindJarAndProcess(folder, blockingQueue.remove());
            writeResult.resultToDB(task);
            if (task.getNewTask() != null && !task.getNewTask().isEmpty()) {//покишо new task is one string
                newTaskToDB.strToDB(task); ///update blockque add 1 el
                last_id += 1;
                Task myTask = parallelDataProcessing.GetInfFromBD(last_id);
                blockingQueue.add(myTask);

            }

            System.out.println("---------------------------------------" + Thread.currentThread().getName());
        }
    }catch (Exception e){
        System.out.print("LOL/Error");
    }
    }
}
