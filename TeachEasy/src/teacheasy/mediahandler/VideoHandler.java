/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.mediahandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    /* Reference to the content pane on which to draw videos */
    Group group;
    Button button;
    List<MediaView> videos;
    
    /** Constructor Method */
    public VideoHandler(Group nGroup) {
        this.group = nGroup;
        button = new Button("Button");
        
        videos = new ArrayList<MediaView>();
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
        MediaView newVideo = new MediaView(mediaPlayer);
        
        newVideo.relocate(x, y);
        newVideo.setFitWidth(width);
        newVideo.setOnMouseEntered(new MouseEventHandler());
        newVideo.setOnMouseExited(new MouseEventHandler());
        
        videos.add(newVideo);
        group.getChildren().add(newVideo);
    }
    
    public void clearVideos() {
        for(int i = 0; i < videos.size(); i++) {
           group.getChildren().remove(videos.get(i));
        }
        
        videos.clear();
    }
    
    /**
     * Mouse Event Handler Class
     */
    public class MouseEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            if(e.getEventType() == MouseEvent.MOUSE_ENTERED) {
                System.out.println("Mouse entered");
            } else if(e.getEventType() == MouseEvent.MOUSE_EXITED) {
                System.out.println("Mouse exited");
            }
        }
    }
}
