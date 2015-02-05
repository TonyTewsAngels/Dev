/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.xml;

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
	private XMLParser xmlParser;
	
	/** XML Writer */
	private XMLWriter xmlWriter;
	
	/** Constructor Method */
	public XMLHandler() {
		
		/* Instantiate the Parser */
		xmlParser = new XMLParser();
		
		/* Instantiate the Writer */
		xmlWriter = new XMLWriter();
	}
	
	/** Parse an xml file */
	public void parseXML(String filename) {
		xmlParser.parse(filename);
	}
}
