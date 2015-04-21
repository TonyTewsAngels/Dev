package teacheasy.runtime.editor;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Utility class to hold functions related to properties
 * initialisation and modification in the editor.
 * 
 * @author Alistair Jewers
 * @version 1.0 21 Apr 2015
 */
public class PropertiesUtil {
    /**
     * Adds a text field to a property pane controller for changing
     * a specific property.
     * 
     * @param id The ID to set on the text field.
     * @param text The text to display next to the field.
     * @param textField The text field object to instantiate.
     * @param container The container to place the property in.
     * @param changeHandler The handler for when the value is changed.
     * 
     * @return The newly constructed text field.
     */
    public static TextField addPropertyField(String id, String text, TextField textField, VBox container, EventHandler<ActionEvent> changeHandler) {
        /* Create a row to hold the components*/
        HBox newRow = new HBox();
        
        /* Set up the text field */
        textField = new TextField("");
        textField.setId(id);
        textField.setOnAction(changeHandler);
        
        /* Add the components to the row */
        newRow.getChildren().addAll(new Label(text), textField);
        
        /* Add the row to the containing VBox */
        container.getChildren().add(newRow);
        
        /* Return the text field so it's value can be used */
        return textField;
    }
    
    /**
     * Adds a 'file' field to a property pane controller to
     * allow a sourcefile to be changed.
     * 
     * @param id The ID to set on the button.
     * @param text The text to display next to the button.
     * @param button The button object to instantiate.
     * @param container The container to place the propert in.
     * @param pressHandler The handler for when the button is pressed.
     * 
     * @return The newly constructed button.
     */
    public static Button addFileField(String id, String text, Button button, VBox container, EventHandler<ActionEvent> pressHandler) {
        /* Create a row to hold the components*/
        HBox newRow = new HBox();
        
        /* Set up the button */
        button = new Button("Choose File");
        button.setId(id);
        button.setOnAction(pressHandler);
        
        /* Add the components to the row */
        newRow.getChildren().addAll(new Label(text), button);
        
        /* Add the row to the containing VBox */
        container.getChildren().add(newRow);
        
        /* Return the button so it can be used */
        return button;
    }
    
    /**
     * Validates a newly entered position value.
     * 
     * @param str The value entered as a string.
     * @param defaultVal The default value to return if the entered value is invalid.
     * 
     * @return Either the entered value as a float, or the default value.
     */
    public static float validatePosition(String str, float defaultVal) {
        float val = 0.0f;
        
        try {
            val = Float.parseFloat(str);
        } catch (NumberFormatException nfe) {
            return defaultVal;
        }
        
        if(val < 0.0f || val > 1.0f) {
            return defaultVal;
        }
        
        return val;
    }
    
    /**
     * Validates a newly entered rotation value.
     * 
     * @param str The value entered as a string.
     * @param defaultVal The default value to return if the entered value is invalid.
     * 
     * @return Either the entered value as a float, or the default value.
     */
    public static float validateRotation(String str, float defaultVal) {
        float val = 0.0f;
        
        try {
            val = Float.parseFloat(str);
        } catch (NumberFormatException nfe) {
            return defaultVal;
        }
        
        return val;
    }
    
    /**
     * Handles choosing a new image source file.
     * 
     * @param defaultVal The value to be returned if the file cannot be opened.
     * 
     * @return Either the path of the new file, or the path of the default file.
     */
    public static String validateImage(String defaultVal) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.JPG", "*.gif", "*.bmp"));
        File file = chooser.showOpenDialog(new Stage());
        
        if(file == null || !file.exists()) {
            return defaultVal;
        }
        
        return file.getAbsolutePath();
    }
}
