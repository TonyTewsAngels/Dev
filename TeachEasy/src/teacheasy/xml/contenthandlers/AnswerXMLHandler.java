package teacheasy.xml.contenthandlers;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import teacheasy.data.AnswerBoxObject;
import teacheasy.data.Lesson;
import teacheasy.data.MultipleChoiceObject;
import teacheasy.data.Page;
import teacheasy.data.RichText;
import teacheasy.data.TextObject;
import teacheasy.data.multichoice.Answer;
import teacheasy.xml.XMLElement;
import teacheasy.xml.util.*;
import teacheasy.xml.util.XMLNotification.*;

public class AnswerXMLHandler extends DefaultHandler{
    private Answer answer;
    
    private ArrayList<XMLNotification> errorList;
    private XMLReader xmlReader;
    private DefaultHandler parent;
    private Lesson lesson;
    private Page page;
    private MultipleChoiceObject multipleChoice;
    private AnswerBoxObject answerBox;
    private boolean isAnswerBox;
    
    String readBuffer;
    
    public AnswerXMLHandler(XMLReader nXMLReader, DefaultHandler nParent, Lesson nLesson, Page nPage, MultipleChoiceObject nMultipleChoice, ArrayList<XMLNotification> nErrorList, Attributes answerAttrs) {
        this.xmlReader = nXMLReader;
        this.parent = nParent;

        this.errorList = nErrorList;
        
        this.lesson = nLesson;
        this.page = nPage;
        this.multipleChoice = nMultipleChoice;
        
        isAnswerBox = false;
        
        answerStart(answerAttrs);
    }
    
    public AnswerXMLHandler(XMLReader nXMLReader, DefaultHandler nParent, Lesson nLesson, Page nPage, AnswerBoxObject nAnswerBox, ArrayList<XMLNotification> nErrorList, Attributes answerAttrs) {
        this.xmlReader = nXMLReader;
        this.parent = nParent;

        this.errorList = nErrorList;
        
        this.lesson = nLesson;
        this.page = nPage;
        this.answerBox = nAnswerBox;
        
        isAnswerBox = true;
        
        answerStart(answerAttrs);
    }
    
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
    
    public void endHandler() {
        if(readBuffer == null) {
            errorList.add(new XMLNotification(Level.ERROR,
                    "Page " + lesson.pages.size() +
                    ", Object " + page.getObjectCount() +
                    " (Answer) Answer " + multipleChoice.getAnswers().size() +
                    " missing text."));
        } else {
            answer.setText(readBuffer);
            
            if(isAnswerBox && answerBox != null) {
                answerBox.addAnswer(answer);
            } else if(!isAnswerBox && multipleChoice != null) {
                multipleChoice.addAnswer(answer); 
            }
        }
        
        xmlReader.setContentHandler(parent);
    }
    
    public void characters(char[] ch, int start, int length) {
        String str = new String(ch, start, length);
        str = str.trim();
        if(!str.isEmpty()) {
            readBuffer = str;
        }
    }
    
    public void answerStart(Attributes attrs) {
        if(isAnswerBox && answerBox != null) {
            boolean correct = XMLUtil.checkBool(attrs.getValue("correct"), false, Level.ERROR, errorList, 
                    "Page " + lesson.pages.size() +
                    ", Object " + page.getObjectCount() +
                    " (Answer) Answer " + answerBox.getAnswers().size());
            
           answer = new Answer(null, correct); 
        } else if(!isAnswerBox && multipleChoice != null) {
             boolean correct = XMLUtil.checkBool(attrs.getValue("correct"), false, Level.ERROR, errorList, 
                    "Page " + lesson.pages.size() +
                    ", Object " + page.getObjectCount() +
                    " (Answer) Answer " + multipleChoice.getAnswers().size());
             
             answer = new Answer(null, correct);
        }
    } 
}


