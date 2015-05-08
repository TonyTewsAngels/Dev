package teacheasy.runtime.editor;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import teacheasy.data.Page;
import teacheasy.data.PageObject.PageObjectType;

public class NewObjectController {
    public static void addObject(Page page, PageObjectType type) {
        switch(type) {
            case ANSWER_BOX:
                
                break;
            case AUDIO:
                break;
            case BUTTON:
                break;
            case GRAPHIC:
                break;
            case IMAGE:
                addImageObject(page);
                break;
            case MULTIPLE_CHOICE:
                break;
            case TEXT:
                break;
            case VIDEO:
                break;
            default:
                break;
        }
    }
    
    public static void addImageObject(Page page) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open Image");
        fc.showOpenDialog(new Stage());
        
        
    }
}
