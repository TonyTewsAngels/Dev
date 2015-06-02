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

import teacheasy.data.Lesson;
import teacheasy.data.Page;
import teacheasy.data.RichText;
import teacheasy.data.TextObject;
import teacheasy.xml.XMLElement;
import teacheasy.xml.util.*;
import teacheasy.xml.util.XMLNotification.*;

/**
 * The XML content handler for a text element.
 * 
 * @author  Alistair Jewers
 * @version 1.0 Apr 12 2015 
 */
public class TextXMLHandler extends DefaultHandler{
    /* Data Objects */
    private Lesson lesson;
    private Page page;
    private TextObject text;
    
    /** List of warnings and errors */
    private ArrayList<XMLNotification> errorList;
    
    /** Reference to the xml reader */
    private XMLReader xmlReader;
    
    /** The handler that activated this one */
    private DefaultHandler parent;
    
    /** The text buffer */
    String readBuffer;
    
    /**
     * Constructor.
     * 
     * @param nXMLReader The XML reader reference.
     * @param nParent The parent handler.
     * @param nLesson The lesson being constructed.
     * @param nPage The page being constructed.
     * @param nErrorList The full error and warnings list.
     * @param textAttrs The attributes found in the XML.
     */
    public TextXMLHandler(XMLReader nXMLReader, DefaultHandler nParent, Lesson nLesson, Page nPage, ArrayList<XMLNotification> nErrorList, Attributes textAttrs) {
        /* Set the references */
        this.xmlReader = nXMLReader;
        this.parent = nParent;
        this.lesson = nLesson;
        this.page = nPage;
        this.errorList = nErrorList;
        
        /* Create an empty text box object */
        text = new TextObject(0.0f, 0.0f, 0.0f, 0.0f, null, 0, null, null, 0.0f, 0.0f);
        
        /* Start constructing the text object */
        textStart(textAttrs);
    }
    
    /**
     * Called when the start of an XML element is found.
     */
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        readBuffer = null;
        
        switch(XMLElement.check(qName.toUpperCase())) {
            case RICHTEXT:
                xmlReader.setContentHandler(new RichTextXMLHandler(xmlReader, this, lesson, page, text, errorList, attrs));
                break;            
            default:
                break;
        }
    }
    
    /**
     * Called when the end of an XML element is found.
     */
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
    
    /**
     * Called when the handler is finished to pass control back to the parent handler.
     */
    public void endHandler() {
        /* Add any text found to the end of the fragment list */
        if(readBuffer != null) {
            text.textFragments.add(new RichText(readBuffer, text.getFont(), text.getFontSize(), text.getColor()));
        }
        
        /* Add the text box object to the page */
        page.pageObjects.add(text);
        
        /* Pass control back to the parent handler */
        xmlReader.setContentHandler(parent);
    }
    
    /**
     * Called when text is found in the XML.
     */
    public void characters(char[] ch, int start, int length) {
        /* Store the text in the buffer */
        String str = new String(ch, start, length);
        str = str.trim();
        if(!str.isEmpty()) {
            readBuffer = str;
        }
    }
    
    /**
     * Handles the text XML element.
     * 
     * @param attrs The attributes found in the XML.
     */
    public void textStart(Attributes attrs) {
        /* Parse the strings from XML attributes */
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
        
        /* Attempt to parse values, add errors as necessary */
        float xStart = XMLUtil.checkFloat(xStartStr, 0.0f, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Text) X Start ");
        
        float yStart = XMLUtil.checkFloat(yStartStr, 0.0f, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Text) Y Start ");
        
        float xEnd = XMLUtil.checkFloat(xEndStr, 1.0f, Level.WARNING, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Text) X End ");
        
        float yEnd = XMLUtil.checkFloat(yEndStr, 1.0f, Level.WARNING, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Text) Y End ");
        
        float startTime = XMLUtil.checkFloat(startTimeStr, 0.0f, Level.WARNING, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Text) Start time ");
        
        float duration = XMLUtil.checkFloat(durationStr, 0.0f, Level.WARNING, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Text) Duration ");
        
        if(sourceFileStr == null) {
            errorList.add(new XMLNotification(Level.ERROR, 
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Text) Sourcefile missing."));
            sourceFileStr = new String("null");
        }
        
        if(fontStr == null || fontStr.equals("")) {
            errorList.add(new XMLNotification(Level.WARNING, 
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Text) Font missing."));
            
            if(lesson.defaultSettings.getFont() != null) {
                fontStr = new String(lesson.defaultSettings.getFont());
            } else {
                fontStr = new String("system");
            }
        }
        
        int fontSize = XMLUtil.checkInt(fontSizeStr, lesson.defaultSettings.getFontSize(), Level.WARNING, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Text) Font size ");
        
        fontColorStr = XMLUtil.checkColor(fontColorStr, lesson.defaultSettings.getFontColour(), Level.WARNING, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Text) Font color ");
        
        /* Construct the text object */
        text = new TextObject(xStart, yStart, xEnd, yEnd, fontStr, fontSize, fontColorStr, sourceFileStr, duration, startTime);
    } 
}

