package teacheasy.debug;

import teacheasy.mediahandler.VideoHandler;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class VideoHandlerDummyGUI extends Application {
    
    VideoHandler videoHandler;
    Button button;
    
    @Override
    public void start(Stage primaryStage) {       
        /* Instantiate the scene and group */
        Group group = new Group();
        Scene scene = new Scene(group, 500, 500);
        
        /* Setup the window */
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        
        
        button = new Button("Button");
        button.relocate(600, 600);
        button.setId("Button1");
        button.setOnAction(new ButtonEventHandler());
        
        group.getChildren().add(button);

        /* Instantiate the video handler */
        videoHandler = new VideoHandler(group);
        
        /* Use the video handler to create a video */
        videoHandler.createVideo(5, 5, 400, "M:/Year 3/SWENG/Videos/avengers-featurehp.mp4");
        
        /* Use the video handler to create a video */
        videoHandler.createVideo(5, 505, 400, "M:/Year 3/SWENG/Videos/avengers-featurehp.mp4");
        
        /* Show the window */
        primaryStage.show(); 
    }
    
    public static void main(String args[]) {
        launch();
    }
    
    /**
     * Button Event Handler Class
     */
    public class ButtonEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            /* Cast the source of the event to a button */
            Button button = (Button)e.getSource();
            
            /* Act based on the ID of the button */
            if(button.getId().equals("Button1")) {
                videoHandler.clearVideos();
                videoHandler.createVideo(300, 300, 400, "M:/Year 3/SWENG/Videos/prometheus-featureukFhp.mp4");
            }
        }
    }
}

