package teacheasy.data;

/**
 * A class to hold audio data
 * @author sr896 sh1157
 *
 */
public class AudioData extends PageObject {

	boolean viewProgress;
	String uri;
	String url;
	
	/**Constructor Method*/
	public AudioData (PageObjectType nType, float nXStart, float nYStart) {
	
		super(PageObjectType.AUDIO, nXStart, nYStart);
	}
	
	public boolean isViewProgress() {
		return viewProgress;
	}

	public void setViewProgress(boolean viewProgress) {
		this.viewProgress = viewProgress;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
