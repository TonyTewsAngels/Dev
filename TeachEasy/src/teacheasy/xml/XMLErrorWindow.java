package teacheasy.xml;

import java.util.ArrayList;

import teacheasy.xml.util.XMLNotification;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class XMLErrorWindow {
    private Stage stage;
    private ArrayList<XMLNotification> errorList;
    
    public XMLErrorWindow(ArrayList<XMLNotification> errorList) {
        this.errorList = errorList;
        
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Lesson Loading Results");
        
        VBox contentBox = new VBox(15);
        
        Label title = new Label("Warnings found whilst parsing xml.");
        
        HBox btnRow = new HBox(10);
        Label cont = new Label("Do you wish to continue?");
        Button yesBtn = new Button("Yes");
        Button noBtn = new Button("No");
        btnRow.getChildren().addAll(cont, yesBtn, noBtn);
        
        contentBox.getChildren().addAll(title, btnRow);
        
        stage.setScene(new Scene(contentBox));
        stage.show();
    }
}
