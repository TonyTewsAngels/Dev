/*
 * Lewis Thresh
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */

package teacheasy.debug;

import java.awt.Insets;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import teacheasy.data.Lesson;
import teacheasy.debug.GeneralDummyGUI.buttonEventHandler;
import teacheasy.xml.XMLHandler;

/**
 * Class created to test different GUI layouts
 * 
 * @version	1.0 25 Feb 2015
 * @author 	lt669
 *
 */

public class DummyGUILayout extends Application {
	Text text;

	
	@Override
	public void start (Stage primaryStage) {
		
		/* Get screen size */
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		
		/* Instantiate the scene and main Grid layout*/
		GridPane grid = new GridPane();
		//grid.setStyle("-fx-background-color:red");
		Scene scene = new Scene(grid, 500, 700);
		
		/* Set gridPane constraints to resize with window */
		ColumnConstraints columnConstraints = new ColumnConstraints();
		columnConstraints.setFillWidth(true);
		columnConstraints.setHgrow(Priority.ALWAYS);
		grid.getColumnConstraints().add(columnConstraints);
		
		
		/* Set grid spacing */
		grid.setVgap(10);
		
		/* Instantiate content of main Grid layout */
		HBox hBoxTop = new HBox();		
		Group group = new Group();
		HBox hBoxBottom = new HBox();
		AnchorPane botAnchor = new AnchorPane();
		

		/* Set Pane Colours */
		hBoxTop.setStyle("-fx-background-color: blue;");
		group.setStyle("-fx-background-color: Green;");
		hBoxBottom.setStyle("-fx-background-color: yellow;");
		
		/* Setup the window */
        primaryStage.setTitle("Grid with hbox test");
        primaryStage.setScene(scene);
        
        /* Create a buttons */
        Button fileBtn = new Button();
        Button editBtn = new Button();
        Button prevBtn = new Button();
        Button nextBtn = new Button();
        
        
        /* Setup the buttons */
        fileBtn.setText("File");
        fileBtn.setId("fileBtn");
          
        editBtn.setText("Edit");
        editBtn.setId("editBtn");
        
        prevBtn.setText("Previous");
        prevBtn.setId("prevBtn");

        nextBtn.setText("hBtn");
        nextBtn.setId("hBtn");
 
        
        /* Create some text and set it up*/
        text = new Text(400, 500,"Content Pane");
        text.setFont(Font.font ("Verdana", 20));
        text.setId("text");
        
        /* Dummy content for content pane */
        
        for (int i = 0; i < 5; i++) {
	        Rectangle r = new Rectangle();
	        r.setY(i * 20);
	        r.setWidth(100);
	        r.setHeight(10);
	        r.setFill(Color.YELLOW);
	        group.getChildren().addAll(r);
        }
   
        
        /* Add content to panes */
        hBoxTop.getChildren().addAll(fileBtn, editBtn);
  
        hBoxBottom.getChildren().addAll(nextBtn,prevBtn);
        botAnchor.getChildren().addAll(nextBtn, prevBtn);

		/* Add other panes to grid layout */
        grid.add(hBoxTop, 0, 0);
		grid.add(group, 0, 1);
		grid.add(botAnchor, 0, 2);
		AnchorPane.setRightAnchor(nextBtn, 8.0);
		AnchorPane.setLeftAnchor(prevBtn, 8.0);
		
		/* Show the window */
		primaryStage.show(); 
		
	}
	public static void main(String[] args) {
		launch(args);
	}
	
	
}
