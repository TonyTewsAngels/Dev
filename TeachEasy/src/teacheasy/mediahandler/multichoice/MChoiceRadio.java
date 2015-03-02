package teacheasy.mediahandler.multichoice;

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
