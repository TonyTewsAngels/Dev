package teacheasy.runtime.editor;

import teacheasy.data.GraphicObject;
import teacheasy.data.ImageObject;
import teacheasy.data.Page;
import teacheasy.data.PageObject;
import teacheasy.data.PageObject.PageObjectType;
import teacheasy.data.TextObject;
import teacheasy.data.VideoObject;

public class MouseController {
    private boolean objectGrab;
    private float xOffSet;
    private float yOffSet;
    
    public MouseController() {
        objectGrab = false;
        xOffSet = 0.0f;
        yOffSet = 0.0f;
    }
    
    public void mousePressed(Page page, PropertiesPane propertiesPane, float relX, float relY, boolean onGroup) {
        if(!onGroup) {
            objectGrab = false;
            return;
        }
        
        PageObject returnObject = null;
        
        for(int i = page.pageObjects.size()-1; i >= 0; i--) {
            PageObject object = page.pageObjects.get(i);
            
            if(relX >= object.getXStart() && relX <= object.getXStart() + 0.1 &&
               relY >= object.getYStart() && relY <= object.getYStart() + 0.1) {
                returnObject = object;
                
                if(returnObject.equals(propertiesPane.getSelectedObject())) {
                    System.out.println("Grab");
                    objectGrab = true;
                    xOffSet = relX - propertiesPane.getSelectedObject().getXStart();
                    yOffSet = relY - propertiesPane.getSelectedObject().getYStart();
                } else {
                    objectGrab = false;
                    xOffSet = 0.0f;
                    yOffSet = 0.0f;
                }
                
                break;
            } else {
                objectGrab = false;
            }
        }

        propertiesPane.update(page, returnObject);
    }
    
    public boolean mouseReleased(Page page, PropertiesPane propertiesPane, float relX, float relY, boolean onGroup) {        
        if(!onGroup) {
            objectGrab = false;
        }
        
        if(objectGrab) {
            System.out.println("Drop");
            
            if(Math.abs((relX - xOffSet) - (propertiesPane.getSelectedObject().getXStart())) < 0.005 &&
               Math.abs((relY - yOffSet) - (propertiesPane.getSelectedObject().getYStart())) < 0.005) {
                return false;
            }
            
            if(propertiesPane.getSelectedObject().getType() == PageObjectType.GRAPHIC) {
                GraphicObject graphic = (GraphicObject)propertiesPane.getSelectedObject();
                
                graphic.setXEnd(graphic.getXEnd() - graphic.getXStart() + relX - xOffSet);
                graphic.setYEnd(graphic.getYEnd() - graphic.getYStart() + relY - yOffSet);
                
                if(graphic.getXEnd() > 1.0f) {
                    graphic.setXEnd(1.0f);
                }
                
                if(graphic.getYEnd() > 1.0f) {
                    graphic.setYEnd(1.0f);
                }
            } else if(propertiesPane.getSelectedObject().getType() == PageObjectType.VIDEO) {
                VideoObject video = (VideoObject)propertiesPane.getSelectedObject();
                
                video.setXEnd(video.getXEnd() - video.getXStart() + relX - xOffSet);
                
                if(video.getXEnd() > 1.0f) {
                    video.setXEnd(1.0f);
                }
            } else if(propertiesPane.getSelectedObject().getType() == PageObjectType.TEXT) {
                TextObject text = (TextObject)propertiesPane.getSelectedObject();
                
                text.setXEnd(text.getXEnd() - text.getXStart() + relX - xOffSet);
                text.setYEnd(text.getYEnd() - text.getYStart() + relY - yOffSet);
                
                if(text.getXEnd() > 1.0f) {
                    text.setXEnd(1.0f);
                }
                
                if(text.getYEnd() > 1.0f) {
                    text.setYEnd(1.0f);
                }
            } else if(propertiesPane.getSelectedObject().getType() == PageObjectType.IMAGE) {
                ImageObject image = (ImageObject)propertiesPane.getSelectedObject();
                
                image.setXEnd(image.getXEnd() - image.getXStart() + relX - xOffSet);
                image.setYEnd(image.getYEnd() - image.getYStart() + relY - yOffSet);
                
                if(image.getXEnd() > 1.0f) {
                    image.setXEnd(1.0f);
                }
                
                if(image.getYEnd() > 1.0f) {
                    image.setYEnd(1.0f);
                }
            }
            
            propertiesPane.getSelectedObject().setXStart(relX - xOffSet);
            propertiesPane.getSelectedObject().setYStart(relY - yOffSet);
            
            if(propertiesPane.getSelectedObject().getXStart() < 0.0f) {
                propertiesPane.getSelectedObject().setXStart(0.0f);
            }
            
            if(propertiesPane.getSelectedObject().getYStart() < 0.0f) {
                propertiesPane.getSelectedObject().setYStart(0.0f);
            }
            
            propertiesPane.update(page, propertiesPane.getSelectedObject());
            
            return true;
        }
        
        return false;
    }
}
