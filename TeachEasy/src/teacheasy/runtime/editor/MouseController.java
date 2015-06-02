/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved. 
 */
package teacheasy.runtime.editor;

import teacheasy.data.GraphicObject;
import teacheasy.data.ImageObject;
import teacheasy.data.Page;
import teacheasy.data.PageObject;
import teacheasy.data.PageObject.PageObjectType;
import teacheasy.data.TextObject;
import teacheasy.data.VideoObject;
import teacheasy.render.Renderer;

/**
 * Contains methods relating to mouse control.
 * 
 * @author Alistair Jewers
 * @version 1.0 15 May 2015
 */
public class MouseController {
    /** Whether an object is being dragged or not */
    private boolean objectGrab;
    
    /* Offsets */
    private float xOffSet;
    private float yOffSet;
    
    /** Reference to the render */
    private Renderer renderer;
    
    /**
     * Constructor.
     * 
     * @param nRenderer Renderer reference.
     */
    public MouseController(Renderer nRenderer) {
        /* Initialise variables */
        objectGrab = false;
        xOffSet = 0.0f;
        yOffSet = 0.0f;
        this.renderer = nRenderer;
    }
    
    /**
     * Called when the mouse is pressed.
     * 
     * @param page The current page.
     * @param propertiesPane The properties pane reference.
     * @param relX The relative X position of the mouse press.
     * @param relY The relative Y position of the mouse press.
     * @param onGroup Indicates if the click was on the page area or not.
     */
    public void mousePressed(Page page, PropertiesPane propertiesPane, float relX, float relY, boolean onGroup) {
        /* If the click was not in the page area */
        if(!onGroup) {
            /* Drop any grabbed object */
            objectGrab = false;
            
            /* Return immediately */
            return;
        }
        
        /* Temporary object variable */
        PageObject returnObject = null;
        
        /* Loop through every object on the page backwards (going down layers) */
        for(int i = page.pageObjects.size()-1; i >= 0; i--) {
            /* Get the object */
            PageObject object = page.pageObjects.get(i);
            
            /* Check if the click is within the objects bounding box */
            if(relX >= object.getXStart() && relX <= object.getXStart() + renderer.getObjectWidth(object, page, true) &&
               relY >= object.getYStart() && relY <= object.getYStart() + renderer.getObjectHeight(object, page, true)) {
                
                /* Set this object for return */
                returnObject = object;
                
                /* If the object was previously select it, grab it to be dragged and take the offset */
                if(returnObject.equals(propertiesPane.getSelectedObject())) {
                    objectGrab = true;
                    xOffSet = relX - propertiesPane.getSelectedObject().getXStart();
                    yOffSet = relY - propertiesPane.getSelectedObject().getYStart();
                } else {
                    /* Else drop the grab and reset the offset */
                    objectGrab = false;
                    xOffSet = 0.0f;
                    yOffSet = 0.0f;
                }
                
                /* Break out of the loop */
                break;
            } else {
                /* Drop the grab */
                objectGrab = false;
            }
        }
        
        /* Update the properties pane */
        propertiesPane.update(page, returnObject);
    }
    
    /**
     * Called when the mouse is released.
     * 
     * @param page The current page.
     * @param propertiesPane The properties pane reference.
     * @param relX The relative X position of the mouse press.
     * @param relY The relative Y position of the mouse press.
     * @param onGroup Indicates if the click was on the page area or not.
     * @return True if an object was moved.
     */
    public boolean mouseReleased(Page page, PropertiesPane propertiesPane, float relX, float relY, boolean onGroup) {
        /* If the click is not on the page drop the grab */
        if(!onGroup) {
            objectGrab = false;
        }
        
        /* If no object is selected */
        if(propertiesPane.getSelectedObject() == null) {
            /* Return immediately */
            return false;
        }
        
        /* If an object is grabbed */
        if(objectGrab) {
            /* Discount small movements */
            if(Math.abs((relX - xOffSet) - (propertiesPane.getSelectedObject().getXStart())) < 0.005 &&
               Math.abs((relY - yOffSet) - (propertiesPane.getSelectedObject().getYStart())) < 0.005) {
                return false;
            }
            
            /* Check the object type */
            if(propertiesPane.getSelectedObject().getType() == PageObjectType.GRAPHIC) {
                /* Get the graphics object */
                GraphicObject graphic = (GraphicObject)propertiesPane.getSelectedObject();
                
                /* Update the end positions*/
                graphic.setXEnd(graphic.getXEnd() - graphic.getXStart() + relX - xOffSet);
                graphic.setYEnd(graphic.getYEnd() - graphic.getYStart() + relY - yOffSet);
                
                /* Limit to <= 1 */
                if(graphic.getXEnd() > 1.0f) {
                    graphic.setXEnd(1.0f);
                }
                
                if(graphic.getYEnd() > 1.0f) {
                    graphic.setYEnd(1.0f);
                }
            } else if(propertiesPane.getSelectedObject().getType() == PageObjectType.VIDEO) {
                /* Get the video object */
                VideoObject video = (VideoObject)propertiesPane.getSelectedObject();
                
                /* Update the end position */
                video.setXEnd(video.getXEnd() - video.getXStart() + relX - xOffSet);
                
                /* Limit the <= 1*/
                if(video.getXEnd() > 1.0f) {
                    video.setXEnd(1.0f);
                }
            } else if(propertiesPane.getSelectedObject().getType() == PageObjectType.TEXT) {
                /* Get the text object */
                TextObject text = (TextObject)propertiesPane.getSelectedObject();
                
                /* Update the end position */
                text.setXEnd(text.getXEnd() - text.getXStart() + relX - xOffSet);
                text.setYEnd(text.getYEnd() - text.getYStart() + relY - yOffSet);
                
                /* Limit to <= 1 */
                if(text.getXEnd() > 1.0f) {
                    text.setXEnd(1.0f);
                }
                
                if(text.getYEnd() > 1.0f) {
                    text.setYEnd(1.0f);
                }
            } else if(propertiesPane.getSelectedObject().getType() == PageObjectType.IMAGE) {
                /* Get the image object */
                ImageObject image = (ImageObject)propertiesPane.getSelectedObject();
                
                /* Update the end position */
                image.setXEnd(image.getXEnd() - image.getXStart() + relX - xOffSet);
                image.setYEnd(image.getYEnd() - image.getYStart() + relY - yOffSet);
                
                /* Limit to <= 1 */
                if(image.getXEnd() > 1.0f) {
                    image.setXEnd(1.0f);
                }
                
                if(image.getYEnd() > 1.0f) {
                    image.setYEnd(1.0f);
                }
            }
            
            /* Update start position */
            propertiesPane.getSelectedObject().setXStart(relX - xOffSet);
            propertiesPane.getSelectedObject().setYStart(relY - yOffSet);
            
            /* Limit to <= 0*/
            if(propertiesPane.getSelectedObject().getXStart() < 0.0f) {
                propertiesPane.getSelectedObject().setXStart(0.0f);
            }
            
            if(propertiesPane.getSelectedObject().getYStart() < 0.0f) {
                propertiesPane.getSelectedObject().setYStart(0.0f);
            }
            
            /* Update the properties pane */
            propertiesPane.update(page, propertiesPane.getSelectedObject());
            
            /* Return true because an object moved */
            return true;
        }
        
        /* No object moved return false*/
        return false;
    }
    
    /**
     * Called when the mouse has been moved.
     * 
     * @param page The current page.
     * @param relX The relative X position of the mouse.
     * @param relY THe relative Y position of the mouse.
     * @return The page object under the mouse, if there is one, else null.
     */
    public PageObject mouseMoved(Page page, float relX, float relY) {
        /* Temporary return object */
        PageObject returnObject = null;
        
        /* Loop through every object on the page in reverse order (going down layers)*/
        for(int i = page.pageObjects.size()-1; i >= 0; i--) {
            /* Get the object */
            PageObject object = page.pageObjects.get(i);
            
            /* Check if the mouse is within the objects bounding box */
            if(relX >= object.getXStart() && relX <= object.getXStart() + renderer.getObjectWidth(object, page, true) &&
               relY >= object.getYStart() && relY <= object.getYStart() + renderer.getObjectHeight(object, page, true)) {
                /* Set the return object */
                returnObject = object;
                
                /* Escape the loop */
                break;
            }
        }
        
        /* Return the return object */
        return returnObject;
    }
}
