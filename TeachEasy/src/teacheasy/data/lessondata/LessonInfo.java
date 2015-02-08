package teacheasy.data.lessondata;

public class LessonInfo {
    private String lessonName;
    private String author;
    private String version;
    private String comment;
    private String dateCreated;
    private int totalMarks;
    
    /** Constructor method */
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

    /** Get the lesson name */
    public String getLessonName() {
        return lessonName;
    }

    /** Set the lesson name */
    public void setLessonName(String nLessonName) {
        this.lessonName = nLessonName;
    }

    /** Get the lesson author */
    public String getAuthor() {
        return author;
    }

    /** Set the lesson author */
    public void setAuthor(String nAuthor) {
        this.author = nAuthor;
    }

    /** Get the version tag */
    public String getVersion() {
        return version;
    }

    /** Set the version tag */
    public void setVersion(String nVersion) {
        this.version = nVersion;
    }

    /** Get the description */
    public String getComment() {
        return comment;
    }

    /** Set the description */
    public void setComment(String nComment) {
        this.comment = nComment;
    }

    /** Get the date created */
    public String getDateCreated() {
        return dateCreated;
    }

    /** Set the date created */
    public void setDateCreated(String nDateCreated) {
        this.dateCreated = nDateCreated;
    }

    /** Get the total marks */
    public int getTotalMarks() {
        return totalMarks;
    }

    /** Set the total marks */
    public void setTotalMarks(int nTotalMarks) {
        this.totalMarks = nTotalMarks;
    }
    
}
