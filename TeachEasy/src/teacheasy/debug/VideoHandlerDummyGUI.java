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

            videoHandler = new VideoHandler(group);
            videoHandler.createVideo(5, 5, 490, "C:/SWEngVideo/avengers-featurehp.mp4");
            
            /* Show the window */
            primaryStage.show(); 
        }
        
        public static void main(String args[]) {
            launch();
        }
    }

