/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved. 
 */
package teacheasy.runtime.editor;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.converter.BooleanStringConverter;
import teacheasy.data.MultipleChoiceObject;
import teacheasy.data.multichoice.Answer;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import teacheasy.data.MultipleChoiceObject.MultiChoiceType;
import teacheasy.data.MultipleChoiceObject.Orientation;;

/**
 * Contains code relating to editor functionality for multiple
 * choice objects.
 * 
 * @author  Alistair Jewers
 * @version 1.0 Apr 21 2015
 */
public class MultipleChoicePropertiesController {
    
    /* Reference to the properties pane responsible for this controller */
    private PropertiesPane parent;
    
    /* Currently selected image */
    private MultipleChoiceObject selectedMultipleChoice;
    
    /* The UI element to contain the editable properties */
    private VBox multipleChoiceProperties;
    
    /* The text fields for the different properties */
    private TextField xStartProperty;
    private TextField yStartProperty;
    private TextField marksProperty;
    
    /* The  retry property */
    private CheckBox retryProperty;
    
    /* The drop down list of orientations */
    private ComboBox<String> orientationProperty;
    private ComboBox<String> typeProperty;
    
    /* The buttons for adding and removing answers */
    private Button newAnswerButton;
    private Button removeAnswerButton;
    
    /* The answer table */
    private TableView<AnswerProperty> answerTable;
    
    /**
     * Constructor. 
     * 
     * @param nParent The properties pane responsible for this controller.
     */
    public MultipleChoicePropertiesController(PropertiesPane nParent) {
        /* Set the parent reference */
        this.parent = nParent;
        
        /* Set the selected object null */
        selectedMultipleChoice = null;
        
        /* Set up the UI container */
        multipleChoiceProperties = new VBox();
        multipleChoiceProperties.setSpacing(5);
        multipleChoiceProperties.setPadding(new Insets(5));

        /* Set up the property fields */
        xStartProperty = PropertiesUtil.addPropertyField("xStart", "X Start: ", xStartProperty, multipleChoiceProperties, new PropertyChangedHandler());
        yStartProperty = PropertiesUtil.addPropertyField("yStart", "Y Start: ", yStartProperty, multipleChoiceProperties, new PropertyChangedHandler());
        marksProperty = PropertiesUtil.addPropertyField("marks", "Marks: ", marksProperty, multipleChoiceProperties, new PropertyChangedHandler());
        
        /* Set up the type property field */
        typeProperty = PropertiesUtil.addSelectionField("type", "Type: ", typeProperty, multipleChoiceProperties, new SelectionPropertyChangedHandler());
        for(MultiChoiceType t : MultiChoiceType.values()) {
            typeProperty.getItems().add(t.toString());
        }
        
        /* Set up the orientation property field */
        orientationProperty = PropertiesUtil.addSelectionField("orientation", "Orientation: ", orientationProperty, multipleChoiceProperties, new SelectionPropertyChangedHandler());
        for(Orientation o : Orientation.values()) {
            orientationProperty.getItems().add(o.toString());
        }
        
        /* Set up the retry property field */
        retryProperty = PropertiesUtil.addBooleanField("retry", "Retry: ", retryProperty, multipleChoiceProperties, new BooleanPropertyChangedHandler());
        
        /* Construct the table for the editable answers */
        setupAnswerTable();
        
        /* Set up the new answer button */
        newAnswerButton = new Button("Add Answer");
        newAnswerButton.setId("newAnswer");
        newAnswerButton.setOnAction(new ButtonPressedHandler());
        
        /* Set up the remove answers button */
        removeAnswerButton = new Button("Remove Selected");
        removeAnswerButton.setId("removeAnswer");
        removeAnswerButton.setOnAction(new ButtonPressedHandler());
        
        /* Add the buttons to the pane */
        multipleChoiceProperties.getChildren().addAll(newAnswerButton, removeAnswerButton);
    }
    
    /**
     * Sets up the answer table.
     */
    public void setupAnswerTable() {
        /* Instantiate a new table view */
        answerTable = new TableView<AnswerProperty>();
        
        /* Make the entries editable */
        answerTable.setEditable(true);
        
        /* Create the answer column */
        TableColumn<AnswerProperty, String> answerColumn = new TableColumn<AnswerProperty, String>("Answer");
        answerColumn.setMinWidth(100);
        answerColumn.setEditable(true);
        answerColumn.setCellValueFactory(new PropertyValueFactory<AnswerProperty, String>("answer"));
        answerColumn.setCellFactory(TextFieldTableCell.<AnswerProperty>forTableColumn());
        answerColumn.setOnEditCommit(new AnswerChangedHandler());
        
        /* Create the correct setting column */
        TableColumn<AnswerProperty, Boolean> correctColumn = new TableColumn<AnswerProperty, Boolean>("Correct");
        correctColumn.setMinWidth(100);
        correctColumn.setCellValueFactory(new PropertyValueFactory<AnswerProperty, Boolean>("correct"));
        correctColumn.setCellFactory(ComboBoxTableCell.<AnswerProperty, Boolean>forTableColumn(new BooleanStringConverter(), true, false));
        correctColumn.setOnEditCommit(new CorrectChangedHandler());
        
        /* Create the remove column */
        TableColumn<AnswerProperty, Boolean> deleteColumn = new TableColumn<AnswerProperty, Boolean>("Remove");
        deleteColumn.setMinWidth(100);
        deleteColumn.setEditable(true);
        deleteColumn.setCellValueFactory(new RemoveCallback());
        deleteColumn.setCellFactory(CheckBoxTableCell.forTableColumn(deleteColumn));
        
        /* Add the columns */
        answerTable.getColumns().add(answerColumn);
        answerTable.getColumns().add(correctColumn);
        answerTable.getColumns().add(deleteColumn);
        
        /* Fix the height */
        answerTable.setMaxHeight(160);
        
        /* Add the answer table to the pane */
        multipleChoiceProperties.getChildren().add(answerTable);
    }

    /**
     * Update the controller with a new multiple choice object selection.
     *  
     * @param nMChoice The new object to select.
     */
    public void update(MultipleChoiceObject nMChoice) {      
        /* Update the reference */
        selectedMultipleChoice = nMChoice;
        
        /* Update the pane */
        update();
    }
    
    /**
     * Updates the controller fields. 
     */
    public void update() {
        if(selectedMultipleChoice == null) {
            xStartProperty.setText("");
            yStartProperty.setText("");
            marksProperty.setText("");
            orientationProperty.setValue("");
            typeProperty.setValue("");
            retryProperty.setSelected(false);
            answerTable.getItems().clear();
        } else {
            xStartProperty.setText(String.valueOf(selectedMultipleChoice.getXStart()));
            yStartProperty.setText(String.valueOf(selectedMultipleChoice.getYStart()));
            marksProperty.setText(String.valueOf(selectedMultipleChoice.getMarks()));
            typeProperty.setValue(selectedMultipleChoice.getMultiChoiceType().toString());
            orientationProperty.setValue(selectedMultipleChoice.getOrientation().toString());
            retryProperty.setSelected(selectedMultipleChoice.isRetry());
            answerTable.getItems().clear();
            
            /* For each answer add a new row to the answer table */
            for(Answer a : selectedMultipleChoice.getAnswers()) {
                answerTable.getItems().add(new AnswerProperty(a));
            }
        }
    }
    
    /**
     * Gets the multiple choice part of the properties pane.
     */
    public VBox getMultipleChoiceProperties() {
        return multipleChoiceProperties;
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
            if(selectedMultipleChoice == null) {
                return;
            }
            
            /* Get the source */
            TextField source = (TextField)e.getSource();
            
            /* Check the ID and change the associated property */
            switch(source.getId()) {
                case "xStart":
                    selectedMultipleChoice.setXStart(PropertiesUtil.validatePosition(source.getText(), selectedMultipleChoice.getXStart()));
                    break;
                case "yStart":
                    selectedMultipleChoice.setYStart(PropertiesUtil.validatePosition(source.getText(), selectedMultipleChoice.getYStart()));
                    break;
                case "marks":
                    selectedMultipleChoice.setMarks(PropertiesUtil.validateInt(source.getText(), selectedMultipleChoice.getMarks()));
                    break;
                default:
                    break;
            }
            
            /* Update the pane */
            update();
            
            /* Redraw */
            parent.redraw();
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
            switch(source.getId()){
                case "newAnswer":
                    /* Add a new answer */
                    selectedMultipleChoice.addAnswer(new Answer("New Answer", true));
                    break;
                case "removeAnswer":
                    /* Remove any answers flagged for removal */
                    for(AnswerProperty a : answerTable.getItems()) {
                        if(a.getRemove()) {
                            selectedMultipleChoice.removeAnswer(a.getAnswerObject());
                        }
                    }
                    break; 
            }
            
            /* Update the pane */
            update();
            
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
            /* If no object is selected cancel */
            if(selectedMultipleChoice == null) {
                return;
            }
            
            /* Get the source */
            ComboBox<String> source = (ComboBox<String>)e.getSource();
            
            /* Check the ID and update the relevant property */
            switch(source.getId()) {
                case "orientation":
                    selectedMultipleChoice.setOrientation(Orientation.valueOf(source.getValue()));
                    break;
                case "type":
                    selectedMultipleChoice.setType(MultiChoiceType.valueOf(source.getValue()));
                    break;
                default:
                    break;
            }
            
            /* Update the pane */
            update();
            
            /* Redraw */
            parent.redraw();
        }
    }
    
    /**
     * Handles changes to boolean properties.
     * 
     * @author Alistair Jewers
     */
    public class BooleanPropertyChangedHandler implements EventHandler<ActionEvent> {
        /**
        * Override the action event handler method.
        */
        @Override
        public void handle(ActionEvent e) {
            /* If no object is selected cancel */
            if(selectedMultipleChoice == null) {
                return;
            }
            
            /* Get the source */
            CheckBox source = (CheckBox)e.getSource();
            
            /* Check the ID and update the relevant property */
            switch(source.getId()) {
                case "retry":
                    selectedMultipleChoice.setRetry(source.isSelected());
                    break;
                default:
                    break;
            }
            
            /* Update the pane */
            update();
            
            /* Redraw */
            parent.redraw();
        }
    }
    
    /**
     * Handles the user changing answers in the answer table.
     * 
     * @author Alistair Jewers
     */
    public class AnswerChangedHandler implements EventHandler<CellEditEvent<AnswerProperty, String>> {
        /**
         * Overrides the handle method for a change to the string
         */
        @Override
        public void handle(CellEditEvent<AnswerProperty, String> t) {
            /* Set the new answer text */
            ((AnswerProperty) t.getTableView()
                               .getItems()
                               .get(t.getTablePosition().getRow()))
                               .setAnswer(t.getNewValue());
            
            /* Redraw */
            parent.redraw();
        }
    }
    
    /**
     * Handles the user changing the correct setting in the answer table.
     * 
     * @author Alistair Jewers
     */
    public class CorrectChangedHandler implements EventHandler<CellEditEvent<AnswerProperty, Boolean>> {
        @Override
        public void handle(CellEditEvent<AnswerProperty, Boolean> t) {
            /* Update the correct propety */
            ((AnswerProperty) t.getTableView()
                               .getItems().get(t.getTablePosition().getRow()))
                               .setCorrect(t.getNewValue());
            
            /* Redraw */
            parent.redraw();
        }
    }
    
    /**
     * Handles the user changing the remove checkbox
     * 
     * @author Alistair Jewers
     */
    public class RemoveCallback implements Callback<TableColumn.CellDataFeatures<AnswerProperty, Boolean>, ObservableValue<Boolean>> {
        /**
         * Overrides the handle method for a change to the boolean property
         */
        @Override
        public ObservableValue<Boolean> call(CellDataFeatures<AnswerProperty, Boolean> b) {
            /* Return the value of the checkbox to the calling method */
            return b.getValue().getRemoveProperty();
        }
    }
}

