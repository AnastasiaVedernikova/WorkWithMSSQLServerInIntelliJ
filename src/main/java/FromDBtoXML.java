

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by cs.ucu.edu.ua on 05.05.2017.
 */
public class FromDBtoXML {
    public ArrayList<ArrayList<String>> getInfoFromDB()throws Exception {
        ArrayList<ArrayList<String>> all = new ArrayList<ArrayList<String>>();
        Class.forName("FromDBtoXML");
        String server = "localhost\\SOLEXPRESS";//Q?
        int port = 1433;
        String database = "Parallel";
        String Url = "jdbc:sqlserver://" + server + ":" + port + ";databaseName=" + database + ";integratedSecurity=true";
        Connection con = DriverManager.getConnection(Url);

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT Result FROM MyDB;");
        ArrayList<String> arrayResults = new ArrayList<String>();
        while(rs.next()) {//через i=2 не можу витягти всі значення
            String result = rs.getString(1);
            arrayResults.add(result);
        }

        Statement stmtD = con.createStatement();
        ResultSet rsD = stmtD.executeQuery("SELECT DATA FROM MyDB");
        ArrayList<String> arrayData = new ArrayList<String>();
        while(rsD.next()) {//через i=2 не можу витягти всі значення
            String data = rsD.getString(1);
            arrayData.add(data);
        }

        Statement stmtC = con.createStatement();
        ResultSet rsC = stmtC.executeQuery("SELECT IDClient FROM MyDB");
        ArrayList<String> arrayClient = new ArrayList<String>();
        while(rsC.next()) {//через i=2 не можу витягти всі значення
            String client = rsC.getString(1);
            arrayClient.add(client);
        }

        Statement stmtP = con.createStatement();
        ResultSet rsP = stmtP.executeQuery("SELECT IDProject FROM MyDB");
        ArrayList<String> arrayProject = new ArrayList<String>();
        while(rsP.next()) {//через i=2 не можу витягти всі значення
            String project = rsP.getString(1);
            arrayProject.add(project);
        }
        all.add(arrayClient);
        all.add(arrayProject);
        all.add(arrayData);
        all.add(arrayResults);

       return all;

    }
    public synchronized void createXML(ArrayList<ArrayList<String>> arr, Integer ID){
        //xml for one string from bd by id
        ArrayList<String> clients = arr.get(0);//id?????ми не зберігаємо зовн id
        ArrayList<String> projects = arr.get(1);
        ArrayList<String> items = arr.get(2);
        ArrayList<String> results = arr.get(3);

        try
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            //root elements
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("job");
            doc.appendChild(rootElement);

            //from elements
            Element from = doc.createElement("from");
            rootElement.appendChild(from);

            Element client = doc.createElement("client");
            from.appendChild(client);
            Attr attr = doc.createAttribute("id");
            attr.setValue(clients.get(ID));
            client.setAttributeNode(attr);

            Element project = doc.createElement("project");
            from.appendChild(project);

            Attr attr1 = doc.createAttribute("id");
            attr1.setValue(projects.get(ID));
            project.setAttributeNode(attr1);

            Element dataset = doc.createElement("dataset");
            rootElement.appendChild(dataset);
            for (int i =0; i<items.size(); i++){
                String l = ""+i;
              //  System.out.print(i+""+l);
                Element item = doc.createElement("item");
                item.appendChild(doc.createTextNode(items.get(i)));
                dataset.appendChild(item);
                Attr attr2 = doc.createAttribute("id");
                attr2.setValue(l);
                item.setAttributeNode(attr2);

                Element rst = doc.createElement("result");
                rst.appendChild(doc.createTextNode(results.get(i)));
                item.appendChild(rst);

            }
           // String[] parts = items.get(ID).split(",");
           // if (parts.length > 1){
//                for (int i= 1; i< parts.length; i++){
//                    String l = ""+i;
//                    Element item = doc.createElement("item");
//                    dataset.appendChild(item);
//                    Attr attr2 = doc.createAttribute("id");
//                    attr2.setValue(l);
//                    item.setAttributeNode(attr2);
//
//                    String[] partsRes = results.get(ID).split(",");
//                    if (partsRes.length > 1) {
//                        for (int k = 0; k < partsRes.length; k++) {
//                            Element rst = doc.createElement("result");
//                            rst.appendChild(doc.createTextNode(partsRes[k]));
//                            item.appendChild(rst);
//                        }
//                    }else{
//                        Element rst = doc.createElement("result");
//                        rst.appendChild(doc.createTextNode(partsRes[0]));
//                        item.appendChild(rst);
//                    }
//
//                }
//            }else {
//
//                Element item = doc.createElement("item");
//                dataset.appendChild(item);
//
//                Attr attr2 = doc.createAttribute("id");
//                attr2.setValue("1");
//                item.setAttributeNode(attr2);
//
//                String[] partsRes = results.get(ID).split(",");
//                if (partsRes.length > 1) {
//                    for (int i = 0; i < partsRes.length; i++) {
//                        Element rst = doc.createElement("result");
//                        rst.appendChild(doc.createTextNode(partsRes[i]));
//                        item.appendChild(rst);
//                    }
//                }else{
//                    Element rst = doc.createElement("result");
//                    rst.appendChild(doc.createTextNode(partsRes[0]));
//                    item.appendChild(rst);
//                }
//
//                }


            //write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result =  new StreamResult(new File("C:\\Users\\cs.ucu.edu.ua\\IdeaProjects\\BD\\tests.xml"));
            transformer.transform(source, result);

            System.out.println("Done");

        }catch(ParserConfigurationException pce){
            pce.printStackTrace();
        }catch(TransformerException tfe){
            tfe.printStackTrace();
        }

    }
}
