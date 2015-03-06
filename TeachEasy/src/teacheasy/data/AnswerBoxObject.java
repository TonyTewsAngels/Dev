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
public class AnswerBoxObject extends PageObject {
	private int characterLimit;
	private int marks;
	private String correctAnswers;
	private boolean retry;
	private boolean numerical;
	
	/** Constructor Method */
	public AnswerBoxObject(float nXStart, float nYStart, int nCharacterLimit,
						   int nMarks, String nCorrectAnswers, boolean nRetry, boolean nNumerical) {
		
		/* Call Superconstructor */
		super(PageObjectType.ANSWER_BOX, nXStart, nYStart);
		
		/* Instantiate class level variables */
		this.characterLimit = nCharacterLimit;
		this.marks = nMarks;
		this.correctAnswers = nCorrectAnswers;
		this.retry = nRetry;
		this.numerical = nNumerical;
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
	
	/** Check the numerical flag */
    public boolean isNumerical() {
        return numerical;
    }

    /** Set the numerical flag */
    public void setNumerical(boolean retry) {
        this.numerical = retry;
    }
	
	/** Prints information about the object to the screen */
    public void debugPrint() {
        super.debugPrint();
        
        System.out.println(", Character Limit " + characterLimit + 
                           ", Marks " + marks + 
                           ", Correct Answers " + correctAnswers +
                           ", Retry " + retry + ".\n");
    }
}

