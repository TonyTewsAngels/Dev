package teacheasy.data;

import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceObject extends PageObject {
    public static enum Orientation {
        VERTICAL,
        HORIZONTAL
    }
    
    public static enum MultiChoiceType {
        CHECKBOX,
        RADIO,
        DROPDOWNLIST
    }
    
    public List<String> correctAnswers;
    public List<String> incorrectAnswers;
    
    private Orientation orientation;
    private MultiChoiceType type;
    private int marks;
    private boolean retry;
    
    /** Constructor method */
    public MultipleChoiceObject(float nXStart, float nYStart, 
                                Orientation nOrientation,
                                MultiChoiceType nType,
                                int nMarks, boolean nRetry) {
        
        /* Call super constructor */
        super(PageObjectType.MULTIPLE_CHOICE, nXStart, nYStart);
        
        /* Instantiate answer lists */
        correctAnswers = new ArrayList<String>();
        incorrectAnswers = new ArrayList<String>();
        
        /* Instantiate class level variables */
        this.orientation = nOrientation;
        this.type = nType;
        this.marks = nMarks;
        this.retry = nRetry;
    }

    /* Get and set methods */
    
    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation nOrientation) {
        this.orientation = nOrientation;
    }

    public MultiChoiceType getType() {
        return type;
    }

    public void setType(MultiChoiceType nType) {
        this.type = nType;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int nMarks) {
        this.marks = nMarks;
    }

    public boolean isRetry() {
        return retry;
    }

    public void setRetry(boolean nRetry) {
        this.retry = nRetry;
    }
    
}
