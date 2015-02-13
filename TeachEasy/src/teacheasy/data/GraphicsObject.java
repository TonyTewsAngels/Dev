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
		OVAL,
		RECTANGLE,
		LINE
	}
	
	/** Graphics Variables */
	private GraphicType type;
	private float XEnd;
	private float YEnd;
	private float rotation;
	private String graphicColor;
	private boolean solid;
	private float outlineThickness;
	private boolean shadow;
	
	/** Constructor Method */
	public GraphicsObject(GraphicType nType, float nXStart, float nYStart, float nXEnd, 
						  float nYEnd, float nRotation, String nGraphicColor,   
						  boolean nSolid, float nOutlineThickness, boolean nShadow) {
	    
		super(PageObjectType.GRAPHIC, nXStart, nYStart);
		
		this.type = nType;
		this.XEnd = nXEnd;
		this.YEnd = nYEnd;
		this.rotation = nRotation;
		this.graphicColor = nGraphicColor;
		this.solid = nSolid;
		this.outlineThickness = nOutlineThickness;
		this.shadow = nShadow;
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

	public String getGraphicColour() {
		return graphicColor;
	}

	public void setGraphicColor(String nGraphicColor) {
		this.graphicColor = nGraphicColor;
	}

	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean nSolid) {
		this.solid = nSolid;
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
