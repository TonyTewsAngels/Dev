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
import teacheasy.data.PageObject;
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
        
        VideoObject video = new VideoObject(0.0f, 0.0f, 1.0f, filePath, false, false);
        
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
        
        AudioObject audio = new AudioObject(0.0f, 0.0f, 0.35f, filePath, 0.0f, true, false, false);
        
        page.pageObjects.add(audio);
    }
    
    /**
     * Add a new text box to a page.
     * 
     * @param page The page to add the text box to.
     */
    public static void addTextObject(Page page) {
        TextObject text = new TextObject(0.0f, 0.0f, 0.25f, 0.1f, "Arial", 20, "#ff000000", null, 0.0f, 0.0f);
        
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
        AnswerBoxObject answerBox = new AnswerBoxObject(0.0f, 0.0f, 12, 1, true, false, 0.0f, 0.0f, "Answer 1", "Answer 2");
        
        page.pageObjects.add(answerBox);
    }
    
    /**
     * Add a graphic to a page.
     * 
     * @param page The page to add the graphic to.
     */
    public static void addGraphicObject(Page page) {
        GraphicObject graphic = new GraphicObject(GraphicType.OVAL, 0.0f, 0.0f, 0.1f, 0.1f, 0.0f, "#ff000000", true, "#ff000000", 0.5f, false, 0.0f, 0.0f);
        
        page.pageObjects.add(graphic);
    }
    
    /**
     * 'Paste' a copy of an object on a page.
     * 
     * @param page The page to paste the object on.
     * @param object The object to copy
     */
    public static void copyObject(Page page, PageObject object) {
        switch(object.getType()) {
            case ANSWER_BOX:
                copyAnswerBoxObject(page, (AnswerBoxObject)object);
                break;
            case AUDIO:
                copyAudioObject(page, (AudioObject)object);
                break;
            case GRAPHIC:
                copyGraphicObject(page, (GraphicObject)object);
                break;
            case IMAGE:
                copyImageObject(page, (ImageObject)object);
                break;
            case MULTIPLE_CHOICE:
                copyMultipleChoiceObject(page, (MultipleChoiceObject)object);
                break;
            case TEXT:
                copyTextObject(page, (TextObject)object);
                break;
            case VIDEO:
                copyVideoObject(page, (VideoObject)object);
                break;
            default:
                break;
        }
    }
    
    /**
     * Copy an image object.
     * 
     * @param page The page to copy the image onto.
     * @param image The image object
     */
    public static void copyImageObject(Page page, ImageObject image) {        
        ImageObject newImage = new ImageObject(image.getXStart(),
                                               image.getYStart(),
                                               image.getXEnd(),
                                               image.getYEnd(),
                                               image.getSourcefile(),
                                               image.getxScaleFactor(),
                                               image.getyScaleFactor(),
                                               image.getRotation(),
                                               image.getStartTime(),
                                               image.getDuration());
        
        page.pageObjects.add(newImage);
    }
    
    /**
     * Copy a video object.
     * 
     * @param page The page to copy the video onto.
     * @param video The video to copy.
     */
    public static void copyVideoObject(Page page, VideoObject video) {        
        VideoObject newVideo = new VideoObject(video.getXStart(),
                                               video.getYStart(),
                                               video.getXEnd(),
                                               video.getSourcefile(),
                                               video.isAutoPlay(),
                                               video.isLoop());
        
        page.pageObjects.add(newVideo);
    }
    
    /**
     * Copy an audio clip object.
     * 
     * @param page The page to copy the audio onto.
     * @param audio The audio object to copy.
     */
    public static void copyAudioObject(Page page, AudioObject audio) {
        AudioObject newAudio = new AudioObject(audio.getXStart(),
                                               audio.getYStart(),
                                               audio.getXEnd(),
                                               audio.getSourcefile(),
                                               audio.getStartTime(),
                                               audio.isViewProgress(),
                                               audio.isAutoPlay(),
                                               audio.isLoop());
        
        page.pageObjects.add(newAudio);
    }
    
    /**
     * Copy a text box.
     * 
     * @param page The page to add copy text box onto.
     * @text The text box object to copy.
     */
    public static void copyTextObject(Page page, TextObject text) {
        TextObject newText = new TextObject(text.getXStart(),
                                            text.getYStart(),
                                            text.getXEnd(),
                                            text.getYEnd(),
                                            text.getFont(),
                                            text.getFontSize(),
                                            text.getColor(),
                                            text.getSourceFile(),
                                            text.getStartTime(),
                                            text.getDuration());
        
        for(int i = 0; i < text.textFragments.size(); i++) {
            RichText frag = text.textFragments.get(i);
            
            newText.textFragments.add(new RichText(frag.getText(),
                                                   frag.getFont(),
                                                   frag.getFontSize(),
                                                   frag.getColor(),
                                                   frag.getSettings().toArray(new String[frag.getSettings().size()])));
        }
        
        page.pageObjects.add(newText);
    }
    
    /**
     * Copy a multiple choice question.
     * 
     * @param page The page to copy the multiple choice object onto.
     * @param mchoice The multiple choice object to copy.
     */
    public static void copyMultipleChoiceObject(Page page, MultipleChoiceObject mChoice) {
        MultipleChoiceObject newMChoice = new MultipleChoiceObject(mChoice.getXStart(),
                                                                   mChoice.getYStart(),
                                                                   mChoice.getOrientation(),
                                                                   mChoice.getMultiChoiceType(),
                                                                   mChoice.getMarks(),
                                                                   mChoice.isRetry());
        
        for(int i = 0; i < mChoice.getAnswers().size(); i++) {
            Answer answer = mChoice.getAnswers().get(i);
            newMChoice.addAnswer(new Answer(answer.getText(), answer.isCorrect()));
        }
        
        page.pageObjects.add(newMChoice);
    }
    
    /**
     * Copy an answer box onto a page.
     * 
     * @param page The page to copy the answer box onto.
     * @param answerBox The answerbox to copy.
     */
    public static void copyAnswerBoxObject(Page page, AnswerBoxObject answerBox) {
        AnswerBoxObject newAnswerBox = new AnswerBoxObject(answerBox.getXStart(),
                                                           answerBox.getYStart(),
                                                           answerBox.getCharacterLimit(),
                                                           answerBox.getMarks(),
                                                           answerBox.isRetry(),
                                                           answerBox.isNumerical(),
                                                           answerBox.getUpperBound(),
                                                           answerBox.getLowerBound());
        
        page.pageObjects.add(newAnswerBox);
    }
    
    /**
     * Copy a graphic onto a page.
     * 
     * @param page The page to copy the graphic onto.
     * @param graphic The grpahic to copy.
     */
    public static void copyGraphicObject(Page page, GraphicObject graphic) {
        GraphicObject newGraphic = new GraphicObject(graphic.getGraphicType(),
                                                     graphic.getXStart(),
                                                     graphic.getYStart(),
                                                     graphic.getXEnd(),
                                                     graphic.getYEnd(),
                                                     graphic.getRotation(),
                                                     graphic.getGraphicColour(),
                                                     graphic.isSolid(),
                                                     graphic.getLineColor(),
                                                     graphic.getOutlineThickness(),
                                                     graphic.isShadow(),
                                                     graphic.getStartTime(),
                                                     graphic.getDuration());
        
        page.pageObjects.add(newGraphic);
    }
}
