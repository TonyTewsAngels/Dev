/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.mediahandler;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaErrorEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer.Status;

/**
 * This class encapsulates the video handler,
 * and enables video playback and control.
 * 
 * @author Alistair Jewers
 * @version 1.0 24 Feb 2015
 */
public class VideoHandler {
    Group group;
    GridPane grid;
    Button button;
    
    /** Constructor Method */
    public VideoHandler(Group nGroup) {
        this.group = nGroup;
        button = new Button("Button");
    }
    
    /** Add a video frame to a group */
    public void createVideo(double x, double y, double width, String sourcefile) {
        File file = new File(sourcefile);
        if(!file.exists()) {
            return;
        }
        
        Media media = new Media(file.toURI().toString());
        
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        
        mediaPlayer.setAutoPlay(true);
        MediaView mediaView = new MediaView(mediaPlayer);
        
        mediaView.relocate(x, y);
        mediaView.setFitWidth(width);
        mediaView.setOnMouseEntered(new MouseEventHandler());
        mediaView.setOnMouseExited(new MouseEventHandler());
        
        group.getChildren().add(mediaView);
    }
    
    /**
     * Menu Event Handler Class
     */
    public class MouseEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            if(e.getEventType() == MouseEvent.MOUSE_ENTERED) {
                System.out.println("Mouse entered");
                group.getChildren().add(button);
            } else if(e.getEventType() == MouseEvent.MOUSE_EXITED) {
                System.out.println("Mouse exited");
                group.getChildren().remove(button);
            }
        }
    }
}
