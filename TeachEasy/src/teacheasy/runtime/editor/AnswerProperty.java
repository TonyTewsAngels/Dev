package teacheasy.runtime.editor;

import teacheasy.data.multichoice.Answer;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class AnswerProperty {
    private final SimpleStringProperty answer;
    private final SimpleBooleanProperty correct;
    private Answer answerObject;
    
    public AnswerProperty(Answer nAnswerObject) {
        this.answerObject = nAnswerObject;
        this.answer = new SimpleStringProperty(answerObject.getText());
        this.correct = new SimpleBooleanProperty(answerObject.isCorrect());
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
}
