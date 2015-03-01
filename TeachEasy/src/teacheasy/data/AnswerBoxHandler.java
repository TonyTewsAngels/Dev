/*
 * Copyright (C) 2015 Sofia Software Solutions
 */
package teacheasy.data;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * 
 * @author Daniel Berhe and Jake Ransom
 * @version 1.0
 * 
 */
public class AnswerBoxHandler {

	public TextField answerField;
	public int marks;
	boolean retry;
	String correctAnswers;

	public AnswerBoxHandler(String nCorrectAnswers) {

		answerField = new TextField();
		marks = 0;
		this.correctAnswers = nCorrectAnswers;

	}

	public void createAnswerBox(double nXStart, double nYStart,
			int nCharacterLimit, boolean nRetry) {

		answerField.relocate(nXStart, nYStart);
		answerField.addEventHandler(KeyEvent.KEY_TYPED,
				maxLength(nCharacterLimit));
		this.retry = nRetry;

	}

	public EventHandler<KeyEvent> maxLength(final Integer i) {
		return new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {

				TextField tx = (TextField) event.getSource();
				if (tx.getText().length() >= i) {
					event.consume();
				}

			}

		};
	}

	public boolean answerChecker(int nMarks) {
		boolean isCorrect = false;
		String[] listOfCorrectAnswers = correctAnswers.split("-");
		for (int i = 0; i < listOfCorrectAnswers.length; i++) {

			if (answerField.getText().equals(listOfCorrectAnswers[i])) {
				marks += nMarks;
				isCorrect = true;
				break;
			}
		}
		answerField.setEditable(retry);
		return isCorrect;

	}
}
