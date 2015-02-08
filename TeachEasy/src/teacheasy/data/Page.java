/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.data;

import java.util.ArrayList;
import java.util.List;

/**
 * This class encapsulates a single page
 * within a Lesson.
 * 
 * @version     1.1 08 Feb 2015
 * @author      Alistair Jewers
 */
public class Page { 
    
    private String backgroundColour; // 8 digit hex code, RGBA
    
    /** Container for the objects on this page */
    public List<PageObject> pageObjects;
    
    /** Constructor Method */
    public Page(String nBackgroundColour) {
        /* Instantiate class level variables */
        this.backgroundColour = nBackgroundColour;
        
        /* Instantiate the page object container */
        pageObjects = new ArrayList<PageObject>();
    }
	
    /** Add a new object to the page */
    public void addObject(PageObject pageObject) {
        pageObjects.add(pageObject);
    }
    
    /** Remove an object from the page */
    public void removeObject(int index) {
        pageObjects.remove(index);
        //TODO Index error handling?
    }
	
    /** Get an object on the page */
    public PageObject getObject(int index) {
        return pageObjects.get(index);
        //TODO Index error handling?    
    }
    
    /** Move an object to another layer */
    public void moveObject(int oldPosition, int newPosition) {
        /* Create temporary copy of the object */
        PageObject temp = pageObjects.get(oldPosition);
        
        /* Remove old object, Add the temporary copy at the new location */
        pageObjects.remove(oldPosition);
        pageObjects.add(newPosition, temp);
        
        //TODO Index error handling?
    }
    
    /** Get page object count */
    public int getObjectCount() {
        return pageObjects.size();
    }
    
    /** Get the page background colour */
    public String getPageColour() {
        return backgroundColour;
    }
    
    /** Set the page background colour */
    public void setPageColour(String nBackgroundColour) {
        this.backgroundColour = nBackgroundColour;
    }
}
