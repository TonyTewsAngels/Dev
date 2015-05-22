package teacheasy.debug;

import teacheasy.data.PageObject.PageObjectType;
import teacheasy.runtime.EditorRunTimeData;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.*;

public class EditorRuntimeDataDummyUI extends Application { 
    /* Runtime Data */
    private EditorRunTimeData editorRunTimeData;
    
    /* GUI Objects */
    private Group group;
    private Label text;
    private Button nextPageButton;
    private Button prevPageButton;
    private VBox propertiesPane;
    
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
        scene.setOnKeyPressed(new KeyHandler());
        scene.addEventFilter(MouseEvent.ANY, new MouseEventHandler());
        
        /* Construct the menu */
        MenuBar menuBar = new MenuBar();
        menuBar.setPrefWidth(bounds.getMaxX()/2);
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        Menu menuPreview = new Menu("Preview");
        Menu menuHelp = new Menu("Help");
        Menu menuDebug = new Menu("Debug");
        
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
        
        /* Add a save button to the file menu */
        MenuItem menuItemSave = new MenuItem("Save");
        menuItemSave.setId("FileSave");
        menuItemSave.setOnAction(new MenuEventHandler());
        
        /* Add a new page button to the edit menu */
        MenuItem menuItemNewPage = new MenuItem("New Page");
        menuItemNewPage.setId("EditNewPage");
        menuItemNewPage.setOnAction(new MenuEventHandler());
        
        /* Add a remove page button to the edit menu */
        MenuItem menuItemRemovePage = new MenuItem("Remove Page");
        menuItemRemovePage.setId("EditRemovePage");
        menuItemRemovePage.setOnAction(new MenuEventHandler());
        
        /* Add a remove object button to the edit menu */
        MenuItem menuItemRemoveObject = new MenuItem("Remove Object");
        menuItemRemoveObject.setId("EditRemoveObject");
        menuItemRemoveObject.setOnAction(new MenuEventHandler());
        
        Menu menuItemNewObject = new Menu("Add...");
        
        MenuItem menuItemNewImage = new MenuItem("Add New Image");
        menuItemNewImage.setId("EditNewImage");
        menuItemNewImage.setOnAction(new MenuEventHandler());
        menuItemNewObject.getItems().add(menuItemNewImage);
        
        MenuItem menuItemNewVideo = new MenuItem("Add New Video");
        menuItemNewVideo.setId("EditNewVideo");
        menuItemNewVideo.setOnAction(new MenuEventHandler());
        menuItemNewObject.getItems().add(menuItemNewVideo);
        
        MenuItem menuItemNewAudio = new MenuItem("Add New Audio");
        menuItemNewAudio.setId("EditNewAudio");
        menuItemNewAudio.setOnAction(new MenuEventHandler());
        menuItemNewObject.getItems().add(menuItemNewAudio);
        
        MenuItem menuItemNewText = new MenuItem("Add New Text Box");
        menuItemNewText.setId("EditNewText");
        menuItemNewText.setOnAction(new MenuEventHandler());
        menuItemNewObject.getItems().add(menuItemNewText);
        
        MenuItem menuItemNewGraphic = new MenuItem("Add New Graphic");
        menuItemNewGraphic.setId("EditNewGraphic");
        menuItemNewGraphic.setOnAction(new MenuEventHandler());
        menuItemNewObject.getItems().add(menuItemNewGraphic);
        
        MenuItem menuItemNewMultiChoice = new MenuItem("Add New Multiple Choice Question");
        menuItemNewMultiChoice.setId("EditNewMultiChoice");
        menuItemNewMultiChoice.setOnAction(new MenuEventHandler());
        menuItemNewObject.getItems().add(menuItemNewMultiChoice);
        
        MenuItem menuItemNewAnswerBox = new MenuItem("Add New Answer Box");
        menuItemNewAnswerBox.setId("EditNewAnswerBox");
        menuItemNewAnswerBox.setOnAction(new MenuEventHandler());
        menuItemNewObject.getItems().add(menuItemNewAnswerBox);
        
        /* Add a next page object button to the debug menu */
        MenuItem menuItemNextObject = new MenuItem("Next Object");
        menuItemNextObject.setId("DebugNextObject");
        menuItemNextObject.setOnAction(new MenuEventHandler());
        
        menuFile.getItems().addAll(menuItemNew, menuItemOpen, menuItemSave, menuItemClose);
        menuEdit.getItems().addAll(menuItemNewPage, menuItemRemovePage, menuItemNewObject, menuItemRemoveObject);
        menuDebug.getItems().addAll(menuItemNextObject);
        menuBar.getMenus().addAll(menuFile, menuEdit, menuPreview, menuHelp, menuDebug);
        
        /* Add the menu to the grid */
        grid.add(menuBar, 0, 0);
        
        /* Create the text area */
        group = new Group();
        
        /* Add the text area to the grid */
        grid.add(group, 0, 1);
        
        propertiesPane = new VBox();
        grid.add(propertiesPane, 1, 1);
        
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
        
        text = new Label("");
        grid.add(text, 0, 3);
        
        /* Instantiate the runtime data */
        editorRunTimeData = new EditorRunTimeData(group, new Rectangle2D(0, 0, bounds.getMaxX()/2, bounds.getMaxY()/2), propertiesPane);
        
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
            text.setText((editorRunTimeData.getCurrentPageNumber()+1) + " / " + editorRunTimeData.getPageCount());
            
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
            text.setText("No lesson open");
            
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
    
    /** File->Save menu option functionality */
    public void fileSavePressed() {
        /* Close the current lesson */
        editorRunTimeData.saveLesson();
        
        /* Re-draw the window */
        updateUI();
    }
    
    /** Mouse pressed */
    public void mousePressed(double x, double y) {       
        Bounds bounds = group.localToScene(group.getBoundsInLocal());
        
        if(!(x > bounds.getMaxX() || x < bounds.getMinX() || y > bounds.getMaxY() || y < bounds.getMinY())) {
            editorRunTimeData.mousePressed(x, y);
        } else {
            /** Click is not in the group */
        }
    }
    
    /** Mouse released */
    public void mouseReleased(double x, double y) {       
        editorRunTimeData.mouseReleased(x, y);
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
            } else if(menuItem.getId().equals("FileNew")) {
                fileNewPressed();
            } else if(menuItem.getId().equals("FileSave")) {
                fileSavePressed();
            } else if (menuItem.getId().equals("EditNewPage")) {
                editorRunTimeData.newPage();
                updateUI();
            } else if (menuItem.getId().equals("EditRemovePage")) {
                editorRunTimeData.removePage();
                updateUI();
            } else if (menuItem.getId().equals("EditNewImage")) {
                editorRunTimeData.newObject(PageObjectType.IMAGE);
                updateUI();
            } else if (menuItem.getId().equals("EditNewVideo")) {
                editorRunTimeData.newObject(PageObjectType.VIDEO);
                updateUI();
            } else if (menuItem.getId().equals("EditNewAudio")) {
                editorRunTimeData.newObject(PageObjectType.AUDIO);
                updateUI();
            } else if (menuItem.getId().equals("EditNewText")) {
                editorRunTimeData.newObject(PageObjectType.TEXT);
                updateUI();
            } else if (menuItem.getId().equals("EditNewMultiChoice")) {
                editorRunTimeData.newObject(PageObjectType.MULTIPLE_CHOICE);
                updateUI();
            } else if (menuItem.getId().equals("EditNewAnswerBox")) {
                editorRunTimeData.newObject(PageObjectType.ANSWER_BOX);
                updateUI();
            } else if (menuItem.getId().equals("EditNewGraphic")) {
                editorRunTimeData.newObject(PageObjectType.GRAPHIC);
                updateUI();
            } else if (menuItem.getId().equals("EditRemovePage")) {
                editorRunTimeData.removePage();
                updateUI();
            } else if (menuItem.getId().equals("EditRemoveObject")) {
                editorRunTimeData.removeObject();
                updateUI();
            } else if(menuItem.getId().equals("DebugNextObject")) {
                editorRunTimeData.nextObject();
                updateUI();
            }
        }
    }
    
    public class KeyHandler implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent ke) {            
            if(ke.getCode() == KeyCode.DELETE) {
                editorRunTimeData.removeObject();
            } else if(ke.getCode() == KeyCode.O && ke.isControlDown()) {
                editorRunTimeData.openLesson();
            } else if(ke.getCode() == KeyCode.N) {
                editorRunTimeData.nextObject();
            }
        }
    }
    
    public class MouseEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent me) {
            if(me.getEventType() == MouseEvent.MOUSE_PRESSED) {
                mousePressed(me.getSceneX(), me.getSceneY());
            } else if(me.getEventType() == MouseEvent.MOUSE_RELEASED) {
                mouseReleased(me.getSceneX(), me.getSceneY());
            }
        }
    }
}

