/*
 * Copyright (C) 2015 Sofia Software Solutions
 */
package teacheasy.mediahandler;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import teacheasy.data.multichoice.Answer;
import teacheasy.mediahandler.answerbox.AnswerBox;

/**
 * Class to draw answer boxes with the specified x & y location
 * 
 * @author Daniel Berhe
 * @author Jake Ransom
 * @version 1.0
 * 
 */
public class AnswerBoxHandler {

    private List<AnswerBox> answerBox;
    private Group group;

    public AnswerBoxHandler(Group nGroup) {
        this.answerBox = new ArrayList<AnswerBox>();
        this.group = nGroup;
    }

    /**
     * Creates a new answer box object
     * 
     * @param xStart X coordinate of the object
     * @param yStart Y coordinate of the object
     * @param characterLimit Maximum allowed characters
     * @param retry To allow retry
     * @param marks Possible marks for a correct answer
     * @param isNumerical Whether or not numerical input is expected
     * @param upperBound Upper range of numerical solution
     * @param lowerBound Lower range of numerical solution
     * @param answers Solutions to questions
     */
    public void createAnswerBox(double xStart, double yStart,
                                int characterLimit, boolean retry, int marks, boolean isNumerical,
                                float upperBound, float lowerBound, ArrayList<Answer> answers) {

        answerBox.add(new AnswerBox(xStart, yStart, characterLimit, retry,
                marks, isNumerical, group, upperBound, lowerBound, answers));
    }

    /** Grays out all of the answer boxes on a page */
    public void DisableAllAnswerBoxes() {
        for (int i = 0; i < answerBox.size(); i++) {
            answerBox.get(i).answerField.setDisable(true);
            answerBox.get(i).checkAnswerButton.setDisable(true);
        }
    }

    /**
     * Grays out all of the answer boxes on a page
     * 
     * @return <code>true</code> if all answer boxes are disabled
     */
    public boolean allBoxesDisabled() {

        boolean allDisabled = true;

        for (int i = 0; i < answerBox.size(); i++) {
            if (!answerBox.get(i).checkAnswerButton.isDisabled()) {
                allDisabled = false;
            }
        }
        return allDisabled;
    }

    /**
     * Method that adds the marks for answer boxes on a page
     * 
     * @return Total marks for a page
     */
    public int currentPageMarks() {
        int totalPageMarks = 0;
        for (int i = 0; i < answerBox.size(); i++) {
            if (answerBox.get(i).checkAnswerButton.isDisabled()
                    && !answerBox.get(i).isMarkCollated()) {

                /* Add marks to total page marks */
                totalPageMarks += answerBox.get(i).getAwardedMarks();

                answerBox.get(i).setMarkCollated(true);
            }
        }
        return totalPageMarks;
    }

    /** Clears the answer box */
    public void clearAnswerBoxes() {
        answerBox.clear();
    }

    /**
     * Gets the height of the answer box
     * 
     * @param answerBoxId Id of the answer box
     * @return Height of the answer box
     */
    public double getAnswerBoxHeight(int answerBoxId) {
        if (answerBoxId < answerBox.size() && answerBoxId >= 0) {
            return answerBox.get(answerBoxId).getHeight();
        }

        return 0.0;
    }

    /**
     * Gets the width of the answer box
     * 
     * @param answerBoxId Id of the answer box
     * @return Width of the answer box
     */
    public double getAnswerBoxWidth(int answerBoxId) {
        if (answerBoxId < answerBox.size() && answerBoxId >= 0) {
            return answerBox.get(answerBoxId).getWidth();
        }

        return 0.0;
    }

    /** Debug print for answer box handler */
    public void debugPrint() {
        System.out.println("### Answer Box Handler ###");
        System.out.println("Handling: " + answerBox.size());
    }
}
