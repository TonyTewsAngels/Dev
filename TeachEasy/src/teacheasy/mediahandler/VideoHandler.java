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
import teacheasy.mediahandler.video.Video;

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
    
    /* Array List of the videos currently on the screen */
    private List<Video> videos;
    
    /** 
     * Constructs the video handler.
     * 
     * @param nGroup - The group this handler should place videos in
     */
    public VideoHandler(Group nGroup) {
        /* Set the group reference */
        this.group = nGroup;
        
        /* Initialise the video list */
        this.videos = new ArrayList<Video>();
    }
    
    /** 
     * Adds a video to the group associated with the handler.
     * 
     * @param x - The x coordinate for the top left of the video
     *            relative to the groups origin.
     *            
     * @param y - The y coordinate for the top left of the video
     *            relative to the group's origin.
     *            
     * @param width - The width of the video in pixels.
     * 
     * @param sourcefile - Absolute filepath of the video as a string.
     * 
     * @param autoplay - If true video plays as soon as created.
     * 
     * @param loop - If true video loops to beginning once it ends.
     */
    public void createVideo(float x, float y, float width, String sourcefile, boolean autoPlay, boolean loop) {
        videos.add(new Video(group, x, y, width, sourcefile, autoPlay, loop));
    }
    
    /** 
     * Causes a video handled by the handler to play.
     * 
     * @param videoId - The ID of the video to be played.
     */
    public void playVideo(int videoId) {
        if(videoId < videos.size() && videoId != 0) {
            videos.get(videoId).play();
        }
    }
    
    /** 
     * Causes a video handled by the handler to pause.
     * 
     * @param videoId - The ID of the video to be played.
     */
    public void pauseVideo(int videoId) {
        if(videoId < videos.size() && videoId != 0) {
            videos.get(videoId).pause();
        }
    }
    
    /** 
     * Causes a video handled by the handler to stop.
     * 
     * @param videoId - The ID of the video to be played.
     */
    public void stopVideo(int videoId) {
        if(videoId < videos.size() && videoId != 0) {
            videos.get(videoId).stop();
        }
    }
    
    /** 
     * Causes a video handled by the handler to change
     * playback position.
     * 
     * @param videoId - The ID of the video to be played.
     * 
     * @param percent - The time in the video to move
     *                  playback to as a percentage of the
     *                  total time.
     */
    public void scanVideo(int videoId, double percent) {
        if(videoId < videos.size() && videoId != 0) {    
            videos.get(videoId).scan(percent);
        }
    }
    
    /** 
     * Causes a video handled by the handler to change
     * size
     * 
     * @param videoId - The ID of the video to be played.
     * 
     * @param nWidth - The new width for the video.
     */
    public void resizeVideo(int videoId, float nWidth) {
        if(videoId < videos.size() && videoId != 0) {
            videos.get(videoId).resize(nWidth);
        }
    }
    
    /** 
     * Causes a video handled by the handler to change
     * location
     * 
     * @param videoId - The ID of the video to be played.
     * 
     * @param nWidth - The new width for the video.
     */
    public void relocateVideo(int videoId, float x, float y) {
        if(videoId < videos.size() && videoId != 0) {
            videos.get(videoId).relocate(x, y);
        }
    }
    
    /** 
     * Clears all the videos currently being handled.
     */
    public void clearVideos() {
        /* Remove all the media views (videos) from the group */
        for(int i = 0; i < videos.size(); i++) {
            videos.get(i).dispose();
            group.getChildren().remove(videos.get(i));
        }
        
        /* Clear the array list */
        videos.clear();
    }
    
    /**
     * Returns the number of videos currently being handled
     * 
     * @return Number of videos being handled
     */
    public int getVideoCount() {
        return videos.size();
    }
}
