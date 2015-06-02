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

import teacheasy.data.MultipleChoiceObject;
import teacheasy.data.Lesson;
import teacheasy.data.MultipleChoiceObject.MultiChoiceType;
import teacheasy.data.MultipleChoiceObject.Orientation;
import teacheasy.data.Page;
import teacheasy.xml.XMLElement;
import teacheasy.xml.util.XMLNotification;
import teacheasy.xml.util.XMLUtil;
import teacheasy.xml.util.XMLNotification.*;

/**
 * The XML content handler for an multiple choice element.
 * 
 * @author  Alistair Jewers
 * @version 1.0 Apr 12 2015 
 */
public class MultipleChoiceXMLHandler extends DefaultHandler{
    /* Data Objects */
    private Lesson lesson;
    private Page page;
    private MultipleChoiceObject multipleChoice;
    
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
     * @param multipleChoiceAttrs The attributes of this multiple choice element.
     */
    public MultipleChoiceXMLHandler(XMLReader nXMLReader, DefaultHandler nParent, Lesson nLesson, Page nPage, ArrayList<XMLNotification> nErrorList, Attributes multipleChoiceAttrs) {
        /* Set the references */
        this.xmlReader = nXMLReader;
        this.parent = nParent;
        this.lesson = nLesson;
        this.page = nPage;
        this.errorList = nErrorList;
        
        /* Start constructing the multiple choice object */
        multipleChoiceStart(multipleChoiceAttrs);
    }
    
    /**
     * Called when the start of an XML element is found.
     */
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        switch(XMLElement.check(qName.toUpperCase())) {
            case ANSWER:
                xmlReader.setContentHandler(new AnswerXMLHandler(xmlReader, this, lesson, page, multipleChoice, errorList, attrs));
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
            case MULTIPLECHOICE:
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
        /* Add the object to the page */
        page.pageObjects.add(multipleChoice);
        
        /* Pass control back to the parent handler */
        xmlReader.setContentHandler(parent);
    }
    
    /**
     * Handles the multiple choice XML element.
     * 
     * @param attrs The attributes found in the XML.
     */
    public void multipleChoiceStart(Attributes attrs) {
        /* Parse the strings from XML attributes */
        String xStartStr = attrs.getValue("xstart");
        String yStartStr = attrs.getValue("ystart");
        String typeStr = attrs.getValue("type");
        String orientationStr = attrs.getValue("orientation");
        String marksStr = attrs.getValue("marks");
        String retryStr = attrs.getValue("retry");
        
        /* Attempt to parse values, add errors as necessary */
        float xStart = XMLUtil.checkFloat(xStartStr, 0.0f, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Multiple Choice) X Start ");
        
        float yStart = XMLUtil.checkFloat(yStartStr, 0.0f, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Multiple Choice) Y Start ");
        
        int marks = XMLUtil.checkInt(marksStr, 0, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Multiple Choice) Marks ");
        
        if(typeStr == null) {
            errorList.add(new XMLNotification(Level.ERROR,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Multiple Choice) Type missing."));
            typeStr = new String("line");
        }
        
        /* Multiple choice type variable */
        MultiChoiceType type = MultiChoiceType.check(typeStr.toUpperCase());
        
        if(orientationStr == null) {
            errorList.add(new XMLNotification(Level.ERROR,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Multiple Choice) Orientation missing."));
            orientationStr = new String("vertical");
        }
        
        /* Orientation type variable */
        Orientation orientation = Orientation.check(orientationStr.toUpperCase());
        
        boolean retry = XMLUtil.checkBool(retryStr, true, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Multiple Choice) Retry setting ");
        
        /* Construct the multiple choice object */
        multipleChoice = new MultipleChoiceObject(xStart, yStart, orientation, type, marks, retry);
    }
}
