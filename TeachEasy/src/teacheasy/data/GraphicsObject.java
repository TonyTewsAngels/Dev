/*
 * Lewis Thresh & Calum Armstrong
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.data;

import teacheasy.xml.XMLElement;

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
		LINE;
		
		public static GraphicType check(String str) {
	        try {
	            return valueOf(str);
	        } catch (Exception ex) {
	            return LINE;
	        }
	    }
	}
	
	/** Enumeration of the various shading types */
	public static enum Shading {
	    NONE,
	    CYCLIC
	}
	
	/** Graphics Variables */
	private GraphicType type;
	private float xEnd;
	private float yEnd;
	private float rotation;
	private String graphicColor;
	private boolean solid;
	private float outlineThickness;
	private boolean shadow;
	
	private Shading shading;
	private String shadingColor;
	
	/** Constructor Method */
	public GraphicsObject(GraphicType nType, float nXStart, float nYStart, float nXEnd, 
						  float nYEnd, float nRotation, String nGraphicColor,   
						  boolean nSolid, float nOutlineThickness, boolean nShadow) {
	    
		super(PageObjectType.GRAPHIC, nXStart, nYStart);
		
		this.type = nType;
		this.xEnd = nXEnd;
		this.yEnd = nYEnd;
		this.rotation = nRotation;
		this.graphicColor = nGraphicColor;
		this.solid = nSolid;
		this.outlineThickness = nOutlineThickness;
		this.shadow = nShadow;
		
		this.shading = Shading.NONE;
		this.shadingColor = new String("#ff000000");
	}
	
	/* Getters and Setters for Graphics variables */
	
	public GraphicType getType() {
		return type;
	}
	
	public void setType(GraphicType nType) {
		this.type = nType;
	}

	public float getXEnd() {
		return xEnd;
	}
	
	public void setXEnd(float nXEnd) {
		xEnd = nXEnd;
	}
	
	public float getYEnd() {
		return yEnd;
	}

	public void setYEnd(float nYEnd) {
		yEnd = nYEnd;
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
	
	public Shading getShading() {
	    return shading;
	}
	
	public void setShading(Shading nShading) {
	    this.shading = nShading;
	}
	
	public String getShadingColor() {
	    return shadingColor;
	}
	
	public void setShadingColor(String nShadingColor) {
	    this.shadingColor = nShadingColor;
	}
	
	/** Prints information about the object to the screen */
    public void debugPrint() {
        super.debugPrint();
        
        System.out.println(", xEnd " + xEnd + 
                           ", yEnd " + yEnd + 
                           ", Rotation " + rotation +
                           ", Color " + graphicColor + 
                           ", Solid " + solid + 
                           ", Outline Thickness " + outlineThickness +
                           ", Shadow " + shadow + 
                           ", Shading " + shading +
                           ", Shading Colour " + shadingColor +
                           ".\n");
    }
}
