/*
 * Copyright (C) 2015 Sofia Software Solutions
 */
package teacheasy.data;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

/**
 * Class to draw answer boxes with the specified x & y location and character
 * limit. The class also checks entered answer against list of possible answers
 * and assigns them the predefined marks.
 * 
 * @author Daniel Berhe and Jake Ransom
 * @version 1.0
 * 
 * 
 */
public class AnswerBox {

	private TextField answerField;
	private int marks;
	private int awardedMarks;
	private boolean retry;
	private String correctAnswers;
	private boolean answerIsCorrect;
	private int characterLimit;
	private double xStart;
	private double yStart;
	private Group group;

	private HBox box;
	private Button checkAnswerButton;

	public AnswerBox(double nXStart, double nYStart, int nCharacterLimit,
			boolean nRetry, String nCorrectAnswers, int nMarks, Group nGroup) {

		answerField = new TextField();
		this.marks = nMarks;
		this.correctAnswers = nCorrectAnswers;
		this.characterLimit = nCharacterLimit;
		this.xStart = nXStart;
		this.yStart = nYStart;
		this.group = nGroup;
		this.retry = nRetry;

		box = new HBox();

		/* Creates new button to check the typed answer */
		checkAnswerButton = new Button("Check answer");
		checkAnswerButton.setId("check answer");
		checkAnswerButton.setOnAction(new ButtonEventHandler());

		createAnswerBox(xStart, yStart, characterLimit, nRetry);

		/* Submits and checks typed answer upon key press "enter" */
		answerField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent key) {
				if (key.getCode().equals(KeyCode.ENTER)
						&& answerField.isEditable() == true) {
					answerIsCorrect = checkAnswer(marks);
					System.out.println("Answered " + answerIsCorrect);
					System.out.println("Awarded marks " + awardedMarks);
				}
			}
		});

		box.relocate(xStart, yStart);
		box.getChildren().addAll(answerField, checkAnswerButton);
		group.getChildren().add(box);
	}

	/** Method to create an answer box */
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

				TextField textField = (TextField) event.getSource();
				if (textField.getText().length() >= characterLimit) {
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
				awardedMarks += nMarks;
				isCorrect = true;
				retry = false;
				break;
			}
		}
		answerField.setEditable(retry);
		return isCorrect;
	}

	/**
	 * Button Event Handler Class
	 */
	public class ButtonEventHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			/* Get the button that was pressed */
			Button button = (Button) e.getSource();

			/* Get the id of the button pressed */
			String id = button.getId();

			/* Act according to id */
			if (id.equals("check answer") && answerField.isEditable() == true) {
				answerIsCorrect = checkAnswer(marks);
				System.out.println("Answered " + answerIsCorrect);
				System.out.println("Awarded marks " + awardedMarks);
			}
		}
	}

}
