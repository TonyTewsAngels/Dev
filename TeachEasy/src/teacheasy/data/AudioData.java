/*
 * Sam Raeburn
 * 
 * Copyright (C) 2015 Sofia Software Solutions
 */
package teacheasy.data;

/**
 * A class to hold audio data
 * @author	sr896 sh1157
 * @version	1.1 08 Feb 2015
 * 
 */
public class AudioData extends PageObject {

	/** If true, progress bar is visible on screen */
	boolean viewProgress;
	
	String uri;
	String url;
	
	/** Constructor Method */
	public AudioData (float nXStart, float nYStart, boolean nViewProgress,
					  String nUri, String nUrl) {
	
		super(PageObjectType.AUDIO, nXStart, nYStart);
		this.viewProgress = nViewProgress;
		this.uri = nUri;
		this.url = nUrl;
	}
	
	/** Get the view progress state */
	public boolean isViewProgress() {
		return viewProgress;
	}

	/** Set the view progress state */
	public void setViewProgress(boolean viewProgress) {
		this.viewProgress = viewProgress;
	}
	
	/** Get the URI */
	public String getUri() {
		return uri;
	}
	
	/** Set the URI */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/** Get the URL */
	public String getUrl() {
		return url;
	}
	
	/** Set the URL */
	public void setUrl(String url) {
		this.url = url;
	}
}

