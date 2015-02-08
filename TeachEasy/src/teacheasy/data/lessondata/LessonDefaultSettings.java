/*
 * Alistair Jewers
 * 
 * Copyright (C) Sofia Software Solutions
 * 
 */
package teacheasy.data.lessondata;

/**
 * A class to encapsulate the default
 * settings for a lesson.
 * 
 * @author Alistair Jewers
 * @version 1.0 08 Feb 2015
 */
public class LessonDefaultSettings {
    private int fontSize;
    private String font;
    private String backgroundColour;
    private String fontColour;
    private String lineColour;
    private String fillColour;
    
    /** Constructor Method */
    public LessonDefaultSettings(int nFontSize, String nFont,
                                 String nBackgroundColour, String nFontColour,
                                 String nLineColour, String nFillColour) {
        
        /* Initialise class level variables */
        this.setFontSize(nFontSize);
        this.setFont(nFont);
        this.setBackgroundColour(nBackgroundColour);
        this.setFontColour(nFontColour);
        this.setLineColour(nLineColour);
        this.setFillColour(nFillColour);
    }

    /** Get the font size */
    public int getFontSize() {
        return fontSize;
    }

    /** Set the font size */
    public void setFontSize(int nFontSize) {
        this.fontSize = nFontSize;
    }

    /** Get the font */
    public String getFont() {
        return font;
    }

    /** Set the font */
    public void setFont(String nFont) {
        this.font = nFont;
    }

    /** Get background colour in 8 digit hex string form */
    public String getBackgroundColour() {
        return backgroundColour;
    }

    /** Set background colour in 8 digit hex string form */
    public void setBackgroundColour(String backgroundColour) {
        this.backgroundColour = backgroundColour;
    }

    /** Get the font colour in 8 digit hex string form */
    public String getFontColour() {
        return fontColour;
    }

    /** Set the font colour in 8 digit hex string form */
    public void setFontColour(String fontColour) {
        this.fontColour = fontColour;
    }

    /** Get the line colour in 8 digit hex string form */
    public String getLineColour() {
        return lineColour;
    }

    /** Get the line colour in 8 digit hex string form */
    public void setLineColour(String lineColour) {
        this.lineColour = lineColour;
    }

    /** Get the fill colour in 8 digit hex string form */
    public String getFillColour() {
        return fillColour;
    }

    /** Set the fill colour in 8 digit hex string form */
    public void setFillColour(String fillColour) {
        this.fillColour = fillColour;
    }    
}

