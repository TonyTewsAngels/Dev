/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.render;

import teacheasy.data.ImageObject;
import teacheasy.data.Lesson;
import teacheasy.data.Page;
import teacheasy.data.PageObject;
import teacheasy.data.VideoObject;
import teacheasy.mediahandler.ImageHandler;
import teacheasy.mediahandler.VideoHandler;
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
   
    /** Constructor */
    public Renderer(Group nGroup, Rectangle2D nBounds) {
        /* Set up the group reference */
        this.group = nGroup;
        
        /* Set the bounds of the drawing area */
        this.bounds = nBounds;
        
        /* Instantiate the handlers */
        videoHandler = new VideoHandler(group);
        imageHandler = new ImageHandler(group);
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
                case IMAGE:
                    renderImage((ImageObject) pageObject);
                    break;
                case VIDEO:
                    renderVideo((VideoObject) pageObject);
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
}
