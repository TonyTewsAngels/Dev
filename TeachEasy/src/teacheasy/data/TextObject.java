/*
 * Daniel Behre & Jake Ransom
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.data;

import java.util.ArrayList;
import java.util.List;

/**
 *This class encapsulates the data for a text object.
 *
 * @version 	1.0 05 Feb 2015
 * @author 	Daniel Berhe & Jake Ransom
 */
public class TextObject extends PageObject {
    public List<RichText> textFragments;
    
    private float xEnd, yEnd;
    private String font;
	private int fontSize;
	private String color;
	private float duration, startTime;
	private String sourceFile;
	
	/** Constructor method */
    public TextObject(float nXStart, float nYStart, float nXEnd, float nYEnd,  
                      String nFont, int nFontSize, String nColor, String nSourceFile,
                      float nDuration, float nStartTime) {
        super(PageObjectType.TEXT, nXStart, nYStart);
        
        this.xEnd = nXEnd;
        this.yEnd = nYEnd;
        this.font = nFont;
        this.fontSize = nFontSize;
        this.color = nColor;
        this.duration = nDuration;
        this.startTime = nStartTime;
        this.sourceFile = nSourceFile;
        
        textFragments = new ArrayList<RichText>();
    }
	
	/** Old Compatibility Constructor method, deprecated */
	public TextObject(float nXStart, float nYStart, String nSourceFile, 
	                  String nFont, int nFontSize, String nColor) {
		super(PageObjectType.TEXT, nXStart, nYStart);
		
		this.sourceFile = nSourceFile;
		this.font = nFont;
		this.fontSize = nFontSize;
		this.color = nColor;
		
		textFragments = new ArrayList<RichText>();
	}

	/* Get and set functions */
	public float getXEnd() {
	    return xEnd;
	}
	
	public void setXEnd(float nXEnd) {
        this.xEnd = nXEnd;
    }
	
	public float getYEnd() {
        return yEnd;
    }
    
    public void setYEnd(float nYEnd) {
        this.yEnd = nYEnd;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String nFont) {
        this.font = nFont;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int nFontSize) {
        this.fontSize = nFontSize;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String nColor) {
        this.color = nColor;
    }
    
    public float getDuration() {
        return duration;
    }
    
    public void setDuration(float nDuration) {
        this.duration = nDuration;
    }
    
    public float getStartTime() {
        return startTime;
    }
    
    public void setStartTime(float nStartTime) {
        this.startTime = nStartTime;
    }
    
    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String nSourceFile) {
        this.sourceFile = nSourceFile;
    }
    
    public void debugPrint() {
        super.debugPrint();
        
        System.out.println(", Font " + font + 
                           ", Font Size " + fontSize + 
                           ", Color " + color +
                           ", Sourcefile " + sourceFile + ".\n");
        for(int i = 0; i < textFragments.size(); i++) {
            if(textFragments.get(i).isNewLine()) {
                System.out.print("\n");
            }
            
            System.out.print(textFragments.get(i).getText());
        }
        
        System.out.print("\n\n");
    }
}
