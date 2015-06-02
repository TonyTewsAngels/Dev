/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 */
package teacheasy.render;

import javafx.scene.paint.Color;

/**
 * Contains renderer utility methods and constants.
 * 
 * @author  Alistair Jewers
 * @version 1.0 03 Mar 2015
 */
public class RenderUtil {
    /** The width of the Learn Easy page */
    public static final double LE_WIDTH = 1250.0;
    
    /** The height of the Learn Easy page */
    public static final double LE_HEIGHT = 703.125;
    
    /** The width of the Teach Easy page */
    public static final double TE_WIDTH = 937.5;
    
    /** The height of the teach easy page */
    public static final double TE_HEIGHT = 527.34;
    
    /**
     * Converts an #AARRGGBB hex color string into a java fx color.
     *  
     * @param hexCol The string to convert.
     * @return The equivalent java FX color object.
     */
    public static Color colorFromString(String hexCol) {
        /* Separate string into components */
        String A = hexCol.substring(1, 3);
        String R = hexCol.substring(3, 5);
        String G = hexCol.substring(5, 7);
        String B = hexCol.substring(7);
        
        /* Convert to decimal */
        int iR = Integer.decode("#" + R);
        int iG = Integer.decode("#" + G);
        int iB = Integer.decode("#" + B);
        int iA = Integer.decode("#" + A);
        
        /* Convert to a 0 - 1 range */
        float fR = iR/255.0f;
        float fG = iG/255.0f;
        float fB = iB/255.0f;
        float fA = iA/255.0f;

        /* Return a colour based on the calculated values */
        return new Color(fR, fG, fB, fA);
    }
    
    /** Convert a JavaFX color into an ARGB hex string */
    public static String stringFromColor(Color color) {
        /* Split colour into alpha channel and colour components */
        String alpha = new String(color.toString().substring(8, 10));
        String colStr = new String(color.toString().substring(2, 8));
        
        /* Return the components reordered concatenated with the hash symbol */
        return new String("#" + alpha + colStr);
    }
}
