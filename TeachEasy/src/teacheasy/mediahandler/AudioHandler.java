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
 * @version 1.5 4 Mar 2015
 */

public class AudioHandler {
	/* Reference to the group on which to add the Audio */
	private Group group;
	
	/* Array List of the Audios on screen */
	private List<Audio> audios;
	
	/** Constructor Method */
	public AudioHandler(Group nGroup) {
		/* Set the group reference */
		this.group = nGroup;
		 
        /* Initialise the audio list */
        this.audios = new ArrayList<Audio>();
	}
	
	 /** Add an audio to a group */
    public void createAudio(double x, double y, double width, String sourceFile, boolean autoPlay, boolean loop, boolean visibleControls, boolean playButtonOnly) {
        audios.add(new Audio(group, x, y, width, sourceFile, autoPlay, loop, visibleControls, playButtonOnly));
    }
    
    /** Play an audio */
    public void playAudio(int audioId) {
        audios.get(audioId).play();
    }
    
    /** Pause an audio */
    public void pauseAudio(int audioId) {
    	audios.get(audioId).pause();
    }    
    
    /** Stop an audio */
    public void stopAudio(int audioId) {
    	audios.get(audioId).stop();
    }
    
    /** Dispose an audio */
    public void disposeAudio(int audioId) {
    	audios.get(audioId).dispose();
    }
    
    /** Resize an audio */
    public void resize(int audioId, float nWidth) {
    	audios.get(audioId).resize(nWidth);
    }
    
    /** Relocate an audio */
    public void relocate(int audioId, float x, float y) {
    	audios.get(audioId).relocate(x, y);
    }
    
    /**Programmatically sets this videos visibility*/
    public void setVisible(int audioId, boolean visible) {
    	audios.get(audioId).setVisible(visible);
    }
    
    /** Scan an audio */
    public void seekAudio(int audioId, double percent) {
    	audios.get(audioId).audioSeek(percent);
    }
    
    /** Clear all the audios currently being handled */
    public void clearAudios() {
        /* Remove all the media views (videos) from the group */
        for(int i = 0; i < audios.size(); i++) {
            audios.get(i).dispose();
            group.getChildren().remove(audios.get(i));
        }
        
        /* Clear the array list */
        audios.clear();
    }
    
}