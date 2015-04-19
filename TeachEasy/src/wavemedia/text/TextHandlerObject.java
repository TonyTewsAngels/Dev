/** (c) Copyright by WaveMedia. */
package wavemedia.text;

import java.util.ArrayList;

import javafx.scene.text.Font;

/**
 * Text object class for passing to the textHandler to draw to screen.
 * 
 * @author tjd511
 * @version 1.0 10/03/2015
 */
public class TextHandlerObject {
	private final float xStart;
	private final float yStart;
	private final float xEnd;
	private final float yEnd;
	private final String backgroundColor;
	private final Alignment alignment;
	private ArrayList<TextFragment> stringBuffer = new ArrayList<TextFragment>();

	private TextHandlerObject(TextBoxBuilder builder) {
		this.xStart = builder.xStart;
		this.yStart = builder.yStart;
		this.xEnd = builder.xEnd;
		this.yEnd = builder.yEnd;
		this.backgroundColor = builder.backgroundColor;
		this.alignment = builder.alignment;
		this.stringBuffer = builder.stringBuffer;
	}

	/**
	 * @return the starting x coordinate of the text box
	 */
	public float getXStart() {
		return xStart;
	}

	/**
	 * @return the starting y coordinate of the text box
	 */
	public float getYStart() {
		return yStart;
	}

	/**
	 * @return the ending x coordinate of the text box
	 */
	public float getXEnd() {
		return xEnd;
	}

	/**
	 * @return the ending y coordinate of the text box
	 */
	public float getYEnd() {
		return yEnd;
	}

	/**
	 * @return the background color of the text box
	 */
	public String getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * @return the alignment of the text in the text box
	 */
	public Alignment getAlignment() {
		return alignment;
	}

	/**
	 * @return the arraylist of text fragments within the text box
	 */
	ArrayList<TextFragment> getStringBuffer() {
		return stringBuffer;
	}

	/**
	 * Builder for the text box object.
	 * 
	 * @author tjd511
	 * @version 0.4 10/03/2015
	 */
	public static class TextBoxBuilder {
		/* Required fields in the text fragment */
		private final float xStart;
		private final float yStart;
		/* Optional fields in the text fragment */
		private float xEnd;
		private float yEnd;
		private String backgroundColor = "#00000000";
		private Alignment alignment = Alignment.LEFT;
		private ArrayList<TextFragment> stringBuffer = new ArrayList<TextFragment>();

		/**
		 * Constructor for the text box builder.
		 * 
		 * @param xStart
		 *            the starting x coordinate of the text box
		 * @param yStart
		 *            the starting y coordinate of the text box
		 */
		public TextBoxBuilder(float xStart, float yStart) {
			this.xStart = xStart;
			this.yStart = yStart;
			this.xEnd = xStart + 200;
			this.yEnd = yStart + 200;
		}

		/**
		 * Method sets the ending x coordinate of the text box
		 * 
		 * @param xEnd
		 *            the ending x coordinate of the text box
		 */
		public TextBoxBuilder xEnd(float xEnd) {
			this.xEnd = xEnd;
			return this;
		}

		/**
		 * Method sets the ending y coordinate of the text box
		 * 
		 * @param yEnd
		 *            the ending y coordinate of the text box
		 */
		public TextBoxBuilder yEnd(float yEnd) {
			this.yEnd = yEnd;
			return this;
		}

		/**
		 * Method sets the background color of the text box
		 * 
		 * @param backgroundColor
		 *            the background color of the text box
		 */
		public TextBoxBuilder backgroundColor(String backgroundColor) {
			this.backgroundColor = backgroundColor;
			return this;
		}

		/**
		 * Method sets the alignment of the text in the text box
		 * 
		 * @param alignment
		 *            the alignment of the text in the text box
		 */
		public TextBoxBuilder alignment(Alignment alignment) {
			this.alignment = alignment;
			return this;
		}

		/**
		 * Method sets the text fragments list to be drawn within the text box
		 * 
		 * @param textFragmentList
		 *            the list of text fragments within the text box
		 */
		public TextBoxBuilder textFragmentList(TextFragmentList textFragmentList) {
			this.stringBuffer = textFragmentList.getList();
			return this;
		}

		/**
		 * Method builds the text box object.
		 * 
		 * @return the new textBox object.
		 */
		public TextHandlerObject build() {
			return new TextHandlerObject(this);
		}

	}

	/**
	 * Class to contain the builder for the textFragment.
	 * 
	 * @author tjd511
	 * @version 1.0 10/03/2015
	 */
	public static class TextFragmentBuilder {
		/* Required fields in the text fragment */
		private final String string;

		/* Optional fields in the text fragment */
		private String fontName = Font.getDefault().getName();
		private String fontColor = "#ff000000";
		private int fontSize = 20;
		private String highlightColor = "#00000000";
		private boolean newline;
		private boolean bold;
		private boolean italic;
		private boolean underline;
		private boolean strikethrough;
		private boolean superscript;
		private boolean subscript;

		/**
		 * Constructor for the TextFragmentBuilder object.
		 * 
		 * @param string
		 *            the text to be stored in the text fragment.
		 */
		public TextFragmentBuilder(String string) {
			this.string = string;
		}

		/**
		 * @param fontName
		 *            the name of the font for the text to be drawn as.
		 */
		public TextFragmentBuilder fontName(String fontName) {
			/* Error checking for fontName */
			String capitalisedFontName = capitaliseEachFirstLetter(fontName);

			/* Loops through the installed fonts and looks for a match */
			if (Font.getFontNames().contains(capitalisedFontName)) {
				this.fontName = fontName;
			}
			return this;
		}

		/**
		 * @param fontColor
		 *            the color of the text
		 */
		public TextFragmentBuilder fontColor(String fontColor) {
			if (verifyColor(fontColor)) {
				this.fontColor = fontColor;
			}
			return this;
		}

		/**
		 * @param fontSize
		 *            the size of the font in pt.
		 */
		public TextFragmentBuilder fontSize(int fontSize) {
			if (fontSize > 0) {
				this.fontSize = fontSize;
			}
			return this;
		}

		/**
		 * @param highlightColor
		 *            the color of the text highlight
		 */
		public TextFragmentBuilder highlightColor(String highlightColor) {
			if (verifyColor(highlightColor)) {
				this.highlightColor = highlightColor;
			}
			return this;
		}

		/**
		 * @param newline
		 *            boolean value for if the text should end in a newline
		 */
		public TextFragmentBuilder newline(boolean newline) {
			this.newline = newline;
			return this;
		}

		/**
		 * @param bold
		 *            boolean value for if the text should be bold
		 */
		public TextFragmentBuilder bold(boolean bold) {
			this.bold = bold;
			return this;
		}

		/**
		 * @param italic
		 *            boolean value for if the text should be italic
		 */
		public TextFragmentBuilder italic(boolean italic) {
			this.italic = italic;
			return this;
		}

		/**
		 * @param underline
		 *            boolean value for if the text should be underlined
		 */
		public TextFragmentBuilder underline(boolean underline) {
			this.underline = underline;
			return this;
		}

		/**
		 * @param strikethrough
		 *            boolean value for if the text should be strikethrough
		 */
		public TextFragmentBuilder strikethrough(boolean strikethrough) {
			this.strikethrough = strikethrough;
			return this;
		}

		/**
		 * @param superscript
		 *            boolean value for if the text should be superscript
		 */
		public TextFragmentBuilder superscript(boolean superscript) {
			this.superscript = superscript;
			return this;
		}

		/**
		 * @param subscript
		 *            boolean value for if the text should be subscript
		 */
		public TextFragmentBuilder subscript(boolean subscript) {
			this.subscript = subscript;
			return this;
		}

		/**
		 * Method builds the text fragment.
		 * 
		 * Defaults:
		 * 
		 * Font size: 20. Font color: "#ff000000" Highlight color: "#00000000"
		 * 
		 * @return a text fragment comprising of all the set parameters for the
		 *         text.
		 */
		public TextFragment build() {
			TextFragment textFragment = new TextFragment(new Defaults());
			textFragment.setText(string);
			textFragment.setFont(fontName);
			textFragment.setFontColor(fontColor);
			textFragment.setFontSize((int) fontSize);
			textFragment.setHighlightColor(highlightColor);
			textFragment.setNewline(newline);
			textFragment.setBold(bold);
			textFragment.setItalicised(italic);
			textFragment.setUnderlined(underline);
			textFragment.setStrikethrough(strikethrough);
			textFragment.setSuperscript(superscript);
			textFragment.setSubscript(subscript);

			return textFragment;
		}

		/**
		 * Method to check validity of any color string
		 * 
		 * @param color
		 *            string to be verified
		 */
		private boolean verifyColor(String color) {
			/*
			 * Checking that color is a 8 digit long hex string starting with a
			 * #
			 */
			return (color.matches("^([#]([0-9a-fA-F]{8}))$"));
		}

		/** Method to capitalise the first letter of each word in a string */
		private String capitaliseEachFirstLetter(String s) {
			String[] words = s.split(" ");
			String finalString = "";
			for (String word : words) {
				finalString += word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase() + " ";
			}
			return finalString.substring(0, finalString.length() - 1);
		}
	}
}
