/*
 * Alistair Jewers
 * 
 * Copyright (C) Sofia Software Solutions
 */
package teacheasy.data.lessondata;

/**
 * Encapsulates the data that describes the default settings
 * for a lesson as defined in the TeachEasy digital lesson 
 * XML format.
 * 
 * @author  Alistair Jewers
 * @version 1.0 08 Feb 2015
 */
public class LessonDefaultSettings {
    
    /* Data variables */
    private int fontSize;
    private String font;
    private String backgroundColour;
    private String fontColour;
    private String lineColour;
    private String fillColour;
    
    /**
     * Constructor to create the data object with the data parsed
     * from XML.
     * 
     * @param nFontSize The default font size.
     * @param nFont The default font size.
     * @param nBackgroundColour The default background colour.
     * @param nFontColour The default font colour.
     * @param nLineColour The default line colour.
     * @param nFillColour The defualt fill colour.
     */
    public LessonDefaultSettings(int nFontSize, String nFont,
                                 String nBackgroundColour, String nFontColour,
                                 String nLineColour, String nFillColour) {
        
        /* Initialise class level data variables */
        this.setFontSize(nFontSize);
        this.setFont(nFont);
        this.setBackgroundColour(nBackgroundColour);
        this.setFontColour(nFontColour);
        this.setLineColour(nLineColour);
        this.setFillColour(nFillColour);
    }

    /** Gets the font size */
    public int getFontSize() {
        return fontSize;
    }

    /** Sets the font size */
    public void setFontSize(int nFontSize) {
        this.fontSize = nFontSize;
    }

    /** Gets the font */
    public String getFont() {
        return font;
    }

    /** Sets the font */
    public void setFont(String nFont) {
        this.font = nFont;
    }

    /** Gets background colour in #AARRGGBB string format */
    public String getBackgroundColour() {
        return backgroundColour;
    }

    /** Sets background colour in #AARRGGBB string format */
    public void setBackgroundColour(String backgroundColour) {
        this.backgroundColour = backgroundColour;
    }

    /** Gets the font colour in #AARRGGBB string format */
    public String getFontColour() {
        return fontColour;
    }

    /** Sets the font colour in #AARRGGBB string format */
    public void setFontColour(String fontColour) {
        this.fontColour = fontColour;
    }

    /** Gets the line colour in #AARRGGBB string format */
    public String getLineColour() {
        return lineColour;
    }

    /** Sets the line colour in #AARRGGBB string format */
    public void setLineColour(String lineColour) {
        this.lineColour = lineColour;
    }

    /** Gets the fill colour in 8 digit hex string form */
    public String getFillColour() {
        return fillColour;
    }

    /** Set the fill colour in 8 digit hex string form */
    public void setFillColour(String fillColour) {
        this.fillColour = fillColour;
    }    
}

