package teacheasy.runtime.editor.text;

import teacheasy.runtime.editor.TextPropertiesController.TextEditorHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TextEditorWindow {
    private Stage stage;
    private Scene scene;
    private VBox box;
    private TextArea textArea;
    private Button doneBtn;
    private Button cancelBtn;
    private HBox buttonBox;
    
    public TextEditorWindow(TextEditorHandler handler, String text) {
        /* Initialise the stage */
        stage = new Stage();
        stage.setWidth(400);
        stage.setHeight(250);
        stage.initModality(Modality.APPLICATION_MODAL);
        
        /* Initialise the components */
        box = new VBox();
        textArea = new TextArea();
        buttonBox = new HBox();
        doneBtn = new Button("Done");
        cancelBtn = new Button("Cancel");
        
        /* Set up the button row */
        buttonBox.getChildren().addAll(doneBtn, cancelBtn);
        buttonBox.setAlignment(Pos.CENTER);
        
        /* Set up the content */
        box.getChildren().addAll(textArea, buttonBox);
        box.setAlignment(Pos.CENTER);
        
        /* Fill the text area */
        textArea.setText(text);
        textArea.setWrapText(true);
        
        /* Set the handlers references  */
        handler.setup(textArea, stage);
        
        /* Set the buton actions */
        doneBtn.setOnAction(handler);
        cancelBtn.setOnAction(new CancelButtonHandler());
        
        /* Create the scene and window */
        scene = new Scene(box);
        stage.setScene(scene);
        stage.show();
    }
    
    public class CancelButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            stage.close();
        }
    }
}
