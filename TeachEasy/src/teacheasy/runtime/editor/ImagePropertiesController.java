package teacheasy.runtime.editor;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import teacheasy.data.ImageObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ImagePropertiesController {
    private PropertiesPane parent;
    
    private ImageObject selectedImage;
    
    private VBox imageProperties;
    
    private TextField xStartProperty;
    
    public ImagePropertiesController(PropertiesPane nParent) {
        this.parent = nParent;
        
        selectedImage = null;
        
        xStartProperty = new TextField("");
        xStartProperty.setId("xStart");
        xStartProperty.setOnAction(new PropertyChangedHandler());
        
        imageProperties = new VBox();
        
        HBox xStartRow = new HBox();
        
        xStartRow.getChildren().addAll(new Label("X Start: "), xStartProperty);
        
        imageProperties.getChildren().addAll(new Label("Image"), xStartRow);
    }
    
    public void update(ImageObject nImage) {
        selectedImage = nImage;
        
        update();
    }
    
    public void update() {
        if(selectedImage == null) {
            xStartProperty.setText("");
        } else {
            xStartProperty.setText(String.valueOf(selectedImage.getXStart()));
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
                    try {
                        float val = Float.parseFloat(source.getText());
                        selectedImage.setXStart(val);
                    } catch (NumberFormatException nfe) {
                        
                    }
                    
                    update();
                    parent.redraw();
                    break;
                default:
                    break;
            }
        }
    }
}
