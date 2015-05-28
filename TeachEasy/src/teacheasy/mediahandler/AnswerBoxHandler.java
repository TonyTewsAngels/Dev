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
 * @author Daniel Berhe and Jake Ransom
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

    /** Creates a new answer box */
    public void createAnswerBox(double xStart, double yStart,
                                int characterLimit, boolean retry, int marks, boolean isNumerical,
                                float upperBound, float lowerBound, ArrayList<Answer> answers) {
        
        answerBox.add(new AnswerBox(xStart, yStart, characterLimit, retry,
                                    marks, isNumerical, group, upperBound, 
                                    lowerBound, answers));
    }

    /** Grays out all of the answer boxes on a page */
    public void DisableAllAnswerBoxes() {
        for (int i = 0; i < answerBox.size(); i++) {
            answerBox.get(i).answerField.setDisable(true);
            answerBox.get(i).checkAnswerButton.setDisable(true);
        }
    }

    /** Checks if all boxes are grayed out */
    public boolean allBoxesDisabled() {
        boolean allDisabled = true;
        for (int i = 0; i < answerBox.size(); i++) {
            if (!answerBox.get(i).checkAnswerButton.isDisabled()) {
                allDisabled = false;
            }
        }
        return allDisabled;
    }

    /** Method that adds the marks for answer boxes on a page */
    public int currentPageMarks() {
        int totalPageMarks = 0;
        for (int i = 0; i < answerBox.size(); i++) {
            if (answerBox.get(i).checkAnswerButton.isDisabled()
                    && !answerBox.get(i).isMarkCollated()) {
                totalPageMarks += answerBox.get(i).getAwardedMarks();
                answerBox.get(i).setMarkCollated(true);
            }
        }
        return totalPageMarks;
    }
    
    public void clearAnswerBoxes() {
        answerBox.clear();
    }

    public void debugPrint() {
        System.out.println("### Answer Box Handler ###");
        System.out.println("Handling: " + answerBox.size());
    }
}
