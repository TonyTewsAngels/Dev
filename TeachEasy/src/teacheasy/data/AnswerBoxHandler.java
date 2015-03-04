/*
 * Copyright (C) 2015 Sofia Software Solutions
 */
package teacheasy.data;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import teacheasy.data.AnswerBox;

/**
 * Class to draw answer boxes with the specified x & y location and character
 * limit. The class also checks entered answer against list of possible answers
 * and assigns predefined marks.
 * 
 * @author Daniel Berhe and Jake Ransom
 * @version 1.0
 * 
 * 
 */
public class AnswerBoxHandler {

	private List<AnswerBox> answerBox;
	private Group group;

	public AnswerBoxHandler(Group nGroup) {
		this.answerBox = new ArrayList<AnswerBox>();
		this.group = nGroup;
	}

	public void createAnswerBox(double xStart, double yStart,
			int characterLimit, boolean retry, String correctAnswers, int marks) {
		answerBox.add(new AnswerBox(xStart, yStart, characterLimit, retry,
				correctAnswers, marks, group));
	}

}
