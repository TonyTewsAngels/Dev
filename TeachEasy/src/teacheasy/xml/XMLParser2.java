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
import teacheasy.xml.handler.DocumentInfoXMLHandler;
import teacheasy.xml.util.XMLNotification;

public class XMLParser2 extends DefaultHandler {
    private Lesson lesson;
    private ArrayList<XMLNotification> errorList;
    private XMLReader xmlReader;
    
    public void parse(String file) throws SAXException, IOException {
        xmlReader = XMLReaderFactory.createXMLReader();
        
        xmlReader.setContentHandler(this);
        
        FileReader reader = new FileReader(file);
        
        xmlReader.parse(new InputSource(reader));
    }
    
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        switch(XMLElement.check(qName.toUpperCase())) {
            case DOCUMENTINFO:
                xmlReader.setContentHandler(new DocumentInfoXMLHandler(xmlReader, this, lesson, errorList));
                break;
            case DEFAULTSETTINGS:
                break;
            case GRADESETTINGS:
                break;
            case SLIDE:
                break;
            default:
                break;
        }
    }
    
    
    
    
    
    
    
    
    
    // #######################################
    public static void main(String args[]) {
        new XMLParser2();
    }
    
    public XMLParser2() {
        lesson = new Lesson();
        errorList = new ArrayList<XMLNotification>();
        
        try {
            parse("testXML/testXML.xml");
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
        
        for(int i = 0; i < errorList.size(); i++) {
            System.out.println(errorList.get(i).toString());
        }
    }
}
