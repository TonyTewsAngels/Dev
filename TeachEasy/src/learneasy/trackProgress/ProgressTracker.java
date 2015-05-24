/*
 * Daniel Berhe
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */

package learneasy.trackProgress;

/**
 * This class keeps track of visited pages and marks earned throughout the
 * lesson
 * 
 * @author Daniel Berhe
 * @version 1.0 22/05/15
 *
 */
public class ProgressTracker {

    private int marks = 0;
    private boolean visitedPages[];
    private int individuaPageMarks[];
    private int totalNumberOfPages;

    public ProgressTracker(int nTotalNumberOfPages) {
        this.totalNumberOfPages = nTotalNumberOfPages;
        visitedPages = new boolean[totalNumberOfPages];
        individuaPageMarks = new int[totalNumberOfPages];
    }

    /** A method that keeps track of all the marks in a lesson */
    private void collateMarks(int pageNumber) {
        marks += individuaPageMarks[pageNumber];
        System.out.println(marks + " marks so far");
    }

    /** Method to keep track of all the visited pages */
    public void setVisitedPages(int pageNumber) {
        visitedPages[pageNumber] = true;
    }

    /** A method to keep track of inidividula page marks */
    public void inidividualPageMarks(int pageMarks, int pageNumber) {
        System.out.println("The page number is: " + pageNumber);
        individuaPageMarks[pageNumber] = pageMarks;
        collateMarks(pageNumber);

    }

    /** A method that specifies whether a page has been visited */
    public boolean pageStatus(int pageNumber) {
        if (visitedPages[pageNumber] == true) {
            return true;
        } else {
            return false;
        }
    }
}
