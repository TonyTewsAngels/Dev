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
public class ButtonData extends PageObject {
	
	/** Enumeration of the button interaction types */
	public static enum ButtonInteractionType {
		NORMAL
	}
	
	private float xEnd, yEnd;
	private float outlineThickness;
	private boolean outline;
	private boolean shadow;
	private boolean visible;
	private String text;
	private int function;
	
	/** The button interaction type */
	private ButtonInteractionType interaction;
	
	/** Constructor for ButtonData */
	public ButtonData(float nXStart, float nYStart, float nXEnd, float nYEnd,
					  float nOutlineThickness, boolean nOutline, boolean nShadow,
					  boolean nVisible, String nText, int nFunction, 
					  ButtonInteractionType nInteraction) {
		
		/* Call superconstructor */
		super(PageObjectType.BUTTON, nXStart, nYStart);
		
		/* Instantiate class level variables */
		this.xEnd = nXEnd;
		this.yEnd = nYEnd;
		this.outlineThickness = nOutlineThickness;
		this.outline = nOutline;
		this.shadow = nShadow;
		this.visible = nVisible;
		this.text = nText;
		this.function = nFunction;
		this.interaction = nInteraction;
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
	
	/** Get outline thickness */
	public float getOutlineThickness() {
		return outlineThickness;
	}
	
	/** Set outline thickness */
	public void setOutlineThickness(float outlineThickness) {
		this.outlineThickness = outlineThickness;
	}
	
	/** Get outlines flag state */
	public boolean isOutline() {
		return outline;
	}
	
	/** Set outline flag */
	public void setOutline(boolean outline) {
		this.outline = outline;
	}
	
	/** Get shadow flag state */
	public boolean isShadow() {
		return shadow;
	}
	
	/** Set shadow flag state */
	public void setShadow(boolean shadow) {
		this.shadow = shadow;
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
	
	/** Get interaction type */
	public ButtonInteractionType getInteraction() {
		return interaction;
	}
	
	/** Set the interaction type */
	public void setInteraction(ButtonInteractionType nInteraction) {
		this.interaction = nInteraction;
	}
}
