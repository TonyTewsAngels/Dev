/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.debug;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;

import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.*;

import teacheasy.data.ImageHandler;

/**
 * This class is a dummy GUI for the purpose
 * of testing out the image handler
 * 
 * @author Alex Cash
 * @version 1.0 19 Feb 2015
 */
public class ImageHandlerTestGUI extends Application {

    /**
     * Override the start method inside the JavaFX Application
     * class. This is called to start the program.
     */
    @Override
    public void start(Stage primaryStage) {       
       
    	ImageHandler imageHandler = new ImageHandler();
    	ImageView imageToShow = new ImageView();
    	File sourceFile = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Hydrangeas.jpg");
    	float xScale = (float) 1.0;
    	float yScale = (float) 1.0;
    	double xStart = 60;
    	double yStart = 0;
    	imageToShow = imageHandler.getImage(sourceFile, xStart, yStart, xScale, yScale, 0);

    	
    	/* Instantiate the scene and group */
        Group group = new Group();
        Scene scene = new Scene(group, imageToShow.getImage().getWidth()+xStart, imageToShow.getImage().getHeight()+yStart);
        System.out.println("image width()"+imageToShow.getImage().getWidth());
        System.out.println("image height()"+imageToShow.getImage().getHeight());
                
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();
        
        /* Setup the window */
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.setWidth(screenWidth);
        primaryStage.setHeight(screenHeight-40);
        
        primaryStage.setOpacity(1.0);

        
        /* Add the button to the group */
        group.getChildren().addAll(imageToShow);
        
        /* Show the window */
        primaryStage.show(); 
    }
    
    /**
     * Main method to start the program.
     */
    public static void main(String args[]) {
        launch();
    }
    
   
}
