package teacheasy.xml.contenthandlers;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import teacheasy.data.MultipleChoiceObject;
import teacheasy.data.Lesson;
import teacheasy.data.MultipleChoiceObject.MultiChoiceType;
import teacheasy.data.MultipleChoiceObject.Orientation;
import teacheasy.data.Page;
import teacheasy.xml.XMLElement;
import teacheasy.xml.util.XMLNotification;
import teacheasy.xml.util.XMLUtil;
import teacheasy.xml.util.XMLNotification.*;

public class MultipleChoiceXMLHandler extends DefaultHandler{
    private Lesson lesson;
    private Page page;
    private MultipleChoiceObject multipleChoice;
    
    private ArrayList<XMLNotification> errorList;
    private XMLReader xmlReader;
    private DefaultHandler parent;
    
    public MultipleChoiceXMLHandler(XMLReader nXMLReader, DefaultHandler nParent, Lesson nLesson, Page nPage, ArrayList<XMLNotification> nErrorList, Attributes multipleChoiceAttrs) {
        this.xmlReader = nXMLReader;
        this.parent = nParent;
        
        this.lesson = nLesson;
        this.page = nPage;
        this.errorList = nErrorList;
        
        multipleChoiceStart(multipleChoiceAttrs);
    }
    
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        switch(XMLElement.check(qName.toUpperCase())) {
            case ANSWER:
                xmlReader.setContentHandler(new AnswerXMLHandler(xmlReader, this, lesson, page, multipleChoice, errorList, attrs));
                break;
            default:
                break;
        }
    }
    
    public void endElement(String uri, String localName, String qName) {
        switch(XMLElement.check(qName.toUpperCase())) {
            /* If the image element has finished, return to the parent */
            case MULTIPLECHOICE:
                endHandler();
                break;            
            default:
                break;
        }
    }
    
    public void endHandler() {
        page.pageObjects.add(multipleChoice);
        
        xmlReader.setContentHandler(parent);
    }
    
    public void multipleChoiceStart(Attributes attrs) {
        String xStartStr = attrs.getValue("xstart");
        String yStartStr = attrs.getValue("ystart");
        String typeStr = attrs.getValue("type");
        String orientationStr = attrs.getValue("orientation");
        String marksStr = attrs.getValue("marks");
        String retryStr = attrs.getValue("retry");
        
        float xStart = XMLUtil.checkFloat(xStartStr, 0.0f, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Multiple Choice) X Start ");
        
        float yStart = XMLUtil.checkFloat(yStartStr, 0.0f, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Multiple Choice) Y Start ");
        
        int marks = XMLUtil.checkInt(marksStr, 0, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Multiple Choice) Marks ");
        
        if(typeStr == null) {
            errorList.add(new XMLNotification(Level.ERROR,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Multiple Choice) Type missing."));
            typeStr = new String("line");
        }
        
        MultiChoiceType type = MultiChoiceType.check(typeStr.toUpperCase());
        
        if(orientationStr == null) {
            errorList.add(new XMLNotification(Level.ERROR,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Multiple Choice) Orientation missing."));
            orientationStr = new String("vertical");
        }
        
        Orientation orientation = Orientation.check(orientationStr.toUpperCase());
        
        boolean retry = XMLUtil.checkBool(retryStr, true, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Multiple Choice) Retry setting ");
        
        multipleChoice = new MultipleChoiceObject(xStart, yStart, orientation, type, marks, retry);
    }
}
