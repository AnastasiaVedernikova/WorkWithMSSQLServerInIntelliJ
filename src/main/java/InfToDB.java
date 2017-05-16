

/**
 * Created by cs.ucu.edu.ua on 04.05.2017.
 */

import java.sql.*;
import java.lang.String;
import java.util.ArrayList;

public class InfToDB {

    public int infToDB(Job job) throws Exception
    /** connect to db
     * insert information from xml to db
     * return last id
     */
    {
        Class.forName("InfToDB");

        String server = "localhost\\SOLEXPRESS";
        int port = 1433;
        String database = "Parallel";
        String Url = "jdbc:sqlserver://"+server+":"+port+";databaseName="+database+";integratedSecurity=true";

        Connection con = DriverManager.getConnection(Url);

        int clientID=job.getClientID();
        int projectID=job.getProjectID();

        String sql = "insert into MyDB (IDClient, IDProject, Data) values ("+clientID+", "+projectID+", ?)";

        PreparedStatement stmt = con.prepareStatement(sql);

        ArrayList<JobItem>items=job.getItems();
        for(int i=0;i<items.size();i++){
            stmt.setString(1, items.get(i).getData());
            stmt.executeUpdate();
        }

        Statement stmt1 = con.createStatement();
        ResultSet rs = stmt1.executeQuery("SELECT IDENT_CURRENT('MyDB')");
        int last_id = 0;
        while (rs.next()){
            last_id = rs.getInt(1);
        }

        stmt.close();
        con.close();
        return last_id;

    }
}
