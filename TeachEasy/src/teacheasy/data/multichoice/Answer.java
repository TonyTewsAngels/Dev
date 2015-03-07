/*
 * Copyright (c) sofia Software Solutions, All Rights Reserved.
 */
package teacheasy.data.multichoice;

/**
 * This class encapsulates one answer within
 * a multiple choice set.
 * 
 * @author Alistair Jewers
 * @version 1.0 06 Feb 2015
 */
public class Answer {
    private String text;
    private boolean correct;
    
    public Answer(String nText, boolean nCorrect) {
        this.text = nText;
        this.correct = nCorrect;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String nText) {
        this.text = nText;
    }
    
    public boolean isCorrect() {
        return correct;
    }
    
    public void setCorrect(boolean nCorrect) {
        this.correct = nCorrect;
    }
}
