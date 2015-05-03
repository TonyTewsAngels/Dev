
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
import javafx.scene.layout.AnchorPane;
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
 * TeachEasy GUI based on BorderLayout TE_GUI_3 (Check Lucid chart diagram)
 * 
 * @author Lewis Thresh
 * @version 1.0 18 April 2015
 */

public class TE_GUI_3 extends Application {

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
        GridPane innerGrid = new GridPane();
        Scene scene = new Scene(innerGrid, 500, 500);
	
        /* Instantiate content of Group */
        GridPane masterGrid = new GridPane();
        BorderPane masterBorder = new BorderPane();
        GridPane topGrid = new GridPane();
        MenuBar menuBar = new MenuBar();
        HBox topBar = new HBox(100);
        //GridPane innerGrid = new GridPane();
        BorderPane innerBorder = new BorderPane();
        
        AnchorPane propertiesAnchor = new AnchorPane();
        
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
        propertiesAnchor.setStyle("-fx-background-color: black;");
        
        
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
        
        /* Button */
        
        Button butt = new Button("Dummy");
        
 
        
        
        /* Set row constraints */
        
   /*     RowConstraints midRow = new RowConstraints();
        midRow.setFillHeight(true);
        midRow.setVgrow(Priority.ALWAYS);
        masterGrid.getRowConstraints().add(midRow);*/
    	
     /*   RowConstraints topRow = new RowConstraints();
        topRow.setMaxHeight(1000);
        masterGrid.getRowConstraints().add(topRow);
        
        RowConstraints midRow = new RowConstraints();
        midRow.setMaxHeight(1000);
        masterGrid.getRowConstraints().add(midRow);
        
        RowConstraints botRow = new RowConstraints();
        botRow.setMaxHeight(1000);
        masterGrid.getRowConstraints().add(botRow);*/
        
/*        ColumnConstraints masterCol = new ColumnConstraints();
        masterCol.setMaxWidth(800);
        masterGrid.getColumnConstraints().add(masterCol);*/
        
       /* ColumnConstraints masterCol = new ColumnConstraints();
        masterCol.setFillWidth(true);
        masterCol.setHgrow(Priority.ALWAYS);
     //   masterCol.setPrefWidth(2000);
       // masterCol.setMaxWidth(2000);
        masterGrid.getColumnConstraints().add(masterCol);*/
   
        
        
        RowConstraints topRow = new RowConstraints();
        topRow.setMaxHeight(900);
        innerGrid.getRowConstraints().add(topRow);
        
        RowConstraints innerRow = new RowConstraints();
        innerRow.setFillHeight(true);
        innerRow.setVgrow(Priority.ALWAYS);
        innerGrid.getRowConstraints().add(innerRow);
        
        RowConstraints content = new RowConstraints();
        content.setMaxHeight(900);
        innerGrid.getRowConstraints().add(content);
        
        RowConstraints innerBot = new RowConstraints();
        innerBot.setFillHeight(true);
        innerBot.setVgrow(Priority.ALWAYS);
        innerGrid.getRowConstraints().add(innerBot);
    	
        RowConstraints botBarRow = new RowConstraints();
        botBarRow.setMaxHeight(900);
        innerGrid.getRowConstraints().add(botBarRow);
        
        
        /* Column constraints for innerGrid */
        
  /*      ColumnConstraints farLeft = new ColumnConstraints();
        farLeft.setMaxWidth(10);
        innerGrid.getColumnConstraints().add(farLeft); */
        
        ColumnConstraints farLeft = new ColumnConstraints();
        farLeft.setFillWidth(true);
       // centerRow.setMaxWidth(100);
        farLeft.setHgrow(Priority.SOMETIMES);
        innerGrid.getColumnConstraints().add(farLeft);
   
        ColumnConstraints contentCol = new ColumnConstraints();
        contentCol.setMaxWidth(1000);
        innerGrid.getColumnConstraints().add(contentCol);
        
        ColumnConstraints centerRow = new ColumnConstraints();
        centerRow.setFillWidth(true);
       // centerRow.setMaxWidth(100);
        centerRow.setHgrow(Priority.SOMETIMES);
        innerGrid.getColumnConstraints().add(centerRow);
        
        ColumnConstraints propertiesCol = new ColumnConstraints();
        propertiesCol.setMaxWidth(50);
        innerGrid.getColumnConstraints().add(propertiesCol);
        
        /* Add content to panes */
        
       // masterGroup.getChildren().addAll(innerGrid);
        
//        masterGrid.add(masterBorder, 0, 0);
  /*      masterGrid.add(topGrid,0,0);
        masterGrid.add(innerGrid,0,1);
        masterGrid.add(bottomBar,0 ,2);
        */
     /*   masterBorder.setTop(topGrid);
        masterBorder.setCenter(propertiesAnchor);
        masterBorder.setRight(propertiesAnchor);*/
        
       // propertiesAnchor.setLeftAnchor(innerGrid, 10.0);
       // propertiesAnchor.setRightAnchor(propertiesPanel, 10.0);
        innerGrid.add(topGrid,0,0,4,1);
        innerGrid.add(contentPanel,1,1);
        innerGrid.add(propertiesPanel,3,1);
        innerGrid.add(bottomBar,0,2,4,1);
     /*   AnchorPane.setTopAnchor(topGrid, 1.0);
        AnchorPane.setTopAnchor(innerGrid, 50.0);
        AnchorPane.setTopAnchor(propertiesPanel, 50.0);
        
        AnchorPane.setLeftAnchor(innerGrid, 50.0);
        AnchorPane.setRightAnchor(propertiesPanel, 10.0);
        
        propertiesAnchor.getChildren().addAll(topGrid,propertiesPanel,innerGrid);
        */
        topGrid.add(menuBar,0,0);
        topGrid.add(topBar,0,1);
        
        topBar.getChildren().addAll(text);
        
        contentPanel.getChildren().addAll(r);
        
        propertiesPanel.getChildren().addAll(propText, butt);
        
        bottomBar.getChildren().addAll(botText);
        
        /* Setup the window */
        primaryStage.setTitle("TE_GUI_3");
        primaryStage.setScene(scene);
        
        /* Show the window */
		primaryStage.show();
        
    }
   	public static void main(String[] args) {
   		launch(args);
   	}
   	
   }