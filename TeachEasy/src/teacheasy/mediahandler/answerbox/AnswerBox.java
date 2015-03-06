/*
 * Copyright (C) 2015 Sofia Software Solutions
 */
package teacheasy.mediahandler.answerbox;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    private Group group;
    private TextField answerField;
    private HBox box;
    private Button checkAnswerButton;
    private Label feedbackLabel;

    public AnswerBox(double nXStart, double nYStart, int nCharacterLimit,
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
        
        /* Create an answer box with the specified character limit */
        createAnswerBox(characterLimit, nRetry);

        /* Submits and checks typed answer upon key press "enter" */
        answerField.setOnKeyPressed(new KeyEventHandler());

        /* Place the box at the specified location */
        box.relocate(xStart, yStart);

        /* Group the elements to be displayed on the screen in box */
        box.getChildren().addAll(answerField, checkAnswerButton, feedbackLabel);

        /* Add box back to the main screen */
        group.getChildren().add(box);
    }

    /** Method to create an answer box */
    private void createAnswerBox(int nCharacterLimit, boolean nRetry) {

        answerField.addEventHandler(KeyEvent.KEY_TYPED,
                handleMaxLength(nCharacterLimit));
        this.retry = nRetry;
    }

    /**
     * This method limits the number of characters that can be typed in the
     * textField to the the specified nCharacterLimit
     * 
     */
    public EventHandler<KeyEvent> handleMaxLength(final Integer characterLimit) {
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

}
