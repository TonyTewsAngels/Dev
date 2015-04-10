package teacheasy.xml;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import teacheasy.data.Lesson;
import teacheasy.xml.util.XMLNotification;

public class XMLParser2 extends DefaultHandler {
    private Lesson lesson;
    private ArrayList<XMLNotification> errorList;
    
    public XMLParser2() {
        try {
            parse("testXML/testXML.xml");
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }
    
    public void parse(String file) throws SAXException, IOException {
        XMLReader xmlReader = XMLReaderFactory.createXMLReader();
        
        xmlReader.setContentHandler(this);
        
        FileReader reader = new FileReader(file);
        
        xmlReader.parse(new InputSource(reader));
    }
    
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        System.out.println(qName);
    }
    
    public static void main(String args[]) {
        new XMLParser2();
    }
}
