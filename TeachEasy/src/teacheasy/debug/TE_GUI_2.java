
/*
 * Lewis Thresh
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */

package teacheasy.debug;

import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.*;

/**
 * 
 * TeachEasy GUI based on BorderLayout
 * 
 * @author Lewis Thresh
 * @version 1.0 18 April 2015
 */

public class TE_GUI_2 extends Application {

Text text, botText, propText;
	
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
        Group masterGroup = new Group();       
        Scene scene = new Scene(masterGroup, 500, 500);
	
        /* Instantiate content of Group */
        GridPane masterGrid = new GridPane();
        
        BorderPane masterBorder = new BorderPane();
        GridPane topGrid = new GridPane();
        MenuBar menuBar = new MenuBar();
        HBox topBar = new HBox(100);
        Group contentPanel = new Group();
        VBox propertiesPanel = new VBox();
        HBox bottomBar = new HBox();      

        
        /* Set layout colours */
        
      //  masterBorder.setStyle("-fx-background-color: black;");
        topBar.setStyle("-fx-background-color: green;");
        propertiesPanel.setStyle("-fx-background-color: yellow;");
        contentPanel.setStyle("-fx-background-color: white;");
        bottomBar.setStyle("-fx-background-color: blue;");
        masterGrid.setStyle("-fx-background-color: pink;");
        
        
        /* Setup Menubar */
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        Menu menuHelp = new Menu("Help");
        Menu menuOption = new Menu("Cunt Button");
        menuBar.setPrefWidth(10000);
        menuBar.getMenus().addAll(menuFile, menuEdit, menuHelp, menuOption);
        
        
        /* DUMMY CONTENT */
        
        /* Text */
        text = new Text(1, 1,"Top Bar");
        text.setFont(Font.font("Verdana", 20));
        text.setId("text");
        
        botText = new Text(1, 1,"bottemBar");
        botText.setFont(Font.font("Verdana", 20));
        botText.setId("botText");
        
        propText = new Text(1, 1,"propText");
        propText.setFont(Font.font("Verdana", 20));
        propText.setId("botText");
        
        /* Page */
        
        Rectangle r = new Rectangle();
        r.setWidth(937.5);
        r.setHeight(527.34);
        r.setFill(Color.WHITE);
        r.setEffect(new DropShadow());
        
        
        /* Set row constraints */
        
        RowConstraints midRow = new RowConstraints();
        midRow.setFillHeight(true);
        midRow.setVgrow(Priority.ALWAYS);
        masterGrid.getRowConstraints().add(midRow);
    	
    	
        RowConstraints botRow = new RowConstraints();
        botRow.setMaxHeight(100);
        masterGrid.getRowConstraints().add(botRow);
   
        
        
      //  masterBorder.setPrefSize(1000, 1000);
        
        
        /* Add content to panes */
        
        masterGroup.getChildren().addAll(masterGrid);
        
        masterGrid.add(masterBorder, 0, 0);
        masterGrid.add(bottomBar,0 ,1);
        
        masterBorder.setMinSize(500,400);
        masterBorder.setPrefSize(700,700);
        masterBorder.setMaxSize(10000,10000);
        
        masterBorder.setTop(topGrid);
//        masterBorder.setLeft(contentPanel);
        masterBorder.setLeft(contentPanel);
       // masterBorder.getMaxWidth(Left,100);
        masterBorder.setAlignment(propertiesPanel, Pos.TOP_RIGHT);

        masterBorder.setRight(propertiesPanel);
        //masterBorder.setBottom(bottomBar);
        
        topGrid.add(menuBar,0,0);
        topGrid.add(topBar,0,1);
        
        topBar.getChildren().addAll(text);
        
        contentPanel.getChildren().addAll(r);
        
        propertiesPanel.getChildren().addAll(propText);
        
        bottomBar.getChildren().addAll(botText);
        
        /* Setup the window */
        primaryStage.setTitle("TE_GUI_2");
        primaryStage.setScene(scene);
        
        /* Show the window */
		primaryStage.show();
        
    }
   	public static void main(String[] args) {
   		launch(args);
   	}
   	
   }