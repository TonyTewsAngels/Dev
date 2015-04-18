package teacheasy.xml.contenthandlers;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import teacheasy.data.AnswerBoxObject;
import teacheasy.data.Lesson;
import teacheasy.data.Page;
import teacheasy.xml.XMLElement;
import teacheasy.xml.util.XMLNotification;
import teacheasy.xml.util.XMLUtil;
import teacheasy.xml.util.XMLNotification.*;

public class AnswerBoxXMLHandler extends DefaultHandler{
    private Lesson lesson;
    private Page page;
    private AnswerBoxObject answerBox;
    
    private ArrayList<XMLNotification> errorList;
    private XMLReader xmlReader;
    private DefaultHandler parent;
    
    public AnswerBoxXMLHandler(XMLReader nXMLReader, DefaultHandler nParent, Lesson nLesson, Page nPage, ArrayList<XMLNotification> nErrorList, Attributes answerboxAttrs) {
        this.xmlReader = nXMLReader;
        this.parent = nParent;
        
        this.lesson = nLesson;
        this.page = nPage;
        this.errorList = nErrorList;
        
        answerboxStart(answerboxAttrs);
    }
    
    public void endElement(String uri, String localName, String qName) {
        switch(XMLElement.check(qName.toUpperCase())) {
            /* If the image element has finished, return to the parent */
            case ANSWERBOX:
                endHandler();
                break;            
            default:
                break;
        }
    }
    
    public void endHandler() {
        page.pageObjects.add(answerBox);
        
        xmlReader.setContentHandler(parent);
    }
    
    public void answerboxStart(Attributes attrs) {
        String xStartStr = attrs.getValue("xstart");
        String yStartStr = attrs.getValue("ystart");
        String characterLimitStr = attrs.getValue("characterlimit");
        String correctAnswerStr = attrs.getValue("correctanswer");
        String marksStr = attrs.getValue("marks");
        String retryStr = attrs.getValue("retry");
        String numericalStr = attrs.getValue("numerical");
        
        float xStart = XMLUtil.checkFloat(xStartStr, 0.0f, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (AnswerBox) X Start ");
        
        float yStart = XMLUtil.checkFloat(yStartStr, 0.0f, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (AnswerBox) Y Start ");
        
        int characterLimit = XMLUtil.checkInt(characterLimitStr, 10, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (AnswerBox) Character limit ");
        
        int marks = XMLUtil.checkInt(marksStr, 0, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (AnswerBox) Marks ");
        
        if(correctAnswerStr == null) {
            errorList.add(new XMLNotification(Level.ERROR,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (AnswerBox) Sourcefile "));
            correctAnswerStr = new String("null");
        }
        
        boolean numerical = XMLUtil.checkBool(numericalStr, false, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (AnswerBox) Numerical setting ");
        
        boolean retry = XMLUtil.checkBool(retryStr, true, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (AnswerBox) Retry setting ");
        
        answerBox = new AnswerBoxObject(xStart, yStart, characterLimit, marks, correctAnswerStr, retry, numerical);
    }
}
