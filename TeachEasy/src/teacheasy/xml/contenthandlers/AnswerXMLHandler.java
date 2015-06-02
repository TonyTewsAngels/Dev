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

import teacheasy.data.AnswerBoxObject;
import teacheasy.data.Lesson;
import teacheasy.data.MultipleChoiceObject;
import teacheasy.data.Page;
import teacheasy.data.multichoice.Answer;
import teacheasy.xml.XMLElement;
import teacheasy.xml.util.*;
import teacheasy.xml.util.XMLNotification.*;

/**
 * The XML content handler for an answer element.
 * 
 * @author  Alistair Jewers
 * @version 1.0 Apr 12 2015 
 */
public class AnswerXMLHandler extends DefaultHandler{
    /** The answer object to construct */
    private Answer answer;
    
    /** The list of warnings and errors */
    private ArrayList<XMLNotification> errorList;
    
    /** The xml reader reference */
    private XMLReader xmlReader;
    
    /** The handler that called this one */
    private DefaultHandler parent;
    
    /* The data objects under construction */
    private Lesson lesson;
    private Page page;
    private MultipleChoiceObject multipleChoice;
    private AnswerBoxObject answerBox;
    
    /** 
     * Boolean to indicate if this answer belongs to an answer box
     * or a multiple choice object.
     */
    private boolean isAnswerBox;
    
    /** A string to hold the characters read in. */
    String readBuffer;
    
    /**
     * Constructor for a multiple choice answer.
     * 
     * @param nXMLReader The XML reader reference.
     * @param nParent The handler that called this one.
     * @param nLesson The lesson being constructed.
     * @param nPage The page being constructed.
     * @param nMultipleChoice The multiple choice object being constructed.
     * @param nErrorList The list of errors and warnings.
     * @param answerAttrs The attributes of this XML element.
     */
    public AnswerXMLHandler(XMLReader nXMLReader, DefaultHandler nParent, Lesson nLesson, Page nPage, MultipleChoiceObject nMultipleChoice, ArrayList<XMLNotification> nErrorList, Attributes answerAttrs) {
        /* Set references */
        this.xmlReader = nXMLReader;
        this.parent = nParent;
        this.errorList = nErrorList;
        this.lesson = nLesson;
        this.page = nPage;
        this.multipleChoice = nMultipleChoice;
        
        /* Set to multiple choice setting */
        isAnswerBox = false;
        
        /* Start constructing the answer object */
        answerStart(answerAttrs);
    }
    
    /**
     * Constructor for an answer box answer.
     * 
     * @param nXMLReader The XML reader reference.
     * @param nParent The handler that called this one.
     * @param nLesson The lesson being constructed.
     * @param nPage The page being constructed.
     * @param nAnswerBox The answer box object being constructed.
     * @param nErrorList The list of errors and warnings.
     * @param answerAttrs The attributes of this XML element.
     */
    public AnswerXMLHandler(XMLReader nXMLReader, DefaultHandler nParent, Lesson nLesson, Page nPage, AnswerBoxObject nAnswerBox, ArrayList<XMLNotification> nErrorList, Attributes answerAttrs) {
        /* Set references */
        this.xmlReader = nXMLReader;
        this.parent = nParent;
        this.errorList = nErrorList;
        this.lesson = nLesson;
        this.page = nPage;
        this.answerBox = nAnswerBox;
        
        /* Set to answer box setting */
        isAnswerBox = true;
        
        /* Start constructing the answer box object */
        answerStart(answerAttrs);
    }
    
    /**
     * Called when the end of an XML element is reached.
     */
    public void endElement(String uri, String localName, String qName) {
        switch(XMLElement.check(qName.toUpperCase())) {
            /* If the answer element has finished, return to the parent */
            case ANSWER:
                endHandler();
                break;            
            default:
                break;
        }
    }
    
    /**
     * Called when the handler is finished to transfer control back
     * to the parent.
     */
    public void endHandler() {
        /* Check if answer text was found */
        if(readBuffer == null) {
            /* No text, add an error */
            errorList.add(new XMLNotification(Level.ERROR,
                    "Page " + lesson.pages.size() +
                    ", Object " + page.getObjectCount() +
                    " (Answer) Answer " + multipleChoice.getAnswers().size() +
                    " missing text."));
        } else {
            /* Set the text */
            answer.setText(readBuffer);
            
            /* Add the answer to the appropriate object */
            if(isAnswerBox && answerBox != null) {
                answerBox.addAnswer(answer);
            } else if(!isAnswerBox && multipleChoice != null) {
                multipleChoice.addAnswer(answer); 
            }
        }
        
        /* Move control back up */
        xmlReader.setContentHandler(parent);
    }
    
    /**
     * Called when text is found.
     */
    public void characters(char[] ch, int start, int length) {
        /* Stash the text in the read buffer */
        String str = new String(ch, start, length);
        str = str.trim();
        if(!str.isEmpty()) {
            readBuffer = str;
        }
    }
    
    /**
     * Called to start constructing the answer object.
     * @param attrs
     */
    public void answerStart(Attributes attrs) {
        /* Check the type of object being constructed */
        if(isAnswerBox && answerBox != null) {
            /* Attempt to parse the correct setting */
            boolean correct = XMLUtil.checkBool(attrs.getValue("correct"), false, Level.ERROR, errorList, 
                    "Page " + lesson.pages.size() +
                    ", Object " + page.getObjectCount() +
                    " (Answer) Answer " + answerBox.getAnswers().size());
            
            /* Create the answer object */
            answer = new Answer(null, correct);
           
        } else if(!isAnswerBox && multipleChoice != null) {
             /* Attempt to parse the correct setting */
             boolean correct = XMLUtil.checkBool(attrs.getValue("correct"), false, Level.ERROR, errorList, 
                    "Page " + lesson.pages.size() +
                    ", Object " + page.getObjectCount() +
                    " (Answer) Answer " + multipleChoice.getAnswers().size());
             
             /* Create the answer object */
             answer = new Answer(null, correct);
        }
    } 
}


