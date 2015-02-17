package teacheasy.data;

public class RichText {
    public enum TextSetting {
        BOLD,
        ITALIC,
        UNDERLINE,
        STRIKETHROUGH,
        SUPERSCRIPT,
        SUBSCRIPT,
        INVALID;
        
        public static TextSetting check(String str) {
            try {
                return valueOf(str);
            } catch (Exception ex) {
                return INVALID;
            }
        }
    }
    
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
    
    public RichText(String nText, String nFont, int nFontSize, String nColor, String... settings) {
        this.text = new String(nText);
        this.font = nFont;
        this.fontSize = nFontSize;
        this.color = nColor;
        
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
                default:
                    break;
            }
        }
    }

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
}
