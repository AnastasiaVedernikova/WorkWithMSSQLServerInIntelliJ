package com.vedernikova;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.Array;
import java.sql.*;
import java.util.ArrayList;
import java.lang.String;


/**
 * Created by cs.ucu.edu.ua on 03.05.2017.
 */
public class XMLtoDB {

    private static ArrayList<ArrayList<String>>  getInformation (String filename){
        ArrayList<ArrayList<String>> all_list  = new ArrayList<ArrayList<String>>();
        try{
            ArrayList<String> my_names = new ArrayList<String>();
            ArrayList<String> my_prices = new ArrayList<String>();
            ArrayList<String> my_calories = new ArrayList<String>();
            ArrayList<String> my_descriptions = new ArrayList<String>();

            File file = new File(filename);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);

            document.getDocumentElement ().normalize ();
//            System.out.println ("Root element of the doc is " +
//                    document.getDocumentElement().getNodeName());

            NodeList listOfFood = document.getElementsByTagName("FOOD");

            for(int s=0; s<listOfFood.getLength() ; s++) {
                Node firstFoodNode = listOfFood.item(s);
                if (firstFoodNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element firstFoodElement = (Element) firstFoodNode;
                    //names
                    NodeList NameList = firstFoodElement.getElementsByTagName("name");
                    Element NameElement = (Element)NameList.item(0);
                    NodeList textFNList = NameElement.getChildNodes();
//                    System.out.println("First Name : " +
//                            ((Node)textFNList.item(0)).getNodeValue().trim());
                    my_names.add(((Node)textFNList.item(0)).getNodeValue().trim());
                    //prices
                    NodeList PriceList = firstFoodElement.getElementsByTagName("price");
                    Element PriceElement = (Element)PriceList.item(0);
                    NodeList text1FNList = PriceElement.getChildNodes();
                    my_prices.add(((Node)text1FNList.item(0)).getNodeValue().trim());
                    //descriptions
                    NodeList DescriptionList = firstFoodElement.getElementsByTagName("description");
                    Element DescElement = (Element)DescriptionList.item(0);
                    NodeList text2FNList = DescElement.getChildNodes();
                    my_descriptions.add(((Node)text2FNList.item(0)).getNodeValue().trim());
                    //calories
                    NodeList CaloriesList = firstFoodElement.getElementsByTagName("calories");
                    Element CalElement = (Element)CaloriesList.item(0);
                    NodeList text3FNList = CalElement.getChildNodes();
                    my_calories.add(((Node)text3FNList.item(0)).getNodeValue().trim());

                    all_list.add(my_names);
                    all_list.add(my_prices);
                    all_list.add(my_descriptions);
                    all_list.add(my_calories);

                }
            }

//            for (String i: my_calories) {
//                System.out.print(i);
//            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error reading configuration file:");
        }
        return all_list;

    }
    public void at() {
        try {

            ArrayList<ArrayList<String>> all_list = getInformation("my_xml.xml");
            ArrayList<String> my_names = all_list.get(0);
            StringBuilder sb = new StringBuilder();
            for (String s : my_names)
            {
                    sb.append(s);
                    sb.append("\t");
            }

//            ArrayList<String> my_prices = all_list.get(1);
//            ArrayList<String> my_descriptions = all_list.get(2);
//            ArrayList<String> my_calories = all_list.get(3);


            Class.forName("com.vedernikova.XMLtoDB");
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
            String name = sb.toString();
          //  System.out.print(name);

            PreparedStatement stmt2 = con.prepareStatement("insert into MyDB (IDClient, Data) values (2, ?)");
            stmt2.setString(1, name);
            stmt2.executeUpdate();


          //  stmt1.executeUpdate("INSERT INTO MyDB " + "VALUES (2, " + a + ")");//додаєм інфу в табл
//
//            ResultSet rs = stmt.executeQuery("SELECT Data FROM MyDB;"); ///дістаєм інфу з таблиці
////            stmt.executeQuery("DELETE FROM MyDB\n" +
////                    "WHERE Data = '11/10/2016';");
//            String sql = "DELETE FROM MyDB " +            //видаляєм інфу з таблиці
//                    "WHERE ID = 1";
//           stmt1.executeUpdate(sql);
//            System.out.println("# - Query Executed");
//
//            if(rs.next()) {
//                System.out.println("Client Count : "+rs.getString(1));
//            }


//            rs.close();
            stmt.close();
            stmt1.close();
            stmt2.close();
            con.close();
            System.out.println("# - Resources released");
        } catch (Exception ex) {
            System.out.println("Error : "+ex);
        }
    }
}
