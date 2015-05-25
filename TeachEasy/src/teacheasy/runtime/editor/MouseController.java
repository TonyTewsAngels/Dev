package teacheasy.runtime.editor;

import teacheasy.data.GraphicObject;
import teacheasy.data.Page;
import teacheasy.data.PageObject;
import teacheasy.data.PageObject.PageObjectType;

public class MouseController {
    private boolean objectGrab;
    
    public MouseController() {
        objectGrab = false;
    }
    
    public void mousePressed(Page page, PropertiesPane propertiesPane, float relX, float relY) {
        PageObject returnObject = null;
        
        for(int i = page.pageObjects.size()-1; i >= 0; i--) {
            PageObject object = page.pageObjects.get(i);
            
            if(relX >= object.getXStart() && relX <= object.getXStart() + 0.1 &&
               relY >= object.getYStart() && relY <= object.getYStart() + 0.1) {
                returnObject = object;
                
                if(returnObject.equals(propertiesPane.getSelectedObject())) {
                    System.out.println("Grab");
                    objectGrab = true;
                } else {
                    objectGrab = false;
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
            
            if(propertiesPane.getSelectedObject().getType() == PageObjectType.GRAPHIC) {
                GraphicObject graphic = (GraphicObject)propertiesPane.getSelectedObject();
                
                graphic.setXEnd(graphic.getXEnd() - graphic.getXStart() + relX);
                graphic.setYEnd(graphic.getYEnd() - graphic.getYStart() + relY);
            }
            
            propertiesPane.getSelectedObject().setXStart(relX);
            propertiesPane.getSelectedObject().setYStart(relY);
            
            
            
            propertiesPane.update(page, propertiesPane.getSelectedObject());
            
            return true;
        }
        
        return false;
    }
}
