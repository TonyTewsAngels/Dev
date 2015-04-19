package teacheasy.xml.contenthandlers;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import teacheasy.data.Lesson;
import teacheasy.data.lessondata.LessonGradeSettings;
import teacheasy.xml.XMLElement;
import teacheasy.xml.util.XMLNotification;
import teacheasy.xml.util.XMLNotification.Level;

public class GradeSettingsXMLHandler extends DefaultHandler {
    private Lesson lesson;
    private ArrayList<XMLNotification> errorList;
    private XMLReader xmlReader;
    private DefaultHandler parent;
    
    private String readBuffer;
    private String passMessage;
    private String failMessage;
    
    private boolean passBoundaryFound = false;
    
    private LessonGradeSettings gradeSettings;
    
    public GradeSettingsXMLHandler(XMLReader nXMLReader, DefaultHandler nParent, Lesson nLesson, ArrayList<XMLNotification> nErrorList) {
        this.xmlReader = nXMLReader;
        this.parent = nParent;
        
        this.lesson = nLesson;
        this.errorList = nErrorList;
        
        gradeSettings = new LessonGradeSettings(0, null, null);
    }
    
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        readBuffer = null;
        
        switch(XMLElement.check(qName.toUpperCase())) {                
            case PASSBOUNDARY:
                passBoundaryStart(attrs);
                break;
            
            default:
                break;
        }
    }
    
    public void endElement(String uri, String localName, String qName) {
        switch(XMLElement.check(qName.toUpperCase())) {
            /* If the grade settings element has finished, return to the parent */
            case GRADESETTINGS:
                endHandler();
                break;
                
            /* Store the relevant grade settings */
            case PASSBOUNDARY:
                passBoundaryEnd();
                break;
            
            default:
                break;
        }
    }
    
    public void characters(char[] ch, int start, int length) {
        String str = new String(ch, start, length);
        str = str.trim();
        if(!str.isEmpty()) {
            readBuffer = str;
        }
    }
    
    public void endHandler() {
        if(!passBoundaryFound) {
            errorList.add(new XMLNotification(Level.ERROR, "Grade settings did not contain pass boundary."));
            
            gradeSettings.setPassBoundary(0);
            gradeSettings.setPassMessage("none");
            gradeSettings.setFailMessage("none");
        }
        
        lesson.gradeSettings = gradeSettings;
        
        xmlReader.setContentHandler(parent);
    }
    
    private void passBoundaryStart(Attributes attrs) {    
        passBoundaryFound = true;
        
        passMessage = attrs.getValue("passmessage");
        failMessage = attrs.getValue("failmessage");
    }
    
    private void passBoundaryEnd() {    
        int passBoundary = 0;
        
        if(passMessage == null) {
            errorList.add(new XMLNotification(Level.ERROR, "Pass boundary did not contain pass message."));
            gradeSettings.setPassMessage("none");
        } else {
            gradeSettings.setPassMessage(passMessage);
        }
        
        if(failMessage == null) {
            errorList.add(new XMLNotification(Level.ERROR, "Pass boundary did not contain fail message."));
            gradeSettings.setFailMessage("none");
        } else {
            gradeSettings.setFailMessage(failMessage);
        }
        
        if(readBuffer == null) {
            errorList.add(new XMLNotification(Level.ERROR, "No value for pass boundary."));
            gradeSettings.setPassBoundary(0);
            return;
        }
        
        try {
            passBoundary = Integer.parseInt(readBuffer);
        } catch (NumberFormatException e) {
            errorList.add(new XMLNotification(Level.ERROR, "Pass boundary non-integer."));
            gradeSettings.setPassBoundary(0);
        }
        
        if(passBoundary < 0) {
            errorList.add(new XMLNotification(Level.ERROR, "Pass boundary less than zero."));
        }
        
        gradeSettings.setPassBoundary(passBoundary);
    }
}
