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
 * @author	Emmanuel Olutayo
 * @version	1.0 07 Mar 2015
 */
public class MChoiceRadio {
    
	private RadioButton radioButton;
	private boolean correct;

	public MChoiceRadio (RadioButton nRb, boolean nCorrect ){
		this.radioButton = nRb;
		this.correct =nCorrect;
	}

	public RadioButton getRadioButton() {
		return radioButton;
	}

	public void setRadioButton(RadioButton nrB) {
		this.radioButton = nrB;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean ncorrect) {
		this.correct = ncorrect;
	}

}
