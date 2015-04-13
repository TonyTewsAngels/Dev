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
        
        //File sourceFile = new File("E:/Users/Alex/Music/Daniel Avery - Drone Logic/03 Drone Logic.mp3");
        String sourceFile = new String("H:/3RD YEAR/SWEng/aactest.AAC");
        
    
        //audioHandler.createAudio(50, 50, 200, sourceFile, false, true, true, false);
        //audioHandler.createAudio(50, 50, 200, "http://www.tonycuffe.com/mp3/tail%20toddle.mp3", false, true, true, false);
        
        audioHandler.createAudio(50, 50, 200, sourceFile, false, true, true, false);
        
        /* Show the window */
        primaryStage.show(); 
    }
    
    public static void main(String args[]) {
        launch();
    }
}

