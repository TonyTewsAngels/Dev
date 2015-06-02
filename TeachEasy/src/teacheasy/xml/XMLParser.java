/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved. 
 */
package teacheasy.xml;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import teacheasy.data.Lesson;
import teacheasy.xml.contenthandlers.SlideshowXMLHandler;
import teacheasy.xml.util.XMLNotification;
import teacheasy.xml.util.XMLNotification.Level;

/**
 * Parses the XML file that makes up a Teach Easy digital
 * lesson into a data structure for use by the programs.
 * 
 * @author  Alistair Jewers
 * @version 2.0 10 Apr 2015
 */
public class XMLParser extends DefaultHandler {
    /** The lesson being constructed from the data */
    private Lesson lesson;
    
    /** A list of the errors and warnings found */
    private ArrayList<XMLNotification> errorList;
    
    /** XML Reader object */
    private XMLReader xmlReader;
    
    /**
     * Constructor.
     */
    public XMLParser() {
        /* Initialise the lesson empty */
        lesson = new Lesson();
        
        /* Initialise the error list empty */
        errorList = new ArrayList<XMLNotification>();
    }
    
    /**
     * Parses an (XML) digital lesson file.
     * @param file The path of the file to parse.
     * @return a list of the errors and warnings found whilst parsing.
     */
    public ArrayList<XMLNotification> parse(String file) {
        /* Clear the error list */
        errorList.clear();
        
        /* Reset the lesson to empty */
        lesson = new Lesson();
        
        try {
            /* Create the reader */
            xmlReader = XMLReaderFactory.createXMLReader();
            
            /* Set the first content handler */
            xmlReader.setContentHandler(this);
            
            /* Create a file reader */
            FileReader reader = new FileReader(file);
            
            /* Begin parsing the file */
            xmlReader.parse(new InputSource(reader));
        } catch (SAXException | IOException e) {
            /* If the file cannot be parsed log an error */
            errorList.add(new XMLNotification(Level.ERROR, "XML File Invalid"));
        }
        
        /* Return the list of errors and warnings accumulated during parsing */
        return errorList;
    }
    
    /**
     * Gets the lesson most recently parsed.
     * 
     * @return the most recently parsed lesson.
     */
    public Lesson getLesson() {
        return lesson;
    }
    
    /**
     * Called when the start of an XML element is found.
     */
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        /* Check the name of the element */
        switch(XMLElement.check(qName.toUpperCase())) {
            case SLIDESHOW:
                /* Switch to the appropriate content handler */
                xmlReader.setContentHandler(new SlideshowXMLHandler(xmlReader, this, lesson, errorList));
                break;
            default:
                /* Log an error of an unexpected top level element */
                errorList.add(new XMLNotification(Level.WARNING, "Non-slideshow top level element found, ignored."));
                break;
        }
    }
}
