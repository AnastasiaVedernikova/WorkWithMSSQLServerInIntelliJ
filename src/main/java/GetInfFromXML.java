

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;

/**
 * Created by cs.ucu.edu.ua on 13.05.2017.
 */
public class GetInfFromXML {
    public Job getInformation (String filename) throws SAXException, javax.xml.parsers.ParserConfigurationException, IOException
    {
        int clientID;
        int projectID;

        File file = new File(filename);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);

        document.getDocumentElement().normalize();

        NodeList FromList = document.getElementsByTagName("from");
        Node FromNode = FromList.item(0);
        Element FromElement = (Element) FromNode;

        //client
        NodeList ClientList = FromElement.getElementsByTagName("client");
        Node node = ClientList.item(0);
        Attr attr = (Attr) node.getAttributes().getNamedItem("id");
        clientID = Integer.parseInt(attr.getValue());

        //project
        NodeList ProjectList = FromElement.getElementsByTagName("project");
        Node node1 = ProjectList.item(0);
        Attr attr1 = (Attr) node1.getAttributes().getNamedItem("id");
        projectID = Integer.parseInt(attr1.getValue());

        Job job=new Job(clientID, projectID);

        //items

        NodeList DataList = document.getElementsByTagName("item");
        for (int i=0; i< DataList.getLength(); i++) {
            Node itemNode = DataList.item(i);
            Element itemEl = (Element)DataList.item(i);

            Attr idAttr = (Attr) itemNode.getAttributes().getNamedItem("id");
            int itemId = Integer.parseInt(idAttr.getValue());

            JobItem ji=new JobItem(itemId, itemEl.getFirstChild().getNodeValue());
            job.getItems().add(ji);

        }
        return job;

    }
}
