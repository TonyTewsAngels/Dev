/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.data;

/**
 * This class encapsulates the data
 * for a video object.
 * 
 * @version     1.0 13 Feb 2015
 * @author      Alistair Jewers
 */
public class VideoObject extends PageObject{
    private String sourcefile;
    private String screenshotFile;

    /** Constructor method */
    public VideoObject(String nSourcefile,  float nXStart, float nYStart, String nScreenshotFile) {
        super(PageObjectType.VIDEO, nXStart, nYStart);
        
        this.setSourcefile(nSourcefile);
        this.setScreenshotFile(nScreenshotFile);
    }
    
    /** Get function for sourcefile string */
    public String getSourcefile() {
        return sourcefile;
    }

    /** Set function for sourcefile string */
    public void setSourcefile(String nSourcefile) {
        this.sourcefile = nSourcefile;
    }

    /** Get function for screenshot string */
    public String getScreenshotFile() {
        return screenshotFile;
    }

    /** Set function for screenshot string */
    public void setScreenshotFile(String nScreenshotFile) {
        this.screenshotFile = nScreenshotFile;
    }
    
    /** Prints information about the object to the screen */
    public void debugPrint() {
        super.debugPrint();
        
        System.out.println(", Sourcefile " + sourcefile + 
                           ", Screen Shot " + screenshotFile + ".\n");
    }
}
