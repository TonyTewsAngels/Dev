package teacheasy.runtime.editor;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import teacheasy.data.AnswerBoxObject;
import teacheasy.data.AudioObject;
import teacheasy.data.GraphicObject;
import teacheasy.data.ImageObject;
import teacheasy.data.MultipleChoiceObject;
import teacheasy.data.MultipleChoiceObject.MultiChoiceType;
import teacheasy.data.Page;
import teacheasy.data.MultipleChoiceObject.Orientation;
import teacheasy.data.PageObject.PageObjectType;
import teacheasy.data.RichText;
import teacheasy.data.TextObject;
import teacheasy.data.VideoObject;
import teacheasy.data.multichoice.Answer;
import wavemedia.graphic.GraphicType;

/**
 * Includes static methods for adding objects to a given page.
 * 
 * @author Alistair Jewers
 * @version 1.0 09 May 2015
 *
 */
public class NewObjectController {
    /**
     * Add an object to a page.
     * 
     * @param page The page to add the object to.
     * @param type The type of the object to add.
     */
    public static void addObject(Page page, PageObjectType type) {
        switch(type) {
            case ANSWER_BOX:
                addAnswerBoxObject(page);
                break;
            case AUDIO:
                addAudioObject(page);
                break;
            case GRAPHIC:
                addGraphicObject(page);
                break;
            case IMAGE:
                addImageObject(page);
                break;
            case MULTIPLE_CHOICE:
                addMultipleChoiceObject(page);
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
    
    /**
     * Add an image to a page by selecting a file.
     * 
     * @param page The page to add the image to.
     */
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
    
    /**
     * Add a video to a page by selecting a file.
     * 
     * @param page The page to add the video to.
     */
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
    
    /**
     * Add an audio clip to a page by selecting a file.
     * 
     * @param page The page to add the clip to.
     */
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
    
    /**
     * Add a new text box to a page.
     * 
     * @param page The page to add the text box to.
     */
    public static void addTextObject(Page page) {
        TextObject text = new TextObject(0.0f, 0.0f, 1.0f, 1.0f, "Arial", 20, "#ff000000", null, 0.0f, 0.0f);
        
        text.textFragments.add(new RichText("Enter Text Here", text.getFont(), text.getFontSize(), text.getColor()));
        
        page.pageObjects.add(text);
    }
    
    /**
     * Add a new multiple choice question to a page.
     * 
     * @param page The page to add the multiple choice object to.
     */
    public static void addMultipleChoiceObject(Page page) {
        MultipleChoiceObject multipleChoice = new MultipleChoiceObject(0.0f, 0.0f, Orientation.VERTICAL, MultiChoiceType.CHECKBOX, 1, true);
        
        multipleChoice.addAnswer(new Answer("New Answer", true));
        
        page.pageObjects.add(multipleChoice);
    }
    
    /**
     * Add an answer box to a page.
     * 
     * @param page The page to add the answer box to.
     */
    public static void addAnswerBoxObject(Page page) {
        AnswerBoxObject answerBox = new AnswerBoxObject(0.0f, 0.0f, 12, 1, "answer1~answer2", true, false);
        
        page.pageObjects.add(answerBox);
    }
    
    /**
     * Add a graphic to a page.
     * 
     * @param page The page to add the graphic to.
     */
    public static void addGraphicObject(Page page) {
        GraphicObject graphic = new GraphicObject(GraphicType.OVAL, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, "#ff000000", true, "#ff000000", 2, false, 0.0f, 0.0f);
        
        page.pageObjects.add(graphic);
    }
}
