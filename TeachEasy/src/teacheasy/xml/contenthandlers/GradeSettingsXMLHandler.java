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
import teacheasy.data.lessondata.LessonGradeSettings;
import teacheasy.xml.XMLElement;
import teacheasy.xml.util.XMLNotification;
import teacheasy.xml.util.XMLNotification.Level;

/**
 * The XML content handler for the default settings element.
 * 
 * @author  Alistair Jewers
 * @version 1.0 Apr 12 2015 
 */
public class GradeSettingsXMLHandler extends DefaultHandler {
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
    
    /** Pass message string */
    private String passMessage;
    
    /** Fail message string */
    private String failMessage;
    
    /** Boolean check for missing pass boundary */
    private boolean passBoundaryFound = false;
    
    /** The grade settings being constructed */
    private LessonGradeSettings gradeSettings;
    
    /**
     * Constructor.
     * 
     * @param nXMLReader The XML reader reference.
     * @param nParent The handler that called this one.
     * @param nLesson The lesson being constructed.
     * @param nErrorList The list of errors and warnings.
     */
    public GradeSettingsXMLHandler(XMLReader nXMLReader, DefaultHandler nParent, Lesson nLesson, ArrayList<XMLNotification> nErrorList) {
        /* Set the references */
        this.xmlReader = nXMLReader;
        this.parent = nParent;
        this.lesson = nLesson;
        this.errorList = nErrorList;
        
        /* Instantiate and empty grade settings */
        gradeSettings = new LessonGradeSettings(0, null, null);
    }
    
    /**
     * Called when the start of an XML element is found.
     */
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        /* Clear the read buffer */
        readBuffer = null;
        
        switch(XMLElement.check(qName.toUpperCase())) {                
            case PASSBOUNDARY:
                passBoundaryStart(attrs);
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
            /* If the grade settings element has finished, return to the parent */
            case GRADESETTINGS:
                endHandler();
                break;
                
            /* Store the relevant grade settings */
            case PASSBOUNDARY:
                passBoundaryEnd();
                break;
            
            default:
                break;
        }
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
     * Called when the handler is finished to transfer control back to the parent handler.
     */
    public void endHandler() {
        /* Verify that a pass boundary was found */
        if(!passBoundaryFound) {
            /* Add an error */
            errorList.add(new XMLNotification(Level.ERROR, "Grade settings did not contain pass boundary."));
            
            /* Set some default values */
            gradeSettings.setPassBoundary(0);
            gradeSettings.setPassMessage("none");
            gradeSettings.setFailMessage("none");
        }
        
        /* Set the grade settings of the lesson */
        lesson.gradeSettings = gradeSettings;
        
        /* Pass control back to the parent handler */
        xmlReader.setContentHandler(parent);
    }
    
    /**
     * Called when the start of the pass boundary element is found.
     * 
     * @param attrs The attributes found in the XML.
     */
    private void passBoundaryStart(Attributes attrs) {
        /* Set the found variable true */
        passBoundaryFound = true;
        
        /* Parse the pass and fail messages */
        passMessage = attrs.getValue("passmessage");
        failMessage = attrs.getValue("failmessage");
    }
    
    /**
     * Called when the end of the pass boundary element is found.
     */
    private void passBoundaryEnd() {    
        int passBoundary = 0;
        
        /* Check for a null pass message */
        if(passMessage == null) {
            errorList.add(new XMLNotification(Level.ERROR, "Pass boundary did not contain pass message."));
            gradeSettings.setPassMessage("none");
        } else {
            gradeSettings.setPassMessage(passMessage);
        }
        
        /* Check for a null fail message */
        if(failMessage == null) {
            errorList.add(new XMLNotification(Level.ERROR, "Pass boundary did not contain fail message."));
            gradeSettings.setFailMessage("none");
        } else {
            gradeSettings.setFailMessage(failMessage);
        }
        
        /* Check for a null pass mark */
        if(readBuffer == null) {
            errorList.add(new XMLNotification(Level.ERROR, "No value for pass boundary."));
            gradeSettings.setPassBoundary(0);
            return;
        }
        
        /* Try to parse the pass boundary */
        try {
            passBoundary = Integer.parseInt(readBuffer);
        } catch (NumberFormatException e) {
            errorList.add(new XMLNotification(Level.ERROR, "Pass boundary non-integer."));
            gradeSettings.setPassBoundary(0);
        }
        
        /* Add an error for a negative pass mark */
        if(passBoundary < 0) {
            errorList.add(new XMLNotification(Level.ERROR, "Pass boundary less than zero."));
        }
        
        /* Set the pass boundary */
        gradeSettings.setPassBoundary(passBoundary);
    }
}
