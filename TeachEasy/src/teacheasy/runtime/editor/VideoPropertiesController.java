package teacheasy.runtime.editor;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import teacheasy.data.VideoObject;
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
        
        /* Set up the property fields */
        xStartProperty = PropertiesUtil.addPropertyField("xStart", "X Start: ", xStartProperty, videoProperties, new PropertyChangedHandler());
        yStartProperty = PropertiesUtil.addPropertyField("yStart", "Y Start: ", yStartProperty, videoProperties, new PropertyChangedHandler());
        xEndProperty = PropertiesUtil.addPropertyField("xEnd", "X End: ", xEndProperty, videoProperties, new PropertyChangedHandler());
    }

    public void update(VideoObject nVideo) {
        selectedVideo = nVideo;
        
        update();
    }
    
    public void update() {
        if(selectedVideo == null) {
            xStartProperty.setText("");
            yStartProperty.setText("");
            xEndProperty.setText("");
        } else {
            xStartProperty.setText(String.valueOf(selectedVideo.getXStart()));
            yStartProperty.setText(String.valueOf(selectedVideo.getYStart()));
            xEndProperty.setText(String.valueOf(selectedVideo.getXEnd()));
        }
    }
    
    public VBox getVideoProperties() {
        return videoProperties;
    }

    public class PropertyChangedHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            if(selectedVideo == null) {
                return;
            }
            
            TextField source = (TextField)e.getSource();
            
            switch(source.getId()) {
                case "xStart":
                    selectedVideo.setXStart(PropertiesUtil.validatePosition(source.getText(), selectedVideo.getXStart()));
                    break;
                case "yStart":
                    selectedVideo.setYStart(PropertiesUtil.validatePosition(source.getText(), selectedVideo.getYStart()));
                    break;
                case "xEnd":
                    selectedVideo.setXEnd(PropertiesUtil.validatePosition(source.getText(), selectedVideo.getXEnd()));
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
                selectedVideo.setSourcefile(PropertiesUtil.validateVideo(selectedVideo.getSourcefile()));
                update();
                parent.redraw();
            }
        }
    }
}
