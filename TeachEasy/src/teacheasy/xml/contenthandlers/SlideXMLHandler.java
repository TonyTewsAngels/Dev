package teacheasy.xml.contenthandlers;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import teacheasy.data.Lesson;
import teacheasy.data.Page;
import teacheasy.xml.XMLElement;
import teacheasy.xml.util.XMLNotification;
import teacheasy.xml.util.XMLUtil;
import teacheasy.xml.util.XMLNotification.Level;

public class SlideXMLHandler extends DefaultHandler {
    private Lesson lesson;
    private ArrayList<XMLNotification> errorList;
    private XMLReader xmlReader;
    private DefaultHandler parent;
    
    private Page page;
    
    public SlideXMLHandler(XMLReader nXMLReader, DefaultHandler nParent, Lesson nLesson, ArrayList<XMLNotification> nErrorList, Attributes slideAttrs) {
        this.xmlReader = nXMLReader;
        this.parent = nParent;
        
        this.lesson = nLesson;
        this.errorList = nErrorList;
        
        page = new Page(lesson.pages.size(), null);
        
        slideStart(slideAttrs);
    }
    
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        switch(XMLElement.check(qName.toUpperCase())) {
            case TEXT:
                xmlReader.setContentHandler(new TextXMLHandler(xmlReader, this, lesson, page, errorList, attrs));
                break;
            case IMAGE:
                xmlReader.setContentHandler(new ImageXMLHandler(xmlReader, this, lesson, page, errorList, attrs));
                break;
            case AUDIO:
                xmlReader.setContentHandler(new AudioXMLHandler(xmlReader, this, lesson, page, errorList, attrs));
                break;
            case VIDEO:
                xmlReader.setContentHandler(new VideoXMLHandler(xmlReader, this, lesson, page, errorList, attrs));
                break;
            case GRAPHIC:
                break;
            case ANSWERBOX:
                break;
            case MULTIPLECHOICE:
                break;
            default:
                break;
        }
    }
    
    public void endElement(String uri, String localName, String qName) {
        switch(XMLElement.check(qName.toUpperCase())) {
            /* If the slide has finished, return to the parent */
            case SLIDE:
                endHandler();
                break;            
            default:
                break;
        }
    }
    
    public void endHandler() {
        lesson.pages.add(page);
        
        xmlReader.setContentHandler(parent);
    }
    
    private void slideStart(Attributes attrs) {
        String backgroundColor = attrs.getValue("backgroundcolor");
        int pn = page.getNumber();
        
        if(backgroundColor == null) {
            errorList.add(new XMLNotification(Level.WARNING, "No background color given for page " + pn+ ". Default used."));
            page.setPageColour(lesson.defaultSettings.getBackgroundColour());
        } else {
            if(XMLUtil.checkColor(backgroundColor)) {
                page.setPageColour(backgroundColor);
            } else {
                errorList.add(new XMLNotification(Level.WARNING, "Page " + pn + "background color invalid color format."));
                page.setPageColour(lesson.defaultSettings.getBackgroundColour());
            }
        }
    }
}
