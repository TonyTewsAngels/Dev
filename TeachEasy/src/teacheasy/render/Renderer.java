/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.render;

import java.io.File;
import java.util.ArrayList;

import teacheasy.data.*;
import teacheasy.data.GraphicObject;
import teacheasy.mediahandler.*;
import wavemedia.graphic.*;
import wavemedia.graphic.GraphicHandlerObject.*;
import wavemedia.text.Alignment;
import wavemedia.text.TextAttribute;
import wavemedia.text.TextHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * This class encapsulates the functionality for
 * rendering a lesson to the screen.
 * 
 * @author Alistair Jewers
 * @version 1.0 02 Mar 2015
 */
public class Renderer {
    private Group group;
    private Rectangle2D bounds;
    
    private VideoHandler videoHandler;
    private ImageHandler imageHandler;
    private TextHandler textHandler;
    private GraphicsHandler graphicsHandler;
    private AnswerBoxHandler answerBoxHandler;
    private MultipleChoiceHandler multipleChoiceHandler;
    private AudioHandler audioHandler;
   
    /** Constructor */
    public Renderer(Group nGroup, Rectangle2D nBounds) {
        /* Set up the group reference */
        this.group = nGroup;
        
        /* Set the bounds of the drawing area */
        this.bounds = nBounds;
        
        /* Instantiate the handlers */
        videoHandler = new VideoHandler(group);
        imageHandler = new ImageHandler(group);
        textHandler = new TextHandler(group);
        graphicsHandler = new GraphicsHandler(group);
        answerBoxHandler = new AnswerBoxHandler(group);
        multipleChoiceHandler = new MultipleChoiceHandler(group);
        audioHandler = new AudioHandler(group);
    }
    
    /** Render an individual page */
    public void renderPage(Page page) {
        /* Clear the page */
        clearPage();
        
        /* Add the background */
        Rectangle bg = new Rectangle(bounds.getMaxX(), bounds.getMaxY(), Util.colorFromString(page.getPageColour()));
        group.getChildren().add(bg);
        
        /* Loop through all the objects on the page */
        for(int i = 0; i < page.pageObjects.size(); i++) {
            /* Get the object */
            PageObject pageObject = page.pageObjects.get(i);
            
            /* Act based on type */
            if(pageObject == null) {
                System.out.println(i);
            }
            switch(pageObject.getType()) {
                case TEXT:
                    renderText((TextObject) pageObject);
                    break;
                case IMAGE:
                    renderImage((ImageObject) pageObject);
                    break;
                case VIDEO:
                    renderVideo((VideoObject) pageObject);
                    break;
                case AUDIO:
                    renderAudio((AudioObject) pageObject);
                    break;
                case GRAPHIC:
                    renderGraphic((GraphicObject) pageObject);
                    break;
                case ANSWER_BOX:
                    renderAnswerBox((AnswerBoxObject) pageObject);
                    break;
                case MULTIPLE_CHOICE:
                    renderMultipleChoice((MultipleChoiceObject) pageObject);
                    break;
                default:
                    break;
            }
        }
    }
    
    /** Render the screen if no lesson is loaded */
    public void renderUnLoaded() {
        /* Clear the page */
        clearPage();
        
        /* Add the background */
        Rectangle bg = new Rectangle(bounds.getMaxX(), bounds.getMaxY(), Color.WHITE);
        group.getChildren().add(bg);
        
        /* Add the help message */
        Text text = new Text("Open a lesson to begin!");
        text.relocate(10, 10);
        group.getChildren().add(text);
    }
    
    /** Clears the page, releasing memory if necessary */
    public void clearPage() {
        videoHandler.clearVideos();
        audioHandler.clearAudios();
        group.getChildren().removeAll();
    }
    
    /** Render a text box on a page */
    private void renderText(TextObject text) {
        /* Clear the handler buffer */
        textHandler.clearBuffer();
        
        /* Loop through each rich text element */
        for(int i = 0; i < text.textFragments.size(); i++) {
            /* Get the rich text */
            RichText richText = text.textFragments.get(i);
            
            /* Create an array of the attributes */
            ArrayList<TextAttribute> attrs = new ArrayList<TextAttribute>();
            if(richText.isBold()) attrs.add(TextAttribute.BOLD);
            if(richText.isItalic()) attrs.add(TextAttribute.ITALIC);
            if(richText.isUnderline()) attrs.add(TextAttribute.UNDERLINE);
            if(richText.isSubscript()) attrs.add(TextAttribute.SUBSCRIPT);
            if(richText.isSuperscript()) attrs.add(TextAttribute.SUPERSCRIPT);
            if(richText.isStrikethrough()) attrs.add(TextAttribute.STRIKETHROUGH);         
            
            /* Add the rich text fragment to the buffer */
            textHandler.addStringToBuffer(richText.getText(),
                                          richText.getFont(),
                                          richText.getFontSize(),
                                          richText.getColor(),
                                          "#00000000",
                                          attrs.toArray(new TextAttribute[attrs.size()])); 
        }
        
        /* Draw the buffer to the screen */
        textHandler.drawBuffer((int)(bounds.getMaxX() * text.getXStart()),
                               (int)(bounds.getMaxY() * text.getYStart()),
                               (int)(bounds.getMaxX() * text.getXEnd()),
                               (int)(bounds.getMaxY() * text.getYEnd()),
                               "#00000000",
                               Alignment.LEFT);
        
    }
    
    /** Render a video on a page */
    private void renderVideo(VideoObject video) {
    	videoHandler.createVideo((float)bounds.getMaxX() * video.getXStart(),
    							(float)bounds.getMaxY() * video.getYStart(),
							    (float)bounds.getMaxX()*(video.getXEnd() - video.getXStart()),
							    video.getSourcefile(),
							    false,
							    false);
    }
    
    /** Render an audio object on a page */
    private void renderAudio(AudioObject audio) {
        audioHandler.createAudio((double)(bounds.getMaxX() * audio.getXStart()),
                                 (double)(bounds.getMaxY() * audio.getYStart()),
                                 (double)bounds.getMaxX()*(audio.getXEnd() - audio.getXStart()),
                                 audio.getSourcefile(),
                                 false,
                                 false,
                                 audio.isViewProgress(),
                                 false);
    }
    
    /** Render an image on a page */
    private void renderImage(ImageObject image) {
        imageHandler.insertImage(image.getSourcefile(),
                                 bounds.getMaxX() * image.getXStart(), 
                                 bounds.getMaxY() * image.getYStart(),
                                 image.getxScaleFactor(), 
                                 image.getyScaleFactor(),
                                 image.getRotation());
    }
    
    /** Render a graphic object on a page */
    private void renderGraphic(GraphicObject graphic) {        
        /* Set up the shading */
        Shading shading;
        switch(graphic.getShading()) {
            case CYCLIC:
                shading = Shading.CYCLIC;
                break;
            case HORIZONTAL:
                shading = Shading.HORIZONTAL;
                break;
            case VERTICAL:
                shading = Shading.VERTICAL;
                break;
            default:
                shading = Shading.NONE;
                break;
        }
    }
    
    /** Add an answer box object to the screen */
    public void renderAnswerBox(AnswerBoxObject aBox) {      
        answerBoxHandler.createAnswerBox((float)(bounds.getMaxX() * aBox.getXStart()),
                                         (float)(bounds.getMaxY() * aBox.getYStart()),
                                         aBox.getCharacterLimit(),
                                         aBox.isRetry(),
                                         aBox.getCorrectAnswers(),
                                         aBox.getMarks(),
                                         aBox.isNumerical());
    }
    
    /** Add a multiple choice object to the screen */
    public void renderMultipleChoice(MultipleChoiceObject mChoice) {
        multipleChoiceHandler.createMultipleChoice((float)(bounds.getMaxX() * mChoice.getXStart()),
                                                   (float)(bounds.getMaxY() * mChoice.getYStart()),
                                                   mChoice.getAnswers(),
                                                   mChoice.getMultiChoiceType(),
                                                   mChoice.getOrientation(),
                                                   mChoice.isRetry());
    }
}