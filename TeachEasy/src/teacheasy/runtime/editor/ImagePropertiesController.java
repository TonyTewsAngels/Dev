package teacheasy.runtime.editor;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import teacheasy.data.ImageObject;

public class ImagePropertiesController {
    private ImageObject selectedImage;
    
    private VBox imageProperties;
    
    private Label xStartProperty;
    
    public ImagePropertiesController() {
        selectedImage = null;
        
        xStartProperty = new Label("");
        
        imageProperties = new VBox();
        
        imageProperties.getChildren().addAll(new Label("Image"), xStartProperty);
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
}
