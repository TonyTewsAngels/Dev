/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import teacheasy.data.Lesson;
import teacheasy.data.Page;
import teacheasy.data.PageObject;

/**
 * This class handles XML writing, 
 * based on data stored in Lesson objects.
 * 
 * @version 	1.0 05 Feb 2015
 * @author 		Alistair Jewers
 */
public class XMLWriter {
	
	/** Constructor Method */
	public XMLWriter() {
		
	}
	
	/** Write an XML file based on a lesson data object */
	public void writeXML(Lesson lesson) {
	    try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            Document doc = docBuilder.newDocument();
            Element lessonElement = doc.createElement("slideshow");
            doc.appendChild(lessonElement);
            
            
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    
	}
	
	public void addPage(Page page, Element lessonElement, Document doc) {
	    
	}
}
