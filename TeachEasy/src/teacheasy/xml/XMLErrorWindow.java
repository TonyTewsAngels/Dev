/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved. 
 */
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

/**
 * Creates a window to display warnings and errors found
 * whilst parsing an XML file.
 * 
 * @author  Alistair Jewers
 * @version 1.0 22 May 2015
 */
public class XMLErrorWindow {
    /** The window stage */
    private Stage stage;
    
    /** The error table */
    private TableView<XMLNotificationWrapper> table;
    
    /** Container for the window content */
    private VBox contentBox;
    
    /* References to runtime data */
    private EditorRunTimeData editor;
    private RunTimeData runtime;
    
    public XMLErrorWindow(ArrayList<XMLNotification> errorList, EditorRunTimeData editor, RunTimeData runtime) {
        /* Set the references */
        this.editor = editor;
        this.runtime = runtime;
        
        /* Create the stage */
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Lesson Loading Results");
        stage.setWidth(650);
        
        /* Initialise the container */
        contentBox = new VBox(15);
        contentBox.setPadding(new Insets(10));
        
        /* Create the title */
        Label title = new Label("Warnings found whilst parsing lesson file.");
        
        /* Create the buttons in a row */
        HBox btnRow = new HBox(10);
        Label cont = new Label("Do you wish to continue?");
        Button yesBtn = new Button("Yes");
        Button noBtn = new Button("No");
        
        /* Set button IDs */
        yesBtn.setId("yes");
        noBtn.setId("no");
        
        /* Set button action handlers */
        yesBtn.setOnAction(new ButtonHandler());
        noBtn.setOnAction(new ButtonHandler());
        
        /* Add buttons to the row */
        btnRow.getChildren().addAll(cont, yesBtn, noBtn);
        
        /* Create a row for the show errors check box */
        HBox showErrorRow = new HBox(10);
        Label show = new Label("Show Warnings");
        CheckBox showCheck = new CheckBox();
        showCheck.selectedProperty().addListener(new CheckboxChangeListener());
        showErrorRow.getChildren().addAll(show, showCheck);
        
        /* Create the table */
        table = new TableView<XMLNotificationWrapper>();
        table.setEditable(false);
        table.setMaxHeight(200);
        
        /* Create the table columns */
        TableColumn<XMLNotificationWrapper, String> level = new TableColumn<XMLNotificationWrapper, String>("Warning Level");
        level.setCellValueFactory(new PropertyValueFactory<XMLNotificationWrapper, String>("level"));
        level.setMinWidth(200);
        
        TableColumn<XMLNotificationWrapper, String> text = new TableColumn<XMLNotificationWrapper, String>("Warning Text");
        text.setCellValueFactory(new PropertyValueFactory<XMLNotificationWrapper, String>("text"));
        text.setMinWidth(200);
        
        /* Add the table columns */
        table.getColumns().add(level);
        table.getColumns().add(text);
        
        /* Loop through every notification and add a row to the list */
        for(XMLNotification x : errorList) {
            table.getItems().add(new XMLNotificationWrapper(x.getText(), x.getLevel()));
            
            if(x.getLevel() == Level.ERROR) {
                /* If an error exists modify the window */
                yesBtn.setDisable(true);
                title.setText("Errors found whilst parsing lesson file.");
                cont.setText("Cannot continue.");
                noBtn.setText("Cancel");
            }
        }
        
        /* Add the components to the container */
        contentBox.getChildren().addAll(title, btnRow, showErrorRow);
        
        /* Show the window */
        stage.setScene(new Scene(contentBox));
        stage.show();
    }
    
    /**
     * Class to wrap XMLNotifications into table rows.
     * 
     * @author Alistair Jewers
     */
    public static class XMLNotificationWrapper {
        /* Simple property wrappers */
        private final SimpleStringProperty text;
        private final SimpleStringProperty level;
        
        /**
         * Constructor.
         * 
         * @param text Text pulled from the notification being wrapped.
         * @param level Level pulled from the notification being wrapped.
         */
        public XMLNotificationWrapper(String text, Level level) {
            this.text = new SimpleStringProperty(text);
            this.level = new SimpleStringProperty(level.toString());
        }
        
        /** Gets the text from the wrapper */
        public String getText() {
            return text.get();
        }
        
        /** Get the level from the wrapper */
        public String getLevel() {
            return level.get();
        }
    }
    
    /**
     * Handles button presses.
     * 
     * @author Alistair Jewers
     *
     */
    public class ButtonHandler implements EventHandler<ActionEvent> {
        /**
         * Overrides the action event handling method.
         */
        @Override
        public void handle(ActionEvent e) {
            /* Get the source */
            Button source = (Button)e.getSource();
            
            /* Check the source ID */
            switch(source.getId()) {
                case "yes":
                    /* User wishes to proceed */
                    if(editor != null) {
                        editor.forceOpenLesson();
                    } else if(runtime != null) {
                        runtime.forceOpenLesson();
                    }
                    
                    /* Close the window */
                    stage.close();
                    break;
                case "no":
                    /* User does not wish to proceed, close the window */
                    stage.close();
                    break;
            }
        }
    }
    
    /**
     * Handles changes to the show errors check box.
     * 
     * @author Alistair Jewers
     */
    public class CheckboxChangeListener implements ChangeListener<Boolean> {
        /**
         * Overrides the value changed method.
         */
        @Override
        public void changed(ObservableValue<? extends Boolean> arg0,
                            Boolean oldVal, Boolean newVal) {
            if(newVal) {
                /* Show the error list */
                contentBox.getChildren().add(table);
                stage.setHeight(375);
            } else {
                /* Hide the error list */
                contentBox.getChildren().remove(table);
                stage.setHeight(135);
            }
        }
    }
}
