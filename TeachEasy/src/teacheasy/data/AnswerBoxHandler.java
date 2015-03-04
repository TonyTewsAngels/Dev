/*
 * Copyright (C) 2015 Sofia Software Solutions
 */
package teacheasy.data;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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

	public TextField answerField;
	public int marks;
	boolean retry;
	String correctAnswers;
	double XStart;
	double YStart;
	int characterLimit;
	
	
	

	public List<AnswerBox> answerBox;

	public AnswerBoxHandler(double nXStart, double nYStart,
			int nCharacterLimit, boolean nRetry, String nCorrectAnswers,
			int nMarks) {
		this.answerBox = new ArrayList<AnswerBox>();
		this.XStart = nXStart;
		this.YStart = nYStart;
		this.characterLimit = nCharacterLimit;
	}

	public void createAnswerBox() {
		answerBox.add(new AnswerBox(XStart, YStart, characterLimit, retry,
				correctAnswers, marks));
	}

}
