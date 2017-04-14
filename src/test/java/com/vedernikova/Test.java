package com.vedernikova;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Test {
    public static void main(String[] args) {
        try {


            // Note :
            // • SQL Server's port no can be found by using TCPView s/w
            // OR
            // • You can also set a fixed port for the server in Sql server TCP/IP
            // properties
            // • Sql Server's TCP/IP should be enabled first for this
            // • A 'SQL' user should be created and GRANTED access to the Database
            // • Rest is just as normal JDBC

            Class.forName("com.vedernikova.Test");
            System.out.println("# - Driver Loaded");


            String server = "localhost\\SOLEXPRESS";//Q?
            int port = 1433;
//            String user = "paulo"; // Sql server username
//            String password = "paulo";
            String database = "Parallel";

            String Url = "jdbc:sqlserver://"+server+":"+port+";databaseName="+database+";integratedSecurity=true";
          //jdbc:sqlserver://localhost\SOLEXPRESS:1433;databaseName=Parallel
           // System.out.print(Url);
            Connection con = DriverManager.getConnection(Url);
            System.out.println("# - Connection Obtained");

            Statement stmt = con.createStatement();
            System.out.println("# - Statement Created");

            ResultSet rs = stmt.executeQuery("SELECT IDClient FROM Client;");
            System.out.println("# - Query Executed");

            if(rs.next()) {
                System.out.println("Client Count : "+rs.getInt(1));
            }

            rs.close();
            stmt.close();
            con.close();
            System.out.println("# - Resources released");
        } catch (Exception ex) {
            System.out.println("Error : "+ex);
        }
    }
}
