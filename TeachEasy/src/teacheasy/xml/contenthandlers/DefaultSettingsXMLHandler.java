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
import teacheasy.data.lessondata.LessonDefaultSettings;
import teacheasy.xml.XMLElement;
import teacheasy.xml.util.XMLNotification;
import teacheasy.xml.util.XMLUtil;
import teacheasy.xml.util.XMLNotification.Level;

/**
 * The XML content handler for the default settings element.
 * 
 * @author  Alistair Jewers
 * @version 1.0 Apr 12 2015 
 */
public class DefaultSettingsXMLHandler extends DefaultHandler {
    /** Lesson object */
    private Lesson lesson;
    
    /** List of errors and warnings */
    private ArrayList<XMLNotification> errorList;
    
    /** The XML reader reference */
    private XMLReader xmlReader;
    
    /** The handler that called this one */
    private DefaultHandler parent;
    
    /** The buffer of characters read in */
    private String readBuffer;
    
    /** The lesson defaults being constructed */
    private LessonDefaultSettings defaultSettings;
    
    /**
     * Constructor.
     * 
     * @param nXMLReader The XML reader reference.
     * @param nParent The handler that called this one.
     * @param nLesson The lesson being constructed.
     * @param nErrorList The list of errors and warnings.
     */
    public DefaultSettingsXMLHandler(XMLReader nXMLReader, DefaultHandler nParent, Lesson nLesson, ArrayList<XMLNotification> nErrorList) {
        /* Set the references */
        this.xmlReader = nXMLReader;
        this.parent = nParent;
        this.lesson = nLesson;
        this.errorList = nErrorList;
        
        /* Create an empty default settings object */
        defaultSettings = new LessonDefaultSettings(0, null, null, null, null, null);
    }
    
    /**
     * Called when the start of an XML element is found.
     */
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        /* Clear the text buffer */
        readBuffer = null;
    }
    
    /**
     * Called when the end of an XML element is found.
     */
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
    
    /**
     * Called when text is found in the XML.
     */
    public void characters(char[] ch, int start, int length) {
        /* Stash the text in the buffer */
        String str = new String(ch, start, length);
        str = str.trim();
        if(!str.isEmpty()) {
            readBuffer = str;
        }
    }
    
    /**
     * Called when the handler is finished to transfer control back to the parent.
     */
    public void endHandler() {
        /* Check that a default font was found */
        if(defaultSettings.getFont() == null) {
            errorList.add(new XMLNotification(Level.ERROR, "Default font not found."));
            defaultSettings.setFont("none");
        }
        
        /* Set the default settings for the lesson */
        lesson.defaultSettings = defaultSettings;
        
        /* Switch back to the parent content handler */
        xmlReader.setContentHandler(parent);
    }
    
    /**
     * Process the background color element.
     */
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
    
    /**
     * Process the font color element.
     */
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
    
    /**
     * Process the font size element.
     */
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
