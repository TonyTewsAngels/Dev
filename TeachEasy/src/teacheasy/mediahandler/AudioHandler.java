/*
 * Alex Cash & Calum Armstrong
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 */
package teacheasy.mediahandler;

import java.util.ArrayList;
import java.util.List;

import teacheasy.mediahandler.audio.Audio;

import javafx.scene.*;

/**
 * Handles audio objects on a page, including their addition and
 * deletion as well as maintaining their state. Audio objects
 * are added by supplying the relevant data from the data structure.
 * This class is used by the renderer when redrawing pages.
 * 
 * @author Alex Cash 
 * @author Calum Armstrong
 * @version 1.6 14 Apr 2015
 */

public class AudioHandler {
    
	/* Reference to the group on which to add the Audio */
	private Group group;
	
	/* Array List of the audio objects on screen */
	private List<Audio> audios;
	
	/** 
	 * Constructor for the audio handler.
	 * 
	 * @param nGroup The group this handler should add audio objects to.
	 * 
	 */
	public AudioHandler(Group nGroup) {
		/* Set the group reference */
		this.group = nGroup;
		 
        /* Initialise the audio list */
        this.audios = new ArrayList<Audio>();
	}
	
	 /** 
	  * Adds an audio track to the group associated with the handler.
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
     * Causes the selected audio object to play.
     * 
     * @param audioId The ID of the audio object to be played.
     * 
     */
    public void playAudio(int audioId) {
    	if(audioId < audios.size() && audioId >= 0) {
    		audios.get(audioId).play();
    	}
    }
    
    /** 
     * Causes the selected audio object to pause.
     * 
     * @param audioId The ID of the audio object to be paused.
     * 
     */
    public void pauseAudio(int audioId) {
    	if(audioId < audios.size() && audioId >= 0) {
    		audios.get(audioId).pause();
    	}
    }    
    
    /** 
     * Causes the selected audio object to stop.
     * 
     * @param audioId The ID of the audio object to be stopped.
     * 
     */
    public void stopAudio(int audioId) {
    	if(audioId < audios.size() && audioId >= 0) {
    		audios.get(audioId).stop();
    	}    	
    }
    
    /** 
     * Causes the selected audio object to be disposed.
     * 
     * @param audioId The ID of the audio object to be disposed.
     * 
     */
    public void disposeAudio(int audioId) {
    	if(audioId < audios.size() && audioId >= 0) {
    		audios.get(audioId).dispose();
    	}    	
    }
    
    /** 
     * Causes the selected audio object to be resized.
     * 
     * @param audioId The ID of the audio object associated with the audio
     * 					player to be resized.
     * 
     * @param nWidth The new width for the audio player.
     * 
     */
    public void resize(int audioId, float nWidth) {
    	if(audioId < audios.size() && audioId >= 0) {
    		audios.get(audioId).resize(nWidth);
    	}    	
    }
    
    /** 
     * Causes the selected audio player to be relocated.
     * 
     * @param audioId The ID of the audio track associated with the audio
     * 					player to be relocated.
     * 
     * @param x The new x-coordinate for the audio player.
     * 
     * @param y The new y-coordinate for the audio player.
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
     * Causes the selected audio object to seek through the media.
     * 
     * @param audioId The ID of the audio object to be seeked through.
     * 
     * @param percent The new location through the audio object, as a percentage.
     * 
     */
    public void seekAudio(int audioId, double percent) {
    	if(audioId < audios.size() && audioId >= 0) {
    		audios.get(audioId).audioSeek(percent);
    	}    	
    }
    
    /** 
     * Clears all audio tracks being handled.
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
     * Returns the x axis end co-ordinate for the audio object selected.
     */
    public double getAudioXEnd(int audioId) {
        /* Position variables */
       	double xStart = 0;
    	double xEnd = 0;
    	
    	/* Check the ID is in range */
    	if(audioId < audios.size() && audioId >= 0) {
    	    /* Start position */
    		xStart = audios.get(audioId).xStartPos;
    		
    		/* Check controls state */
    		if(audios.get(audioId).buttonVisibilityLevel == 0){
    		    /* Controls invisible, width is 0 */
    			xEnd = xStart;
    		} else if (audios.get(audioId).buttonVisibilityLevel == 1){
    		    /* Controls minimal, width is 30 */
    			xEnd = xStart + 30;
    		} else if (audios.get(audioId).buttonVisibilityLevel == 2){
    		    /* Controls full, check collapsed setting */
    			if (audios.get(audioId).collapsedControls == false){
    			    /* Controls open, get full width */
    				xEnd = xStart + audios.get(audioId).widthValue;
    			} else if (audios.get(audioId).collapsedControls == true){
    			    /* Controls collapsed, width is 30 */
    				xEnd = xStart + 30;
    			}
    		}
    	}
    	
    	/* Return the end position */
        return xEnd;
    }
    
    /** 
     * Returns the  y axis end co-ordinate for the audio object selected.
     */
    public double getAudioYEnd(int audioId) {
        /* Position variables */
       	double yStart = 0;
    	double yEnd = 0;
    	
    	/* Check the ID is in range */
    	if(audioId < audios.size() && audioId >= 0) {
    	    /* Start position */
    		yStart = audios.get(audioId).yStartPos;
    		
    		/* Check controls state */
    		if(audios.get(audioId).buttonVisibilityLevel == 0){
    		    /* Controls not visible, height is 0 */
    			yEnd = yStart;
    		} else if (audios.get(audioId).buttonVisibilityLevel == 1){
    		    /* Controls are minimal, height is 30 */
    			yEnd = yStart + 30;
    		} else if (audios.get(audioId).buttonVisibilityLevel == 2){
    		    /* Controls full, height is 80 */
    			yEnd = yStart + 80;
    		}
    	}
    	
    	/* Return the end position */
        return yEnd;
    }
    
    /** 
     * Prints information about the audio handler to console
     * for debugging purposes.
     */
    public void debugPrint() {
        System.out.println("### Audio Handler ###");
        System.out.println("Handling: " + audios.size());
    }
}