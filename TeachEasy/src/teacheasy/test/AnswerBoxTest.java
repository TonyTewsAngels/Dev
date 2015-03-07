/*
 * Samuel Raeburn
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.test;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import com.sun.javafx.scene.control.behavior.TextFieldBehavior;
import com.sun.javafx.scene.control.skin.TextFieldSkin;

/**
 * This class is used to manually test the
 * answer box handler
 * 
 * @author Alistair Jewers
 * @version 1.0 02 Mar 2015
 */

public class AnswerBoxTest {
	/* Fields */
	private int marks;
	private int awardedMarks;
	private boolean retry;
	private String correctAnswers;
	private boolean answerIsCorrect;
	private int characterLimit;
	private double xStart;
	private double yStart;
	private boolean isNumerical;
	private boolean validInput;

	/* UI Elements */
	private Group group;
	private TextField answerField;
	private HBox box;
	private Button checkAnswerButton;
	private Label feedbackLabel;

	/** Constructor method */
	public AnswerBoxTest(double nXStart, double nYStart, int nCharacterLimit,
			boolean nRetry, String nCorrectAnswers, int nMarks,
			boolean nIsNumerical, Group nGroup) {

		/* Setting local variables */
		this.marks = nMarks;
		this.correctAnswers = nCorrectAnswers;
		this.characterLimit = nCharacterLimit;
		this.xStart = nXStart;
		this.yStart = nYStart;
		this.group = nGroup;
		this.retry = nRetry;
		this.isNumerical = nIsNumerical;

		/* A text field where answers can be typed */
		answerField = new TextField();

		/* Creates new button to check the typed answer */
		checkAnswerButton = new Button("Check answer");
		checkAnswerButton.setId("check answer");
		checkAnswerButton.setOnAction(new ButtonEventHandler());

		/* Creates feedback label to display correct/incorrect */
		feedbackLabel = new Label();

		/* HBox to hold the answerBox, Check answer button & the feedback label */
		box = new HBox();
		box.setSpacing(5);
		box.setAlignment(Pos.CENTER);

		/* Submits and checks typed answer upon key press "enter" */
		answerField.setOnKeyPressed(new KeyEventHandler());

		/* Uses a change listener to limit the character count */
		answerField.textProperty().addListener(new MaxLengthListener());

		/* Set a custom skin for the text box to remove the context menu */
		answerField.setSkin(new TextFieldSkin(answerField, new TextFieldBehavior(answerField) {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseButton.SECONDARY) {
					return; // don't allow context menu to show 
				}

				super.mouseReleased(e);
			}
		}));

		/* Place the box at the specified location */
		box.relocate(xStart, yStart);

		/* Group the elements to be displayed on the screen in box */
		box.getChildren().addAll(answerField, checkAnswerButton, feedbackLabel);

		/* Add box back to the main screen */
		group.getChildren().add(box);
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

		if (!isNumerical) {
			String[] listOfCorrectAnswers = correctAnswers.split("~");
			for (int i = 0; i < listOfCorrectAnswers.length; i++) {
				/*
				 * Compares user submitted answers with the list of available
				 * answers
				 */
				if (answerField.getText().toUpperCase().equals(listOfCorrectAnswers[i].toUpperCase())) {
					/* Award marks */
					awardedMarks += nMarks;

					/* Acknowledge that question is answered correctly */
					isCorrect = true;

					/* Disable retry if answered correctly */
					retry = false;

					break;
				}
			}
		}
		/* Used when expecting numerical inputs in the TextField */
		else {
			float minRange;
			float maxRange;
			float answer;

			/* Splits the correctAnswers so that min & max ranges are identified */
			String[] listOfCorrectAnswers = correctAnswers.split("~");

			/* A variable that is set when input is invalid */
			validInput = true;

			/*
			 * Converting range of acceptable answers and user submitted answers
			 * to float
			 */
			try {
				minRange = Float.parseFloat(listOfCorrectAnswers[0]);
				maxRange = Float.parseFloat(listOfCorrectAnswers[1]);
				answer = Float.parseFloat(answerField.getText());
			} catch (NumberFormatException nfe) {
				minRange = 0.0f;
				maxRange = 0.0f;
				answer = 0.0f;
				validInput = false;
			}
			/*
			 * Checks submitted numerical answer is within the specified region
			 */
			if (validInput) {
				if (answer >= minRange && answer <= maxRange) {
					awardedMarks += nMarks;
					isCorrect = true;
					retry = false;
				}
			}
		}

		answerField.setEditable(retry);
		answerField.setDisable(!retry);
		checkAnswerButton.setDisable(!retry);
		return isCorrect;
	}

	/** A method to display feedback to the user */
	public void displayFeedback() {

		if (validInput == false && isNumerical == true) {
			feedbackLabel.setText("Invalid input");
		} else {
			if (answerIsCorrect) {
				feedbackLabel.setText("Correct! " + awardedMarks + " marks");
			} else {
				feedbackLabel.setText("Incorrect");
			}
		}
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
				displayFeedback();
			}
		}
	}

	/**
	 * Key press event handler
	 */
	public class KeyEventHandler implements EventHandler<KeyEvent> {
		public void handle(KeyEvent key) {
			if (key.getCode().equals(KeyCode.ENTER)
					&& answerField.isEditable() == true) {
				/* Check if answer is correct */
				answerIsCorrect = checkAnswer(marks);

				/* Display the outcome */
				displayFeedback();
			}
		}
	}

	/**
	 * This class limits the number of characters that can be typed in the
	 * textField to the the specified nCharacterLimit
	 * 
	 */
	public class MaxLengthListener implements ChangeListener<String> {
		@Override
		public void changed(ObservableValue<? extends String> ov,
				String oldVal, String newVal) {
			if(answerField.getText().length() > characterLimit) {
				String s = answerField.getText().substring(0, characterLimit);
				answerField.setText(s);
			}
		}
	}
}

