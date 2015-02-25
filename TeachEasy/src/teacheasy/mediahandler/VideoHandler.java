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
import javafx.geometry.Pos;
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
import javafx.util.Duration;

/**
 * This class encapsulates the video handler,
 * and enables video playback and control.
 * 
 * @author Alistair Jewers
 * @version 1.0 24 Feb 2015
 */
public class VideoHandler {
    /* Reference to the group on which to draw videos */
    Group group;
    
    /* Debug */
    Button playButton;
    Button stopButton;
    
    /* Array list of the currently open videos */
    List<MediaView> videos;
    
    /* Array list of the currently open video frames */
    List<Group> videoFrames;
    
    /** Constructor Method */
    public VideoHandler(Group nGroup) {
        /* Set the group reference */
        this.group = nGroup;
        
        /* Debug */
        playButton = new Button("Play");
        playButton.setOnAction(new ButtonEventHandler());
        
        stopButton = new Button("Stop");
        stopButton.setOnAction(new ButtonEventHandler());
        
        /* Instantiate the array list of videos */
        videos = new ArrayList<MediaView>();
        videoFrames = new ArrayList<Group>();
    }
    
    /** Add a video frame to a group */
    public void createVideo(double x, double y, double width, String sourcefile) {
        /* Check that the file exists and is .mp4 */
        File file = new File(sourcefile);
        if(!file.exists()) {
            return;
        } else if(!file.getAbsolutePath().endsWith(".mp4")) {
            return;
        }
        
        /* Load the file as a media object */
        Media media = new Media(file.toURI().toString());
        
        /* Create a media player for the object */
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        
        /* Create a media view for the object */
        MediaView newVideo = new MediaView(mediaPlayer);
        
        /* Set up the media view */
        newVideo.setFitWidth(width);

        /* Create a group */
        Group videoGroup = new Group();
        videoGroup.relocate(x, y);
        videoGroup.getChildren().add(newVideo);
        videoGroup.setOnMouseEntered(new MouseEventHandler());
        videoGroup.setOnMouseExited(new MouseEventHandler());
        videoGroup.setId("" + videos.size());
        
        /* Add the media view to the list */
        videos.add(newVideo);
        
        /* Add the video frame to the list */
        videoFrames.add(videoGroup);
        
        /* Add it to the group */
        group.getChildren().add(videoGroup);
    }
    
    public void clearVideos() {
        /* Remove all the media views (videos) from the group */
        for(int i = 0; i < videoFrames.size(); i++) {
           group.getChildren().remove(videoFrames.get(i));
        }
        
        /* Clear the array lists */
        videoFrames.clear();
        videos.clear();
    }
    
    /** Helper function to set the correct button labels */
    private void setButtonLabels(int videoId) {
        /* Set the button text */
        if(videos.get(videoId).getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING) {
            playButton.setText("Pause");
        } else {
            playButton.setText("Play");
        }
        
        /* Set the button IDs */
        playButton.setId(videoId + "~play");
        stopButton.setId(videoId + "~stop");
    }
    
    /**
     * Mouse Event Handler Class
     */
    public class MouseEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            Group videoFrame = (Group) e.getSource();
            
            if(e.getEventType() == MouseEvent.MOUSE_ENTERED) {
                /* Add the play/pause button */
                setButtonLabels(Integer.parseInt(videoFrame.getId()));
                videoFrame.getChildren().add(playButton);
                
                /* Add the stop button */
                setButtonLabels(Integer.parseInt(videoFrame.getId()));
                stopButton.relocate(100, 0);
                videoFrame.getChildren().add(stopButton);
                
            } else if(e.getEventType() == MouseEvent.MOUSE_EXITED) {
                /* Remove the controls */
                videoFrame.getChildren().remove(playButton);
                videoFrame.getChildren().remove(stopButton);
            }
        }
    }
    
    /**
     * Button Event Handler Class
     */
    public class ButtonEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            /* Get the button that was pressed */
            Button button = (Button) e.getSource();
            
            /* Get the id of the button pressed */
            String id = button.getId();
            
            /* Separate the numerical ID from the type */
            String[] ids = id.split("~");
            int nId = Integer.parseInt(ids[0]);
            String type = ids[1];
            
            /* Act according to type */
            if(type.equals("play")) {
                if(videos.get(nId).getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING) {
                    videos.get(nId).getMediaPlayer().pause();
                    playButton.setText("Play");
                } else if(videos.get(nId).getMediaPlayer().getStatus() == MediaPlayer.Status.PAUSED ||
                          videos.get(nId).getMediaPlayer().getStatus() == MediaPlayer.Status.STOPPED ||
                          videos.get(nId).getMediaPlayer().getStatus() == MediaPlayer.Status.READY) {
                    videos.get(nId).getMediaPlayer().play();
                    playButton.setText("Pause");
                }
            } else if (type.equals("stop")) {
                videos.get(nId).getMediaPlayer().stop();
                playButton.setText("Play");
            }
        }
    }
}
