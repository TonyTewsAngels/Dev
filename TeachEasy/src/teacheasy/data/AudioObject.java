/*
 * Sam Raeburn
 * 
 * Copyright (C) 2015 Sofia Software Solutions
 */
package teacheasy.data;

/**
 * A class to hold audio data
 * 
 * @author	sr896 sh1157
 * @version	1.2 13 Feb 2015
 */
public class AudioObject extends PageObject {
	/** If true, progress bar is visible on screen */
	boolean viewProgress;
	
	private String sourcefile;
	
	/** Constructor Method */
	public AudioObject (float nXStart, float nYStart, boolean nViewProgress,
					    String nSourcefile) {
	
		super(PageObjectType.AUDIO, nXStart, nYStart);
		this.viewProgress = nViewProgress;
		this.sourcefile = nSourcefile;
	}
	
	/** Get the view progress state */
	public boolean isViewProgress() {
		return viewProgress;
	}

	/** Set the view progress state */
	public void setViewProgress(boolean nViewProgress) {
		this.viewProgress = nViewProgress;
	}
	
	/** Get the URI */
	public String getSourcefile() {
		return sourcefile;
	}
	
	/** Set the URI */
	public void setSourcefile(String nSourcefile) {
		this.sourcefile = nSourcefile;
	}
	
	/** Prints information about the object to the screen */
	public void debugPrint() {
        super.debugPrint();
        
        System.out.println(", Sourcefile " + sourcefile + 
                           ", View Progress " + viewProgress + ".\n");
    }
}

