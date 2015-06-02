/*
 * Lewis Thresh & Calum Armstrong
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 */
package teacheasy.data;

import wavemedia.graphic.GraphicType;
import wavemedia.graphic.Shading;

/**
 * Encapsulates the data that describes a Graphics Object
 * as defined in the TeachEasy digital lesson XML format.
 * Extends the page object supertype.
 * 
 * @author 	Lewis Thresh 
 * @author  Calum Armstrong
 * @version 1.0 05 Feb 2015
 */
public class GraphicObject extends PageObject {	
    
	/* Data Variables */
	private float xEnd;
	private float yEnd;
	private float rotation;
	private float outlineThickness;
	private float startTime;
	private float duration;
	private boolean shadow;
	private boolean solid;
	private String lineColor;
	private String graphicColor;
	private String shadingColor;
	private GraphicType graphicType;
	private Shading shading;
	
	/**
     * Constructor to create the data object with the data parsed
     * from XML.
     * 
	 * @param nType Graphic type.
	 * @param nXStart Relative X axis start position.
	 * @param nYStart Relative Y axis start position.
	 * @param nXEnd Relative X axis end position.
	 * @param nYEnd Relative Y axis end position.
	 * @param nRotation Rotation in degrees.
	 * @param nGraphicColor Colour of the graphic in #AARRGGBB format.
	 * @param nSolid Solid setting.
	 * @param nLineColor Outline colour in #AARRGGBB format.
	 * @param nOutlineThickness Outline thickness.
	 * @param nShadow Shadow setting.
	 * @param nStartTime Start time (not used).
	 * @param nDuration Duration (not used).
	 */
    public GraphicObject(GraphicType nType, float nXStart, float nYStart, float nXEnd, 
                         float nYEnd, float nRotation, String nGraphicColor,   
                         boolean nSolid, String nLineColor, float nOutlineThickness,
                         boolean nShadow,
                         float nStartTime, float nDuration) {

        /* Call the superconstructor */
        super(PageObjectType.GRAPHIC, nXStart, nYStart);
        
        /* Initialise data variables */
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
	
    /** Gets the graphic type */
	public GraphicType getGraphicType() {
		return graphicType;
	}
	
	/** Sets the graphic type */
	public void setType(GraphicType nType) {
		this.graphicType = nType;
	}

	/** Gets the relative X axis end position */
	public float getXEnd() {
		return xEnd;
	}
	
	/** Sets the relative X axis end positon */
	public void setXEnd(float nXEnd) {
		xEnd = nXEnd;
	}
	
	/** Gets the relative Y axis end position */
	public float getYEnd() {
		return yEnd;
	}

	/** Sets the relative Y axis end position */
	public void setYEnd(float nYEnd) {
		yEnd = nYEnd;
	}

	/** Gets the rotation in degrees */
	public float getRotation() {
		return rotation;
	}

	/** Sets the rotation in degrees */
	public void setRotation(float nRotation) {
		this.rotation = nRotation;
	}

	/** Gets the colour in #AARRGGBB string format */
	public String getGraphicColour() {
		return graphicColor;
	}

	/** Sets the colour in #AARRGGBB string format */
	public void setGraphicColor(String nGraphicColor) {
		this.graphicColor = nGraphicColor;
	}

	/** Checks the solid setting */
	public boolean isSolid() {
		return solid;
	}

	/** Sets the solid setting */
	public void setSolid(boolean nSolid) {
		this.solid = nSolid;
	}
	
	/** Gets the outline colour in #AARRGGBB string format */
	public String getLineColor() {
	    return lineColor;
	}
	
	/** Sets the outline colour in #AARRGGBB string format */
	public void setLineColor(String nLineColor) {
	    this.lineColor = nLineColor;
	}

	/** Gets the outline thickness */
	public float getOutlineThickness() {
		return outlineThickness;
	}
	
	/** Sets the outline thickness */
	public void setOutlineThickness(float nOutlineThickness) {
		this.outlineThickness = nOutlineThickness;
	}

	/** Checks the shadow setting */
	public boolean isShadow() {
		return shadow;
	}
	
	/** Sets the shadow setting */
	public void setShadow(boolean nShadow) {
		this.shadow = nShadow;
	}
	
	/** Gets the shading type */
	public Shading getShading() {
	    return shading;
	}
	
	/** Sets the shading type */
	public void setShading(Shading nShading) {
	    this.shading = nShading;
	}
	
	/** Gets the shading colour in #AARRGGBB string format */
	public String getShadingColor() {
	    return shadingColor;
	}
	
	/** Sets the shading colour in #AARRGGBB string format */
	public void setShadingColor(String nShadingColor) {
	    this.shadingColor = nShadingColor;
	}

	/** Gets the start time (not used) */
    public float getStartTime() {
        return startTime;
    }

    /** Sets the start time (not used) */
    public void setStartTime(float nStartTime) {
        this.startTime = nStartTime;
    }

    /** Gets the duration (not used) */
    public float getDuration() {
        return duration;
    }

    /** Sets the duration (not used) */
    public void setDuration(float nDuration) {
        this.duration = nDuration;
    }
    
    /** Prints information about the object to the screen */
    public void debugPrint() {
        /* Print the supertype data */
        super.debugPrint();
        
        /* Print the data variables */
        System.out.println(", type " + graphicType.toString() +
                           ", xEnd " + xEnd + 
                           ", yEnd " + yEnd + 
                           ", Rotation " + rotation +
                           ", Color " + graphicColor + 
                           ", Solid " + solid + 
                           ", Outline Color " + lineColor +
                           ", Outline Thickness " + outlineThickness +
                           ", Shadow " + shadow + 
                           ", Shading " + shading +
                           ", Shading Colour " + shadingColor +
                           ".\n");
    }
}
