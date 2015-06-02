/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved. 
 */
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
import teacheasy.xml.util.XMLUtil;
import teacheasy.xml.util.XMLNotification.*;

/**
 * The XML content handler for an image element.
 * 
 * @author  Alistair Jewers
 * @version 1.0 Apr 12 2015 
 */
public class ImageXMLHandler extends DefaultHandler{
    /* Data Objects */
    private Lesson lesson;
    private Page page;
    private ImageObject image;
    
    /** List of warnings and errors */
    private ArrayList<XMLNotification> errorList;
    
    /** Reference to the xml reader */
    private XMLReader xmlReader;
    
    /** The handler that activated this one */
    private DefaultHandler parent;
    
    /**
     * Constructor. 
     * 
     * @param nXMLReader The XML reader reference.
     * @param nParent The parent handler.
     * @param nLesson The lesson being constructed.
     * @param nPage The page being constructed.
     * @param nErrorList The full error and warnings list.
     * @param imageAttrs The attributes of the image element.
     */
    public ImageXMLHandler(XMLReader nXMLReader, DefaultHandler nParent, Lesson nLesson, Page nPage, ArrayList<XMLNotification> nErrorList, Attributes imageAttrs) {
        /* Set reference */
        this.xmlReader = nXMLReader;
        this.parent = nParent;
        this.lesson = nLesson;
        this.page = nPage;
        this.errorList = nErrorList;
        
        /* Create an empty image element */
        image = new ImageObject(0.0f, 0.0f, 0.0f, 0.0f, null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        
        /* Start the constructing the image object */
        imageStart(imageAttrs);
    }
    
    /**
     * Called when the end of an XML element is found.
     */
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
    
    /**
     * Called when the handler is finished to pass control back to the parent handler.
     */
    public void endHandler() {
        page.pageObjects.add(image);
        
        xmlReader.setContentHandler(parent);
    }
    
    /**
     * Handles the image XML element.
     * 
     * @param attrs The attributes found in the XML.
     */
    public void imageStart(Attributes attrs) {
        /* Parse the strings from XML attributes */
        String sourceFileStr = attrs.getValue("sourcefile");
        String xStartStr = attrs.getValue("xstart");
        String yStartStr = attrs.getValue("ystart");
        String scaleStr = attrs.getValue("scale");
        String xEndStr = attrs.getValue("xend");
        String yEndStr = attrs.getValue("yend");
        String rotationStr = attrs.getValue("rotation");
        String startTimeStr = attrs.getValue("starttime");
        String durationStr = attrs.getValue("duration");
        
        /* Attempt to parse values, add errors as necessary */
        float xStart = XMLUtil.checkFloat(xStartStr, 0.0f, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Image) X Start ");
        
        float yStart = XMLUtil.checkFloat(yStartStr, 0.0f, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Image) Y Start ");
        
        float scale = XMLUtil.checkFloat(scaleStr, 1.0f, Level.WARNING, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Image) Scale ");
        
        float xEnd = XMLUtil.checkFloat(xEndStr, -1.0f, Level.WARNING, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Image) X End ");
        
        float yEnd = XMLUtil.checkFloat(yEndStr, -1.0f, Level.WARNING, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Image) Y End ");
        
        float rotation = XMLUtil.checkFloat(rotationStr, 0.0f, Level.WARNING, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Image) Rotation ");
        
        float startTime = XMLUtil.checkFloat(startTimeStr, 0.0f, Level.WARNING, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Image) Start time ");
        
        float duration = XMLUtil.checkFloat(durationStr, 0.0f, Level.WARNING, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Image) Duration ");
        
        if(sourceFileStr == null) {
            errorList.add(new XMLNotification(Level.ERROR, 
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Image) Sourcefile missing."));
            sourceFileStr = new String("null");
        }
        
        /* Construct the new image data object */
        image = new ImageObject(xStart, yStart, xEnd, yEnd, sourceFileStr, scale, scale, rotation, startTime, duration);
    }
}
