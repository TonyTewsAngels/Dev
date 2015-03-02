/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.render;

import java.util.ArrayList;

import teacheasy.data.GraphicObject;
import teacheasy.data.ImageObject;
import teacheasy.data.Lesson;
import teacheasy.data.Page;
import teacheasy.data.PageObject;
import teacheasy.data.RichText;
import teacheasy.data.TextObject;
import teacheasy.data.VideoObject;
import teacheasy.mediahandler.ImageHandler;
import teacheasy.mediahandler.VideoHandler;
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
    }
    
    /** Render an individual page */
    public void renderPage(Page page) {
        /* Clear the page */
        group.getChildren().removeAll();
        
        /* Add the background */
        Rectangle bg = new Rectangle(bounds.getMaxX(), bounds.getMaxY() - 100, Color.WHITE);
        group.getChildren().add(bg);
        
        /* Loop through all the objects on the page */
        for(int i = 0; i < page.pageObjects.size(); i++) {
            /* Get the object */
            PageObject pageObject = page.pageObjects.get(i);
            
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
                case GRAPHIC:
                    renderGraphic((GraphicObject) pageObject);
                    break;
                default:
                    break;
            }
        }
    }
    
    /** Render the screen if no lesson is loaded */
    public void renderUnLoaded() {
        /* Clear the page */
        group.getChildren().removeAll();
        
        /* Add the background */
        Rectangle bg = new Rectangle(bounds.getMaxX(), bounds.getMaxY() - 100, Color.WHITE);
        group.getChildren().add(bg);
        
        /* Add the help message */
        Text text = new Text("Open a lesson to begin!");
        text.relocate(10, 10);
        group.getChildren().add(text);
    }
    
    /** Render a text box on a page */
    private void renderText(TextObject text) {
        textHandler.clearBuffer();
        
        for(int i = 0; i < text.textFragments.size(); i++) {
           RichText richText = text.textFragments.get(i);
           
           ArrayList<TextAttribute> attrs = new ArrayList<TextAttribute>();
           if(richText.isBold()) attrs.add(TextAttribute.BOLD);
           if(richText.isItalic()) attrs.add(TextAttribute.ITALIC);
           if(richText.isUnderline()) attrs.add(TextAttribute.UNDERLINE);
           if(richText.isSubscript()) attrs.add(TextAttribute.SUBSCRIPT);
           if(richText.isSuperscript()) attrs.add(TextAttribute.SUPERSCRIPT);
           if(richText.isStrikethrough()) attrs.add(TextAttribute.STRIKETHROUGH);         
           
           textHandler.addStringToBuffer(richText.getText(),
                                         richText.getFont(),
                                         richText.getFontSize(),
                                         richText.getColor(),
                                         "#00000000",
                                         attrs.toArray(new TextAttribute[attrs.size()])); 
        }
        
        textHandler.drawBuffer((int)(bounds.getMaxX() * text.getXStart()),
                               (int)(bounds.getMaxY() * text.getYStart()),
                               500,
                               500,
                               "#00000000",
                               Alignment.JUSTIFY);
        
    }
    
    /** Render a video on a page */
    private void renderVideo(VideoObject video) {
        videoHandler.createVideo(bounds.getMaxX() * video.getXStart(),
                                 bounds.getMaxY() * video.getYStart(),
                                 500,
                                 video.getSourcefile(),
                                 false,
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
        switch(graphic.getGraphicType()) {
            case OVAL:
                graphicsHandler.drawOval((float)(bounds.getMaxX() * graphic.getXStart()),
                                         (float)(bounds.getMaxY() * graphic.getYStart()), 
                                         (float)(bounds.getMaxX() * graphic.getXEnd()), 
                                         (float)(bounds.getMaxY() * graphic.getYEnd()), 
                                         new Color(1.0, 0.0, 1.0, 1.0), 
                                         graphic.isSolid(), 
                                         new Color(0.0, 0.0, 0.0, 0.0),
                                         graphic.getOutlineThickness(), 
                                         Shadow.NONE, 
                                         graphic.getRotation(), 
                                         Shading.NONE);
                break;
            default:
                break;
        }
    }
}
