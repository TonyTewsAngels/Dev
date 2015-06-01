/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.data;

import java.util.ArrayList;
import java.util.List;

import teacheasy.data.PageObject.PageObjectType;
import teacheasy.data.lessondata.*;

/**
 * Encapsulates the data that describes a lesson
 * as defined in the TeachEasy digital lesson XML format.
 * 
 * @author  Alistair Jewers
 * @version 1.1 08 Feb 2015
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

    /**
     * Constructor to create the data object with the data parsed
     * from XML.
     * 
     * @param nLessonInfo The lesson information object.
     * @param nDFSettings The default lesson settings object.
     * @param nGradeSettings The grade settings object.
     */
    public Lesson(LessonInfo nLessonInfo, 
                  LessonDefaultSettings nDFSettings,
                  LessonGradeSettings nGradeSettings) {
		
        /* Instantiate the pages container */
        pages = new ArrayList<Page>();
    		
        /* Set the lesson information object reference */
        this.lessonInfo = nLessonInfo;

        /* Set the default settings object reference */
        this.defaultSettings = nDFSettings;
		
        /* Set the grade settings object reference */
        this.gradeSettings = nGradeSettings;
    }
	
    /** 
     * Blank constructor 
     */
    public Lesson() {
        /* Instantiate the pages container */
        pages = new ArrayList<Page>();
        
        /* Instantiate the metadata object */
        lessonInfo = new LessonInfo("New Lesson", "Author", "1.0",
                                    "Comment", "01/01/2001", 0);
        
        /* Instantiate the default settings */
        defaultSettings = new LessonDefaultSettings(20, "Arial",
                                    "#ff000000", "#ff000000",
                                    "#ff000000", "#ff000000");
        
        /* Instantiate the grade settings */
        gradeSettings = new LessonGradeSettings(0, "pass", "fail");
    }
    
    /**
     * Counts the total marks in a lesson.
     * 
     * @return The total number of marks available in this lesson.
     */
    public int getTotalMarks() {
        /* Running total variable */
        int total = 0;
        
        /* Loop through every page */
        for(Page page : pages) {            
            /* Loop through every page object */
            for(PageObject object : page.pageObjects) {                
                /* Check for the two question object types */
                if(object.getType() == PageObjectType.MULTIPLE_CHOICE) {
                    /* Type cast to access variables */
                    MultipleChoiceObject mchoice = (MultipleChoiceObject)object;
                    
                    /* Add to the running total */
                    total += mchoice.getMarks();
                } else if(object.getType() == PageObjectType.ANSWER_BOX) {
                    /* Type cast to access variables */
                    AnswerBoxObject answerbox = (AnswerBoxObject)object;
                    
                    /* Add to the running total */
                    total += answerbox.getMarks();
                }
            }
        }
        
        /* Return the total */
        return total;
    }
    
    /** Prints the data in the lesson object to the console */
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
        
        /* Print the data of each page */
        for(Page page : pages) {
            page.debugPrint();
        }
    }
}
