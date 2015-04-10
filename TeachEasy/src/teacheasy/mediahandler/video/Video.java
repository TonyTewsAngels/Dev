/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.mediahandler.video;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
 * @version 1.1 10 Apr 2015
 * 
 */
public class Video {
    /** Reference to the group on which to draw videos. */
    private Group group;
    
    /** Media player object to control playback. */
    private MediaPlayer mediaPlayer;
    
    /** Media view object to display the video. */
    private MediaView video;
    
    /** Grid layout to contain the video and the controls. */
    private GridPane videoFrame;
    
    /* Various UI Control Objects */
    private HBox controls;
    private Button playButton;
    private Button stopButton;
    private Label timeStamp;
    private Label durationStamp;
    private Slider scanBar;
    private ScanListener scanBarListener;
    private VideoListener videoListener;
    private Button fullscreenButton;
    private Button volumeButton;
    private Slider volumeSlider;
    private VolumeListener volumeListener;
    
    /* GUI Elements */
    private ImageView playImage;
    private ImageView pauseImage;
    private ImageView stopImage;
    private ImageView fullscreenImage;
    private ImageView fullscreenOffImage;
    private ImageView volumeImage;
    private ImageView volumeOffImage;
    
    /** Variable to maintain play state after scan */
    private boolean wasPlaying = false;
    
    /** Variable to maintain whether the volume slider is visible or not */
    private boolean volumeOpen = false;
    
    /** Fullscreen stage. */
    private Stage fsStage;
    
    /** Fullscreen information, needed when exiting fullscreen. */
    private FullscreenInfo fsInfo;
    
    private static final int MIN_WIDTH = 350;
    
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
     * @param width - The width of the video in pixels.
     * 
     * @param sourcefile - Absolute path of the video as a string. Can be a local
     *                     file path or a web address beginning with 'http'
     * 
     * @param autoPlay - If true the video plays immediately
     * 
     * @param loop - If true the video loops to the beginning when it ends
     */
    public Video(Group nGroup, float x, float y, float width, String sourcefile, boolean autoPlay, boolean loop) {
        /* Set the group reference */
        this.group = nGroup;
        
        /* Load icon images */
        playImage = new ImageView(new Image(getClass().getResourceAsStream("Play_ST_CONTENT_RECT_Transparent_L-01.png")));
        pauseImage = new ImageView(new Image(getClass().getResourceAsStream("Pause_ST_CONTENT_RECT_Transparent_L-01.png")));        
        stopImage = new ImageView(new Image(getClass().getResourceAsStream("Stop_ST_CONTENT_RECT_Transparent_L-01.png")));
        fullscreenImage = new ImageView(new Image(getClass().getResourceAsStream("FullscreenON_ST_CONTENT_RECT_Transparent_L-01.png")));
        fullscreenOffImage = new ImageView(new Image(getClass().getResourceAsStream("FullscreenOFF_ST_CONTENT_RECT_Transparent_L-01.png")));
        volumeImage = new ImageView(new Image(getClass().getResourceAsStream("VolumeON_ST_CONTENT_RECT_Transparent_L-01.png")));
        volumeOffImage = new ImageView(new Image(getClass().getResourceAsStream("VolumeOFF_ST_CONTENT_RECT_Transparent_L-01.png")));

        /* Play/Pause Control */
        playButton = new Button("");
        playButton.setOnAction(new ButtonEventHandler());
        playButton.getStylesheets().add(this.getClass().getResource("MediaHandlerStyle.css").toExternalForm());
        playButton.setGraphic(playImage);
        playButton.setId("play");
        
        /* Stop Control */
        stopButton = new Button("");
        stopButton.getStylesheets().add(this.getClass().getResource("MediaHandlerStyle.css").toExternalForm());
        stopButton.setGraphic(stopImage);
        stopButton.setOnAction(new ButtonEventHandler());
        stopButton.setId("stop");
        
        /* Fullscreen Control */
        fullscreenButton = new Button("");
        fullscreenButton.getStylesheets().add(this.getClass().getResource("MediaHandlerStyle.css").toExternalForm());
        fullscreenButton.setGraphic(fullscreenImage);
        fullscreenButton.setOnAction(new ButtonEventHandler());
        fullscreenButton.setId("fullscreen");
        
        /* Volume Button */
        volumeButton = new Button("");
        volumeButton.getStylesheets().add(this.getClass().getResource("MediaHandlerStyle.css").toExternalForm());
        volumeButton.setGraphic(volumeImage);
        volumeButton.setOnAction(new ButtonEventHandler());
        volumeButton.setId("volume");
        
        /* Volume Control */
        volumeSlider = new Slider();
        volumeSlider.getStylesheets().add(this.getClass().getResource("MediaHandlerStyle.css").toExternalForm());
        volumeSlider.setId("slider");
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
        scanBar.getStylesheets().add(this.getClass().getResource("MediaHandlerStyle.css").toExternalForm());
        scanBar.setId("slider");
        scanBarListener = new ScanListener();
        videoListener = new VideoListener();
        
        /* Set up Scan Control */
        scanBar.setMin(0);
        scanBar.setMax(100);
        scanBar.setValue(0);
        scanBar.setPrefWidth(1000);
        scanBar.setMinHeight(28);
        scanBar.setShowTickLabels(false);
        scanBar.setShowTickMarks(false);
        scanBar.valueProperty().addListener(scanBarListener);
        scanBar.setOnMousePressed(new ScanMouseHandler());
        scanBar.setOnMouseReleased(new ScanMouseHandler());
        
        /* Set up the time stamp */
        timeStamp = new Label("00:00:00");
        timeStamp.setTextFill(Color.LIGHTGRAY);
        timeStamp.setMinWidth(50);
        timeStamp.setMinHeight(28);
        
        /* Set up the duration stamp */
        durationStamp = new Label("00:00:00");
        durationStamp.setTextFill(Color.LIGHTGRAY);
        durationStamp.setMinWidth(50);
        durationStamp.setMinHeight(28);
        
        /* Check that the sourcefile is not null */
        if(sourcefile == null) {
            return;
        }
        
        /* Temporary file object to load media from */
        File file;
        
        /* Temporary media object to create media player from */
        Media media;
        
        /* Check whether the media file is a local or web resource */
        if(sourcefile.startsWith("http")) {
            /* File is a web resource. Check that it exists */
            if(!mediaExists(sourcefile)) {
                /* Add a label to notify the user that the media was unavailable */
                Label label = new Label("Media Unavailable");
                label.relocate(x, y);
                group.getChildren().add(label);
                
                /* Return to halt the creation of this video */
                return;
            }
            
            /* Load the media from URL */
            media = new Media(sourcefile);
            
        } else {
            /* File is a local resource */
            file = new File(sourcefile);
            
            /* Check that the file exists and is .mp4 or .flv */
            if(!file.exists()) {
                /* Add a label to notify the user that the file could not be found */
                Label label = new Label("File could not be found");
                label.relocate(x, y);
                group.getChildren().add(label);
                return;
            } else if(!file.getAbsolutePath().endsWith(".mp4") &&
                      !file.getAbsolutePath().endsWith(".flv")) {
                /* Return to halt the creation of this video */
                return;
            }

            /* Load the file as a media object */
            media = new Media(file.toURI().toString());
        }

        /* Create a media player for the object */
        mediaPlayer = new MediaPlayer(media);
        
        /* Set up the media player */
        mediaPlayer.setAutoPlay(autoPlay);
        mediaPlayer.setOnEndOfMedia(new MediaEndHandler());
        mediaPlayer.currentTimeProperty().addListener(videoListener);
        mediaPlayer.statusProperty().addListener(new PlayerStatusListener());
        
        if(loop) {
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        }
        
        /* Create a media view for the object */
        video = new MediaView(mediaPlayer);
        
        /* Set up the media view */
        if(width < MIN_WIDTH) {
            width = MIN_WIDTH;
        }
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
    
    /** 
     * Programmatically plays this video 
     */
    public void play() {
        if(mediaPlayer != null) {
            mediaPlayer.play();
        }
    }
    
    /**
     *  Programmatically pauses this video 
     */
    public void pause() {
        if(mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }
    
    /** 
     * Programmatically stops this video 
     */
    public void stop() {
        if(mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
    
    /** 
     * Programmatically disposes of this video 
     */
    public void dispose() {
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
    }
    
    /** 
     * Programmatically resizes this video 
     */
    public void resize(float nWidth) {
        if(video != null) {
            video.setFitWidth(nWidth);
        }
    }
    
    /** 
     * Programmatically relocates this video 
     */
    public void relocate(float x, float y) {
        if(videoFrame != null) {
            videoFrame.relocate(x, y);
        }
    }
    
    /** 
     * Programmatically sets this videos visibility 
     */
    public void setVisible(boolean visible) {
        if(videoFrame != null) {
            videoFrame.setVisible(visible);
        }
    }
    
    /** 
     * Adds the controls to a video frame 
     */
    private void addControls() {
        /* Set up the control bar */
        controls = new HBox();
        controls.setAlignment(Pos.BOTTOM_CENTER);
        controls.setSpacing(5);
        controls.setPrefWidth(videoFrame.getWidth());
        
        /* Adjust the scan location */
        setScan();
        
        /* Set the volume slider value */
        volumeSlider.setValue(mediaPlayer.getVolume());
        
        /* Add the buttons to the control bar */
        controls.getChildren().addAll(playButton, stopButton, timeStamp, scanBar, durationStamp, fullscreenButton, volumeButton);
        if(volumeOpen) {
            controls.getChildren().add(volumeSlider);
        }

        /* Add the control bar to the video frame */
        videoFrame.getChildren().add(controls);
    }
    
    /** 
     * Removes the controls from a video frame 
     */
    private void removeControls() {
        /* Remove the controls */
        videoFrame.getChildren().remove(controls);
    }
    
    /** 
     * Scans to a position in the video 
     * 
     * @param percent - The new position in the video as a 
     *                  percentage of the video length.
     */
    public void scan(double percent) {
        /* Get the duration of the clip */
        double duration = mediaPlayer.getMedia().getDuration().toSeconds();
        
        /* Get the new position using the percentage */
        double newPosition = duration * (percent/100);
        
        if(mediaPlayer.getStatus() == Status.STOPPED ||
           mediaPlayer.getStatus() == Status.READY) {
            mediaPlayer.pause();
        }
        
        /* Set the new position (milliseconds) */
        mediaPlayer.seek(new Duration(newPosition*1000));
    }
    
    /** 
     * Sets the scan bar to match the video position 
     */
    private void setScan() {
        /* Get the duration of the clip */
        double duration = mediaPlayer.getMedia().getDuration().toSeconds();
        
        /* Get the current position in the clip */
        double current =  mediaPlayer.getCurrentTime().toSeconds();
        
        /* Calculate the percentage */
        double percentage = (current/duration) * 100;
        
        /* Set the scan bar value */
        scanBar.setValue(percentage);
    }
    
    /** 
     * Resets the scan bar to the start of the video 
     */
    private void resetScan() {
        scanBar.setValue(0);
    }
    
    /** 
     * Update a label to show a time based on a duration 
     */
    private void updateTimeStamp(Label stamp, Duration duration) {
        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        
        /* Get the number of seconds */
        seconds = (int)Math.round(duration.toSeconds());
        
        /* Convert up to minutes */
        while(seconds > 59) {
            seconds -= 60;
            minutes++;
        }
        
        /* Convert up to hours */
        while(minutes > 59) {
            minutes -= 60;
            hours++;
        }
        
        /* Convert integers to strings*/
        String secStr = zeroPad(seconds);
        String minStr = zeroPad(minutes);
        String hrStr = zeroPad(hours);
        
        /* Set the text */
        stamp.setText(hrStr + ":" + minStr + ":" + secStr);
    }
    
    /** 
     * Utility function, adds a leading zero to numbers under 10 
     */
    private String zeroPad(int n) {
        if(n < 10) {
            return new String("0" + n);
        } else {
            return new String("" + n);
        }
    }
    
    /** 
     * Toggles video in and out of fullscreen 
     */
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
            
            /* Set the fullscreen button graphic */
            fullscreenButton.setGraphic(fullscreenImage);
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
            
            /* Set the fullscreen button graphic */
            fullscreenButton.setGraphic(fullscreenOffImage);
        }
    }
    
    /** 
     * Utility function, checks if an online video exists 
     */
    private static boolean mediaExists(String url){
        try {
          /* Do not follow redirects */
          HttpURLConnection.setFollowRedirects(false);
          
          /* Open a connection to the media */
          HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
          
          /* Use a head only request to just retrieve metadata */
          con.setRequestMethod("HEAD");
          
          /* If a response code of 200 (okay) is received, the media is available */
          return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        }
        catch (Exception e) {
           return false;
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
                    /* If the video was playing, pause it */
                    mediaPlayer.pause();
                } else if(mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED ||
                          mediaPlayer.getStatus() == MediaPlayer.Status.STOPPED ||
                          mediaPlayer.getStatus() == MediaPlayer.Status.READY) {
                    /* If the video wasn't playing, play it */
                    mediaPlayer.play();
                }
            } else if (id.equals("stop")) {
                /* Stop the video and reset the time stanp and scan bar */
                mediaPlayer.stop();
                resetScan();
                updateTimeStamp(timeStamp, new Duration(0));
            } else if (id.equals("fullscreen")) {
                fullscreen();
            } else if (id.equals("volume")) {
                /* Add or remove the volume slider as necessary */
                if(controls.getChildren().contains(volumeSlider)) {
                    controls.getChildren().remove(volumeSlider);
                    volumeOpen = false;
                } else {
                    controls.getChildren().add(volumeSlider);
                    volumeOpen = true;
                }
            }
        }
    }
    
    /**
     * Scan event handler class
     */
    public class ScanListener implements ChangeListener<Number> {
        /* 
         * Class level variable indicates whether or not the scan bar is enabled
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
                if(mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    wasPlaying = true;
                    mediaPlayer.pause();
                }
            } else {
                if(wasPlaying) {
                    wasPlaying = false;
                    mediaPlayer.play();
                }
            }
        }
        
        /** When the value changes update the position in the video */
        @Override
        public void changed(ObservableValue<? extends Number> ov,
                            Number old_val, Number new_val) {
            /* If enabled update the video position */
            if(enable) {
                scan(new_val.doubleValue());
                updateTimeStamp(timeStamp, mediaPlayer.getCurrentTime());
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
            updateTimeStamp(timeStamp, mediaPlayer.getCurrentTime());
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
            if(newVal.doubleValue() < 0.001) {
                volumeButton.setGraphic(volumeOffImage);
            } else if(oldVal.doubleValue() < 0.01 && newVal.doubleValue() >= 0.001) {
                volumeButton.setGraphic(volumeImage);
            }
        }
    }
    
    /**
     * Event Handler for the end of the media
     */
    public class MediaEndHandler implements Runnable {
        public void run() {
            mediaPlayer.stop();
        }
    }
    
    /**
     * Listener for the media player status
     */
    public class PlayerStatusListener implements ChangeListener<Status> {
        @Override
        public void changed(ObservableValue<? extends Status> val,
                            Status oldVal, Status newVal) {            
            switch(newVal) {
                case DISPOSED:
                    break;
                case HALTED:
                    break;
                case PAUSED:
                    playButton.setGraphic(playImage);
                    break;
                case PLAYING:
                    playButton.setGraphic(pauseImage);
                    break;
                case READY:
                    updateTimeStamp(durationStamp, mediaPlayer.getMedia().getDuration());
                    playButton.setGraphic(playImage);
                    break;
                case STALLED:
                    break;
                case STOPPED:
                    setScan();
                    updateTimeStamp(timeStamp, mediaPlayer.getCurrentTime());
                    playButton.setGraphic(playImage);
                    break;
                case UNKNOWN:
                    break;
                default:
                    break;
            }
        }
    }
}
