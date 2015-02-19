/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.debug;

import java.util.ArrayList;

import teacheasy.data.Lesson;
import teacheasy.xml.XMLHandler;

/**
 * This class provides a dummy UI for
 * use with Iteration 1 code.
 * 
 * @version 	1.0 05 Feb 2015
 * @author 		Alistair Jewers
 */
public class Iteration1DummyUI {
	/** Handles XML read and write operations */
	private XMLHandler xmlHandler;
	
	/** Constructor Method */
	public Iteration1DummyUI() {
		
		/* Instantiate XML Handler */
		xmlHandler = new XMLHandler();
		
		// Create GUI Frame
		
		// Test Parser
		ArrayList<String> errorList = xmlHandler.parseXML("testXML.xml");
		
		System.out.println(errorList.size() + " error(s)\n");
		
		for(int i = 0; i < errorList.size(); i++) {
		    System.out.println(errorList.get(i));
		}
		
		Lesson lesson = xmlHandler.getLesson();
		
		lesson.debugPrint();
		
		xmlHandler.writeLesson(lesson);
	}
	
	public void makeFrame() {
	    
	}

	/** Main Function called when application is run */
	public static void main(String[] args) {
		new Iteration1DummyUI();
	}
}
