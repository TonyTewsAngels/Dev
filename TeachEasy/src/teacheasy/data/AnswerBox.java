package teacheasy.data;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class AnswerBox {

	public TextField answerField;
	public int marks;
	boolean retry;
	String correctAnswers;
	boolean answerIsCorrect;

	public AnswerBox(double nXStart, double nYStart, int nCharacterLimit,
			boolean nRetry, String nCorrectAnswers, int nMarks) {
		answerField = new TextField();
		this.marks = nMarks;
		this.correctAnswers = nCorrectAnswers;
		createAnswerBox(nXStart, nYStart, nCharacterLimit, nRetry);
		answerField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)
						&& answerField.isEditable() == true) {
					answerIsCorrect = checkAnswer(marks);
					// System.out.println(correctAnswer);
					// System.out.println(answerBox.marks);
				}
			}
		});
	}

	/* Method to create an answer box */
	private void createAnswerBox(double nXStart, double nYStart,
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
	public boolean checkAnswer(int nMarks) {
		boolean isCorrect = false;
		String[] listOfCorrectAnswers = correctAnswers.split("~");
		for (int i = 0; i < listOfCorrectAnswers.length; i++) {

			if (answerField.getText().equals(listOfCorrectAnswers[i])) {
				marks += nMarks;
				isCorrect = true;
				retry = false;
				break;
			}
		}
		answerField.setEditable(retry);
		return isCorrect;
	}

}
