/*
 * Emmanuel Olutayo
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */

package teacheasy.mediahandler.multichoice;

import java.util.ArrayList;
import java.util.List;

import teacheasy.data.MultipleChoiceObject.MultiChoiceType;
import teacheasy.data.MultipleChoiceObject.Orientation;
import teacheasy.data.multichoice.Answer;
import javafx.scene.Group;

public class MChoiceMediaHandler {
	private Group group; 
	private List <MultipleChoice> multipleChoice;
	
	/*constructor method */
	
	public MChoiceMediaHandler(Group nGroup) {
		
		this.group = nGroup;
		
		/* initialise the Question list */
		this.multipleChoice = new ArrayList<MultipleChoice>();
		
	}
	public void createMultipleChoice(Group nGroup , ArrayList<Answer>answers, int defaultPadding, 
			int spacing, MultiChoiceType type, Orientation orientation){
		
	multipleChoice.add(new MultipleChoice(group, answers, defaultPadding, spacing, type , 
			      orientation));
	}

}
