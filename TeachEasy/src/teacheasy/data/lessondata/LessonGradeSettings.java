/*
 * Alistair Jewers
 * 
 * Copyright (C) Sofia Software Solutions
 */
package teacheasy.data.lessondata;

/**
 * Encapsulates the data that describes the grade settings
 * for a lesson as defined in the TeachEasy digital lesson 
 * XML format.
 * 
 * @author  Alistair Jewers
 * @version 1.0 08 Feb 2015
 */
public class LessonGradeSettings {
    
    /* Data variables */
    private int passBoundary;
    private String passMessage;
    private String failMessage;
    
    /**
     * Constructor to create the data object with the data parsed
     * from XML.
     * 
     * @param nPassBoundary The pass mark.
     * @param nPassMessage The message to display to passing students.
     * @param nFailMessage The message to display to failing students.
     */
    public LessonGradeSettings(int nPassBoundary, String nPassMessage,
                               String nFailMessage) {
        
        /* Initialise class level data variables */
        this.setPassBoundary(nPassBoundary);
        this.setPassMessage(nPassMessage);
        this.setFailMessage(nFailMessage);
    }

    /** Gets the pass mark */
    public int getPassBoundary() {
        return passBoundary;
    }

    /** Sets the pass mark */
    public void setPassBoundary(int nPassBoundary) {
        this.passBoundary = nPassBoundary;
    }

    /** Gets the message to display if a student passes */
    public String getPassMessage() {
        return passMessage;
    }

    /** Sets the message to display if a student passes */
    public void setPassMessage(String nPassMessage) {
        this.passMessage = nPassMessage;
    }
    
    /** Gets the message to display if a student fails */
    public String getFailMessage() {
        return failMessage;
    }

    /** Sets the message to display if a student fails */
    public void setFailMessage(String nFailMessage) {
        this.failMessage = nFailMessage;
    }
}

