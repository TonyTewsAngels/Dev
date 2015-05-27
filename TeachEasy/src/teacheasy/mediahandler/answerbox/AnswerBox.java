/*
 * Copyright (C) 2015 Sofia Software Solutions
 */
package teacheasy.mediahandler.answerbox;

import java.util.ArrayList;

import teacheasy.data.multichoice.Answer;

import com.sun.javafx.scene.control.behavior.TextFieldBehavior;
import com.sun.javafx.scene.control.skin.TextFieldSkin;

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

/**
 * Class to draw answer boxes with the specified x & y location and character
 * limit. The class also checks entered answer against list of possible answers
 * and assigns them the predefined marks.
 * 
 * @author Daniel Berhe and Jake Ransom
 * @version 1.1 07 Mar 2015
 * 
 */
public class AnswerBox {
    /* Fields */
    private int marks;
    private int awardedMarks;
    private boolean retry;
    private boolean answerIsCorrect;
    private int characterLimit;
    private double xStart;
    private double yStart;
    private boolean isNumerical;
    private boolean validInput;
    private float upperBound;
    private float lowerBound;
    private ArrayList<Answer> answers;

    /* UI Elements */
    private Group group;
    public TextField answerField;
    private HBox box;
    public Button checkAnswerButton;
    private Label feedbackLabel;

    /* For checking if marks have been collated */
    private boolean markCollated;

    /** Constructor method */
    public AnswerBox(double nXStart, double nYStart, int nCharacterLimit,
            boolean nRetry, int nMarks, boolean nIsNumerical, Group nGroup,
            float nUpperBound, float nLowerBound, ArrayList<Answer> nAnswers) {

        /* Setting local variables */
        if (nMarks > 0){
            this.marks = nMarks;
        } else {
            this.marks = 0;
        }

        /* To prevent negative or 0 character limit */
        if (nCharacterLimit >= 1) {
            this.characterLimit = nCharacterLimit;
        } else if (nCharacterLimit <= 0) {
            this.characterLimit = 2;
        }

        this.xStart = nXStart;
        this.yStart = nYStart;
        this.group = nGroup;
        this.retry = nRetry;
        this.isNumerical = nIsNumerical;
        this.upperBound = nUpperBound;
        this.lowerBound = nLowerBound;
        this.answers = nAnswers;

        setAwardedMarks(0);
        setMarkCollated(false);

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
        answerField.setSkin(new TextFieldSkin(answerField,
                new TextFieldBehavior(answerField) {
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

        // /*Initialise the buttonPressed variable as false*/
        // this.buttonPressed = false;
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
            for (Answer a : answers) {
                /*
                 * Checks if there is at least a character and compares user
                 * submitted answers with the list of available answers
                 */
                if (answerField.getText().length() >= 1 && 
                    answerField.getText().equalsIgnoreCase(a.getText())) {
                    /* Award marks */
                    setAwardedMarks(getAwardedMarks() + marks);

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
            float answer;

            /* A variable that is set when input is invalid */
            validInput = true;

            /*
             * Converting range of acceptable answers and user submitted answers
             * to float
             */
            try {
                answer = Float.parseFloat(answerField.getText());
            } catch (NumberFormatException nfe) {
                answer = 0.0f;
                validInput = false;
            }
            /*
             * Checks submitted numerical answer is within the specified region
             */
            if (validInput) {
                if (answer >= lowerBound && answer <= upperBound) {
                    setAwardedMarks(getAwardedMarks() + marks);
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
            if (answerIsCorrect && getAwardedMarks() > 1) {
                feedbackLabel.setText("Correct! " + getAwardedMarks()
                        + " marks");
            } else if (answerIsCorrect && getAwardedMarks() == 1) {
                feedbackLabel
                        .setText("Correct! " + getAwardedMarks() + " mark");
            } else {
                feedbackLabel.setText("Incorrect");
            }
        }
    }

    /** method for getting markCollated */
    public boolean isMarkCollated() {
        return markCollated;
    }

    /** method for setting markCollated */
    public void setMarkCollated(boolean markCollated) {
        this.markCollated = markCollated;
    }

    public int getAwardedMarks() {
        return awardedMarks;
    }

    public void setAwardedMarks(int awardedMarks) {
        this.awardedMarks = awardedMarks;
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
            if (answerField.getText().length() > characterLimit) {
                String s = answerField.getText().substring(0, characterLimit);
                answerField.setText(s);
            }
        }
    }
}
