package teacheasy.runtime.editor;

import teacheasy.data.ImageObject;
import teacheasy.data.Lesson;
import teacheasy.data.MultipleChoiceObject;
import teacheasy.data.Page;
import teacheasy.data.RichText;
import teacheasy.data.TextObject;
import teacheasy.data.MultipleChoiceObject.MultiChoiceType;
import teacheasy.data.MultipleChoiceObject.Orientation;
import teacheasy.data.lessondata.LessonDefaultSettings;

public class TemplateController {
	
	public static enum TemplateType {
		INFORMATION,
		MCQUIZ,
		ABQUIZ,
		VIDEO
	}

	
	
	public void ApplyTemplate(Page page, LessonDefaultSettings defaults, TemplateType templateType) {
		
		switch(templateType) {
			case INFORMATION:
				ApplyInformationTemplate(page, defaults);
	            break;
	        case MCQUIZ:
	        	ApplyMCQuizTemplate(page, defaults);
	            break;
	        case ABQUIZ:
	        	ApplyABQuizTemplate(page, defaults);
	            break;
	        case VIDEO:
	        	ApplyVideoTemplate(page, defaults);
	            break;
	        default:
	            break;
		}
	}
	
	
	/**
	 * public TextObject(float nXStart, float nYStart, float nXEnd, float nYEnd,  
                      String nFont, int nFontSize, String nColor, String nSourceFile,
                      float nDuration, float nStartTime)
                      
	 * public MultipleChoiceObject(float nXStart, float nYStart, 
                                Orientation nOrientation,
                                MultiChoiceType nType,
                                int nMarks, boolean nRetry)
     *
     *                           
     *         
     *                       
     *                       
     *                       
     *                       
     *                       
     *                       
	 */
	
	
	public void ApplyInformationTemplate(Page page, LessonDefaultSettings defaults) {
		
		// Add Title text object to page (Top Centre)
		TextObject title = new TextObject(0.2f, 0.08f, 0.8f, 0.15f, defaults.getFont(), defaults.getFontSize()+10, defaults.getFontColour(), "null", 0f, 0f);
		RichText titleText = new RichText("Insert Title Here", title.getFont(), title.getFontSize(), title.getColor(), "bold", "underline");
		title.textFragments.add(titleText);
		page.addObject(title);
		
		// Add Image object to page (Bottom Left)
		ImageObject image = new ImageObject(0.1f, 0.25f, 0.45f, 0.9f, "Question-mark-scratch-head.jpg", 0f, 0f, 0f, 0f, 0f);
		page.addObject(image);
		
		// Add Information text object to page (Bottom Right)
		TextObject info = new TextObject(0.55f, 0.25f, 0.9f, 0.9f, defaults.getFont(), defaults.getFontSize(), defaults.getFontColour(), "null", 0f, 0f);
		RichText infoText = new RichText("Insert Information Here", info.getFont(), info.getFontSize(), info.getColor());
		info.textFragments.add(infoText);
		page.addObject(info);
		
	}
	
	public void ApplyMCQuizTemplate(Page page, LessonDefaultSettings defaults) {
		
		// Add Title text object to page (Top Centre)
		TextObject title = new TextObject(0.25f, 0.08f, 0.8f, 0.15f, defaults.getFont(), defaults.getFontSize()+10, defaults.getFontColour(), "null", 0f, 0f);
		RichText titleText = new RichText("Insert Title Here", title.getFont(), title.getFontSize(), title.getColor(), "bold", "underline");
		title.textFragments.add(titleText);
		page.addObject(title);
		
		//Add Question 1 (Top Left)
		TextObject question1 = new TextObject(0.1f, 0.25f, 0.45f, 0.3f, defaults.getFont(), defaults.getFontSize(), defaults.getFontColour(), "null", 0f, 0f);
		RichText question1Text = new RichText("Question 1", question1.getFont(), question1.getFontSize(), question1.getColor());
		question1.textFragments.add(question1Text);
		page.addObject(question1);
		
		//Add Multiple Choice Answer 1 (Top Left)
		MultipleChoiceObject multipleChoice1 = new MultipleChoiceObject(0.1f, 0.3f, Orientation.HORIZONTAL, MultiChoiceType.CHECKBOX, 1, true);
		page.addObject(multipleChoice1);
		
		//Add Question 2 (Top Right)
		TextObject question2 = new TextObject(0.55f, 0.25f, 0.9f, 0.3f, defaults.getFont(), defaults.getFontSize(), defaults.getFontColour(), "null", 0f, 0f);
		RichText question2Text = new RichText("Question 1", question2.getFont(), question2.getFontSize(), question2.getColor());
		question2.textFragments.add(question2Text);
		page.addObject(question2);
				
		//Add Multiple Choice Answer 2 (Top Right)
		MultipleChoiceObject multipleChoice2 = new MultipleChoiceObject(0.55f, 0.3f, Orientation.HORIZONTAL, MultiChoiceType.CHECKBOX, 1, true);
		page.addObject(multipleChoice2);
		
		//Add Question 3 (Bottom Left)
		TextObject question3 = new TextObject(0.1f, 0.575f, 0.45f, 0.625f, defaults.getFont(), defaults.getFontSize(), defaults.getFontColour(), "null", 0f, 0f);
		RichText question3Text = new RichText("Question 1", question3.getFont(), question3.getFontSize(), question3.getColor());
		question3.textFragments.add(question3Text);
		page.addObject(question3);
				
		//Add Multiple Choice Answer 3 (Bottom Left)
		MultipleChoiceObject multipleChoice3 = new MultipleChoiceObject(0.1f, 0.625f, Orientation.HORIZONTAL, MultiChoiceType.CHECKBOX, 1, true);
		page.addObject(multipleChoice3);
		
		//Add Question 4 (Bottom Right)
		TextObject question4 = new TextObject(0.55f, 0.575f, 0.9f, 0.625f, defaults.getFont(), defaults.getFontSize(), defaults.getFontColour(), "null", 0f, 0f);
		RichText question4Text = new RichText("Question 1", question4.getFont(), question4.getFontSize(), question4.getColor());
		question4.textFragments.add(question4Text);
		page.addObject(question4);
				
		//Add Multiple Choice Answer 4 (Bottom Right)
		MultipleChoiceObject multipleChoice4 = new MultipleChoiceObject(0.55f, 0.625f, Orientation.HORIZONTAL, MultiChoiceType.CHECKBOX, 1, true);
		page.addObject(multipleChoice4);
	}
	
	public void ApplyABQuizTemplate(Page page, LessonDefaultSettings defaults) {
		
	}
	
	public void ApplyVideoTemplate(Page page, LessonDefaultSettings defaults) {
	
	}
	
	
	
	
	
}
