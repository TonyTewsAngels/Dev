/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 */
package teacheasy.data;

/**
 * Encapsulates the data that describes a Video Object 
 * as defined in the TeachEasy digital lesson XML format.
 * Extends the page object supertype.
 * 
 * @author  Alistair Jewers
 * @version 1.0 13 Feb 2015 
 */
public class VideoObject extends PageObject{
    /** Video source file path as a string */
    private String sourceFile;
    
    /* Data variables */
    private float xEnd;
    private boolean autoPlay;
    private boolean loop;

    /**
     * Constructor to create the data object with the data parsed
     * from XML.
     * 
     * @param nXStart Relative X axis start position (0 - 1).
     * @param nYStart Relative Y axis start position (0 - 1).
     * @param nXEnd Relative X axis end position (0 - 1).
     * @param nSourcefile The source file path as a string.
     * @param nAutoPlay The auto play setting.
     * @param nLoop The loop setting.
     */
    public VideoObject(float nXStart, float nYStart, float nXEnd, String nSourcefile, boolean nAutoPlay, boolean nLoop) {
        /* Call the superconstructor */
        super(PageObjectType.VIDEO, nXStart, nYStart);
        
        /* Initialise the data variables */
        this.xEnd = nXEnd;
        this.sourceFile = nSourcefile;
        this.autoPlay = nAutoPlay;
        this.loop = nLoop;
    }
    
    /** Gets for source file path */
    public String getSourcefile() {
        return sourceFile;
    }

    /** Sets for source file path */
    public void setSourcefile(String nSourceFile) {
        this.sourceFile = nSourceFile;
    }

    /** Gets the relative X axis end position*/
    public float getXEnd() {
        return xEnd;
    }

    /** Sets the relative X axis end position*/
    public void setXEnd(float nXEnd) {
        this.xEnd = nXEnd;
    }

    /** Checks the auto play setting */
    public boolean isAutoPlay() {
        return autoPlay;
    }

    /** Sets the auto play setting */
    public void setAutoPlay(boolean nAutoPlay) {
        this.autoPlay = nAutoPlay;
    }

    /** Checks the loop setting */
    public boolean isLoop() {
        return loop;
    }

    /** Sets the loop setting */
    public void setLoop(boolean nLoop) {
        this.loop = nLoop;
    }
    
    /** Prints information about the object to the screen */
    public void debugPrint() {
        /* Print the superclass data */
        super.debugPrint();
        
        /* Print the data variables */
        System.out.println(", X End " + xEnd +
                           ", Sourcefile " + sourceFile +
                           ", Auto play " + autoPlay +
                           ", Loop " + loop +
                           ".\n");
    }
}
