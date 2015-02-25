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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaErrorEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;
import javafx.stage.Stage;
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
    private Group group;
    
    /* Controls */
    private HBox controls;
    private Button playButton;
    private Button stopButton;
    private Slider scanBar;
    private ScanListener scanBarListener;
    private VideoListener videoListener;
    private Button fullscreenButton;
    
    
    /* Array list of the currently open videos */
    private List<MediaView> videos;
    
    /* Array list of the currently open video frames */
    private List<GridPane> videoFrames;
    
    /** Constructor Method */
    public VideoHandler(Group nGroup) {
        /* Set the group reference */
        this.group = nGroup;
        
        /* Play/Pause Control */
        playButton = new Button("Play");
        playButton.setOnAction(new ButtonEventHandler());
        
        /* Stop Control */
        stopButton = new Button("Stop");
        stopButton.setOnAction(new ButtonEventHandler());
        
        /* Fullscreen Control */
        fullscreenButton = new Button("[ ]");
        fullscreenButton.setOnAction(new ButtonEventHandler());
        
        /* Scan Control */
        scanBar = new Slider();
        scanBarListener = new ScanListener(0);
        videoListener = new VideoListener(0);
        
        /* Setup Scan Control */
        scanBar.setMin(0);
        scanBar.setMax(100);
        scanBar.setValue(0);
        scanBar.setShowTickLabels(false);
        scanBar.setShowTickMarks(false);
        scanBar.valueProperty().addListener(scanBarListener);
        scanBar.setOnMousePressed(new ScanMouseHandler());
        scanBar.setOnMouseReleased(new ScanMouseHandler());
        
        /* Instantiate the array list of videos */
        videos = new ArrayList<MediaView>();
        videoFrames = new ArrayList<GridPane>();
    }
    
    /** Add a video frame to a group */
    public void createVideo(double x, double y, double width, String sourcefile, boolean autoPlay, boolean loop) {
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
        
        /* Set up the media player */
        mediaPlayer.setAutoPlay(autoPlay);
        mediaPlayer.currentTimeProperty().addListener(videoListener);
        if(loop) {
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        }
        
        /* Create a media view for the object */
        MediaView newVideo = new MediaView(mediaPlayer);
        
        /* Set up the media view */
        newVideo.setFitWidth(width);

        /* Create a group */
        GridPane videoGroup = new GridPane();
        videoGroup.relocate(x, y);
        videoGroup.add(newVideo, 0, 0);
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
    
    /** Clear all the videos currently being handled */
    public void clearVideos() {
        /* Remove all the media views (videos) from the group */
        for(int i = 0; i < videoFrames.size(); i++) {
           group.getChildren().remove(videoFrames.get(i));
        }
        
        /* Clear the array lists */
        videoFrames.clear();
        videos.clear();
    }
    
    /** Set the correct button labels and IDs */
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
        fullscreenButton.setId(videoId+"~fullscreen");
        scanBar.setId(videoId+"~scan");
    }
    
    /** Adds the controls to a video frame */
    private void addControls(GridPane videoFrame) {
        /* Get the ID of the video frame */
        int id = Integer.parseInt(videoFrame.getId());
        
        /* Set up the control bar */
        controls = new HBox();
        controls.setAlignment(Pos.BOTTOM_CENTER);
        
        /* Set the button labels */
        setButtonLabels(id);
        
        /* Set the scan listener target */
        scanBarListener.setId(id);
        videoListener.setId(id);
        
        /* Adjust the scan location */
        setScan(id);
        
        /* Add the buttons to the control bar */
        controls.getChildren().addAll(playButton, stopButton, scanBar, fullscreenButton);
        controls.setPrefWidth(videoFrame.getWidth());
        
        /* Add the control bar to the video frame */
        videoFrame.getChildren().add(controls);
    }
    
    /** Removes the controls from a video frame */
    private void removeControls(GridPane videoFrame) {
        /* Remove the controls */
        videoFrame.getChildren().remove(controls);
    }
    
    /** Scan to a location in the video */
    private void scan(int videoId, double percent) {
        /* Get the duration of the clip */
        double duration = videos.get(videoId).getMediaPlayer().getMedia().getDuration().toSeconds();
        
        /* Get the new position using the percentage */
        double newPosition = duration * (percent/100);
        
        /* Set the new position (milliseconds) */
        videos.get(videoId).getMediaPlayer().seek(new Duration(newPosition*1000));
    }
    
    /** Set the scan bar to match the video position */
    private void setScan(int videoId) {
        /* Get the duration of the clip */
        double duration = videos.get(videoId).getMediaPlayer().getMedia().getDuration().toSeconds();
        
        /* Get the current position in the clip */
        double current = videos.get(videoId).getMediaPlayer().getCurrentTime().toSeconds();
        
        /* Calculate the percentage */
        double percentage = (current/duration) * 100;
        
        /* Set the scan bar value */
        scanBar.setValue(percentage);
    }
    
    /** Make the a video fullscreen */
    public void fullscreen(int videoId) {
        GridPane videoFrame = videoFrames.get(videoId);
        MediaView video = videos.get(videoId);
        
        group.getChildren().remove(videoFrame);
        
        Scene scene = new Scene(videoFrame);
        scene.setFill(Color.BLACK);
        videoFrame.relocate(0, 0);
        video.setFitWidth(Screen.getPrimary().getBounds().getMaxX());
        
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("");
        stage.setFullScreen(true);
        stage.show();
    }
    
    /**
     * Mouse Event Handler Class
     */
    public class MouseEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            /* Get the video frame by casting the event source */
            GridPane videoFrame = (GridPane) e.getSource();
            
            if(e.getEventType() == MouseEvent.MOUSE_ENTERED) {
                /* Add the controls */
                addControls(videoFrame);
            } else if(e.getEventType() == MouseEvent.MOUSE_EXITED) {
                /* Remove the controls */
                removeControls(videoFrame);
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
                    /* If the video was playing, pause it and change the button label to play */
                    videos.get(nId).getMediaPlayer().pause();
                    playButton.setText("Play");
                } else if(videos.get(nId).getMediaPlayer().getStatus() == MediaPlayer.Status.PAUSED ||
                          videos.get(nId).getMediaPlayer().getStatus() == MediaPlayer.Status.STOPPED ||
                          videos.get(nId).getMediaPlayer().getStatus() == MediaPlayer.Status.READY) {
                    /* If the video wasn't playing, play it and change the button label to pause */
                    videos.get(nId).getMediaPlayer().play();
                    playButton.setText("Pause");
                }
            } else if (type.equals("stop")) {
                /* Stop the video and set the button label to play */
                videos.get(nId).getMediaPlayer().stop();
                playButton.setText("Play");
            } else if (type.equals("fullscreen")) {
                fullscreen(nId);
            }
        }
    }
    
    /**
     * Scan event handler class
     */
    public class ScanListener implements ChangeListener<Number> {
        /* 
         * Class level variables to hold the ID of the video affect
         * and whether or not the scan bar is enabled
         */
        private int videoId; 
        private boolean enable;
        
        /** Constructor */
        public ScanListener(int nId) {
            videoId = nId;
            enable = false;
        }
        
        /** Set the id of the video to affect */
        public void setId(int nId) {
            this.videoId = nId;
        }
        
        /** Enable or disable the listener */
        public void setEnabled(boolean nEnable) {
            enable = nEnable;
        }
        
        /** When the value changes update the position in the video */
        @Override
        public void changed(ObservableValue<? extends Number> ov,
                            Number old_val, Number new_val) {
            if(enable) {
                scan(videoId, new_val.doubleValue());
            }
        }
    }
    
    /**
     * Scan mouse click handler class
     */
    public class ScanMouseHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            if(e.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                /* Enable scanning */
                scanBarListener.setEnabled(true);
            } else if(e.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
                /* Disable scanning */
                scanBarListener.setEnabled(false);
            }
        }
    }
    
    /**
     * Video Position Listener
     */
    public class VideoListener implements ChangeListener<Duration> {
        /* ID of the video to listen to */
        private int videoId;
        
        /** Constructor */
        public VideoListener(int nId) {
            this.videoId = nId;
        }
        
        /** Set the ID of the video to listen to */
        public void setId(int nId) {
            this.videoId = nId;
        }
        
        /** When the postion in the video changes, update the scan bar */
        @Override
        public void changed(ObservableValue<? extends Duration> arg0,
                            Duration arg1, Duration arg2) {
            setScan(videoId);
        }
    }
}
