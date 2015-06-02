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
import teacheasy.data.lessondata.LessonInfo;
import teacheasy.xml.XMLElement;
import teacheasy.xml.util.XMLNotification;
import teacheasy.xml.util.XMLNotification.Level;

/**
 * The XML content handler for the main slideshow element.
 * 
 * @author  Alistair Jewers
 * @version 1.0 Apr 10 2015 
 */
public class SlideshowXMLHandler extends DefaultHandler {
    /** The lesson under construction */
    private Lesson lesson;
    
    /** List of warnings and errors */
    private ArrayList<XMLNotification> errorList;
    
    /** Reference to the xml reader */
    private XMLReader xmlReader;
    
    /** The handler that activated this one */
    private DefaultHandler parent;
    
    /** The text buffer */
    private String readBuffer;
    
    /* Boolean variables to verify key components found */
    private boolean docInfoFound = false,
                    defaultSettingsFound = false,
                    gradeSettingsFound = false,
                    slideFound = false;
    
    /**
     * Constructor. 
     * 
     * @param nXMLReader The XML reader reference.
     * @param nParent The parent handler.
     * @param nLesson The lesson being constructed.
     * @param nErrorList The full error and warnings list.
     */
    public SlideshowXMLHandler(XMLReader nXMLReader, DefaultHandler nParent, Lesson nLesson, ArrayList<XMLNotification> nErrorList) {
        /* Set the references */
        this.xmlReader = nXMLReader;
        this.parent = nParent;
        this.lesson = nLesson;
        this.errorList = nErrorList;
    }
    
    /**
     * Called when the start of an XML element is found.
     */
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        readBuffer = null;
        
        /* Switch to the appropriate content handler */
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
    
    /**
     * Called when the end of an XML element is found.
     */
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
    
    /**
     * Called when text is found in the XML.
     */
    public void characters(char[] ch, int start, int length) {
        String str = new String(ch, start, length);
        str = str.trim();
        if(!str.isEmpty()) {
            readBuffer = str;
        }
    }
    
    /**
     * Called when the handler is finished to pass control back to the parent handler.
     */
    public void endHandler() {
        /* Verify that the key components were found. Add errors if not. */
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
        
        /* Pass control back to the parent handler */
        xmlReader.setContentHandler(parent);
    }
}
