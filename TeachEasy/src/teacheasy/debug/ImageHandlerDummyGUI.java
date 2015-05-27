/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.debug;

import teacheasy.mediahandler.ImageHandler;
import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.*;

/**
 * This class is a dummy GUI for the purpose
 * of testing out Java FX image support.
 * 
 * @author Alistair Jewers
 * @version 1.0 19 Feb 2015
 */
public class ImageHandlerDummyGUI extends Application {

	/*Image Handler*/
	ImageHandler imageHandler;
	
    /**
     * Override the start method inside the JavaFX Application
     * class. This is called to start the program.
     */
    @Override
    public void start(Stage primaryStage) {       

    	
        /* Instantiate the scene and group */
        Group group = new Group();
        Scene scene = new Scene(group, 500, 500);
        
        /* Setup the window */
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        
        /* Create a button */
        Button btn = new Button();
        
        /* Setup the button */
        btn.setText("A Button");
        btn.setId("Button1");
        btn.relocate(200.0, 50.0);
        
        /* Set the button to use the button event handler */
        btn.setOnAction(new buttonEventHandler());
        
        /* Add the button to the group */
        group.getChildren().addAll(btn);
        
        /*Instantiate the image handler*/
        imageHandler = new ImageHandler(group);
        
        /*Use the image handler to create an image stored on disk*/
        imageHandler.insertImage("rabbit.jpg", 0, 100, 1, 1, 0, true);
        
        /*Use the image handler to create an image stored online*/
        imageHandler.insertImage("http://www.county-vets.co.uk/media/resources/rabbit.jpg",
        		600, 100, 1, 1, 0, true);
        
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
            if(button.getId().equals("Button1")) {
                System.out.println("Button Pressed");
            }
        }
    }  
}
