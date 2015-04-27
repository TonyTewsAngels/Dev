package teacheasy.runtime.editor;

import teacheasy.data.multichoice.Answer;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class AnswerProperty {
    private final SimpleStringProperty answer;
    private final SimpleBooleanProperty correct;
    private final SimpleBooleanProperty remove;
    private Answer answerObject;
    
    public AnswerProperty(Answer nAnswerObject) {
        this.answerObject = nAnswerObject;
        this.answer = new SimpleStringProperty(answerObject.getText());
        this.correct = new SimpleBooleanProperty(answerObject.isCorrect());
        this.remove = new SimpleBooleanProperty(false);
    }

    public String getAnswer() {
        return answer.get();
    }
    
    public void setAnswer(String nAnswer) {
        answer.set(nAnswer);
        answerObject.setText(nAnswer);
    }

    public boolean getCorrect() {
        return correct.get();
    }
    
    public void setCorrect(Boolean nCorrect) {
        correct.set(nCorrect);
        answerObject.setCorrect(nCorrect);
    }
    
    public boolean getRemove() {
        return remove.get();
    }
    
    public void setRemove(Boolean nRemove) {
        remove.set(nRemove);
    }
    
    public SimpleBooleanProperty getRemoveProperty() {
        return remove;
    }
    
    public Answer getAnswerObject() {
        return answerObject;
    }
}
