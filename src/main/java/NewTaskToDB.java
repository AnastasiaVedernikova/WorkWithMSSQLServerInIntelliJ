import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by cs.ucu.edu.ua on 14.05.2017.
 */
public class NewTaskToDB {
    public synchronized void strToDB(Task task) throws Exception{
    Class.forName("NewTaskToDB");

    String server = "localhost\\SOLEXPRESS";
    int port = 1433;
    String database = "Parallel";
    String Url = "jdbc:sqlserver://"+server+":"+port+";databaseName="+database+";integratedSecurity=true";

    Connection con = DriverManager.getConnection(Url);

    int clientID=task.getClientID();
    int projectID=task.getProjectID();
    String newTask = task.getNewTask();

    String sql = "insert into MyDB (IDClient, IDProject, Data) values (?, ?, ?)";
    PreparedStatement stmt = con.prepareStatement(sql);
    stmt.setInt(1, clientID);
    stmt.setInt(2,projectID);
    stmt.setString(3, newTask);
    stmt.executeUpdate();

    }
}
