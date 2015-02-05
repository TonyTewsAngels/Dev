/*
 * Sam Raeburn
 * Sam Hall
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 */
package teacheasy.data;
/**
 * Class for holding answer box data
 * @author sr896 and sh1157
 *
 */
public class AnswerBoxData extends PageObject {
	
	int characterLimit, marks;
	String correctAnswers;
	boolean retry;
	
	
	public int getCharacterLimit() {
		return characterLimit;
	}


	public void setCharacterLimit(int characterLimit) {
		this.characterLimit = characterLimit;
	}


	public int getMarks() {
		return marks;
	}


	public void setMarks(int marks) {
		this.marks = marks;
	}


	public String getCorrectAnswers() {
		return correctAnswers;
	}


	public void setCorrectAnswers(String correctAnswers) {
		this.correctAnswers = correctAnswers;
	}


	public boolean isRetry() {
		return retry;
	}


	public void setRetry(boolean retry) {
		this.retry = retry;
	}

	public AnswerBoxData(PageObjectType nType, float nXStart, float nYStart) {
		super(nType, nXStart, nYStart);
	}

}
