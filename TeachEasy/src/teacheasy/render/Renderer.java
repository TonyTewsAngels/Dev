/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.render;

import teacheasy.data.*;
import teacheasy.mediahandler.*;
import wavemedia.graphic.*;
import wavemedia.text.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
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
    public AnswerBoxHandler answerBoxHandler;
    public MultipleChoiceHandler multipleChoiceHandler;
    private AudioHandler audioHandler;
   
    /** Constructor */
    public Renderer(Group nGroup, Rectangle2D nBounds) {
        /* Set up the group reference */
        this.group = nGroup;
        
        /* Set the bounds of the drawing area */
        this.bounds = nBounds;
        
        /* Instantiate the handlers */
        videoHandler = new VideoHandler(group, null);
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
        Rectangle bg = new Rectangle(bounds.getMaxX(), bounds.getMaxY(), RenderUtil.colorFromString(page.getPageColour()));
        bg.setEffect(new DropShadow());
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
        
        debugPrint();
    }
    
    /** Render the screen if no lesson is loaded */
    public void renderUnLoaded() {
        /* Clear the page */
        clearPage();
        
        /* Add the background */
        Rectangle bg = new Rectangle(bounds.getMaxX(), bounds.getMaxY(), Color.WHITE);
        bg.setEffect(new DropShadow());
        group.getChildren().add(bg);
    }
    
    /** Clears the page, releasing memory if necessary */
    public void clearPage() {
        videoHandler.clearVideos();
        audioHandler.clearAudios();
        imageHandler.clearImages();
        graphicsHandler.clearGraphics();
        textHandler.clearTexts();
        answerBoxHandler.clearAnswerBoxes();
        group.getChildren().clear();
    }
    
    /** Render a text box on a page */
    private void renderText(TextObject text) {
        float xstart = (float)bounds.getMaxX() * text.getXStart();
        float ystart = (float)bounds.getMaxY() * text.getYStart();
        float xend = (float)bounds.getMaxX() * text.getXEnd();
        float yend = (float)bounds.getMaxY() * text.getYEnd();
        
        float textScale = 1.0f;
        
        if(RenderUtil.TE_WIDTH - bounds.getMaxX() >= 0.0f) {
            textScale = 0.75f;
        }
        
        TextFragmentList fragmentList = new TextFragmentList();

        for(RichText rt : text.textFragments) {
            fragmentList.add(new TextHandlerObject.TextFragmentBuilder(rt.getText())
                                                  .bold(rt.isBold())
                                                  .italic(rt.isItalic())
                                                  .underline(rt.isUnderline())
                                                  .superscript(rt.isSuperscript())
                                                  .subscript(rt.isSubscript())
                                                  .strikethrough(rt.isStrikethrough())
                                                  .fontName(rt.getFont())
                                                  .fontColor(rt.getColor())
                                                  .fontSize(Math.round(rt.getFontSize() * textScale))
                                                  .newline(rt.isNewLine())
                                                  .build());
        }
        
        textHandler.createTextbox(new TextHandlerObject.TextBoxBuilder(xstart, ystart)
                                                       .xEnd(xend)
                                                       .yEnd(yend)
                                                       .textFragmentList(fragmentList)
                                                       .build());
    }
    
    /** Render a video on a page */
    private void renderVideo(VideoObject video) {
    	videoHandler.createVideo((float)bounds.getMaxX() * video.getXStart(),
    							(float)bounds.getMaxY() * video.getYStart(),
							    (float)bounds.getMaxX()*(video.getXEnd() - video.getXStart()),
							    video.getSourcefile(),
							    video.isAutoPlay(),
							    video.isLoop());
    }
    
    /** Render an audio object on a page */
    private void renderAudio(AudioObject audio) {
        audioHandler.createAudio((float)(bounds.getMaxX() * audio.getXStart()),
                                 (float)(bounds.getMaxY() * audio.getYStart()),
                                 (float)bounds.getMaxX()*(audio.getXEnd() - audio.getXStart()),
                                 audio.getSourcefile(),
                                 audio.isAutoPlay(),
                                 audio.isLoop(),
                                 true,
                                 !audio.isViewProgress());
    }
    
    /** Render an image on a page */
    private void renderImage(ImageObject image) {
        if(image.getXEnd() == -1.0f && image.getYEnd() == -1.0f) {
            int index = imageHandler.getImages().size();
            
            imageHandler.insertImage(image.getSourcefile(),
                                 bounds.getMaxX() * image.getXStart(), 
                                 bounds.getMaxY() * image.getYStart(),
                                 image.getxScaleFactor(), 
                                 image.getyScaleFactor(),
                                 image.getRotation(),
                                 true);
            
            /* The image couldn't be added for some reason */
            if(imageHandler.getImages().size() == index) {
                return;
            }
            
            float width = (float)imageHandler.getImage(index).getWidth() * image.getxScaleFactor();
            float height = (float)imageHandler.getImage(index).getHeight() * image.getyScaleFactor();
            
            float xEnd = (float)(((image.getXStart() * RenderUtil.LE_WIDTH) + width) / RenderUtil.LE_WIDTH);
            float yEnd = (float)(((image.getYStart() * RenderUtil.LE_HEIGHT) + height) / RenderUtil.LE_HEIGHT);
            
            if(xEnd > 1.0f) {
                xEnd = 1.0f;
            }
            
            if(yEnd > 1.0f) {
                yEnd = 1.0f;
            }
            
            image.setXEnd(xEnd);
            image.setYEnd(yEnd);
            
            imageHandler.removeImage(index);
        }
        
        imageHandler.insertImage(image.getSourcefile(),
                                 bounds.getMaxX() * image.getXStart(), 
                                 bounds.getMaxY() * image.getYStart(),
                                 bounds.getMaxX() * (image.getXEnd() - image.getXStart()), 
                                 bounds.getMaxY() * (image.getYEnd() - image.getYStart()),
                                 image.getRotation(),
                                 false);        
        
    }
    
    /** Render a graphic object on a page */
    private void renderGraphic(GraphicObject graphic) {
        float xstart = (float)bounds.getMaxX() * graphic.getXStart();
        float ystart = (float)bounds.getMaxY() * graphic.getYStart();
        float xend = (float)bounds.getMaxX() * graphic.getXEnd();
        float yend = (float)bounds.getMaxY() * graphic.getYEnd();
        
        String shadowStr = new String("none");
        
        if(graphic.isShadow()) {
            shadowStr = new String("normal");
        }
        
        graphicsHandler.createGraphic(new GraphicHandlerObject.GraphicBuilder(graphic.getGraphicType(), xstart, ystart)
                                                                              .xEndPos(xend)
                                                                              .yEndPos(yend)
                                                                              .color(graphic.getGraphicColour())
                                                                              .outlineColor(graphic.getLineColor())
                                                                              .outlineThickness(graphic.getOutlineThickness())
                                                                              .rotation(graphic.getRotation())
                                                                              .shadingType(graphic.getShading().toString())
                                                                              .shadingElement(graphic.getShadingColor(), 1.0f)
                                                                              .solid(graphic.isSolid())
                                                                              .shadow(shadowStr)
                                                                              .build());
    }
    
    /** Add an answer box object to the screen */
    public void renderAnswerBox(AnswerBoxObject aBox) {      
        answerBoxHandler.createAnswerBox((float)(bounds.getMaxX() * aBox.getXStart()),
                                         (float)(bounds.getMaxY() * aBox.getYStart()),
                                         aBox.getCharacterLimit(),
                                         aBox.isRetry(),
                                         aBox.getMarks(),
                                         aBox.isNumerical(),
                                         aBox.getUpperBound(),
                                         aBox.getLowerBound(),
                                         aBox.getAnswers());
    }
    
    /** Add a multiple choice object to the screen */
    public void renderMultipleChoice(MultipleChoiceObject mChoice) {
        multipleChoiceHandler.createMultipleChoice((float)(bounds.getMaxX() * mChoice.getXStart()),
                                                   (float)(bounds.getMaxY() * mChoice.getYStart()),
                                                   mChoice.getAnswers(),
                                                   mChoice.getMultiChoiceType(),
                                                   mChoice.getOrientation(),
                                                   mChoice.isRetry(), 
                                                   mChoice.getMarks());
    }
    
    public void debugPrint() {
        System.out.println("");
        textHandler.debugPrint();
        imageHandler.debugPrint();
        videoHandler.debugPrint();
        audioHandler.debugPrint();
        graphicsHandler.debugPrint();
        answerBoxHandler.debugPrint();
        System.out.println("");
    }
}