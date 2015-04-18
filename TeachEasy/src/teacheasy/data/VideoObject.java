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
    private String sourceFile;
    private float xEnd;

    /** Constructor method */
    public VideoObject(float nXStart, float nYStart, float nXEnd, String nSourcefile) {
        super(PageObjectType.VIDEO, nXStart, nYStart);
        
        this.xEnd = nXEnd;
        this.sourceFile = nSourcefile;
    }
    
    /** Old Compatability Constructor method. Deprecated. */
    public VideoObject(String nSourcefile,  float nXStart, float nYStart, String nScreenshotFile) {
        super(PageObjectType.VIDEO, nXStart, nYStart);
        
        this.sourceFile = nSourcefile;
    }
    
    /** Get function for Source File string */
    public String getSourcefile() {
        return sourceFile;
    }

    /** Set function for Source File string */
    public void setSourcefile(String nSourceFile) {
        this.sourceFile = nSourceFile;
    }
    
    /** Prints information about the object to the screen */
    public void debugPrint() {
        super.debugPrint();
        
        System.out.println(", Sourcefile " + sourceFile + ".\n");
    }

    public float getXEnd() {
        return xEnd;
    }

    public void setXEnd(float nXEnd) {
        this.xEnd = nXEnd;
    }
}
