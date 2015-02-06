/*
 * Sam Hall and Sam Raeburn
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.data;
/** Class to hold Button Data 
 * @version 1.0 05 Feb 2015
 * @author sh1157 and sr896
 * */
public class ButtonData extends PageObject {
	
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
	private ButtonInteractionType interaction;
	
	/** Contructor for ButtonData */
	public ButtonData(PageObjectType nType, float nXStart, float nYStart) {
		super(nType, nXStart, nYStart);
	}
	
	public float getxEnd() {
		return xEnd;
	}
	
	public void setxEnd(float nXEnd) {
		this.xEnd = nXEnd;
	}
	
	public float getyEnd() {
		return yEnd;
	}
	
	public void setyEnd(float nYEnd) {
		this.yEnd = nYEnd;
	}
	
	public float getOutlineThickness() {
		return outlineThickness;
	}
	
	public void setOutlineThickness(float outlineThickness) {
		this.outlineThickness = outlineThickness;
	}
	
	public boolean isOutline() {
		return outline;
	}
	
	public void setOutline(boolean outline) {
		this.outline = outline;
	}
	
	public boolean isShadow() {
		return shadow;
	}
	
	public void setShadow(boolean shadow) {
		this.shadow = shadow;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String nText) {
		this.text = nText;
	}
	
	public int getFunction() {
		return function;
	}
	
	public void setFunction(int nFunction) {
		this.function = nFunction;
	}
	
	public ButtonInteractionType getInteraction() {
		return interaction;
	}
	
	public void setInteraction(ButtonInteractionType nInteraction) {
		this.interaction = nInteraction;
	}

}