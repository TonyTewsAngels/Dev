package teacheasy.debug;

import java.io.File;

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
        

        /* Instantiate the audio handler */
        audioHandler = new AudioHandler(group);

        String sourceFile = new String("http://www.tonycuffe.com/mp3/tail%20toddle.mp3");

        audioHandler.createAudio(50, 50, 10, sourceFile, false, true, false, true);
        audioHandler.createAudio(50, 200, 500, sourceFile, false, true, true, false);
        //audioHandler.createAudio(50, 200, 500, sourceFile, true, false, true, true);
        //audioHandler.createAudio(50, 400, 200, sourceFile, false, true, false, true);

        
        /* Show the window */
        primaryStage.show(); 
    }
    
    public static void main(String args[]) {
        launch();
    }
}

