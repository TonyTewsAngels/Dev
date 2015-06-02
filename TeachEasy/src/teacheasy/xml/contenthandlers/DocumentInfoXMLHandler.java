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
 * The XML content handler for the default settings element.
 * 
 * @author  Alistair Jewers
 * @version 1.0 Apr 12 2015 
 */
public class DocumentInfoXMLHandler extends DefaultHandler{
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
    
    private LessonInfo lessonInfo;
    
    public DocumentInfoXMLHandler(XMLReader nXMLReader, DefaultHandler nParent, Lesson nLesson, ArrayList<XMLNotification> nErrorList) {
        this.xmlReader = nXMLReader;
        this.parent = nParent;
        
        this.lesson = nLesson;
        this.errorList = nErrorList;
        
        lessonInfo = new LessonInfo(null, null, null, null, null, -1);
    }
    
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        readBuffer = null;
    }
    
    public void endElement(String uri, String localName, String qName) {
        switch(XMLElement.check(qName.toUpperCase())) {
            /* If the document info has finished, return to the parent */
            case DOCUMENTINFO:
                endHandler();
                break;
                
            /* Store the relevant document info */
            case AUTHOR:
                lessonInfo.setAuthor(readBuffer);
                break;
            case VERSION:
                lessonInfo.setVersion(readBuffer);
                break;
            case COMMENT:
                lessonInfo.setComment(readBuffer);
                break;
            case GROUPID:
                if(readBuffer == null) 
                    errorList.add(new XMLNotification(Level.ERROR, "Missing Group ID"));
                break;
            case DATECREATED:
                lessonInfo.setDateCreated(readBuffer);
                break;
            case TOTALMARKS:
                totalMarks();
                break;
            case LESSONNAME:
                lessonInfo.setLessonName(readBuffer);
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
        if(lessonInfo.getAuthor() == null) {
            errorList.add(new XMLNotification(Level.ERROR, "Missing Author"));
            lessonInfo.setAuthor("Null");
        }
        
        if(lessonInfo.getVersion() == null) {
            errorList.add(new XMLNotification(Level.ERROR, "Missing Version"));
            lessonInfo.setVersion("Null");
        }
        
        if(lessonInfo.getComment() == null) { 
            errorList.add(new XMLNotification(Level.ERROR, "Missing Comment"));
            lessonInfo.setComment("Null");
        }
        
        if(lessonInfo.getDateCreated() == null) {
            errorList.add(new XMLNotification(Level.WARNING, "Missing Date Created"));
            lessonInfo.setDateCreated("Null");
        }
        
        if(lessonInfo.getLessonName() == null) {
            errorList.add(new XMLNotification(Level.WARNING, "Missing Lesson Name"));
            lessonInfo.setDateCreated("Null");
        }
        
        lesson.lessonInfo = lessonInfo;
        
        xmlReader.setContentHandler(parent);
    }
    
    public void totalMarks() {
        int totalMarks = 0;
        
        if(readBuffer == null) {
            errorList.add(new XMLNotification(Level.WARNING, "Missing Total Marks"));
        } else {
            try {
                totalMarks = Integer.parseInt(readBuffer);
            } catch (NumberFormatException e) {
                errorList.add(new XMLNotification(Level.ERROR, "Non-integer total marks"));
            }
        }

        lessonInfo.setTotalMarks(totalMarks);
    }
}
