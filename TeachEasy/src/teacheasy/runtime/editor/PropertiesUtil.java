package teacheasy.runtime.editor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PropertiesUtil {
    public static TextField addPropertyField(String id, String text, TextField textField, VBox container, EventHandler<ActionEvent> changeHandler) {
        HBox newRow = new HBox();
        
        textField = new TextField("");
        textField.setId(id);
        textField.setOnAction(changeHandler);
        
        newRow.getChildren().addAll(new Label(text), textField);
        
        container.getChildren().add(newRow);
        
        return textField;
    }
    
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
    
    public static float validateRotation(String str, float defaultVal) {
        float val = 0.0f;
        
        try {
            val = Float.parseFloat(str);
        } catch (NumberFormatException nfe) {
            return defaultVal;
        }
        
        return val;
    }
}
