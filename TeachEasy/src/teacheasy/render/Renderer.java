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
import teacheasy.mediahandler.*;
import wavemedia.graphic.GraphicsHandler;
import wavemedia.graphic.Shading;
import wavemedia.graphic.Shadow;
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
                               (int)(bounds.getMaxX()),
                               (int)(bounds.getMaxY()),
                               "#00000000",
                               Alignment.LEFT);
        
    }
    
    /** Render a video on a page */
    private void renderVideo(VideoObject video) {
        videoHandler.createVideo(bounds.getMaxX() * video.getXStart(),
                                 bounds.getMaxY() * video.getYStart(),
                                 800,
                                 video.getSourcefile(),
                                 false,
                                 false);
    }
    
    /** Render an audio object on a page */
    private void renderAudio(AudioObject audio) {
        audioHandler.createAudio((double)(bounds.getMaxX() * audio.getXStart()),
                                 (double)(bounds.getMaxY() * audio.getYStart()),
                                 300,
                                 new File(audio.getSourcefile()),
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
        
        /* Draw the appropriate graphic */
        switch(graphic.getGraphicType()) {
            case OVAL:
                graphicsHandler.drawOval((float)(bounds.getMaxX() * graphic.getXStart()),
                                         (float)(bounds.getMaxY() * graphic.getYStart()), 
                                         (float)(bounds.getMaxX() * graphic.getXEnd()), 
                                         (float)(bounds.getMaxY() * graphic.getYEnd()), 
                                         Util.colorFromString(graphic.getGraphicColour()), 
                                         graphic.isSolid(), 
                                         Util.colorFromString(graphic.getLineColor()),
                                         graphic.getOutlineThickness(), 
                                         Shadow.NONE, 
                                         graphic.getRotation(), 
                                         shading,
                                         Util.colorFromString(graphic.getShadingColor()));
                break;
            case RECTANGLE:
                graphicsHandler.drawRectangle((float)(bounds.getMaxX() * graphic.getXStart()),
                                              (float)(bounds.getMaxY() * graphic.getYStart()),
                                              (float)(bounds.getMaxX() * graphic.getXEnd()),
                                              (float)(bounds.getMaxY() * graphic.getYEnd()),
                                              0.0f,
                                              0.0f,
                                              Util.colorFromString(graphic.getGraphicColour()),
                                              graphic.isSolid(), 
                                              Util.colorFromString(graphic.getLineColor()),
                                              graphic.getOutlineThickness(),
                                              Shadow.NONE, 
                                              graphic.getRotation(), 
                                              shading,
                                              Util.colorFromString(graphic.getShadingColor()));
                break;
            case LINE:
                graphicsHandler.drawLine((float)(bounds.getMaxX() * graphic.getXStart()),
                                         (float)(bounds.getMaxY() * graphic.getYStart()), 
                                         (float)(bounds.getMaxX() * graphic.getXEnd()), 
                                         (float)(bounds.getMaxY() * graphic.getYEnd()), 
                                         Util.colorFromString(graphic.getGraphicColour()), 
                                         graphic.getOutlineThickness(), 
                                         shading,
                                         Util.colorFromString(graphic.getShadingColor()));
                break;
            case TRIANGLE:
                System.out.println("Cannot Currently Draw Equi Triangle - Graphics Handler Unfinished");
                graphicsHandler.drawEquiTriangle();
                break;
            case ROUNDEDRECTANGLE:
                System.out.println("Cannot Currently Draw Rounded Rects - Graphics Handler Unfinished");
                graphicsHandler.drawRectangle((float)(bounds.getMaxX() * graphic.getXStart()),
                                              (float)(bounds.getMaxY() * graphic.getYStart()),
                                              (float)(bounds.getMaxX() * graphic.getXEnd()),
                                              (float)(bounds.getMaxY() * graphic.getYEnd()),
                                              150.0f,
                                              150.0f,
                                              Util.colorFromString(graphic.getGraphicColour()),
                                              graphic.isSolid(), 
                                              Util.colorFromString(graphic.getLineColor()),
                                              graphic.getOutlineThickness(),
                                              Shadow.NONE, 
                                              graphic.getRotation(), 
                                              shading,
                                              Util.colorFromString(graphic.getShadingColor()));
                break;
            default:
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
