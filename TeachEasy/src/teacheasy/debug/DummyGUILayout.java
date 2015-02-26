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
import javafx.scene.layout.RowConstraints;
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
		grid.setStyle("-fx-background-color:red");
		Scene scene = new Scene(grid, 500, 700);
		
		/* Set gridPane constraints to resize with window */
		ColumnConstraints columnConstraints = new ColumnConstraints();
		columnConstraints.setFillWidth(true);
		columnConstraints.setHgrow(Priority.ALWAYS);
		grid.getColumnConstraints().add(columnConstraints);
			
		/* Set row sizes */
		RowConstraints row1 = new RowConstraints();
		row1.setMaxHeight(100);
		grid.getRowConstraints().add(row1);
		
		RowConstraints row2 = new RowConstraints();
		row2.setFillHeight(true);
		row2.setVgrow(Priority.ALWAYS);
		grid.getRowConstraints().add(row2);
		
		RowConstraints row3 = new RowConstraints();
		row3.setMaxHeight(100);
		grid.getRowConstraints().add(row3);
		
		/* Instantiate content of main Grid layout */
		HBox hBoxTop = new HBox();		
		Group group = new Group();
		AnchorPane botAnchor = new AnchorPane();

		/* Set Pane Colours */
		hBoxTop.setStyle("-fx-background-color: blue;");
		group.setStyle("-fx-background-color: Green;");
		botAnchor.setStyle("-fx-background-color: yellow;");
		
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

        nextBtn.setText("Next");
        nextBtn.setId("hBtn");
 
        
        /* Create some text and set it up*/
        text = new Text(400, 500,"Content Pane");
        text.setFont(Font.font ("Verdana", 20));
        text.setId("text");
        
        /* Add content to panes */
        hBoxTop.getChildren().addAll(fileBtn, editBtn);
        group.getChildren().addAll(text);
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
