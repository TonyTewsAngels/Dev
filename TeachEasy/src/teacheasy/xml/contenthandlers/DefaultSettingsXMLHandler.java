package teacheasy.xml.contenthandlers;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import teacheasy.data.Lesson;
import teacheasy.data.lessondata.LessonDefaultSettings;
import teacheasy.xml.XMLElement;
import teacheasy.xml.util.XMLNotification;
import teacheasy.xml.util.XMLUtil;
import teacheasy.xml.util.XMLNotification.Level;

public class DefaultSettingsXMLHandler extends DefaultHandler {
    private Lesson lesson;
    private ArrayList<XMLNotification> errorList;
    private XMLReader xmlReader;
    private DefaultHandler parent;
    
    private String readBuffer;
    
    private LessonDefaultSettings defaultSettings;
    
    public DefaultSettingsXMLHandler(XMLReader nXMLReader, DefaultHandler nParent, Lesson nLesson, ArrayList<XMLNotification> nErrorList) {
        this.xmlReader = nXMLReader;
        this.parent = nParent;
        
        this.lesson = nLesson;
        this.errorList = nErrorList;
        
        defaultSettings = new LessonDefaultSettings(0, null, null, null, null, null);
    }
    
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        readBuffer = null;
    }
    
    public void endElement(String uri, String localName, String qName) {
        switch(XMLElement.check(qName.toUpperCase())) {
            /* If the default settings element has finished, return to the parent */
            case DEFAULTSETTINGS:
                endHandler();
                break;
                
            /* Store the relevant default settings */
            case BACKGROUNDCOLOR:
                backgroundColor();
                break;
            case FONT:
                defaultSettings.setFont(readBuffer);
                break;
            case FONTSIZE:
                fontSize();
                break;
            case FONTCOLOR:
                fontColor();
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
        if(defaultSettings.getFont() == null) {
            errorList.add(new XMLNotification(Level.ERROR, "Default font not found."));
            defaultSettings.setFont("none");
        }
        
        lesson.defaultSettings = defaultSettings;
        
        xmlReader.setContentHandler(parent);
    }
    
    private void backgroundColor() {    
        if(readBuffer == null) {
            errorList.add(new XMLNotification(Level.ERROR, "Default background colour not found."));
            defaultSettings.setBackgroundColour("none");
            return;
        }
        
        if(XMLUtil.checkColor(readBuffer)) {
            defaultSettings.setBackgroundColour(readBuffer);
        } else {
            errorList.add(new XMLNotification(Level.ERROR, "Default background colour invalid colour string."));
            defaultSettings.setBackgroundColour("none");
        }
    }
    
    private void fontColor() {    
        if(readBuffer == null) {
            errorList.add(new XMLNotification(Level.ERROR, "Default font colour not found."));
            defaultSettings.setFontColour("none");
            return;
        }
        
        if(XMLUtil.checkColor(readBuffer)) {
            defaultSettings.setFontColour(readBuffer);
        } else {
            errorList.add(new XMLNotification(Level.ERROR, "Default font colour invalid colour string."));
            defaultSettings.setFontColour("none");
        }
    }
    
    private void fontSize() {
        int fontSize = 0;
        
        if(readBuffer == null) {
            errorList.add(new XMLNotification(Level.ERROR, "Default font size not found."));
            defaultSettings.setFontSize(8);
            return;
        }
        
        try {
            fontSize = Integer.parseInt(readBuffer);
        } catch(NumberFormatException e) {
            errorList.add(new XMLNotification(Level.ERROR, "Default font size non-integer."));
            defaultSettings.setFontSize(8);
            return;
        }
        
        defaultSettings.setFontSize(fontSize);
    }
}
