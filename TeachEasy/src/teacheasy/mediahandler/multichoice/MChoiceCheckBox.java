/*
 * Emmanuel Olutayo
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.mediahandler.multichoice;

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
