/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved. 
 */
package teacheasy.runtime.editor;

import teacheasy.data.multichoice.Answer;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Wraps an answer object in simple properties to allow it to
 * be included in table rows in the properties pane.
 * 
 * @author Alistair Jewers
 * @version 1.0 21 Apr 2015
 */
public class AnswerProperty {
    /* Simple properties can be edited by the cell factory */
    private final SimpleStringProperty answer;
    private final SimpleBooleanProperty correct;
    private final SimpleBooleanProperty remove;
    
    /* The answer object to wrap*/
    private Answer answerObject;
    
    /**
     * Constructor to build the wrapper from the given answer object.
     * 
     * @param nAnswerObject The answer object to wrap.
     */
    public AnswerProperty(Answer nAnswerObject) {
        /* Set the reference */
        this.answerObject = nAnswerObject;
        
        /* Copy the data */
        this.answer = new SimpleStringProperty(answerObject.getText());
        this.correct = new SimpleBooleanProperty(answerObject.isCorrect());
        this.remove = new SimpleBooleanProperty(false);
    }

    /**
     * Gets the answer string.
     */
    public String getAnswer() {
        return answer.get();
    }
    
    /**
     * Sets the answer string.
     */
    public void setAnswer(String nAnswer) {
        answer.set(nAnswer);
        answerObject.setText(nAnswer);
    }

    /**
     * Gets the correct property setting.
     */
    public boolean getCorrect() {
        return correct.get();
    }
    
    /**
     * Sets the correct property setting.
     */
    public void setCorrect(Boolean nCorrect) {
        correct.set(nCorrect);
        answerObject.setCorrect(nCorrect);
    }
    
    /**
     * Gets the remove property setting. 
     */
    public boolean getRemove() {
        return remove.get();
    }
    
    /**
     * Sets the remove property setting.
     */
    public void setRemove(Boolean nRemove) {
        remove.set(nRemove);
    }
    
    /**
     * Get the remove property itself.
     */
    public SimpleBooleanProperty getRemoveProperty() {
        return remove;
    }
    
    /**
     * Gets the answer object.
     */
    public Answer getAnswerObject() {
        return answerObject;
    }
}
