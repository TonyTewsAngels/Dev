/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.xml;

/**
 * This enumerated type contains an entry
 * for any attribute the parser anticipates
 * finding within an xml file. This allows
 * for the 'string switching' method seen
 * in the XMLParser class.
 * 
 * @author  Alistair Jewers
 * @version 1.0 13 Feb 2015
 */
public enum XMLAttribute {
    /* General positioning */
    XSTART,
    YSTART,
    
    XEND,
    YEND,
    
    SCALE,
    YSCALE,
    ROTATION,

    /* Slide element */
    NUMBER,
    BACKGROUNDCOLOR,
    
    /* Text element */
    SOURCEFILE,
    FONT,
    FONTSIZE,
    FONTCOLOR,
    
    /* Rich text element */
    BOLD,
    ITALIC,
    UNDERLINE,
    
    /* Audio element */
    VIEWPROGRESS,
    
    /* Video element */
    VIDEOSCREENSHOT,
    
    /* Graphic element */
    TYPE,
    SOLID,
    GRAPHICCOLOR,
    OUTLINETHICKNESS,
    SHADOW,
    
    /* Cyclic shading element */    
    SHADINGCOLOR,
    
    /* Answer box element */
    CHARACTERLIMIT,
    CORRECTANSWER,
    MARKS,
    RETRY,
    
    /* Button element */
    OUTLINE,
    
    /* Multiple choice element */
    ORIENTATION,
    
    /* Answer element */
    CORRECT,
    
    /* Pass boundary element */
    PASSMESSAGE,
    FAILMESSAGE,
    
    INVALID;
    
    public static XMLAttribute check(String str) {
        try {
            return valueOf(str);
        } catch (Exception ex) {
            return INVALID;
        }
    }
}
