/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 */
package teacheasy.data;

import java.util.ArrayList;

/**
 * Encapsulates the data that describes a Rich Text Fragment
 * as defined in the TeachEasy digital lesson XML format.
 * 
 * @author  Alistair Jewers
 * @version 1.0 17 Feb 2015
 */
public class RichText {
    
    /** Enumeration of the possible emphasis settings */
    public enum TextSetting {
        BOLD,
        ITALIC,
        UNDERLINE,
        STRIKETHROUGH,
        SUPERSCRIPT,
        SUBSCRIPT,
        NEWLINE,
        INVALID;
        
        /**
         * Checks a string against the possible values,
         * returning a match if one exists or a default.
         * 
         * @param str The string to check.
         * @return A matching text setting if one exists.
         */
        public static TextSetting check(String str) {
            try {
                return valueOf(str);
            } catch (Exception ex) {
                return INVALID;
            }
        }
    }
    
    /* Data variables */
    private String text;
    private String font;
    private int fontSize;
    private String color;
    private boolean bold = false;
    private boolean italic = false;
    private boolean underline = false;
    private boolean strikethrough = false;
    private boolean superscript = false;
    private boolean subscript = false;
    private boolean newLine = false;
    
    /** 
     * Constructor to create the object from the data parsed from XML.
     * 
     * @param nText The text string.
     * @param nFont The font.
     * @param nFontSize The font size as an integer pt size.
     * @param nColor The color as a hex string.
     * @param settings A vararg collection of the various emphasis settings, e.g. bold and italic.
     */
    public RichText(String nText, String nFont, int nFontSize, String nColor, String... settings) {
        /* Instantiate class level variables */
        this.text = nText;
        this.font = nFont;
        this.fontSize = nFontSize;
        this.color = nColor;
        
        /* Check and instantiate the emphasis settings */
        for(String arg : settings) {
            switch(TextSetting.check(arg.toUpperCase())) {
                case BOLD:
                    this.bold = true;
                    break;
                case ITALIC:
                    this.italic = true;
                    break;
                case UNDERLINE:
                    this.underline = true;
                    break;
                case STRIKETHROUGH:
                    this.strikethrough = true;
                    break;
                case SUPERSCRIPT:
                    this.superscript = true;
                    break;
                case SUBSCRIPT:
                    this.subscript = true;
                    break;
                case NEWLINE:
                    this.newLine = true;
                    break;
                default:
                    break;
            }
        }
    }
    
    /** Gets the text of this fragment */
    public String getText() {
        return text;
    }

    /** Sets the text of this fragment */
    public void setText(String nText) {
        this.text = nText;
    }

    /** Gets the font of this fragment */
    public String getFont() {
        return font;
    }

    /** Sets the font of this fragment */
    public void setFont(String nFont) {
        this.font = nFont;
    }

    /** Gets the font size of this fragment */
    public int getFontSize() {
        return fontSize;
    }

    /** Sets the font size of this fragment */
    public void setFontSize(int nFontSize) {
        this.fontSize = nFontSize;
    }

    /** Gets the colour of this fragment in #AARRGGBB string format */
    public String getColor() {
        return color;
    }

    /** Sets the colour of this fragment in #AARRGGBB string format */
    public void setColor(String nColor) {
        this.color = nColor;
    }

    /** Checks the bold setting */
    public boolean isBold() {
        return bold;
    }

    /** Sets the bold setting */
    public void setBold(boolean nBold) {
        this.bold = nBold;
    }

    /** Checks the italic setting */
    public boolean isItalic() {
        return italic;
    }

    /** Sets the italic setting */
    public void setItalic(boolean nItalic) {
        this.italic = nItalic;
    }

    /** Checks the underline setting */
    public boolean isUnderline() {
        return underline;
    }

    /** Sets the underline setting */
    public void setUnderline(boolean nUnderline) {
        this.underline = nUnderline;
    }

    /** Gets the strikethrough setting */
    public boolean isStrikethrough() {
        return strikethrough;
    }

    /** Sets the strikethrough setting */
    public void setStrikethrough(boolean nStrikethrough) {
        this.strikethrough = nStrikethrough;
    }

    /** Checks the superscript setting */
    public boolean isSuperscript() {
        return superscript;
    }

    /** Sets the superscript setting */
    public void setSuperscript(boolean nSuperscript) {
        this.superscript = nSuperscript;
    }

    /** Checks the subscript setting */
    public boolean isSubscript() {
        return subscript;
    }

    /** Sets the subscript setting */
    public void setSubscript(boolean nSubscript) {
        this.subscript = nSubscript;
    }

    /** Checks the new line setting */
    public boolean isNewLine() {
        return newLine;
    }

    /** Sets the new line setting */
    public void setNewLine(boolean nNewLine) {
        this.newLine = nNewLine;
    }
    
    /**
     * Gets the settings as an arraylist of strings.
     * 
     * @return The settings of this text fragment as an arraylist
     *         of strings. An entry indicates a true setting.
     */
    public ArrayList<String> getSettings() {
        /** Instantiate an arraylist to hold the strings */
        ArrayList<String> list = new ArrayList<String>();
        
        /* Check the bold setting */
        if(bold) {
            /* Add the bold string */
            list.add("BOLD");
        }
        
        /* Check the italic setting */
        if(italic) {
            /* Add the italic string */
            list.add("ITALIC");
        }
        
        /* Check the underline setting */
        if(underline) {
            /* Add the underline string */
            list.add("UNDERLINE");
        }
        
        /* Check the strikethrough setting */
        if(strikethrough) {
            /* Add the strikethrough string */
            list.add("STRIKETHROUGH");
        }
        
        /* Check the superscript setting */
        if(superscript) {
            /* Add the superscript string */
            list.add("SUPERSCRIPT");
        }
        
        /* Check the subscript setting */
        if(subscript) {
            /* Add the subscript string */
            list.add("SUBSCRIPT");
        }
        
        /* Check the new line setting */
        if(newLine) {
            /* Add the new line string */
            list.add("NEWLINE");
        }
        
        /* Return the array list */
        return list;
    }
}
