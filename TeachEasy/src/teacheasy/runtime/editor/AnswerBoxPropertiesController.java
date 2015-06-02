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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import teacheasy.data.AnswerBoxObject;
import teacheasy.data.multichoice.Answer;

/**
 * Contains code relating to editor
 * functionality for Answer Box objects.
 * 
 * @author  Alistair Jewers
 * @version 1.0 21 Apr 2015
 */
public class AnswerBoxPropertiesController {
    /* Reference to the properties pane responsible for this controller */
    private PropertiesPane parent;
    
    /* Currently selected image */
    private AnswerBoxObject selectedAnswerBox;
    
    /* The UI element to contain the editable properties */
    private VBox answerBoxProperties;
    
    /* The text fields for the different properties */
    private TextField xStartProperty;
    private TextField yStartProperty;
    private TextField marksProperty;
    private TextField characterLimitProperty;
    private TextField upperLimitProperty;
    private TextField lowerLimitProperty;
    
    /* The  retry property */
    private CheckBox retryProperty;
    private CheckBox numericalProperty;
    
    /* The drop down list of orientations */
    private ComboBox<String> orientationProperty;
    private ComboBox<String> typeProperty;
    
    /* The buttons for adding and removing answers */
    private HBox answerButtons;
    
    /* The answer table */
    private TableView<AnswerProperty> answerTable;
    
    /**
     * Constructor. 
     * 
     * @param nParent The properties pane responsible for this controller.
     */
    public AnswerBoxPropertiesController(PropertiesPane nParent) {
        /* Set the parent reference */
        this.parent = nParent;
        
        /* Set the selected object null */
        selectedAnswerBox = null;
        
        /* Set up the UI container */
        answerBoxProperties = new VBox();
        answerBoxProperties.setSpacing(5);
        answerBoxProperties.setPadding(new Insets(5));

        /* Set up the property fields */
        xStartProperty = PropertiesUtil.addPropertyField("xStart", "X Start: ", xStartProperty, answerBoxProperties, new PropertyChangedHandler());
        yStartProperty = PropertiesUtil.addPropertyField("yStart", "Y Start: ", yStartProperty, answerBoxProperties, new PropertyChangedHandler());
        marksProperty = PropertiesUtil.addPropertyField("marks", "Marks: ", marksProperty, answerBoxProperties, new PropertyChangedHandler());
        characterLimitProperty = PropertiesUtil.addPropertyField("characterLimit", "Character Limit: ", characterLimitProperty, answerBoxProperties, new PropertyChangedHandler());
        upperLimitProperty = PropertiesUtil.addPropertyField("upperLimit", "Upper Limit: ", upperLimitProperty, answerBoxProperties, new PropertyChangedHandler());
        lowerLimitProperty = PropertiesUtil.addPropertyField("lowerLimit", "Lower Limit: ", lowerLimitProperty, answerBoxProperties, new PropertyChangedHandler());

        /* Set up the rety property field */
        retryProperty = PropertiesUtil.addBooleanField("retry", "Retry: ", retryProperty, answerBoxProperties, new BooleanPropertyChangedHandler());
        numericalProperty = PropertiesUtil.addBooleanField("numerical", "Numerical: ", retryProperty, answerBoxProperties, new BooleanPropertyChangedHandler());
        
        /* Construct the table for the editable answers */
        setupAnswerTable();
        
        /* Set up the new answer button */
        Button newAnswerButton= new Button("Add Answer");
        newAnswerButton.setId("newAnswer");
        newAnswerButton.setOnAction(new ButtonPressedHandler());
        
        /* Set up the remove answer button */
        Button removeAnswerButton = new Button("Remove Selected");
        removeAnswerButton.setId("removeAnswer");
        removeAnswerButton.setOnAction(new ButtonPressedHandler());
        
        /* Create the button container */
        answerButtons = new HBox();
        answerButtons.getChildren().addAll(newAnswerButton, removeAnswerButton);
        
        /* Add the button container */
        answerBoxProperties.getChildren().add(answerButtons);
    }
    
    /**
     * Sets up the table of answers
     */
    public void setupAnswerTable() {
        /* Instantiate the table*/
        answerTable = new TableView<AnswerProperty>();
        
        /* Set the table fields editable */
        answerTable.setEditable(true);
        
        /* Create the answer column */
        TableColumn<AnswerProperty, String> answerColumn = new TableColumn<AnswerProperty, String>("Answer");
        answerColumn.setMinWidth(100);
        answerColumn.setEditable(true);
        answerColumn.setCellValueFactory(new PropertyValueFactory<AnswerProperty, String>("answer"));
        answerColumn.setCellFactory(TextFieldTableCell.<AnswerProperty>forTableColumn());
        answerColumn.setOnEditCommit(new AnswerChangedHandler());
        
        /* Create the delete column */
        TableColumn<AnswerProperty, Boolean> deleteColumn = new TableColumn<AnswerProperty, Boolean>("Remove");
        deleteColumn.setMinWidth(100);
        deleteColumn.setEditable(true);
        deleteColumn.setCellValueFactory(new RemoveCallback());
        deleteColumn.setCellFactory(CheckBoxTableCell.forTableColumn(deleteColumn));
        
        /* Add the columns */
        answerTable.getColumns().add(answerColumn);
        answerTable.getColumns().add(deleteColumn);
        
        /* Fix the size */
        answerTable.setMaxHeight(160);
        
        /* Add the answer table to the pane */
        answerBoxProperties.getChildren().add(answerTable);
    }

    /**
     * Update the answer box controller.
     * 
     * @param nAnswerBox The selected answer box.
     */
    public void update(AnswerBoxObject nAnswerBox) {
        /* Update the selected reference */
        selectedAnswerBox = nAnswerBox;
        
        /* Update the display */
        update();
    }
    
    /**
     * Updates the displayed properties to have the 
     * values of the selected answerbox.
     */
    public void update() {
        if(selectedAnswerBox == null) {
            xStartProperty.setText("");
            yStartProperty.setText("");
            marksProperty.setText("");
            characterLimitProperty.setText("");
            upperLimitProperty.setText("");
            lowerLimitProperty.setText("");
            orientationProperty.setValue("");
            typeProperty.setValue("");
            retryProperty.setSelected(false);
            numericalProperty.setSelected(false);
            answerTable.getItems().clear();
        } else {
            xStartProperty.setText(String.valueOf(selectedAnswerBox.getXStart()));
            yStartProperty.setText(String.valueOf(selectedAnswerBox.getYStart()));
            marksProperty.setText(String.valueOf(selectedAnswerBox.getMarks()));
            characterLimitProperty.setText(String.valueOf(selectedAnswerBox.getCharacterLimit()));
            retryProperty.setSelected(selectedAnswerBox.isRetry());
            numericalProperty.setSelected(selectedAnswerBox.isNumerical());
            answerTable.getItems().clear();
            answerBoxProperties.getChildren().removeAll(answerTable, answerButtons);
            
            /* Check if this answerbox is non-numerical */
            if(!selectedAnswerBox.isNumerical()) {
                /* Disable the upper and lower bound property boxes */
                upperLimitProperty.setDisable(true);
                lowerLimitProperty.setDisable(true);
                
                /* Add the answer table and answer buttons */
                answerBoxProperties.getChildren().addAll(answerTable, answerButtons);
                
                /* For each answer add a row to the table */
                for(Answer a : selectedAnswerBox.getAnswers()) {
                    answerTable.getItems().add(new AnswerProperty(a));
                }
            } else {
                /* Enable the upper and lower bound property fields */
                upperLimitProperty.setDisable(false);
                lowerLimitProperty.setDisable(false);
                
                /* Set the text */
                lowerLimitProperty.setText("" + selectedAnswerBox.getLowerBound());
                upperLimitProperty.setText("" + selectedAnswerBox.getUpperBound());
            }
        }
    }
    
    /**
     * Gets the answer box portion of the properties pane.
     */
    public VBox getAnswerBoxProperties() {
        return answerBoxProperties;
    }

    /**
     * Handles changes to the properties of the answer box.
     * 
     * @author Alistair Jewers
     */
    public class PropertyChangedHandler implements EventHandler<ActionEvent> {
        /**
         * Override the action event handling method.
         */
        @Override
        public void handle(ActionEvent e) {
            /* If there is no answerbox selected cancel */
            if(selectedAnswerBox == null) {
                return;
            }
            
            /* Get the source of the event */
            TextField source = (TextField)e.getSource();
            
            /* Check the ID and update the relevant property */
            switch(source.getId()) {
                case "xStart":
                    selectedAnswerBox.setXStart(PropertiesUtil.validatePosition(source.getText(), selectedAnswerBox.getXStart()));
                    break;
                case "yStart":
                    selectedAnswerBox.setYStart(PropertiesUtil.validatePosition(source.getText(), selectedAnswerBox.getYStart()));
                    break;
                case "marks":
                    selectedAnswerBox.setMarks(PropertiesUtil.validateInt(source.getText(), selectedAnswerBox.getMarks()));
                    break;
                case "characterLimit":
                    selectedAnswerBox.setCharacterLimit(PropertiesUtil.validateInt(source.getText(), selectedAnswerBox.getCharacterLimit()));
                    break;
                case "upperLimit":
                    selectedAnswerBox.setUpperBound(PropertiesUtil.validateFloat(source.getText(), selectedAnswerBox.getUpperBound()));
                    break;
                case "lowerLimit":
                    selectedAnswerBox.setLowerBound(PropertiesUtil.validateFloat(source.getText(), selectedAnswerBox.getLowerBound()));
                    break;
                default:
                    break;
            }
            
            /* Update the property fields */
            update();
            
            /* Redrasw the application UI */
            parent.redraw();
        }
    }
    
    /**
     * Handles button press events in the answer box properties.
     * 
     * @author Alistair Jewers
     */
    public class ButtonPressedHandler implements EventHandler<ActionEvent> {
        /**
         * Override the action event handling method
         */
        @Override
        public void handle(ActionEvent e) {
            /* If there is no answer box selected cancel */
            if(selectedAnswerBox == null) {
                return;
            }
            
            /* Get the source */
            Button source = (Button)e.getSource();
            
            /* Check the source ID */
            switch(source.getId()){
                case "newAnswer":
                    /* Add an answer to the answer box data object */
                    selectedAnswerBox.addAnswer(new Answer("New Answer", true));
                    break;
                case "removeAnswer":
                    /* Remove any answers which have the remove box ticked */
                    for(AnswerProperty a : answerTable.getItems()) {
                        if(a.getRemove()) {
                            selectedAnswerBox.removeAnswer(a.getAnswerObject());
                        }
                    }
                    break; 
            }
            
            /* Update the property fields */
            update();
            
            /* Redraw */
            parent.redraw();
        }
    }
    
    /**
     * Handles changes to boolean properties.
     * 
     * @author Alistair Jewers
     *
     */
    public class BooleanPropertyChangedHandler implements EventHandler<ActionEvent> {
        /**
         * Override the action event handling method.
         */
        @Override
        public void handle(ActionEvent e) {
            /* If no answer box is selected cancel */
            if(selectedAnswerBox == null) {
                return;
            }
            
            /* Get the source */
            CheckBox source = (CheckBox)e.getSource();
            
            /* Check the ID and update the properties */
            switch(source.getId()) {
                case "retry":
                    selectedAnswerBox.setRetry(source.isSelected());
                    break;
                case "numerical":
                    selectedAnswerBox.setNumerical(source.isSelected());
                default:
                    break;
            }
            
            /* Update the properties pane properties */
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
            ((AnswerProperty)t.getTableView()
                              .getItems()
                              .get(t.getTablePosition()
                              .getRow()))
                              .setAnswer(t.getNewValue());
            
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

