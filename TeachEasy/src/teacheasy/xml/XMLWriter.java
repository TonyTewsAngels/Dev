/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import teacheasy.data.*;
import teacheasy.data.GraphicObject.Shading;
import teacheasy.data.lessondata.*;

/**
 * This class handles XML writing, 
 * based on data stored in Lesson objects.
 * 
 * @version 	1.0 05 Feb 2015
 * @author 		Alistair Jewers
 */
public class XMLWriter {
	
	/** Constructor Method */
	public XMLWriter() {
		/* No setup required */
	}
	
	/** Write an XML file based on a lesson data object */
	public void writeXML(Lesson lesson) {
	    try {
	        /* Create the document */
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            doc.setXmlStandalone(false);
            
            /* Create the root lesson element */
            Element lessonElement = doc.createElement("slideshow");
            doc.appendChild(lessonElement);
            
            /* Add the standard metadata elements to the lesson element */
            addLessonInfo(lesson.lessonInfo, lessonElement, doc);
            addDefaultSettings(lesson.defaultSettings, lessonElement, doc);
            addGradeSettings(lesson.gradeSettings, lessonElement, doc);
            
            /* Add each page to the lesson element in turn */
            for(int i = 0; i < lesson.pages.size(); i++) {
                addPage(lesson.pages.get(i), lessonElement, doc);
            }            
            
            /* Create a transformer to output the file */
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            /* Set extra transformer settings for indentation */
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            
            /* Setup the location for the file */
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("testOutput.xml"));
     
            /* Output the xml file */
            transformer.transform(source, result);
            
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
	    
	}
	
	/** Add the lesson info element to the xml file */
	public void addLessonInfo(LessonInfo lessonInfo, Element lessonElement, Document doc) {
	    /* Create the lesson info element and add it to the lesson element */
	    Element lessonInfoElement = doc.createElement("documentinfo");
	    lessonElement.appendChild(lessonInfoElement);
	    
	    /* Create and add the child elements to the lesson info element and set their text */
	    Element author = doc.createElement("author");
	    author.appendChild(doc.createTextNode(lessonInfo.getAuthor()));
	    lessonInfoElement.appendChild(author);
	    
	    Element version = doc.createElement("version");
	    version.appendChild(doc.createTextNode(lessonInfo.getVersion()));
        lessonInfoElement.appendChild(version);
        
        Element comment = doc.createElement("comment");
        comment.appendChild(doc.createTextNode(lessonInfo.getComment()));
        lessonInfoElement.appendChild(comment);
        
        Element groupid = doc.createElement("groupid");
        groupid.appendChild(doc.createTextNode("4"));
        lessonInfoElement.appendChild(groupid);
        
        Element dateCreated = doc.createElement("datecreated");
        dateCreated.appendChild(doc.createTextNode(lessonInfo.getDateCreated()));
        lessonInfoElement.appendChild(dateCreated);
        
        Element totalMarks = doc.createElement("totalmarks");
        totalMarks.appendChild(doc.createTextNode(String.valueOf(lessonInfo.getTotalMarks())));
        lessonInfoElement.appendChild(totalMarks);
        
        Element lessonName = doc.createElement("lessonname");
        lessonName.appendChild(doc.createTextNode(lessonInfo.getLessonName()));
        lessonInfoElement.appendChild(lessonName);
	}
	
	/** Add the default settings element to the xml file */
	public void addDefaultSettings(LessonDefaultSettings defaultSettings, Element lessonElement, Document doc) {
	    /* Create the default settings element and add it to the lesson element*/
	    Element defaultSettingsElement = doc.createElement("defaultsettings");
        lessonElement.appendChild(defaultSettingsElement);
        
        /* Create and add the child elements and their text */
        Element backgroundColor = doc.createElement("backgroundcolor");
        backgroundColor.appendChild(doc.createTextNode(defaultSettings.getBackgroundColour()));
        defaultSettingsElement.appendChild(backgroundColor);
        
        Element font = doc.createElement("font");
        font.appendChild(doc.createTextNode(defaultSettings.getFont()));
        defaultSettingsElement.appendChild(font);
        
        Element fontSize = doc.createElement("fontsize");
        fontSize.appendChild(doc.createTextNode(String.valueOf(defaultSettings.getFontSize())));
        defaultSettingsElement.appendChild(fontSize);
        
        Element fontColor = doc.createElement("fontcolor");
        fontColor.appendChild(doc.createTextNode(defaultSettings.getFontColour()));
        defaultSettingsElement.appendChild(fontColor);
    }
	
	/** Add the grade settings element */
	public void addGradeSettings(LessonGradeSettings gradeSettings, Element lessonElement, Document doc) {
	    /* Create the grade settings element and add it to the lesson element*/
	    Element gradeSettingsElement = doc.createElement("gradesettings");
        lessonElement.appendChild(gradeSettingsElement);
        
        /* Create and add the pass boundary element with its attributes and text */
        Element passBoundary = doc.createElement("passboundary");
        passBoundary.setAttribute("passmessage", gradeSettings.getPassMessage());
        passBoundary.setAttribute("failmessage", gradeSettings.getFailMessage());
        passBoundary.appendChild(doc.createTextNode(String.valueOf(gradeSettings.getPassBoundary())));
        gradeSettingsElement.appendChild(passBoundary);
    }
	
	/** Add a page element to the xml file*/
	public void addPage(Page page, Element lessonElement, Document doc) {
	    /* Create the page element and add it to the lesson element */
	    Element pageElement = doc.createElement("slide"); 
	    lessonElement.appendChild(pageElement);
	    
	    /* Set the attributes for the page */
	    pageElement.setAttribute("number", String.valueOf(page.getNumber()));
	    pageElement.setAttribute("backgroundcolor", page.getPageColour());
	    
	    /* Add each of the page object elements to the page element */
	    for(int i = 0; i < page.pageObjects.size(); i++) {
	        /* Get the general page object */
	        PageObject pageObject = page.pageObjects.get(i);
	        
	        /* Determine the type, cast and add */
	        switch(pageObject.getType()) {
	            case TEXT:
	                TextObject textObject = (TextObject) pageObject;
	                addText(textObject, pageElement, doc);
	                break;
	            case IMAGE:
	                ImageObject imageObject = (ImageObject) pageObject;
	                addImage(imageObject, pageElement, doc);
	                break;
	            case GRAPHIC:
	                GraphicObject graphicObject = (GraphicObject) pageObject;
	                addGraphic(graphicObject, pageElement, doc);
	                break;
                case AUDIO:
                    AudioObject audioObject = (AudioObject) pageObject;
                    addAudio(audioObject, pageElement, doc);
                    break;
                case VIDEO:
                    VideoObject videoObject = (VideoObject) pageObject;
                    addVideo(videoObject, pageElement, doc);
                    break;
                case ANSWER_BOX:
                    AnswerBoxObject answerBoxObject = (AnswerBoxObject) pageObject;
                    addAnswerBox(answerBoxObject, pageElement, doc);
                    break;
                case MULTIPLE_CHOICE:
                    MultipleChoiceObject multipleChoiceObject = (MultipleChoiceObject) pageObject;
                    addMultipleChoice(multipleChoiceObject, pageElement, doc);
                    break;
                case BUTTON:
                    ButtonObject buttonObject = (ButtonObject) pageObject;
                    addButton(buttonObject, pageElement, doc);
                    break;
	            default:
	                break;
	        }
	    }
	}
	
	/** Add a text box element */
	public void addText(TextObject text, Element pageElement, Document doc) {
	    /* Create the text box element and add it to the page element */
	    Element textElement = doc.createElement("text");
	    pageElement.appendChild(textElement);
	    
	    /* Set the attributes based on the data in memory */
	    textElement.setAttribute("sourcefile", text.getSourceFile());
	    textElement.setAttribute("xstart", String.valueOf(text.getXStart()));
	    textElement.setAttribute("ystart", String.valueOf(text.getYStart()));
	    textElement.setAttribute("font", text.getFont());
	    textElement.setAttribute("fontsize", String.valueOf(text.getFontSize()));
	    textElement.setAttribute("fontcolor", text.getColor());
	    
	    /* Add the rich text elements as children */
	    for(int i = 0; i < text.textFragments.size(); i++) {
	        /* Get the data for the rich text object */
            RichText richText = text.textFragments.get(i);
            
            /* Create the rich text element and add it to the text element */
            Element richTextElement = doc.createElement("richtext");
            textElement.appendChild(richTextElement);
            
            /* Set the attributes for the rich text element */
            richTextElement.setAttribute("font", richText.getFont());
            richTextElement.setAttribute("fontsize", String.valueOf(richText.getFontSize()));
            richTextElement.setAttribute("fontcolor", richText.getColor());
            
            /* Add the emphasis attributes as necessary */
            if(richText.isBold()) {
                richTextElement.setAttribute("bold", "true"); 
            }
            
            if(richText.isItalic()) {
                richTextElement.setAttribute("italic", "true"); 
            }
            
            if(richText.isUnderline()) {
                richTextElement.setAttribute("underline", "true"); 
            }
            
            if(richText.isStrikethrough()) {
                richTextElement.setAttribute("strikethrough", "true"); 
            }
            
            if(richText.isSuperscript()) {
                richTextElement.setAttribute("superscript", "true"); 
            }
            
            if(richText.isSubscript()) {
                richTextElement.setAttribute("subscript", "true"); 
            }
            
            if(richText.isNewLine()) {
                richTextElement.setAttribute("newline", "true"); 
            }
            
            /* Add the text string */
            richTextElement.appendChild(doc.createTextNode(richText.getText()));
	    }
	}
	
	/** Add an image element */
	public void addImage(ImageObject image, Element pageElement, Document doc) {
	    /* Create the image element and add it to the page element */
	    Element imageElement = doc.createElement("image");
	    pageElement.appendChild(imageElement);
	    
	    /* Set the attributes */
	    imageElement.setAttribute("sourcefile", image.getSourcefile());
	    imageElement.setAttribute("xstart", String.valueOf(image.getXStart()));
	    imageElement.setAttribute("ystart", String.valueOf(image.getYStart()));
	    imageElement.setAttribute("scale", String.valueOf(image.getxScaleFactor()));
	    imageElement.setAttribute("yscale", String.valueOf(image.getyScaleFactor()));
	    imageElement.setAttribute("rotation", String.valueOf(image.getRotation()));
	}
	
	/** Add an audio clip */
	public void addAudio(AudioObject audio, Element pageElement, Document doc) {
	    /* Create the audio element and add it to the page element */
	    Element audioElement = doc.createElement("audio");
        pageElement.appendChild(audioElement);

        /* Set the attributes */
	    audioElement.setAttribute("sourcefile", audio.getSourcefile());
	    audioElement.setAttribute("xstart", String.valueOf(audio.getXStart()));
	    audioElement.setAttribute("ystart", String.valueOf(audio.getYStart()));
        audioElement.setAttribute("viewprogress", String.valueOf(audio.isViewProgress()));
	}
	
	/** Add a graphics element */
	public void addGraphic(GraphicObject graphic, Element pageElement, Document doc) {
	    /* Create the graphic element and add it to the page element */
	    Element graphicElement = doc.createElement("graphic");
        pageElement.appendChild(graphicElement);
        
        /* Set the attributes */
	    graphicElement.setAttribute("type", graphic.getGraphicType().toString().toLowerCase());
	    graphicElement.setAttribute("xstart", String.valueOf(graphic.getXStart()));
	    graphicElement.setAttribute("ystart", String.valueOf(graphic.getYStart()));
	    graphicElement.setAttribute("xend", String.valueOf(graphic.getXEnd()));
        graphicElement.setAttribute("yend", String.valueOf(graphic.getYEnd()));
        graphicElement.setAttribute("sold", String.valueOf(graphic.isSolid()));
        graphicElement.setAttribute("graphiccolor", graphic.getGraphicColour());
        graphicElement.setAttribute("rotation", String.valueOf(graphic.getRotation()));
        graphicElement.setAttribute("outlinethickness", String.valueOf(graphic.getOutlineThickness()));
        graphicElement.setAttribute("shadow", String.valueOf(graphic.isShadow()));
        
        /* add shading element if necessary */
        if(graphic.getShading() == Shading.CYCLIC) {
            Element shadingElement = doc.createElement("cyclicshading");
            graphicElement.appendChild(shadingElement);
            
            shadingElement.setAttribute("shadingcolor", graphic.getShadingColor());
        }
	}
	
	/** Add a video */
	public void addVideo(VideoObject video, Element pageElement, Document doc) {
	    Element videoElement = doc.createElement("video");
	    videoElement.setAttribute("sourcefile", video.getSourcefile());
	    videoElement.setAttribute("xstart", String.valueOf(video.getXStart()));
        videoElement.setAttribute("ystart", String.valueOf(video.getYStart()));
        videoElement.setAttribute("videoscreenshot", video.getScreenshotFile());
        pageElement.appendChild(videoElement);
	}
	
	/** Add an answer box */
	public void addAnswerBox(AnswerBoxObject answerBox, Element pageElement, Document doc) {
	    Element answerBoxElement = doc.createElement("answerbox");
	    answerBoxElement.setAttribute("xstart", String.valueOf(answerBox.getXStart()));
        answerBoxElement.setAttribute("ystart", String.valueOf(answerBox.getXStart()));
        answerBoxElement.setAttribute("characterlimit", String.valueOf(answerBox.getCharacterLimit()));
        answerBoxElement.setAttribute("correctanswer", answerBox.getCorrectAnswers());
        answerBoxElement.setAttribute("marks", String.valueOf(answerBox.getMarks()));
        answerBoxElement.setAttribute("retry", String.valueOf(answerBox.isRetry()));
        pageElement.appendChild(answerBoxElement);
	}
	
	/** Add a multiple choice question */
	public void addMultipleChoice(MultipleChoiceObject multipleChoice, Element pageElement, Document doc) {
	    Element multipleChoiceElement = doc.createElement("multiplechoice");
	    multipleChoiceElement.setAttribute("xstart", String.valueOf(multipleChoice.getXStart()));
	    multipleChoiceElement.setAttribute("ystart", String.valueOf(multipleChoice.getYStart()));
	    multipleChoiceElement.setAttribute("type", multipleChoice.getMultiChoiceType().toString().toLowerCase());
	    multipleChoiceElement.setAttribute("orientation", multipleChoice.getOrientation().toString().toLowerCase());
	    multipleChoiceElement.setAttribute("marks", String.valueOf(multipleChoice.getMarks()));
	    multipleChoiceElement.setAttribute("retry", String.valueOf(multipleChoice.isRetry()));
	    
	    for(int i = 0; i < multipleChoice.correctAnswers.size(); i++) {
	        Element answerElement = doc.createElement("answer");
	        answerElement.setAttribute("correct", "true");
	        answerElement.appendChild(doc.createTextNode(multipleChoice.correctAnswers.get(i)));
	        multipleChoiceElement.appendChild(answerElement);
	    }
	    
	    for(int i = 0; i < multipleChoice.incorrectAnswers.size(); i++) {
            Element answerElement = doc.createElement("answer");
            answerElement.setAttribute("correct", "false");
            answerElement.appendChild(doc.createTextNode(multipleChoice.incorrectAnswers.get(i)));
            multipleChoiceElement.appendChild(answerElement);
        }
	    
	    pageElement.appendChild(multipleChoiceElement);
	}
	
	/** Add a button */
	public void addButton(ButtonObject button, Element pageElement, Document doc) {
	    Element buttonElement = doc.createElement("button");
	    buttonElement.setAttribute("xstart", String.valueOf(button.getXStart()));
	    buttonElement.setAttribute("ystart", String.valueOf(button.getYStart()));
	    buttonElement.setAttribute("xend", String.valueOf(button.getxEnd()));
        buttonElement.setAttribute("yend", String.valueOf(button.getxEnd()));
        buttonElement.setAttribute("function", String.valueOf(button.getFunction()));
        buttonElement.setAttribute("visible", String.valueOf(button.isVisible()));
        
        buttonElement.appendChild(doc.createTextNode(button.getText()));
        
        pageElement.appendChild(buttonElement);
	}
}
