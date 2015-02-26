/*
 * Copyright (C) 2015 Sofia Software Solutions
 */
package teacheasy.data;

import java.awt.TextField;

/**
 * 
 * @author Daniel Berhe and Jake Ransom
 * @version 1.0
 * 
 */
public class AnswerBoxHandler {

	TextField answerField;
	public int marks;
	boolean retry;
	String correctAnswers;

	public AnswerBoxHandler(String nCorrectAnswers) {

		answerField = new TextField();
		marks = 0;
		this.correctAnswers = nCorrectAnswers;

	}

	public void createAnswerBox(int nXStart, int nYStart, int nCharacterLimit,
			boolean nRetry) {

		answerField.setLocation(nXStart, nYStart);
		answerField.setColumns(nCharacterLimit);
		this.retry = nRetry;

	}

	public boolean answerChecker(int nMarks) {

		boolean isCorrect = false;

		String[] listOfCorrectAnswers = correctAnswers.split("-");

		for (int i = 0; i < listOfCorrectAnswers.length; i++) {

			if (answerField.getText() == listOfCorrectAnswers[i]) {
				isCorrect = true;
				marks += nMarks;
			} else {
				isCorrect = false;

			}
		}
		answerField.setEditable(retry);
		return isCorrect;

	}
}
