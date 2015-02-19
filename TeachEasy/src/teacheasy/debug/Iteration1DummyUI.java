/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.debug;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import teacheasy.data.Lesson;
import teacheasy.debug.GeneralDummyGUI.buttonEventHandler;
import teacheasy.xml.XMLHandler;

/**
 * This class provides a dummy UI for
 * use with Iteration 1 code.
 * 
 * @version 	1.0 05 Feb 2015
 * @author 		Alistair Jewers
 */
public class Iteration1DummyUI extends Application {
	/** Handles XML read and write operations */
	private XMLHandler xmlHandler;
	
	/** The text input boxes for the two file names */
	private TextField parseBox;
	private TextField writeBox;
	
	/** The text area for reporting errors */
	private TextArea text;
	
	/** The lesson object */
	Lesson lesson;
	
	/** Constructor Method */
	public Iteration1DummyUI() {
		
		/* Instantiate XML Handler */
		xmlHandler = new XMLHandler();
		
		/* Instantiate an empty lesson */
		lesson = new Lesson();
		
		// Create GUI Frame
		//launch();
		
		/* Test Parser
		ArrayList<String> errorList = xmlHandler.parseXML("testXML.xml");
		
		System.out.println(errorList.size() + " error(s)\n");
		
		for(int i = 0; i < errorList.size(); i++) {
		    System.out.println(errorList.get(i));
		}
		
		Lesson lesson = xmlHandler.getLesson();
		
		lesson.debugPrint();
		
		xmlHandler.writeLesson(lesson);*/
	}

	/** Main Function called when application is run */
	public static void main(String[] args) {
		new Iteration1DummyUI();
		
		launch();
	}

    @Override
    public void start(Stage primaryStage) throws Exception {
        /* Instantiate the scene and group */
        Group group = new Group();
        Scene scene = new Scene(group, 550, 350);
        
        /* Setup the window */
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        
        /* Create the text boxes */
        parseBox = new TextField();
        writeBox = new TextField();
        
        /* Create the buttons */
        Button parseBtn = new Button();
        Button writeBtn = new Button();
        
        /* Create the text area */
        text = new TextArea();
        
        /* Setup the buttons */
        parseBtn.relocate(75.0, 100.0);
        parseBtn.setText("Parse XML");
        parseBtn.setId("XMLParse");
        
        writeBtn.relocate(225.0, 100.0);
        writeBtn.setText("Write XML");
        writeBtn.setId("XMLWrite");
        
        /* Setup the text boxes */
        parseBox.relocate(40.0, 75.0);
        
        writeBox.relocate(190.0, 75.0);
        
        /* Setup the text area */
        text.relocate(40.0, 150.0);
        
        /* Set the button to use the button event handler */
        parseBtn.setOnAction(new buttonEventHandler());
        writeBtn.setOnAction(new buttonEventHandler());
        
        /* Add the button to the group */
        group.getChildren().addAll(parseBox, writeBox, parseBtn, writeBtn, text);
        
        /* Show the window */
        primaryStage.show(); 
    }
    
    public void parseXML(String filename) {
        /* Clear the error text area */
        text.clear();
        
        /* Notify parsing beginning */
        text.appendText("Parsing \'" + filename + "\'...\n");
        
        /* Parse the xml and get errors */
        ArrayList<String> errors = xmlHandler.parseXML(filename);
        
        /* Output the errors */
        text.appendText("Errors: " + errors.size() + ".\n");
        
        for(int i = 0; i < errors.size(); i++) {
            text.appendText(errors.get(i) + ".\n");
        }
        
        /* Get the lesson if clean */
        if(errors.size() == 0) {
            lesson = xmlHandler.getLesson();
        }
    }
    
    public void writeXML(String filename) {
        /* Clear the error text area */
        text.clear();
        
        /* Notify writing beginning */
        text.appendText("Writing \'" + filename + "\'...\n");
        
        /* Write the XML file */
        ArrayList<String> errors = xmlHandler.writeXML(lesson, filename);
        
        /* Output the errors */
        text.appendText("Errors: " + errors.size() + ".\n");
        
        for(int i = 0; i < errors.size(); i++) {
            text.appendText(errors.get(i) + ".\n");
        }
    }
    
    /**
     * Event Handler Class
     */
    public class buttonEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            /* Cast the source of the event to a button */
            Button button = (Button)e.getSource();
            
            /* Act based on the ID of the button */
            if(button.getId().equals("XMLParse")) {
                parseXML(parseBox.getCharacters().toString());
            } else if(button.getId().equals("XMLWrite")) {
                writeXML(writeBox.getCharacters().toString());
            }
        }
    }
}
