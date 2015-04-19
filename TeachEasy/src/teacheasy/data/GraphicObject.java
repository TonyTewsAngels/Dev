/*
 * Lewis Thresh & Calum Armstrong
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.data;

import teacheasy.xml.XMLElement;
import wavemedia.graphic.GraphicType;
import wavemedia.graphic.Shading;

/**
 * This class extends PageObject and encapsulates a single graphics object.
 * 
 * @version 	1.0 05 Feb 2015
 * @author 		Lewis Thresh & Calum Armstrong
 */
public class GraphicObject extends PageObject {		
	/** Graphics Variables */
	private GraphicType graphicType;
	private float xEnd;
	private float yEnd;
	private float rotation;
	private String graphicColor;
	private boolean solid;
	private float outlineThickness;
	private String lineColor;
	private boolean shadow;
	
	private Shading shading;
	private String shadingColor;
	
	private float startTime;
	private float duration;
	
    public GraphicObject(GraphicType nType, float nXStart, float nYStart, float nXEnd, 
                         float nYEnd, float nRotation, String nGraphicColor,   
                         boolean nSolid, String nLineColor, float nOutlineThickness,
                         boolean nShadow,
                         float nStartTime, float nDuration) {

        super(PageObjectType.GRAPHIC, nXStart, nYStart);
        
        this.graphicType = nType;
        this.xEnd = nXEnd;
        this.yEnd = nYEnd;
        this.rotation = nRotation;
        this.graphicColor = nGraphicColor;
        this.solid = nSolid;
        this.lineColor = nLineColor;
        this.outlineThickness = nOutlineThickness;
        this.shadow = nShadow;
        
        this.startTime = nStartTime;
        this.duration = nDuration;
        
        this.shading = Shading.NONE;
        this.shadingColor = new String("#ff000000");
    }
	
	/** Old Compatability Constructor Method, Deprecated.*/
	public GraphicObject(GraphicType nType, float nXStart, float nYStart, float nXEnd, 
						 float nYEnd, float nRotation, String nGraphicColor,   
						 boolean nSolid, String nLineColor, float nOutlineThickness, boolean nShadow) {
	    
		super(PageObjectType.GRAPHIC, nXStart, nYStart);
		
		this.graphicType = nType;
		this.xEnd = nXEnd;
		this.yEnd = nYEnd;
		this.rotation = nRotation;
		this.graphicColor = nGraphicColor;
		this.solid = nSolid;
		this.outlineThickness = nOutlineThickness;
		this.lineColor = nLineColor;
		this.shadow = nShadow;
		
		this.shading = Shading.NONE;
		this.shadingColor = new String("#ff000000");
	}
	
	/* Getters and Setters for Graphics variables */
	
	public GraphicType getGraphicType() {
		return graphicType;
	}
	
	public void setType(GraphicType nType) {
		this.graphicType = nType;
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
	
	public String getLineColor() {
	    return lineColor;
	}
	
	public void setLineColor(String nLineColor) {
	    this.lineColor = nLineColor;
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
        
        System.out.println(", type " + graphicType.toString() +
                           ", xEnd " + xEnd + 
                           ", yEnd " + yEnd + 
                           ", Rotation " + rotation +
                           ", Color " + graphicColor + 
                           ", Solid " + solid + 
                           ", Outline Color " + lineColor +
                           ", Shadow " + shadow + 
                           ", Shading " + shading +
                           ", Shading Colour " + shadingColor +
                           ".\n");
    }

    public float getStartTime() {
        return startTime;
    }

    public void setStartTime(float nStartTime) {
        this.startTime = nStartTime;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float nDuration) {
        this.duration = nDuration;
    }
}
