/*
 * Sam Hall and Sam Raeburn
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.data;

/**
 * A class to hold Picture Data
 * @author 	sh1157 sr896
 * @version	1.1 08 Feb 2015
 */
public class ImageObject extends PageObject {
	private String sourcefile;
	private float xScaleFactor, yScaleFactor;
	private float rotation;
	
	/** Constructor Method */
	public ImageObject (float nXStart, float nYStart, String nSourcefile,
						float nXScaleFactor, float nYScaleFactor, float nRotation) {
		
		/* Must call the constructor method of superclass */
		super(PageObjectType.PICTURE, nXStart, nYStart);
		
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
}

