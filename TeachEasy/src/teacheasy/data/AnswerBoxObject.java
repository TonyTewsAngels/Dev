/*
 * Sam Raeburn & Sam Hall
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 */
package teacheasy.data;

import java.util.ArrayList;
import teacheasy.data.multichoice.Answer;

/**
 * Encapsulates the data that describes an Answer Box Object
 * as defined in the TeachEasy digital lesson XML format.
 * Extends the page object supertype.
 * 
 * @author	Sam Raeburn and Sam Hall
 * @version	1.1 08 Feb 2015
 */
public class AnswerBoxObject extends PageObject {
    
    /* Data variables */
	private int characterLimit;
	private int marks;
	private boolean retry;
	private boolean numerical;
	private float upperBound;
	private float lowerBound;
	
	/* List of possible answers */
	private ArrayList<Answer> answers;
	
	/**
	 * Constructor to create the data object with the data parsed
	 * from XML.
	 * 
	 * @param nXStart Relative start position.
	 * @param nYStart Relative end position.
	 * @param nCharacterLimit Character limit for the answer field.
	 * @param nMarks Marks awarded for a correct answer.
	 * @param nRetry Indicates whether the question is retry-able.
	 * @param nNumerical Indicates if the answer is numerical.
	 * @param nUpperBound The upper limit of the numerical answer range.
	 * @param nLowerBound The lower limit of the numerical answer range.
	 * @param nAnswers The possible answers as a varargs string set.
	 */
	public AnswerBoxObject(float nXStart, float nYStart, int nCharacterLimit,
						   int nMarks, boolean nRetry, boolean nNumerical,
						   float nUpperBound, float nLowerBound, String... nAnswers) {
		
		/* Call Superconstructor */
		super(PageObjectType.ANSWER_BOX, nXStart, nYStart);
		
		/* Initialise class level data variables */
		this.characterLimit = nCharacterLimit;
		this.marks = nMarks;
		this.retry = nRetry;
		this.numerical = nNumerical;
		this.upperBound = nUpperBound;
		this.lowerBound = nLowerBound;
		
		/* Instantiate answer list */
		answers = new ArrayList<Answer>();
		
		/* Add the possible answers */
		for(String a : nAnswers) {
		    answers.add(new Answer(a, true));
		}
	}
	
	/** Gets the character limit  */
	public int getCharacterLimit() {
		return characterLimit;
	}
	
	/** Sets the character limit */
	public void setCharacterLimit(int characterLimit) {
		this.characterLimit = characterLimit;
	}

	/** Gets the available marks */
	public int getMarks() {
		return marks;
	}

	/** Sets the available marks */
	public void setMarks(int marks) {
		this.marks = marks;
	}
	
	/** Adds an answer */
	public void addAnswer(Answer answer) {
	    answers.add(answer);
	}
	
	/** Removes an answer */
	public void removeAnswer(Answer answer) {
	    if(answers.contains(answer)) {
	        answers.remove(answer);
	    }
	}
	
	/** Returns the array list of answers */
	public ArrayList<Answer> getAnswers() {
	    return answers;
	}

	/** Checks the retry flag */
	public boolean isRetry() {
		return retry;
	}

	/** Sets the retry flag */
	public void setRetry(boolean retry) {
		this.retry = retry;
	}
	
	/** Checks the numerical flag */
    public boolean isNumerical() {
        return numerical;
    }

    /** Sets the numerical flag */
    public void setNumerical(boolean retry) {
        this.numerical = retry;
    }
    
    /** Gets the upper bound in the numerical answer range */
	public float getUpperBound() {
        return upperBound;
    }

	/** Sets the upper bound in the numerical answer range */
    public void setUpperBound(float nUpperBound) {
        this.upperBound = nUpperBound;
    }

    /** Gets the lower bound in the numerical answer range */
    public float getLowerBound() {
        return lowerBound;
    }

    /** Sets the lower bound in the numerical answer range */
    public void setLowerBound(float nLowerBound) {
        this.lowerBound = nLowerBound;
    }
    
	/** Prints information about the object to the screen */
    public void debugPrint() {
        /* Print the super type info */
        super.debugPrint();
        
        /* Print the data variables */
        System.out.println(", Character Limit " + characterLimit + 
                           ", Marks " + marks + 
                           ", Retry " + retry +
                           ", Numerical " + numerical +
                           ", Upper Bound " + upperBound +
                           ", Lower Bound " + lowerBound +
                           ", Correct Answers: ");
        
        /* Print all the possible answers */
        for(Answer a : answers) {
            System.out.println(a.getText());
        }
    }
}

