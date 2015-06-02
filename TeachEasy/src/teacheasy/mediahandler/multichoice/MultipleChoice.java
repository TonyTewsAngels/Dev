/*
 * Emmanuel Olutayo
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.mediahandler.multichoice;

import java.util.ArrayList;
import java.util.List;

import teacheasy.data.MultipleChoiceObject.MultiChoiceType;
import teacheasy.data.MultipleChoiceObject.Orientation;
import teacheasy.data.multichoice.Answer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * A class to create check boxes and radio buttons
 * 
 * @author Emmanuel Olutayo
 * @author Penelope Nicole
 * @version 1.1 10 Mar 2015
 */
public class MultipleChoice {
    /* Group reference */
    private Group group;

    /* UI Objects */
    private VBox verticalPosition;
    private HBox horizontalPosition;
    private Button markButton;
    private Label markLabel;

    /* Lists of the check boxes and radio buttons */
    private List<MChoiceCheckBox> cB;
    private List<MChoiceRadio> rB;
    private List<Answer> dList;

    /* Data */
    public MultiChoiceType type;
    private Orientation orientation;
    private boolean retry;
    private boolean buttonPressed;
    private int marks;
    private int awardedMarks;
    private boolean markCollated;
    private int numberOfAnswers;

    /* Create Drop Down List */
    public ComboBox dropDownList = new ComboBox();

    /**
     * Constructor method to create multiple choice object
     * 
     * @param nGroup
     *            A node to contain the multiple choice object
     * @param xStart
     *            X-coordinate of the top left corner multiple choice in pixels
     * @param yStart
     *            Y-coordinate of the top left corner multiple choice in pixels
     * @param answers
     *            List of correct answers
     * @param nType
     *            Type of multiple choice. E.g. drop down, radio..
     * @param nOrientation
     *            Vertical/horizontal orientation of the object
     * @param nRetry
     *            Enable/disable retry
     * @param nMarks
     *            Marks for correct answer
     */
    public MultipleChoice(Group nGroup, float xStart, float yStart,
            ArrayList<Answer> answers, MultiChoiceType nType,
            Orientation nOrientation, boolean nRetry, int nMarks) {

        /* Set the group reference */
        this.group = nGroup;

        /* Set the variable to say how many possible answers there are */
        numberOfAnswers = answers.size();

        /* Instantiate the array lists */
        cB = new ArrayList<MChoiceCheckBox>();
        rB = new ArrayList<MChoiceRadio>();
        this.dList = answers;

        /* Set the type variable */
        this.type = nType;
        this.orientation = nOrientation;
        this.retry = nRetry;

        /* Simple statement to ensure negative marks are not awarded */
        if (nMarks > 0) {
            this.marks = nMarks;
        } else {
            this.marks = 0;
        }

        /* Initialise local variables */
        setAwardedMarks(0);
        setMarkCollated(false);

        /* VBox to hold the answers if vertically oriented */
        verticalPosition = new VBox(10);
        verticalPosition.setPadding(new Insets(10));

        /* HBox to hold the answers if horizontally oriented */
        horizontalPosition = new HBox(10);
        horizontalPosition.setPadding(new Insets(10));

        /* The button to mark the question */
        setMarkButton(new Button("Mark"));
        markLabel = new Label("");

        /* Toggle group used to allow only one radio button to be selected */
        ToggleGroup tGroup = new ToggleGroup();

        /* Statement to create the appropriate multiple choice based on type */
        switch (type) {
        case CHECKBOX:
            /* Create the check boxes */
            for (int i = 0; i < answers.size(); i++) {
                CheckBox temp = new CheckBox(answers.get(i).getText());
                cB.add(new MChoiceCheckBox(temp, answers.get(i).isCorrect()));
            }

            /* Check the orientation and add the check boxes appropriately */
            if (nOrientation == Orientation.VERTICAL) {
                for (int i = 0; i < cB.size(); i++) {
                    verticalPosition.getChildren().add(cB.get(i).getCheckBox());
                }

                verticalPosition.getChildren().add(getMarkButton());
                verticalPosition.getChildren().add(markLabel);
                verticalPosition.relocate(xStart, yStart);
                group.getChildren().add(verticalPosition);
            } else {
                for (int i = 0; i < cB.size(); i++) {
                    horizontalPosition.getChildren().add(
                            cB.get(i).getCheckBox());
                }

                horizontalPosition.getChildren().add(getMarkButton());
                horizontalPosition.getChildren().add(markLabel);
                horizontalPosition.relocate(xStart, yStart);
                group.getChildren().add(horizontalPosition);
            }

            /* Set the mark buttons action */
            getMarkButton().setOnAction(new MultipleChoiceCheckHandler());

            break;

        case RADIO:
            /* Create the radio buttons */
            for (int i = 0; i < answers.size(); i++) {
                RadioButton tempRb = new RadioButton(answers.get(i).getText());
                tempRb.setToggleGroup(tGroup);
                rB.add(new MChoiceRadio(tempRb, answers.get(i).isCorrect()));
            }

            /* Check the orientation and add the radio buttons appropriately */
            if (nOrientation == Orientation.VERTICAL) {
                for (int i = 0; i < rB.size(); i++) {
                    verticalPosition.getChildren().add(
                            rB.get(i).getRadioButton());
                }

                verticalPosition.getChildren().add(getMarkButton());
                verticalPosition.getChildren().add(markLabel);
                verticalPosition.relocate(xStart, yStart);
                group.getChildren().add(verticalPosition);
            } else {
                for (int i = 0; i < rB.size(); i++) {
                    horizontalPosition.getChildren().add(
                            rB.get(i).getRadioButton());
                }

                horizontalPosition.getChildren().add(getMarkButton());
                horizontalPosition.getChildren().add(markLabel);
                horizontalPosition.relocate(xStart, yStart);
                group.getChildren().add(horizontalPosition);
            }

            /* Set the mark buttons action */
            getMarkButton().setOnAction(new MultipleChoiceCheckHandler());

            break;

        case DROPDOWNLIST:
            /* Add answers to drop down List */
            for (int i = 0; i < answers.size(); i++) {
                dropDownList.getItems().add(dList.get(i).getText());

            }
            /* Check the orientation and add the drop down appropriately */
            if (nOrientation == Orientation.VERTICAL) {
                verticalPosition.getChildren().add(dropDownList);
                verticalPosition.getChildren().add(getMarkButton());
                verticalPosition.getChildren().add(markLabel);
                verticalPosition.relocate(xStart, yStart);
                group.getChildren().add(verticalPosition);
            } else {
                horizontalPosition.getChildren().add(dropDownList);
                horizontalPosition.getChildren().add(getMarkButton());
                horizontalPosition.getChildren().add(markLabel);
                horizontalPosition.relocate(xStart, yStart);
                group.getChildren().add(horizontalPosition);
            }

            /* Set the mark buttons action */
            getMarkButton().setOnAction(new MultipleChoiceCheckHandler());
            break;
        }
    }

    /**
     * Public method to return the x start position
     * 
     * @return x-coordinate of top left corner of bounding box
     */
    public double getMultiChoiceXStart() {
        if (orientation == Orientation.VERTICAL) {
            return verticalPosition.getLayoutX();
        } else if (orientation == Orientation.HORIZONTAL) {
            return horizontalPosition.getLayoutX();
        } else {
            return 0;
        }
    }

    /**
     * Public method to return the y start position
     * 
     * @return y-coordinate of top left corner of bounding box
     */
    public double getMultiChoiceYStart() {
        if (orientation == Orientation.VERTICAL) {
            return verticalPosition.getLayoutY();
        } else if (orientation == Orientation.HORIZONTAL) {
            return horizontalPosition.getLayoutY();
        } else {
            return 0;
        }
    }

    /**
     * Public method to return the x end position
     * 
     * @return x-coordinate of bottom right corner of bounding box
     */
    public double getMultiChoiceXEnd() {
        double xStart = getMultiChoiceXStart();
        if (orientation == Orientation.VERTICAL) {
            return xStart + verticalPosition.getWidth();
        } else if (orientation == Orientation.HORIZONTAL) {
            return xStart + horizontalPosition.getWidth();
        } else {
            return 0;
        }
    }

    /**
     * Public method to return the y end position
     * 
     * @return y-coordinate of bottom right corner of bounding box
     */
    public double getMultiChoiceYEnd() {
        double yStart = getMultiChoiceYStart();
        if (orientation == Orientation.VERTICAL) {
            return yStart + verticalPosition.getHeight();
        } else if (orientation == Orientation.HORIZONTAL) {
            return yStart + horizontalPosition.getHeight();
        } else {
            return 0;
        }
    }

    /**
     * Gets the width of the multiple choice object
     * 
     * @return Width of the multiple choice object
     */
    public double getMultiChoiceWidth() {
        if (orientation == Orientation.VERTICAL) {
            return verticalPosition.getWidth();
        } else if (orientation == Orientation.HORIZONTAL) {
            return horizontalPosition.getWidth();
        } else {
            return 0;
        }
    }

    /**
     * Gets height of the multiple choice
     * 
     * @return Height of the multiple choice object
     */
    public double getMultiChoiceHeight() {
        if (orientation == Orientation.VERTICAL) {
            return verticalPosition.getHeight();
        } else if (orientation == Orientation.HORIZONTAL) {
            return horizontalPosition.getHeight();
        } else {
            return 0;
        }
    }

    /**
     * Method to check the selected answers.
     * 
     * @return true if the selected answers are correct.
     */
    private boolean Check() {
        /* Check the type */
        switch (type) {
        case CHECKBOX:
            /* Check the check boxes */
            return CheckBoxCheck();
        case RADIO:
            /* Check the radio buttons */
            return RadioCheck();
        case DROPDOWNLIST:
            /* Check the drop down list */
            return DropDownListCheck();
        default:
            return false;
        }
    }

    /**
     * Method to check a set of check boxes.
     * 
     * @return <code>true</code> if the selected answers are the correct
     *         answers; <code>false</code> otherwise.
     */
    private boolean CheckBoxCheck() {
        /* Loop through all the check boxes */
        for (int i = 0; i < cB.size(); i++) {
            /* If this box is ticked and is incorrect return false */
            if (cB.get(i).getCheckBox().isSelected() && !cB.get(i).isCorrect()) {
                return false;
            }

            /* If this box is not ticked but the answer is correct return false */
            if (!cB.get(i).getCheckBox().isSelected() && cB.get(i).isCorrect()) {
                return false;
            }
        }

        /* Award appropriate marks */
        setAwardedMarks(getAwardedMarks() + marks);

        /* If all boxes are correct return true */
        return true;
    }

    /**
     * Method to check a set of radio buttons.
     * 
     * @return <code>true</code> if the correct answer is selected;
     *         <code>false</code> otherwise.
     */
    private boolean RadioCheck() {
        /* Loop through all the radio buttons */
        for (int i = 0; i < rB.size(); i++) {
            /* If this is a correct answer and its selected return true */
            if (rB.get(i).isCorrect()
                    && rB.get(i).getRadioButton().isSelected()) {
                setAwardedMarks(getAwardedMarks() + marks);
                return true;
            }
        }

        /* A correct answer was not selected, return false */
        return false;
    }

    /**
     * Checks if the correct answer is selected from the drop down list
     * 
     * @return <code>true</code> if correct answer is selected;
     *         <code>false</code> otherwise.
     */

    private boolean DropDownListCheck() {
        /* Gets the values in the drop down list */
        String str = (String) dropDownList.getValue();

        /* If this is a correct answer and its selected return true */
        for (int i = 0; i < dList.size(); i++) {
            if (dList.get(i).isCorrect() && dList.get(i).getText().equals(str)) {
                setAwardedMarks(getAwardedMarks() + marks);
                return true;
            }
        }
        return false;
    }

    /**
     * Method to handle a correct result
     */
    private void handleCorrect() {
        if (marks == 1) {
            markLabel.setText("Correct! " + getAwardedMarks() + " mark");
        } else {
            markLabel.setText("Correct! " + getAwardedMarks() + " marks");
        }

        disable();
    }

    /**
     * Method to handle an incorrect result
     */
    private void handleIncorrect() {
        markLabel.setText("Incorrect.");

        if (!retry) {
            disable();
        }
    }

    /**
     * Method to disable the set of answer options
     */
    public void disable() {
        /* Disable the appropriate answer objects */
        switch (type) {
        case CHECKBOX:
            for (int i = 0; i < cB.size(); i++) {
                cB.get(i).getCheckBox().setDisable(true);
            }
            break;
        case RADIO:
            for (int i = 0; i < rB.size(); i++) {
                rB.get(i).getRadioButton().setDisable(true);
            }
            break;
        case DROPDOWNLIST:
            dropDownList.setDisable(true);
            break;

        default:
            break;
        }

        /* Disable the mark button */
        getMarkButton().setDisable(true);
    }

    /**
     * Gets the mark button object
     * 
     * @return The mark button object
     */
    public Button getMarkButton() {
        return markButton;
    }

    /**
     * Sets mark button object
     * 
     * @param mark
     *            button object
     */
    public void setMarkButton(Button markButton) {
        this.markButton = markButton;
    }

    /**
     * Gets the boolean variable markCollated
     * 
     * @return <code>true</code> if marks have been collated;
     *         <code>false</otherwise>
     */
    public boolean isMarkCollated() {
        return markCollated;
    }

    /**
     * Sets the boolean variable marksCollated
     * 
     * @param markCollated
     *            Desired boolean variable of markCollated
     */
    public void setMarkCollated(boolean markCollated) {
        this.markCollated = markCollated;
    }

    /**
     * Gets awarded marks for this object
     * 
     * @return Value of the awarded marks
     */
    public int getAwardedMarks() {
        return awardedMarks;
    }

    /**
     * Sets the variable awardedMarks
     * 
     * @param awardedMarks
     *            Number of marks to award
     */
    public void setAwardedMarks(int awardedMarks) {
        this.awardedMarks = awardedMarks;
    }

    /**
     * This class handles the mark button's action
     */
    public class MultipleChoiceCheckHandler implements
            EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent arg0) {
            if (Check()) {
                handleCorrect();
            } else {
                handleIncorrect();
            }
        }
    }

}
