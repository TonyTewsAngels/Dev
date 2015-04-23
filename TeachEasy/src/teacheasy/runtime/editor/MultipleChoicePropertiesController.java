package teacheasy.runtime.editor;

import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.StringConverter;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.CharacterStringConverter;
import teacheasy.data.MultipleChoiceObject;
import teacheasy.data.multichoice.Answer;
import teacheasy.render.Util;
import wavemedia.graphic.GraphicType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

/**
 * Encapsulates functionality relating to editor
 * functionality for Video objects.
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
    
    /* The  retry pproperty */
    
    /* The drop down list of orientations */
    private ComboBox<String> orientationProperty;
    
    /* The button for creating a new answer */
    private Button newAnswerButton;
    
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
        
        /*  */
        setupAnswerTable();
        
        multipleChoiceProperties.getChildren().add(answerTable);
        
        /* Set up the file select button */
        newAnswerButton = new Button("Add Answer");
        newAnswerButton.setId("newAnswer");
        newAnswerButton.setOnAction(new ButtonPressedHandler());
        multipleChoiceProperties.getChildren().add(newAnswerButton);
    }
    
    public void setupAnswerTable() {
        answerTable = new TableView<AnswerProperty>();
        answerTable.setEditable(true);
        
        TableColumn<AnswerProperty, String> answerColumn = new TableColumn<AnswerProperty, String>("Answer");
        answerColumn.setMinWidth(100);
        answerColumn.setEditable(true);
        answerColumn.setCellValueFactory(new PropertyValueFactory<AnswerProperty, String>("answer"));
        answerColumn.setCellFactory(TextFieldTableCell.<AnswerProperty>forTableColumn());
        answerColumn.setOnEditCommit(new answerChangedHandler());
        
        TableColumn<AnswerProperty, Boolean> correctColumn = new TableColumn<AnswerProperty, Boolean>("Correct");
        correctColumn.setMinWidth(100);
        correctColumn.setCellValueFactory(new PropertyValueFactory<AnswerProperty, Boolean>("correct"));
        correctColumn.setCellFactory(TextFieldTableCell.<AnswerProperty, Boolean>forTableColumn(new BooleanStringConverter()));
        correctColumn.setOnEditCommit(new correctChangedHandler());
        
        answerTable.getColumns().add(answerColumn);
        answerTable.getColumns().add(correctColumn);
    }

    public void update(MultipleChoiceObject nMChoice) {
        selectedMultipleChoice = nMChoice;
        
        update();
    }
    
    public void update() {
        if(selectedMultipleChoice == null) {
            xStartProperty.setText("");
            yStartProperty.setText("");
            answerTable.getItems().clear();
        } else {
            xStartProperty.setText(String.valueOf(selectedMultipleChoice.getXStart()));
            yStartProperty.setText(String.valueOf(selectedMultipleChoice.getYStart()));
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
            
            if(source.getId() == "newAnswer") {
                selectedMultipleChoice.addAnswer(new Answer("New Answer", true));
                update();
                parent.redraw();
            }
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
                    break;
                default:
                    break;
            }
            
            update();
            parent.redraw();
        }
    }
    
    public class answerChangedHandler implements EventHandler<CellEditEvent<AnswerProperty, String>> {
        @Override
        public void handle(CellEditEvent<AnswerProperty, String> t) {
            ((AnswerProperty) t.getTableView().getItems().get(
                t.getTablePosition().getRow())
                ).setAnswer(t.getNewValue());
            
            parent.redraw();
        }
    }
    
    public class correctChangedHandler implements EventHandler<CellEditEvent<AnswerProperty, Boolean>> {
        @Override
        public void handle(CellEditEvent<AnswerProperty, Boolean> t) {
            ((AnswerProperty) t.getTableView().getItems().get(
                t.getTablePosition().getRow())
                ).setCorrect(t.getNewValue());
            
            parent.redraw();
        }
    }
}

