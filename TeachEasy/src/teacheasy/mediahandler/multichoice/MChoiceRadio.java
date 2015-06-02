/*
 * Emmanuel Olutayo
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.mediahandler.multichoice;

import javafx.scene.control.RadioButton;

/**
 * A class to label right and wrong radio buttons
 * 
 * @author Emmanuel Olutayo
 * @version 1.0 07 Mar 2015
 */
public class MChoiceRadio {

    private RadioButton radioButton;
    private boolean correct;

    /**
     * Constructor that instantiated the class variables from the parameters
     * 
     * @param nRb
     *            RadioButton object which is to be labelled
     * @param nCorrect
     *            <code>true</code> if object answered correctly;
     *            <code>false</code> otherwise.
     */
    public MChoiceRadio(RadioButton nRb, boolean nCorrect) {
        this.radioButton = nRb;
        this.correct = nCorrect;
    }

    /**
     * 
     * @return RadioButton object
     */
    public RadioButton getRadioButton() {
        return radioButton;
    }

    /**
     * 
     * @param nrB
     *            RadioButton object
     */
    public void setRadioButton(RadioButton nrB) {
        this.radioButton = nrB;
    }

    /**
     * 
     * @return <code>true</code> if answer is correct; <code>false</code>
     *         otherwise.
     */
    public boolean isCorrect() {
        return correct;
    }

    /**
     * Sets the boolean variable correct
     * 
     * @param ncorrect
     *            Desired boolean value for the variable correct
     */
    public void setCorrect(boolean ncorrect) {
        this.correct = ncorrect;
    }

}
