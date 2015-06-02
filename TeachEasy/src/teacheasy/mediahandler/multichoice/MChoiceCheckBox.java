/*
 * Emmanuel Olutayo
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.mediahandler.multichoice;

import javafx.scene.control.CheckBox;

/**
 * A class to label right and wrong check boxes
 * 
 * @author Emmanuel Olutayo
 * @version 1.0 07 Mar 2015
 */
public class MChoiceCheckBox {

    private CheckBox checkBox;
    private boolean correct;

    /**
     * Constructor that instantiated the class variables from the parameters
     * @param nCB
     *            CheckBox object to be labelled
     * @param nCorrect
     *            <code>true</code> if object answered correctly;
     *            <code>false</code> otherwise.
     */
    public MChoiceCheckBox(CheckBox nCB, boolean nCorrect) {
        this.checkBox = nCB;
        this.correct = nCorrect;
    }

    /**
     * 
     * @return
     */
    public CheckBox getCheckBox() {
        return checkBox;
    }

    /**
     * 
     * @param ncB
     */
    public void setCheckBox(CheckBox ncB) {
        this.checkBox = ncB;
    }

    /**
     * 
     * @return
     */
    public boolean isCorrect() {
        return correct;
    }

    /**
     * 
     * @param nCorrect
     */
    public void setCorrect(boolean nCorrect) {
        this.correct = nCorrect;
    }
}
