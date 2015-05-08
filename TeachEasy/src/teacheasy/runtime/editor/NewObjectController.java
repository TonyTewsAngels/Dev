package teacheasy.runtime.editor;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import teacheasy.data.AudioObject;
import teacheasy.data.ImageObject;
import teacheasy.data.Page;
import teacheasy.data.PageObject.PageObjectType;
import teacheasy.data.RichText;
import teacheasy.data.TextObject;
import teacheasy.data.VideoObject;

public class NewObjectController {
    public static void addObject(Page page, PageObjectType type) {
        switch(type) {
            case ANSWER_BOX:
                
                break;
            case AUDIO:
                addAudioObject(page);
                break;
            case GRAPHIC:
                break;
            case IMAGE:
                addImageObject(page);
                break;
            case MULTIPLE_CHOICE:
                break;
            case TEXT:
                addTextObject(page);
                break;
            case VIDEO:
                addVideoObject(page);
                break;
            default:
                break;
        }
    }
    
    public static void addImageObject(Page page) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open Image");
        fc.getExtensionFilters().add(new ExtensionFilter( "Images", "*.png", "*.jpg", "*.jpeg", "*.JPG", "*.gif", "*.bmp"));
        
        File imageFile = fc.showOpenDialog(new Stage());
        
        if(imageFile == null) {
            return;
        }
        
        String filePath = imageFile.getAbsolutePath();
        
        ImageObject image = new ImageObject(0.0f, 0.0f, 1.0f, 1.0f, filePath, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f);
        
        page.pageObjects.add(image);
    }
    
    public static void addVideoObject(Page page) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open Video");
        fc.getExtensionFilters().add(new ExtensionFilter( "Videos", "*.mp4", "*.flv"));
        
        File videoFile = fc.showOpenDialog(new Stage());
        
        if(videoFile == null) {
            return;
        }
        
        String filePath = videoFile.getAbsolutePath();
        
        VideoObject video = new VideoObject(0.0f, 0.0f, 1.0f, filePath);
        
        page.pageObjects.add(video);
    }
    
    public static void addAudioObject(Page page) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open Audio");
        fc.getExtensionFilters().add(new ExtensionFilter( "Audio", "*.wav", "*.mp3"));
        
        File audioFile = fc.showOpenDialog(new Stage());
        
        if(audioFile == null) {
            return;
        }
        
        String filePath = audioFile.getAbsolutePath();
        
        AudioObject audio = new AudioObject(0.0f, 0.0f, true, filePath);
        
        page.pageObjects.add(audio);
    }
    
    public static void addTextObject(Page page) {
        TextObject text = new TextObject(0.0f, 0.0f, 1.0f, 1.0f, "Arial", 20, "#ff000000", null, 0.0f, 0.0f);
        
        text.textFragments.add(new RichText("Enter Text Here", text.getFont(), text.getFontSize(), text.getColor()));
        
        page.pageObjects.add(text);
    }
}
