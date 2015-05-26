package teacheasy.xml.contenthandlers;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import teacheasy.data.Lesson;
import teacheasy.data.lessondata.LessonInfo;
import teacheasy.xml.XMLElement;
import teacheasy.xml.util.XMLNotification;
import teacheasy.xml.util.XMLNotification.Level;

public class SlideshowXMLHandler extends DefaultHandler {
    private Lesson lesson;
    private ArrayList<XMLNotification> errorList;
    private XMLReader xmlReader;
    private DefaultHandler parent;
    
    private String readBuffer;
    
    private boolean docInfoFound = false, defaultSettingsFound = false, gradeSettingsFound = false, slideFound = false;
    
    public SlideshowXMLHandler(XMLReader nXMLReader, DefaultHandler nParent, Lesson nLesson, ArrayList<XMLNotification> nErrorList) {
        this.xmlReader = nXMLReader;
        this.parent = nParent;
        
        this.lesson = nLesson;
        this.errorList = nErrorList;
    }
    
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        readBuffer = null;
        
        switch(XMLElement.check(qName.toUpperCase())) {
            case DOCUMENTINFO:
                docInfoFound = true;
                xmlReader.setContentHandler(new DocumentInfoXMLHandler(xmlReader, this, lesson, errorList));
                break;
            case DEFAULTSETTINGS:
                defaultSettingsFound = true;
                xmlReader.setContentHandler(new DefaultSettingsXMLHandler(xmlReader, this, lesson, errorList));
                break;
            case GRADESETTINGS:
                gradeSettingsFound = true;
                xmlReader.setContentHandler(new GradeSettingsXMLHandler(xmlReader, this, lesson, errorList));
                break;
            case SLIDE:
                slideFound = true;
                xmlReader.setContentHandler(new SlideXMLHandler(xmlReader, this, lesson, errorList, attrs));
                break;
            default:
                break;
        }
    }
    
    public void endElement(String uri, String localName, String qName) {
        switch(XMLElement.check(qName.toUpperCase())) {
            /* If the slideshow has finished, return to the parent */
            case SLIDESHOW:
                endHandler();
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
        if(!docInfoFound) {
            errorList.add(new XMLNotification(Level.ERROR, "No Document Info found."));
        }
        
        if(!defaultSettingsFound) {
            errorList.add(new XMLNotification(Level.ERROR, "No Default Settings found."));
        }
        if(!gradeSettingsFound) {
            errorList.add(new XMLNotification(Level.WARNING, "No Grade Settings found. Default Inserted."));
        }
        
        if(!slideFound) {
            errorList.add(new XMLNotification(Level.ERROR, "No Slides were found."));
        }
        
        if(lesson.lessonInfo.getTotalMarks() < lesson.gradeSettings.getPassBoundary()) {
            errorList.add(new XMLNotification(Level.ERROR, "Pass boundary exceeds total marks"));
        }
        
        xmlReader.setContentHandler(parent);
    }
}
