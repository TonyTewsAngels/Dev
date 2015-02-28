package teacheasy.debug;

import java.io.File;

import teacheasy.debug.GeneralDummyGUI.buttonEventHandler;
import teacheasy.mediahandler.AudioHandler;

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
        Scene scene = new Scene(group, 450, 150);
        
        /* Setup the window */
        primaryStage.setTitle("Audio Test Window");
        primaryStage.setScene(scene);
        /* Define the file to use in the audio player */
        File file = new File("E:/Users/Alex/Music/Daniel Avery - Drone Logic/03 Drone Logic.mp3");
        /* Create the audio player */
        audioHandler = new AudioHandler(group);
        audioHandler.createAudio(group, file, false);
        
        
        /* Show the window */
        primaryStage.show(); 
    }
    
    public static void main(String args[]) {
        launch();
    }
}
