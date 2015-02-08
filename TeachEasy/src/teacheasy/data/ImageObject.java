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
	String url;
	String uri;
	float xScaleFactor, yScaleFactor;
	float rotation;
	
	/** Constructor Method */
	public ImageObject (float nXStart, float nYStart, String nUrl, String nUri,
						float nXScaleFactor, float nYScaleFactor, float nRotation) {
		
		/* Must call the constructor method of superclass */
		super(PageObjectType.PICTURE, nXStart, nYStart);
		
		/* Instantiate class level variables */
		this.url = nUrl;
		this.uri = nUri;
		this.xScaleFactor = nXScaleFactor;
		this.yScaleFactor = nYScaleFactor;
		this.rotation = nRotation;
	}
	
	/** Method to get the URL */
	public String getUrl() {
		return url;
	}
	
	/** Method to set the URL */
	public void setUrl(String nUrl) {
		this.url = nUrl;
	}
	
	/** Method to get the URI */
	public String getUri() {
		return uri;
	}
	
	/** Method to set the URI */
	public void setUri(String nUri) {
		this.uri = nUri;
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

