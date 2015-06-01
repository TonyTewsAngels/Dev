/*
 * Emmanuel Olutayo & Alistair Jewers
 * 
 * Copyright (c) sofia Software Solutions, All Rights Reserved.
 */
package teacheasy.data.multichoice;

/**
 * Encapsulates the data that describes an Answer in either
 * a multiple choice object or an answer box object as
 * defined in the TeachEasy digital lesson XML format.
 * 
 * @author  Alistair Jewers
 * @version 1.0 06 Feb 2015
 */
public class Answer {
    /** The text for this answer */
    private String text;
    
    /** Whether or not the answer is correct */
    private boolean correct;
    
    /**
     * Constructor method to create an answer object.
     * 
     * @param nText The answer text.
     * @param nCorrect Whether or not the answer is correct.
     */
    public Answer(String nText, boolean nCorrect) {
        this.text = nText;
        this.correct = nCorrect;
    }
    
    /** Gets the answer text */
    public String getText() {
        return text;
    }
    
    /** Sets the answer text */
    public void setText(String nText) {
        this.text = nText;
    }
    
    /** Checks if the answer is correct */
    public boolean isCorrect() {
        return correct;
    }
    
    /** Sets the correct setting */
    public void setCorrect(boolean nCorrect) {
        this.correct = nCorrect;
    }
}
