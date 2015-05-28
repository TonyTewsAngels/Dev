package teacheasy.main;

import teacheasy.data.Lesson;
import teacheasy.main.TeachEasyClient.ButtonEventHandler;
import teacheasy.runtime.RunTimeData;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.*;

public class PreviewWindow {
    /* Text */
    Text text;
    
    /* UI Elements */
    Button nextPageBtn;
    Button prevPageBtn;
    
    /* Application state */
    RunTimeData runtimeData;
    
    public PreviewWindow(Lesson lesson) {
        /* Create a stage */
        Stage primaryStage = new Stage();
        
        /* Get screen size */
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        Rectangle2D canvasBounds = new Rectangle2D(0, 0, 1250.0, 703.125);
        
        /* Instantiate the scene and main Grid layout*/
        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color:red");
        Scene scene = new Scene(grid, bounds.getMaxX(), bounds.getMaxY());
        
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
        contentPane.setStyle("-fx-background-color: rgb(74, 104, 177);");
        botAnchor.setStyle("-fx-background-color: Grey;");
        gridBot.setStyle("-fx-background-color: Grey;");
        
        /* Setup the window */
        primaryStage.setTitle("Learn Easy");
        primaryStage.setScene(scene);
        
        /* Create a buttons */
        prevPageBtn = new Button();
        nextPageBtn = new Button();
      
        /* Set buttons as transparent */
        nextPageBtn.setStyle("-fx-background-color: transparent;");
        prevPageBtn.setStyle("-fx-background-color: transparent;");
        
        /* Set Tooltip */
        nextPageBtn.setTooltip(new Tooltip("Next Page"));
        prevPageBtn.setTooltip(new Tooltip("Previous Page"));
        
        /* Set button ID */
        nextPageBtn.setId("nextPageBtn");
        prevPageBtn.setId("prevPageBtn");
        
        /* Add Arrow images */
        Image arImST_R = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Arrow_ST_BOTTOM_RECT_DarkBlue_L-01.png"));
        Image arImHO_R = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Arrow_HO_BOTTOM_RECT_DarkBlue_L-01.png"));
        Image arImPRE_R = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Arrow_PRE_BOTTOM_RECT_DarkBlue_L-01.png"));
        
        Image arImST_L = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Arrow_ST_BOTTOM_RECT_DarkBlue_L-02.png"));
        Image arImHO_L = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Arrow_HO_BOTTOM_RECT_DarkBlue_L-02.png"));
        Image arImPRE_L = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Arrow_PRE_BOTTOM_RECT_DarkBlue_L-02.png"));
        
        /* Image Views*/
        final ImageView arST_L = new ImageView(arImST_L);
        final ImageView arHO_L = new ImageView(arImHO_L);
        final ImageView arPRE_L = new ImageView(arImPRE_L);
        
        final ImageView arST_R = new ImageView(arImST_R);
        final ImageView arHO_R = new ImageView(arImHO_R);
        final ImageView arPRE_R = new ImageView(arImPRE_R);
        
        /* Skin Buttons */
        nextPageBtn.setGraphic(arST_R);
        prevPageBtn.setGraphic(arST_L);
        
        /* Arrow image sizes */
        int x = 40;
        int y = 40;
        
        arST_L.setFitWidth(x);
        arST_L.setFitHeight(y);
        arHO_L.setFitWidth(x);
        arHO_L.setFitHeight(y);
        arPRE_L.setFitWidth(x);
        arPRE_L.setFitHeight(y);
        
        arST_R.setFitWidth(x);
        arST_R.setFitHeight(y);
        arHO_R.setFitWidth(x);
        arHO_R.setFitHeight(y);
        arPRE_R.setFitWidth(x);
        arPRE_R.setFitHeight(y);
        
        /* Setup the button event handling */
        
        /* Mouse Pressed */
        nextPageBtn.setOnMousePressed(new ButtonEventHandler(nextPageBtn, arPRE_R));
        prevPageBtn.setOnMousePressed(new ButtonEventHandler(prevPageBtn, arPRE_L));
        
        /* Mouse Released */  
        nextPageBtn.setOnMouseReleased(new ButtonEventHandler(nextPageBtn, arHO_R));
        prevPageBtn.setOnMouseReleased(new ButtonEventHandler(prevPageBtn, arHO_L));
        
        /* Mouse Entered */
        nextPageBtn.setOnMouseEntered(new ButtonEventHandler(nextPageBtn, arHO_R));
        prevPageBtn.setOnMouseEntered(new ButtonEventHandler(prevPageBtn, arHO_L));
        
        /* Mouse Exited */
        nextPageBtn.setOnMouseExited(new ButtonEventHandler(nextPageBtn, arST_R));
        prevPageBtn.setOnMouseExited(new ButtonEventHandler(prevPageBtn, arST_L));
        
        /* Add central LE icon */
        Image image2 = new Image("file://userfs/lt669/w2k/Desktop/Workspace/Learneasy_v2_3.png");
        ImageView LE = new ImageView(image2);
        LE.setFitWidth(50);
        LE.setFitHeight(50);
        
        /* Create Menubar */
        MenuBar menuBar = new MenuBar();
        
        Menu menuHelp = new Menu("Help");
        menuBar.setPrefWidth(10000);
        menuBar.getMenus().addAll(menuHelp);

        /* Create some text and set it up*/
        text = new Text(200, 200,"Content Pane");
        text.setFont(Font.font ("Verdana", 20));
        text.setId("text");
        
        Rectangle r = new Rectangle();
        r.setWidth(canvasBounds.getMaxX());
        r.setHeight(canvasBounds.getMaxY());
        r.setFill(Color.WHITE);
        r.setEffect(new DropShadow());

        /* Set the position of group in content pane */
        contentPane.setCenter(group);
        
        /* Add content to panes */
        hBoxTop.getChildren().addAll(menuBar/*,imageNext*/);
        group.getChildren().addAll(text,r/*,imageNext*/);
        gridBot.add(prevPageBtn, 0, 0); 
        gridBot.add(LE, 2, 0);      
        gridBot.add(nextPageBtn, 4, 0);
        gridBot.setAlignment(Pos.CENTER);
        
        
        /* Add other panes to grid layout */
        grid.add(hBoxTop, 0, 0);
        grid.add(contentPane, 0, 1);
        grid.add(gridBot, 0, 2);
        AnchorPane.setRightAnchor(nextPageBtn, 8.0);
        AnchorPane.setLeftAnchor(prevPageBtn, 8.0);
        
        runtimeData = new RunTimeData(group, canvasBounds, this, lesson);
        
        /* Show the window */
        primaryStage.show(); 
        
        /* Update the UI */
        updateUI();
    }
    
    public void openFilePressed() {
        /* Open the file */
        if(runtimeData.openLesson()) {
            /* Opened Successfully */
        } else {
            System.out.print("Parse Failed");
        }
    }
    
    public void closeFilePressed() {
        runtimeData.closeLesson();
    }
    
    public void nextPageButtonPressed() {
        runtimeData.setPageDirection(true);
        
        if(runtimeData.checkPageCompleted()) {
             runtimeData.nextPage();
        }
    }
    
    public void prevPageButtonPressed() {
        runtimeData.setPageDirection(false);
        
        if(runtimeData.checkPageCompleted()) {
            runtimeData.prevPage();  
        }    
    }
    
    public void updateUI() {
        /* 
         * If there is a lesson open enable the relevant page 
         * buttons, if not disable both.
         */
        if(runtimeData.isLessonOpen()) {
            if(!runtimeData.isNextPage()) {
                nextPageBtn.setDisable(true);
            } else {
                nextPageBtn.setDisable(false);
            }
            
            if(!runtimeData.isPrevPage()) {
                prevPageBtn.setDisable(true);
            } else {
                prevPageBtn.setDisable(false);
            }
        } else {
            nextPageBtn.setDisable(true);
            prevPageBtn.setDisable(true);
        }
    }
    
    public class ButtonEventHandler implements EventHandler<MouseEvent> {
        private Button button;
        private ImageView image;
        
        ButtonEventHandler(Button nButton, ImageView nImage) {
            this.button = nButton;
            this.image = nImage;
        }
    
        @Override
        public void handle(MouseEvent me) {        	
            button.setGraphic(image);
            
            /* Check the ID of the source button and call the relevant runtime method */
            
            if(me.getEventType() == MouseEvent.MOUSE_PRESSED) {
	            switch(button.getId()) {
	                case "nextPageBtn":
	                    nextPageButtonPressed();
	                    break;
	                case "prevPageBtn":
	                    prevPageButtonPressed();
	                    break;
	                default:
	                     /*Do Nothing */
	                    break;
	            }
            }
            
            /* Update the UI to reflect any changes to the application state */
            updateUI();
        }
    }
}

