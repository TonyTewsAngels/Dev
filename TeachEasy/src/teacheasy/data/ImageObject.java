/*
 * Sam Raeburn & Sam Hall
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 */
package teacheasy.data;

/**
 * Encapsulates the data that describes an Image Object
 * as defined in the TeachEasy digital lesson XML format.
 * Extends the page object supertype.
 * 
 * @author  Sam Raeburn and Sam Hall
 * @version 1.2 13 Feb 2015
 */
public class ImageObject extends PageObject {
    
    /** Sourcefile location as a string */
	private String sourcefile;
	
	/* Data variables */
	private float xEnd;
	private float yEnd;
	private float xScaleFactor; // Compatibility only
	private float yScaleFactor; // Compatibility only
	private float rotation;
	private float startTime;
	private float duration;
	
	/** 
	 * Constructor to create the data object with the data parsed
     * from XML.
	 * 
	 * @param nXStart Relative X axis start position.
	 * @param nYStart Relative Y axis start position.
	 * @param nXEnd Relative X axis end position.
	 * @param nYEnd Relative Y axis end position.
	 * @param nSourcefile Image file path.
	 * @param nXScaleFactor X Scale as a proportion of image size.
	 * @param nYScaleFactor Y Scale as a proportion of image size.
	 * @param nRotation Rotation in degrees.
	 * @param nStartTime Start time of object (not used).
	 * @param nDuration End time of object (not used).
	 */
	public ImageObject (float nXStart, float nYStart,
	                    float nXEnd, float nYEnd,
	                    String nSourcefile,
                        float nXScaleFactor, float nYScaleFactor, 
                        float nRotation,
                        float nStartTime, float nDuration) {
    
        /* Call the superconstructor */
        super(PageObjectType.IMAGE, nXStart, nYStart);
        
        /* Instantiate class level data variables */
        this.sourcefile = nSourcefile;
        this.xEnd = nXEnd;
        this.yEnd = nYEnd;
        this.xScaleFactor = nXScaleFactor;
        this.yScaleFactor = nYScaleFactor;
        this.rotation = nRotation;
        this.duration = nDuration;
        this.startTime = nStartTime;
    }
	
	/** Gets the sourcefile file path */
	public String getSourcefile() {
		return sourcefile;
	}
	
	/** Sets the sourcefile file path */
	public void setSourcefile(String nSourcefile) {
		this.sourcefile = nSourcefile;
	}
	
	/** Gets the relative X axis end position */
    public float getXEnd() {
        return xEnd;
    }
    
    /** Sets the relative X axis end positon */
    public void setXEnd(float nXEnd) {
        xEnd = nXEnd;
    }
    
    /** Gets the relative Y axis end position */
    public float getYEnd() {
        return yEnd;
    }

    /** Sets the relative Y axis end position */
    public void setYEnd(float nYEnd) {
        yEnd = nYEnd;
    }
	
	/** Gets the X axis scale factor */
	public float getxScaleFactor() {
		return xScaleFactor;
	}
	
	/** Sets the X axis scale factor */
	public void setxScaleFactor(float nXScaleFactor) {
		this.xScaleFactor = nXScaleFactor;
	}
	
	/** Gets the Y axis scale factor */
	public float getyScaleFactor() {
		return yScaleFactor;
	}
	
	/** Sets the Y axis scale factor */
	public void setyScaleFactor(float nYScaleFactor) {
		this.yScaleFactor = nYScaleFactor;
	}
	
	/** Gets the rotation in degrees */
	public float getRotation() {
		return rotation;
	}
	
	/** Sets the rotation in degrees */
	public void setRotation(float nRotation) {
		this.rotation = nRotation;
	}
	
	/** Gets the start time (not used) */
    public float getStartTime() {
        return startTime;
    }

    /** Sets the start time (not used) */
    public void setStartTime(float nStartTime) {
        this.startTime = nStartTime;
    }
	
	/** Gets the duration (not used) */
    public float getDuration() {
        return duration;
    }
    
    /** Sets the duration (not used) */
    public void setDuration(float nDuration) {
        this.duration = nDuration;
    }
    
	/** Prints information about the object to the screen */
	public void debugPrint() {
	    /* Print the supertype data  */
	    super.debugPrint();
	    
	    /* Print the data variables */
	    System.out.println(", Sourcefile " + sourcefile + 
	                       ", X End " + xEnd + 
	                       ", Y End " + yEnd + 
	                       ", Rotation " + rotation + ".\n");
	}
}
























