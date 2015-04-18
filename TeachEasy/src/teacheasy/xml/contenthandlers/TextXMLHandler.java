package teacheasy.xml.contenthandlers;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.sun.org.apache.bcel.internal.classfile.SourceFile;

import teacheasy.data.Lesson;
import teacheasy.data.Page;
import teacheasy.data.RichText;
import teacheasy.data.TextObject;
import teacheasy.xml.XMLElement;
import teacheasy.xml.util.*;
import teacheasy.xml.util.XMLNotification.*;

public class TextXMLHandler extends DefaultHandler{
    private Lesson lesson;
    private Page page;
    private TextObject text;
    
    private ArrayList<XMLNotification> errorList;
    private XMLReader xmlReader;
    private DefaultHandler parent;
    
    String readBuffer;
    
    public TextXMLHandler(XMLReader nXMLReader, DefaultHandler nParent, Lesson nLesson, Page nPage, ArrayList<XMLNotification> nErrorList, Attributes slideAttrs) {
        this.xmlReader = nXMLReader;
        this.parent = nParent;
        
        this.lesson = nLesson;
        this.page = nPage;
        this.errorList = nErrorList;
        
        text = new TextObject(0.0f, 0.0f, 0.0f, 0.0f, null, 0, null, null, 0.0f, 0.0f);
        
        textStart(slideAttrs);
    }
    
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        readBuffer = null;
        
        switch(XMLElement.check(qName.toUpperCase())) {
            /* If the image element has finished, return to the parent */
            case RICHTEXT:
                
                break;            
            default:
                break;
        }
    }
    
    public void endElement(String uri, String localName, String qName) {
        switch(XMLElement.check(qName.toUpperCase())) {
            /* If the image element has finished, return to the parent */
            case TEXT:
                endHandler();
                break;            
            default:
                break;
        }
    }
    
    public void endHandler() {
        if(readBuffer != null) {
            text.textFragments.add(new RichText(readBuffer, text.getFont(), text.getFontSize(), text.getColor()));
        }
        
        page.pageObjects.add(text);
        
        xmlReader.setContentHandler(parent);
    }
    
    public void characters(char[] ch, int start, int length) {
        String str = new String(ch, start, length);
        str = str.trim();
        if(!str.isEmpty()) {
            readBuffer = str;
        }
    }
    
    public void textStart(Attributes attrs) {
        String xStartStr = attrs.getValue("xstart");
        String yStartStr = attrs.getValue("ystart");
        String xEndStr = attrs.getValue("xend");
        String yEndStr = attrs.getValue("yend");
        String fontStr = attrs.getValue("font");
        String fontSizeStr = attrs.getValue("fontsize");
        String fontColorStr = attrs.getValue("fontcolor");
        String startTimeStr = attrs.getValue("starttime");
        String durationStr = attrs.getValue("duration");
        String sourceFileStr = attrs.getValue("sourcefile");
        
        float xStart = XMLUtil.checkFloat(xStartStr, 0.0f, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Text) X Start ");
        
        float yStart = XMLUtil.checkFloat(yStartStr, 0.0f, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Text) Y Start ");
        
        float xEnd = XMLUtil.checkFloat(xEndStr, -1.0f, Level.WARNING, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Text) X End ");
        
        float yEnd = XMLUtil.checkFloat(yEndStr, -1.0f, Level.WARNING, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Text) Y End ");
        
        float startTime = XMLUtil.checkFloat(startTimeStr, 0.0f, Level.WARNING, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Text) Start time ");
        
        float duration = XMLUtil.checkFloat(durationStr, 0.0f, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Text) Duration ");
        
        if(sourceFileStr == null) {
            errorList.add(new XMLNotification(Level.ERROR, 
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Text) Sourcefile missing."));
            sourceFileStr = new String("null");
        }
        
        if(fontStr == null) {
            errorList.add(new XMLNotification(Level.WARNING, 
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Text) Font missing."));
            fontStr = new String("null");
        }
        
        int fontSize = XMLUtil.checkInt(fontSizeStr, lesson.defaultSettings.getFontSize(), Level.WARNING, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Text) Font size ");
        
        fontColorStr = XMLUtil.checkColor(fontColorStr, lesson.defaultSettings.getFontColour(), Level.WARNING, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Text) Font color ");
        
        text = new TextObject(xStart, yStart, xEnd, yEnd, fontStr, fontSize, fontColorStr, sourceFileStr, duration, startTime);
    } 
}

