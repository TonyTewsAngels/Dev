package teacheasy.debug;

import teacheasy.data.Lesson;
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

public class RuntimeDataDummyUI extends Application {
    /* Current Lesson */
    Lesson lesson;
    
    /* Runtime Data */
    RunTimeData runTimeData;
    
    /* XML Handler */
    XMLHandler xmlHandler;
    
    /* GUI Objects */
    TextArea textArea;
    
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
        menuFile.getItems().add(new MenuItem("Open"));
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
        Button nextPageButton = new Button("Next Page");
        Button prevPageButton = new Button("Prev Page");
        
        nextPageButton.setId("NextButton");
        prevPageButton.setId("PrevButton");
        
        nextPageButton.setOnAction(new buttonEventHandler());
        prevPageButton.setOnAction(new buttonEventHandler());

        buttonRow.getChildren().addAll(prevPageButton, nextPageButton);
       
        /* Add the page buttons to the grid */
        grid.add(buttonRow, 0, 2);
        
        /* Show the stage */
        primaryStage.show();
        
        /* Redraw the text */
        redrawText();
    }
    
    public void redrawText() {
        /* Clear the text */
        textArea.clear();
        
        /* Check if a lesson is open */
        if(runTimeData.isLessonOpen()) {
            textArea.appendText("Lesson: " + lesson.lessonInfo.getLessonName() + "\n");
            textArea.appendText("Current Page: " + runTimeData.getPage() + "\n");
            textArea.appendText("Page Count: " + runTimeData.getPageCount() + "\n");
        } else {
            /* No lesson is open */
            textArea.appendText("No Lesson Currently Open. \n");
        }
    }
    
    public void nextPageButtonPressed() {
        System.out.println("Next");
    }
    
    public void prevPageButtonPressed() {
        System.out.println("Prev");
    }
    
    public static void main(String args[]) {
        new RuntimeDataDummyUI();
        
        launch();
    }
    
    /**
     * Button Event Handler Class
     */
    public class buttonEventHandler implements EventHandler<ActionEvent> {
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
}
