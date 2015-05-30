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
 * Encapsulates functionality relating to editor
 * functionality for Video objects.
 * 
 * @author Alistair Jewers
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
        
        /* Set up the property fields */
        xStartProperty = PropertiesUtil.addPropertyField("xStart", "X Start: ", xStartProperty, audioProperties, new PropertyChangedHandler());
        yStartProperty = PropertiesUtil.addPropertyField("yStart", "Y Start: ", yStartProperty, audioProperties, new PropertyChangedHandler());
        xEndProperty = PropertiesUtil.addPropertyField("xEnd", "X End: ", xEndProperty, audioProperties, new PropertyChangedHandler());
        
        /* Set up the view progress field */
        viewProgressProperty = PropertiesUtil.addBooleanField("viewProgress", "View Progress: ", viewProgressProperty, audioProperties, new BooleanPropertyChangedHandler());
        autoPlayProperty = PropertiesUtil.addBooleanField("autoPlay", "Auto Play: ", autoPlayProperty, audioProperties, new BooleanPropertyChangedHandler());
        loopProperty = PropertiesUtil.addBooleanField("loop", "Loop: ", loopProperty, audioProperties, new BooleanPropertyChangedHandler());
    }

    public void update(AudioObject nAudio) {        
        selectedAudio = nAudio;
        
        update();
    }
    
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
    
    public VBox getAudioProperties() {
        return audioProperties;
    }

    public class PropertyChangedHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            if(selectedAudio == null) {
                return;
            }
            
            TextField source = (TextField)e.getSource();
            
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
            
            update();
            parent.redraw();
        }
    }
    
    public class ButtonPressedHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            Button source = (Button)e.getSource();
            
            if(source.getId() == "file") {
                selectedAudio.setSourcefile(PropertiesUtil.validateFile(selectedAudio.getSourcefile(), "Audio: ", "*.wav", "*.mp3"));
                update();
                parent.redraw();
            }
        }
    }
    
    public class BooleanPropertyChangedHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            if(selectedAudio == null) {
                return;
            }
            
            CheckBox source = (CheckBox)e.getSource();
            
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
            
            update();
            parent.redraw();
        }
    }
}
