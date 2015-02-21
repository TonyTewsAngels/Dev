/*
 * Alex Cash
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.data;

import java.io.File;

import javafx.application.Application;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.*;
import javafx.stage.Stage;

/**
 * This class is the handler for audio
 * 
 * @author Alex Cash
 * @version 1.0 19 Feb 2015
 */
public class AudioHandler extends Application {

	@Override
	public void start(Stage firstStage) throws Exception {
		
		/* Instantiate the scene and group */
        Group group = new Group();
        Scene scene = new Scene(group, 550, 350);
        
        /* Setup the window */
        /* Ensure there is a mildly funny title */
        firstStage.setTitle("Audio for ya motha");
        firstStage.setScene(scene);		
        firstStage.show();
        
        /* Give the file URI, make sure to escape backslashes */
        
        File file = new File("C:\\Users\\Public\\Music\\Sample Music\\Kalimba.mp3");
        /* Create new Media instance using the file declared above */
        Media audio = new Media(file.toURI().toURL().toString());
        /* Create MediaPlayer */
        MediaPlayer player = new MediaPlayer(audio);
        player.play();

        MediaView mediaView = new MediaView(player);
        ((Group) scene.getRoot()).getChildren().add(mediaView);
        
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}