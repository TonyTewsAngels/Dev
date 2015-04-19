/*
 * Sam Raeburn
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.test;

import teacheasy.mediahandler.VideoHandler;
import teacheasy.mediahandler.video.Video;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 * This class draws a video on the screen using the video
 * handler. I have used this to manually test the handler.
 * Any changes that need to be made are outlined
 * in the test report on the google drive
 * @author amj523
 * @version 1.0 20 Feb 2015
 */

public class VideoHandlerTest extends Application {
    /* Video Handler */
    VideoHandler videoHandler;
    
    @Override
    public void start(Stage primaryStage) {       
        /* Instantiate the scene and group */
        Group group = new Group();
        Scene scene = new Scene(group, 800, 800);
        
        /* Setup the window */
        primaryStage.setTitle("Video Handler Test");
        primaryStage.setScene(scene);

        /* Instantiate the video handler */
        videoHandler = new VideoHandler(group, null);
        
        /* Use the video handler to create a video */
        videoHandler.createVideo(5, 5, 100, "M:/Year 3/SWENG/Videos/avengers-featurehp.mp4", true, true);
           
        /* Show the window */
        primaryStage.show(); 
    }
    
    public static void main(String args[]) {
        launch();
    }
}

