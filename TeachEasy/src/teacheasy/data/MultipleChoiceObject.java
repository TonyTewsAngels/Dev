/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 */
package teacheasy.data;

import java.util.ArrayList;

import teacheasy.data.multichoice.Answer;

/**
 * Encapsulates the data that describes a Multiple Choice 
 * Object as defined in the TeachEasy digital lesson XML format.
 * Extends the page object supertype.
 * 
 * @author  Alistair Jewers
 * @version 1.0 13 Feb 2015
 */
public class MultipleChoiceObject extends PageObject {
    
    /** Enumeration of the possible orientations */
    public static enum Orientation {
        VERTICAL,
        HORIZONTAL;
        
        /**
         * Checks a string against the possible values,
         * returning a match if one exists or a default.
         * 
         * @param str The string to check.
         * @return A matching orientation if one exists.
         */
        public static Orientation check(String str) {
            try {
                return valueOf(str);
            } catch (Exception ex) {
                return VERTICAL;
            }
        }
    }
    
    /** Enumeration of the possible multiple choice types */
    public static enum MultiChoiceType {
        CHECKBOX,
        RADIO,
        DROPDOWNLIST;
        
        /**
         * Checks a string against the possible values,
         * returning a match if one exists or a default.
         * 
         * @param str The string to check.
         * @return A matching type if one exists.
         */
        public static MultiChoiceType check(String str) {
            try {
                return valueOf(str);
            } catch (Exception ex) {
                return CHECKBOX;
            }
        }
    }
    
    /** List of the possible answers */
    private ArrayList<Answer> answers;
    
    /* Data variables */
    private int marks;
    private boolean retry;
    private Orientation orientation;
    private MultiChoiceType multiChoiceType;

    /**
     * Constructor to create the data object with the data parsed
     * from XML.
     * 
     * @param nXStart Relative X axis start position (0 - 1).
     * @param nYStart Relative Y axis start position (0 - 1).
     * @param nOrientation Orientation setting.
     * @param nType Type setting.
     * @param nMarks Marks awarded for a correct answer.
     * @param nRetry Retry setting.
     */
    public MultipleChoiceObject(float nXStart, float nYStart, 
                                Orientation nOrientation,
                                MultiChoiceType nType,
                                int nMarks, boolean nRetry) {
        
        /* Call super constructor */
        super(PageObjectType.MULTIPLE_CHOICE, nXStart, nYStart);
        
        /* Instantiate answer list */
        answers = new ArrayList<Answer>();
        
        /* Instantiate class level data variables */
        this.marks = nMarks;
        this.retry = nRetry;
        this.orientation = nOrientation;
        this.multiChoiceType = nType;
    }

    /** Gets the orientation */
    public Orientation getOrientation() {
        return orientation;
    }

    /** Sets the orientation */
    public void setOrientation(Orientation nOrientation) {
        this.orientation = nOrientation;
    }

    /** Gets the type of multiple choice */
    public MultiChoiceType getMultiChoiceType() {
        return multiChoiceType;
    }

    /** Sets the type of multiple choice */
    public void setType(MultiChoiceType nType) {
        this.multiChoiceType = nType;
    }

    /** Gets the marks available */
    public int getMarks() {
        return marks;
    }

    /** Sets the marks available */
    public void setMarks(int nMarks) {
        this.marks = nMarks;
    }

    /** Checks the retry setting */
    public boolean isRetry() {
        return retry;
    }

    /** Sets the retry setting */
    public void setRetry(boolean nRetry) {
        this.retry = nRetry;
    }
    
    /** Gets the list of answers */
    public ArrayList<Answer> getAnswers() {
        return answers;
    }
    
    /** Clears the list of answers */
    public void clearAnswers() {
        answers.clear();
    }
    
    /** Adds an answers to the list */    
    public void addAnswer(Answer answer) {
        answers.add(answer);
    }
    
    /** Removes an answer if it is in the list*/
    public void removeAnswer(Answer answer) {
        if(answers.contains(answer)) {
            answers.remove(answer);
        }
    }
    
    /** Prints information about the object to the console */
    public void debugPrint() {
        /* Print the supertype data */
        super.debugPrint();
        
        /* Print the data variables */
        System.out.println(", Orientation " + orientation + 
                           ", Type " + multiChoiceType + 
                           ", Marks " + marks + 
                           ", Retry " + retry + ".");
        
        /* Print all the answers */
        for(Answer a : answers) {
            System.out.println("\tAnswer: " + a.getText() + ". Correct: " + a.isCorrect());
        }
        
        /* Print a new line */
        System.out.println("");
    }
}
