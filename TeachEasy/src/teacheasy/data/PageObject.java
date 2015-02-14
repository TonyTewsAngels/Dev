/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.data;

/**
 * This class encapsulates a single object 
 * on a page.
 * 
 * @version 	1.0 05 Feb 2015
 * @author 		Alistair Jewers
 */
public abstract class PageObject {
	
	/** Enumeration of the various page object types */
	public static enum PageObjectType {
		TEXT,
		PICTURE,
		GRAPHIC,
		AUDIO,
		VIDEO,
		ANSWER_BOX,
		MULTIPLE_CHOICE,
		BUTTON
	}
	
	/** The type of this page object */
	private PageObjectType type;
	
	/** Relative X and Y position, measured from top left */
	private float xStart;
	private float yStart;
	
	/**
	 * Constructor Method
	 * 
	 * @param nType - The type of this page object
	 * @param nXStart - Relative X position
	 * @param nYStart - Relative Y position
	 */
	public PageObject(PageObjectType nType, float nXStart, float nYStart) {
		this.type = nType;
		this.xStart = nXStart;
		this.yStart = nYStart;
	}
	
	/** Prints information about the object to the console */
	public void debugPrint() {
	    System.out.println("Object Type: " + type);
	}
}
