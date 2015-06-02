/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved. 
 */
package teacheasy.runtime.editor;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import teacheasy.data.VideoObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

/**
 * Contains code relating to editor functionality for video
 * objects.
 * 
 * @author  Alistair Jewers
 * @version 1.0 Apr 21 2015
 */
public class VideoPropertiesController {
    
    /* Reference to the properties pane responsible for this controller */
    private PropertiesPane parent;
    
    /* Currently selected image */
    private VideoObject selectedVideo;
    
    /* The UI element to contain the editable properties */
    private VBox videoProperties;
    
    /* The text fields for the different properties */
    private TextField xStartProperty;
    private TextField yStartProperty;
    private TextField xEndProperty;
    
    /* The button for choosing a new file */
    private Button fileButton;
    private Button URLButton;
    
    /* The check boxes for the autoplay and loop settings */
    private CheckBox autoPlayProperty;
    private CheckBox loopProperty;
    
    /**
     * Constructor. 
     * 
     * @param nParent The properties pane responsible for this controller.
     */
    public VideoPropertiesController(PropertiesPane nParent) {
        /* Set the parent reference */
        this.parent = nParent;
        
        /* Set the selected object null */
        selectedVideo = null;
        
        /* Set up the UI container */
        videoProperties = new VBox();
        videoProperties.setSpacing(5);
        videoProperties.setPadding(new Insets(5));
        
        /* Set up the file select button */
        fileButton = PropertiesUtil.addFileField("file", "File: ", fileButton, videoProperties, new ButtonPressedHandler());
        URLButton = PropertiesUtil.addFileField("URL", "URL: ", URLButton, videoProperties, new ButtonPressedHandler());
        
        /* Set up the property fields */
        xStartProperty = PropertiesUtil.addPropertyField("xStart", "X Start: ", xStartProperty, videoProperties, new PropertyChangedHandler());
        yStartProperty = PropertiesUtil.addPropertyField("yStart", "Y Start: ", yStartProperty, videoProperties, new PropertyChangedHandler());
        xEndProperty = PropertiesUtil.addPropertyField("xEnd", "X End: ", xEndProperty, videoProperties, new PropertyChangedHandler());
        
        /* Set up the auto play and loop properties */
        autoPlayProperty = PropertiesUtil.addBooleanField("autoPlay", "Auto Play: ", autoPlayProperty, videoProperties, new BooleanPropertyChangedHandler());
        loopProperty = PropertiesUtil.addBooleanField("loop", "Loop: ", loopProperty, videoProperties, new BooleanPropertyChangedHandler());
    }

    /**
     * Update the controller with a new video object selection.
     *  
     * @param nVideo The new object to select.
     */
    public void update(VideoObject nVideo) {        
        /* Set the selection reference */
        selectedVideo = nVideo;
        
        /* Update the pane */
        update();
    }
    
    /**
     * Updates the controller fields.
     */
    public void update() {
        if(selectedVideo == null) {
            xStartProperty.setText("");
            yStartProperty.setText("");
            xEndProperty.setText("");
            autoPlayProperty.setSelected(false);
            loopProperty.setSelected(false);
        } else {
            xStartProperty.setText(String.valueOf(selectedVideo.getXStart()));
            yStartProperty.setText(String.valueOf(selectedVideo.getYStart()));
            xEndProperty.setText(String.valueOf(selectedVideo.getXEnd()));
            autoPlayProperty.setSelected(selectedVideo.isAutoPlay());
            loopProperty.setSelected(selectedVideo.isLoop());
        }
    }
    
    /**
     * Gets the video portion of the properties pane.
     */
    public VBox getVideoProperties() {
        return videoProperties;
    }

    /**
     * Handles changes to the properties fields.
     * 
     * @author Alistair Jewers
     */
    public class PropertyChangedHandler implements EventHandler<ActionEvent> {
        /**
         * Override the action event handler method.
         */
        @Override
        public void handle(ActionEvent e) {
            /* If no object is selected cancel */
            if(selectedVideo == null) {
                return;
            }
            
            /* Get the source */
            TextField source = (TextField)e.getSource();
            
            /* Check the ID and change the associated property */
            switch(source.getId()) {
                case "xStart":
                    float oldXStart = selectedVideo.getXStart();
                    float newXStart = PropertiesUtil.validatePosition(source.getText(), selectedVideo.getXStart());
                    if(newXStart != oldXStart) {
                        selectedVideo.setXStart(PropertiesUtil.validatePosition(source.getText(), selectedVideo.getXStart()));
                    }
                    break;
                case "yStart":
                    float oldYStart = selectedVideo.getYStart();
                    float newYStart = PropertiesUtil.validatePosition(source.getText(), selectedVideo.getYStart());
                    if(newYStart != oldYStart) {
                        selectedVideo.setYStart(PropertiesUtil.validatePosition(source.getText(), selectedVideo.getYStart()));
                    }
                    break;
                case "xEnd":
                    float oldXEnd = selectedVideo.getXStart();
                    float newXEnd = PropertiesUtil.validatePosition(source.getText(), selectedVideo.getXStart());
                    if(newXEnd != oldXEnd) {
                        selectedVideo.setXEnd(PropertiesUtil.validatePosition(source.getText(), selectedVideo.getXEnd()));
                    }
                    break;
                default:
                    break;
            }
            
            /* Update the pane */
            update();
            
            /* Redraw */
            parent.redraw();
        }
    }
    
    /**
     * Handles button presses.
     * 
     * @author Alistair Jewers
     */
    public class ButtonPressedHandler implements EventHandler<ActionEvent> {
        /**
         * Overrides the action event handler method.
         */
        @Override
        public void handle(ActionEvent e) {
            /* Get the source */
            Button source = (Button)e.getSource();
            
            /* Check the ID */
            if(source.getId() == "file") {
                /* Change the sourcefile */
                selectedVideo.setSourcefile(PropertiesUtil.validateFile(selectedVideo.getSourcefile(), "Videos","*.mp4", "*.flv"));
                
                /* Update the pane */
                update();
                
                /* Redraw */
                parent.redraw();
                
            } else if(source.getId() == "URL") {
                /* Launch a URL window */
                new URLWindow(selectedVideo, parent);
            }
        }
    }
    
    /**
     * Handles changes to boolean properties.
     * 
     * @author Alistair Jewers
     */
    public class BooleanPropertyChangedHandler implements EventHandler<ActionEvent> {
        /**
         * Override the action event handler method.
         */
        @Override
        public void handle(ActionEvent e) {
            /* If no object is selected cancel */
            if(selectedVideo == null) {
                return;
            }
            
            /* Get the source */
            CheckBox source = (CheckBox)e.getSource();
            
            /* Check the ID and update the relevant property */
            switch(source.getId()) {
                case "autoPlay":
                    selectedVideo.setAutoPlay(source.isSelected());
                    break;
                case "loop":
                    selectedVideo.setLoop(source.isSelected());
                    break;
                default:
                    break;
            }
            
            /* Update the pane */
            update();

            /* Redraw */
            parent.redraw();
        }
    }
}
