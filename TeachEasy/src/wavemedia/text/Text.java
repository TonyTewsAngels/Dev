/** (c) Copyright by WaveMedia. */
package wavemedia.text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import com.sun.webpane.platform.WebPage;

import javafx.scene.Group;
import javafx.scene.web.WebView;

/**
 * Class for handling text and text boxes on a javafx group.
 * 
 * @author tjd511
 * @version 3.0 07/04/2015
 */
public class Text {

	/*
	 * Constants for where in the string "#00112233" the A, R, G and B portions
	 * of the color string are.
	 */
	private final int alphaStartChar = 1;
	private final int rStartChar = 3;
	private final int gStartChar = 5;
	private final int bStartChar = 7;
	private final int alphaEndChar = 3;
	private final int rEndChar = 5;
	private final int gEndChar = 7;
	private final int bEndChar = 9;

	/*
	 * ArrayList of TextFragments used to store details about the strings that
	 * will be displayed.
	 */
	private ArrayList<TextFragment> stringBuffer = new ArrayList<TextFragment>();

	/* Group onto which the text boxes are drawn. */
	private Group group;

	private WebView webView;

	/**
	 * Constructor for the textHandler.
	 * 
	 * @param group
	 *            The group that all of the text is to be drawn to.
	 */
	public Text(Group group) {
		this.group = group;
		
		/* Instantiate the WebView that will be used to display the text */
		webView = new WebView();
	}

	/**
	 * Method for forming and drawing a text box.
	 * 
	 * @param textBox
	 *            the text box to be drawn. Must be formed using the TextBox
	 *            builder.
	 * @see {@link TextHandlerObject.TextObject}
	 */
	public void drawText(TextHandlerObject textBox) {
		stringBuffer = textBox.getStringBuffer();

		float xStartPos = textBox.getXStart();
		float yStartPos = textBox.getYStart();
		float xEndPos = textBox.getXEnd();
		float yEndPos = textBox.getYEnd();
		String backgroundColor = textBox.getBackgroundColor();
		Alignment alignment = textBox.getAlignment();

		drawBuffer(xStartPos, yStartPos, xEndPos, yEndPos, backgroundColor, alignment);
	}

	/**
	 * Method to set the visibility of the textbox.
	 * 
	 * @param visible
	 *            boolean containing if the textbox should be visible or not
	 */
	public void setVisible(boolean visible) {
		if (webView != null) {
			webView.setVisible(visible);
		}
	}

	/** Method to send the textbox to the back of the group. */
	public void toBack() {
		if (webView != null) {
			webView.toBack();
		}
	}

	/** Method to send the textbox to the back of the group. */
	public void toFront() {
		if (webView != null) {
			webView.toFront();
		}
	}

	/**
	 * Method forms a text box of a set size and color and adds all the strings
	 * contained in the buffer to it. Then draws the text box on the group
	 * specified during the instantiation of the object.
	 * 
	 * @param xStartPos
	 *            the starting x coordinate of the text box
	 * @param yStartPos
	 *            the starting y coordinate of the text box
	 * @param xEndPos
	 *            the ending x coordinate of the text box
	 * @param yEndPos
	 *            the ending y coordinate of the text box
	 * @param backgroundColor
	 *            the color for the background of the text box, in the form of a
	 *            8 digit hex number, ARGB.
	 * @param alignment
	 *            an alignment enum that sets the alignment of the text in the
	 *            text box.
	 * 
	 */
	private void drawBuffer(float xStartPos, float yStartPos, float xEndPos, float yEndPos, String backgroundColor,
			Alignment alignment) {

		/* Swaps around coordinates if incorrectly passed in */
		if (xStartPos > xEndPos) {
			float temp = xStartPos;
			xStartPos = xEndPos;
			xEndPos = temp;
		}
		if (yStartPos > yEndPos) {
			float temp = yStartPos;
			yStartPos = yEndPos;
			yEndPos = temp;
		}

		/* Checking that backgroundColor is a 8 digit long hex string */
		if (!verifyColor(backgroundColor)) {
			backgroundColor = "#00000000";
		}

		/* Set starting position, height and width of the panel */
		webView.relocate(xStartPos, yStartPos);
		webView.setPrefWidth(xEndPos - xStartPos);
		webView.setPrefHeight(yEndPos - yStartPos);

		/* Load a css file that hides the scrollbar added by webView */
		File f = new File("custom.css");

		if (!f.isFile())
			createCustomCss();

		try {
			webView.getEngine().setUserStyleSheetLocation(f.toURI().toURL().toString());
		} catch (MalformedURLException ex) {
			System.err.println("custom.css does not exist.");
		}

		/*
		 * Disable the WebView so that scrolling + text selecting cannot happen.
		 * Also disables the right click menu popup.
		 */
		webView.setDisable(true);

		/*
		 * Loads a HTML string containing all the data about the strings into
		 * the webView
		 */
		webView.getEngine().loadContent(createHTMLStringFromBuffer(backgroundColor, alignment));

		/*
		 * Remove the background from the webView panel. Adapted from
		 * http://stackoverflow
		 * .com/questions/12421250/transparent-background-in-
		 * the-webview-in-javafx
		 */
		Field field;
		try {
			field = webView.getEngine().getClass().getDeclaredField("page");
			field.setAccessible(true);
			WebPage page = (WebPage) field.get(webView.getEngine());
			page.setBackgroundColor((new java.awt.Color(0, 0, 0, 0)).getRGB());
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			System.err.println("An error occured when trying to build a text box.");
			System.exit(-1);
		} catch (SecurityException e) {
			e.printStackTrace();
			System.err.println("An error occured when trying to build a text box.");
			System.exit(-1);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			System.err.println("An error occured when trying to build a text box.");
			System.exit(-1);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			System.err.println("An error occured when trying to build a text box.");
			System.exit(-1);
		}

		group.getChildren().addAll(webView);
	}

	/**
	 * Formats a HTML string in order to display a rich text box full with the
	 * fragments from the buffer.
	 * 
	 * @param backgroundColor
	 *            8 bit hex string of style ARGB.
	 * 
	 * @param alignment
	 *            an enum used to specify how the text is aligned in the text
	 *            box. Can be CENTER, RIGHT, JUSTIFY or LEFT. Defaults to LEFT.
	 */
	private String createHTMLStringFromBuffer(String backgroundColor, Alignment alignment) {
		/*
		 * Initialise string with the initial attribute of a body element that
		 * has a tag that stops the html being edited by the user.
		 */
		String htmlString = "<body contenteditable=\"false\" style=\"background-color: "
				+ formatColorHTMLString(backgroundColor) + "\">";

		/*
		 * Append the tag for the type of text alignment required. Set
		 * alignment, with the default value being left
		 */
		switch (alignment) {
		case CENTER:
			htmlString = htmlString + "<p style=\"text-align: center;\">";
			break;
		case RIGHT:
			htmlString = htmlString + "<p style=\"text-align: right;\">";
			break;
		case JUSTIFY:
			htmlString = htmlString + "<p style=\"text-align: justify;\">";
			break;
		default:
			htmlString = htmlString + "<p style=\"text-align: left;\">";
			break;
		}

		/**
		 * Loops through all the items in the buffer, and builds a dynamic HTML
		 * string that can be used to generate a "text box" in a webView.
		 */
		for (int i = 0; i < stringBuffer.size(); i++) {
			/* Get the current text fragment */
			TextFragment currentString = stringBuffer.get(i);

			/*
			 * Initialise two strings that will be used to store the tags before
			 * and after the text body.
			 */
			String preBodyAttributes = "";
			String postBodyAttributes = "";

			/*
			 * Appends tags for superscript and subscript, but giving priority
			 * to superscript
			 */
			if (currentString.isSuperscript()) {
				preBodyAttributes = preBodyAttributes + "<sup>";
				postBodyAttributes = postBodyAttributes + "</sup>";
			} else if (currentString.isSubscript()) {
				preBodyAttributes = preBodyAttributes + "<sub>";
				postBodyAttributes = postBodyAttributes + "</sub>";
			}

			/* Section for font, font size and font color. */
			String colorString = currentString.getFontColor();

			/* Initialise new string to store the preBody font size information */
			String fontSizeString = "<span style=\"font-size:" + currentString.getFontSize() + "px\">";

			/*
			 * Initialise new string to store the preBody font name, and font
			 * color information (last 6 chars of color string contain RRGGBB in
			 * hex).
			 */
			String fontNameAndColorString = "<font face =\"" + currentString.getFont() + "\" color=\""
					+ colorString.substring(rStartChar, bEndChar) + "\">";

			/*
			 * Highlighting section. Initialise string with the 8bit hex value
			 * for highlight.
			 */
			String highlightString = currentString.getHighlightColor();

			/* Combine all of the highlight strings */
			String highlightingString = "<span style=\"background-color: " + formatColorHTMLString(highlightString)
					+ "\">";

			/*
			 * Convert the opacity to a 1 decimal place number so that it works
			 * in the opacity tag (first 2 chars of color string contain alpha
			 * in hex). 16 to convert from hex to int, dividing by 255f to
			 * convert to a decimal from 0 to 1
			 */
			DecimalFormat df = new DecimalFormat("0.0");
			String opacityFormatted = df.format((Integer.parseInt(colorString.substring(alphaStartChar, alphaEndChar),
					16)) / 255f);

			/*
			 * Initialise new string to store the preBody font opacity
			 * information
			 */
			String fontOpacityString = "<span style=\"opacity:" + opacityFormatted + "\">";

			/* Combines the attribute strings */
			preBodyAttributes = preBodyAttributes + fontSizeString + fontNameAndColorString + highlightingString
					+ fontOpacityString;

			/* Adds tags for bold, italic, underline and strike through */
			if (currentString.isBold()) {
				preBodyAttributes = preBodyAttributes + "<b>";
				postBodyAttributes = postBodyAttributes + "</b>";
			}
			if (currentString.isItalicised()) {
				preBodyAttributes = preBodyAttributes + "<i>";
				postBodyAttributes = postBodyAttributes + "</i>";
			}
			if (currentString.isUnderlined()) {
				preBodyAttributes = preBodyAttributes + "<u>";
				postBodyAttributes = postBodyAttributes + "</u>";
			}
			if (currentString.isStrikethrough()) {
				preBodyAttributes = preBodyAttributes + "<strike>";
				postBodyAttributes = postBodyAttributes + "</strike>";
			}

			/*
			 * Close all of the elements that have been added to the html
			 * string. These are not correctly ordered, but it does not matter.
			 */
			postBodyAttributes = postBodyAttributes + "</font>" + "</span>" + "</span>" + "</span>";

			/* Add a new line tag if required */
			if (currentString.endsWithNewline())
				postBodyAttributes = postBodyAttributes + "<br>";

			/*
			 * Replaces the < character with the html expression for a <
			 * character, "%lt". This is to prevent html tags within the entered
			 * string from messing with the text box.
			 */
			String editedString = currentString.getText().replaceAll("<", "&lt");

			/*
			 * Combines the current htmlstring (to preserve anything already
			 * there), the preBodyAttributes, the body of the string and then
			 * postBodyAttributes.
			 */
			htmlString = htmlString + preBodyAttributes + editedString + postBodyAttributes;
		}

		/* Empty the buffer so new strings can be added */
		stringBuffer.clear();

		/*
		 * Append the closing tags for the initial attribute and the text
		 * alignment paragraph tag.
		 */
		return htmlString + "</p></body>";
	}

	/**
	 * Method to format a string in the style rgba(r,g,b,a) for html styling
	 * 
	 * @param colorString
	 *            8 digit hex string specifying a color in ARGB format.
	 */
	private String formatColorHTMLString(String colorString) {
		String formattedColorString = "rgba(";

		/*
		 * Use the decimal format to convert the opacity to 1dp number so it
		 * works with rgba css tag
		 */
		DecimalFormat df = new DecimalFormat("0.0");

		String highlightingOpacityFormatted = df.format((Integer.parseInt(
				colorString.substring(alphaStartChar, alphaEndChar), 16)) / 255f);

		/*
		 * Convert r, g and b from 2digit hex to integer values to work with
		 * rgba css tag
		 */
		String redHighlightingFormatted = Integer.toString((Integer.parseInt(
				colorString.substring(rStartChar, rEndChar), 16)));
		String greenHighlightingFormatted = Integer.toString((Integer.parseInt(
				colorString.substring(gStartChar, gEndChar), 16)));
		String blueHighlightingFormatted = Integer.toString((Integer.parseInt(
				colorString.substring(bStartChar, bEndChar), 16)));

		formattedColorString = formattedColorString + redHighlightingFormatted + "," + greenHighlightingFormatted + ","
				+ blueHighlightingFormatted + "," + highlightingOpacityFormatted + ")";

		return formattedColorString;
	}

	/**
	 * Method to check validity of any color string
	 * 
	 * @param color
	 *            string to be verified
	 */
	private boolean verifyColor(String color) {
		/* Checking that color is a 8 digit long hex string starting with a # */
		return (color.matches("^([#]([0-9a-fA-F]{8}))$"));
	}

	/**
	 * Method to create the custom.css file used for removing the background and
	 * scrollbars of the WebView.
	 */
	private void createCustomCss() {
		Writer writer = null;
		File file = new File("custom.css");

		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write("body {\n	    overflow-x: hidden;\n	    overflow-y: hidden;\n}\n::-webkit-scrollbar { \n   width: 16px;\n}::-webkit-scrollbar-track  { \n   background-color: white;\n}");
		} catch (IOException ex) {
			System.err.println("IOException occured during custom.css creation.");
		} finally {
			try {
				writer.close();
			} catch (IOException ex) {
				System.err.println("IOException occured during custom.css creation.");
			}
		}
	}
}