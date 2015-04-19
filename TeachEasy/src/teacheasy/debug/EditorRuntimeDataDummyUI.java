package teacheasy.debug;

import teacheasy.runtime.EditorRunTimeData;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class EditorRuntimeDataDummyUI extends Application { 
    /* Runtime Data */
    private EditorRunTimeData editorRunTimeData;
    
    /* GUI Objects */
    private Group group;
    private Button nextPageButton;
    private Button prevPageButton;
    
    /* Screen size */
    private Rectangle2D bounds;
    
    /** Constructor method */
    public EditorRuntimeDataDummyUI() {
        // Do Nothing
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        /* Get the screen size */
        Screen screen = Screen.getPrimary();
        bounds = screen.getVisualBounds();
        
        /* Set the stage */
        primaryStage.setTitle("Runtime");
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getMaxX());
        primaryStage.setHeight(bounds.getMaxY());
        primaryStage.setResizable(true);
        
        /* Set up the grid */
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setGridLinesVisible(true);
        
        /* Set the scene */
        Scene scene = new Scene(grid, bounds.getMaxX(), bounds.getMaxY());
        primaryStage.setScene(scene);
        
        /* Construct the menu */
        MenuBar menuBar = new MenuBar();
        menuBar.setPrefWidth(bounds.getMaxX());
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        Menu menuPreview = new Menu("Preview");
        Menu menuHelp = new Menu("Help");
        
        /* Add an open button to the file menu */
        MenuItem menuItemOpen = new MenuItem("Open");
        menuItemOpen.setId("FileOpen");
        menuItemOpen.setOnAction(new MenuEventHandler());
        
        /* Add a close button to the file menu */
        MenuItem menuItemClose = new MenuItem("Close");
        menuItemClose.setId("FileClose");
        menuItemClose.setOnAction(new MenuEventHandler());
        
        /* Add a new button to the file menu */
        MenuItem menuItemNew = new MenuItem("New");
        menuItemNew.setId("FileNew");
        menuItemNew.setOnAction(new MenuEventHandler());
        
        menuFile.getItems().addAll(menuItemNew, menuItemClose, menuItemOpen);
        menuBar.getMenus().addAll(menuFile, menuEdit, menuPreview, menuHelp);
        
        /* Add the menu to the grid */
        grid.add(menuBar, 0, 0);
        
        /* Create the text area */
        group = new Group();
        
        /* Add the text area to the grid */
        grid.add(group, 0, 1);
        
        /* Create page buttons */
        HBox buttonRow = new HBox();
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.setSpacing(20);
        nextPageButton = new Button("Next Page");
        prevPageButton = new Button("Prev Page");
        
        nextPageButton.setId("NextButton");
        prevPageButton.setId("PrevButton");
        
        nextPageButton.setOnAction(new ButtonEventHandler());
        prevPageButton.setOnAction(new ButtonEventHandler());

        buttonRow.getChildren().addAll(prevPageButton, nextPageButton);
       
        /* Add the page buttons to the grid */
        grid.add(buttonRow, 0, 2);
        
        /* Instantiate the runtime data */
        editorRunTimeData = new EditorRunTimeData(group, new Rectangle2D(0, 0, bounds.getMaxX()/2, bounds.getMaxY()/2));
        
        /* Show the stage */
        primaryStage.show();
        
        /* Redraw the window */
        updateUI();
    }
    
    public void updateUI() {        
        /* 
         * If there is a lesson open enable the relevant page 
         * buttons, if not disable both.
         */
        if(editorRunTimeData.isLessonOpen()) {
            if(!editorRunTimeData.isNextPage()) {
                nextPageButton.setDisable(true);
            } else {
                nextPageButton.setDisable(false);
            }
            
            if(!editorRunTimeData.isPrevPage()) {
                prevPageButton.setDisable(true);
            } else {
                prevPageButton.setDisable(false);
            }
        } else {
            nextPageButton.setDisable(true);
            prevPageButton.setDisable(true);
        }
    }
    
    /** Next page button functionality */
    public void nextPageButtonPressed() {
        editorRunTimeData.nextPage();
        updateUI();
    }
    
    /** Previous page button functionality */
    public void prevPageButtonPressed() {
        editorRunTimeData.prevPage();
        updateUI();
    }
    
    /** File->Open menu option functionality */
    public void fileOpenPressed() {
        /* Open the file */
        if(editorRunTimeData.openLesson()) {
            /* Opened Successfully */
        } else {
            System.out.print("Parse Failed");
        }
        
        /* Redraw the window */
        updateUI();
    }
    
    /** File->Close menu option functionality */
    public void fileClosePressed() {
        /* Close the current lesson */
        editorRunTimeData.closeLesson();
        
        /* Re-draw the window */
        updateUI();
    }
    
    /** File->New menu option functionality */
    public void fileNewPressed() {
        /* Close the current lesson */
        editorRunTimeData.newLesson();
        
        /* Re-draw the window */
        updateUI();
    }
    
    public static void main(String args[]) {
        new RuntimeDataDummyUI();
        
        launch();
    }
    
    /**
     * Button Event Handler Class
     */
    public class ButtonEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            /* Cast the source of the event to a button */
            Button button = (Button)e.getSource();
            
            /* Act based on the ID of the button */
            if(button.getId().equals("NextButton")) {
                nextPageButtonPressed();
            } else if(button.getId().equals("PrevButton")) {
                prevPageButtonPressed();
            }
        }
    }
    
    /**
     * Menu Event Handler Class
     */
    public class MenuEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            /* Cast the source of the event to a menu item */
            MenuItem menuItem = (MenuItem) e.getSource();
            
            /* Act based on the ID of the menu item */
            if(menuItem.getId().equals("FileOpen")) {
                fileOpenPressed();
            } else if(menuItem.getId().equals("FileClose")) {
                fileClosePressed();
            }  else if(menuItem.getId().equals("FileNew")) {
                fileNewPressed();
            } 
        }
    } 
}

