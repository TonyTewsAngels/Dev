/*
 * Daniel Berhe
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */

package learneasy.trackProgress;

/**
 * This class keeps track of visited pages and marks
 * earned throughout the lesson
 * @author Daniel Berhe
 * @version 1.0 22/05/15
 *
 */
public class ProgressTracker {

    private int marks = 0;
    private boolean pages[];
    private int totalNumberOfPages;
    
    public ProgressTracker(int nTotalNumberOfPages){
        this.totalNumberOfPages = nTotalNumberOfPages;
        pages = new boolean[totalNumberOfPages];
    }
   
    /** A method that keeps track of all the marks in a lesson*/
    public void collateMarks(int nMarks){
        marks += nMarks;
        System.out.println(marks + " marks so far");
    }
    
    /**Method to keep track of all the visited pages*/
    public void setVisitedPages(int pageNumber){
        pages[pageNumber] = true;
    }
    
    /**A method that specifies whether a page has been visited*/
    public boolean pageStatus(int pageNumber){       
        if(pages[pageNumber] == true){
            return true;
        } else {
            return false;
        }
    }
}
