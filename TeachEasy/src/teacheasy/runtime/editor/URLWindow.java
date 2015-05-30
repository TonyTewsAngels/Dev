package teacheasy.runtime.editor;

import teacheasy.data.AudioObject;
import teacheasy.data.ImageObject;
import teacheasy.data.PageObject;
import teacheasy.data.VideoObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class URLWindow {
    private Stage stage;
    private TextField textField;
    private PageObject object;
    private PropertiesPane pane;
    
    public URLWindow(PageObject object, PropertiesPane pane) {
        this.object = object;
        this.pane = pane;
        
        stage = new Stage();
        stage.setTitle("URL");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        
        VBox contentBox = new VBox(10);
        contentBox.setAlignment(Pos.CENTER);
        
        Label http = new Label("http://");
        textField = new TextField();
        HBox textFieldRow = new HBox(10);
        textFieldRow.getChildren().addAll(http, textField);
        
        Button doneBtn = new Button("Done");
        doneBtn.setId("done");
        doneBtn.setOnAction(new ButtonHandler());
                
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setId("cancel");
        cancelBtn.setOnAction(new ButtonHandler());
        
        HBox buttonRow = new HBox(10);
        buttonRow.getChildren().addAll(doneBtn, cancelBtn);
        buttonRow.setAlignment(Pos.CENTER);
        
        contentBox.getChildren().addAll(new Label("Enter URL:"), textFieldRow, buttonRow);
        
        stage.setScene(new Scene(contentBox));
        
        stage.show();
    }
    
    public void updateURL() {
        if(textField.getText() == "") {
            return;
        }
        
        switch(object.getType()) {
            case AUDIO:
                AudioObject audio = (AudioObject)object;
                audio.setSourcefile("http://" + textField.getText());
                break;
            case IMAGE:
                ImageObject image = (ImageObject)object;
                image.setSourcefile("http://" + textField.getText());
                break;
            case VIDEO:
                VideoObject video = (VideoObject)object;
                video.setSourcefile("http://" + textField.getText());
                break;
            default:
                break;
        }
        
        pane.update();
        pane.redraw();
    }
    
    public class ButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            Button btn = (Button)e.getSource();
            
            switch(btn.getId()) {
                case "done":
                    updateURL();
                    stage.close();
                    break;
                case "cancel":
                    stage.close();
                    break;
            }
        }
    }
}

