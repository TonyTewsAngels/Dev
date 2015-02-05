/*
 * Lewis Thresh & Calum Armstrong
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.data;

/**
 * This class extends PageObject and encapsulates a single graphics object.
 * 
 * @version 	1.0 05 Feb 2015
 * @author 		Lewis Thresh & Calum Armstrong
 */
public class GraphicsObject extends PageObject {

	/** Enumeration of the various graphic types */
	public static enum GraphicType {
		CIRCLE,
		SQUARE,
		TRIANGLE
	}
	
	/** Graphics Variables */
	private GraphicType type;
	private float XEnd;
	private float YEnd;
	private float rotation;
	private int fillColour;
	private boolean outline;
	private float outlineThickness;
	private boolean shadow;
	
	public GraphicsObject(float nXStart, float nYStart) {
		super(PageObjectType.GRAPHIC, nXStart, nYStart);
	}
	
	/* Getters and Setters for Graphics variables */
	
	public GraphicType getType() {
		return type;
	}
	
	public void setType(GraphicType nType) {
		this.type = nType;
	}

	public float getXEnd() {
		return XEnd;
	}
	
	public void setXEnd(float nXEnd) {
		XEnd = nXEnd;
	}
	
	public float getYEnd() {
		return YEnd;
	}

	public void setYEnd(float nYEnd) {
		YEnd = nYEnd;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float nRotation) {
		this.rotation = nRotation;
	}

	public int getFillColour() {
		return fillColour;
	}

	public void setFillColour(int nFillColour) {
		this.fillColour = nFillColour;
	}

	public boolean isOutline() {
		return outline;
	}

	public void setOutline(boolean nOutline) {
		this.outline = nOutline;
	}

	public float getOutlineThickness() {
		return outlineThickness;
	}

	public void setOutlineThickness(float nOutlineThickness) {
		this.outlineThickness = nOutlineThickness;
	}

	public boolean isShadow() {
		return shadow;
	}

	public void setShadow(boolean nShadow) {
		this.shadow = nShadow;
	}

}