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
import javafx.scene.control.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import teacheasy.data.Lesson;
import teacheasy.debug.GeneralDummyGUI.buttonEventHandler;
import teacheasy.xml.XMLHandler;
import teacheasy.xml.util.XMLNotification;

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
	}

	/** Main Function called when application is run */
	public static void main(String[] args) {
	    /* Constructor */
		new Iteration1DummyUI();
		
		/* Launch the java FX application */
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
        Button printBtn = new Button();
        
        /* Create the text area */
        text = new TextArea();
        
        /* Setup the buttons */
        parseBtn.relocate(75.0, 100.0);
        parseBtn.setText("Parse XML");
        parseBtn.setId("XMLParse");
        
        writeBtn.relocate(225.0, 100.0);
        writeBtn.setText("Write XML");
        writeBtn.setId("XMLWrite");
        
        printBtn.relocate(375.0, 100.0);
        printBtn.setText("Print Lesson");
        printBtn.setId("LessonPrint");
        
        /* Setup the text boxes */
        parseBox.relocate(40.0, 75.0);
        
        writeBox.relocate(190.0, 75.0);
        
        /* Setup the text area */
        text.relocate(40.0, 150.0);
        
        /* Set the button to use the button event handler */
        parseBtn.setOnAction(new buttonEventHandler());
        writeBtn.setOnAction(new buttonEventHandler());
        printBtn.setOnAction(new buttonEventHandler());
        
        /* Add the button to the group */
        group.getChildren().addAll(parseBox, writeBox, parseBtn, writeBtn, printBtn, text);
        
        /* Show the window */
        primaryStage.show(); 
    }
    
    public void parseXML(String filename) {
        /* Clear the error text area */
        text.clear();
        
        /* Notify parsing beginning */
        text.appendText("Parsing \'" + filename + "\'...\n");
        
        /* Parse the xml and get errors */
        ArrayList<XMLNotification> errorList = xmlHandler.parseXML2(filename);
        
        /* Output the errors */
        text.appendText("Errors: " + errorList.size() + ".\n");
        
        for(int i = 0; i < errorList.size(); i++) {
            text.appendText(errorList.get(i).toString() + ".\n");
        }
        
        /* Get the lesson if clean */
        if(errorList.size() == 0) {
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
            } else if(button.getId().equals("LessonPrint")) {
                lesson.debugPrint();
            }
        }
    }
}
