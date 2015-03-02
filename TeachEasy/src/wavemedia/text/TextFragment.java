package wavemedia.text;

import javafx.scene.text.Font;

public class TextFragment {

    private String text;
    private String font = Defaults.getFont();
    private double fontSize = Defaults.getFontSize();
    private String color = Defaults.getFontColor();
    private String highlightColor = Defaults.getHighlightColor();
    private boolean bold;
    private boolean underlined;
    private boolean italicised;
    private boolean superscript;
    private boolean subscript;
    private boolean strikethrough;
    private String textCase = Defaults.getTextCase();
    
    /**
     * @Initialises a new text fragment
     */
    public TextFragment() {
        
    }

    /**
     * @return true if fragment is superscript
     */
    public boolean isSuperscript() {
        return superscript;
    }

    /**
     * @param superscript
     *            the superscript to set
     */
    public void setSuperscript(String string) {

        boolean b = Boolean.parseBoolean(string);
        this.superscript = b;

    }

    /**
     * @return the subscript
     */
    public boolean isSubscript() {
        return subscript;
    }

    /**
     * @param subscript
     *            the subscript to set
     */
    public void setSubscript(String string) {

            boolean b = Boolean.parseBoolean(string);
            this.subscript = b;

    }

    /**
     * @return the strikethrough
     */
    public boolean isStrikethrough() {
        return strikethrough;
    }

    /**
     * @param strikethrough
     *            the strikethrough to set
     */
    public void setStrikethrough(String string) {

            boolean b = Boolean.parseBoolean(string);
            this.strikethrough = b;

    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text
     *            the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the font
     */
    public String getFont() {
        return font;
    }

    /**
     * @param font
     *            the font to set
     */
    public void setFont(String font) {
        font = Defaults.capitaliseEachFirstLetter(font);
        if (Font.getFontNames().contains(font)) {
            this.font = font;
        } else {
            this.font = Defaults.getFont();
        }

    }

    /**
     * @return the fontSize
     */
    public double getFontSize() {
        return fontSize;
    }

    /**
     * @param fontSize
     *            the fontSize to set
     */
    public void setFontSize(String string) {
        try {
            double d = Double.parseDouble(string);
            this.fontSize = d;
        } catch (Exception e) {
            this.fontSize = Defaults.getFontSize();
        }
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param colour
     *            the colour to set
     */
    public void setColor(String color) {
        try {
            if (color.matches("^([#]([0-9a-fA-F]{8}))$")) {
                this.color = color;
            }
        } catch (NullPointerException npe) {
            /* Do nothing */
        }
    }

    /**
     * @return the bold
     */
    public boolean isBold() {
        return bold;
    }

    /**
     * @param bold
     *            the bold to set
     */
    public void setBold(String string) {
        try {
            boolean b = Boolean.parseBoolean(string);
            this.bold = b;
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /**
     * @return the underlined
     */
    public boolean isUnderlined() {
        return underlined;
    }

    /**
     * @param underlined
     *            the underlined to set
     */
    public void setUnderlined(String string) {
        try {
            boolean b = Boolean.parseBoolean(string);
            this.underlined = b;
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /**
     * @return the italicised
     */
    public boolean isItalicised() {
        return italicised;
    }

    /**
     * @param italicised
     *            the italicised to set
     */
    public void setItalicised(String string) {
        try {
            boolean b = Boolean.parseBoolean(string);
            this.italicised = b;
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /**
     * @return the textCase
     */
    public String getTextCase() {
        return textCase;
    }

    /**
     * @param textCase
     *            the textCase to set
     */
    public void setTextCase(String string) {
        if (string.matches("upper|lower|camel|none")) {
            this.textCase = string;
        } else {
            this.textCase = Defaults.getTextCase();
        }

    }

    /**
     * @return the highlightColor
     */
    public String getHighlightColor() {
        return highlightColor;
    }

    /**
     * @param highlightColor
     *            the highlightColor to set
     */
    public void setHighlightColor(String highlightColor) {
        try {
            if (highlightColor.matches("^([#]([0-9a-fA-F]{8}))$")) {
                this.highlightColor = highlightColor;
            }
        } catch (NullPointerException npe) {
            /* Do nothing */
        }

    }

    /**
     * @param fontSize the fontSize to set
     */
    public void setFontSize(double fontSize) {
        if(fontSize > 0) {
            this.fontSize = fontSize;
        }
    }

    /**
     * @param bold the bold to set
     */
    public void setBold(boolean bold) {
        this.bold = bold;
    }

    /**
     * @param underlined the underlined to set
     */
    public void setUnderlined(boolean underlined) {
        this.underlined = underlined;
    }

    /**
     * @param italicised the italicised to set
     */
    public void setItalicised(boolean italicised) {
        this.italicised = italicised;
    }

    /**
     * @param superscript the superscript to set
     */
    public void setSuperscript(boolean superscript) {
        this.superscript = superscript;
    }

    /**
     * @param subscript the subscript to set
     */
    public void setSubscript(boolean subscript) {
        this.subscript = subscript;
    }

    /**
     * @param strikethrough the strikethrough to set
     */
    public void setStrikethrough(boolean strikethrough) {
        this.strikethrough = strikethrough;
    }

}

