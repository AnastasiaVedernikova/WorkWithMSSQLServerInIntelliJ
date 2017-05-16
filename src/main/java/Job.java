
import java.util.ArrayList;

/**
 * Created by cs.ucu.edu.ua on 09.05.2017.
 */
public class Job {

    public int getClientID() {
        return ClientID;
    }

    protected int ClientID;

    protected int ProjectID;

    public int getProjectID() {
        return ProjectID;
    }

    public ArrayList<JobItem> getItems() {
        return Items;
    }

    ArrayList<JobItem> Items;


    public  Job(int ClientID, int ProjectID){
        this.ClientID=ClientID;
        this.ProjectID=ProjectID;
        this.Items=new ArrayList<JobItem>();
    }

}
