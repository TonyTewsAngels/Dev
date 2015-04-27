package teacheasy.runtime.editor;

import teacheasy.data.ImageObject;
import teacheasy.data.Lesson;
import teacheasy.data.Page;
import teacheasy.data.RichText;
import teacheasy.data.TextObject;
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
	
	public void ApplyInformationTemplate(Page page, LessonDefaultSettings defaults) {
		
		// Add Title text object to page (Top Centre)
		TextObject title = new TextObject(0.2f, 0.05f, 0.8f, 0.15f, defaults.getFont(), defaults.getFontSize()+10, defaults.getFontColour(), "null", 0f, 0f);
		RichText titleText = new RichText("Insert Title Here", title.getFont(), title.getFontSize(), title.getColor(), "bold", "underline");
		title.textFragments.add(titleText);
		page.addObject(title);
		
		// Add Information text object to page (Bottom left)
		TextObject info = new TextObject(0.05f, 0.3f, 0.45f, 0.95f, defaults.getFont(), defaults.getFontSize(), defaults.getFontColour(), "null", 0f, 0f);
		RichText infoText = new RichText("Insert Information Here", info.getFont(), info.getFontSize(), info.getColor());
		info.textFragments.add(infoText);
		page.addObject(info);
		
		// Add Image object to page (Bottom Right)
		ImageObject image = new ImageObject(0.55f, 0.3f, 0.95f, 0.95f, "Question-mark-scratch-head.jpg", 0f, 0f, 0f, 0f, 0f);
		page.addObject(image);
		
		
	}
	
	public void ApplyMCQuizTemplate(Page page, LessonDefaultSettings defaults) {
		
	}
	
	public void ApplyABQuizTemplate(Page page, LessonDefaultSettings defaults) {
		
	}
	
	public void ApplyVideoTemplate(Page page, LessonDefaultSettings defaults) {
	
	}
	
	
	
	
	
}
