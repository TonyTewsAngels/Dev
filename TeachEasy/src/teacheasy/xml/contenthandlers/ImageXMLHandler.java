package teacheasy.xml.contenthandlers;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import teacheasy.data.ImageObject;
import teacheasy.data.Lesson;
import teacheasy.data.Page;
import teacheasy.xml.XMLElement;
import teacheasy.xml.util.XMLNotification;
import teacheasy.xml.util.XMLNotification.*;

public class ImageXMLHandler extends DefaultHandler{
    private Lesson lesson;
    private Page page;
    private ImageObject image;
    
    private ArrayList<XMLNotification> errorList;
    private XMLReader xmlReader;
    private DefaultHandler parent;
    
    public ImageXMLHandler(XMLReader nXMLReader, DefaultHandler nParent, Lesson nLesson, Page nPage, ArrayList<XMLNotification> nErrorList, Attributes slideAttrs) {
        this.xmlReader = nXMLReader;
        this.parent = nParent;
        
        this.lesson = nLesson;
        this.page = nPage;
        this.errorList = nErrorList;
        
        image = new ImageObject(0.0f, 0.0f, null, 0.0f, 0.0f, 0.0f);
        
        imageStart(slideAttrs);
    }
    
    public void endElement(String uri, String localName, String qName) {
        switch(XMLElement.check(qName.toUpperCase())) {
            /* If the image element has finished, return to the parent */
            case IMAGE:
                endHandler();
                break;            
            default:
                break;
        }
    }
    
    public void endHandler() {
        
        
        page.pageObjects.add(image);
        
        xmlReader.setContentHandler(parent);
    }
    
    public void imageStart(Attributes attrs) {
        String sourceFileStr = attrs.getValue("sourcefile");
        String xStartStr = attrs.getValue("xstart");
        String yStartStr = attrs.getValue("ystart");
        String scaleStr = attrs.getValue("scale");
        String xEndStr = attrs.getValue("xend");
        String yEndStr = attrs.getValue("yend");
        String rotationStr = attrs.getValue("scale");
        String startTimeStr = attrs.getValue("starttime");
        String durationStr = attrs.getValue("duration");
        
        float xStart = checkFloat(xStartStr, 0.0f, Level.ERROR,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Image) Missing X Start.");
        
        float yStart = checkFloat(yStartStr, 0.0f, Level.ERROR,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Image) Missing Y Start.");
        
        float scale = checkFloat(scaleStr, 1.0f, Level.WARNING,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Image) No scale attribute, default 1 inserted.");
        
        float xEnd = checkFloat(xEndStr, -1.0f, Level.WARNING,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Image) No X End attribute, placeholder -1 inserted. Use scale for size.");
        
        float yEnd = checkFloat(yEndStr, -1.0f, Level.WARNING,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Image) No Y End attribute, placeholder -1 inserted. Use scale for size.");
        
        float rotation = checkFloat(rotationStr, 0.0f, Level.WARNING,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Image) No rotation attribute, default 0 inserted.");
        
        float startTime = checkFloat(startTimeStr, 0.0f, Level.WARNING,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Image) No start time attribute, default 0 inserted.");
        
        float duration = checkFloat(durationStr, 0.0f, Level.WARNING,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Image) No duration attribute, default 0 inserted.");
    }
    
    public float checkFloat(String str, float defaultValue, Level errorLevel, String errorMessage) {
        float val;
        
        try {
            val = Float.parseFloat(str);
        } catch (NumberFormatException e) {
            errorList.add(new XMLNotification(errorLevel, errorMessage));
            val = defaultValue;
        }
        
        return val;
    }
    
    public float checkFloat(String str, float defaultValue) {
        float val;
        
        try {
            val = Float.parseFloat(str);
        } catch (NumberFormatException e) {
            val = defaultValue;
        }
        
        return val;
    }
}
