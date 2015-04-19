/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.xml;

import java.util.ArrayList;

import teacheasy.data.Lesson;
import teacheasy.xml.util.XMLNotification;

/**
 * This class handles XML reading and
 * writing whilst maintaining info
 * about file locations (recent files).
 * 
 * @version 	1.0 05 Feb 2015
 * @author 		Alistair Jewers
 */
public class XMLHandler {	
	/** XML Parser */
	private XMLParser xmlParser2;
	
	/** XML Writer */
	private XMLWriter xmlWriter;
	
	/** The most recent read location */
	String recentReadPath = null;
	
	/** The most recent write location*/
	String recentWritePath = null;
	
	/** Constructor Method */
	public XMLHandler() {
		/* Instantiate the Parser */
        xmlParser2 = new XMLParser();
		
		/* Instantiate the Writer */
		xmlWriter = new XMLWriter();
	}
	
	/** Parse an xml file */
    public ArrayList<XMLNotification> parseXML2(String filename) {
        return xmlParser2.parse(filename);
    }
	
	/** Get the most recently parsed lesson */
	public Lesson getLesson() {
	    return xmlParser2.getLesson();
	}
	
	/** Write a lesson to xml */
	public ArrayList<String> writeXML(Lesson lesson, String filename) {
	    return xmlWriter.writeXML(lesson, filename);
	}
	
	/** Get the recent read path */
	public String getRecentReadPath() {
	    return recentReadPath;
	}
	
	/** Set the recent read path */
	public void setRecentReadPath(String nPath) {
	    this.recentReadPath = nPath;
	}
	
	/** Get the recent write path */
    public String getRecentWritePath() {
        return recentWritePath;
    }
    
    /** Set the recent write path */
    public void setRecentWritePath(String nPath) {
        this.recentWritePath = nPath;
    }
}
