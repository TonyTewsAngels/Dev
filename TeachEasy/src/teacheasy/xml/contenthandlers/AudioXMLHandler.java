package teacheasy.xml.contenthandlers;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.sun.org.apache.bcel.internal.classfile.SourceFile;

import teacheasy.data.AudioObject;
import teacheasy.data.Lesson;
import teacheasy.data.Page;
import teacheasy.xml.XMLElement;
import teacheasy.xml.util.XMLNotification;
import teacheasy.xml.util.XMLUtil;
import teacheasy.xml.util.XMLNotification.*;

public class AudioXMLHandler extends DefaultHandler{
    private Lesson lesson;
    private Page page;
    private AudioObject audio;
    
    private ArrayList<XMLNotification> errorList;
    private XMLReader xmlReader;
    private DefaultHandler parent;
    
    public AudioXMLHandler(XMLReader nXMLReader, DefaultHandler nParent, Lesson nLesson, Page nPage, ArrayList<XMLNotification> nErrorList, Attributes audioAttrs) {
        this.xmlReader = nXMLReader;
        this.parent = nParent;
        
        this.lesson = nLesson;
        this.page = nPage;
        this.errorList = nErrorList;
        
        audioStart(audioAttrs);
    }
    
    public void endElement(String uri, String localName, String qName) {
        switch(XMLElement.check(qName.toUpperCase())) {
            /* If the image element has finished, return to the parent */
            case AUDIO:
                endHandler();
                break;            
            default:
                break;
        }
    }
    
    public void endHandler() {
        page.pageObjects.add(audio);
        
        xmlReader.setContentHandler(parent);
    }
    
    public void audioStart(Attributes attrs) {
        String sourceFileStr = attrs.getValue("sourcefile");
        String xStartStr = attrs.getValue("xstart");
        String yStartStr = attrs.getValue("ystart");
        String xEndStr = attrs.getValue("xend");
        String startTimeStr = attrs.getValue("starttime");
        String viewProgressStr = attrs.getValue("viewprogress");
        
        float xStart = XMLUtil.checkFloat(xStartStr, 0.0f, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Audio) X Start ");
        
        float yStart = XMLUtil.checkFloat(yStartStr, 0.0f, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Audio) Y Start ");
        
        float xEnd = XMLUtil.checkFloat(xEndStr, -1.0f, Level.WARNING, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Audio) X End ");
        
        float startTime = XMLUtil.checkFloat(startTimeStr, 0.0f, Level.WARNING, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Audio) Start time ");
        
        if(sourceFileStr == null) {
            errorList.add(new XMLNotification(Level.ERROR,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Audio) Sourcefile "));
            sourceFileStr = new String("null");
        }
        
        boolean viewProgress = XMLUtil.checkBool(viewProgressStr, true, Level.WARNING, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Audio) View progress ");
        
        audio = new AudioObject(xStart, yStart, xEnd, sourceFileStr, startTime, viewProgress);
    }
}
