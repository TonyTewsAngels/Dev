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
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
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
		
	}
	
	/** Write an XML file based on a lesson data object */
	public void writeXML(Lesson lesson) {
	    try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            doc.setXmlStandalone(false);
            
            Element lessonElement = doc.createElement("slideshow");
            doc.appendChild(lessonElement);
            
            addLessonInfo(lesson.lessonInfo, lessonElement, doc);
            addDefaultSettings(lesson.defaultSettings, lessonElement, doc);
            addGradeSettings(lesson.gradeSettings, lessonElement, doc);
            
            for(int i = 0; i < lesson.pages.size(); i++) {
                addPage(lesson.pages.get(i), lessonElement, doc);
            }
            
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("testOutput.xml"));
     
            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);
     
            transformer.transform(source, result);
            
        } catch (ParserConfigurationException | TransformerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    
	}
	
	/** Adds the lesson info element */
	public void addLessonInfo(LessonInfo lessonInfo, Element lessonElement, Document doc) {
	    Element lessonInfoElement = doc.createElement("documentinfo");
	    lessonElement.appendChild(lessonInfoElement);
	    
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
	
	/** Adds the default settings element */
	public void addDefaultSettings(LessonDefaultSettings defaultSettings, Element lessonElement, Document doc) {
        Element defaultSettingsElement = doc.createElement("defaultsettings");
        lessonElement.appendChild(defaultSettingsElement);
        
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
	
	/** Adds the grade settings element */
	public void addGradeSettings(LessonGradeSettings gradeSettings, Element lessonElement, Document doc) {
        Element gradeSettingsElement = doc.createElement("gradesettings");
        lessonElement.appendChild(gradeSettingsElement);
        
        Element passBoundary = doc.createElement("passboundary");
        passBoundary.setAttribute("passmessage", gradeSettings.getPassMessage());
        passBoundary.setAttribute("failmessage", gradeSettings.getFailMessage());
        passBoundary.appendChild(doc.createTextNode(String.valueOf(gradeSettings.getPassBoundary())));
        gradeSettingsElement.appendChild(passBoundary);
    }
	
	/** Add a page */
	public void addPage(Page page, Element lessonElement, Document doc) {
	    Element pageElement = doc.createElement("slide"); 
	    lessonElement.appendChild(pageElement);
	    
	    pageElement.setAttribute("number", String.valueOf(page.getNumber()));
	    pageElement.setAttribute("backgroundcolor", page.getPageColour());
	    
	    for(int i = 0; i < page.pageObjects.size(); i++) {
	        PageObject pageObject = page.pageObjects.get(i);
	        
	        switch(pageObject.getType()) {
	            case TEXT:
	                
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
                    break;
	            default:
	                break;
	        }
	    }
	}
	
	/** Add an image */
	public void addImage(ImageObject image, Element pageElement, Document doc) {
	    Element imageElement = doc.createElement("image");
	    imageElement.setAttribute("sourcefile", image.getSourcefile());
	    imageElement.setAttribute("xstart", String.valueOf(image.getXStart()));
	    imageElement.setAttribute("ystart", String.valueOf(image.getYStart()));
	    imageElement.setAttribute("scale", String.valueOf(image.getxScaleFactor()));
	    imageElement.setAttribute("yscale", String.valueOf(image.getyScaleFactor()));
	    imageElement.setAttribute("rotation", String.valueOf(image.getRotation()));
	    pageElement.appendChild(imageElement);
	}
	
	/** Add an audio clip */
	public void addAudio(AudioObject audio, Element pageElement, Document doc) {
	    Element audioElement = doc.createElement("audio");
	    audioElement.setAttribute("sourcefile", audio.getSourcefile());
	    audioElement.setAttribute("xstart", String.valueOf(audio.getXStart()));
	    audioElement.setAttribute("ystart", String.valueOf(audio.getYStart()));
        audioElement.setAttribute("viewprogress", String.valueOf(audio.isViewProgress()));
        pageElement.appendChild(audioElement);
	}
	
	/** Add a graphics element */
	public void addGraphic(GraphicObject graphic, Element pageElement, Document doc) {
	    Element graphicElement = doc.createElement("graphic");
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
        
        if(graphic.getShading() == Shading.CYCLIC) {
            Element shadingElement = doc.createElement("cyclicshading");
            shadingElement.setAttribute("shadingcolor", graphic.getShadingColor());
            graphicElement.appendChild(shadingElement);
        }
        
        pageElement.appendChild(graphicElement);
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
}
