/*
 * Alistair Jewers
 * 
 * Copyright (C) Sofia Software Solutions
 */
package teacheasy.data.lessondata;

/**
 * Encapsulates the data that describes the information
 * for a lesson as defined in the TeachEasy digital lesson 
 * XML format.
 * 
 * @author  Alistair Jewers
 * @version 1.0 08 Feb 2015
 */
public class LessonInfo {
    
    /* Data variables */
    private String lessonName;
    private String author;
    private String version;
    private String comment;
    private String dateCreated;
    private int totalMarks;
    
    /**
     * Constructor to create the data object with the data parsed
     * from XML.
     * 
     * @param nLessonName The title of the lesson.
     * @param nAuthor The lesson creator.
     * @param nVersion Lesson version.
     * @param nComment Description of the lesson.
     * @param nDateCreated Date
     * @param nTotalMarks
     */
    public LessonInfo(String nLessonName, String nAuthor, String nVersion,
                      String nComment, String nDateCreated, int nTotalMarks) {
        
        /* Initialise class level variables */
        this.setLessonName(nLessonName);
        this.setAuthor(nAuthor);
        this.setVersion(nVersion);
        this.setComment(nComment);
        this.setDateCreated(nDateCreated);
        this.setTotalMarks(nTotalMarks);
    }

    /** Gets the lesson title */
    public String getLessonName() {
        return lessonName;
    }

    /** Sets the lesson title */
    public void setLessonName(String nLessonName) {
        this.lessonName = nLessonName;
    }

    /** Gets the lesson author */
    public String getAuthor() {
        return author;
    }

    /** Sets the lesson author */
    public void setAuthor(String nAuthor) {
        this.author = nAuthor;
    }

    /** Gets the version tag */
    public String getVersion() {
        return version;
    }

    /** Sets the version tag */
    public void setVersion(String nVersion) {
        this.version = nVersion;
    }

    /** Gets the description */
    public String getComment() {
        return comment;
    }

    /** Sets the description */
    public void setComment(String nComment) {
        this.comment = nComment;
    }

    /** Gets the date created */
    public String getDateCreated() {
        return dateCreated;
    }

    /** Sets the date created */
    public void setDateCreated(String nDateCreated) {
        this.dateCreated = nDateCreated;
    }

    /** Gets the total marks */
    public int getTotalMarks() {
        return totalMarks;
    }

    /** Sets the total marks */
    public void setTotalMarks(int nTotalMarks) {
        this.totalMarks = nTotalMarks;
    }
}
