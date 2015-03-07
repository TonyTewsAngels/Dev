/*
 * Emmanuel Olutayo
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.mediahandler.multichoice;

/**
 * A class to label right and wrong check boxes 
 * 
 * @author	Emmanuel Olutayo
 * @version	1.0 07 Mar 2015
 */

import javafx.scene.control.CheckBox;

public class MChoiceCheckBox {

	private CheckBox cB;
	private boolean correct;

	public MChoiceCheckBox(CheckBox nCB, boolean nCorrect){
		this.cB = nCB;
		this.correct = nCorrect;
	}

	public CheckBox getcB() {
		return cB;
	}

	public void setcB(CheckBox ncB) {
		this.cB = ncB;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean nCorrect) {
		this.correct = nCorrect;
	}



}
