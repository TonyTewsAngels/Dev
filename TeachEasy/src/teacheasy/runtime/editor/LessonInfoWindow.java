package teacheasy.runtime.editor;

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
    
    private TextField titleField;
    private TextField authorField;
    private TextField versionField;
    
    private GridPane grid;

    private Button doneBtn;
    private Button cancelBtn;
    private HBox buttonBox;
    
    private LessonInfo info;
    
    private TeachEasyClient parent;
    
    public LessonInfoWindow(LessonInfo nInfo, TeachEasyClient nParent) {
        /* Initialise the info reference */
        info = nInfo;
        parent = nParent;
        
        /* Initialise the stage */
        stage = new Stage();
        stage.setWidth(420);
        stage.setHeight(320);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        
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
        
        titleField = new TextField(info.getLessonName());
        authorField = new TextField(info.getAuthor());
        versionField = new TextField(info.getVersion());
        
        description = new TextArea();
        description.setText(info.getComment());
        
        grid.add(titleLabel, 0, 0);
        grid.add(titleField, 1, 0);
        
        grid.add(authorLabel, 0, 1);
        grid.add(authorField, 1, 1);
        
        grid.add(versionLabel, 0, 2);
        grid.add(versionField, 1, 2);
        
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
        
        info.setLessonName(title);
        info.setAuthor(author);
        info.setVersion(version);
        info.setComment(comment);
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
