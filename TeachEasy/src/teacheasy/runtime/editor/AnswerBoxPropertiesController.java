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
 * Encapsulates functionality relating to editor
 * functionality for Answer Box objects.
 * 
 * @author Alistair Jewers
 * @version 1.0 Apr 21 2015
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
        
        Button removeAnswerButton = new Button("Remove Selected");
        removeAnswerButton.setId("removeAnswer");
        removeAnswerButton.setOnAction(new ButtonPressedHandler());
        
        answerButtons = new HBox();
        answerButtons.getChildren().addAll(newAnswerButton, removeAnswerButton);
        
        answerBoxProperties.getChildren().add(answerButtons);
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
        
        TableColumn<AnswerProperty, Boolean> deleteColumn = new TableColumn<AnswerProperty, Boolean>("Remove");
        deleteColumn.setMinWidth(100);
        deleteColumn.setEditable(true);
        deleteColumn.setCellValueFactory(new RemoveCallback());
        deleteColumn.setCellFactory(CheckBoxTableCell.forTableColumn(deleteColumn));
        
        answerTable.getColumns().add(answerColumn);
        answerTable.getColumns().add(deleteColumn);
        
        answerBoxProperties.getChildren().add(answerTable);
    }

    public void update(AnswerBoxObject nAnswerBox) {
        if(selectedAnswerBox != null) {
            if(selectedAnswerBox.equals(nAnswerBox)) {
                return;
            }
        }
        
        selectedAnswerBox = nAnswerBox;
        
        update();
    }
    
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
            
            if(!selectedAnswerBox.isNumerical()) {
                upperLimitProperty.setDisable(true);
                lowerLimitProperty.setDisable(true);
                
                answerBoxProperties.getChildren().addAll(answerTable, answerButtons);
                
                for(Answer a : selectedAnswerBox.getAnswers()) {
                    answerTable.getItems().add(new AnswerProperty(a));
                }
            } else {
                upperLimitProperty.setDisable(false);
                lowerLimitProperty.setDisable(false);
                
                if(selectedAnswerBox.getAnswers().size() >= 2) {
                    lowerLimitProperty.setText(selectedAnswerBox.getAnswers().get(0).getText());
                    upperLimitProperty.setText(selectedAnswerBox.getAnswers().get(1).getText());
                }
            }
        }
    }
    
    public VBox getAnswerBoxProperties() {
        return answerBoxProperties;
    }

    public class PropertyChangedHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            if(selectedAnswerBox == null) {
                return;
            }
            
            TextField source = (TextField)e.getSource();
            
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
                default:
                    break;
            }
            
            update();
            parent.redraw();
        }
    }
    
    public class ButtonPressedHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            if(selectedAnswerBox == null) {
                return;
            }
            
            Button source = (Button)e.getSource();
            
            switch(source.getId()){
                case "newAnswer":
                    selectedAnswerBox.addAnswer(new Answer("New Answer", true));
                    break;
                case "removeAnswer":
                    for(AnswerProperty a : answerTable.getItems()) {
                        if(a.getRemove()) {
                            selectedAnswerBox.removeAnswer(a.getAnswerObject());
                        }
                    }
                    break; 
            }
            
            update();
            parent.redraw();
        }
    }
    
    public class BooleanPropertyChangedHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            if(selectedAnswerBox == null) {
                return;
            }
            
            CheckBox source = (CheckBox)e.getSource();
            
            switch(source.getId()) {
                case "retry":
                    selectedAnswerBox.setRetry(source.isSelected());
                    break;
                case "numerical":
                    selectedAnswerBox.setNumerical(source.isSelected());
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
    
    public class RemoveCallback implements Callback<TableColumn.CellDataFeatures<AnswerProperty, Boolean>, ObservableValue<Boolean>> {
        @Override
        public ObservableValue<Boolean> call(CellDataFeatures<AnswerProperty, Boolean> b) {
            return b.getValue().getRemoveProperty();
        }
    }
}

