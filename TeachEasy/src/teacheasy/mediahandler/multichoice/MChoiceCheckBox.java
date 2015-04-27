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
 * @author	Emmanuel Olutayo
 * @version	1.0 07 Mar 2015
 */
public class MChoiceCheckBox {

	private CheckBox checkBox;
	private boolean correct;

	public MChoiceCheckBox(CheckBox nCB, boolean nCorrect){
		this.checkBox = nCB;
		this.correct = nCorrect;
	}

	public CheckBox getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(CheckBox ncB) {
		this.checkBox = ncB;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean nCorrect) {
		this.correct = nCorrect;
	}
}
