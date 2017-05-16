import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * Created by cs.ucu.edu.ua on 14.05.2017.
 */
public class WriteResult {
    public synchronized void resultToDB(Task task) throws Exception{
        Class.forName("WriteResult");

        String server = "localhost\\SOLEXPRESS";
        int port = 1433;
        String database = "Parallel";
        String Url = "jdbc:sqlserver://"+server+":"+port+";databaseName="+database+";integratedSecurity=true";

        Connection con = DriverManager.getConnection(Url);

        String result = task.getResult();
        String id = ""+(task.getId());
        String sql = "UPDATE MyDB " +
                        " SET Result = ? " +
                        " WHERE ID = ? ";

        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, result);
        pstmt.setString(2, id);
        pstmt.executeUpdate();
    }



}
