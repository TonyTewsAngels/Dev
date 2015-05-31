package teacheasy.xml;

import java.util.ArrayList;

import teacheasy.runtime.EditorRunTimeData;
import teacheasy.runtime.RunTimeData;
import teacheasy.xml.util.XMLNotification;
import teacheasy.xml.util.XMLNotification.Level;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class XMLErrorWindow {
    private Stage stage;
    private TableView<XMLNotificationWrapper> table;
    VBox contentBox;
    
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
        stage.setWidth(650);
        
        contentBox = new VBox(15);
        contentBox.setPadding(new Insets(10));
        
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
        
        table = new TableView<XMLNotificationWrapper>();
        table.setEditable(false);
        table.setMaxHeight(200);
        
        TableColumn<XMLNotificationWrapper, String> level = new TableColumn<XMLNotificationWrapper, String>("Warning Level");
        level.setCellValueFactory(new PropertyValueFactory<XMLNotificationWrapper, String>("level"));
        level.setMinWidth(200);
        
        TableColumn<XMLNotificationWrapper, String> text = new TableColumn<XMLNotificationWrapper, String>("Warning Text");
        text.setCellValueFactory(new PropertyValueFactory<XMLNotificationWrapper, String>("text"));
        text.setMinWidth(200);
        
        table.getColumns().add(level);
        table.getColumns().add(text);
        
        for(XMLNotification x : errorList) {
            table.getItems().add(new XMLNotificationWrapper(x.getText(), x.getLevel()));
            
            if(x.getLevel() == Level.ERROR) {
                yesBtn.setDisable(true);
                title.setText("Errors found whilst parsing lesson file.");
                cont.setText("Cannot continue.");
                noBtn.setText("Cancel");
            }
        }
        
        contentBox.getChildren().addAll(title, btnRow, showErrorRow);
        
        stage.setScene(new Scene(contentBox));
        stage.show();
    }
    
    public static class XMLNotificationWrapper {
        private final SimpleStringProperty text;
        private final SimpleStringProperty level;
        
        public XMLNotificationWrapper(String text, Level level) {
            this.text = new SimpleStringProperty(text);
            this.level = new SimpleStringProperty(level.toString());
        }
        
        public String getText() {
            return text.get();
        }
        
        public String getLevel() {
            return level.get();
        }
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
            if(newVal) {
                contentBox.getChildren().add(table);
                stage.setHeight(375);
            } else {
                contentBox.getChildren().remove(table);
                stage.setHeight(135);
            }
        }
    }
}
