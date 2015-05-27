/*
 * Sam Raeburn
 * 
 * Copyright (C) 2015 Sofia Software Solutions
 */
package teacheasy.data;

/**
 * A class to hold audio data
 * 
 * @author	sr896 sh1157
 * @version	1.2 13 Feb 2015
 */
public class AudioObject extends PageObject {
	/** If true, progress bar is visible on screen */
	boolean viewProgress;
	
	private String sourcefile;
	private float xEnd;
	private float startTime;
    private boolean autoPlay;
    private boolean loop;
	
	public AudioObject(float nXStart, float nYStart,
	                   float nXEnd,
	                   String nSourcefile,
	                   float nStartTime,
	                   boolean nViewProgress, 
	                   boolean autoPlay, 
	                   boolean loop) {
    
        super(PageObjectType.AUDIO, nXStart, nYStart);
        
        this.xEnd = nXEnd;
        this.viewProgress = nViewProgress;
        this.sourcefile = nSourcefile;
        this.startTime = nStartTime;
    }
	
	/** Old compatability Constructor Method. Deprecated */
	public AudioObject(float nXStart, float nYStart, boolean nViewProgress,
					    String nSourcefile) {
	
		super(PageObjectType.AUDIO, nXStart, nYStart);
		this.viewProgress = nViewProgress;
		this.sourcefile = nSourcefile;
	}
	
	/** Get the view progress state */
	public boolean isViewProgress() {
		return viewProgress;
	}

	/** Set the view progress state */
	public void setViewProgress(boolean nViewProgress) {
		this.viewProgress = nViewProgress;
	}
	
	/** Get the URI */
	public String getSourcefile() {
		return sourcefile;
	}
	
	/** Set the URI */
	public void setSourcefile(String nSourcefile) {
		this.sourcefile = nSourcefile;
	}
	
	/** Prints information about the object to the screen */
	public void debugPrint() {
        super.debugPrint();
        
        System.out.println(", Sourcefile " + sourcefile + 
                           ", View Progress " + viewProgress + ".\n");
    }

    public float getXEnd() {
        return xEnd;
    }

    public void setXEnd(float nXEnd) {
        this.xEnd = nXEnd;
    }

    public float getStartTime() {
        return startTime;
    }

    public void setStartTime(float nStartTime) {
        this.startTime = nStartTime;
    }
    
    public boolean isAutoPlay() {
        return autoPlay;
    }

    public void setAutoPlay(boolean nAutoPlay) {
        this.autoPlay = nAutoPlay;
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean nLoop) {
        this.loop = nLoop;
    }
}

