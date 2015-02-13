/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import teacheasy.data.Lesson;
import teacheasy.data.Page;
import teacheasy.data.PageObject;
import teacheasy.data.lessondata.*;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

/**
 * This class handles XML parsing, 
 * storing the data as Lesson objects.
 * 
 * @version 	1.0 05 Feb 2015
 * @author 		Alistair Jewers
 */
public class XMLParser extends DefaultHandler{        
	/** The lesson being constructed */
	private Lesson currentLesson;
	
	/** XML Standard Indicator */
	private boolean standard = false;
	
	/** An array list describing the current position in the XML nest */
	private List<String> elementList;
	
	/** The most recently read string of characters */
	private String readBuffer;
	
	/** Constructor Method */
	public XMLParser() {
	    /* Instantiate the xml position tracking array list */
	    elementList = new ArrayList<String>();
	    
	    /* Instantiate an empty lesson */
		currentLesson = new Lesson();
	}
	
	/** Parses an XML file */
	public void parse(String filename) {
	    
	    /* Instantiate a new empty lesson */
	    currentLesson = new Lesson();
	    
		try {
			/* Create an instance of the SAX Parser */
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			
			/* Parse the file */
			saxParser.parse(filename,  this);
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
	
	/** Returns the lesson parsed from xml */
	public Lesson getLesson() {
		return currentLesson;
	}
	
	/** Called by parser at the start of an element */
	public void startElement(String uri, String localName, String qName, Attributes attrs) throws SAXException{		
		elementList.add(qName);
	}
	
	/** Called by parser at the end of an element */
	public void endElement(String uri, String localName, String qName) throws SAXException{
		elementList.remove(elementList.size() - 1);
		
		switch (XMLElement.check(qName.toUpperCase())) {
            /* Establish if the XML is standard or not */   
            case GROUPID:
                if (readBuffer.equals("0")) {
                    standard = true;
                } else {
                    standard = false;
                }
                break;  
                
            /* Document Info Elements */
            case AUTHOR:
                currentLesson.lessonInfo.setAuthor(readBuffer);
                break;
            case VERSION:
                currentLesson.lessonInfo.setVersion(readBuffer);
                break;
            case COMMENT:
                currentLesson.lessonInfo.setComment(readBuffer);
                break;
            case DATECREATED:
                currentLesson.lessonInfo.setDateCreated(readBuffer);
                break;
            case TOTALMARKS:
                currentLesson.lessonInfo.setTotalMarks(Integer.parseInt(readBuffer));
                break;
            case LESSONNAME:
                currentLesson.lessonInfo.setLessonName(readBuffer);
                break;
                
            /* Default Settings Elements */
            case BACKGROUNDCOLOR:
                currentLesson.defaultSettings.setBackgroundColour(readBuffer);
                break;
            case FONT:
                currentLesson.defaultSettings.setFont(readBuffer);
                break;
            case FONTSIZE:
                currentLesson.defaultSettings.setFontSize(Integer.parseInt(readBuffer));
                break;
            case FONTCOLOR:
                currentLesson.defaultSettings.setBackgroundColour(readBuffer);
                break;

            /* Grade Settings Elements */
            default:
                break;
		}
	}
	
	/** Called by parser when characters have been read */
	public void characters(char ch[], int start, int length) throws SAXException {
		readBuffer = new String(ch, start, length).trim();
	}
	
	/** Called by parser at the start of a document */
	public void startDocument() throws SAXException {
	    
	}
	
	/** Called by parser at the start of a document */
	public void endDocument() throws SAXException {
	    System.out.println("Author: " + currentLesson.lessonInfo.getAuthor());
	    System.out.println("Version: " + currentLesson.lessonInfo.getVersion());
	    System.out.println("Comment: " + currentLesson.lessonInfo.getComment());
	    System.out.println("Date Created: " + currentLesson.lessonInfo.getDateCreated());
	    System.out.println("Total Marks: " + currentLesson.lessonInfo.getTotalMarks());
	    System.out.println("Lesson Name: " + currentLesson.lessonInfo.getLessonName() + "\n");
	    
	    System.out.println("BG Color: " + currentLesson.defaultSettings.getBackgroundColour());
        System.out.println("Font: " + currentLesson.defaultSettings.getFont());
        System.out.println("Font Size: " + currentLesson.defaultSettings.getFontSize());
        System.out.println("Font Color: " + currentLesson.defaultSettings.getFontColour() + "\n");
    }
}