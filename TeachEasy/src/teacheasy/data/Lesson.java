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
    public Lesson(LessonInfo nLessonInfo, 
                  LessonDefaultSettings nDFSettings,
                  LessonGradeSettings nGradeSettings) {
		
        /* Instantiate the pages container */
        pages = new ArrayList<Page>();
    		
        /* Instantiate the metadata object */
        this.lessonInfo = nLessonInfo;

        /* Instantiate the default settings */
        this.defaultSettings = nDFSettings;
		
        /* Instantiate the grade settings */
        this.gradeSettings = nGradeSettings;
    }
	
    /** Blank constructor */
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
    
    /** Prints the Lesson object data to console */
    public void debugPrint() {
        System.out.println("Author: " + lessonInfo.getAuthor());
        System.out.println("Version: " + lessonInfo.getVersion());
        System.out.println("Comment: " + lessonInfo.getComment());
        System.out.println("Date Created: " + lessonInfo.getDateCreated());
        System.out.println("Total Marks: " + lessonInfo.getTotalMarks());
        System.out.println("Lesson Name: " + lessonInfo.getLessonName() + "\n");
        
        System.out.println("BG Color: " + defaultSettings.getBackgroundColour());
        System.out.println("Font: " + defaultSettings.getFont());
        System.out.println("Font Size: " + defaultSettings.getFontSize());
        System.out.println("Font Color: " + defaultSettings.getFontColour() + "\n");
        
        System.out.println("Pass Boundary: " + gradeSettings.getPassBoundary());
        System.out.println("Pass Message: " + gradeSettings.getPassMessage());
        System.out.println("Fail Message: " + gradeSettings.getFailMessage() + "\n");
        
        System.out.println("Page Count: " + pages.size() + "\n");
        
        for(int i = 0; i < pages.size(); i++) {
            pages.get(i).debugPrint();
        }
    }
}
