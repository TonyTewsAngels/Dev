/*
 * Emmanuel Olutayo
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */

package teacheasy.mediahandler.multichoice;

/**
 * A class to label right and wrong radio buttons 
 * 
 * @author	Emmanuel Olutayo
 * @version	1.0 07 Mar 2015
 */

import javafx.scene.control.RadioButton;

public class MChoiceRadio {
	private RadioButton rB;
	private boolean correct;

	public MChoiceRadio (RadioButton nRb, boolean nCorrect ){
		this.rB = nRb;
		this.correct =nCorrect;
	}

	public RadioButton getrB() {
		return rB;
	}

	public void setrB(RadioButton nrB) {
		this.rB = nrB;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean ncorrect) {
		this.correct = ncorrect;
	}

}
