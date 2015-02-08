/*
 * Alistair Jewers
 * 
 * Copyright (C) Sofia Software Solutions
 * 
 */
package teacheasy.data.lessondata;

/**
 * A class to encapsulate the grade
 * settings for a lesson.
 * 
 * @author Alistair Jewers
 * @version 1.0 08 Feb 2015
 */
public class LessonGradeSettings {
    private int passBoundary;
    private String passMessage;
    private String failMessage;
    
    /** Constructor Method */
    public LessonGradeSettings(int nPassBoundary, String nPassMessage,
                               String nFailMessage) {
        
        /* Initialise class level fields */
        this.setPassBoundary(nPassBoundary);
        this.setPassMessage(nPassMessage);
        this.setFailMessage(nFailMessage);
    }

    /** Get the pass mark */
    public int getPassBoundary() {
        return passBoundary;
    }

    /** Set the pass mark */
    public void setPassBoundary(int nPassBoundary) {
        this.passBoundary = nPassBoundary;
    }

    /** Get the message to display if a student passes */
    public String getPassMessage() {
        return passMessage;
    }

    /** Set the message to display if a student passes */
    public void setPassMessage(String nPassMessage) {
        this.passMessage = nPassMessage;
    }
    
    /** Get the message to display if a student fails */
    public String getFailMessage() {
        return failMessage;
    }

    /** Set the message to display if a student fails */
    public void setFailMessage(String nFailMessage) {
        this.failMessage = nFailMessage;
    }
}

