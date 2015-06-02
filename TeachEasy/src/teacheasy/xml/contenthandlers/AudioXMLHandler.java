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

import teacheasy.data.AudioObject;
import teacheasy.data.Lesson;
import teacheasy.data.Page;
import teacheasy.xml.XMLElement;
import teacheasy.xml.util.XMLNotification;
import teacheasy.xml.util.XMLUtil;
import teacheasy.xml.util.XMLNotification.*;

/**
 * The XML content handler for an audio element.
 * 
 * @author  Alistair Jewers
 * @version 1.0 Apr 12 2015 
 */
public class AudioXMLHandler extends DefaultHandler{
    /* Data objects */
    private Lesson lesson;
    private Page page;
    private AudioObject audio;
    
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
     * @param audioAttrs The XML attributes of the audio object.
     */
    public AudioXMLHandler(XMLReader nXMLReader, DefaultHandler nParent, Lesson nLesson, Page nPage, ArrayList<XMLNotification> nErrorList, Attributes audioAttrs) {
        /* Set references */
        this.xmlReader = nXMLReader;
        this.parent = nParent;
        this.lesson = nLesson;
        this.page = nPage;
        this.errorList = nErrorList;
        
        /* Start constructing the audio object */
        audioStart(audioAttrs);
    }
    
    /**
     * Called when the end of an XML element is found.
     */
    public void endElement(String uri, String localName, String qName) {
        switch(XMLElement.check(qName.toUpperCase())) {
            /* If the audio element has finished, return to the parent */
            case AUDIO:
                endHandler();
                break;            
            default:
                break;
        }
    }
    
    /**
     * Called to finish this handler and transfer control to the parent handler.
     */
    public void endHandler() {
        /* Add the audio object to the page */
        page.pageObjects.add(audio);
        
        /* Transfer control back up */
        xmlReader.setContentHandler(parent);
    }
    
    /**
     * Start creating the audio object.
     * 
     * @param attrs The attributes associated with the object.
     */
    public void audioStart(Attributes attrs) {
        /* Get the strings from the XML */
        String sourceFileStr = attrs.getValue("sourcefile");
        String xStartStr = attrs.getValue("xstart");
        String yStartStr = attrs.getValue("ystart");
        String xEndStr = attrs.getValue("xend");
        String startTimeStr = attrs.getValue("starttime");
        String viewProgressStr = attrs.getValue("viewprogress");
        String autoPlayStr = attrs.getValue("autoplay");
        String loopStr = attrs.getValue("loop");
        
        /* Attempt to parse the values using utility methods. Inser erros and warnings as needed */
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
        
        boolean autoPlay = XMLUtil.checkBool(autoPlayStr, false, Level.WARNING, errorList,
        		"Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Audio) Auto Play ");
        
        boolean loop = XMLUtil.checkBool(loopStr, false, Level.WARNING, errorList, 
        		"Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Audio) Loop");
        
        /* Create the audio data object */
        audio = new AudioObject(xStart, yStart, xEnd, sourceFileStr, startTime, viewProgress, autoPlay, loop);
    }
}
