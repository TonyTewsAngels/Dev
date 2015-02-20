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

    /** Constructor method */
    public RunTimeData(int nPageCount) {
        /* Instantiate class level variables */
        this.pageCount = nPageCount;
        this.page = 0;
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
}
