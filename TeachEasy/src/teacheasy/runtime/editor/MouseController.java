package teacheasy.runtime.editor;

import teacheasy.data.GraphicObject;
import teacheasy.data.Page;
import teacheasy.data.PageObject;
import teacheasy.data.PageObject.PageObjectType;

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
            }
            
            propertiesPane.getSelectedObject().setXStart(relX - xOffSet);
            propertiesPane.getSelectedObject().setYStart(relY - yOffSet);
            
            
            
            propertiesPane.update(page, propertiesPane.getSelectedObject());
            
            return true;
        }
        
        return false;
    }
}
