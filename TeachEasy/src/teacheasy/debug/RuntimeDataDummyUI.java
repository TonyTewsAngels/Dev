package teacheasy.debug;

import java.io.File;
import java.util.ArrayList;

import teacheasy.data.Lesson;
import teacheasy.data.Page;
import teacheasy.runtime.RunTimeData;
import teacheasy.xml.XMLHandler;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.stage.FileChooser.ExtensionFilter;

public class RuntimeDataDummyUI extends Application {
    /* Current Lesson */
    Lesson lesson;
    
    /* Runtime Data */
    RunTimeData runTimeData;
    
    /* XML Handler */
    XMLHandler xmlHandler;
    
    /* GUI Objects */
    TextArea textArea;
    FileChooser fileChooser;
    Button nextPageButton;
    Button prevPageButton;
    
    public RuntimeDataDummyUI() {
        lesson = new Lesson();
        runTimeData = new RunTimeData(0);
        xmlHandler = new XMLHandler();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        /* Set the stage */
        primaryStage.setTitle("Runtime");
        
        /* Set up the grid */
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setGridLinesVisible(true);
        
        /* Set the scene */
        Scene scene = new Scene(grid, 800, 450);
        primaryStage.setScene(scene);
        
        /* Construct the menu */
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        Menu menuPreview = new Menu("Preview");
        Menu menuHelp = new Menu("Help");
        
        /* Add an open button to the file menu */
        MenuItem menuItemOpen = new MenuItem("Open");
        menuFile.getItems().add(menuItemOpen);
        menuItemOpen.setId("FileOpen");
        menuItemOpen.setOnAction(new MenuEventHandler());
        
        /* Add a close button to the file menu */
        MenuItem menuItemClose = new MenuItem("Close");
        menuFile.getItems().add(menuItemClose);
        menuItemClose.setId("FileClose");
        menuItemClose.setOnAction(new MenuEventHandler());
        
        menuBar.getMenus().addAll(menuFile, menuEdit, menuPreview, menuHelp);
        
        /* Add the menu to the grid */
        grid.add(menuBar, 0, 0);
        
        /* Create the text area */
        textArea = new TextArea();
        textArea.setEditable(false);
        
        /* Add the text area to the grid */
        grid.add(textArea, 0, 1);
        
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
        
        /* Show the stage */
        primaryStage.show();
        
        /* Redraw the text */
        redraw();
    }
    
    public void redraw() {
        /* Clear the text */
        textArea.clear();
        
        /* Check if a lesson is open */
        if(runTimeData.isLessonOpen()) {
            /* Write the info for the lesson */
            textArea.appendText("Lesson: " + lesson.lessonInfo.getLessonName() + "\n");
            textArea.appendText("Current Page: " + (runTimeData.getPage() + 1) + "\n");
            textArea.appendText("Page Count: " + runTimeData.getPageCount() + "\n");
            
            /* Write the info for the current page */
            Page page = lesson.pages.get(runTimeData.getPage());
            
            for(int i = 0; i < page.pageObjects.size(); i++) {
                textArea.appendText(page.pageObjects.get(i).getType() + ", ");
            }
            
            textArea.deleteText(textArea.getLength() - 2, textArea.getLength());
            textArea.appendText(".\n");
        } else {
            /* No lesson is open */
            textArea.appendText("No Lesson Currently Open. \n");
        }
        
        /* 
         * If there is a lesson open enable the relevant page 
         * buttons, if not disable both.
         */
        if(runTimeData.isLessonOpen()) {
            if(!runTimeData.isNextPage()) {
                nextPageButton.setDisable(true);
            } else {
                nextPageButton.setDisable(false);
            }
            
            if(!runTimeData.isPrevPage()) {
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
        runTimeData.nextPage();
        redraw();
    }
    
    /** Previous page button functionality */
    public void prevPageButtonPressed() {
        runTimeData.prevPage();
        redraw();
    }
    
    /** Open file menu option functionality */
    public void fileOpenPressed() {
        /* Create a file chooser */
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new ExtensionFilter("XML Files", "*.xml"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        
        /* Get the file to open */
        File file = fileChooser.showOpenDialog(new Stage());
        
        /* Check that the file is not null */
        if(file == null) {
            return;
        }
        
        /* Parse the file */
        ArrayList<String> errorList = xmlHandler.parseXML(file.getAbsolutePath());
        
        /* Check for errors */
        if(errorList.size() > 0) {
            for(int i = 0; i < errorList.size(); i++) {
                textArea.appendText(errorList.get(i) + "\n");
            }
            
            /* If errors are found do not load the lesson */
            return;
        }
        
        /* Get the lesson */
        lesson = xmlHandler.getLesson();
        runTimeData.setPageCount(lesson.pages.size());
        runTimeData.setLessonOpen(true);
        
        /* Redraw the text */
        redraw();
    }
    
    /** Close file menu option functionality */
    public void fileClosePressed() {
        /* Set the lesson open flag to false */
        runTimeData.setLessonOpen(false);
        
        /* Set the lesson to an empty lesson */
        lesson = new Lesson();
        
        /* Re-draw */
        redraw();
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
            MenuItem menuItem = (MenuItem) e.getSource();
            
            if(menuItem.getId().equals("FileOpen")) {
                fileOpenPressed();
            } else if(menuItem.getId().equals("FileClose")) {
                fileClosePressed();
            } 
        }
    } 
}
