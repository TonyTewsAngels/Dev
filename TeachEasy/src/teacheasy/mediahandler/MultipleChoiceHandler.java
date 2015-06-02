/*
 * Emmanuel Olutayo
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.mediahandler;

import java.util.ArrayList;
import java.util.List;

import teacheasy.data.MultipleChoiceObject.MultiChoiceType;
import teacheasy.data.MultipleChoiceObject.Orientation;
import teacheasy.data.multichoice.Answer;
import teacheasy.mediahandler.multichoice.MultipleChoice;

import javafx.scene.Group;

/**
 * A class to handle the multiple choice objects on a page.
 * 
 * @author Emmanuel Olutayo
 * @version 1.0 07 Mar 2015
 */
public class MultipleChoiceHandler {
    /* Group reference */
    private Group group;

    /* List of multiple choice objects */
    private List<MultipleChoice> multipleChoice;

    /** Constructor */
    public MultipleChoiceHandler(Group nGroup) {
        /* Set the group reference */
        this.group = nGroup;

        /* initialise the Question list */
        this.multipleChoice = new ArrayList<MultipleChoice>();
    }

    /**
     * Method to add a multiple choice object to a page
     * 
     * @param xStart
     *            X-coordinate of the top left corner of the multiple choice
     *            object
     * @param yStart
     *            Y-coordinate of the top left corner of the multiple choice
     *            object
     * @param answers
     *            List of correct answers
     * @param type
     *            Type of multiple choice. E.g. drop down, radio..
     * @param orientation
     *            Vertical/horizontal orientation of the object
     * @param retry
     *            Enable/disable retry after wrong attempt
     * @param marks
     *            Awarded marks for correct answer
     */
    public void createMultipleChoice(float xStart, float yStart,
            ArrayList<Answer> answers, MultiChoiceType type,
            Orientation orientation, boolean retry, int marks) {
        multipleChoice.add(new MultipleChoice(group, xStart, yStart, answers,
                type, orientation, retry, marks));
    }

    /**
     * Returns the x-coordinate of the starting point
     * 
     * @param questionId
     *            The id of the multiple choice object
     * @return Top left x-coordinate of the object in pixels
     */
    public double getMultiChoiceXStart(int questionId) {
        if (questionId < multipleChoice.size() && questionId >= 0) {
            return multipleChoice.get(questionId).getMultiChoiceXStart();
        } else {
            return 0;
        }
    }

    /**
     * Returns the y-coordinate of the starting point
     * 
     * @param questionId
     *            The id of the multiple choice object
     * @return Top left y-coordinate of the object in pixels
     */
    public double getMultiChoiceYStart(int questionId) {
        if (questionId < multipleChoice.size() && questionId >= 0) {
            return multipleChoice.get(questionId).getMultiChoiceYStart();
        } else {
            return 0;
        }
    }

    /**
     * Returns the x-coordinate of the end point
     * 
     * @param questionId
     *            The id of the multiple choice object
     * @return Bottom right x-coordinate of the object in pixels
     */
    public double getMultiChoiceXEnd(int questionId) {
        if (questionId < multipleChoice.size() && questionId >= 0) {
            return multipleChoice.get(questionId).getMultiChoiceXEnd();
        } else {
            return 0;
        }
    }

    /**
     * Returns the y-coordinate of the end point
     * 
     * @param questionId
     *            The id of the multiple choice object
     * @return Bottom right y-coordinate of the object in pixels
     */
    public double getMultiChoiceYEnd(int questionId) {
        if (questionId < multipleChoice.size() && questionId >= 0) {
            return multipleChoice.get(questionId).getMultiChoiceYEnd();
        } else {
            return 0;
        }
    }

    /**
     * Gets the width of the multiple choice object in pixels
     * 
     * @param questionId
     *            The id of the multiple choice object
     * @return Width of the object in pixels
     */
    public double getMultiChoiceWidth(int questionId) {
        if (questionId < multipleChoice.size() && questionId >= 0) {
            return multipleChoice.get(questionId).getMultiChoiceWidth();
        } else {
            return 0;
        }
    }

    /**
     * Gets the height of the multiple choice object in pixels
     * 
     * @param questionId
     *            The id of the multiple choice object
     * @return The height of the object in pixels
     */
    public double getMultiChoiceHeight(int questionId) {
        if (questionId < multipleChoice.size() && questionId >= 0) {
            return multipleChoice.get(questionId).getMultiChoiceHeight();
        } else {
            return 0;
        }
    }

    /** Grays out all of the multiple choices currently on the page */
    public void DisableAllMultipleChoices() {
        for (int i = 0; i < multipleChoice.size(); i++) {
            multipleChoice.get(i).disable();
        }
    }

    /**
     * Collates the total marks for multiple choice questions on a page
     * 
     * @return <code>int</code> value of the total page marks
     */
    public int totalPageMarks() {
        int totalPageMarks = 0;

        /*
         * Loops through all the multiple choice object on the page collating
         * the marks for the answered ones
         */
        for (int i = 0; i < multipleChoice.size(); i++) {
            if (multipleChoice.get(i).getMarkButton().isDisabled()
                    && !multipleChoice.get(i).isMarkCollated()) {
                totalPageMarks += multipleChoice.get(i).getAwardedMarks();
                multipleChoice.get(i).setMarkCollated(true);
            }
        }

        return totalPageMarks;
    }

    /**
     * Checks if all multiple choices are grayed out
     * 
     * @return <code>true</code> if all multiple choice objects on a page are
     *         grayed out; <code>false</false> otherwise.
     */
    public boolean allMultipleChoicesDisabled() {
        boolean allDisabled = true;
        /*
         * Loops through all the multiple choice object on the page checking if
         * the mark button is disabled
         */
        for (int i = 0; i < multipleChoice.size(); i++) {
            if (!multipleChoice.get(i).getMarkButton().isDisabled()) {
                allDisabled = false;
            }
        }
        return allDisabled;
    }

    /**
     * Clears the multiple choice object
     */
    public void clearMultiChoice() {
        multipleChoice.clear();
    }
}
