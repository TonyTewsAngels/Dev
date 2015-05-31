package teacheasy.xml;

import java.util.ArrayList;

import teacheasy.runtime.EditorRunTimeData;
import teacheasy.runtime.RunTimeData;
import teacheasy.xml.util.XMLNotification;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class XMLErrorWindow {
    private Stage stage;
    private ArrayList<XMLNotification> errorList;
    
    private EditorRunTimeData editor;
    private RunTimeData runtime;
    
    public XMLErrorWindow(ArrayList<XMLNotification> errorList, EditorRunTimeData editor, RunTimeData runtime) {
        this.errorList = errorList;
        this.editor = editor;
        this.runtime = runtime;
        
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Lesson Loading Results");
        
        VBox contentBox = new VBox(15);
        
        Label title = new Label("Warnings found whilst parsing lesson file.");
        
        HBox btnRow = new HBox(10);
        Label cont = new Label("Do you wish to continue?");
        Button yesBtn = new Button("Yes");
        Button noBtn = new Button("No");
        
        yesBtn.setId("yes");
        noBtn.setId("no");
        
        yesBtn.setOnAction(new ButtonHandler());
        noBtn.setOnAction(new ButtonHandler());
        
        btnRow.getChildren().addAll(cont, yesBtn, noBtn);
        
        HBox showErrorRow = new HBox(10);
        Label show = new Label("Show Warnings");
        CheckBox showCheck = new CheckBox();
        showCheck.selectedProperty().addListener(new CheckboxChangeListener());
        showErrorRow.getChildren().addAll(show, showCheck);
        
        contentBox.getChildren().addAll(title, btnRow, showErrorRow);
        
        stage.setScene(new Scene(contentBox));
        stage.show();
    }
    
    public class ButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            Button source = (Button)e.getSource();
            
            switch(source.getId()) {
            case "yes":
                if(editor != null) {
                    editor.forceOpenLesson();
                } else if(runtime != null) {
                    runtime.forceOpenLesson();
                }
                stage.close();
                break;
            case "no":
                stage.close();
                break;
            }
        }
    }
    
    public class CheckboxChangeListener implements ChangeListener<Boolean> {
        @Override
        public void changed(ObservableValue<? extends Boolean> arg0,
                            Boolean oldVal, Boolean newVal) {
            System.out.println(newVal);
        }
        
    }
}
