/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.data;

import java.util.ArrayList;
import java.util.List;

import teacheasy.data.lessondata.*;

/**
 * This class encapsulates a single Lesson
 * within the TeachEasy product.
 * 
 * @version     1.1 08 Feb 2015
 * @author      Alistair Jewers
 */
public class Lesson {
	
    /** Container for the pages within this lesson */
    public List<Page> pages;

    /** Metadata Object */
    public LessonInfo lessonInfo;

    /** Default Settings Object */
    public LessonDefaultSettings defaultSettings;

    /** Grade Settings Object */
	public LessonGradeSettings gradeSettings;

    /** Constructor Method */
    public Lesson(String author, String version, String comment,
                  String dateCreated, int totalMarks, String lessonName,
                  String dfBackgroundColour, String dfFont, int dfFontSize,
                  String dfFontColour, String dfLineColour, String dfFillColour,
                  int passMark, String passMessage, String failMessage) {
		
        /* Instantiate the pages container */
        pages = new ArrayList<Page>();
    		
        /* Instantiate the metadata object */
        lessonInfo = new LessonInfo(lessonName, author, version,
                                    comment, dateCreated, totalMarks);

        /* Instantiate the default settings */
        defaultSettings = new LessonDefaultSettings(dfFontSize, dfFont,
                                    dfBackgroundColour, dfFontColour,
                                    dfLineColour, dfFillColour);
		
        /* Instantiate the grade settings */
        gradeSettings = new LessonGradeSettings(passMark, passMessage,
                                    failMessage);
	}
	
    /** Blank constructor (debug purposes only! */
    public Lesson() {
        /* Instantiate the pages container */
        pages = new ArrayList<Page>();
        
        /* Instantiate the metadata object */
        lessonInfo = new LessonInfo("Lesson", "Author", "1.0",
                                    "Comment", "01/01/2001", 1);
        
        /* Instantiate the default settings */
        defaultSettings = new LessonDefaultSettings(12, "Arial",
                                    "#00000000", "#00000000",
                                    "#00000000", "#00000000");
        
        /* Instantiate the grade settings */
        gradeSettings = new LessonGradeSettings(1, "pass", "fail");
	}
}
