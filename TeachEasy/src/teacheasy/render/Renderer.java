/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 */
package teacheasy.render;

import teacheasy.data.*;
import teacheasy.data.PageObject.PageObjectType;
import teacheasy.mediahandler.*;
import wavemedia.graphic.*;
import wavemedia.text.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Contains all the functionality for rendering a lesson
 * on the screen using the media handlers.
 * 
 * @author  Alistair Jewers
 * @version 1.0 02 Mar 2015
 */
public class Renderer {
    /** Reference to the group to draw on */
    private Group group;
    
    /** Rectangle to describe the size of the page area */
    private Rectangle2D bounds;
    
    /* Media Handlers */
    private VideoHandler videoHandler;
    private ImageHandler imageHandler;
    private TextHandler textHandler;
    private GraphicsHandler graphicsHandler;
    public AnswerBoxHandler answerBoxHandler;
    public MultipleChoiceHandler multipleChoiceHandler;
    private AudioHandler audioHandler;
    
    /* Selection boxes*/
    private Rectangle selectionBox;
    private Rectangle hoverBox;
   
    /**
     * Constructor method to set up the renderer.
     * 
     * @param nGroup The group to draw objects on to
     * @param nBounds The size of the page drawing area. 
     */
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
        
        /* Initialise the selection box */
        selectionBox = new Rectangle(10.0, 10.0, 10.0, 10.0);
        selectionBox.setFill(Color.TRANSPARENT);
        selectionBox.setStroke(Color.RED);
        selectionBox.setStrokeWidth(1);
        
        /* Initialise the hover highlight */
        hoverBox = new Rectangle(10.0, 10.0, 10.0, 10.0);
        hoverBox.setStroke(Color.TRANSPARENT);
        hoverBox.setFill(new Color(0.1f, 0.1f, 0.1f, 0.1f));
    }
    
    /** 
     * Render an individual page.
     * 
     * @param page The page to render.
     */
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
            
            /* Break if the object is null */
            if(pageObject == null) {
                break;
            }
            
            /* Act based on type */
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
        
        /* Set up the background */
        Rectangle bg = new Rectangle(bounds.getMaxX(), bounds.getMaxY(), Color.LIGHTGREY);
        bg.setEffect(new DropShadow());

        /* Add the background */
        group.getChildren().addAll(bg);
    }
    
    /** Clears the page, releasing memory if necessary */
    public void clearPage() {
        /* Clear all the media handlers */
        videoHandler.clearVideos();
        audioHandler.clearAudios();
        imageHandler.clearImages();
        graphicsHandler.clearGraphics();
        textHandler.clearTexts();
        answerBoxHandler.clearAnswerBoxes();
        multipleChoiceHandler.clearMultiChoice();
        
        /* Clear the group */
        group.getChildren().clear();
    }
    
    /** 
     * Renders a text box on a page.
     * 
     * @param text The text box to render (data object).
     */
    private void renderText(TextObject text) {
        /* Size and position variables */
        float xstart = (float)bounds.getMaxX() * text.getXStart();
        float ystart = (float)bounds.getMaxY() * text.getYStart();
        float xend = (float)bounds.getMaxX() * text.getXEnd();
        float yend = (float)bounds.getMaxY() * text.getYEnd();
        
        /* Text scaling variable */
        float textScale = 1.0f;
        
        /* Scale to 75% if drawing on the smaller teach easy page*/
        if(RenderUtil.TE_WIDTH - bounds.getMaxX() >= 0.0f) {
            textScale = 0.75f;
        }
        
        /* Instantiate a list of text fragments */
        TextFragmentList fragmentList = new TextFragmentList();

        /* For each rich text object in the text box add to the fragment list */
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
        
        /* Create the text box */
        textHandler.createTextbox(new TextHandlerObject.TextBoxBuilder(xstart, ystart)
                                                       .xEnd(xend)
                                                       .yEnd(yend)
                                                       .textFragmentList(fragmentList)
                                                       .build());
    }
    
    /**
     * Renders a video on a page.
     * 
     * @param video The video object to render (data object).
     */
    private void renderVideo(VideoObject video) {
    	videoHandler.createVideo((float)bounds.getMaxX() * video.getXStart(),
    							(float)bounds.getMaxY() * video.getYStart(),
							    (float)bounds.getMaxX()*(video.getXEnd() - video.getXStart()),
							    video.getSourcefile(),
							    video.isAutoPlay(),
							    video.isLoop());
    }
    
    /**
     * Renders an audio object on a page.
     * 
     * @param audio The audio object to render (data object).
     */
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
    
    /**
     * Renders an image object on a page.
     * 
     * @param image The image object to render (data object).
     */
    private void renderImage(ImageObject image) {
        /* Check the end position of the image */
        if(image.getXEnd() == -1.0f && image.getYEnd() == -1.0f) {
            /*
             * End positions of -1 indicate that the end position has not
             * been calculated.
             */
            
            /* Get the idex the image will be inserted at */
            int index = imageHandler.getImages().size();
            
            /* Insert the image using the scale setting */
            imageHandler.insertImage(image.getSourcefile(),
                                 bounds.getMaxX() * image.getXStart(), 
                                 bounds.getMaxY() * image.getYStart(),
                                 image.getxScaleFactor(), 
                                 image.getyScaleFactor(),
                                 image.getRotation(),
                                 true);
            
            /* Check if the index has not increased */
            if(imageHandler.getImages().size() == index) {
                /* The image couldn't be added for some reason, so return */
                return;
            }
            
            /* Calculate the image size */
            float width = (float)imageHandler.getImage(index).getWidth() * image.getxScaleFactor();
            float height = (float)imageHandler.getImage(index).getHeight() * image.getyScaleFactor();
            
            /* Calculate the image end positions */
            float xEnd = (float)(((image.getXStart() * RenderUtil.LE_WIDTH) + width) / RenderUtil.LE_WIDTH);
            float yEnd = (float)(((image.getYStart() * RenderUtil.LE_HEIGHT) + height) / RenderUtil.LE_HEIGHT);
            
            /* If either end position is greater than 1 reduce to 1 */
            if(xEnd > 1.0f) {
                xEnd = 1.0f;
            }
            
            if(yEnd > 1.0f) {
                yEnd = 1.0f;
            }
            
            /* Set the end positions */
            image.setXEnd(xEnd);
            image.setYEnd(yEnd);
            
            /* Remove the image now that size has been calculated */
            imageHandler.removeImage(index);
        }
        
        /* Insert the image using the start and end positions */
        imageHandler.insertImage(image.getSourcefile(),
                                 bounds.getMaxX() * image.getXStart(), 
                                 bounds.getMaxY() * image.getYStart(),
                                 bounds.getMaxX() * (image.getXEnd() - image.getXStart()), 
                                 bounds.getMaxY() * (image.getYEnd() - image.getYStart()),
                                 image.getRotation(),
                                 false);        
        
    }
    
    /**
     * Renders a graphic object on a page.
     * 
     * @param graphic The graphic object to render (data object).
     */
    private void renderGraphic(GraphicObject graphic) {
        /* Calculate position information */
        float xstart = (float)bounds.getMaxX() * graphic.getXStart();
        float ystart = (float)bounds.getMaxY() * graphic.getYStart();
        float xend = (float)bounds.getMaxX() * graphic.getXEnd();
        float yend = (float)bounds.getMaxY() * graphic.getYEnd();
        
        /* String object for shadow type */
        String shadowStr = new String("none");
        
        /* If the data includes a shadow, set the string to normal */
        if(graphic.isShadow()) {
            shadowStr = new String("normal");
        }
        
        /* Use the graphics handler to build and display the graphic */
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
    
    /**
     * Renders an answer box to the page.
     * 
     * @param aBox The answer box object to render (data object).
     */
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
    
    /**
     * Renders a multiple choice object to the page.
     * 
     * @param mChoice The multiple choice object to render (data object).
     */
    public void renderMultipleChoice(MultipleChoiceObject mChoice) {
        multipleChoiceHandler.createMultipleChoice((float)(bounds.getMaxX() * mChoice.getXStart()),
                                                   (float)(bounds.getMaxY() * mChoice.getYStart()),
                                                   mChoice.getAnswers(),
                                                   mChoice.getMultiChoiceType(),
                                                   mChoice.getOrientation(),
                                                   mChoice.isRetry(), 
                                                   mChoice.getMarks());
    }
    
    /**
     * Renders a highlight around an object. Can either be a selection
     * indicator or a hover indicator.
     * 
     * @param object The object that is currently selected.
     * @param page The current page.
     * @param hover Whether or not this is a hover render.
     */
    public void renderSelection(PageObject object, Page page, boolean hover) {
        /* If there is no object selected, hide the selection and return */
        if(object == null) {
            if(hover) {
                hoverBox.setVisible(false);
            } else {
                selectionBox.setVisible(false);
            }
            return;
        }
        
        /* Position and size variables */
        double x = 0.0, y = 0.0, width = 0.0, height = 0.0;
        
        /* Calculate position and size values */
        x = object.getXStart() * bounds.getMaxX();
        y = object.getYStart() * bounds.getMaxY();
        width = getObjectWidth(object, page, false);
        height = getObjectHeight(object, page, false);
        
        /* Render the hover box or the selection box depending on the setting */
        if(hover) {
            renderHoverBox(x, y, width, height);
        } else {
            renderSelectionBox(x, y, width, height);
        }
        
    }
    
    /**
     * Renders a box to indicate object selection.
     * 
     * @param x The x position of the left edge of the box.
     * @param y The y position of the top edge of the box.
     * @param width The width of the box.
     * @param height The height of the box.
     */
    public void renderSelectionBox(double x, double y, double width, double height) {
        /* Make the box visible */
        selectionBox.setVisible(true);
        
        /* Set the location and size */
        selectionBox.relocate(x, y);
        selectionBox.setWidth(width);
        selectionBox.setHeight(height);
        
        /* Bring the box to the front of the scene */
        selectionBox.toFront();
        
        /* If the box is not already on the group, add it */
        if(!group.getChildren().contains(selectionBox)) {
            group.getChildren().add(selectionBox);
        }
    }
    
    /**
     * Renders a box to indicate the mouse hovering over an object.
     * 
     * @param x The x position of the left edge of the box.
     * @param y The y position of the top edge of the box.
     * @param width The width of the box.
     * @param height The height of the box.
     */
    public void renderHoverBox(double x, double y, double width, double height) {
        /* Make the box visible */
        hoverBox.setVisible(true);
        
        /* Set the location and size */
        hoverBox.relocate(x, y);
        hoverBox.setWidth(width);
        hoverBox.setHeight(height);
        
        /* Bring the box to the front of the scene */
        hoverBox.toFront();
        
        /* If the box is not already on the group, add it */
        if(!group.getChildren().contains(hoverBox)) {
            group.getChildren().add(hoverBox);
        }
    }
    
    /**
     * Returns the width of a video in pixels, given the corresponding
     * data object.
     * 
     * @param video The video data object.
     * @param page The current page.
     * @return The width of the related video object in the scene.
     */
    public double getVideoWidth(VideoObject video, Page page) {
        int index = 0;
        double width = 150;
        
        for(PageObject p : page.pageObjects) {            
            if(p.getType() == PageObjectType.VIDEO) {                
                if(p == video) {                                        
                    width = videoHandler.getVideoWidth(index);
                }
                
                index++;
            }
        }
        
        if(width == 0.0) {
            return 150.0;
        }
        return width;
    }
    
    /**
     * Returns the height of a video in pixels, given the corresponding
     * data object.
     * 
     * @param video The video data object.
     * @param page The current page.
     * @return The height of the related video object in the scene.
     */
    public double getVideoHeight(VideoObject video, Page page) {
        int index = 0;
        double height = 150;
        
        for(PageObject p : page.pageObjects) {            
            if(p.getType() == PageObjectType.VIDEO) {                
                if(p == video) {                                        
                    height = videoHandler.getVideoHeight(index);
                }
                
                index++;
            }
        }
        
        if(height == 0.0) {
            return 150.0;
        }
        return height;
    }
    
    /**
     * Returns the width of an audio object in pixels, given the 
     * corresponding data object.
     * 
     * @param audio The audio data object.
     * @param page The current page.
     * @return The width of the related audio object in the scene.
     */
    public double getAudioWidth(AudioObject audio, Page page) {
        int index = 0;
        double width = 0;
        
        for(PageObject p : page.pageObjects) {            
            if(p.getType() == PageObjectType.AUDIO) {                
                if(p == audio) {                                        
                    width = audioHandler.getAudioXEnd(index) - (audio.getXStart() * bounds.getMaxX());
                }
                
                index++;
            }
        }
        
        return width;
    }
    
    /**
     * Returns the height of an audio object in pixels, given the 
     * corresponding data object.
     * 
     * @param audio The audio data object.
     * @param page The current page.
     * @return The height of the related audio object in the scene.
     */
    public double getAudioHeight(AudioObject audio, Page page) {
        int index = 0;
        double height = 0;
        
        for(PageObject p : page.pageObjects) {            
            if(p.getType() == PageObjectType.AUDIO) {                
                if(p == audio) {                                        
                    height = audioHandler.getAudioYEnd(index) - (audio.getYStart() * bounds.getMaxY());
                }
                
                index++;
            }
        }
        
        return height;
    }
    
    /**
     * Returns the width of an answer box object in pixels, given the 
     * corresponding data object.
     * 
     * @param answerBox The answer box data object.
     * @param page The current page.
     * @return The width of the related answer box object in the scene.
     */
    public double getAnswerBoxWidth(AnswerBoxObject answerBox, Page page) {
        int index = 0;
        double width = 0;
        
        for(PageObject p : page.pageObjects) {            
            if(p.getType() == PageObjectType.ANSWER_BOX) {                
                if(p == answerBox) {                                        
                    width = answerBoxHandler.getAnswerBoxWidth(index);
                }
                
                index++;
            }
        }
        
        return width;
    }
    
    /**
     * Returns the height of an answer box object in pixels, given the 
     * corresponding data object.
     * 
     * @param answerBox The answer box data object.
     * @param page The current page.
     * @return The height of the related answer box object in the scene.
     */
    public double getAnswerBoxHeight(AnswerBoxObject answerBox, Page page) {
        int index = 0;
        double height = 0;
        
        for(PageObject p : page.pageObjects) {            
            if(p.getType() == PageObjectType.ANSWER_BOX) {                
                if(p == answerBox) {                                        
                    height = answerBoxHandler.getAnswerBoxHeight(index);
                }
                
                index++;
            }
        }
        
        return height;
    }
    
    /**
     * Returns the width of a multiple choice object in pixels, given the 
     * corresponding data object.
     * 
     * @param answerBox The multiple choice data object.
     * @param page The current page.
     * @return The width of the related multiple choice object in the scene.
     */
    public double getMultipleChoiceWidth(MultipleChoiceObject multipleChoice, Page page) {
        int index = 0;
        double width = 0;
        
        for(PageObject p : page.pageObjects) {            
            if(p.getType() == PageObjectType.MULTIPLE_CHOICE) {                
                if(p == multipleChoice) {                                        
                    width = multipleChoiceHandler.getMultiChoiceWidth(index);
                }
                
                index++;
            }
        }
        
        return width;
    }
    
    /**
     * Returns the height of a multiple choice object in pixels, given the 
     * corresponding data object.
     * 
     * @param answerBox The multiple choice data object.
     * @param page The current page.
     * @return The height of the related multiple choice object in the scene.
     */
    public double getMultipleChoiceHeight(MultipleChoiceObject multipleChoice, Page page) {
        int index = 0;
        double height = 0;
        
        for(PageObject p : page.pageObjects) {            
            if(p.getType() == PageObjectType.MULTIPLE_CHOICE) {                
                if(p == multipleChoice) {                                        
                    height = multipleChoiceHandler.getMultiChoiceHeight(index);
                }
                
                index++;
            }
        }
        
        return height;
    }
    
    /**
     * Returns the width of an object in the scene given its
     * corresponding data object.
     * 
     * @param object The data object to get the scene width of.
     * @param page The page the object is on.
     * @param relative Whether to return a relative or absolute value.
     * @return The width of the given object.
     */
    public double getObjectWidth(PageObject object, Page page, boolean relative) {
        /* Width variable */
        double width = 0.0;
        
        /* Act based on object type to calculate width */
        switch(object.getType()) {
            case ANSWER_BOX:
                width = getAnswerBoxWidth((AnswerBoxObject)object, page);
                break;
            case AUDIO:
                width = getAudioWidth((AudioObject)object, page);
                break;
            case GRAPHIC:
                GraphicObject graphic = (GraphicObject)object;
                width = (graphic.getXEnd() - graphic.getXStart()) * bounds.getMaxX();
                break;
            case IMAGE:
                ImageObject image = (ImageObject)object;
                width = (image.getXEnd() - image.getXStart()) * bounds.getMaxX(); 
                break;
            case MULTIPLE_CHOICE:
                width = getMultipleChoiceWidth((MultipleChoiceObject)object, page);
                break;
            case TEXT:
                TextObject text = (TextObject)object;
                width = (text.getXEnd() - text.getXStart()) * bounds.getMaxX(); 
                break;
            case VIDEO:
                width = getVideoWidth((VideoObject)object, page);
                break;
            default:
                return 0.0;
        }
        
        /* Either return the relative or absolute width */
        if(relative) {
            return width / bounds.getMaxX();
        } else {
            return width;
        }
    }
    
    /**
     * Returns the height of an object in the scene given its
     * corresponding data object.
     * 
     * @param object The data object to get the scene height of.
     * @param page The page the object is on.
     * @param relative Whether to return a relative or absolute value.
     * @return The height of the given object.
     */
    public double getObjectHeight(PageObject object, Page page, boolean relative) {
        /* Height variable */
        double height = 0.0;
        
        /* Act based on type to calculate height */
        switch(object.getType()) {
            case ANSWER_BOX:
                height = getAnswerBoxHeight((AnswerBoxObject)object, page);
                break;
            case AUDIO:
                height = getAudioHeight((AudioObject)object, page) + 20;
                break;
            case GRAPHIC:
                GraphicObject graphic = (GraphicObject)object;
                height = (graphic.getYEnd() - graphic.getYStart()) * bounds.getMaxY(); 
                break;
            case IMAGE:
                ImageObject image = (ImageObject)object;
                height = (image.getYEnd() - image.getYStart()) * bounds.getMaxY(); 
                break;
            case MULTIPLE_CHOICE:
                height = getMultipleChoiceHeight((MultipleChoiceObject)object, page);
                break;
            case TEXT:
                TextObject text = (TextObject)object;
                height = (text.getYEnd() - text.getYStart()) * bounds.getMaxY(); 
                break;
            case VIDEO:
                height = getVideoHeight((VideoObject)object, page);
                break;
            default:
                return 0.0;
        }
        
        /* Either return a relative or absolute height */
        if(relative) {
            return height / bounds.getMaxY();
        } else {
            return height;
        }
    }
    
    /**
     * Prints debugging information about all the handlers.
     */
    public void debugPrint() {
        /* Clear a line */
        System.out.println("");
        
        /* Print all the handlers debug info */
        textHandler.debugPrint();
        imageHandler.debugPrint();
        videoHandler.debugPrint();
        audioHandler.debugPrint();
        graphicsHandler.debugPrint();
        answerBoxHandler.debugPrint();
        
        /* Clear a line */
        System.out.println("");
    }
}