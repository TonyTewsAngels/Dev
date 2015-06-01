/*
 * Daniel Behre & Jake Ransom
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 */
package teacheasy.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates the data that describes a Text Object 
 * as defined in the TeachEasy digital lesson XML format.
 * Extends the page object supertype.
 * 
 * @author 	Daniel Berhe & Jake Ransom
 * @version 1.0 05 Feb 2015
 */
public class TextObject extends PageObject {
    /** A list of the text fragments in this text box */
    public List<RichText> textFragments;
    
    /* Data variables */
    private float xEnd, yEnd;
    private float duration, startTime;
    private int fontSize;
    private String font;
	private String color;
	
	/** Path of the source text file as a string */
	private String sourceFile;
	
	/**
     * Constructor to create the data object with the data parsed
     * from XML.
	 * 
     * @param nXStart Relative X axis start position (0 - 1).
     * @param nYStart Relative Y axis start position (0 - 1).
     * @param nXEnd Relative X axis end position (0 - 1).
	 * @param nYEnd Relative Y axis end position (0 - 1).
	 * @param nFont The font of the text box.
	 * @param nFontSize The size of the text.
	 * @param nColor The colour of the text in #AARRGGBB string format.
	 * @param nSourceFile The source file path as a string.
	 * @param nDuration The duration (not used).
	 * @param nStartTime The start time (not used).
	 */
    public TextObject(float nXStart, float nYStart, float nXEnd, float nYEnd,  
                      String nFont, int nFontSize, String nColor, String nSourceFile,
                      float nDuration, float nStartTime) {
        /* Call the super constructor */
        super(PageObjectType.TEXT, nXStart, nYStart);
        
        /* Initialise the data variables */
        this.xEnd = nXEnd;
        this.yEnd = nYEnd;
        this.font = nFont;
        this.fontSize = nFontSize;
        this.color = nColor;
        this.duration = nDuration;
        this.startTime = nStartTime;
        this.sourceFile = nSourceFile;
        
        /* Instantiate the array list of text fragments */
        textFragments = new ArrayList<RichText>();
    }

	/** Gets the relative X axis end position */
	public float getXEnd() {
	    return xEnd;
	}
	
	/** Sets the relative X axis end position */
	public void setXEnd(float nXEnd) {
        this.xEnd = nXEnd;
    }
	
	/** Gets the relative Y axis end position */
	public float getYEnd() {
        return yEnd;
    }
    
	/** Sets the relative Y axis end position */
    public void setYEnd(float nYEnd) {
        this.yEnd = nYEnd;
    }

    /** Gets the font */
    public String getFont() {
        return font;
    }

    /** Sets the font */
    public void setFont(String nFont) {
        this.font = nFont;
    }

    /** Gets the font size */
    public int getFontSize() {
        return fontSize;
    }

    /** Sets the font size */
    public void setFontSize(int nFontSize) {
        this.fontSize = nFontSize;
    }

    /** Gets the text colour in #AARRGGBB string format */
    public String getColor() {
        return color;
    }

    /** Sets the text colour in #AARRGGBB string format */
    public void setColor(String nColor) {
        this.color = nColor;
    }
    
    /** Gets the start time (not used) */
    public float getStartTime() {
        return startTime;
    }
    
    /** Sets the start time (not used) */
    public void setStartTime(float nStartTime) {
        this.startTime = nStartTime;
    }
    
    /** Gets the duration (not used) */
    public float getDuration() {
        return duration;
    }
    
    /** Sets the duration (not used) */
    public void setDuration(float nDuration) {
        this.duration = nDuration;
    }
    
    /** Gets the source file path */
    public String getSourceFile() {
        return sourceFile;
    }

    /** Sets the source file path */
    public void setSourceFile(String nSourceFile) {
        this.sourceFile = nSourceFile;
    }
    
    /** Prints the objects data to the console */
    public void debugPrint() {
        /* Print the superclass data */
        super.debugPrint();
        
        /* Print the data variables */
        System.out.println(", Font " + font + 
                           ", Font Size " + fontSize + 
                           ", Color " + color +
                           ", Sourcefile " + sourceFile + ".\n");
        
        /* Print all the text fragments */
        for(RichText frag : textFragments) {
            System.out.print(frag.getText());
            
            if(frag.isNewLine()) {
                System.out.print("\n");
            }
        }
        
        /* Print two new lines */
        System.out.print("\n\n");
    }
}
