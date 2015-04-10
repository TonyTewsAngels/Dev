/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.mediahandler;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;

import teacheasy.mediahandler.video.Video;

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
     * @param nGroup The group this handler should place videos in
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
     * @param x The x coordinate for the top left of the video
     *            relative to the groups origin.
     *            
     * @param y The y coordinate for the top left of the video
     *            relative to the group's origin.
     *            
     * @param width The width of the video in pixels.
     * 
     * @param sourcefile Absolute path of the video as a string. Can be a local
     *                     file path or a web address beginning with 'http'
     * 
     * @param autoPlay If true video plays as soon as created.
     * 
     * @param loop If true video loops to beginning once it ends.
     */
    public void createVideo(float x, float y, float width, String sourcefile, boolean autoPlay, boolean loop) {
        videos.add(new Video(group, x, y, width, sourcefile, autoPlay, loop));
    }
    
    /** 
     * Causes a video handled by the handler to play.
     * 
     * @param videoId The ID of the video to be played.
     */
    public void playVideo(int videoId) {
        if(videoId < videos.size() && videoId >= 0) {
            videos.get(videoId).play();
        }
    }
    
    /** 
     * Causes a video handled by the handler to pause.
     * 
     * @param videoId The ID of the video to be played.
     */
    public void pauseVideo(int videoId) {
        if(videoId < videos.size() && videoId >= 0) {
            videos.get(videoId).pause();
        }
    }
    
    /** 
     * Causes a video handled by the handler to stop.
     * 
     * @param videoId The ID of the video to be played.
     */
    public void stopVideo(int videoId) {
        if(videoId < videos.size() && videoId >= 0) {
            videos.get(videoId).stop();
        }
    }
    
    /** 
     * Causes a video handled by the handler to change
     * playback position.
     * 
     * @param videoId The ID of the video to be played.
     * 
     * @param percent The time in the video to move
     *                  playback to as a percentage of the
     *                  total time.
     */
    public void scanVideo(int videoId, double percent) {
        if(videoId < videos.size() && videoId >= 0) {    
            videos.get(videoId).scan(percent);
        }
    }
    
    /** 
     * Causes a video handled by the handler to change
     * size
     * 
     * @param videoId The ID of the video to be played.
     * 
     * @param nWidth - The new width for the video.
     */
    public void resizeVideo(int videoId, float nWidth) {
        if(videoId < videos.size() && videoId >= 0) {
            videos.get(videoId).resize(nWidth);
        }
    }
    
    /** 
     * Causes a video handled by the handler to change
     * location
     * 
     * @param videoId The ID of the video to be played.
     * 
     * @param nx The new x location in pixels
     * @param ny The new y location in pixels
     */
    public void relocateVideo(int videoId, float nx, float ny) {
        if(videoId < videos.size() && videoId >= 0) {
            videos.get(videoId).relocate(nx, ny);
        }
    }
    
    /** 
     * Causes a video handled by the handler to change
     * visibility
     * 
     * @param videoId The ID of the video to be played.
     * 
     * @param visible The new visibility setting as a boolean.
     */
    public void setVisible(int videoId, boolean visible) {
        if(videoId < videos.size() && videoId >= 0) {
            videos.get(videoId).setVisible(visible);
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
