package teacheasy.debug;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.*;

public class RuntimeDataDummyUI extends Application {
    TextArea textArea;

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
        Button nextPageButton = new Button("Next Page");
        Button prevPageButton = new Button("Prev Page");
        
        buttonRow.getChildren().addAll(prevPageButton, nextPageButton);
       
        /* Add the page buttons to the grid */
        grid.add(buttonRow, 0, 2);
        
        primaryStage.show();
    }
    
    public static void main(String args[]) {
        launch();
    }
}
