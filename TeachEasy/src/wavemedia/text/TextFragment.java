package wavemedia.text;

public class TextFragment implements Cloneable {

	private boolean bold;
	private boolean underlined;
	private boolean italicised;
	private boolean superscript;
	private boolean subscript;
	private boolean strikethrough;
	private boolean newline;
	private String fontColor;
	private String highlightColor;
	private String font;
	private double fontSize;
	private String text;
	
	/**
	 * @Initialises a new text fragment
	 */
	public TextFragment(Defaults defaults) {
		this.font = defaults.getFont();
		this.fontColor = defaults.getFontColor();
		this.fontSize = defaults.getFontSize();
		this.highlightColor = defaults.getHighlightColor();
	}
	
	public TextFragment clone(){  
	    try{  
	        return (TextFragment)super.clone();  
	    }catch(Exception e){ 
	        return null; 
	    }
	}

	public void printItem() {
		System.out.println("Bold: " + bold);
		System.out.println("Underlined: " + underlined);
		System.out.println("Italicised: " + italicised);
		System.out.println("Superscript: " + superscript);
		System.out.println("Subscript: " + subscript);
		System.out.println("Strikethrough: " + strikethrough);
		System.out.println("Font Color: " + fontColor);
		System.out.println("Highlight Color: " + highlightColor);
		System.out.println("Font: " + font);
		System.out.println("Font Size: " + fontSize);
		System.out.println("Newline: " + newline);
		System.out.println("Text: \"" + text + "\"");
		System.out.println("");
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
		this.superscript = Boolean.parseBoolean(string);
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
		this.subscript = Boolean.parseBoolean(string);
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
		this.strikethrough = Boolean.parseBoolean(string);
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
	public void setFont(String string) {
		string = Utils.capitaliseEachFirstLetter(string);
		if (Utils.validFont(string)) {
			this.font = string;
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
			if (d > 0) {
				this.fontSize = d;
			}
		} catch (Exception e) {
			/* Do Nothing */
		}
	}

	/**
	 * @return the color
	 */
	public String getFontColor() {
		return fontColor;
	}

	/**
	 * @param colour
	 *            the colour to set
	 */
	public void setFontColor(String string) {
		if (Utils.validARGB(string)) {
			this.fontColor = string;
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
		this.bold = Boolean.parseBoolean(string);
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
		this.underlined = Boolean.parseBoolean(string);
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
		this.italicised = Boolean.parseBoolean(string);
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
	public void setHighlightColor(String string) {
		if (Utils.validARGB(string)) {
			this.highlightColor = string;
		}
	}

	/**
	 * @param fontSize
	 *            the fontSize to set
	 */
	public void setFontSize(double fontSize) {
		if (fontSize > 0) {
			this.fontSize = fontSize;
		}
	}

	/**
	 * @param bold
	 *            the bold to set
	 */
	public void setBold(boolean bold) {
		this.bold = bold;
	}

	/**
	 * @param underlined
	 *            the underlined to set
	 */
	public void setUnderlined(boolean underlined) {
		this.underlined = underlined;
	}

	/**
	 * @param italicised
	 *            the italicised to set
	 */
	public void setItalicised(boolean italicised) {
		this.italicised = italicised;
	}

	/**
	 * @param superscript
	 *            the superscript to set
	 */
	public void setSuperscript(boolean superscript) {
		this.superscript = superscript;
	}

	/**
	 * @param subscript
	 *            the subscript to set
	 */
	public void setSubscript(boolean subscript) {
		this.subscript = subscript;
	}

	/**
	 * @param strikethrough
	 *            the strikethrough to set
	 */
	public void setStrikethrough(boolean strikethrough) {
		this.strikethrough = strikethrough;
	}

	/**
	 * @return the newline
	 */
	public boolean endsWithNewline() {
		return newline;
	}

	/**
	 * @param newline the newline to set
	 */
	public void setNewline(boolean newline) {
		this.newline = newline;
	}
	
	/**
	 * @param newline the newline to set
	 */
	public void setNewline(String string) {
		this.newline= Boolean.parseBoolean(string);
	}

}
