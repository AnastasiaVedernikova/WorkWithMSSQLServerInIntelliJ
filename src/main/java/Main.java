


import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by cs.ucu.edu.ua on 13.05.2017.
 */
public class Main {
    public static void main(String[] args) {
        ParallelDataProcessing parallelDataProcessing = new ParallelDataProcessing();
        InfToDB InfToDB = new InfToDB();
        Validation Validation = new Validation();
        BlockingQueue blockingQueue = new BlockingQueue();
        GetInfFromXML GetInfFromXML = new GetInfFromXML();
        NewTaskToDB newTaskToDB = new NewTaskToDB();
       // ImitationOfchangedDB imitationOfchangedDB = new ImitationOfchangedDB();
        FromDBtoXML fromDBtoXML = new FromDBtoXML();
        WriteResult writeResult = new WriteResult();
        boolean flag = true;
        try {
            int last_id=0;

           Validation.validate("data.xml", "data.xsd");
            Job xml = GetInfFromXML.getInformation("data.xml");
            last_id = InfToDB.infToDB(xml);


            for (int i = 1; i<= last_id; i++){//fist time
                Task str = parallelDataProcessing.GetInfFromBD(i);//in for
                 str.setId(i);
                blockingQueue.add(str);
            } //created blocking queue from bd

            Worker w1 = new Worker(blockingQueue,last_id);
            Worker w2 = new Worker(blockingQueue,last_id);
            Thread t = new Thread(w1);
            Thread t2 = new Thread(w2);
            t.start();
            t2.start();
//            if (blockingQueue.isEmpty()){
//                t.wait(100);
//                t2.wait(100);
//            }
            t.join();
            t2.join();
            System.out.print(w1.getID()+" "+w2.getID());


//-------------------------------paralel
//            while(!blockingQueue.isEmpty()){
//                final File folder = new File("C:/Users/cs.ucu.edu.ua/IdeaProjects/papochka1");
//                Task task = parallelDataProcessing.FindJarAndProcess(folder, blockingQueue.remove());
//                writeResult.resultToDB(task);
//                if (task.getNewTask() != null && !task.getNewTask().isEmpty()) {//покишо new task is one string
//                    newTaskToDB.strToDB(task); ///update blockque add 1 el
//                    last_id += 1;
//                    Task myTask = parallelDataProcessing.GetInfFromBD(last_id);
//                    blockingQueue.add(myTask);
//
//                }

 //           }
            //---------------------------------------------------------
            ArrayList<ArrayList<String>> ar = fromDBtoXML.getInfoFromDB();
            fromDBtoXML.createXML(ar, 3);
        } catch(SAXException e) {
            flag = false;
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
            flag=false;
        }catch (javax.xml.parsers.ParserConfigurationException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
       // System.out.print("xml is valid: "+flag);

    }

}
