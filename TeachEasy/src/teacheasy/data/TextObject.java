package teacheasy.data;

import java.util.ArrayList;

/**
 @version 	1.0 05 Feb 2015
 @author 	Daniel Berhe & Jake Ransom*/


public class TextObject extends PageObject {
	
	private int 	fontSize;
	private String  fontColour;
	private String  textFont;
	private boolean superscript;
	private boolean subscript;
	private boolean bold;
	private boolean italics;
	private boolean underline;
	private String  textFragment;
	
	ArrayList <String> list; 
	
	public TextObject(float nXStart, float nYStart) {
		super(PageObjectType.TEXT, nXStart, nYStart);
		
		list = new ArrayList <String> ();
		list.add(textFragment);
		
		
	}
	/**
	 * 
	 * @return
	 */
	public int getFontSize() {
		return fontSize;
	}
	
	/**
	 * 
	 * @param newfontSize
	 */
	public void setFontSize(int newfontSize) {
		this.fontSize = newfontSize;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getFontColour() {
		return fontColour;
	}
	
	/**
	 * 
	 * @param newfontColour
	 */
	public void setFontColour(String newfontColour) {
		this.fontColour = newfontColour;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getTextFont() {
		return textFont;
	}
	
	/**
	 * 
	 * @param newtextFont
	 */
	public void setTextFont(String newtextFont) {
		this.textFont = newtextFont;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean get_superscript()
	{
		return superscript;
	}
	
	/**
	 * 
	 * @param superscript
	 */
	public void set_superscript(boolean superscript)
	{
		this.superscript = superscript;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean get_subscript()
	{
		return subscript;
	}
	
	/**
	 * 
	 * @param superscript
	 */
	public void set_subscript(boolean superscript)
	{
		this.superscript = superscript;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean get_bold(){
		return bold;
	}
	
	/**
	 * 
	 * @param bold
	 */
	public void set_bold(boolean bold){
		this.bold = bold;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean italics(){
		return italics;
	}
	
	/**
	 * 
	 * @param italics
	 */
	public void set_italics(boolean italics){
		this.italics = italics;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean get_underline(){
		return underline;
	}
	
	/**
	 * 
	 * @param underline
	 */
	public void set_underline(boolean underline){
		this.underline = underline;
	}
	
	
}
