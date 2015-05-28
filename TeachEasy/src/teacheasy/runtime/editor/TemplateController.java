package teacheasy.runtime.editor;

import teacheasy.data.AnswerBoxObject;
import teacheasy.data.AudioObject;
import teacheasy.data.ImageObject;
import teacheasy.data.Lesson;
import teacheasy.data.MultipleChoiceObject;
import teacheasy.data.Page;
import teacheasy.data.RichText;
import teacheasy.data.TextObject;
import teacheasy.data.MultipleChoiceObject.MultiChoiceType;
import teacheasy.data.MultipleChoiceObject.Orientation;
import teacheasy.data.VideoObject;
import teacheasy.data.lessondata.LessonDefaultSettings;
import teacheasy.data.multichoice.Answer;

public class TemplateController {
	
	public static enum TemplateType {
		INFORMATION,
		MCQUIZ,
		ABQUIZ,
		VIDEO,
		AUDIO
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
	        case AUDIO:
	        	ApplyAudioTemplate(page, defaults);
	            break;
	        default:
	            break;
		}
	}
	
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
		TextObject title = new TextObject(0.2f, 0.08f, 0.8f, 0.15f, defaults.getFont(), defaults.getFontSize()+10, defaults.getFontColour(), "null", 0f, 0f);
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
		multipleChoice1.addAnswer(new Answer("New Answer", true));
		page.addObject(multipleChoice1);
		
		//Add Question 2 (Top Right)
		TextObject question2 = new TextObject(0.55f, 0.25f, 0.9f, 0.3f, defaults.getFont(), defaults.getFontSize(), defaults.getFontColour(), "null", 0f, 0f);
		RichText question2Text = new RichText("Question 2", question2.getFont(), question2.getFontSize(), question2.getColor());
		question2.textFragments.add(question2Text);
		page.addObject(question2);
				
		//Add Multiple Choice Answer 2 (Top Right)
		MultipleChoiceObject multipleChoice2 = new MultipleChoiceObject(0.55f, 0.3f, Orientation.HORIZONTAL, MultiChoiceType.CHECKBOX, 1, true);
		multipleChoice2.addAnswer(new Answer("New Answer", true));
		page.addObject(multipleChoice2);
		
		//Add Question 3 (Bottom Left)
		TextObject question3 = new TextObject(0.1f, 0.575f, 0.45f, 0.625f, defaults.getFont(), defaults.getFontSize(), defaults.getFontColour(), "null", 0f, 0f);
		RichText question3Text = new RichText("Question 3", question3.getFont(), question3.getFontSize(), question3.getColor());
		question3.textFragments.add(question3Text);
		page.addObject(question3);
				
		//Add Multiple Choice Answer 3 (Bottom Left)
		MultipleChoiceObject multipleChoice3 = new MultipleChoiceObject(0.1f, 0.625f, Orientation.HORIZONTAL, MultiChoiceType.CHECKBOX, 1, true);
		multipleChoice3.addAnswer(new Answer("New Answer", true));
		page.addObject(multipleChoice3);
		
		//Add Question 4 (Bottom Right)
		TextObject question4 = new TextObject(0.55f, 0.575f, 0.9f, 0.625f, defaults.getFont(), defaults.getFontSize(), defaults.getFontColour(), "null", 0f, 0f);
		RichText question4Text = new RichText("Question 4", question4.getFont(), question4.getFontSize(), question4.getColor());
		question4.textFragments.add(question4Text);
		page.addObject(question4);
				
		//Add Multiple Choice Answer 4 (Bottom Right)
		MultipleChoiceObject multipleChoice4 = new MultipleChoiceObject(0.55f, 0.625f, Orientation.HORIZONTAL, MultiChoiceType.CHECKBOX, 1, true);
		multipleChoice4.addAnswer(new Answer("New Answer", true));
		page.addObject(multipleChoice4);
		
	}
	
	public void ApplyABQuizTemplate(Page page, LessonDefaultSettings defaults) {
		
		// Add Title text object to page (Top Centre)
		TextObject title = new TextObject(0.2f, 0.08f, 0.8f, 0.15f, defaults.getFont(), defaults.getFontSize()+10, defaults.getFontColour(), "null", 0f, 0f);
		RichText titleText = new RichText("Insert Title Here", title.getFont(), title.getFontSize(), title.getColor(), "bold", "underline");
		title.textFragments.add(titleText);
		page.addObject(title);
		
		//Add Question 1 (First)
		TextObject question1 = new TextObject(0.1f, 0.25f, 0.45f, 0.3f, defaults.getFont(), defaults.getFontSize(), defaults.getFontColour(), "null", 0f, 0f);
		RichText question1Text = new RichText("Question 1", question1.getFont(), question1.getFontSize(), question1.getColor());
		question1.textFragments.add(question1Text);
		page.addObject(question1);
		
		//Add Answer box 1 (First)
		AnswerBoxObject answerBox1 = new AnswerBoxObject(0.55f, 0.26f, 15, 1, true, false, 0, 0);
		page.addObject(answerBox1);
		
		//Add Question 2 (Second)
		TextObject question2 = new TextObject(0.1f, 0.35f, 0.45f, 0.3f, defaults.getFont(), defaults.getFontSize(), defaults.getFontColour(), "null", 0f, 0f);
		RichText question2Text = new RichText("Question 2", question2.getFont(), question2.getFontSize(), question2.getColor());
		question2.textFragments.add(question2Text);
		page.addObject(question2);
				
		//Add Answer box 2 (Second)
		AnswerBoxObject answerBox2 = new AnswerBoxObject(0.55f, 0.36f, 15, 1, true, false, 0, 0);
		page.addObject(answerBox2);
		
		//Add Question 3 (Third)
		TextObject question3 = new TextObject(0.1f, 0.45f, 0.45f, 0.3f, defaults.getFont(), defaults.getFontSize(), defaults.getFontColour(), "null", 0f, 0f);
		RichText question3Text = new RichText("Question 3", question3.getFont(), question3.getFontSize(), question3.getColor());
		question3.textFragments.add(question3Text);
		page.addObject(question3);
		
		//Add Answer box 3 (Third)
		AnswerBoxObject answerBox3 = new AnswerBoxObject(0.55f, 0.46f, 15, 1, true, false, 0, 0);
		page.addObject(answerBox3);
				
		//Add Question 4 (Forth)
		TextObject question4 = new TextObject(0.1f, 0.55f, 0.45f, 0.3f, defaults.getFont(), defaults.getFontSize(), defaults.getFontColour(), "null", 0f, 0f);
		RichText question4Text = new RichText("Question 4", question4.getFont(), question4.getFontSize(), question4.getColor());
		question4.textFragments.add(question4Text);
		page.addObject(question4);
		
		//Add Answer box 4 (Forth)
		AnswerBoxObject answerBox4 = new AnswerBoxObject(0.55f, 0.56f, 15, 1, true, false, 0, 0);
		page.addObject(answerBox4);
		
		//Add Question 5 (Fifth)
		TextObject question5 = new TextObject(0.1f, 0.65f, 0.45f, 0.3f, defaults.getFont(), defaults.getFontSize(), defaults.getFontColour(), "null", 0f, 0f);
		RichText question5Text = new RichText("Question 5", question5.getFont(), question5.getFontSize(), question5.getColor());
		question5.textFragments.add(question5Text);
		page.addObject(question5);
		
		//Add Answer box 5 (Fifth)
		AnswerBoxObject answerBox5 = new AnswerBoxObject(0.55f, 0.66f, 15, 1, true, false, 0, 0);
		page.addObject(answerBox5);
		
		//Add Question 6 (sixth)
		TextObject question6 = new TextObject(0.1f, 0.75f, 0.45f, 0.3f, defaults.getFont(), defaults.getFontSize(), defaults.getFontColour(), "null", 0f, 0f);
		RichText question6Text = new RichText("Question 6", question6.getFont(), question6.getFontSize(), question6.getColor());
		question6.textFragments.add(question6Text);
		page.addObject(question6);
		
		//Add Answer box 6 (Sixth)
		AnswerBoxObject answerBox6 = new AnswerBoxObject(0.55f, 0.76f, 15, 1, true, false, 0, 0);
		page.addObject(answerBox6);
				
		//Add Question 7 (Seventh)
		TextObject question7 = new TextObject(0.1f, 0.85f, 0.45f, 0.3f, defaults.getFont(), defaults.getFontSize(), defaults.getFontColour(), "null", 0f, 0f);
		RichText question7Text = new RichText("Question 7", question7.getFont(), question7.getFontSize(), question7.getColor());
		question7.textFragments.add(question7Text);
		page.addObject(question7);
		
		//Add Answer box 7 (Seventh)
		AnswerBoxObject answerBox7 = new AnswerBoxObject(0.55f, 0.86f, 15, 1, true, false, 0, 0);
		page.addObject(answerBox7);
		
	}
	
	public void ApplyVideoTemplate(Page page, LessonDefaultSettings defaults) {
	
		// Add Title text object to page (Top Centre)
		TextObject title = new TextObject(0.2f, 0.08f, 0.8f, 0.15f, defaults.getFont(), defaults.getFontSize()+10, defaults.getFontColour(), "null", 0f, 0f);
		RichText titleText = new RichText("Insert Title Here", title.getFont(), title.getFontSize(), title.getColor(), "bold", "underline");
		title.textFragments.add(titleText);
		page.addObject(title);
		
		// Add Video (Bottom Left)
		VideoObject video = new VideoObject(0.55f, 0.25f, 0.9f, "null", false, false);
		page.addObject(video);
		
		// Add Information text object to page (Bottom Right)
		TextObject info = new TextObject(0.55f, 0.25f, 0.9f, 0.9f, defaults.getFont(), defaults.getFontSize(), defaults.getFontColour(), "null", 0f, 0f);
		RichText infoText = new RichText("Insert Information Here", info.getFont(), info.getFontSize(), info.getColor());
		info.textFragments.add(infoText);
		page.addObject(info);
				
	}
	
	public void ApplyAudioTemplate(Page page, LessonDefaultSettings defaults) {
		
		// Add Title text object to page (Top Centre)
		TextObject title = new TextObject(0.2f, 0.08f, 0.8f, 0.15f, defaults.getFont(), defaults.getFontSize()+10, defaults.getFontColour(), "null", 0f, 0f);
		RichText titleText = new RichText("Insert Title Here", title.getFont(), title.getFontSize(), title.getColor(), "bold", "underline");
		title.textFragments.add(titleText);
		page.addObject(title);
		
		// Add Audio 1 (Bottom Left)
		AudioObject audio1 = new AudioObject(0.2f, 0.3f, 0.3f, "null", 0, true, false, false);
		page.addObject(audio1);
		
		// Add Audio 2 (Bottom Left)
		AudioObject audio2 = new AudioObject(0.2f, 0.55f, 0.3f, "null", 0, true, false, false);
		page.addObject(audio2);
		
		// Add Information text object to page (Bottom Right)
		TextObject info = new TextObject(0.55f, 0.25f, 0.9f, 0.9f, defaults.getFont(), defaults.getFontSize(), defaults.getFontColour(), "null", 0f, 0f);
		RichText infoText = new RichText("Insert Information Here", info.getFont(), info.getFontSize(), info.getColor());
		info.textFragments.add(infoText);
		page.addObject(info);
		
	}
	
}
