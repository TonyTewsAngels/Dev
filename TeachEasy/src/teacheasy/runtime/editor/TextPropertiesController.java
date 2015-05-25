package teacheasy.runtime.editor;

import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import teacheasy.data.RichText;
import teacheasy.data.TextObject;
import teacheasy.render.Util;
import teacheasy.xml.contenthandlers.text.TextEditorWindow;
import wavemedia.graphic.GraphicType;
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
    
    /* The color picker for the font colour */
    private ColorPicker fontColorProperty;
    
    /* The drop down list of fonts */
    private ComboBox<String> fontProperty;
    
    /* The button for choosing a new file */
    private Button fileButton;
    
    /* The button for changing the text */
    private Button editTextButton;
    
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
        
        /* Set up the edit text button */
        editTextButton = new Button("Edit Text");
        editTextButton.setId("editText");
        editTextButton.setOnAction(new ButtonPressedHandler());
        textProperties.getChildren().add(editTextButton);
        
        /* Set up the file select button */
        //fileButton = PropertiesUtil.addFileField("file", "File: ", fileButton, textProperties, new ButtonPressedHandler());
        
        /* Set up the property fields */
        xStartProperty = PropertiesUtil.addPropertyField("xStart", "X Start: ", xStartProperty, textProperties, new PropertyChangedHandler());
        yStartProperty = PropertiesUtil.addPropertyField("yStart", "Y Start: ", yStartProperty, textProperties, new PropertyChangedHandler());
        xEndProperty = PropertiesUtil.addPropertyField("xEnd", "X End: ", xEndProperty, textProperties, new PropertyChangedHandler());
        yEndProperty = PropertiesUtil.addPropertyField("yEnd", "Y End: ", yEndProperty, textProperties, new PropertyChangedHandler());
        fontSizeProperty = PropertiesUtil.addPropertyField("fontSize", "Font Size: ", fontSizeProperty, textProperties, new PropertyChangedHandler());
        
        /* Set up the font color property */
        fontColorProperty = PropertiesUtil.addColorField("fontColor", "Font Color: ", fontColorProperty, textProperties, new ColorPropertyChangedHandler());
        
        /* Set up the font property */
        fontProperty = PropertiesUtil.addSelectionField("font", "Font: ", fontProperty, textProperties, new SelectionPropertyChangedHandler());
        fontProperty.getItems().addAll(Font.getFontNames());
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
            fontColorProperty.setValue(Color.WHITE);
            fontProperty.setValue("Arial");
        } else {
            xStartProperty.setText(String.valueOf(selectedText.getXStart()));
            yStartProperty.setText(String.valueOf(selectedText.getYStart()));
            xEndProperty.setText(String.valueOf(selectedText.getXEnd()));
            yEndProperty.setText(String.valueOf(selectedText.getYEnd()));
            fontSizeProperty.setText(String.valueOf(selectedText.getFontSize()));
            fontColorProperty.setValue(Util.colorFromString(selectedText.getColor()));
            fontProperty.setValue(selectedText.getFont());
        }
        
        fontColorProperty.fireEvent(new ActionEvent(fontColorProperty, fontColorProperty));
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
            
            float oldVal = 0.0f;
            float newVal = 0.0f;
            
            switch(source.getId()) {
                case "xStart":
                    oldVal = selectedText.getXStart();
                    newVal = PropertiesUtil.validatePosition(source.getText(), selectedText.getXStart());
                    selectedText.setXStart(newVal);
                    break;
                case "yStart":
                    oldVal = selectedText.getYStart();
                    newVal = PropertiesUtil.validatePosition(source.getText(), selectedText.getYStart());
                    selectedText.setYStart(newVal);
                    break;
                case "xEnd":
                    oldVal = selectedText.getXEnd();
                    newVal = PropertiesUtil.validatePosition(source.getText(), selectedText.getXEnd());
                    selectedText.setXEnd(newVal);
                    break;
                case "yEnd":
                    oldVal = selectedText.getYEnd();
                    newVal = PropertiesUtil.validatePosition(source.getText(), selectedText.getYEnd());
                    selectedText.setYEnd(newVal);
                    break;
                case "fontSize":
                    int oldIntVal = selectedText.getFontSize();
                    int newIntVal = PropertiesUtil.validateInt(source.getText(), selectedText.getFontSize());
                    selectedText.setFontSize(newIntVal);
                    
                    if(newIntVal != oldIntVal) {
                        update();
                        parent.redraw();
                    }

                    return;
                default:
                    break;
            }
            
            if(newVal != oldVal) {
                System.out.println("new: " + newVal + " old: " + oldVal);
                
                update();
                parent.redraw();
            }
        }
    }
    
    public class ButtonPressedHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            Button source = (Button)e.getSource();
            
            if(source.getId() == "file") {
                selectedText.setSourceFile(PropertiesUtil.validateFile(selectedText.getSourceFile(), "Text: ", "*.txt"));
                update();
                parent.redraw();
            } else if(source.getId() == "editText") {
                System.out.println("Edit");
                
                TextEditorHandler handler = new TextEditorHandler();
                new TextEditorWindow(handler, selectedText.textFragments.get(0).getText());
            }
        }
    }
    
    public class ColorPropertyChangedHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            if(selectedText == null) {
                return;
            }
            
            ColorPicker source = (ColorPicker)e.getSource();
            
            switch(source.getId()) {
                case "fontColor":
                    selectedText.setColor(Util.stringFromColor(source.getValue()));
                    break;
                default:
                    break;
            }
            
            parent.redraw();
        }
    }
    
    public class SelectionPropertyChangedHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            if(selectedText == null) {
                return;
            }
            
            ComboBox<String> source = (ComboBox<String>)e.getSource();
            
            switch(source.getId()) {
                case "font":
                    selectedText.setFont(source.getValue());
                    break;
                default:
                    break;
            }
            
            update();
            parent.redraw();
        }
    }
    
    public class TextEditorHandler implements EventHandler<ActionEvent> {
        private TextArea textArea;
        private Stage stage;
        
        public TextEditorHandler(){
            textArea = new TextArea();
            stage = new Stage();
        }
        
        public void setup(TextArea nTextArea, Stage nStage) {
            this.textArea = nTextArea;
            this.stage = nStage;
        }
        
        @Override
        public void handle(ActionEvent e) {
            System.out.println(textArea.getText());
            stage.close();
            
            RichText text = new RichText(textArea.getText(), selectedText.getFont(), selectedText.getFontSize(), selectedText.getColor());
            
            selectedText.textFragments.clear();
            selectedText.textFragments.add(text);
            
            update();
            parent.redraw();
        }
    }
}
