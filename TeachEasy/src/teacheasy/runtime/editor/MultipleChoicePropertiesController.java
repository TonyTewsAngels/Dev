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
 * Encapsulates functionality relating to editor
 * functionality for Multiple Choice objects.
 * 
 * @author Alistair Jewers
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
        
        /* Set up the type and orientation property fields */
        typeProperty = PropertiesUtil.addSelectionField("type", "Type: ", typeProperty, multipleChoiceProperties, new SelectionPropertyChangedHandler());
        for(MultiChoiceType t : MultiChoiceType.values()) {
            typeProperty.getItems().add(t.toString());
        }
        
        orientationProperty = PropertiesUtil.addSelectionField("orientation", "Orientation: ", orientationProperty, multipleChoiceProperties, new SelectionPropertyChangedHandler());
        for(Orientation o : Orientation.values()) {
            orientationProperty.getItems().add(o.toString());
        }
        
        /* Set up the rety property field */
        retryProperty = PropertiesUtil.addBooleanField("retry", "Retry: ", retryProperty, multipleChoiceProperties, new BooleanPropertyChangedHandler());
        
        /* Construct the table for the editable answers */
        setupAnswerTable();
        
        /* Set up the new answer button */
        newAnswerButton = new Button("Add Answer");
        newAnswerButton.setId("newAnswer");
        newAnswerButton.setOnAction(new ButtonPressedHandler());
        
        removeAnswerButton = new Button("Remove Selected");
        removeAnswerButton.setId("removeAnswer");
        removeAnswerButton.setOnAction(new ButtonPressedHandler());
        
        multipleChoiceProperties.getChildren().addAll(newAnswerButton, removeAnswerButton);
    }
    
    public void setupAnswerTable() {
        answerTable = new TableView<AnswerProperty>();
        answerTable.setEditable(true);
        
        TableColumn<AnswerProperty, String> answerColumn = new TableColumn<AnswerProperty, String>("Answer");
        answerColumn.setMinWidth(100);
        answerColumn.setEditable(true);
        answerColumn.setCellValueFactory(new PropertyValueFactory<AnswerProperty, String>("answer"));
        answerColumn.setCellFactory(TextFieldTableCell.<AnswerProperty>forTableColumn());
        answerColumn.setOnEditCommit(new AnswerChangedHandler());
        
        TableColumn<AnswerProperty, Boolean> correctColumn = new TableColumn<AnswerProperty, Boolean>("Correct");
        correctColumn.setMinWidth(100);
        correctColumn.setCellValueFactory(new PropertyValueFactory<AnswerProperty, Boolean>("correct"));
        correctColumn.setCellFactory(ComboBoxTableCell.<AnswerProperty, Boolean>forTableColumn(new BooleanStringConverter(), true, false));
        correctColumn.setOnEditCommit(new CorrectChangedHandler());
        
        TableColumn<AnswerProperty, Boolean> deleteColumn = new TableColumn<AnswerProperty, Boolean>("Remove");
        deleteColumn.setMinWidth(100);
        deleteColumn.setEditable(true);
        deleteColumn.setCellValueFactory(new RemoveCallback());
        deleteColumn.setCellFactory(CheckBoxTableCell.forTableColumn(deleteColumn));
        
        answerTable.getColumns().add(answerColumn);
        answerTable.getColumns().add(correctColumn);
        answerTable.getColumns().add(deleteColumn);
        
        answerTable.setMaxHeight(160);
        
        multipleChoiceProperties.getChildren().add(answerTable);
    }

    public void update(MultipleChoiceObject nMChoice) {
        if(selectedMultipleChoice != null) {
            if(selectedMultipleChoice.equals(nMChoice)) {
                return;
            }
        }
        
        selectedMultipleChoice = nMChoice;
        
        update();
    }
    
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
            for(Answer a : selectedMultipleChoice.getAnswers()) {
                answerTable.getItems().add(new AnswerProperty(a));
            }
        }
    }
    
    public VBox getMultipleChoiceProperties() {
        return multipleChoiceProperties;
    }

    public class PropertyChangedHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            if(selectedMultipleChoice == null) {
                return;
            }
            
            TextField source = (TextField)e.getSource();
            
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
            
            update();
            parent.redraw();
        }
    }
    
    public class ButtonPressedHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            Button source = (Button)e.getSource();
            
            switch(source.getId()){
                case "newAnswer":
                    selectedMultipleChoice.addAnswer(new Answer("New Answer", true));
                    break;
                case "removeAnswer":
                    for(AnswerProperty a : answerTable.getItems()) {
                        if(a.getRemove()) {
                            selectedMultipleChoice.removeAnswer(a.getAnswerObject());
                        }
                    }
                    break; 
            }
            
            update();
            parent.redraw();
        }
    }
    
    public class SelectionPropertyChangedHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            if(selectedMultipleChoice == null) {
                return;
            }
            
            ComboBox<String> source = (ComboBox<String>)e.getSource();
            
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
            
            update();
            parent.redraw();
        }
    }
    
    public class BooleanPropertyChangedHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            if(selectedMultipleChoice == null) {
                return;
            }
            
            CheckBox source = (CheckBox)e.getSource();
            
            switch(source.getId()) {
                case "retry":
                    selectedMultipleChoice.setRetry(source.isSelected());
                    break;
                default:
                    break;
            }
            
            update();
            parent.redraw();
        }
    }
    
    public class AnswerChangedHandler implements EventHandler<CellEditEvent<AnswerProperty, String>> {
        @Override
        public void handle(CellEditEvent<AnswerProperty, String> t) {
            ((AnswerProperty) t.getTableView().getItems().get(
                t.getTablePosition().getRow())
                ).setAnswer(t.getNewValue());
            
            parent.redraw();
        }
    }
    
    public class CorrectChangedHandler implements EventHandler<CellEditEvent<AnswerProperty, Boolean>> {
        @Override
        public void handle(CellEditEvent<AnswerProperty, Boolean> t) {            
            ((AnswerProperty) t.getTableView().getItems().get(
                t.getTablePosition().getRow())
                ).setCorrect(t.getNewValue());
            
            parent.redraw();
        }
    }
    
    public class RemoveCallback implements Callback<TableColumn.CellDataFeatures<AnswerProperty, Boolean>, ObservableValue<Boolean>> {
        @Override
        public ObservableValue<Boolean> call(CellDataFeatures<AnswerProperty, Boolean> b) {
            return b.getValue().getRemoveProperty();
        }
    }
}

