/*
 * Sam Raeburn & Sam Hall
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 */
package teacheasy.data;

/**
 * Encapsulates the data that describes an Audio Object
 * as defined in the TeachEasy digital lesson XML format.
 * Extends the page object supertype.
 * 
 * @author  Sam Raeburn and Sam Hall
 * @version 1.2 13 Feb 2015
 */
public class AudioObject extends PageObject {
    
	/** Indicates if the full controls should be visible on screen. */
	boolean viewProgress;
	
	/** Sourcefile location as a string */
	private String sourcefile;
	
	/* Data variables */
	private float xEnd;
	private float startTime;
    private boolean autoPlay;
    private boolean loop;
	
    /**
     * Constructor to create the data object with the data parsed
     * from XML.
     * 
     * @param nXStart Relative X axis start position (0 - 1).
     * @param nYStart Relative Y axis start position (0 - 1).
     * @param nXEnd Relative X axis end position (0 - 1).
     * @param nSourcefile Source file path as a string.
     * @param nStartTime Start time (not used).
     * @param nViewProgress Control display setting.
     * @param nAutoPlay Autoplay setting.
     * @param nLoop Loop setting.
     */
	public AudioObject(float nXStart, float nYStart,
	                   float nXEnd,
	                   String nSourcefile,
	                   float nStartTime,
	                   boolean nViewProgress, 
	                   boolean nAutoPlay, 
	                   boolean nLoop) {
    
	    /* Call the superconstructor */
        super(PageObjectType.AUDIO, nXStart, nYStart);
        
        /* Initialise data variables */
        this.xEnd = nXEnd;
        this.viewProgress = nViewProgress;
        this.sourcefile = nSourcefile;
        this.startTime = nStartTime;
        this.autoPlay = nAutoPlay;
        this.loop = nLoop;
    }
	
	/** Gets the control view setting */
	public boolean isViewProgress() {
		return viewProgress;
	}

	/** Sets the control view setting */
	public void setViewProgress(boolean nViewProgress) {
		this.viewProgress = nViewProgress;
	}
	
	/** Gets the file path of the source media */
	public String getSourcefile() {
		return sourcefile;
	}
	
	/** Sets the file path of the source media */
	public void setSourcefile(String nSourcefile) {
		this.sourcefile = nSourcefile;
	}

	/** Gets the relative X axis end position */
    public float getXEnd() {
        return xEnd;
    }

    /** Sets the relative X axis end position */
    public void setXEnd(float nXEnd) {
        this.xEnd = nXEnd;
    }

    /** Gets the start time */
    public float getStartTime() {
        return startTime;
    }

    /** Sets the start time */
    public void setStartTime(float nStartTime) {
        this.startTime = nStartTime;
    }
    
    /** Checks the auto play setting */
    public boolean isAutoPlay() {
        return autoPlay;
    }

    /** Sets the auto play setting */
    public void setAutoPlay(boolean nAutoPlay) {
        this.autoPlay = nAutoPlay;
    }

    /** Checks the loop setting */
    public boolean isLoop() {
        return loop;
    }

    /** Sets the loop setting */
    public void setLoop(boolean nLoop) {
        this.loop = nLoop;
    }
    
	/** Prints information about the object to the screen */
	public void debugPrint() {
	    /* Print the supertype data */
        super.debugPrint();
        
        /* Print the data variables */
        System.out.println(", X End " + xEnd +
                           ", Sourcefile " + sourcefile + 
                           ", View Progress " + viewProgress +
                           ", Autoplay " + autoPlay +
                           ", Loop " + loop +
                            ".\n");
    }
}

