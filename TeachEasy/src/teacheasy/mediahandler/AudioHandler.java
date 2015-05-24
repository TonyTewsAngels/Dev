/*
 * Alex Cash and Calum Armstrong
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.mediahandler;

import java.util.ArrayList;
import java.util.List;

import teacheasy.mediahandler.audio.Audio;

import javafx.scene.*;

/**
 * This class is the handler for audio
 * 
 * @author Alex Cash 
 * @author Calum Armstrong
 * @version 1.6 14 April 2015
 */

public class AudioHandler {
	/* Reference to the group on which to add the Audio */
	private Group group;
	
	/* Array List of the Audios on screen */
	private List<Audio> audios;
	
	/** 
	 * Constructs the audio handler.
	 * 
	 * @param nGroup The group this handler should add audio tracks to.
	 * 
	 */
	public AudioHandler(Group nGroup) {
		/* Set the group reference */
		this.group = nGroup;
		 
        /* Initialise the audio list */
        this.audios = new ArrayList<Audio>();
	}
	
	 /** 
	  * Adds an audio track to the group associated with the handler
	  * 
	  *  @param x The x-coordinate for the top left of the audio player relative to 
	  *  			the groups origin.
	  *  
	  *  @param y The y-coordinate for the top left of the audio player relative to 
	  *  			the groups origin.
	  *  
	  *  @param width The width of the audio player.
	  *  
	  *  @param sourceFile Absolute path to the audio file as a string. Can either be
	  *  					a local file path or a web address.
	  *  
	  *  @param autoPlay If true, the audio player will start playing the audio track
	  *  					as soon as it is create.
	  *  
	  *  @param loop If true, the audio track will loop back to the beginning once it
	  *  				has reached the end of the file. This will occur indefinitely.
	  *  
	  *  @param visibleControls If true, the audio player will be displayed and can be
	  *  							interacted with using buttons. If false, no buttons
	  *  							will appear and autoPlay will be set to true.
	  *  
	  *  @param playButtonOnly If true, the only button that will appear will be a play
	  *  						and pause button. If false, all controls will be visible.
	  *  
	  */
    public void createAudio(float x, float y, float width, String sourceFile, boolean autoPlay, boolean loop, boolean visibleControls, boolean playButtonOnly) {
        audios.add(new Audio(group, x, y, width, sourceFile, autoPlay, loop, visibleControls, playButtonOnly));
    }
    
    /** 
     * Will cause the selected audio track to play.
     * 
     * @param audioId The ID of the audio track to be played.
     * 
     */
    public void playAudio(int audioId) {
    	if(audioId < audios.size() && audioId >= 0) {
    		audios.get(audioId).play();
    	}
    }
    
    /** 
     * Will cause the selected audio track to pause.
     * 
     * @param audioId The ID of the audio track to be paused.
     * 
     */
    public void pauseAudio(int audioId) {
    	if(audioId < audios.size() && audioId >= 0) {
    		audios.get(audioId).pause();
    	}
    }    
    
    /** 
     * Will cause the selected audio track to stop.
     * 
     * @param audioId The ID of the audio track to be stopped.
     * 
     */
    public void stopAudio(int audioId) {
    	if(audioId < audios.size() && audioId >= 0) {
    		audios.get(audioId).stop();
    	}    	
    }
    
    /** 
     * Will cause the selected audio track to be disposed.
     * 
     * @param audioId The ID of the audio track to be disposed.
     * 
     */
    public void disposeAudio(int audioId) {
    	if(audioId < audios.size() && audioId >= 0) {
    		audios.get(audioId).dispose();
    	}    	
    }
    
    /** 
     * Will cause the selected audio player to be resized.
     * 
     * @param audioId The ID of the audio track associated with the audio
     * 					player to be resized.
     * 
     * @param nWidth The new width for the audio player
     * 
     */
    public void resize(int audioId, float nWidth) {
    	if(audioId < audios.size() && audioId >= 0) {
    		audios.get(audioId).resize(nWidth);
    	}    	
    }
    
    /** 
     * Will cause the selected audio player to be relocated.
     * 
     * @param audioId The ID of the audio track associated with the audio
     * 					player to be relocated.
     * 
     * @param x The new x-coordinate for the audio player
     * 
     * @param y The new y-coordinate for the audio player
     * 
     */
    public void relocate(int audioId, float x, float y) {
    	if(audioId < audios.size() && audioId >= 0) {
    		audios.get(audioId).relocate(x, y);
    	}    	
    }
    
    /** 
     * Controls the visibility of a selected audio player.
     * 
     * @param audioId The ID of the audio track associated with the
     * 					audio player to have its visibility changed.
     * 
     * @param visible The new visibility setting, as a boolean input.
     * 
     */
    public void setVisible(int audioId, boolean visible) {
    	if(audioId < audios.size() && audioId >= 0) {
    		audios.get(audioId).setVisible(visible);
    	}    	
    }
    
    /** 
     * Will cause the selected audio track to seek through the media.
     * 
     * @param audioId The ID of the audio track to be seeked through.
     * 
     * @param percent The new location through the audio track, as a percentage.
     * 
     */
    public void seekAudio(int audioId, double percent) {
    	if(audioId < audios.size() && audioId >= 0) {
    		audios.get(audioId).audioSeek(percent);
    	}    	
    }
    
    /** 
     * Will clear all audio tracks being handled.
     * 
     */
    public void clearAudios() {
        /* Remove all the media views (audios) from the group */
        for(int i = 0; i < audios.size(); i++) {
            audios.get(i).dispose();
            group.getChildren().remove(audios.get(i));
        }
        
        /* Clear the array list */
        audios.clear();
    }
    
    /**
     * Returns the number of audio clips currently being handled
     * 
     * @return Number of Audio clips being handled
     */
    public int getAudioCount() {
        return audios.size();
    }
    
    /** 
     * Will return the end x co-ordinate for the audio player selected
     * 
     */
    public double getAudioXEnd(int audioId) {
       	double xStart = 0;
    	double xEnd = 0;
    	if(audioId < audios.size() && audioId >= 0) {
    		xStart = audios.get(audioId).xStartPos;
    		if(audios.get(audioId).buttonVisibilityLevel == 0){
    			xEnd = xStart;
    		} else if (audios.get(audioId).buttonVisibilityLevel == 1){
    			xEnd = xStart + 30;
    			System.out.println("xEnd with lvl 1: " +xEnd);
    		} else if (audios.get(audioId).buttonVisibilityLevel == 2){
    			xEnd = xStart + audios.get(audioId).widthValue;
    		}
    	}   	
        return xEnd;
    }
    
    /** 
     * Will return the end y co-ordinate for the audio player selected
     * 
     */
    public double getAudioYEnd(int audioId) {
       	double yStart = 0;
    	double yEnd = 0;
    	if(audioId < audios.size() && audioId >= 0) {
    		yStart = audios.get(audioId).yStartPos;
    		if(audios.get(audioId).buttonVisibilityLevel == 0){
    			yEnd = yStart;
    		} else if (audios.get(audioId).buttonVisibilityLevel == 1){
    			yEnd = yStart + 30;
    		} else if (audios.get(audioId).buttonVisibilityLevel == 1){
    			yEnd = yStart + 100;
    		}
    	}   	
        return yEnd;
    }
    
}