package teacheasy.runtime.editor;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import teacheasy.data.TextObject;
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
public class TextPropertiesController {
    /* Reference to the properties pane responsible for this controller */
    private PropertiesPane parent;
    
    /* Currently selected image */
    private TextObject selectedText;
    
    /* The UI element to contain the editable properties */
    private VBox textProperties;
    
    /* The text fields for the different properties */
    private TextField xStartProperty;
    private TextField yStartProperty;
    private TextField xEndProperty;
    private TextField yEndProperty;
    private TextField fontSizeProperty;
    
    /* The button for choosing a new file */
    private Button fileButton;
    
    /**
     * Constructor. 
     * 
     * @param nParent The properties pane responsible for this controller.
     */
    public TextPropertiesController(PropertiesPane nParent) {
        /* Set the parent reference */
        this.parent = nParent;
        
        /* Set the selected object null */
        selectedText = null;
        
        /* Set up the UI container */
        textProperties = new VBox();
        textProperties.setSpacing(5);
        textProperties.setPadding(new Insets(5));
        
        /* Set up the file select button */
        fileButton = PropertiesUtil.addFileField("file", "File: ", fileButton, textProperties, new ButtonPressedHandler());
        
        /* Set up the property fields */
        xStartProperty = PropertiesUtil.addPropertyField("xStart", "X Start: ", xStartProperty, textProperties, new PropertyChangedHandler());
        yStartProperty = PropertiesUtil.addPropertyField("yStart", "Y Start: ", yStartProperty, textProperties, new PropertyChangedHandler());
        xEndProperty = PropertiesUtil.addPropertyField("xEnd", "X End: ", xEndProperty, textProperties, new PropertyChangedHandler());
        yEndProperty = PropertiesUtil.addPropertyField("yEnd", "Y End: ", yEndProperty, textProperties, new PropertyChangedHandler());
        fontSizeProperty = PropertiesUtil.addPropertyField("fontSize", "Font Size: ", fontSizeProperty, textProperties, new PropertyChangedHandler());
    }

    public void update(TextObject nText) {
        selectedText = nText;
        
        update();
    }
    
    public void update() {
        if(selectedText == null) {
            xStartProperty.setText("");
            yStartProperty.setText("");
            xEndProperty.setText("");
            yEndProperty.setText("");
            fontSizeProperty.setText("");
        } else {
            xStartProperty.setText(String.valueOf(selectedText.getXStart()));
            yStartProperty.setText(String.valueOf(selectedText.getYStart()));
            xEndProperty.setText(String.valueOf(selectedText.getXEnd()));
            yEndProperty.setText(String.valueOf(selectedText.getYEnd()));
            fontSizeProperty.setText(String.valueOf(selectedText.getFontSize()));
        }
    }
    
    public VBox getTextProperties() {
        return textProperties;
    }

    public class PropertyChangedHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            if(selectedText == null) {
                return;
            }
            
            TextField source = (TextField)e.getSource();
            
            switch(source.getId()) {
                case "xStart":
                    selectedText.setXStart(PropertiesUtil.validatePosition(source.getText(), selectedText.getXStart()));
                    break;
                case "yStart":
                    selectedText.setYStart(PropertiesUtil.validatePosition(source.getText(), selectedText.getYStart()));
                    break;
                case "xEnd":
                    selectedText.setXEnd(PropertiesUtil.validatePosition(source.getText(), selectedText.getXEnd()));
                    break;
                case "yEnd":
                    selectedText.setYEnd(PropertiesUtil.validatePosition(source.getText(), selectedText.getYEnd()));
                    break;
                case "fontSize":
                    selectedText.setFontSize(PropertiesUtil.validateInt(source.getText(), selectedText.getFontSize()));
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
                selectedText.setSourceFile(PropertiesUtil.validateFile(selectedText.getSourceFile(), "Text: ", "*.txt"));
                update();
                parent.redraw();
            }
        }
    }
}
