

/**
 * Created by cs.ucu.edu.ua on 09.05.2017.
 */
public class JobItem {

    public int getID() {
        return ID;
    }

    protected int ID;

    public String getData() {
        return Data;
    }

    protected String Data;
    public JobItem(int ID, String Data){
        this.ID=ID;
        this.Data=Data;
    }
}
