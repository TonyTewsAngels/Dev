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
    private List<RichText> text;
    private String sourceFile;
    private String font;
	private int fontSize;
	private String color;
	
	/** Constructor method */
	public TextObject(float nXStart, float nYStart, String nSourceFile, 
	                  String nFont, int nFontSize, String nColor) {
		super(PageObjectType.TEXT, nXStart, nYStart);
		
		this.sourceFile = nSourceFile;
		this.font = nFont;
		this.fontSize = nFontSize;
		this.color = nColor;
		
		text = new ArrayList<RichText>();
	}
	
	/** Add a rich text fragment */
	public void addText(RichText nText, int index) {
	    text.add(index, nText);
	}
	
	/** Add a rich text fragment at the end */
    public void addText(RichText nText) {
        text.add(nText);
    }
	
	/** Remove a rich text fragment */
	public void removeText(int index) {
	    text.remove(index);
	}

	/* Get and set functions */
	
    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String nSourceFile) {
        this.sourceFile = nSourceFile;
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
    
    public void debugPrint() {
        super.debugPrint();
        
        System.out.println(", Font " + font + 
                           ", Font Size " + fontSize + 
                           ", Color " + color +
                           ", Sourcefile " + sourceFile + ".\n");
        for(int i = 0; i < text.size(); i++) {
            if(text.get(i).isNewLine()) {
                System.out.print("\n");
            }
            
            System.out.print(text.get(i).getText());
        }
        
        System.out.print("\n\n");
    }
}
