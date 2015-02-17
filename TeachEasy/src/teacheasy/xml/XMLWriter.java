/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import teacheasy.data.*;
import teacheasy.data.lessondata.*;

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
            doc.setXmlStandalone(false);
            
            Element lessonElement = doc.createElement("slideshow");
            doc.appendChild(lessonElement);
            
            addLessonInfo(lesson.lessonInfo, lessonElement, doc);
            addDefaultSettings(lesson.defaultSettings, lessonElement, doc);
            addGradeSettings(lesson.gradeSettings, lessonElement, doc);
            
            for(int i = 0; i < lesson.pages.size(); i++) {
                addPage(lesson.pages.get(i), lessonElement, doc);
            }
            
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("testOutput.xml"));
     
            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);
     
            transformer.transform(source, result);
            
        } catch (ParserConfigurationException | TransformerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    
	}
	
	public void addLessonInfo(LessonInfo lessonInfo, Element lessonElement, Document doc) {
	    Element lessonInfoElement = doc.createElement("documentinfo");
	    lessonElement.appendChild(lessonInfoElement);
	}
	
	public void addDefaultSettings(LessonDefaultSettings defaultSettings, Element lessonElement, Document doc) {
        Element defaultSettingsElement = doc.createElement("defaultsettings");
        lessonElement.appendChild(defaultSettingsElement);
    }
	
	public void addGradeSettings(LessonGradeSettings gradeSettings, Element lessonElement, Document doc) {
        Element gradeSettingsElement = doc.createElement("gradesettings");
        lessonElement.appendChild(gradeSettingsElement);
    }
	
	public void addPage(Page page, Element lessonElement, Document doc) {
	    Element pageElement = doc.createElement("slide"); 
	    lessonElement.appendChild(pageElement);
	    
	    pageElement.setAttribute("number", String.valueOf(page.getNumber()));
	    pageElement.setAttribute("backgroundcolor", page.getPageColour());
	    
	    /*Attr number = doc.createAttribute("number");
	    number.setValue(String.valueOf(page.getNumber()));
	    
	    Attr backgroundColor = doc.createAttribute("backgroundcolor");
	    backgroundColor.setValue(page.getPageColour());

	    pageElement.setAttributeNode(number);
	    pageElement.setAttributeNode(backgroundColor);*/
	}
}
