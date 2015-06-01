/*
 * Daniel Berhe
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */

package learneasy.trackprogress;

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
    private int individualPageMarks[];
    private int totalNumberOfPages;

    public ProgressTracker(int nTotalNumberOfPages) {
        this.totalNumberOfPages = nTotalNumberOfPages;

        individualPageMarks = new int[totalNumberOfPages];
        visitedPages = new boolean[totalNumberOfPages];

        /* Initialise arrays */
        initialiseArray();
    }

    /** A method that keeps track of all the marks in a lesson */
    private void collateMarks(int pageNumber) {
        marks += individualPageMarks[pageNumber];
        System.out.println(marks + " marks so far");
    }

    /** Method to keep track of all the visited pages */
    public void setVisitedPages(int pageNumber) {
        visitedPages[pageNumber] = true;
    }

    /** A method to keep track of individual page marks */
    public void inidividualPageMarks(int pageMarks, int pageNumber) {
        System.out.println("The page number is: " + pageNumber);
        individualPageMarks[pageNumber] = pageMarks;
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

    /**
     * A method to initialise the individualPageMarks array to 0 and
     * visitedPages array to false.
     * */
    private void initialiseArray() {
        for (int i = 0; i < visitedPages.length; i++) {
            individualPageMarks[i] = 0;
            visitedPages[i] = false;
        }
    }
    
    public int getTotalMarks() {
        return marks;
    }
}
