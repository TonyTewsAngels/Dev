package teacheasy.render;

import javafx.scene.paint.Color;

/**
 * A class for renderer utility methods.
 * 
 * @author Alistair Jewers
 * @version 1.0 03 Mar 2015
 */
public class RenderUtil {
    public static final double LE_WIDTH = 1250.0;
    public static final double LE_HEIGHT = 703.125;
    
    public static final double TE_WIDTH = 937.5;
    public static final double TE_HEIGHT = 527.34;
    
    
    /** Convert an ARGB hex color string into a java fx color */
    public static Color colorFromString(String hexCol) {
        String A = hexCol.substring(1, 3);
        String R = hexCol.substring(3, 5);
        String G = hexCol.substring(5, 7);
        String B = hexCol.substring(7);
        
        int iR = Integer.decode("#" + R);
        int iG = Integer.decode("#" + G);
        int iB = Integer.decode("#" + B);
        int iA = Integer.decode("#" + A);
        
        float fR = iR/255.0f;
        float fG = iG/255.0f;
        float fB = iB/255.0f;
        float fA = iA/255.0f;

        return new Color(fR, fG, fB, fA);
    }
    
    /** Convert a JavaFX color into an ARGB hex string */
    public static String stringFromColor(Color color) {
        String alpha = new String(color.toString().substring(8, 10));
        String colStr = new String(color.toString().substring(2, 8));
        
        return new String("#" + alpha + colStr);
    }
}
