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
        Scene scene = new Scene(group, 1000, 500);
        
        /* Setup the window */
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        /* Define the file to use in the audio player */
        File file = new File("E:/Users/Alex/Music/Floorplan - Sanctified EP/01-floorplan-we_magnify_his_name.mp3");
        /* Create the audio player */
        audioHandler = new AudioHandler(group);
        audioHandler.createAudio(group, file, true);
        
        
        /* Show the window */
        primaryStage.show(); 
    }
    
    public static void main(String args[]) {
        launch();
    }
}
