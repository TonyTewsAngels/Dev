/*
 * Sam Raeburn
 * Sam Hall
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 */
package teacheasy.data;

/**
 * Class for holding answer box data
 * 
 * @author	sr896 and sh1157
 * @version	1.1 08 Feb 2015
 */
public class AnswerBoxData extends PageObject {
	private int characterLimit;
	private int marks;
	private String correctAnswers;
	private boolean retry;
	
	/** Constructor Method */
	public AnswerBoxData(float nXStart, float nYStart, int nCharacterLimit,
						 int nMarks, String nCorrectAnswers, boolean nRetry) {
		
		/* Call Superconstructor */
		super(PageObjectType.ANSWER_BOX, nXStart, nYStart);
		
		/* Instantiate class level variables */
		this.characterLimit = nCharacterLimit;
		this.marks = nMarks;
		this.correctAnswers = nCorrectAnswers;
		this.retry = nRetry;
	}
	
	/** Get the character limit  */
	public int getCharacterLimit() {
		return characterLimit;
	}
	
	/** Set the character limit */
	public void setCharacterLimit(int characterLimit) {
		this.characterLimit = characterLimit;
	}

	/** Get the available marks */
	public int getMarks() {
		return marks;
	}

	/** Set the available marks */
	public void setMarks(int marks) {
		this.marks = marks;
	}
	
	/** Get the correct answers string */
	public String getCorrectAnswers() {
		return correctAnswers;
	}

	/** Set the correct answers string */
	public void setCorrectAnswers(String correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	/** Check the retry flag */
	public boolean isRetry() {
		return retry;
	}

	/** Set the retry flag */
	public void setRetry(boolean retry) {
		this.retry = retry;
	}
}

