/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved. 
 */
package teacheasy.xml.contenthandlers;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import teacheasy.data.Lesson;
import teacheasy.data.Page;
import teacheasy.data.RichText;
import teacheasy.data.TextObject;
import teacheasy.xml.XMLElement;
import teacheasy.xml.util.*;
import teacheasy.xml.util.XMLNotification.*;

/**
 * The XML content handler for a rich text element.
 * 
 * @author  Alistair Jewers
 * @version 1.0 Apr 12 2015 
 */
public class RichTextXMLHandler extends DefaultHandler{
    
    /* Data objects */
    private RichText richText;
    private Lesson lesson;
    private Page page;
    private TextObject text;
    
    /** List of warnings and errors */
    private ArrayList<XMLNotification> errorList;
    
    /** Reference to the xml reader */
    private XMLReader xmlReader;
    
    /** The handler that activated this one */
    private DefaultHandler parent;
    
    /* The text buffer */
    String readBuffer;
    
    /**
     * Constructor.
     * 
     * @param nXMLReader The XML reader reference.
     * @param nParent The parent handler.
     * @param nLesson The lesson being constructed.
     * @param nPage The page being constructed.
     * @param nText The text box this rich text will be added to.
     * @param nErrorList The full error and warnings list.
     * @param richTextAttrs The attributes found in the XML.
     */
    public RichTextXMLHandler(XMLReader nXMLReader, DefaultHandler nParent, Lesson nLesson, Page nPage, TextObject nText, ArrayList<XMLNotification> nErrorList, Attributes richTextAttrs) {
        /* Set references */
        this.xmlReader = nXMLReader;
        this.parent = nParent;
        this.errorList = nErrorList;
        this.lesson = nLesson;
        this.page = nPage;
        this.text = nText;
        
        /* Create an empty rich text object */
        richText = new RichText(null, null, 0, null);
        
        /* Begin constructing the rich text object */
        richTextStart(richTextAttrs);
    }
    
    /**
     * Called when the end of an XML element is found.
     */
    public void endElement(String uri, String localName, String qName) {
        switch(XMLElement.check(qName.toUpperCase())) {
            /* If the image element has finished, return to the parent */
            case RICHTEXT:
                endHandler();
                break;            
            default:
                break;
        }
    }
    
    /**
     * Called when the handler is finished to pass control back to the parent handler.
     */
    public void endHandler() {
        /* Check for null text */
        if(readBuffer == null) {
            errorList.add(new XMLNotification(Level.WARNING, 
                          "Page " + lesson.pages.size() + 
                          ", Object " + page.getObjectCount() +
                          " (Text), RichText" + (text.textFragments.size()-1) +
                          "Text missing. Empty string inserted"));
            
            richText.setText("");
        } else {
            richText.setText(readBuffer);
        }
        
        /* Add the rich text to the text box */
        text.textFragments.add(richText);
        
        /* Pass control back to the parent handler */
        xmlReader.setContentHandler(parent);
    }
    
    /**
     * Called when text is found in the XML.
     */
    public void characters(char[] ch, int start, int length) {
        String str = new String(ch, start, length);
        if(!str.isEmpty()) {
            readBuffer = str;
        }
    }
    
    /**
     * Called to start constructing the rich text object.
     * 
     * @param attrs The attributes found in XML.
     */
    public void richTextStart(Attributes attrs) {
        /* Parse the strings from XML attributes */
        String fontStr = attrs.getValue("font");
        String fontSizeStr = attrs.getValue("fontsize");
        String fontColorStr = attrs.getValue("fontcolor");
        String boldStr = attrs.getValue("bold");
        String italicStr = attrs.getValue("italic");
        String underlineStr = attrs.getValue("underline");
        String strikethroughStr = attrs.getValue("strikethrough");
        String superscriptStr = attrs.getValue("superscript");
        String subscriptStr = attrs.getValue("subscript");
        String newLineStr = attrs.getValue("newline");
        
        /* Attempt to parse values, add errors as necessary */
        if(fontStr == null) {
            errorList.add(new XMLNotification(Level.WARNING, 
                "Page " + lesson.pages.size() + 
                ", Object " + page.getObjectCount() +
                " (Text), RichText " + (text.textFragments.size()) +
                " Font missing. Default used"));
            fontStr = lesson.defaultSettings.getFont();
        }
        
        int fontSize = XMLUtil.checkInt(fontSizeStr, lesson.defaultSettings.getFontSize(), Level.WARNING, errorList,
                "Page " + lesson.pages.size() + 
                ", Object " + page.getObjectCount() +
                " (Text), RichText " + (text.textFragments.size()) +
                " Font size ");
        
        fontColorStr = XMLUtil.checkColor(fontColorStr, lesson.defaultSettings.getFontColour(), Level.WARNING, errorList,
                "Page " + lesson.pages.size() + 
                ", Object " + page.getObjectCount() +
                " (Text), RichText " + (text.textFragments.size()) +
                " Font color ");
        
        boolean bold = XMLUtil.checkBool(boldStr, false, Level.WARNING, errorList, 
                "Page " + lesson.pages.size() + 
                ", Object " + page.getObjectCount() +
                " (Text), RichText " + (text.textFragments.size()) +
                " Bold ");
        
        boolean italic = XMLUtil.checkBool(italicStr, false, Level.WARNING, errorList, 
                "Page " + lesson.pages.size() + 
                ", Object " + page.getObjectCount() +
                " (Text), RichText " + (text.textFragments.size()) +
                " Italic ");
        
        boolean underline = XMLUtil.checkBool(underlineStr, false, Level.WARNING, errorList, 
                "Page " + lesson.pages.size() + 
                ", Object " + page.getObjectCount() +
                " (Text), RichText " + (text.textFragments.size()) +
                " Underline ");
        
        boolean superScript = XMLUtil.checkBool(superscriptStr, false, Level.WARNING, errorList, 
                "Page " + lesson.pages.size() + 
                ", Object " + page.getObjectCount() +
                " (Text), RichText " + (text.textFragments.size()) +
                " Superscript ");
        
        boolean subScript = XMLUtil.checkBool(subscriptStr, false, Level.WARNING, errorList, 
                "Page " + lesson.pages.size() + 
                ", Object " + page.getObjectCount() +
                " (Text), RichText " + (text.textFragments.size()) +
                " Subscript ");
        
        boolean strikeThrough = XMLUtil.checkBool(strikethroughStr, false, Level.WARNING, errorList, 
                "Page " + lesson.pages.size() + 
                ", Object " + page.getObjectCount() +
                " (Text), RichText " + (text.textFragments.size()) +
                " Strike-through ");
        
        boolean newLine = XMLUtil.checkBool(newLineStr, false, Level.WARNING, errorList, 
                "Page " + lesson.pages.size() + 
                ", Object " + page.getObjectCount() +
                " (Text), RichText " + (text.textFragments.size()) +
                " New line ");
        
        /* Convert the emphasis settings into an array list */
        ArrayList<String> settings = new ArrayList<String>();
        
        if(bold) settings.add(new String("bold"));
        if(italic) settings.add(new String("italic"));
        if(underline) settings.add(new String("underline"));
        if(superScript) settings.add(new String("superscript"));
        if(subScript) settings.add(new String("subscript"));
        if(strikeThrough) settings.add(new String("strikethrough"));
        if(newLine) settings.add(new String("newline"));
        
        /* Construct the new Rich Text object */
        richText = new RichText(null, fontStr, fontSize, fontColorStr, settings.toArray(new String[settings.size()]));
    } 
}


