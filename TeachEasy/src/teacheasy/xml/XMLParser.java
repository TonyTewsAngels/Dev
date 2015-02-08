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
    /** The current element */
    private String currentElement;
    
	/** The lesson being constructed */
	private Lesson currentLesson;
	
	/** Metatdata for the lesson under construction */
	private LessonInfo constructLessonInfo;
	
	/** Default settings for the lesson under construction */
	private LessonDefaultSettings constructDFSettings;
	
	/** Grade settings for the lesson under construction */
	private LessonGradeSettings constructGradeSettings;
	
	private List<String> elementList;
	
	/** Constructor Method */
	public XMLParser() {
	    elementList = new ArrayList<String>();
		currentLesson = new Lesson();
	}
	
	/** Parses an XML file */
	public void parse(String filename) {
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
		
		for(int i = 0; i < elementList.size(); i++) {
		    System.out.print(elementList.get(i) + " ");
		}
		
		System.out.println("");
	}
	
	/** Called by parser at the end of an element */
	public void endElement(String uri, String localName, String qName) throws SAXException{
		elementList.remove(elementList.size() - 1);
		
		for(int i = 0; i < elementList.size(); i++) {
            System.out.print(elementList.get(i) + " ");
        }
        
        System.out.println("/" + qName);
	}
	
	/** Called by parser when characters have been read */
	public void characters(char ch[], int start, int length) throws SAXException {
		String s = new String(ch, start, length).trim();
		if(!s.equals("")) {
			//System.out.println("ch: [" + s + "]");
		}
	}
	
	/** Called by parser at the start of a document */
	public void startDocument() throws SAXException {
		System.out.println("Document");
	}
	
	/** Called by parser at the start of a document */
	public void endDocument() throws SAXException {
		System.out.println("Document End");
	}
}