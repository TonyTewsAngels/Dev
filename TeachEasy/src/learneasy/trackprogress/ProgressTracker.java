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

    /* Class level variables */
    private int marks = 0;
    private boolean visitedPages[];
    private int individualPageMarks[];
    private int totalNumberOfPages;

    /**
     * Constructor for creating and initialising arrays that hold individual
     * page marks and visited pages.
     * 
     * @param nTotalNumberOfPages Total number of pages in an opened lesson
     */
    public ProgressTracker(int nTotalNumberOfPages) {
        this.totalNumberOfPages = nTotalNumberOfPages;

        /* Instantiate arrays */
        individualPageMarks = new int[totalNumberOfPages];
        visitedPages = new boolean[totalNumberOfPages];

        /* Initialise arrays */
        initialiseArray();
    }

    /**
     * A method that keeps track of all the marks in a lesson
     * 
     * @param pageNumber The number of the page in which the marks came from
     */
    private void collateMarks(int pageNumber) {
        marks += individualPageMarks[pageNumber];
    }

    /**
     * Method to keep track of all the visited pages
     * 
     * @param pageNumber The page number of the visited page
     */
    public void setVisitedPages(int pageNumber) {
        visitedPages[pageNumber] = true;
    }

    /**
     * A method to keep track of individual page marks
     * 
     * @param pageMarks Total marks of a single page
     * @param pageNumber The number of the page in which the
     *                   marks came from
     */
    public void inidividualPageMarks(int pageMarks, int pageNumber) {
        individualPageMarks[pageNumber] = pageMarks;
        collateMarks(pageNumber);
    }

    /**
     * A method that specifies whether a page has been visited
     * 
     * @param pageNumber The page number of the page to be checked
     * @return <code>true</code> if the page has been visited;
     *         <code>false</code> otherwise.
     */
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

    /**
     * Gets total collated marks so far
     * 
     * @return <code>int</code> value of the total collated marks
     */
    public int getTotalMarks() {
        return marks;
    }
}
