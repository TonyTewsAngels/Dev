/*
 * Sam Hall and Sam Raeburn
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.data;

/** 
 * Class to hold Button Data 
 * 
 * @version 1.0 05 Feb 2015
 * @author sh1157 and sr896
 * */
public class ButtonObject extends PageObject {
	
	private float xEnd, yEnd;
	private boolean visible;
	private String text;
	private int function;
	
	/** Constructor for ButtonData */
	public ButtonObject(float nXStart, float nYStart, float nXEnd, float nYEnd,
					    boolean nVisible, String nText, int nFunction) {
		
		/* Call superconstructor */
		super(PageObjectType.BUTTON, nXStart, nYStart);
		
		/* Instantiate class level variables */
		this.xEnd = nXEnd;
		this.yEnd = nYEnd;
		this.visible = nVisible;
		this.text = nText;
		this.function = nFunction;
	}
	
	/** Get X end position */
	public float getxEnd() {
		return xEnd;
	}
	
	/** Set X end position */
	public void setxEnd(float nXEnd) {
		this.xEnd = nXEnd;
	}
	
	/** Get Y end position */
	public float getyEnd() {
		return yEnd;
	}
	
	/** Set Y end position */
	public void setyEnd(float nYEnd) {
		this.yEnd = nYEnd;
	}
	
	/** Get visible flag state */
	public boolean isVisible() {
		return visible;
	}
	
	/** Set visible flag state */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	/** Get text */
	public String getText() {
		return text;
	}
	
	/** Set text */
	public void setText(String nText) {
		this.text = nText;
	}
	
	/** Get function value */
	public int getFunction() {
		return function;
	}
	
	/** Set the function value */
	public void setFunction(int nFunction) {
		this.function = nFunction;
	}
	
	/** Prints information about the object to the screen */
    public void debugPrint() {
        super.debugPrint();
        
        System.out.println(", xEnd " + xEnd + 
                           ", yEnd " + yEnd + 
                           ", Visible " + visible +
                           ", Button Text " + text + 
                           ", Function " + function + ".\n");
    }
}
