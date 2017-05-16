
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by cs.ucu.edu.ua on 04.05.2017.
 */
public class ImitationOfchangedDB {
    public void changeDB()throws Exception
    {
        Class.forName("ImitationOfchangedDB");

        String server = "localhost\\SOLEXPRESS";//Q?
        int port = 1433;
        String database = "Parallel";
        String Url = "jdbc:sqlserver://"+server+":"+port+";databaseName="+database+";integratedSecurity=true";
        Connection con = DriverManager.getConnection(Url);

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT Data FROM MyDB;");

        Statement stmt1 = con.createStatement();
        ResultSet rs1 = stmt1.executeQuery("SELECT IDClient FROM MyDB;");

        Statement stmt2 = con.createStatement();
        ResultSet rs2 = stmt2.executeQuery("SELECT IDProject FROM MyDB;");

        ArrayList<String> array = new ArrayList<String>();
        while(rs.next()) {
            String result = "";
            result = "44" + rs.getString(1);
            array.add(result);

        }
        ArrayList<String> array1 = new ArrayList<String>();
        while(rs1.next()) {
            String result = "";
            result = rs1.getString(1);
            array1.add(result);
        }
        ArrayList<String> array2 = new ArrayList<String>();
        while(rs2.next()) {
            String result = "";
            result = rs2.getString(1);
            array2.add(result);
        }

        for (int i=0; i < array.size(); i++){
        String my_index = ""+(i + 1);
        String sql =
                "UPDATE MyDB " +
                        " SET Result = ? " +
                        " WHERE ID = ? ";

        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, array.get(i));
        pstmt.setString(2, my_index);
        pstmt.executeUpdate();
        }

        String sql1 = "insert into MyDB (IDClient, IDProject, Data) values (?, ?, ?)";

        PreparedStatement stmt4 = con.prepareStatement(sql1);

        for(int i=0;i<array.size();i++){
            stmt4.setString(1, array1.get(i));
            stmt4.setString(2, array2.get(i));
            stmt4.setString(3, array.get(i));
            stmt4.executeUpdate();
        }

        rs.close();
        stmt.close();

    }
}
