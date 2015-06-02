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
import teacheasy.data.AudioObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

/**
 * Contains code relating to editor functionality for audio objects.
 * 
 * @author  Alistair Jewers
 * @version 1.0 Apr 21 2015
 */
public class AudioPropertiesController {
    /* Reference to the properties pane responsible for this controller */
    private PropertiesPane parent;
    
    /* Currently selected image */
    private AudioObject selectedAudio;
    
    /* The UI element to contain the editable properties */
    private VBox audioProperties;
    
    /* The text fields for the different properties */
    private TextField xStartProperty;
    private TextField yStartProperty;
    private TextField xEndProperty;
    
    /* The button for choosing a new file */
    private Button fileButton;
    private Button URLButton;
    
    /* The check box for the view progress property */
    private CheckBox viewProgressProperty;
    private CheckBox autoPlayProperty;
    private CheckBox loopProperty;
    
    /**
     * Constructor. 
     * 
     * @param nParent The properties pane responsible for this controller.
     */
    public AudioPropertiesController(PropertiesPane nParent) {
        /* Set the parent reference */
        this.parent = nParent;
        
        /* Set the selected object null */
        selectedAudio = null;
        
        /* Set up the UI container */
        audioProperties = new VBox();
        audioProperties.setSpacing(5);
        audioProperties.setPadding(new Insets(5));
        
        /* Set up the file select button */
        fileButton = PropertiesUtil.addFileField("file", "File: ", fileButton, audioProperties, new ButtonPressedHandler());
        URLButton = PropertiesUtil.addFileField("URL", "URL: ", URLButton, audioProperties, new ButtonPressedHandler());
        
        /* Set up the property fields */
        xStartProperty = PropertiesUtil.addPropertyField("xStart", "X Start: ", xStartProperty, audioProperties, new PropertyChangedHandler());
        yStartProperty = PropertiesUtil.addPropertyField("yStart", "Y Start: ", yStartProperty, audioProperties, new PropertyChangedHandler());
        xEndProperty = PropertiesUtil.addPropertyField("xEnd", "X End: ", xEndProperty, audioProperties, new PropertyChangedHandler());
        
        /* Set up the view progress field */
        viewProgressProperty = PropertiesUtil.addBooleanField("viewProgress", "View Progress: ", viewProgressProperty, audioProperties, new BooleanPropertyChangedHandler());
        autoPlayProperty = PropertiesUtil.addBooleanField("autoPlay", "Auto Play: ", autoPlayProperty, audioProperties, new BooleanPropertyChangedHandler());
        loopProperty = PropertiesUtil.addBooleanField("loop", "Loop: ", loopProperty, audioProperties, new BooleanPropertyChangedHandler());
    }

    /**
     * Update the controller with a new object reference.
     * 
     * @param nAudio The new object to reference.
     */
    public void update(AudioObject nAudio) {
        /* Set the new object */
        selectedAudio = nAudio;
        
        /* Update the properties pane */
        update();
    }
    
    /**
     * Updates all the fields in the pane.
     */
    public void update() {
        if(selectedAudio == null) {
            xStartProperty.setText("");
            yStartProperty.setText("");
            xEndProperty.setText("");
            viewProgressProperty.setSelected(false);
            autoPlayProperty.setSelected(false);
            loopProperty.setSelected(false);
        } else {
            xStartProperty.setText(String.valueOf(selectedAudio.getXStart()));
            yStartProperty.setText(String.valueOf(selectedAudio.getYStart()));
            xEndProperty.setText(String.valueOf(selectedAudio.getXEnd()));
            viewProgressProperty.setSelected(selectedAudio.isViewProgress());
            autoPlayProperty.setSelected(selectedAudio.isAutoPlay());
            loopProperty.setSelected(selectedAudio.isLoop());
        }
    }
    
    /**
     * Gets the audio portion of the properties pane.
     */
    public VBox getAudioProperties() {
        return audioProperties;
    }

    /**
     * Handles changes to the property fields.
     * 
     * @author Alistair Jewers
     */
    public class PropertyChangedHandler implements EventHandler<ActionEvent> {
        /**
         * Override the method for handling action events.
         */
        @Override
        public void handle(ActionEvent e) {
            /* If there is no audio object selected cancel */
            if(selectedAudio == null) {
                return;
            }
            
            /* Get the source */
            TextField source = (TextField)e.getSource();
            
            /* Check the ID and update the relevant property */
            switch(source.getId()) {
                case "xStart":
                    selectedAudio.setXStart(PropertiesUtil.validatePosition(source.getText(), selectedAudio.getXStart()));
                    break;
                case "yStart":
                    selectedAudio.setYStart(PropertiesUtil.validatePosition(source.getText(), selectedAudio.getYStart()));
                    break;
                case "xEnd":
                    selectedAudio.setXEnd(PropertiesUtil.validatePosition(source.getText(), selectedAudio.getXEnd()));
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
         * Override the method for handling action events.
         */
        @Override
        public void handle(ActionEvent e) {
            /* Get the source */
            Button source = (Button)e.getSource();
            
            /* Check the ID */
            if(source.getId() == "file") {
                /* Get new sourcefile */
                selectedAudio.setSourcefile(PropertiesUtil.validateFile(selectedAudio.getSourcefile(), "Audio: ", "*.wav", "*.mp3"));
                
                /* Update the pane */
                update();
                
                /* Redraw */
                parent.redraw();
            } else if(source.getId() == "URL") {
                /* Launch a URL window */
                new URLWindow(selectedAudio, parent);
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
         * Override the action event handling method.
         */
        @Override
        public void handle(ActionEvent e) {
            /* If no audio is selected cancel */
            if(selectedAudio == null) {
                return;
            }
            
            /* Get the source */
            CheckBox source = (CheckBox)e.getSource();
            
            /* Check the ID and update the relevant property */
            switch(source.getId()) {
                case "viewProgress":
                    selectedAudio.setViewProgress(source.isSelected());
                    break;
                case "autoPlay":
                    selectedAudio.setAutoPlay(source.isSelected());
                    break;
                case "loop":
                    selectedAudio.setLoop(source.isSelected());
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
