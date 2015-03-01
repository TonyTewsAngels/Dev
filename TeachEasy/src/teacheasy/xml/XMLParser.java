/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.xml;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import teacheasy.data.*;
import teacheasy.data.GraphicObject.*;
import teacheasy.data.MultipleChoiceObject.*;
import teacheasy.data.lessondata.*;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

/**
 * This class handles XML parsing, 
 * storing the data as Lesson objects.
 * 
 * @version 	1.0 05 Feb 2015
 * @author 		Alistair Jewers
 */
public class XMLParser extends DefaultHandler{        
	/** The lesson being constructed */
	private Lesson currentLesson;
	
	/** The page being constructed */
	private Page currentPage;
	
	/** The text object currently being constructed */
	private TextObject currentText;
	
	/** The text fragment currently being constructed */
	private RichText currentTextFragment;
	
	/** The graphics object being constructed */
	private GraphicObject currentGraphic;
	
	/** The multiple choice object currently being constructed */
	private MultipleChoiceObject currentMultiChoice;
	
	/** The answer currently being constructed */
	private boolean currentAnswer;
	
	/** The button currently being constructed */
	private ButtonObject currentButton;
	
	/** XML PWS Indicator */
	private boolean standard = false;
	
	/** An array list describing the current position in the XML nest */
	private List<String> elementList;
	
	/** The most recently read string of characters */
	private String readBuffer;
	
	/** List of errors that occurred during parsing */
	private ArrayList<String> errorList;
	
	/** Flag to indicate the object currentl being constructed is invalid */
	private boolean invalidConstruct;
	
	/** Flag to indicate if document info was found */
	private boolean documentHasInfo;
	
	/** Flag to indicate if default settings were found */
    private boolean documentHasDefaults;
	
	/** Constructor Method */
	public XMLParser() {
	    /* Instantiate the xml position tracking array list */
	    elementList = new ArrayList<String>();
	    
	    /* Instantiate an empty lesson */
		currentLesson = new Lesson();
		
		/* Initialise the invalid construction flag to false */
		invalidConstruct = false;
		
		/* Initialise the document info flag to false */
		documentHasInfo = false;
		
		/* Initialise the document default flag to false */
		documentHasDefaults = false;
	}
	
	/** Parses an XML file */
	public ArrayList<String> parse(String filename) {	    
	    /* Instantiate the error list */
	    errorList = new ArrayList<String>();
	    
	    /* Reset the document info flag */
	    documentHasInfo = false;
	    
	    /* Reset the defult settings flag */
	    documentHasDefaults = false;
	    
		try {
			/* Create an instance of the SAX Parser */
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			
			/* Parse the file */
			if(new File(filename).isFile()) {
			    saxParser.parse(filename,  this);
			} else {
			    errorList.add(new String("File Does Not Exist"));
			}
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		
		/* Return the error list */
		return errorList;
	}
	
	/** Returns the lesson parsed from xml */
	public Lesson getLesson() {
		return currentLesson;
	}
	
	/** Called by parser at the start of an element */
	public void startElement(String uri, String localName, String qName, Attributes attrs) throws SAXException{		
		/* Add the new element to the list */
	    elementList.add(qName);
	    
	    /* Clear the readbuffer */
		readBuffer = null;
		
		/* Find out what attribute has just started */
		switch (XMLElement.check(qName.toUpperCase())) {
		    case SLIDESHOW:
		        currentLesson = new Lesson();
		        break;
		    case SLIDE:
		        handleSlideElement(attrs);
		        break;
		    case DOCUMENTINFO:
		        documentHasInfo = true;
		        break;
		    case DEFAULTSETTINGS:
                documentHasDefaults = true;
                break;
		    
		    /* Media elements */
		    case TEXT:
		        handleTextElement(attrs);
		        break;
		    case RICHTEXT:
		        handleRichTextElement(attrs);
		        break;
		    case IMAGE:
		        handleImageElement(attrs);
		        break;
		    case AUDIO:
		        handleAudioElement(attrs);
		        break;
		    case VIDEO:
		        handleVideoElement(attrs);
		        break;
		    case GRAPHIC:
		        handleGraphicElement(attrs);
		        break;
		    case CYCLICSHADING:
		        handleCyclicShadingElement(attrs);
		        break; 
		    case ANSWERBOX:
		        handleAnswerBoxElement(attrs);
		        break;
		    case BUTTON:
                handleButtonElement(attrs);
                break;
		    case MULTIPLECHOICE:
                handleMultipleChoiceElement(attrs);
                break;
		    case ANSWER:
		        currentAnswer = Boolean.parseBoolean(attrs.getValue("correct"));
		    default:
		        break;
		}
	}
	
	/** Called by parser at the end of an element */
	public void endElement(String uri, String localName, String qName) throws SAXException{
		elementList.remove(elementList.size() - 1);
		
		switch (XMLElement.check(qName.toUpperCase())) {
            /* Establish if the XML is standard or not */   
            case GROUPID:
                if (readBuffer.equals("0")) {
                    standard = true;
                } else {
                    standard = false;
                }
                break;
                
            /* End of lesson reached */
            case SLIDESHOW:
                if(!documentHasInfo) { errorList.add("Document info not found"); }
                if(!documentHasDefaults) { errorList.add("Default settings not found"); }
                break;
                
            /* Document Info Elements */
            case AUTHOR:
                currentLesson.lessonInfo.setAuthor(readBuffer);
                break;
            case VERSION:
                currentLesson.lessonInfo.setVersion(readBuffer);
                break;
            case COMMENT:
                currentLesson.lessonInfo.setComment(readBuffer);
                break;
            case DATECREATED:
                currentLesson.lessonInfo.setDateCreated(readBuffer);
                break;
            case TOTALMARKS:
                handleTotalMarks();
                break;
            case LESSONNAME:
                currentLesson.lessonInfo.setLessonName(readBuffer);
                break;
                
            /* Default Settings Elements */
            case BACKGROUNDCOLOR:
                handleBackgroundColor();
                break;
            case FONT:
                currentLesson.defaultSettings.setFont(readBuffer);
                break;
            case FONTSIZE:
                handleFontSize();
                break;
            case FONTCOLOR:
                handleFontColor();
                break;

            /* Grade Settings Elements */
            case PASSBOUNDARY:
                handlePassBoundary();
                break;
                
            /* Slide Element */
            case SLIDE:
                currentLesson.pages.add(currentPage);
                break;
                
            /* Media Elements */
            case TEXT:
                handleTextEnd(readBuffer);
                break;
            case RICHTEXT:
                handleRichTextEnd(readBuffer);
                break;
            case GRAPHIC:
                if(invalidConstruct) {
                    invalidConstruct = false;
                } else {
                    currentPage.pageObjects.add(currentGraphic);
                }
                break;
            case MULTIPLECHOICE:
                currentPage.pageObjects.add(currentMultiChoice);
                break;
            case ANSWER:
                if(currentAnswer) {
                    currentMultiChoice.correctAnswers.add(readBuffer);
                } else {
                    currentMultiChoice.incorrectAnswers.add(readBuffer);
                }
                break;
            case BUTTON:
                currentButton.setText(readBuffer);
                currentPage.pageObjects.add(currentButton);
                break;
            default:
                break;
		}
	}
	
	/** Called by parser when characters have been read */
	public void characters(char ch[], int start, int length) throws SAXException {
		readBuffer = new String(ch, start, length).trim();
	}
	
	/** Called by parser at the start of a document */
	public void startDocument() throws SAXException { /* Do Nothing */ }
	
	/** Called by parser at the start of a document */
	public void endDocument() throws SAXException { /* Do Nothing */ }
	
	/** Called to handle the Total Marks element */
	private void handleTotalMarks() {
	    int totalMarks;
	    
	    try {
	        totalMarks = Integer.parseInt(readBuffer);
	    } catch(NumberFormatException ex) {
	        errorList.add("Could not parse total marks - non integer!");
	        currentLesson.lessonInfo.setTotalMarks(0);
	        return;
	    }
	    
	    currentLesson.lessonInfo.setTotalMarks(totalMarks);
	}
	
	/** Called to handle the default Background Color element */
	private void handleBackgroundColor() {
	    if(!checkColor(readBuffer)) {
	        errorList.add("Default Background Color is invalid Color String");
	        currentLesson.defaultSettings.setBackgroundColour("#00000000");
	        return;
	    }
	    
	    currentLesson.defaultSettings.setBackgroundColour(readBuffer);
	}
	
	/** Called to handle the default Font Size element */
	private void handleFontSize() {
	    int fontSize;
	    
	    try {
	        fontSize = Integer.parseInt(readBuffer);
	    } catch (NumberFormatException ex) {
	        errorList.add("Could not parse default Font Size - non integer!");
	        currentLesson.defaultSettings.setFontSize(11);
	        return;
	    }
	    
	    currentLesson.defaultSettings.setFontSize(fontSize);
	}
	
	/** Called to handle the default Font Color element */
	private void handleFontColor() {
	    if(!checkColor(readBuffer)) {
            errorList.add("Default Font Color is invalid Color String");
            currentLesson.defaultSettings.setFontColour("#FFFFFFFF");
            return;
        }
        
        currentLesson.defaultSettings.setFontColour(readBuffer);
	}
	
	/** Called to handle the Pass Boundary element */
	private void handlePassBoundary() {
	    int passBoundary;
	    int totalMarks = currentLesson.lessonInfo.getTotalMarks();
	    
	    try {
            passBoundary = Integer.parseInt(readBuffer);
        } catch (NumberFormatException ex) {
            errorList.add("Could not parse pass boundary - non integer!");
            currentLesson.gradeSettings.setPassBoundary(totalMarks);
            return;
        }
	    
	    if(passBoundary > totalMarks) {
	        errorList.add("Pass boundary higher than total marks! Set to full marks");
	        currentLesson.gradeSettings.setPassBoundary(totalMarks);
	        return;
	    }
	    
	    currentLesson.gradeSettings.setPassBoundary(passBoundary);
	}
	
	/** Called to handle a text element in the XML */
    private void handleTextElement(Attributes attrs) {
        /* Variables to hold the attribute strings */
        String sourcefile = attrs.getValue("sourcefile");
        String xstart = attrs.getValue("xstart");
        String ystart = attrs.getValue("ystart");
        String font = attrs.getValue("font");
        String fontsize = attrs.getValue("fontsize");
        String fontcolor = attrs.getValue("fontcolor");
        
        /* Check for null attributes */
        if(sourcefile == null) {
            sourcefile = new String("null");
        }
        
        if(xstart == null) {
            errorList.add(new String("Text; Missing X Start"));
            invalidConstruct = true;
            return;
        }
        
        if(ystart == null) {
            errorList.add(new String("Text; Missing Y Start"));
            invalidConstruct = true;
            return;
        }
        
        if(font == null) {
            String defaultFont = currentLesson.defaultSettings.getFont();
            if(defaultFont != null) {
                font = defaultFont;
            } else {
                font = "arial";
            }
        }
        
        if(fontsize == null) {
            try {
                int defaultFontSize = currentLesson.defaultSettings.getFontSize();
                fontsize = String.valueOf(defaultFontSize);
            } catch (NullPointerException e) {
                fontsize = "11";
            }
        }
        
        if(fontcolor == null) {
            String defaultFontColor = currentLesson.defaultSettings.getFontColour();
            if(defaultFontColor != null) {
                fontcolor = defaultFontColor;
            } else {
                fontcolor = "#ffffffff";
            }
        } else if(!checkColor(fontcolor)) {
            errorList.add(new String("Text; Invalid Color String"));
            invalidConstruct = true;
            return;
        }
        
        /* Create the object, checking for parsing errors */
        try {
            currentText = new TextObject(checkPos(xstart, "Text XStartPos: "),
                                         checkPos(ystart, "Text YStartPos: "),
                                         sourcefile,
                                         font,
                                         Integer.parseInt(fontsize),
                                         fontcolor);
        } catch (NullPointerException | NumberFormatException e) {
            errorList.add(new String("Text; Could not parse value"));
            invalidConstruct = true;
        }
    }
    
    /** Called to handle the end of a text element in the XML */
    private void handleTextEnd(String readBuffer) {
        /* Check the text under construction is valid */
        if(invalidConstruct) {
            invalidConstruct = false;
            return;
        }
        
        /* Check for any non-rich text */
        if(readBuffer != null && !readBuffer.equals("")) {
            currentText.textFragments.add(new RichText(readBuffer, 
                                              currentText.getFont(), 
                                              currentText.getFontSize(), 
                                              currentText.getColor()));
        }
        
        /* Add the text object to the page */
        currentPage.addObject(currentText);
    }
    
    /** Called to handle a rich text element in the XML */
    private void handleRichTextElement(Attributes attrs) {
        /* Get attributes */
        String font = attrs.getValue("font");
        String fontsize = attrs.getValue("fontsize");
        String fontcolor = attrs.getValue("fontcolor");
        
        String bold = attrs.getValue("bold");
        String italic = attrs.getValue("italic");
        String underline = attrs.getValue("underline");
        String strikethrough = attrs.getValue("strikethrough");
        String superscript = attrs.getValue("superscript");
        String subscript = attrs.getValue("subscript");
        String newline = attrs.getValue("newline");
        
        /* create an array list to hold the emphasis settings */
        ArrayList<String> settings = new ArrayList<String>();
        
        /* Check for null attributes, insert defaults as necessary */
        if(font == null) {
            String defaultFont = currentLesson.defaultSettings.getFont();
            if(defaultFont != null) {
                font = new String(defaultFont);
            } else {
                font = new String("arial");
            }
        }
        
        if(fontsize == null) {
            try {
                int defaultFontSize = currentLesson.defaultSettings.getFontSize();
                fontsize = String.valueOf(defaultFontSize);
            } catch (NullPointerException e) {
                fontsize = "11";
            }
        }
        
        if(fontcolor == null) {
            String defaultFontColor = currentLesson.defaultSettings.getFontColour();
            if(defaultFontColor != null) {
                fontcolor = new String(defaultFontColor);
            } else {
                fontcolor = new String("#ffffffff");
            }
        } else if(!checkColor(fontcolor)) {
            errorList.add(new String("RichText; Invalid Color String"));
            invalidConstruct = true;
            return;
        }
        
        
        /* Add the emphasis settings */
        if(Boolean.parseBoolean(bold) == true) {
            settings.add("bold");
        }
        
        if(Boolean.parseBoolean(italic) == true) {
            settings.add("italic");
        }
        
        if(Boolean.parseBoolean(underline) == true) {
            settings.add("underline");
        }
        
        if(Boolean.parseBoolean(strikethrough) == true) {
            settings.add("strikethrough");
        }
        
        if(Boolean.parseBoolean(superscript) == true) {
            settings.add("superscript");
        }
        
        if(Boolean.parseBoolean(subscript) == true) {
            settings.add("subscript");
        }
        
        if(Boolean.parseBoolean(newline) == true) {
            settings.add("newline");
        }
        
        /* Convert the emphasis settings to an array */
        String[] settingsArray = settings.toArray(new String[settings.size()]);
        
        /* Start building the text fragment. Check for parsing errors */
        try {
            currentTextFragment = new RichText("null",
                                               font,
                                               Integer.parseInt(fontsize), 
                                               fontcolor,
                                               settingsArray);
        } catch (NullPointerException | NumberFormatException e) {
            e.printStackTrace();
            errorList.add(new String("RichText; Could not parse value"));
            invalidConstruct = true;
        }
    }
    
    /** Called to handle the end of a rich text element in the XML */
    private void handleRichTextEnd(String readBuffer) {
        /* Check the rich text under construction is valid */
        if(invalidConstruct) {
            // Leave the flag high to invalidate the parent text object
            return;
        }
        
        /* Get the text for the text fragment */
        if(readBuffer != null) {
            currentTextFragment.setText(readBuffer);
            currentText.textFragments.add(currentTextFragment);
        }
    }

	/** Called to handle an image element in the XML */
	private void handleImageElement(Attributes attrs) {
	    /* Variables to hold the attribute strings */
	    String sourcefile = attrs.getValue("sourcefile");
	    String xstart = attrs.getValue("xstart");
	    String ystart = attrs.getValue("ystart");
	    String xscale = attrs.getValue("scale");
	    String yscale = attrs.getValue("yscale");
	    String rotation = attrs.getValue("rotation");
	    
	    /* Check for null attributes */
	    if(sourcefile == null) {
	        errorList.add(new String("Image; Missing Sourcefile String"));
	        return;
	    }
	    
	    if(xstart == null) {
            errorList.add(new String("Image; Missing X Start"));
            return;
        }
	    
	    if(ystart == null) {
            errorList.add(new String("Image; Missing Y Start"));
            return;
        }
	    
	    if(xscale == null) {
            xscale = "1.0";
        }
	    
	    if(yscale == null) {
            yscale = new String(xscale);
        }
	    
	    if(rotation == null) {
            rotation = new String("0.0");
        }
	    
	    /* Create the object, checking for parsing errors */
	    try {
	        currentPage.addObject(new ImageObject(checkPos(xstart, "Image XStart: "),
	                                              checkPos(ystart, "Image YStart: "),
	                                              sourcefile,
	                                              Float.parseFloat(xscale),
	                                              Float.parseFloat(yscale),
	                                              Float.parseFloat(rotation)));
	    } catch (NullPointerException | NumberFormatException e) {
	        errorList.add(new String("Image; Could not parse float value"));
	    }
	}
	
	/** Called to handle an audio element in the XML */
    private void handleAudioElement(Attributes attrs) {
        /* Variables to hold the attribute strings */
        String sourcefile = attrs.getValue("sourcefile");
        String xstart = attrs.getValue("xstart");
        String ystart = attrs.getValue("ystart");
        String viewprogress = attrs.getValue("viewprogress");
        
        /* Check for null attributes */
        if(sourcefile == null) {
            errorList.add(new String("Audio; Missing Sourcefile String"));
            return;
        }
        
        if(xstart == null) {
            errorList.add(new String("Audio; Missing X Start"));
            return;
        }
        
        if(ystart == null) {
            errorList.add(new String("Audio; Missing Y Start"));
            return;
        }
        
        if(viewprogress == null) {
            viewprogress = new String("true");
        }
        
        /* Create the object, checking for parsing errors */
        try {
            currentPage.addObject(new AudioObject(checkPos(xstart, "Audio XStart"),
                                                  checkPos(ystart, "Audio YStart"),
                                                  Boolean.parseBoolean(viewprogress),
                                                  sourcefile));
        } catch (NullPointerException | NumberFormatException e) {
            errorList.add(new String("Audio; Could not parse float value"));
        }
    }
    
    /** Called to handle a video element in the XML */
    private void handleVideoElement(Attributes attrs) {
        /* Variables to hold the attribute strings */
        String sourcefile = attrs.getValue("sourcefile");
        String xstart = attrs.getValue("xstart");
        String ystart = attrs.getValue("ystart");
        String videoscreenshot = attrs.getValue("videoscreenshot");
        
        /* Check for null attributes */
        if(sourcefile == null) {
            errorList.add(new String("Video; Missing Sourcefile String"));
            return;
        }
        
        if(xstart == null) {
            errorList.add(new String("Video; Missing X Start"));
            return;
        }
        
        if(ystart == null) {
            errorList.add(new String("Video; Missing Y Start"));
            return;
        }
        
        if(videoscreenshot == null) {
            videoscreenshot = new String("null");
        }
        
        /* Create the object, checking for parsing errors */
        try {
            currentPage.addObject(new VideoObject(sourcefile, 
                                                  checkPos(xstart, "Video XStart"),
                                                  checkPos(ystart, "Video YStart"),
                                                  videoscreenshot));
        } catch (NullPointerException | NumberFormatException e) {
            errorList.add(new String("Video; Could not parse float value"));
        }
    }
    
    /** Called to handle a Graphics element in the XML */
    private void handleGraphicElement(Attributes attrs) {
        /* Variables to hold the attribute strings */
        String type = attrs.getValue("type");
        String xstart = attrs.getValue("xstart");
        String ystart = attrs.getValue("ystart");
        String xend = attrs.getValue("xend");
        String yend = attrs.getValue("yend");
        String solid = attrs.getValue("solid");
        String graphiccolor = attrs.getValue("graphiccolor");
        String rotation = attrs.getValue("rotation");
        String outlinethickness = attrs.getValue("outlinethickness");
        String shadow = attrs.getValue("shadow");
        
        GraphicType gType;
        
        /* Check for null attributes */
        if(type == null) {
            errorList.add(new String("Graphic; Missing Type String"));
            invalidConstruct = true;
            return;
        } else {
            gType = GraphicType.check(type.toUpperCase());
        }
        
        if(xstart == null) {
            errorList.add(new String("Graphic; Missing X Start"));
            invalidConstruct = true;
            return;
        }
        
        if(ystart == null) {
            errorList.add(new String("Graphic; Missing Y Start"));
            invalidConstruct = true;
            return;
        }
        
        if(xend == null) {
            errorList.add(new String("Graphic; Missing X End"));
            invalidConstruct = true;
            return;
        }
        
        if(yend == null) {
            errorList.add(new String("Graphic; Missing Y End"));
            invalidConstruct = true;
            return;
        }
        
        if(solid == null) {
            errorList.add(new String("Graphic; Missing Solid"));
            invalidConstruct = true;
            return;
        }
        
        if(graphiccolor == null) {
            errorList.add(new String("Graphic; Missing Graphic Color"));
            return;
        } else if(!checkColor(graphiccolor)) {
            errorList.add(new String("Graphic; Invalid Color String"));
            invalidConstruct = true;
            return;
        }
        
        if(rotation == null) {
            rotation = new String("0.0");
        }
        
        if(outlinethickness == null) {
            outlinethickness = new String("1.0");
        }
        
        if(shadow == null) {
            shadow = new String("false");
        }
        
        /* Create the object, checking for parsing errors */
        try {
            currentGraphic = new GraphicObject(gType, 
                                               checkPos(xstart, "Graphic XStart: "),
                                               checkPos(ystart, "Graphic YStart: "),
                                               checkPos(xend, "Graphic XEnd: "),
                                               checkPos(yend, "Graphic YEnd: "),
                                               Float.parseFloat(rotation),
                                               graphiccolor,
                                               Boolean.parseBoolean(solid),
                                               Float.parseFloat(outlinethickness),
                                               Boolean.parseBoolean(shadow));
        } catch (NullPointerException | NumberFormatException e) {
            errorList.add(new String("Video; Could not parse float value"));
            invalidConstruct = true;
        }
    }
    
    /** Called to handle a Cyclic Shading element in the XML */
    private void handleCyclicShadingElement(Attributes attrs) {
        /* Variables to hold the attribute strings */
        String shadingcolor = attrs.getValue("shadingcolor");
        
        if(shadingcolor == null) {
            errorList.add(new String("CyclicShading: Missing shading color"));
            return;
        }
        
        currentGraphic.setShading(GraphicObject.Shading.CYCLIC);
        currentGraphic.setShadingColor(shadingcolor);
    }
    
    /** Called to handle an answer box element in the XML */
    private void handleAnswerBoxElement(Attributes attrs) {
        /* Variables to hold the attribute strings */
        String xstart = attrs.getValue("xstart");
        String ystart = attrs.getValue("ystart");
        String characterlimit = attrs.getValue("characterlimit");
        String correctanswer = attrs.getValue("correctanswer");
        String marks = attrs.getValue("marks");
        String retry = attrs.getValue("retry");
        
        /* Check for null attributes */        
        if(xstart == null) {
            errorList.add(new String("Answer Box; Missing X Start"));
            return;
        }
        
        if(ystart == null) {
            errorList.add(new String("Answer Box; Missing Y Start"));
            return;
        }
        
        if(characterlimit == null) {
            errorList.add(new String("Answer Box; Missing Character Limit"));
            return;
        }
        
        if(correctanswer == null) {
            errorList.add(new String("Answer Box; Missing Correct Answer(s)"));
            return;
        }
        
        if(marks == null) {
            errorList.add(new String("Answer Box; Missing Marks"));
            return;
        }
        
        if(retry == null) {
            retry = new String("True");
        }
        
        /* Create the object, checking for parsing errors */
        try {
            currentPage.addObject(new AnswerBoxObject(checkPos(xstart, "Answer Box XStart: "),
                                                      checkPos(ystart, "Answer Box YStart: "),
                                                      Integer.parseInt(characterlimit),
                                                      Integer.parseInt(marks),
                                                      correctanswer,
                                                      Boolean.parseBoolean(retry)));
        } catch (NullPointerException | NumberFormatException e) {
            errorList.add(new String("Answer Box; Could not parse a value"));
        }
    }
    
    /** Called to handle an answer box element in the XML */
    private void handleMultipleChoiceElement(Attributes attrs) {
        /* Variables to hold the attribute strings */
        String xstart = attrs.getValue("xstart");
        String ystart = attrs.getValue("ystart");
        String type = attrs.getValue("type");
        String orientation = attrs.getValue("orientation");
        String marks = attrs.getValue("marks");
        String retry = attrs.getValue("retry");
        
        Orientation mcOrientation;
        MultiChoiceType mcType;
        
        /* Check for null attributes */        
        if(xstart == null) {
            errorList.add(new String("Multi Choice; Missing X Start"));
            return;
        }
        
        if(ystart == null) {
            errorList.add(new String("Multi Choice; Missing Y Start"));
            return;
        }
        
        if(type == null) {
            errorList.add(new String("Multi Choice; Missing Type"));
            return;
        } else {
            mcType = MultiChoiceType.check(type.toUpperCase());
        }
        
        if(orientation == null) {
            errorList.add(new String("Multi Choice; Orientation"));
            return;
        } else {
            mcOrientation = Orientation.check(orientation.toUpperCase());
        }
        
        if(marks == null) {
            errorList.add(new String("Multi Choice; Missing Marks"));
            return;
        }
        
        if(retry == null) {
            retry = new String("True");
        }
        
        /* Create the object, checking for parsing errors */
        try {
            currentMultiChoice = new MultipleChoiceObject(checkPos(xstart, "Multi Choice XStart: "),
                                                          checkPos(ystart, "Multi Choice YStart: "),
                                                          mcOrientation,
                                                          mcType,
                                                          Integer.parseInt(marks),
                                                          Boolean.parseBoolean(retry));
        } catch (NullPointerException | NumberFormatException e) {
            errorList.add(new String("Answer Box; Could not parse a value"));
        }
    }
    
    /** Called to handle a button element in the XML */
    private void handleButtonElement(Attributes attrs) {
        /* Variables to hold the attribute strings */
        String xstart = attrs.getValue("xstart");
        String ystart = attrs.getValue("ystart");
        String xend = attrs.getValue("xend");
        String yend = attrs.getValue("yend");
        String function = attrs.getValue("function");
        String visible = attrs.getValue("visible");
        
        /* Check for null attributes */        
        if(xstart == null) {
            errorList.add(new String("Button; Missing X Start"));
            return;
        }
        
        if(ystart == null) {
            errorList.add(new String("Button; Missing Y Start"));
            return;
        }
        
        if(xend == null) {
            errorList.add(new String("Button; Missing X End"));
            return;
        }
        
        if(yend == null) {
            errorList.add(new String("Button; Missing Y End"));
            return;
        }
        
        if(function == null) {
            errorList.add(new String("Button; Missing Function"));
            return;
        }
        
        if(visible == null) {
            visible = new String("true");
        }
        
        /* Create the object, checking for parsing errors */
        try {
            currentButton = new ButtonObject(checkPos(xstart, "Multi Choice XStart: "),
                                             checkPos(ystart, "Multi Choice YStart: "),
                                             checkPos(xend, "Multi Choice XEnd: "),
                                             checkPos(yend, "Multi Choice YEnd: "),
                                             Boolean.parseBoolean(visible),
                                             "text",
                                             Integer.parseInt(function));
        } catch (NullPointerException | NumberFormatException e) {
            errorList.add(new String("Answer Box; Could not parse a value"));
        }
    }
	
	/** Called to handle a slide element in the XML */
	private void handleSlideElement(Attributes attrs) {	    
	    String bgcolor = attrs.getValue("backgroundcolor");
	    String defaultBGColor = currentLesson.defaultSettings.getBackgroundColour();
	    String number = attrs.getValue("number");
	    int pNumber;
	    
	    try {
	         pNumber = Integer.parseInt(number);
	    } catch (NullPointerException | NumberFormatException e) {
	        errorList.add("Page number could not be parsed, set to -1");
	        pNumber = -1;
	    }
	    
	    if(bgcolor == null) {
	        bgcolor = defaultBGColor;
	    } else if(!checkColor(bgcolor)) {
	        errorList.add("Page background color not a valid color string");
	        bgcolor = defaultBGColor;
	    }
	    
	    currentPage = new Page(pNumber, bgcolor);
	}
	
	/** 
	 * Helper function to check that a color is given as a 8 digit hex string.
	 * Returns true if string is valid.
	 */
	private boolean checkColor(String hexCol) {	    
	    /** Check that the first character is a hash to indicate a hex value */
	    if(!hexCol.startsWith("#")) {
	        return false;
	    }
	    
	    /** Check that the string has 9 characters */
	    if(hexCol.length() != 9) {
	        return false;
	    }
	    
	    /** Check that the last 8 characters are numbers */
	    for(int i = 1; i < 9; i++) {
	        String s = String.valueOf(hexCol.charAt(i));
	        
	        if(!s.matches("[a-zA-Z0-9]")) {
	            return false;
	        }
	    }
	    
	    /** If all checks pass return true */
	    return true;
	}
	
	/**
	 * Helper function to check that a relative position value
	 * is in the range 0 <= x <= 1. Returns the position value
	 * if it was in range and could be parsed, returns 0.0f
	 * otherwise.
	 */
	private float checkPos(String spos, String errorStart) {
	    float pos;
	    
	    try {
	        pos = Float.parseFloat(spos);
	    } catch(NumberFormatException ex) {
	        errorList.add(errorStart + "Could not parse value, 0.0 inserted");
	        return 0.0f;
	    }
	    
	    if(pos >= 0.0f && pos <= 1.0f) {
	        return pos;
	    }
	    
	    errorList.add(errorStart + "Value out of range, 0.0 inserted");
	    return 0.0f;
	}
}