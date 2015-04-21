package teacheasy.runtime.editor;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import teacheasy.data.ImageObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

/**
 * Encapsulates functionality relating to editor
 * functionality for Image objects.
 * 
 * @author Alistair Jewers
 * @version 1.0 Apr 21 2015
 */
public class ImagePropertiesController {
    /* Reference to the properties pane responsible for this controller */
    private PropertiesPane parent;
    
    /* Currently selected image */
    private ImageObject selectedImage;
    
    /* The UI element to contain the editable properties */
    private VBox imageProperties;
    
    /* The text fields for the different properties */
    private TextField xStartProperty;
    private TextField yStartProperty;
    private TextField xEndProperty;
    private TextField yEndProperty;
    private TextField rotationProperty;
    
    /* The button for choosing a new file */
    private Button fileButton;
    
    /**
     * Constructor. 
     * 
     * @param nParent The properties pane responsible for this controller.
     */
    public ImagePropertiesController(PropertiesPane nParent) {
        /* Set the parent reference */
        this.parent = nParent;
        
        /* Set the selected object null */
        selectedImage = null;
        
        /* Set up the UI container */
        imageProperties = new VBox();
        imageProperties.setSpacing(5);
        imageProperties.setPadding(new Insets(5));
        
        /* Set up the file select button */
        fileButton = PropertiesUtil.addFileField("file", "File: ", fileButton, imageProperties, new ButtonPressedHandler());
        
        /* Set up the property fields */
        xStartProperty = PropertiesUtil.addPropertyField("xStart", "X Start: ", xStartProperty, imageProperties, new PropertyChangedHandler());
        yStartProperty = PropertiesUtil.addPropertyField("yStart", "Y Start: ", yStartProperty, imageProperties, new PropertyChangedHandler());
        xEndProperty = PropertiesUtil.addPropertyField("xEnd", "X End: ", xEndProperty, imageProperties, new PropertyChangedHandler());
        yEndProperty = PropertiesUtil.addPropertyField("yEnd", "Y End: ", yEndProperty, imageProperties, new PropertyChangedHandler());
        rotationProperty = PropertiesUtil.addPropertyField("rotation", "Rotation: ", rotationProperty, imageProperties, new PropertyChangedHandler());
    }

    public void update(ImageObject nImage) {
        selectedImage = nImage;
        
        update();
    }
    
    public void update() {
        if(selectedImage == null) {
            xStartProperty.setText("");
            yStartProperty.setText("");
            xEndProperty.setText("");
            yEndProperty.setText("");
            rotationProperty.setText("");
        } else {
            xStartProperty.setText(String.valueOf(selectedImage.getXStart()));
            yStartProperty.setText(String.valueOf(selectedImage.getYStart()));
            xEndProperty.setText(String.valueOf(selectedImage.getXEnd()));
            yEndProperty.setText(String.valueOf(selectedImage.getYEnd()));
            rotationProperty.setText(String.valueOf(selectedImage.getRotation()));
        }
    }
    
    public VBox getImageProperties() {
        return imageProperties;
    }

    public class PropertyChangedHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            if(selectedImage == null) {
                return;
            }
            
            TextField source = (TextField)e.getSource();
            
            switch(source.getId()) {
                case "xStart":
                    selectedImage.setXStart(PropertiesUtil.validatePosition(source.getText(), selectedImage.getXStart()));
                    break;
                case "yStart":
                    selectedImage.setYStart(PropertiesUtil.validatePosition(source.getText(), selectedImage.getYStart()));
                    break;
                case "xEnd":
                    selectedImage.setXEnd(PropertiesUtil.validatePosition(source.getText(), selectedImage.getXEnd()));
                    break;
                case "yEnd":
                    selectedImage.setYEnd(PropertiesUtil.validatePosition(source.getText(), selectedImage.getYEnd()));
                    break;
                case "rotation":
                    selectedImage.setRotation(PropertiesUtil.validateRotation(source.getText(), selectedImage.getRotation()));
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
                selectedImage.setSourcefile(PropertiesUtil.validateImage(selectedImage.getSourcefile()));
                update();
                parent.redraw();
            }
        }
    }
}
