package teacheasy.debug;

import java.io.File;

import teacheasy.debug.ImageHandlerDummyGUI.buttonEventHandler;
import teacheasy.mediahandler.AudioHandler;
import teacheasy.mediahandler.audio.Audio;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class AudioHandlerDummyGUI extends Application {
    
    AudioHandler audioHandler;
    
    @Override
    public void start(Stage primaryStage) {       
        /* Instantiate the scene and group */
        Group group = new Group();
        Scene scene = new Scene(group, 500, 500);
        
        /* Setup the window */
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        
        
        /* Create a button */
        Button btn = new Button();
        
        /* Setup the button */
        btn.setText("A Button");
        btn.setId("Button1");
        btn.relocate(200.0, 50.0);
        
        /* Set the button to use the button event handler */
        btn.setOnAction(new buttonEventHandler());
        
        /* Add the button to the group */
        group.getChildren().addAll(btn);
        

        /* Instantiate the audio handler */
        audioHandler = new AudioHandler(group);

        String sourceFile = new String("http://www.tonycuffe.com/mp3/tail%20toddle.mp3");

        //audioHandler.createAudio(50, 50, 10, sourceFile, false, true, false, true);
        audioHandler.createAudio(50, 200, 200, sourceFile, false, true, true, false);
        //audioHandler.createAudio(50, 200, 500, sourceFile, true, false, true, true);
        //audioHandler.createAudio(50, 400, 200, sourceFile, false, true, false, true);
        
        

        
        /* Show the window */
        primaryStage.show(); 
    }
    
    /**
     * Event Handler Class
     */
    public class buttonEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
        	System.out.println("xEnd is:" +audioHandler.getAudioXEnd(0));
            }
        }
      
    
    public static void main(String args[]) {
        launch();
    }
}

