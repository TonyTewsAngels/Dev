
/*
 * Lewis Thresh
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */

package teacheasy.debug;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

Text text, botText, propText, titleText;
Image textIm;
	
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
        GridPane innerGrid = new GridPane();
        Scene scene = new Scene(innerGrid, 500, 500);
	
        /* Instantiate content of Group */
        GridPane topGrid = new GridPane();
        MenuBar menuBar = new MenuBar();
        HBox topBar = new HBox(5);
        HBox titleBox = new HBox();
        HBox preview = new HBox();
        Group contentPanel = new Group();
        VBox propertiesPanel = new VBox();
        HBox bottomBar = new HBox();      

        
        /* Set layout colours */
        topGrid.setStyle("-fx-background-color: Grey;");
        propertiesPanel.setStyle("-fx-background-color:  rgb(177, 177, 177);");
        contentPanel.setStyle("-fx-background-color: rgb(101, 4, 7);");
        bottomBar.setStyle("-fx-background-color: Grey;");
        innerGrid.setStyle("-fx-background-color: rgb(101, 4, 7);");
        
        /* Setup Menubar */
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        Menu menuHelp = new Menu("Help");
        menuBar.setPrefWidth(10000);
        menuBar.getMenus().addAll(menuFile, menuEdit, menuHelp);
        
        
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
        
        titleText = new Text(1, 1,"TITLE");
        titleText.setFont(Font.font("Verdana", 30));
        titleText.setId("titleText");
        
        
        /* Page */
        
        Rectangle r = new Rectangle();
        r.setWidth(937.5);
        r.setHeight(527.34);
        r.setFill(Color.WHITE);
        r.setEffect(new DropShadow());
        
        /* Buttons */
        
        Button butt = new Button("Dummy");
        
        Button text = new Button();
        text.setStyle("-fx-background-color: transparent;");
        Button image = new Button();
        Button video = new Button();
        Button tick = new Button();
        Button graphic = new Button();
        Button audio = new Button();
     
        Button prev = new Button("Preview");
        
        /*Button dimensions*/
        
     
        
        
        /* Create page tabs */
        
        /* Re-skin buttons */
      
   
        int x = 40;
        int y = 40;
        
        /* Images */
        
        //Text Box
        Image textImST = new Image(getClass().getResourceAsStream("Textbox_ST_TOP_CIRC_Blue_T-01.png"));
        Image textImHO = new Image(getClass().getResourceAsStream("Textbox_HO_TOP_CIRC_Blue_T-01.png"));
        Image textImPR = new Image(getClass().getResourceAsStream("Textbox_PRE_TOP_CIRC_Blue_T-01.png"));
        
        //Image
        Image imImST = new Image(getClass().getResourceAsStream("Textbox_ST_TOP_CIRC_Blue_T-01.png"));
        Image imImHO = new Image(getClass().getResourceAsStream("Textbox_ST_TOP_CIRC_Blue_T-01.png"));
        Image imImPR = new Image(getClass().getResourceAsStream("Textbox_ST_TOP_CIRC_Blue_T-01.png"));
        
        //Video
        Image vidImST = new Image(getClass().getResourceAsStream("Textbox_ST_TOP_CIRC_Blue_T-01.png"));
        Image vidImHO = new Image(getClass().getResourceAsStream("Textbox_ST_TOP_CIRC_Blue_T-01.png"));
        Image vidImPR = new Image(getClass().getResourceAsStream("Textbox_ST_TOP_CIRC_Blue_T-01.png"));
        
        //Audio
        Image audioImST = new Image(getClass().getResourceAsStream("Textbox_ST_TOP_CIRC_Blue_T-01.png"));
        Image audioImHO = new Image(getClass().getResourceAsStream("Textbox_ST_TOP_CIRC_Blue_T-01.png"));
        Image audioImPR = new Image(getClass().getResourceAsStream("Textbox_ST_TOP_CIRC_Blue_T-01.png"));
        
        //Graphics
        Image graphicImST = new Image(getClass().getResourceAsStream("Textbox_ST_TOP_CIRC_Blue_T-01.png"));
        Image graphicImHO = new Image(getClass().getResourceAsStream("Textbox_ST_TOP_CIRC_Blue_T-01.png"));
        Image graphicImPR = new Image(getClass().getResourceAsStream("Textbox_ST_TOP_CIRC_Blue_T-01.png"));
        
        //Question Box
        Image queImST = new Image(getClass().getResourceAsStream("Textbox_ST_TOP_CIRC_Blue_T-01.png"));
        Image queImHO = new Image(getClass().getResourceAsStream("Textbox_ST_TOP_CIRC_Blue_T-01.png"));
        Image queImPR = new Image(getClass().getResourceAsStream("Textbox_ST_TOP_CIRC_Blue_T-01.png"));
        
        //Multiple Choice
        Image mulImST = new Image(getClass().getResourceAsStream("Textbox_ST_TOP_CIRC_Blue_T-01.png"));
        Image mulImHO = new Image(getClass().getResourceAsStream("Textbox_ST_TOP_CIRC_Blue_T-01.png"));
        Image mulImPR = new Image(getClass().getResourceAsStream("Textbox_ST_TOP_CIRC_Blue_T-01.png"));
        
        /* Image Views */
        ImageView textBox = new ImageView(textImST);
        
        
        
        /* Enent Handlers */
        final InvalidationListener hoverListener = new InvalidationListener() {
            public void invalidated(Observable ov) {
               if (textBoxST.isHover()) {
            	   ImageView textBox = new ImageView(textImHO);
                } else {
                    text.setText("not hovered");
              }
            }
        }
        
        ImageView textBoxHO = new ImageView(textImHO);
        ImageView textBoxPR= new ImageView(textImPR);
        
       /* ImageView imImST = new ImageView(imImST);
        ImageView imImHO = new ImageView(imImHO);
        ImageView imImPR= new ImageView(imImPR);

        ImageView vidImST = new ImageView(vidImST);
        ImageView vidImHO = new ImageView(vidImHO);
        ImageView vidImPR= new ImageView(vidImPR);
        
        ImageView audioImST = new ImageView(textImST);
        ImageView audioImHO = new ImageView(audioImHO);
        ImageView audioImPR= new ImageView(audioImPR);
        
        ImageView graphicImST = new ImageView(textImST);
        ImageView graphicImHO = new ImageView(graphicImHO);
        ImageView graphicImPR= new ImageView(graphicImPR);
        
        ImageView queImST = new ImageView(textImST);
        ImageView queImPR = new ImageView(queImPR);
        ImageView queImHO= new ImageView(queImHO);
        
        ImageView mulImST = new ImageView(mulImST);
        ImageView mulImHO = new ImageView(mulImHO);
        ImageView mulImPR= new ImageView(mulImPR);
        */
        
        text.setGraphic(textBoxST); 

        textBoxST.setFitWidth(x);
        textBoxST.setFitHeight(y);
        
        
       /* Image textIm = new Image("Textbox_ST_TOP_CIRC_Blue_T-01.png");
        ImageView textBox = new ImageView();
        textBox.setImage(textIm);
        text.setGraphic(textBox);*/
        

        
        
      
        
textBox.setOnMousePressed(new EventHandler<MouseEvent>(){
	public void handle(MouseEvent me) {textBoxPressed();}
});
        
        
        
        /* Top Bar Constraints */
        
        ColumnConstraints topFarLeft = new ColumnConstraints();
        topFarLeft.setMaxWidth(50);
        topGrid.getColumnConstraints().add(topFarLeft);
        
        ColumnConstraints stretchLeft = new ColumnConstraints();
        stretchLeft.setFillWidth(true);
        stretchLeft.setHgrow(Priority.SOMETIMES);
        topGrid.getColumnConstraints().add(stretchLeft);
        
        ColumnConstraints topCentre = new ColumnConstraints();
        topCentre.setMaxWidth(50);
        topGrid.getColumnConstraints().add(topCentre);
        
        ColumnConstraints stretchRight = new ColumnConstraints();
        stretchRight.setFillWidth(true);
        stretchRight.setHgrow(Priority.SOMETIMES);
        topGrid.getColumnConstraints().add(stretchRight);
        
        ColumnConstraints topFarRight = new ColumnConstraints();
        topFarRight.setMaxWidth(50);
        topGrid.getColumnConstraints().add(topFarRight);
        
        
        /* Set row constraints */
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
        botBarRow.setMaxHeight(1000);
        innerGrid.getRowConstraints().add(botBarRow);
        
        
        /* Column constraints for innerGrid */

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
        
        innerGrid.add(topGrid,0,0,4,1);
        innerGrid.add(contentPanel,1,1);
        innerGrid.add(propertiesPanel,3,1,1,3);
        innerGrid.add(bottomBar,0,4,4,1);

        topGrid.add(menuBar,0,0,5,1);
        topGrid.add(titleBox,0,1);
        topGrid.add(topBar,2,1);
        topGrid.add(preview,4,1);
        
        topBar.setAlignment(Pos.BASELINE_CENTER);
        titleBox.setAlignment(Pos.BASELINE_LEFT);
        preview.setAlignment(Pos.BASELINE_RIGHT);
        
        titleBox.getChildren().addAll(titleText);
        preview.getChildren().addAll(prev);
        topBar.getChildren().addAll(text,image,video,tick,graphic,audio);
        
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