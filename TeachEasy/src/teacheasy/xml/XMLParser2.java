package teacheasy.xml;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import teacheasy.data.Lesson;
import teacheasy.xml.contenthandlers.DocumentInfoXMLHandler;
import teacheasy.xml.contenthandlers.SlideshowXMLHandler;
import teacheasy.xml.util.XMLNotification;
import teacheasy.xml.util.XMLNotification.Level;

public class XMLParser2 extends DefaultHandler {
    private Lesson lesson;
    private ArrayList<XMLNotification> errorList;
    private XMLReader xmlReader;
    
    public XMLParser2() {
        lesson = new Lesson();
        errorList = new ArrayList<XMLNotification>();
    }
    
    public void parse(String file) throws SAXException, IOException {
        xmlReader = XMLReaderFactory.createXMLReader();
        
        xmlReader.setContentHandler(this);
        
        FileReader reader = new FileReader(file);
        
        try {
            xmlReader.parse(new InputSource(reader));
        } catch (SAXParseException e) {
            errorList.add(new XMLNotification(Level.ERROR, "XML File Invalid"));
        }
    }
    
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        switch(XMLElement.check(qName.toUpperCase())) {
            case SLIDESHOW:
                xmlReader.setContentHandler(new SlideshowXMLHandler(xmlReader, this, lesson, errorList));
                break;
            default:
                errorList.add(new XMLNotification(Level.WARNING, "Non-slideshow top level element found, ignored."));
                break;
        }
    }
    
    
    
    
    
    
    
    
    
    // #######################################
    public static void main(String args[]) {
        XMLParser2 xmlParser = new XMLParser2();
        
        try {
            xmlParser.parse("testXML/PV2TestXML.xml");
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
        
        for(int i = 0; i < xmlParser.errorList.size(); i++) {
            System.out.println(xmlParser.errorList.get(i).toString());
        }
        
        System.out.println(XMLNotification.countLevel(xmlParser.errorList, Level.ERROR) + " error(s) during parsing.");
        System.out.println(XMLNotification.countLevel(xmlParser.errorList, Level.WARNING) + " warning(s) during parsing.\n");
            
        xmlParser.lesson.debugPrint();
    }
    
    
}
