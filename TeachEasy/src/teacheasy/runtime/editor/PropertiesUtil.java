package teacheasy.runtime.editor;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
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
     * allow a source file to be changed.
     * 
     * @param id The ID to set on the button.
     * @param text The text to display next to the button.
     * @param button The button object to instantiate.
     * @param container The container to place the property in.
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
     * Adds a boolean field to a property pane controller to
     * allow a boolean property to be changed. Takes check box form.
     * 
     * @param id The ID to set on the check box.
     * @param text The text to display next to the check box.
     * @param checkBox The check box to instantiate.
     * @param container The container to place the property in.
     * @param pressHandler The handler for when the check box is pressed.
     * 
     * @return The newly constructed check box.
     */
    public static CheckBox addBooleanField(String id, String text, CheckBox checkBox, VBox container, EventHandler<ActionEvent> pressHandler) {
        /* Create a row to hold the components*/
        HBox newRow = new HBox();
        
        /* Set up the check box */
        checkBox = new CheckBox();
        checkBox.setId(id);
        checkBox.setOnAction(pressHandler);
        
        /* Add the components to the row */
        newRow.getChildren().addAll(new Label(text), checkBox);
        
        /* Add the row to the containing VBox */
        container.getChildren().add(newRow);
        
        /* Return the check box so it can be used */
        return checkBox;
    }
    
    /**
     * 
     * 
     * @param id The ID to set on the color picker.
     * @param text The text to display next to the color picker.
     * @param picker The color picker to instantiate.
     * @param container The container to place the property in.
     * @param changeHandler The handler for when the color picker is pressed.
     * 
     * @return The newly constructed Color Picker.
     */
    public static ColorPicker addColorField(String id, String text, ColorPicker picker, VBox container, EventHandler<ActionEvent> changeHandler) {
        /* Create a row to hold the components*/
        HBox newRow = new HBox();
        
        /* Set up the color picker */
        picker = new ColorPicker();
        picker.setId(id);
        picker.setOnAction(changeHandler);
        
        /* Add the components to the row */
        newRow.getChildren().addAll(new Label(text), picker);
        
        /* Add the row to the containing VBox */
        container.getChildren().add(newRow);
        
        /* Return the color picker so it can be used */
        return picker;
    }
    
    public static ComboBox<String> addSelectionField(String id, String text, ComboBox<String> comboBox, VBox container, EventHandler<ActionEvent> selectionHandler) {
        /* Create a row to hold the components*/
        HBox newRow = new HBox();
        
        /* Set up the combo box */
        comboBox = new ComboBox<String>();
        comboBox.setId(id);
        comboBox.setOnAction(selectionHandler);
        
        /* Add the components to the row */
        newRow.getChildren().addAll(new Label(text), comboBox);
        
        /* Add the row to the containing VBox */
        container.getChildren().add(newRow);
        
        /* Return the combo box so it can be used */
        return comboBox;
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
     * Validates a newly entered float value.
     * 
     * @param str The value entered as a string.
     * @param defaultVal The default value to return if the entered value is invalid.
     * 
     * @return Either the entered value as a float, or the default value.
     */
    public static float validateFloat(String str, float defaultVal) {
        float val = 0.0f;
        
        try {
            val = Float.parseFloat(str);
        } catch (NumberFormatException nfe) {
            return defaultVal;
        }
        
        return val;
    }
    
    /**
     * Validates a newly entered integer value.
     * 
     * @param str The value entered as a string.
     * @param defaultVal The default value to return if the entered value is invalid.
     * 
     * @return Either the entered value as a integer, or the default value.
     */
    public static int validateInt(String str, int defaultVal) {
        int val = 0;
        
        try {
            val = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return defaultVal;
        }

        return val;
    }
    
    /**
     * Handles choosing a new source file.
     * 
     * @param defaultVal The value to be returned if the file cannot be opened.
     * 
     * @param filterTitle The title of the file extension filter.
     * 
     * @param fileTypes A collection of file types for the filter.
     * 
     * @return Either the path of the new file, or the path of the default file.
     */    
    public static String validateFile(String defaultVal, String filterTitle, String... fileTypes) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(filterTitle, fileTypes));
        File file = chooser.showOpenDialog(new Stage());
        
        if(file == null || !file.exists()) {
            return defaultVal;
        }
        
        return file.getAbsolutePath();
    }
}
