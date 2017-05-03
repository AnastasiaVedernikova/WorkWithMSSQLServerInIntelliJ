package com.vedernikova;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Test {
    public static void main(String[] args) {
        XMLtoDB ob = new XMLtoDB();
        ob.at();

        try {

            Class.forName("com.vedernikova.Test");
            System.out.println("# - Driver Loaded");

            String server = "localhost\\SOLEXPRESS";//Q?
            int port = 1433;
            String database = "Parallel";
            String Url = "jdbc:sqlserver://"+server+":"+port+";databaseName="+database+";integratedSecurity=true";
          //jdbc:sqlserver://localhost\SOLEXPRESS:1433;databaseName=Parallel
           // System.out.print(Url);
            Connection con = DriverManager.getConnection(Url);
            System.out.println("# - Connection Obtained");

            Statement stmt = con.createStatement();
            Statement stmt1 = con.createStatement();
            System.out.println("# - Statement Created");


            stmt1.executeUpdate("INSERT INTO MyDB " + "VALUES (2, 'nhh')");//додаєм інфу в табл

            ResultSet rs = stmt.executeQuery("SELECT Data FROM MyDB;"); ///дістаєм інфу з таблиці
//            stmt.executeQuery("DELETE FROM MyDB\n" +
//                    "WHERE Data = '11/10/2016';");
            String sql = "DELETE FROM MyDB " +            //видаляєм інфу з таблиці
                    "WHERE ID = 1";
            stmt1.executeUpdate(sql);
            System.out.println("# - Query Executed");

            if(rs.next()) {
                System.out.println("Client Count : "+rs.getString(1));
            }


            rs.close();
            stmt.close();
            stmt1.close();
            con.close();
            System.out.println("# - Resources released");
        } catch (Exception ex) {
            System.out.println("Error : "+ex);
        }
   }
}
