/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.runtime;

/**
 * This class encapsulates the current state of the
 * application data beyond the media content. This
 * data is used whilst the program is running.
 * 
 * @author amj523
 * @version 1.0 20 Feb 2015
 */
public class RunTimeData {
    /* Class level variables */
    private int pageCount;
    private int page;
    private boolean lessonOpen;

    /** Constructor method */
    public RunTimeData(int nPageCount) {
        /* Instantiate class level variables */
        this.pageCount = nPageCount;
        this.page = 0;
        this.lessonOpen = false;
    }
    
    /** Get the current page */
    public int getPage() {
        return page;
    }
    
    /** Set the current page */
    public void setPage(int nPage) {
        if(nPage >= pageCount) {
            this.page = pageCount - 1;
        } else if(nPage < 0) {
            this.page = 0;
        } else {
            this.page = nPage;
        }
    }
    
    /** Move to the next page */
    public void nextPage() {        
        if(page < pageCount - 1) {
            page++;
        }
    }
    
    /** Move to the previous page */
    public void prevPage() {        
        if(page > 0) {
            page--;
        }
    }
    
    /** Check if there is a next page */
    public boolean isNextPage() {
        if(page < pageCount - 1) {
            return true;
        } else {
            return false;
        }
    }
    
    /** Check if there is a previous page */
    public boolean isPrevPage() {
        if(page > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    /** Get the page count */
    public int getPageCount() {
        return pageCount;
    }
    
    /** Set the page count */
    public void setPageCount(int nPageCount) {
        this.pageCount = nPageCount;
    }
    
    /** Check if there is an open lesson */
    public boolean isLessonOpen() {
        return lessonOpen;
    }
    
    /** Set the lesson open flag */
    public void setLessonOpen(boolean flag) {
        lessonOpen = flag;
    }
}
