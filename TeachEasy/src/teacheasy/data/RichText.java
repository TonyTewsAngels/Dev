/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.data;

import java.util.ArrayList;

/**
 * This class encapsulates a fragment of text and it's
 * various settings.
 * 
 * @author Alistair Jewers
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
        
        public static TextSetting check(String str) {
            try {
                return valueOf(str);
            } catch (Exception ex) {
                return INVALID;
            }
        }
    }
    
    /** Class level variables */
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
     * Constructor method
     * 
     * @param nText The text string.
     * @param nFont The font.
     * @param nFontSize The font size as an integer pt size.
     * @oaram nColor The color as a hex string.
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

    /* Get and set functions */
    
    public String getText() {
        return text;
    }

    public void setText(String nText) {
        this.text = nText;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String nFont) {
        this.font = nFont;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int nFontSize) {
        this.fontSize = nFontSize;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String nColor) {
        this.color = nColor;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean nBold) {
        this.bold = nBold;
    }

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(boolean nItalic) {
        this.italic = nItalic;
    }

    public boolean isUnderline() {
        return underline;
    }

    public void setUnderline(boolean nUnderline) {
        this.underline = nUnderline;
    }

    public boolean isStrikethrough() {
        return strikethrough;
    }

    public void setStrikethrough(boolean nStrikethrough) {
        this.strikethrough = nStrikethrough;
    }

    public boolean isSuperscript() {
        return superscript;
    }

    public void setSuperscript(boolean nSuperscript) {
        this.superscript = nSuperscript;
    }

    public boolean isSubscript() {
        return subscript;
    }

    public void setSubscript(boolean nSubscript) {
        this.subscript = nSubscript;
    }

    public boolean isNewLine() {
        return newLine;
    }

    public void setNewLine(boolean nNewLine) {
        this.newLine = nNewLine;
    }
    
    public ArrayList<String> getSettings() {
        ArrayList<String> list = new ArrayList<String>();
        
        if(bold) {
            list.add("BOLD");
        }
        
        if(italic) {
            list.add("ITALIC");
        }
        
        if(underline) {
            list.add("UNDERLINE");
        }
        
        if(strikethrough) {
            list.add("STRIKETHROUGH");
        }
        
        if(superscript) {
            list.add("SUPERSCRIPT");
        }
        
        if(subscript) {
            list.add("SUBSCRIPT");
        }
        
        if(newLine) {
            list.add("NEWLINE");
        }
        
        return list;
    }
}
