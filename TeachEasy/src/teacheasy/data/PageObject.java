/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.data;

/**
 * Encapsulates the data that describes a Page Object 
 * as defined in the TeachEasy digital lesson XML format.
 * Extended by all the various page object types.
 * 
 * @version 1.0 05 Feb 2015
 * @author  Alistair Jewers
 */
public abstract class PageObject {
	
	/** Enumeration of the various page object types */
	public static enum PageObjectType {
		TEXT,
		IMAGE,
		GRAPHIC,
		AUDIO,
		VIDEO,
		ANSWER_BOX,
		MULTIPLE_CHOICE,
	}
	
	/** The type of this page object */
	private PageObjectType type;
	
	/** 
	 * Relative X axis start position, 
	 * measured from top left as a proportion of the page. 
	 */
	private float xStart;
	
	/** 
     * Relative Y axis start position, 
     * measured from top left as a proportion of the page. 
     */
	private float yStart;
	
	/**
	 * Constructor to initialise the super type data.
	 * 
	 * @param nType The type of this page object.
	 * @param nXStart Relative X axis start position.
	 * @param nYStart Relative Y axis start position.
	 */
	public PageObject(PageObjectType nType, float nXStart, float nYStart) {
	    /* Initialise the data variables */
		this.type = nType;
		this.xStart = nXStart;
		this.yStart = nYStart;
	}
	
	/** Gets the relative X axis start position */
	public float getXStart() {
	    return xStart;
	}
	
	/** Sets the relative X axis start position */
	public void setXStart(float nXStart) {
        this.xStart = nXStart;
    }
	
	/** Gets the relative Y axis start position */
	public float getYStart() {
	    return yStart;
	}

	/** Sets the relative Y axis start position */
    public void setYStart(float nYStart) {
        this.yStart = nYStart;
    }
	
	/** Gets the type of this page object */
	public PageObjectType getType() {
	    return type;
	}
	
	/** Prints information about the object to the console */
	public void debugPrint() {
	    System.out.print(type + ": \n" + "xStart " + xStart + ", yStart " + yStart);
	}
}
