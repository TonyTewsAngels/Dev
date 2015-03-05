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
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
		Scene scene = new Scene(grid, 500, 500);
		
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
		BorderPane contentPane = new BorderPane();
		AnchorPane botAnchor = new AnchorPane();
		GridPane gridBot = new GridPane();
		
		/* Set gridBot to resize correctly */
		
		ColumnConstraints alignLeft = new ColumnConstraints();
		alignLeft.setMaxWidth(150);
		gridBot.getColumnConstraints().add(alignLeft);
		
		ColumnConstraints alignCenterLeft = new ColumnConstraints();
		alignCenterLeft.setFillWidth(true);
		alignCenterLeft.setHgrow(Priority.ALWAYS);
		gridBot.getColumnConstraints().add(alignCenterLeft);
		
		ColumnConstraints alignCenter = new ColumnConstraints();
		alignCenter.setMaxWidth(50);
		gridBot.getColumnConstraints().add(alignCenter);
		
		ColumnConstraints alignCenterRight = new ColumnConstraints();
		alignCenterRight.setFillWidth(true);
		alignCenterRight.setHgrow(Priority.ALWAYS);
		gridBot.getColumnConstraints().add(alignCenterRight);
		
		ColumnConstraints alignRight = new ColumnConstraints();
		alignRight.setMaxWidth(150);
		gridBot.getColumnConstraints().add(alignRight);
		
		group.minHeight(200);
		group.maxHeight(200);
		group.minWidth(200);
		group.maxWidth(200);
		
		/* Set Pane Colours */
		hBoxTop.setStyle("-fx-background-color: blue;");
		group.setStyle("-fx-blend-mode: hard-light;");
		contentPane.setStyle("-fx-background-color: rgb(74, 104, 177);");
		botAnchor.setStyle("-fx-background-color: Grey;");
		gridBot.setStyle("-fx-background-color: Grey;");
		
		/* Setup the window */
        primaryStage.setTitle("Grid with hbox test");
        primaryStage.setScene(scene);
        
        /* Create a buttons */
        Button fileBtn = new Button();
        Button editBtn = new Button();
        Button prevBtn = new Button();
        Button nextBtn = new Button();
      
        
        /* Add button images */
        Image image = new Image("file://userfs/lt669/w2k/Desktop/Workspace/Learneasy_v2_3.png");
        ImageView imageNext = new ImageView(image);
        imageNext.setFitWidth(20);
        imageNext.setFitHeight(20);
        nextBtn.setGraphic(imageNext);
        
        
        /* Add central LE icon */
        Image image2 = new Image("file://userfs/lt669/w2k/Desktop/Workspace/Learneasy_v2_3.png");
        ImageView LE = new ImageView(image2);
        LE.setFitWidth(50);
        LE.setFitHeight(50);
        
        
        /* Setup the buttons */
        fileBtn.setText("File");
        fileBtn.setId("fileBtn");
          
        editBtn.setText("Edit");
        editBtn.setId("editBtn");
        
        prevBtn.setText("Previous");
        prevBtn.setId("prevBtn");

    //  nextBtn.setText("Next");
        nextBtn.setId("hBtn");
        
        /* Create Menubar */
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        Menu menuHelp = new Menu("Help");
        menuBar.setPrefWidth(10000);
        menuBar.getMenus().addAll(menuFile, menuEdit, menuHelp);

        /* Create some text and set it up*/
        text = new Text(200, 200,"Content Pane");
        text.setFont(Font.font ("Verdana", 20));
        text.setId("text");
        
        Rectangle r = new Rectangle();
        r.setWidth(1250);
        r.setHeight(703.125);
        r.setFill(Color.WHITE);
        r.setEffect(new DropShadow());
        
        
        /* Set the position of group in content pane */
        contentPane.setCenter(group);
        
        
        
        /* Add content to panes */
        hBoxTop.getChildren().addAll(menuBar,imageNext);
        group.getChildren().addAll(text,r,imageNext);
       // contentPane.getChildren().addAll(group);
        //botAnchor.getChildren().addAll(nextBtn, prevBtn,LE);
        gridBot.add(prevBtn, 0, 0); 
        gridBot.add(LE, 2, 0);      
        gridBot.add(nextBtn, 4, 0);
        gridBot.setAlignment(Pos.CENTER);
        
        
		/* Add other panes to grid layout */
        grid.add(hBoxTop, 0, 0);
		grid.add(contentPane, 0, 1);
		//grid.add(botAnchor, 0, 2);
		grid.add(gridBot, 0, 2);
		AnchorPane.setRightAnchor(nextBtn, 8.0);
		AnchorPane.setLeftAnchor(prevBtn, 8.0);
		
		/* Show the window */
		primaryStage.show(); 
		
	}
	public static void main(String[] args) {
		launch(args);
	}
	
	
}
