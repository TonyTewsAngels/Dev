/**
 * 
 */
package wavemedia.text;

import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.sun.webpane.platform.WebPage;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.Screen;

/**
 * Class for handling text and text boxes on a javafx group.
 * 
 * @author tjd511
 * @version 1.0 23:01/2015
 */
public class TextHandler {

    private final int alphaStartChar = 1;
    private final int rStartChar = 3;
    private final int gStartChar = 5;
    private final int bStartChar = 7;

    private final int alphaEndChar = 3;
    private final int rEndChar = 5;
    private final int gEndChar = 7;
    private final int bEndChar = 9;

    private ArrayList<TextFragment> stringBuffer = new ArrayList<TextFragment>();

    private Group group;

    /**
     * Constructor for the textHandler.
     * 
     * @param group
     *            The group that all of the text is to be drawn to.
     */
    public TextHandler(Group group) {
        this.group = group;
    }

    /**
     * Method adds a string to the buffer for later drawing on the screen. A
     * text box can be formed and printed on the screen by calling the
     * drawBuffer(int xStartPos, int yStartPos, int xEndPos, int yEndPos, String
     * alignment) method.
     * 
     * @param string
     *            the string to be added to the buffer
     * @param fontName
     *            the name of the font for the string
     * @param fontSize
     *            the size of the font in pt
     * @param fontColor
     *            the color for the string to be drawn in, in the form of a 8
     *            digit hex number, ARGB.
     * @param stringCase
     *            enum that controls the case of the string stored. Options:
     *            TextCase.UPPER - all chars get changed to upper case.
     *            TextCase.LOWER - all chars get changed to lower case.
     *            TextCase.CAPITALISED - the first letter of each word in the
     *            string is capitalised
     * @param TextAttributes
     *            varargs of TextAttribute enum that controls what effects are
     *            applied to the string. Options are TextAttribute.BOLD,
     *            TextAttribute.ITALIC, TextAttribute.UNDERLINE,
     *            TextAttribute.STRIKETHROUGH, TextAttribute.SUPERSCRIPT and
     *            TextAttribute.SUBSCRIPT. If both SUPERSCRIPT and SUBSCRIPT,
     *            the text is displayed as SUPERSCRIPT.
     * */
    public void addStringToBuffer(String string, String fontName, int fontSize, String fontColor,
            String highlightColor, TextAttribute... TextAttributes) {
        TextFragment currentString = new TextFragment();
        currentString.setText(string);

        /* Set all parameters that do not require error checking */
        for (TextAttribute currentAttribute : TextAttributes) {
            switch (currentAttribute) {
            case BOLD:
                currentString.setBold(true);
                break;
            case ITALIC:
                currentString.setItalicised(true);
                break;
            case UNDERLINE:
                currentString.setUnderlined(true);
                break;
            case STRIKETHROUGH:
                currentString.setStrikethrough(true);
                break;
            case SUPERSCRIPT:
                currentString.setSuperscript(true);
                break;
            case SUBSCRIPT:
                currentString.setSubscript(true);
                break;
            }
        }

        /* Error checking for fontName */
        String capitalisedFontName = capitaliseEachFirstLetter(fontName);

        /* Set the default font to be the system default font */
        currentString.setFont(Font.getDefault().getName());

        /* Loops through the installed fonts and looks for a match */
        if (Font.getFontNames().contains(capitalisedFontName)) {
            currentString.setFont(fontName);
        }

        /* Error checking for fontSize */
        if (fontSize > 0)
            currentString.setFontSize(fontSize);
        else {
            System.out.print("The fontSize integer is smaller than 0. Setting it to 10.");
            currentString.setFontSize(10);
        }

        /* Sets fontColor to blank if the fontColor is not a valid hex string */
        if (verifyColor(fontColor))
            currentString.setColor(fontColor);
        else {
            System.err.println("The fontColor string is in an incorrect format. Setting it to blank.");
            System.out.println(fontColor);
            currentString.setColor("#00000000");
        }

        /*
         * Sets highlightColor to blank if the fontColor is not a valid hex
         * string
         */
        if (verifyColor(highlightColor))
            currentString.setHighlightColor(highlightColor);
        else {
            System.err.println("The highlightColor string is in an incorrect format. Setting it to blank.");
            currentString.setHighlightColor("#00000000");
        }

        stringBuffer.add(currentString);
    }

    /** Error checking method for printing all the stored strings in the buffer. */
    public void printBuffer() {
        for (int i = 0; i < stringBuffer.size(); i++)
            System.out.println(stringBuffer.get(i).getText());
    }

    /** Method for clearing the string buffer */
    public void clearBuffer() {
        stringBuffer.clear();
    }

    /**
     * Method forms a text box of a set size and adds all the strings contained
     * in the buffer to it. Then draws the text box on the group specified
     * during the instantiation of the object.
     * 
     * @param xStartPos
     *            the starting x coordinate of the text box
     * @param yStartPos
     *            the starting y coordinate of the text box
     * @param xEndPos
     *            the ending x coordinate of the text box
     * @param yEndPos
     *            the ending y coordinate of the text box
     * @param alignment
     *            a string that can be used to control the alignment of the text
     *            within the text box. Options: "centre" - centres the text.
     *            "right" - sets the text to be right aligned. "justify" -
     *            justifies the text. "left" - sets the text to be left aligned.
     * 
     * */
    public void drawBuffer(int xStartPos, int yStartPos, int xEndPos, int yEndPos, String backgroundColor,
            Alignment alignment) {
        /* Swaps around coordinates if incorrectly passed in */
        if (xStartPos > xEndPos) {
            int temp = xStartPos;
            xStartPos = xEndPos;
            xEndPos = temp;
        }
        if (yStartPos > yEndPos) {
            int temp = yStartPos;
            yStartPos = yEndPos;
            yEndPos = temp;
        }

        /* Checking that backgroundColor is a 8 digit long hex string */
        if (!verifyColor(backgroundColor)) {
            System.err
                    .println("The backgroundColor string is in an incorrect format. Buffer has been cleared, text box will not be displayed. ");
            stringBuffer.clear();
            return;
        }

        /* Instantiate the WebView that will be used to display the text */
        WebView webView = new WebView();

        /* Set starting position and width of the panel */
        webView.relocate(xStartPos, yStartPos);
        webView.setPrefWidth(xEndPos - xStartPos);

        /* Load a css file that hides the scrollbar added by webView */
        File f = new File("custom.css");
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

        /* Section for resizing the text to fit in the box. */
        webView.setPrefHeight(yEndPos - yStartPos);

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
     * Method forms a transparent text box of a large size to display a single
     * string at one point. Wraps at edge of screen.
     * 
     * @param xStartPos
     *            the starting x coordinate of the text box
     * @param yStartPos
     *            the starting y coordinate of the text box
     */
    public void drawBuffer(int xStartPos, int yStartPos) {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        drawBuffer(xStartPos, yStartPos, (int) primaryScreenBounds.getWidth() - xStartPos,
                (int) primaryScreenBounds.getHeight() - yStartPos, "#00000000", Alignment.LEFT);
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
            String colorString = currentString.getColor();

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

            /*
             * Combines the current htmlstring (to preserve anything already
             * there), the preBodyAttributes, the body of the string and then
             * postBodyAttributes.
             */
            htmlString = htmlString + preBodyAttributes + currentString.getText() + postBodyAttributes;
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
     *            8 digit hex string specifying a color in ARGB format
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
        /* Checking that backgroundColor is a 8 digit long hex string */
        return (color.matches("^([#]([0-9a-fA-F]{8}))$"));
    }

    /**
     * Method draws a string on the group set by the constructor.
     * 
     * @param string
     *            the string to be drawn to the group
     * @param xPos
     *            the starting x coordinate of the string
     * @param yPos
     *            the starting y coordinate of the string
     * @param fontName
     *            a string containing the font of the string. If font is not
     *            recognised, it defaults to the system default font.
     * @param fontSize
     *            the size of the font in pt
     * @param fontColor
     *            8 bit hex string specifying the font color in ARGB format
     * @param highlightColor
     *            8 bit hex string specifying the highlight color in ARGB format
     * @param TextAttributes
     *            varargs of TextAttribute enum that controls what effects are
     *            applied to the string. Options are TextAttribute.BOLD,
     *            TextAttribute.ITALIC, TextAttribute.UNDERLINE,
     *            TextAttribute.STRIKETHROUGH, TextAttribute.SUPERSCRIPT and
     *            TextAttribute.SUBSCRIPT. If both SUPERSCRIPT and SUBSCRIPT,
     *            the text is displayed as SUPERSCRIPT
     */
    public void drawString(String string, int xPos, int yPos, String fontName, int fontSize, String fontColor,
            String highlightColor, TextAttribute... TextAttributes) {
        ArrayList<TextFragment> tempBuffer = new ArrayList<TextFragment>(stringBuffer);
        stringBuffer.clear();

        addStringToBuffer(string, fontName, fontSize, fontColor, highlightColor, TextAttributes);
        drawBuffer(xPos, yPos);

        /* Store stringBuffer back */
        stringBuffer = tempBuffer;
    }

    /**
     * Method drawString for not specifying font name, size, color and highlight
     * color
     */
    public void drawString(String string, int xPos, int yPos) {
        drawString(string, xPos, yPos, Font.getDefault().getName(), 16, "#ff000000", "#00000000");
    }

    /** Method to capitalise the first letter of each word in a string */
    private static String capitaliseEachFirstLetter(String s) {
        String[] words = s.split(" ");
        String finalString = "";
        for (String word : words) {
            finalString += word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase() + " ";
        }
        return finalString.substring(0, finalString.length() - 1);
    }

}
