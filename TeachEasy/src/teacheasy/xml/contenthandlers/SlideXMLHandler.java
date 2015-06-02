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
import teacheasy.xml.XMLElement;
import teacheasy.xml.util.XMLNotification;
import teacheasy.xml.util.XMLUtil;
import teacheasy.xml.util.XMLNotification.Level;

/**
 * The XML content handler for the slide element.
 * 
 * @author  Alistair Jewers
 * @version 1.0 Apr 10 2015 
 */
public class SlideXMLHandler extends DefaultHandler {
    /** The lesson under construction */
    private Lesson lesson;
    
    /** List of warnings and errors */
    private ArrayList<XMLNotification> errorList;
    
    /** Reference to the xml reader */
    private XMLReader xmlReader;
    
    /** The handler that activated this one */
    private DefaultHandler parent;
    
    /** The page being constructed */
    private Page page;
    
    public SlideXMLHandler(XMLReader nXMLReader, DefaultHandler nParent, Lesson nLesson, ArrayList<XMLNotification> nErrorList, Attributes slideAttrs) {
        /* Set the references */
        this.xmlReader = nXMLReader;
        this.parent = nParent;
        this.lesson = nLesson;
        this.errorList = nErrorList;
        
        /* Create an empty page */
        page = new Page(lesson.pages.size(), null);
        
        /* Start constructing the page */
        slideStart(slideAttrs);
    }
    
    /**
     * Called when the start of an XML element is found.
     */
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        /* Switch to the correct content handler */
        switch(XMLElement.check(qName.toUpperCase())) {
            case TEXT:
                xmlReader.setContentHandler(new TextXMLHandler(xmlReader, this, lesson, page, errorList, attrs));
                break;
            case IMAGE:
                xmlReader.setContentHandler(new ImageXMLHandler(xmlReader, this, lesson, page, errorList, attrs));
                break;
            case AUDIO:
                xmlReader.setContentHandler(new AudioXMLHandler(xmlReader, this, lesson, page, errorList, attrs));
                break;
            case VIDEO:
                xmlReader.setContentHandler(new VideoXMLHandler(xmlReader, this, lesson, page, errorList, attrs));
                break;
            case GRAPHIC:
                xmlReader.setContentHandler(new GraphicXMLHandler(xmlReader, this, lesson, page, errorList, attrs));
                break;
            case ANSWERBOX:
                xmlReader.setContentHandler(new AnswerBoxXMLHandler(xmlReader, this, lesson, page, errorList, attrs));
                break;
            case MULTIPLECHOICE:
                xmlReader.setContentHandler(new MultipleChoiceXMLHandler(xmlReader, this, lesson, page, errorList, attrs));
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
            /* If the slide has finished, return to the parent */
            case SLIDE:
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
        /* Add the page to the lesson */
        lesson.pages.add(page);
        
        /* Pass control back to the parent handler */
        xmlReader.setContentHandler(parent);
    }
    
    /**
     * Handles a slide element in the XML.
     * 
     * @param attrs The attributes found in the XML.
     */
    private void slideStart(Attributes attrs) {
        /* Parse the strings from the attributes */
        String backgroundColor = attrs.getValue("backgroundcolor");
        
        /* Get the page number */
        int pn = page.getNumber();
        
        /* Check that background color is not null, add an error if it is */
        if(backgroundColor == null) {
            errorList.add(new XMLNotification(Level.WARNING, "No background color given for page " + pn+ ". Default used."));
            page.setPageColour(lesson.defaultSettings.getBackgroundColour());
        } else {
            if(XMLUtil.checkColor(backgroundColor)) {
                page.setPageColour(backgroundColor);
            } else {
                errorList.add(new XMLNotification(Level.WARNING, "Page " + pn + "background color invalid color format."));
                page.setPageColour(lesson.defaultSettings.getBackgroundColour());
            }
        }
    }
}
