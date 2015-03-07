/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.debug;

import java.util.ArrayList;

import teacheasy.data.MultipleChoiceObject.MultiChoiceType;
import teacheasy.data.MultipleChoiceObject.Orientation;
import teacheasy.data.multichoice.Answer;
import teacheasy.mediahandler.MultipleChoiceHandler;
import teacheasy.mediahandler.multichoice.MultipleChoice;
import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.*;

/**
 * This class is a dummy GUI for people to play with
 * to get to grips with JavaFX.
 * 
 * @author Alistair Jewers
 * @version 1.0 19 Feb 2015
 */


public class MultipleChoiceDummyGUI extends Application {
	MultipleChoiceHandler multipleChoiceHandler;

    /**
     * Override the start method inside the JavaFX Application
     * class. This is called to start the program.
     */
    @Override
    public void start(Stage primaryStage) {  	
        /* Get the screen size */
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        
        /* Instantiate the scene and group */
        Group group = new Group();
        Scene scene = new Scene(group);
        
        /* Setup the window */
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getMaxX());
        primaryStage.setHeight(bounds.getMaxY());
        
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(new Answer("Correct Answer", true));
        answers.add(new Answer("INCorrect Answer", false));
        answers.add(new Answer("Correct Answer", true));
        answers.add(new Answer("INCorrect Answer", false));
        answers.add(new Answer("Correct Answer", true));
        
        multipleChoiceHandler = new MultipleChoiceHandler(group);
        
        multipleChoiceHandler.createMultipleChoice(answers, 10, 5, MultiChoiceType.CHECKBOX, Orientation.VERTICAL);
        multipleChoiceHandler.createMultipleChoice(answers, 100, 5, MultiChoiceType.RADIO, Orientation.HORIZONTAL);
        
        /* Show the window */
        primaryStage.show(); 
    }
    
    /**
     * Main method to start the program.
     */
    public static void main(String args[]) {
        launch();
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
            if(button.getId().equals("GUIButton")) {
                System.out.println("GUI");
            } else if(button.getId().equals("OtherButton")) {
                System.out.println("Other");
            }
        }
    }  
}

