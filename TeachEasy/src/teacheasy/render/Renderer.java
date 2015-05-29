/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
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
import javafx.scene.paint.Paint;
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
    
    private Rectangle selectionBox;
    private Rectangle hoverBox;
   
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
        
        selectionBox = new Rectangle(10.0, 10.0, 10.0, 10.0);
        selectionBox.setFill(Color.TRANSPARENT);
        selectionBox.setStroke(Color.RED);
        selectionBox.setStrokeWidth(1);
        
        hoverBox = new Rectangle(10.0, 10.0, 10.0, 10.0);
        hoverBox.setStroke(Color.TRANSPARENT);
        hoverBox.setFill(new Color(0.1f, 0.1f, 0.1f, 0.1f));
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
    
    public void renderSelection(PageObject object, Page page, boolean hover) {
        if(object == null) {
            if(hover) {
                hoverBox.setVisible(false);
            } else {
                selectionBox.setVisible(false);
            }
            return;
        }
        
        double x = 0.0, y = 0.0, width = 0.0, height = 0.0;
        
        switch(object.getType()) {
            case IMAGE:
                ImageObject image = (ImageObject)object;
                x = image.getXStart() * bounds.getMaxX();
                y = image.getYStart() * bounds.getMaxY();
                width = (image.getXEnd() * bounds.getMaxX()) - x;
                height = (image.getYEnd() * bounds.getMaxY()) - y;
                break;
            case ANSWER_BOX:
                AnswerBoxObject answerBox = (AnswerBoxObject)object;
                x = answerBox.getXStart() * bounds.getMaxX();
                y = answerBox.getYStart() * bounds.getMaxY();
                width = getAnswerBoxWidth(answerBox, page);
                height = getAnswerBoxHeight(answerBox, page);
                break;
            case AUDIO:
                AudioObject audio = (AudioObject)object;
                x =  audio.getXStart() * bounds.getMaxX();
                y =  audio.getYStart() * bounds.getMaxY();
                width = getAudioWidth(audio, page);
                height = getAudioHeight(audio, page);
                break;
            case GRAPHIC:
                GraphicObject graphic = (GraphicObject)object;
                x = graphic.getXStart() * bounds.getMaxX();
                y = graphic.getYStart() * bounds.getMaxY();
                width = (graphic.getXEnd() * bounds.getMaxX()) - x;
                height = (graphic.getYEnd() * bounds.getMaxY()) - y;
                break;
            case MULTIPLE_CHOICE:
                break;
            case TEXT:
                TextObject text = (TextObject)object;
                x = text.getXStart() * bounds.getMaxX();
                y = text.getYStart() * bounds.getMaxY();
                width = (text.getXEnd() * bounds.getMaxX()) - x;
                height = (text.getYEnd() * bounds.getMaxY()) - y;
                break;
            case VIDEO:
                VideoObject video = (VideoObject)object;
                x = video.getXStart() * bounds.getMaxX();
                y = video.getYStart() * bounds.getMaxY();
                width = getVideoWidth(video, page);
                height = getVideoHeight(video, page);
                break;
            default:
                break;
        }
        
        if(hover) {
            renderHoverBox(x, y, width, height);
        } else {
            renderSelectionBox(x, y, width, height);
        }
        
    }
    
    public void renderSelectionBox(double x, double y, double width, double height) {
        selectionBox.setVisible(true);
        selectionBox.relocate(x, y);
        selectionBox.setWidth(width);
        selectionBox.setHeight(height);
        selectionBox.toFront();
        
        if(!group.getChildren().contains(selectionBox)) {
            group.getChildren().add(selectionBox);
        }
    }
    
    public void renderHoverBox(double x, double y, double width, double height) {
        hoverBox.setVisible(true);
        hoverBox.relocate(x, y);
        hoverBox.setWidth(width);
        hoverBox.setHeight(height);
        hoverBox.toFront();
        
        if(!group.getChildren().contains(hoverBox)) {
            group.getChildren().add(hoverBox);
        }
    }
    
    public double getVideoWidth(VideoObject video, Page page) {
        int index = 0;
        double width = 0;
        
        for(PageObject p : page.pageObjects) {            
            if(p.getType() == PageObjectType.VIDEO) {                
                if(p == video) {                                        
                    width = videoHandler.getVideoWidth(index);
                }
                
                index++;
            }
        }
        
        return width;
    }
    
    public double getVideoHeight(VideoObject video, Page page) {
        int index = 0;
        double height = 0;
        
        for(PageObject p : page.pageObjects) {            
            if(p.getType() == PageObjectType.VIDEO) {                
                if(p == video) {                                        
                    height = videoHandler.getVideoHeight(index);
                }
                
                index++;
            }
        }
        
        return height;
    }
    
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
    
    public double getObjectWidth(PageObject object, Page page, boolean relative) {
        double val = 0.0;
        
        switch(object.getType()) {
        case ANSWER_BOX:
            val = getAnswerBoxWidth((AnswerBoxObject)object, page);
            break;
        case AUDIO:
            val = getAudioWidth((AudioObject)object, page);
            break;
        case GRAPHIC:
            GraphicObject graphic = (GraphicObject)object;
            val = (graphic.getXEnd() - graphic.getXStart()) * bounds.getMaxX();
            break;
        case IMAGE:
            ImageObject image = (ImageObject)object;
            val = (image.getXEnd() - image.getXStart()) * bounds.getMaxX(); 
            break;
        case MULTIPLE_CHOICE:
            val = bounds.getMaxX() * 0.05;
            break;
        case TEXT:
            TextObject text = (TextObject)object;
            val = (text.getXEnd() - text.getXStart()) * bounds.getMaxX(); 
            break;
        case VIDEO:
            val = getVideoWidth((VideoObject)object, page);
            break;
        default:
            return 0.0;
        }
        
        if(relative) {
            return val / bounds.getMaxX();
        } else {
            return val;
        }
    }
    
    public double getObjectHeight(PageObject object, Page page, boolean relative) {
        double val = 0.0;
        
        switch(object.getType()) {
        case ANSWER_BOX:
            val = getAnswerBoxHeight((AnswerBoxObject)object, page);
            break;
        case AUDIO:
            val = getAudioHeight((AudioObject)object, page);
            break;
        case GRAPHIC:
            GraphicObject graphic = (GraphicObject)object;
            val = (graphic.getYEnd() - graphic.getYStart()) * bounds.getMaxY(); 
            break;
        case IMAGE:
            ImageObject image = (ImageObject)object;
            val = (image.getYEnd() - image.getYStart()) * bounds.getMaxY(); 
            break;
        case MULTIPLE_CHOICE:
            val = bounds.getMaxY() * 0.05;
            break;
        case TEXT:
            TextObject text = (TextObject)object;
            val = (text.getYEnd() - text.getYStart()) * bounds.getMaxY(); 
            break;
        case VIDEO:
            val = getVideoHeight((VideoObject)object, page);
            break;
        default:
            return 0.0;
        }
        
        if(relative) {
            return val / bounds.getMaxY();
        } else {
            return val;
        }
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