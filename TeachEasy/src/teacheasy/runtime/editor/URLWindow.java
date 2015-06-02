/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved. 
 */
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

/**
 * Creates a window to enter a URL to load online media from.
 * 
 * @author Alistair Jewers
 * @version 1.0 20 May 2015
 */
public class URLWindow {
    /* JavaFX components */
    private Stage stage;
    private TextField textField;
    private PageObject object;
    private PropertiesPane pane;
    
    /**
     * Constructor.
     * 
     * @param object The object to change the sourcefile of.
     * @param pane The properties pane reference.
     */
    public URLWindow(PageObject object, PropertiesPane pane) {
        /* Set references */
        this.object = object;
        this.pane = pane;
        
        /* Create the stage */
        stage = new Stage();
        stage.setTitle("URL");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        
        /* Create a container for the components */
        VBox contentBox = new VBox(10);
        contentBox.setAlignment(Pos.CENTER);
        
        /* Add the field to enter the URL */
        Label http = new Label("http://");
        textField = new TextField();
        HBox textFieldRow = new HBox(10);
        textFieldRow.getChildren().addAll(http, textField);
        
        /* Add the done and cancel buttons */
        Button doneBtn = new Button("Done");
        doneBtn.setId("done");
        doneBtn.setOnAction(new ButtonHandler());
                
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setId("cancel");
        cancelBtn.setOnAction(new ButtonHandler());
        
        /* Arrange the buttons into a row */
        HBox buttonRow = new HBox(10);
        buttonRow.getChildren().addAll(doneBtn, cancelBtn);
        buttonRow.setAlignment(Pos.CENTER);
        
        /* Add the components to the stage */
        contentBox.getChildren().addAll(new Label("Enter URL:"), textFieldRow, buttonRow);
        
        /* Setup the stage */
        stage.setScene(new Scene(contentBox));
        
        /* Show the stage */
        stage.show();
    }
    
    /**
     * Update the source URL of the object.
     */
    public void updateURL() {
        /* Return if the field is empty */
        if(textField.getText() == "") {
            return;
        }
        
        /* Act based on the type of object */
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
        
        /* Update the pane */
        pane.update();
        
        /* Trigger a redraw of the page */
        pane.redraw();
    }
    
    /**
     * Handles button events.
     *  
     * @author Alistair Jewers
     */
    public class ButtonHandler implements EventHandler<ActionEvent> {
        /**
         * Override the action event handling method.
         */
        @Override
        public void handle(ActionEvent e) {
            /* Get the source */
            Button btn = (Button)e.getSource();
            
            /* Act based on ID */
            switch(btn.getId()) {
                case "done":
                    /* Update the objects URL */
                    updateURL();
                    
                    /* Close the window */
                    stage.close();
                    break;
                case "cancel":
                    /* Close the window */
                    stage.close();
                    break;
            }
        }
    }
}

