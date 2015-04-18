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

public class RichTextXMLHandler extends DefaultHandler{
    private RichText richText;
    
    private ArrayList<XMLNotification> errorList;
    private XMLReader xmlReader;
    private DefaultHandler parent;
    private Lesson lesson;
    private Page page;
    private TextObject text;
    
    String readBuffer;
    
    public RichTextXMLHandler(XMLReader nXMLReader, DefaultHandler nParent, Lesson nLesson, Page nPage, TextObject nText, ArrayList<XMLNotification> nErrorList, Attributes richTextAttrs) {
        this.xmlReader = nXMLReader;
        this.parent = nParent;

        this.errorList = nErrorList;
        
        this.lesson = nLesson;
        this.page = nPage;
        this.text = nText;
        
        richText = new RichText(null, null, 0, null);
        
        richTextStart(richTextAttrs);
    }
    
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
    
    public void endHandler() {
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
        
        text.textFragments.add(richText);
        
        xmlReader.setContentHandler(parent);
    }
    
    public void characters(char[] ch, int start, int length) {
        String str = new String(ch, start, length);
        str = str.trim();
        if(!str.isEmpty()) {
            readBuffer = str;
        }
    }
    
    public void richTextStart(Attributes attrs) {
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
        
        ArrayList<String> settings = new ArrayList<String>();
        
        if(bold) settings.add(new String("bold"));
        if(italic) settings.add(new String("italic"));
        if(underline) settings.add(new String("underline"));
        if(superScript) settings.add(new String("superscript"));
        if(subScript) settings.add(new String("subscript"));
        if(strikeThrough) settings.add(new String("strikethrough"));
        if(newLine) settings.add(new String("newline"));
        
        richText = new RichText(null, fontStr, fontSize, fontColorStr, settings.toArray(new String[settings.size()]));
    } 
}


