package teacheasy.debug;

import java.io.File;


import teacheasy.debug.GeneralDummyGUI.buttonEventHandler;
import teacheasy.mediahandler.*;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class EventHandlerTestDummyGUI extends Application {
    AudioHandler audioHandler;
    
    @Override
    public void start(Stage primaryStage) {       
        /* Instantiate the scene and group */
        Group group = new Group();
        Scene scene = new Scene(group, 800, 900);
        
        /* Setup the window */
        primaryStage.setTitle("Audio Test Window");
        primaryStage.setScene(scene);
        /* Define the file to use in the audio player */
        File file = new File("H:\\3RD YEAR\\SWEng\\SampleAudioFiles\\tail toddle.mp3");
        /* Create the audio player */
        audioHandler = new AudioHandler(group);
        audioHandler.createAudio(5, 5, 600, file, false, true, false);
        audioHandler.createAudio(5, 300, 400, file, false, true, false);
        audioHandler.createAudio(5, 500, 400, file, false, true, false);
        audioHandler.createAudio(5, 700, 400, file, false, true, false);
        audioHandler.createAudio(5, 900, 400, file, false, true, false);
        
        
        /* Show the window */
        primaryStage.show(); 
    }
    
    public static void main(String args[]) {
        launch();
    }
}
