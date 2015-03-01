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

import teacheasy.mediahandler.video.FullscreenInfo;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
    private Button volumeButton;
    private Slider volumeSlider;
    private VolumeListener volumeListener;
    
    /* Array list of the currently open videos */
    private List<MediaView> videos;
    
    /* Array list of the currently open video frames */
    private List<GridPane> videoFrames;
    
    /* Fullscreen stage */
    Stage fsStage;
    
    /* Array list of information related to fullscreen for each video */
    private List<FullscreenInfo> fsInfo;
    
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
        
        /* Volume Control */
        volumeButton = new Button("Vol");
        volumeButton.setOnAction(new VolumeButtonListener());
        volumeSlider = new Slider();
        volumeListener = new VolumeListener(0);
        
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
        scanBarListener = new ScanListener(0);
        videoListener = new VideoListener(0);
        
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
        
        /* Instantiate the array list of videos */
        videos = new ArrayList<MediaView>();
        videoFrames = new ArrayList<GridPane>();
        fsInfo = new ArrayList<FullscreenInfo>();
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
        GridPane videoFrame = new GridPane();
        videoFrame.relocate(x, y);
        videoFrame.add(newVideo, 0, 0);
        videoFrame.setOnMouseEntered(new MouseEventHandler());
        videoFrame.setOnMouseExited(new MouseEventHandler());
        videoFrame.setId("" + videos.size());
        
        /* Add the media view to the list */
        videos.add(newVideo);
        
        /* Add the video frame to the list */
        videoFrames.add(videoFrame);
        
        /* Add the fullscreen info to the list */
        fsInfo.add(new FullscreenInfo(x, y, width, false));
        
        /* Add it to the group */
        group.getChildren().add(videoFrame);
    }
    
    /** Play a video */
    public void playVideo(int videoId) {
        videos.get(videoId).getMediaPlayer().play();
    }
    
    /** Pause a video */
    public void pauseVideo(int videoId) {
        videos.get(videoId).getMediaPlayer().pause();
    }
    
    /** Stop a video */
    public void stopVideo(int videoId) {
        videos.get(videoId).getMediaPlayer().stop();
    }
    
    /** Scan a video */
    public void scanVideo(int videoId, double percent) {
        scan(videoId, percent);
    }
    
    /** Clear all the videos currently being handled */
    public void clearVideos() {
        /* Remove all the media views (videos) from the group */
        for(int i = 0; i < videoFrames.size(); i++) {
            videos.get(i).getMediaPlayer().dispose();
            group.getChildren().remove(videoFrames.get(i));
        }
        
        /* Clear the array lists */
        videoFrames.clear();
        videos.clear();
        fsInfo.clear();
        
        /* Reset the video listener target */
        videoListener.setId(0);
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
        controls.setSpacing(10);
        
        /* Set the button labels */
        setButtonLabels(id);
        
        /* Set the scan listener target */
        scanBarListener.setId(id);
        videoListener.setId(id);
        volumeListener.setId(id);
        
        /* Adjust the scan location */
        setScan(id);
        
        /* Add the buttons to the control bar */
        controls.getChildren().addAll(playButton, stopButton, scanBar, fullscreenButton, volumeButton);
        controls.setPrefWidth(videoFrame.getWidth());
        
        /* Set the volume slider value */
        volumeSlider.setValue(videos.get(id).getMediaPlayer().getVolume());
        
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
    
    /** Toggle video fullscreen */
    public void fullscreen(int videoId) {
        /* Get the video frame */
        GridPane videoFrame = videoFrames.get(videoId);
        
        /* Get the video */
        MediaView video = videos.get(videoId);
        
        /* Get the fullscreen info for this video */
        FullscreenInfo videoFsInfo = fsInfo.get(videoId);
        
        /* If the video is already fullscreen, go back. If not, go fullscreen */
        if(videoFsInfo.isFullscreen()) {
            /* Relocate to original position */
            videoFrame.relocate(fsInfo.get(videoId).getOriginalX(), fsInfo.get(videoId).getOriginalY());
            
            /* Set to original width */
            video.setFitWidth(fsInfo.get(videoId).getOriginalWidth());
            
            /* Add the video frame back to the main screen */
            group.getChildren().add(videoFrame);
            
            /* Hide the fullscreen video stage */
            fsStage.hide();
            
            /* Set the fullscreen flag false */
            fsInfo.get(videoId).setFullscreen(false);
        } else {
            /* Remove the video from the main screen */
            group.getChildren().remove(videoFrame);
            
            /* Setup the fullscreen root */
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER_LEFT);
            
            /* Set up the fullscreen scene */
            Scene fsScene = new Scene(vBox);
            fsScene.setFill(Color.BLACK);
            fsScene.setOnKeyPressed(new FullscreenKeyListener(videoId));
            
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
            fsInfo.get(videoId).setFullscreen(true);
        }
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
            
            /* Pause the video whilst scanning is enabled, restart when disabled */
            if(enable) {
                videos.get(videoId).getMediaPlayer().pause();
            } else {
                videos.get(videoId).getMediaPlayer().play();
            }
        }
        
        /** When the value changes update the position in the video */
        @Override
        public void changed(ObservableValue<? extends Number> ov,
                            Number old_val, Number new_val) {
            /* If enabled update the video position */
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
        
        /** When the position in the video changes, update the scan bar */
        @Override
        public void changed(ObservableValue<? extends Duration> arg0,
                            Duration arg1, Duration arg2) {
            setScan(videoId);
        }
    }
    
    /**
     * Listener for the esape key in fullscreen
     */
    public class FullscreenKeyListener implements EventHandler<KeyEvent> {
        private int videoId;
        
        public FullscreenKeyListener(int nId) {
            this.videoId = nId;
        }
        
        @Override
        public void handle(KeyEvent k) {
            if(k.getCode() == KeyCode.ESCAPE) {
                fullscreen(videoId);
            }
        }
    }
    
    /**
     * Listener for the volume slider
     */
    public class VolumeListener implements ChangeListener<Number> {
        private int videoId;
        
        public VolumeListener(int nId) {
            this.videoId = nId;
        }
        
        public void setId(int nId) {
            this.videoId = nId;
        }
        
        @Override
        public void changed(ObservableValue<? extends Number> val,
                Number oldVal, Number newVal) {
            videos.get(videoId).getMediaPlayer().setVolume(newVal.doubleValue());
        }
    }
    
    /**
     * Listener for the volume button
     */
    public class VolumeButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            /* Add or remove the volume slider as necessary */
            if(controls.getChildren().contains(volumeSlider)) {
                controls.getChildren().remove(volumeSlider);
            } else {
                controls.getChildren().add(volumeSlider);  
            }
        }
    }
}
