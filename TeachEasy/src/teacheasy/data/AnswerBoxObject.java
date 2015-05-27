/*
 * Sam Raeburn
 * Sam Hall
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 */
package teacheasy.data;

import java.util.ArrayList;

import teacheasy.data.multichoice.Answer;

/**
 * Class for holding answer box data
 * 
 * @author	sr896 and sh1157
 * @version	1.1 08 Feb 2015
 */
public class AnswerBoxObject extends PageObject {
	private int characterLimit;
	private int marks;
	private boolean retry;
	private boolean numerical;
	private float upperBound;
	private float lowerBound;
	
	private ArrayList<Answer> answers;
	
	/** Constructor Method */
	public AnswerBoxObject(float nXStart, float nYStart, int nCharacterLimit,
						   int nMarks, boolean nRetry, boolean nNumerical,
						   float nUpperBound, float nLowerBound, String... nAnswers) {
		
		/* Call Superconstructor */
		super(PageObjectType.ANSWER_BOX, nXStart, nYStart);
		
		/* Instantiate class level variables */
		this.characterLimit = nCharacterLimit;
		this.marks = nMarks;
		this.retry = nRetry;
		this.numerical = nNumerical;
		this.upperBound = nUpperBound;
		this.lowerBound = nLowerBound;
		
		answers = new ArrayList<Answer>();
		
		for(String a : nAnswers) {
		    answers.add(new Answer(a, true));
		}
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
	
	/** Add an answer */
	public void addAnswer(Answer answer) {
	    answers.add(answer);
	}
	
	/** Remove an answer */
	public void removeAnswer(Answer answer) {
	    if(answers.contains(answer)) {
	        answers.remove(answer);
	    }
	}
	
	/** Returns the array list of answers */
	public ArrayList<Answer> getAnswers() {
	    return answers;
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
    
	public float getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(float nUpperBound) {
        this.upperBound = nUpperBound;
    }

    public float getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(float nLowerBound) {
        this.lowerBound = nLowerBound;
    }
    
	/** Prints information about the object to the screen */
    public void debugPrint() {
        super.debugPrint();
        
        System.out.println(", Character Limit " + characterLimit + 
                           ", Marks " + marks + 
                           ", Retry " + retry +
                           ", Correct Answers: ");
        
        for(Answer a : answers) {
            System.out.println(a.getText());
        }
    }
}

