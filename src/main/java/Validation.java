
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;


/**
 * Created by cs.ucu.edu.ua on 04.05.2017.
 */
public class Validation {

    public void validate(String XMLFile, String XSDFile) throws SAXException, IOException
    {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        ((schemaFactory.newSchema(new File(XSDFile))).newValidator()).validate(new StreamSource(new File(XMLFile)));

    }
}
