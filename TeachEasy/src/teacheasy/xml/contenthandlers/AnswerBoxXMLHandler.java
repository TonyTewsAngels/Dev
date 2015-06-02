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

import teacheasy.data.AnswerBoxObject;
import teacheasy.data.Lesson;
import teacheasy.data.Page;
import teacheasy.xml.XMLElement;
import teacheasy.xml.util.XMLNotification;
import teacheasy.xml.util.XMLUtil;
import teacheasy.xml.util.XMLNotification.*;

/**
 * The XML content handler for an answer box element.
 * 
 * @author  Alistair Jewers
 * @version 1.0 Apr 12 2015 
 */
public class AnswerBoxXMLHandler extends DefaultHandler{
    /* Data objects */
    private Lesson lesson;
    private Page page;
    private AnswerBoxObject answerBox;
    
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
     * @param answerboxAttrs The XML attributes of the answer box object that triggered this handler.
     */
    public AnswerBoxXMLHandler(XMLReader nXMLReader, DefaultHandler nParent, Lesson nLesson, 
                               Page nPage, ArrayList<XMLNotification> nErrorList, 
                               Attributes answerboxAttrs) {
        /* Set references */
        this.xmlReader = nXMLReader;
        this.parent = nParent;
        this.lesson = nLesson;
        this.page = nPage;
        this.errorList = nErrorList;
        
        /* Use the answer box attributes to construct the object */
        answerboxStart(answerboxAttrs);
    }
    
    /**
     * Called at the start of an element.
     */
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        switch(XMLElement.check(qName.toUpperCase())) {
            case ANSWER:
                /* Switch to the answer content handler */
                xmlReader.setContentHandler(new AnswerXMLHandler(xmlReader,
                                                                 this, 
                                                                 lesson, 
                                                                 page, 
                                                                 answerBox, 
                                                                 errorList, 
                                                                 attrs));
                break;
            default:
                break;
        }
    }
    
    /**
     * Called at the end of an element.
     */
    public void endElement(String uri, String localName, String qName) {
        switch(XMLElement.check(qName.toUpperCase())) {
            /* If the answer box element has finished, return to the parent */
            case ANSWERBOX:
                endHandler();
                break;            
            default:
                break;
        }
    }
    
    /**
     * Called when the handler is finished to
     * move control back to the parent.
     */
    public void endHandler() {
        page.pageObjects.add(answerBox);
        
        xmlReader.setContentHandler(parent);
    }
    
    /**
     * Called at the start of an answerbox element.
     * 
     * @param attrs The attributes of the element.
     */
    public void answerboxStart(Attributes attrs) {
        /* Get the strings from XML */
        String xStartStr = attrs.getValue("xstart");
        String yStartStr = attrs.getValue("ystart");
        String characterLimitStr = attrs.getValue("characterlimit");
        String marksStr = attrs.getValue("marks");
        String retryStr = attrs.getValue("retry");
        String numericalStr = attrs.getValue("numerical");
        String upperBoundStr = attrs.getValue("upperbound");
        String lowerBoundStr = attrs.getValue("lowerbound");
        
        /* Attmept to parse data using util methods, adding errors and warnings as necessary */
        float xStart = XMLUtil.checkFloat(xStartStr, 0.0f, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (AnswerBox) X Start ");
        
        float yStart = XMLUtil.checkFloat(yStartStr, 0.0f, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (AnswerBox) Y Start ");
        
        int characterLimit = XMLUtil.checkInt(characterLimitStr, 10, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (AnswerBox) Character limit ");
        
        int marks = XMLUtil.checkInt(marksStr, 0, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (AnswerBox) Marks ");
        
        boolean numerical = XMLUtil.checkBool(numericalStr, false, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (AnswerBox) Numerical setting ");
        
        boolean retry = XMLUtil.checkBool(retryStr, true, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (AnswerBox) Retry setting ");
        
        float upperBound = XMLUtil.checkFloat(upperBoundStr, 0.0f, Level.WARNING, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (AnswerBox) Upper bound ");
        
        float lowerBound = XMLUtil.checkFloat(lowerBoundStr, 0.0f, Level.WARNING, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (AnswerBox) Lower bound ");
        
        /* Construct the answer box from the parsed data */
        answerBox = new AnswerBoxObject(xStart, yStart, characterLimit, marks, retry, numerical, upperBound, lowerBound);
    }
}
