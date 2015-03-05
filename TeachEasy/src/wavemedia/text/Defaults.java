package wavemedia.text;

import java.util.HashMap;

import javafx.scene.text.Font;

public class Defaults {

    private static String backgroundColour = "#00FFFFFF";
    private static String font = Font.getDefault().getName();
    private static double fontSize = Font.getDefault().getSize();
    private static String fontColor = "#FF000000";
    private static String graphicColor = "#FF000000";
    private static String highlightColor = "#FFFF0000";
    private static float startTime = 0;
    private static float duration = Float.MAX_VALUE;
    private static String alignment = "none";
    private static String textCase = "none";
    private static float scale = 1;
    private static int rotation = 0;
    private static float cropx1 = 0;
    private static float cropy1 = 0;
    private static float cropx2 = 1;
    private static float cropy2 = 1;

    /**
     * @return the backgroundColour
     */
    public static String getBackgroundColour() {
        return backgroundColour;
    }

    /**
     * @param backgroundColour
     *            the backgroundColour to set
     */
    public static void setBackgroundColour(String string) {
        try {
            if (string.matches("^([#]([0-9a-fA-F]{8}))$")) {
                backgroundColour = string;
            }
        } catch (NullPointerException npe) {
            /* Do nothing */
        }
    }

    /**
     * @return the font
     */
    public static String getFont() {
        return font;
    }

    /**
     * @param font
     *            the font to set
     */
    public static void setFont(String string) {
        string = capitaliseEachFirstLetter(string);
        if (Font.getFontNames().contains(string)) {
            font = string;
        }
    }

    /**
     * @return the fontSize
     */
    public static double getFontSize() {
        return fontSize;
    }

    /**
     * @param fontSize
     *            the fontSize to set
     */
    public static void setFontSize(String string) {
        try {
            double d = Double.parseDouble(string);
            if((d > 0)) {
                fontSize = d;
            }
        } catch (Exception e) {
            /* Do nothing */
        }
    }

    /**
     * @return the fontColor
     */
    public static String getFontColor() {
        return fontColor;
    }

    /**
     * @param fontColor
     *            the fontColor to set
     */
    public static void setFontColor(String string) {
        try {
            if (string.matches("^([#]([0-9a-fA-F]{8}))$")) {
                fontColor = string;
            }
        } catch (NullPointerException npe) {
            /* Do nothing */
        }
    }

    /**
     * @return the graphicColour
     */
    public static String getGraphicColor() {
        return graphicColor;
    }

    /**
     * @param fillColour
     *            the graphicColour to set
     */
    public static void setGraphicColor(String string) {
        try {
            if (string.matches("^([#]([0-9a-fA-F]{8}))$")) {
                graphicColor = string;
            }
        } catch (NullPointerException npe) {
            /* Do nothing */
        }
    }

    /**
     * @return the graphicColour
     */
    public static String getHighlightColor() {
        return highlightColor;
    }

    /**
     * @param fillColour
     *            the graphicColour to set
     */
    public static void setHighlightColor(String string) {
        try {
            if (string.matches("^([#]([0-9a-fA-F]{8}))$")) {
                highlightColor = string;
            }
        } catch (NullPointerException npe) {
            /* Do nothing */
        }
        
    }

    public static void add(HashMap<String, String> hashMap) {

        setBackgroundColour(hashMap.get("backgroundcolor"));
        setFont(hashMap.get("font"));
        setFontColor(hashMap.get("fontcolor"));
        setFontSize(hashMap.get("fontsize"));
        setGraphicColor(hashMap.get("graphiccolor"));
    }

    /**
     * @return the startTime
     */
    public static float getStartTime() {
        return startTime;
    }

    /**
     * @param startTime
     *            the startTime to set
     */
    public static void setStartTime(String string) {
        try {
            float f = Float.parseFloat(string);
            if(f >= 0) {
                startTime = f;
            }
        } catch (Exception e) {
            /* Do Nothing */
        }
    }

    /**
     * @return the duration
     */
    public static float getDuration() {
        return duration;
    }

    /**
     * @param duration
     *            the duration to set
     */
    public static void setDuration(String string) {
        try {
            float f = Float.parseFloat(string);
            if(f > 0){
                duration = f;
            }
        } catch (Exception e) {
            /* Do Nothing */
        }
    }

    /**
     * @return the alignment
     */
    public static String getAlignment() {
        return alignment;
    }

    /**
     * @param alignment
     *            the alignment to set
     */
    public static void setAlignment(String string) {
        try {
            if (string.matches("left|right|centre|none")) {
                alignment = string;
            }
        } catch (Exception e) {
            /* Do nothing */
        }
        
    }

    /**
     * @return the textCase
     */
    public static String getTextCase() {
        return textCase;
    }

    /**
     * @param textCase
     *            the textCase to set
     */
    public static void setTextCase(String string) {
        try {
            if (string.matches("upper|lower|camel|none")) {
                textCase = string;
            }
        } catch (Exception e) {
            /* Do Nothing */
        }
    }

    /**
     * @return the scale
     */
    public static float getScale() {
        return scale;
    }

    /**
     * @param scale
     *            the scale to set
     */
    public static void setScale(String string) {
        try {
            float f = Float.parseFloat(string);
            if(f > 0) {
                scale = f;
            }
        } catch (Exception e) {
            /* Do Nothing */
        }
    }

    /**
     * @return the rotation
     */
    public static int getRotation() {
        return rotation;
    }

    /**
     * @param rotation
     *            the rotation to set
     */
    public static void setRotation(String string) {
        try {
            int i = Integer.parseInt(string);
            if((i >= 0) && (i <= 360)) {
                rotation = i;
            }
        } catch (Exception e) {
            /* Do Nothing */
        }
    }

    /**
     * @return the cropx1
     */
    public static float getCropx1() {
        return cropx1;
    }

    /**
     * @param cropx1
     *            the cropx1 to set
     */
    public static void setCropx1(String string) {
        try {
            float f = Float.parseFloat(string);
            if((f >= 0) && (f <= 1)) {
                cropx1 = f;
            }
        } catch (Exception e) {
            /* Do Nothing */
        }
    }

    /**
     * @return the cropy1
     */
    public static float getCropy1() {
        return cropy1;
    }

    /**
     * @param cropy1
     *            the cropy1 to set
     */
    public static void setCropy1(String string) {
        try {
            float f = Float.parseFloat(string);
            if((f >= 0) && (f <= 1)) {
                cropy1 = f;
            }
        } catch (Exception e) {
            /* Do Nothing */
        }
    }

    /**
     * @return the cropx2
     */
    public static float getCropx2() {
        return cropx2;
    }

    /**
     * @param cropx2
     *            the cropx2 to set
     */
    public static void setCropx2(String string) {
        try {
            float f = Float.parseFloat(string);
            if((f >= 0) && (f <= 1)) {
                cropx2 = f;
            }
        } catch (Exception e) {
            /* Do Nothing */
        }
    }

    /**
     * @return the cropy2
     */
    public static float getCropy2() {
        return cropy2;
    }

    /**
     * @param cropy2
     *            the cropy2 to set
     */
    public static void setCropy2(String string) {
        try {
            float f = Float.parseFloat(string);
            if ((f >= 0) && (f <= 1)) {
                cropy2 = f;
            }
        } catch (Exception e) {
            /* Do Nothing */
        }
    }
    
    public static String capitaliseEachFirstLetter(String s) {
        String[] words = s.split(" ");
        String finalString = "";
        for (String word : words) {
            finalString += word.substring(0, 1).toUpperCase()
                    + word.substring(1).toLowerCase() + " ";
        }
        return finalString.substring(0, finalString.length() - 1);
    }
}

