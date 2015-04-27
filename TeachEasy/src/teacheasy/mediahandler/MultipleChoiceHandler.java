/*
 * Emmanuel Olutayo
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.mediahandler;

import java.util.ArrayList;
import java.util.List;

import teacheasy.data.MultipleChoiceObject.MultiChoiceType;
import teacheasy.data.MultipleChoiceObject.Orientation;
import teacheasy.data.multichoice.Answer;
import teacheasy.mediahandler.multichoice.MultipleChoice;

import javafx.scene.Group;


/**
 * A class to handle the multiple choice objects
 * on a page. 
 * 
 * @author	Emmanuel Olutayo
 * @version	1.0 07 Mar 2015
 */
public class MultipleChoiceHandler {
    /* Group reference */
	private Group group;
	
	/* List of multiple choice objects */
	private List <MultipleChoice> multipleChoice;

	/** Constructor */
	public MultipleChoiceHandler(Group nGroup) {
	    /* Set the group reference */
		this.group = nGroup;

		/* initialise the Question list */
		this.multipleChoice = new ArrayList<MultipleChoice>();
	}
	
	/**
	 * Method to add a multiple choice object to a page.
	 * 
	 * @param answers - Array list of Answer objects representing the possible
	 *                  answers; correct or incorrect.        
	 * @param defaultPadding
	 * @param spacing
	 * @param type - The type of multiple choice object being created
	 * @param orientation - Which orientation (vertical or horizontal) to draw the answers.
	 */
	public void createMultipleChoice(float xStart, float yStart, ArrayList<Answer>answers, 
	                                 MultiChoiceType type, Orientation orientation, boolean retry){
		multipleChoice.add(new MultipleChoice(group,
		                                      xStart,
		                                      yStart,
		                                      answers,
		                                      type, 
				                              orientation,
				                              retry));
	}
	
	/** Checks to see if all questions on the current page have been attempted */
	public boolean AllMultipleChoiceQuestionsAttempted() {
		for (int i=0; i < multipleChoice.size(); i++) {
			if (!multipleChoice.get(i).buttonPressed) {
				return false;
			}
		}
		return true;
	}
}
