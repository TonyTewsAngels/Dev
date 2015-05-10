package teacheasy.runtime.editor;

import teacheasy.data.Page;
import teacheasy.data.PageObject;

public class MouseController {
    public static void mousePressed(Page page, PropertiesPane propertiesPane, float relX, float relY) {
        for(int i = page.pageObjects.size()-1; i >= 0; i--) {
            PageObject object = page.pageObjects.get(i);
            
            if(relX >= object.getXStart() && relX <= object.getXStart() + 0.1 &&
               relY >= object.getYStart() && relY <= object.getYStart() + 0.1) {
                propertiesPane.update(page, object);
                return;
            }
        }
        
        propertiesPane.update(page, null);
    }
}
