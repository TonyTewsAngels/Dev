/*
 * Copyright (C) 2015 Sofia Software Solutions
 */
package teacheasy.data;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

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

	public AnswerBoxHandler(String nCorrectAnswers) {

		answerField = new TextField();
		marks = 0;
		this.correctAnswers = nCorrectAnswers;
	}

	/* Method to create an answer box */
	public void createAnswerBox(double nXStart, double nYStart,
			int nCharacterLimit, boolean nRetry) {

		answerField.relocate(nXStart, nYStart);
		answerField.addEventHandler(KeyEvent.KEY_TYPED,
				maxLength(nCharacterLimit));
		this.retry = nRetry;
	}

	/**
	 * This method limits the number of characters that can be typed in the
	 * textField to the the specified nCharacterLimit
	 *
	 */
	public EventHandler<KeyEvent> maxLength(final Integer characterLimit) {
		return new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {

				TextField tx = (TextField) event.getSource();
				if (tx.getText().length() >= characterLimit) {
					event.consume();
				}
			}
		};
	}

	/**
	 * This method stores the potential answers in an array. When an answer is
	 * provided the loop compares the entered answer against the possible
	 * answers in the array. It also allows/disallows retry depending on the
	 * value of the variable retry.
	 * 
	 */
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
