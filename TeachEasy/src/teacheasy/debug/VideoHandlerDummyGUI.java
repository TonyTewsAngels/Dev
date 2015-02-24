package teacheasy.debug;

import teacheasy.mediahandler.VideoHandler;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VideoHandlerDummyGUI extends Application {
    
        VideoHandler videoHandler;
        
        @Override
        public void start(Stage primaryStage) {       
            /* Instantiate the scene and group */
            Group group = new Group();
            Scene scene = new Scene(group, 500, 500);
            
            /* Setup the window */
            primaryStage.setTitle("Hello World!");
            primaryStage.setScene(scene);
            
            videoHandler = new VideoHandler();
            videoHandler.createVideo(group, 100, 100, "null");
            
            /* Show the window */
            primaryStage.show(); 
        }
        
        public static void main(String args[]) {
            launch();
        }
    }

