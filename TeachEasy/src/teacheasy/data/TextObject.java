package teacheasy.data;

import java.util.ArrayList;

/**
 @version 	1.0 05 Feb 2015
 @author 	Daniel Berhe & Jake Ransom*/


public class TextObject extends PageObject {
	private int fontSize;
	private String fontColour;
	private String textFont;
	ArrayList <String> list; 
	
	public TextObject(float nXStart, float nYStart) {
		super(PageObjectType.TEXT, nXStart, nYStart);
		
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int newfontSize) {
		this.fontSize = newfontSize;
	}

	public String getFontColour() {
		return fontColour;
	}

	public void setFontColour(String newfontColour) {
		this.fontColour = newfontColour;
	}

	public String getTextFont() {
		return textFont;
	}

	public void setTextFont(String newtextFont) {
		this.textFont = newtextFont;
	}
	
	
	private void storeString(){
		list = new ArrayList <String> ();
	}
	
}
