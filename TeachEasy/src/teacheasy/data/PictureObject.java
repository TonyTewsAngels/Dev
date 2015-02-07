/*
 * Sam Hall and Sam Raeburn
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.data;

import teacheasy.data.PageObject.PageObjectType;

/**
 * A class to hold Picture Data
 * @author sh1157 sr896
 *
 */
public class PictureObject extends PageObject {
	String url;
	String uri;
	float xScaleFactor, yScaleFactor;
	float rotation;
	
	/** Constructor Method */
	public PictureObject (PageObjectType nType, float nXStart, float nYStart) {
		/* Must call the constructor method of superclass */
		super(PageObjectType.PICTURE, nXStart, nYStart);	
	}
	
	/** Method to get the URL */
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String nUrl) {
		this.url = nUrl;
	}
	
	public String getUri() {
		return uri;
	}
	
	public void setUri(String nUri) {
		this.uri = nUri;
	}
	
	public float getyScaleFactor() {
		return yScaleFactor;
	}
	public void setyScaleFactor(float yScaleFactor) {
		this.yScaleFactor = yScaleFactor;
	}
	
	public float getRotation() {
		return rotation;
	}
	
	public void setRotation(float nRotation) {
		this.rotation = nRotation;
	}
	
	public float getxScaleFactor() {
		return xScaleFactor;
	}
}
