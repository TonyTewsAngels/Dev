/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.mediahandler.video;

import java.io.File;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.*;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This class encapsulates an individual video
 * within the video handler.
 * 
 * @author Alistair Jewers
 * @version 1.0 24 Feb 2015
 */
public class Video {
    /** Reference to the group on which to draw videos. */
    private Group group;
    
    /** Media object containing the video. */
    private Media media;
    
    /** Media player object to control playback. */
    private MediaPlayer mediaPlayer;
    
    /** Media view object to display the video. */
    private MediaView video;
    
    /** Grid layout to contain the video and the controls. */
    private GridPane videoFrame;
    
    /* Controls */
    private HBox controls;
    private Button playButton;
    private Button stopButton;
    private Slider scanBar;
    private ScanListener scanBarListener;
    private VideoListener videoListener;
    private Button fullscreenButton;
    private Button volumeButton;
    private Slider volumeSlider;
    private VolumeListener volumeListener;
    
    /** Fullscreen stage. */
    private Stage fsStage;
    
    /** Fullscreen information, needed when exiting fullscreen. */
    private FullscreenInfo fsInfo;
    
    /**
     * Constructs the video.
     * 
     * @param nGroup - The group this video goes on.
     * 
     * @param x - The x coordinate of the top left of the video 
     *            relative to the group's origin.
     *            
     * @param y - The y coordinate of the top left of the video 
     *            relative to the group's origin.
     *            
     * @param width - The width of the video in pixels
     * 
     * @param sourcefile - The absolute path of the video file
     * 
     * @param autoPlay - If true the video plays immediately
     * 
     * @param loop - If true the video loops to the beggining when it ends
     */
    public Video(Group nGroup, float x, float y, float width, String sourcefile, boolean autoPlay, boolean loop) {
        /* Set the group reference */
        this.group = nGroup;
        
        /* Play/Pause Control */
        playButton = new Button("Play");
        playButton.setId("play");
        playButton.setOnAction(new ButtonEventHandler());
        
        /* Stop Control */
        stopButton = new Button("Stop");
        stopButton.setId("stop");
        stopButton.setOnAction(new ButtonEventHandler());
        
        /* Fullscreen Control */
        fullscreenButton = new Button("[ ]");
        fullscreenButton.setId("fullscreen");
        fullscreenButton.setOnAction(new ButtonEventHandler());
        
        /* Volume Control */
        volumeButton = new Button("Vol");
        volumeButton.setId("volume");
        volumeButton.setOnAction(new ButtonEventHandler());
        volumeSlider = new Slider();
        volumeListener = new VolumeListener();
        
        /* Set up volume control */
        volumeSlider.setMin(0);
        volumeSlider.setMax(1);
        volumeSlider.setValue(1);
        volumeSlider.setMaxHeight(50);
        volumeSlider.setOrientation(Orientation.VERTICAL);
        volumeSlider.setShowTickLabels(false);
        volumeSlider.setShowTickMarks(false);
        volumeSlider.valueProperty().addListener(volumeListener);
        
        /* Scan Control */
        scanBar = new Slider();
        scanBarListener = new ScanListener();
        videoListener = new VideoListener();
        
        /* Setup Scan Control */
        scanBar.setMin(0);
        scanBar.setMax(100);
        scanBar.setValue(0);
        scanBar.setPrefWidth(1000);
        scanBar.setShowTickLabels(false);
        scanBar.setShowTickMarks(false);
        scanBar.valueProperty().addListener(scanBarListener);
        scanBar.setOnMousePressed(new ScanMouseHandler());
        scanBar.setOnMouseReleased(new ScanMouseHandler());
        
        /* Check that the file exists and is .mp4 */
        File file = new File(sourcefile);
        if(!file.exists()) {
            return;
        } else if(!file.getAbsolutePath().endsWith(".mp4")) {
            return;
        }
        
        /* Load the file as a media object */
        media = new Media(file.toURI().toString());
        
        /* Create a media player for the object */
        mediaPlayer = new MediaPlayer(media);
        
        /* Set up the media player */
        mediaPlayer.setAutoPlay(autoPlay);
        mediaPlayer.currentTimeProperty().addListener(videoListener);
        mediaPlayer.statusProperty().addListener(new PlayerStatusListener());
        if(loop) {
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        }
        
        /* Create a media view for the object */
        video = new MediaView(mediaPlayer);
        
        /* Set up the media view */
        video.setFitWidth(width);

        /* Create a grid */
        videoFrame = new GridPane();
        videoFrame.relocate(x, y);
        videoFrame.add(video, 0, 0);
        videoFrame.setOnMouseEntered(new MouseEventHandler());
        videoFrame.setOnMouseExited(new MouseEventHandler());
        
        /* Init the fullscreen info */
        fsInfo = new FullscreenInfo(x, y, width, false);
        
        /* Add the video frame to the group */
        group.getChildren().add(videoFrame);
    }
    
    /** Programmatically plays this video */
    public void play() {
        if(mediaPlayer != null) {
            mediaPlayer.play();
        }
    }
    
    /** Programmatically pauses this video */
    public void pause() {
        if(mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }
    
    /** Programmatically stops this video */
    public void stop() {
        if(mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
    
    /** Programmatically disposes of this video */
    public void dispose() {
        if(mediaPlayer != null) {
            mediaPlayer.dispose();
        }
    }
    
    /** Programmatically resizes this video */
    public void resize(float nWidth) {
        video.setFitWidth(nWidth);
    }
    
    /** Programmatically relocates this video */
    public void relocate(float x, float y) {
        video.relocate(x, y);
    }
    
    /** Programmatically sets this videos visibility */
    public void setVisible(boolean visible) {
        video.setVisible(visible);
    }
    
    /** Adds the controls to a video frame */
    private void addControls() {
        /* Set up the control bar */
        controls = new HBox();
        controls.setAlignment(Pos.BOTTOM_CENTER);
        controls.setSpacing(10);
        
        /* Set the button labels */
        setButtonLabels();
        
        /* Adjust the scan location */
        setScan();
        
        /* Add the buttons to the control bar */
        controls.getChildren().addAll(playButton, stopButton, scanBar, fullscreenButton, volumeButton);
        controls.setPrefWidth(videoFrame.getWidth());
        
        /* Set the volume slider value */
        volumeSlider.setValue(mediaPlayer.getVolume());
        
        /* Add the control bar to the video frame */
        videoFrame.getChildren().add(controls);
    }
    
    /** Removes the controls from a video frame */
    private void removeControls() {
        /* Remove the controls */
        videoFrame.getChildren().remove(controls);
    }
    
    /** Set the correct button labels */
    private void setButtonLabels() {
        /* Set the button text */
        if(mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            playButton.setText("Pause");
        } else {
            playButton.setText("Play");
        }
    }
    
    /** 
     * Scan to a position in the video 
     * 
     * @param percent - The new position in the video as a 
     *                  percentage of the video length.
     */
    public void scan(double percent) {
        /* Get the duration of the clip */
        double duration = media.getDuration().toSeconds();
        
        /* Get the new position using the percentage */
        double newPosition = duration * (percent/100);
        
        /* Set the new position (milliseconds) */
        mediaPlayer.seek(new Duration(newPosition*1000));
    }
    
    /** Set the scan bar to match the video position */
    private void setScan() {
        /* Get the duration of the clip */
        double duration = media.getDuration().toSeconds();
        
        /* Get the current position in the clip */
        double current =  mediaPlayer.getCurrentTime().toSeconds();
        
        /* Calculate the percentage */
        double percentage = (current/duration) * 100;
        
        /* Set the scan bar value */
        scanBar.setValue(percentage);
    }
    
    /** Toggle video fullscreen */
    public void fullscreen() {                
        /* If the video is already fullscreen, go back. If not, go fullscreen */
        if(fsInfo.isFullscreen()) {
            /* Relocate to original position */
            videoFrame.relocate(fsInfo.getOriginalX(), fsInfo.getOriginalY());
            
            /* Set to original width */
            video.setFitWidth(fsInfo.getOriginalWidth());
            
            /* Add the video frame back to the main screen */
            group.getChildren().add(videoFrame);
            
            /* Hide the fullscreen video stage */
            fsStage.hide();
            
            /* Set the fullscreen flag false */
            fsInfo.setFullscreen(false);
        } else {
            /* Remove the video from the main screen */
            group.getChildren().remove(videoFrame);
            
            /* Setup the fullscreen root */
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER_LEFT);
            
            /* Set up the fullscreen scene */
            Scene fsScene = new Scene(vBox);
            fsScene.setFill(Color.BLACK);
            fsScene.setOnKeyPressed(new FullscreenKeyListener());
            
            /* Adjust the video */
            videoFrame.relocate(0, 0);
            video.setFitWidth(Screen.getPrimary().getBounds().getMaxX());
            
            /* Add the video to the fullscreen scene */
            vBox.getChildren().add(videoFrame);
            
            /* Create the fullscreen stage */
            fsStage = new Stage();
            fsStage.setScene(fsScene);
            fsStage.setTitle("");
            fsStage.setFullScreen(true);
            fsStage.show();
            
            /* Set the fullscreen flag true */
            fsInfo.setFullscreen(true);
        }
    }
    
    /**
     * Mouse Event Handler Class
     */
    public class MouseEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {            
            if(e.getEventType() == MouseEvent.MOUSE_ENTERED) {
                /* Add the controls */
                addControls();
            } else if(e.getEventType() == MouseEvent.MOUSE_EXITED) {
                /* Remove the controls */
                removeControls();
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
            
            /* Act according to id */
            if(id.equals("play")) {
                if(mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    /* If the video was playing, pause it and change the button label to play */
                    mediaPlayer.pause();
                    playButton.setText("Play");
                } else if(mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED ||
                          mediaPlayer.getStatus() == MediaPlayer.Status.STOPPED ||
                          mediaPlayer.getStatus() == MediaPlayer.Status.READY) {
                    /* If the video wasn't playing, play it and change the button label to pause */
                    mediaPlayer.play();
                    playButton.setText("Pause");
                }
            } else if (id.equals("stop")) {
                /* Stop the video and set the button label to play */
                mediaPlayer.stop();
                playButton.setText("Play");
            } else if (id.equals("fullscreen")) {
                fullscreen();
            } else if (id.equals("volume")) {
                /* Add or remove the volume slider as necessary */
                if(controls.getChildren().contains(volumeSlider)) {
                    controls.getChildren().remove(volumeSlider);
                } else {
                    controls.getChildren().add(volumeSlider);  
                }
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
        private boolean enable;
        
        /** Constructor */
        public ScanListener() {
            enable = false;
        }
        
        /** Enable or disable the listener */
        public void setEnabled(boolean nEnable) {
            enable = nEnable;
            
            /* Pause the video whilst scanning is enabled, restart when disabled */
            if(enable) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.play();
            }
        }
        
        /** When the value changes update the position in the video */
        @Override
        public void changed(ObservableValue<? extends Number> ov,
                            Number old_val, Number new_val) {
            /* If enabled update the video position */
            if(enable) {
                scan(new_val.doubleValue());
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
        /** When the position in the video changes, update the scan bar */
        @Override
        public void changed(ObservableValue<? extends Duration> arg0,
                            Duration arg1, Duration arg2) {
            setScan();
        }
    }
    
    /**
     * Listener for the esape key in fullscreen
     */
    public class FullscreenKeyListener implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent k) {
            if(k.getCode() == KeyCode.ESCAPE) {
                fullscreen();
            }
        }
    }
    
    /**
     * Listener for the volume slider
     */
    public class VolumeListener implements ChangeListener<Number> {
        @Override
        public void changed(ObservableValue<? extends Number> val,
                            Number oldVal, Number newVal) {
            mediaPlayer.setVolume(newVal.doubleValue());
        }
    }
    
    /**
     * Listener for the media player status
     */
    public class PlayerStatusListener implements ChangeListener<Status> {
        @Override
        public void changed(ObservableValue<? extends Status> val,
                            Status oldVal, Status newVal) {
            //TODO stuff with the status
            System.out.println("New status:" + newVal.toString());
        }
    }
}
