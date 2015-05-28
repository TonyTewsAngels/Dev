package teacheasy.debug;

import teacheasy.data.Page;
import teacheasy.data.lessondata.LessonDefaultSettings;
import teacheasy.runtime.editor.TemplateController;
import teacheasy.runtime.editor.TemplateController.TemplateType;

public class PageTemplateTester {

	public static void main(String args[]) {
		Page page = new Page(1, "WHITE");
		
		TemplateController controller = new TemplateController();
		
		LessonDefaultSettings defaults = new LessonDefaultSettings(12, "arial", "#ffffffff", "#000000ff", "#000000ff", "#000000ff");
		
		controller.ApplyTemplate(page, defaults, TemplateType.MCQUIZ);
				
		page.debugPrint();
	}
	
}
