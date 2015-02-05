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
	float xScaleFactor, yScaleFactor, rotation;
	
	/** Constructor Method */
	public PictureObject (PageObjectType nType, float nXStart, float nYStart) {
		/* Must call the constructor method of superclass */
		super(PageObjectType.PICTURE, nXStart, nYStart);	
	}
	
	/** Method to get the URL */
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
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

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public float getxScaleFactor() {
		return xScaleFactor;
	}


	
}
