/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved. 
 */
package teacheasy.runtime.editor;

import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import teacheasy.data.RichText;
import teacheasy.data.TextObject;
import teacheasy.render.RenderUtil;
import teacheasy.runtime.editor.text.TextEditorWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

/**
 * Contains code relating to editor functionality for text box
 * objects.
 * 
 * @author  Alistair Jewers
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
        fontProperty.setMaxWidth(100);
    }

    /**
     * Update the text controller with a new selection.
     * 
     * @param nText The newly selected text.
     */
    public void update(TextObject nText) {
        if(selectedText != null) {
            /* If the new selection is the same as the old */
            if(selectedText.equals(nText)) {
                /* Soft update */
                update(true);
                
                /* Exit method */
                return;
            }
        }

        /* Update the selection reference */
        selectedText = nText;
        
        /* Hard update */
        update(false);
    }
    
    /**
     * Updates the controller fields. 
     * 
     * @param soft Whether or not this is a soft update.
     */
    public void update(boolean soft) {
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
            fontColorProperty.setValue(RenderUtil.colorFromString(selectedText.getColor()));
            fontProperty.setValue(selectedText.getFont());
        }
        
        /* If the update is a hard one fire the color event */
        if(!soft) {
            fontColorProperty.fireEvent(new ActionEvent(fontColorProperty, fontColorProperty));
        }
    }
    
    /**
     * Gets the text portion of the properties pane.
     */
    public VBox getTextProperties() {
        return textProperties;
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
            if(selectedText == null) {
                return;
            }
            
            /* Get the source */
            TextField source = (TextField)e.getSource();
            
            /* Check that the value has changed */
            float oldVal = 0.0f;
            float newVal = 0.0f;
            
            /* Check the ID and change the associated property */
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
                    
                    for(int i = 0; i < selectedText.textFragments.size(); i++) {
                        selectedText.textFragments.get(i).setFontSize(newIntVal);
                    }
                    
                    if(newIntVal != oldIntVal) {
                        update(false);
                        parent.redraw();
                    }

                    return;
                default:
                    break;
            }
            
            /* If the value has changed */
            if(newVal != oldVal) {      
                /* Hard update */
                update(false);
                
                /* Redraw */
                parent.redraw();
            }
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
            if(source.getId() == "editText") {
                /* Launch a text edit window */
                TextEditorHandler handler = new TextEditorHandler();
                new TextEditorWindow(handler, selectedText);
            }
        }
    }
    
    /**
     * Handles changes to color properties.
     * 
     * @author Alistair Jewers
     */
    public class ColorPropertyChangedHandler implements EventHandler<ActionEvent> {
        /**
         * Override the action event handler method.
         */
        @Override
        public void handle(ActionEvent e) {
            /* If no object is selected cancel */
            if(selectedText == null) {
                return;
            }
            
            /* Get the source */
            ColorPicker source = (ColorPicker)e.getSource();
            
            /* Check the ID and update the relevant property */
            switch(source.getId()) {
                case "fontColor":
                    selectedText.setColor(RenderUtil.stringFromColor(source.getValue()));
                    
                    for(int i = 0; i < selectedText.textFragments.size(); i++) {
                        selectedText.textFragments.get(i).setColor(RenderUtil.stringFromColor(source.getValue()));
                    }
                    
                    break;
                default:
                    break;
            }
            
            /* Redraw */
            parent.redraw();
        }
    }
    
    /**
     * Handles changes to drop down selection lists.
     * 
     * @author Alistair Jewers
     */
    public class SelectionPropertyChangedHandler implements EventHandler<ActionEvent> {
        /**
         * Override the action event handler method.
         */
        @Override
        public void handle(ActionEvent e) {
            if(selectedText == null) {
                return;
            }
            
            /* Get the source */
            ComboBox<String> source = (ComboBox<String>)e.getSource();
            
            /* Check the ID and update the relevant property */
            switch(source.getId()) {
                case "font":
                    selectedText.setFont(source.getValue());
                    
                    for(int i = 0; i < selectedText.textFragments.size(); i++) {
                        selectedText.textFragments.get(i).setFont(source.getValue());
                    }
                    
                    break;
                default:
                    break;
            }
            
            /* Hard update */
            update(false);
            
            /* Redraw */
            parent.redraw();
        }
    }
    
    /**
     * Handles the results of a text editing window.
     * 
     * @author Alistair Jewers
     */
    public class TextEditorHandler implements EventHandler<ActionEvent> {
        /* Window components */
        private TextArea textArea;
        private Stage stage;
        
        /**
         * Constructor.
         */
        public TextEditorHandler(){
            textArea = new TextArea();
            stage = new Stage();
        }
        
        /**
         * Sets up the handler with references to the window components.
         * 
         * @param nTextArea The reference to the text area in the text window.
         * @param nStage The stage of the text window.
         */
        public void setup(TextArea nTextArea, Stage nStage) {
            this.textArea = nTextArea;
            this.stage = nStage;
        }
        
        /**
         * Overrides the action event handler method.
         */
        @Override
        public void handle(ActionEvent e) {
            /* Close the text window */
            stage.close();
            
            /* Construct the new text */
            RichText text = new RichText(textArea.getText(), selectedText.getFont(), selectedText.getFontSize(), selectedText.getColor());
            
            /* Clear the selected text and add the new text */
            selectedText.textFragments.clear();
            selectedText.textFragments.add(text);
            
            /* Update the pane */
            update(false);
            
            /* Redraw */
            parent.redraw();
        }
    }
}
