/*
 * Sam Hall and Sam Raeburn
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.data;

/**
 * A class to hold Picture Data
 * 
 * @author 	sh1157 sr896
 * @version	1.2 13 Feb 2015
 */
public class ImageObject extends PageObject {
	private String sourcefile;
	private float xEnd, yEnd;
	private float xScaleFactor, yScaleFactor;
	private float rotation;
	private float startTime;
	private float duration;
	
	/** Constructor Method */
	public ImageObject (float nXStart, float nYStart,
	                    float nXEnd, float nYEnd,
	                    String nSourcefile,
                        float nXScaleFactor, float nYScaleFactor, 
                        float nRotation,
                        float nStartTime, float nDuration) {
    
        /* Must call the constructor method of superclass */
        super(PageObjectType.IMAGE, nXStart, nYStart);
        
        /* Instantiate class level variables */
        this.sourcefile = nSourcefile;
        this.xEnd = nXEnd;
        this.yEnd = nYEnd;
        this.xScaleFactor = nXScaleFactor;
        this.yScaleFactor = nYScaleFactor;
        this.rotation = nRotation;
        this.duration = nDuration;
        this.startTime = nStartTime;
    }
	
	/** Old Compatibility Constructor Method. Deprecated. */
	public ImageObject (float nXStart, float nYStart, String nSourcefile,
						float nXScaleFactor, float nYScaleFactor, float nRotation) {
		
		/* Must call the constructor method of superclass */
		super(PageObjectType.IMAGE, nXStart, nYStart);
		
		/* Instantiate class level variables */
		this.sourcefile = nSourcefile;
		this.xScaleFactor = nXScaleFactor;
		this.yScaleFactor = nYScaleFactor;
		this.rotation = nRotation;
	}
	
	/** Method to get the URL */
	public String getSourcefile() {
		return sourcefile;
	}
	
	/** Method to set the URL */
	public void setSourcefile(String nSourcefile) {
		this.sourcefile = nSourcefile;
	}
	
	/** Method to get the X End position */
	public float getXEnd() {
	    return xEnd;
	}
	
	/** Method to set the X End position */
	public void setXEnd(float nXEnd) {
	    this.xEnd = nXEnd;
	}
	
	/** Method to get the Y End position */
    public float getYEnd() {
        return yEnd;
    }
    
    /** Method to set the Y End position */
    public void setYEnd(float nXEnd) {
        this.yEnd = nXEnd;
    }
	
	/** Method to get the x scale factor */
	public float getxScaleFactor() {
		return xScaleFactor;
	}
	
	/** Method to set the x scale factor */
	public void setxScaleFactor(float nXScaleFactor) {
		this.xScaleFactor = nXScaleFactor;
	}
	
	/** Method to get the y scale factor */
	public float getyScaleFactor() {
		return yScaleFactor;
	}
	
	/** Method to set the y scale factor */
	public void setyScaleFactor(float nYScaleFactor) {
		this.yScaleFactor = nYScaleFactor;
	}
	
	/** Method to get the rotation */
	public float getRotation() {
		return rotation;
	}
	
	/** Method to set the rotation */
	public void setRotation(float nRotation) {
		this.rotation = nRotation;
	}
	
	/** Method to get the duration */
    public float getDuration() {
        return duration;
    }
    
    /** Method to set the duration */
    public void setDuration(float nDuration) {
        this.duration = nDuration;
    }
    
    /** Method to get the start time */
    public float geStartTime() {
        return startTime;
    }
    
    /** Method to set the X End position */
    public void setStartTime(float nStartTime) {
        this.startTime = nStartTime;
    }
	
	/** Prints information about the object to the screen */
	public void debugPrint() {
	    super.debugPrint();
	    
	    System.out.println(", Sourcefile " + sourcefile + 
	                       ", xScale " + xScaleFactor + 
	                       ", yScale " + yScaleFactor + 
	                       ", Rotation " + rotation + ".\n");
	}
}
























