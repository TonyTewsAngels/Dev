package teacheasy.runtime.editor;

import teacheasy.data.Lesson;
import teacheasy.data.lessondata.LessonGradeSettings;
import teacheasy.data.lessondata.LessonInfo;
import teacheasy.main.TeachEasyClient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LessonInfoWindow {
    private Stage stage;
    private Scene scene;
    private VBox box;

    private TextArea description;
    
    private Label titleLabel;
    private Label authorLabel;
    private Label versionLabel;
    private Label passMarkLabel;
    private Label totalMarkLabel;
    private Label passMessageLabel;
    private Label failMessageLabel;
    
    private TextField titleField;
    private TextField authorField;
    private TextField versionField;
    private TextField passMarkField;
    private TextField passMessageField;
    private TextField failMessageField;
    
    private GridPane grid;

    private Button doneBtn;
    private Button cancelBtn;
    private HBox buttonBox;
    
    private LessonInfo info;
    private LessonGradeSettings gradeSettings;
    
    private TeachEasyClient parent;
    
    public LessonInfoWindow(Lesson lesson, TeachEasyClient nParent) {
        /* Initialise the info reference */
        info = lesson.lessonInfo;
        info.setTotalMarks(lesson.getTotalMarks());
        gradeSettings = lesson.gradeSettings;
        parent = nParent;
        
        /* Initialise the stage */
        stage = new Stage();
        stage.setWidth(420);
        stage.setHeight(440);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Lesson Properties");
        
        /* Initialise the components */
        box = new VBox(10);
        box.setPadding(new Insets(10));
        
        grid = new GridPane();
        grid.setVgap(10);

        buttonBox = new HBox();
        buttonBox.setSpacing(25);
        doneBtn = new Button("Save");
        doneBtn.setId("done");
        cancelBtn = new Button("Cancel");
        cancelBtn.setId("cancel");
        
        titleLabel = new Label("Title: ");
        authorLabel = new Label("Author: ");
        versionLabel = new Label("Version: ");
        passMarkLabel = new Label("Pass Mark: ");
        totalMarkLabel = new Label(" / " + info.getTotalMarks());
        passMessageLabel = new Label("Pass Message: ");
        failMessageLabel = new Label("Fail Message: ");
        
        titleField = new TextField(info.getLessonName());
        authorField = new TextField(info.getAuthor());
        versionField = new TextField(info.getVersion());
        passMarkField = new TextField("" + gradeSettings.getPassBoundary());
        passMessageField = new TextField(gradeSettings.getPassMessage());
        failMessageField = new TextField(gradeSettings.getFailMessage());
        
        description = new TextArea();
        description.setText(info.getComment());
        
        passMarkField.setMaxWidth(65);
        
        grid.add(titleLabel, 0, 0);
        grid.add(titleField, 1, 0);
        
        grid.add(authorLabel, 0, 1);
        grid.add(authorField, 1, 1);
        
        grid.add(versionLabel, 0, 2);
        grid.add(versionField, 1, 2);
        
        grid.add(passMarkLabel, 0, 3);
        grid.add(passMarkField, 1, 3);
        grid.add(totalMarkLabel, 2, 3);
        
        grid.add(passMessageLabel, 0, 4);
        grid.add(passMessageField, 1, 4);
        
        grid.add(failMessageLabel, 0, 5);
        grid.add(failMessageField, 1, 5);
        
        /* Set up the button row */
        buttonBox.getChildren().addAll(doneBtn, cancelBtn);
        buttonBox.setAlignment(Pos.CENTER);
        
        /* Set up the content */
        box.getChildren().addAll(grid, description, buttonBox);
        box.setAlignment(Pos.CENTER);
        
        /* Set the buton actions */
        doneBtn.setOnAction(new ButtonHandler());
        cancelBtn.setOnAction(new ButtonHandler());
        
        /* Create the scene and window */
        scene = new Scene(box);
        stage.setScene(scene);
        stage.show();
    }
    
    public void updateInfo() {
        String title = titleField.getText();
        String author = authorField.getText();
        String version = versionField.getText();
        String comment = description.getText();
        String passMarkStr = passMarkField.getText();
        int passMark = 0;
        String passMessage = passMessageField.getText();
        String failMessage = failMessageField.getText();

        if(title.length() > 20) {
            title = new String(title.substring(0, 20));
        }
        
        if(author.length() > 20) {
            author = new String(author.substring(0, 20));
        }
        
        if(version.length() > 10) {
            version = new String(version.substring(0, 10));
        }
        
        if(comment.length() > 150) {
            comment = new String(comment.substring(0, 150));
        }
        
        if(passMessage.length() > 50) {
            passMessage = new String(passMessage.substring(0, 20));
        }
        
        if(failMessage.length() > 50) {
            failMessage = new String(failMessage.substring(0, 20));
        }
        
        try{
            passMark = Integer.parseInt(passMarkStr);
        } catch(NumberFormatException nfe) {
            passMark = info.getTotalMarks();
        }
        
        if(passMark > info.getTotalMarks()) {
            passMark = info.getTotalMarks();
        }
        
        info.setLessonName(title);
        info.setAuthor(author);
        info.setVersion(version);
        info.setComment(comment);
        gradeSettings.setPassBoundary(passMark);
        gradeSettings.setPassMessage(passMessage);
        gradeSettings.setFailMessage(failMessage);
    }
    
    public class ButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            Button source = (Button)e.getSource();
            
            switch(source.getId()) {
                case "cancel":
                    stage.close();
                    break;
                case "done":
                    updateInfo();                 
                    stage.close();
                    parent.updateUI();
                    break;
            }
        }
    }
}
