/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 */
package teacheasy.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates the data that describes a Page 
 * as defined in the TeachEasy digital lesson XML format.
 * 
 * @author  Alistair Jewers
 * @version 1.1 08 Feb 2015
 */
public class Page { 
    
    /** The number of the page */
    private int number;
    
    /** The background colour of the page in #AARRGGBB string format */
    private String backgroundColour;
    
    /** List of the objects on this page */
    public List<PageObject> pageObjects;
    
    /**
     * Constructor to create the data object with the data parsed
     * from XML.
     *  
     * @param nNumber Page number.
     * @param nBackgroundColour Background colour in #AARRGGBB string format.
     */
    public Page(int nNumber, String nBackgroundColour) {
        /* Instantiate class level data variables */
        this.number = nNumber;
        this.backgroundColour = nBackgroundColour;
        
        /* Instantiate the page object container */
        pageObjects = new ArrayList<PageObject>();
    }
	
    /** Adds a new object to the page */
    public void addObject(PageObject pageObject) {
        pageObjects.add(pageObject);
    }
    
    /** Removes an object from the page */
    public void removeObject(int index) {
        if(index >= 0 && index < pageObjects.size()) {
            pageObjects.remove(index);
        }
    }
	
    /** Gets an object on the page */
    public PageObject getObject(int index) {
        return pageObjects.get(index);
    }
    
    /** Moves an object to another layer */
    public void moveObject(int oldPosition, int newPosition) {
        /* Create temporary copy of the object */
        PageObject temp = pageObjects.get(oldPosition);
        
        /* Remove old object, Add the temporary copy at the new location */
        pageObjects.remove(oldPosition);
        pageObjects.add(newPosition, temp);
    }
    
    /** Gets page object count */
    public int getObjectCount() {
        return pageObjects.size();
    }
    
    /** Gets the page background colour in #AARRGGBB string format */
    public String getPageColour() {
        return backgroundColour;
    }
    
    /** Gets the page number */
    public int getNumber() {
        return number;
    }
    
    /** Sets the page background colour */
    public void setPageColour(String nBackgroundColour) {
        this.backgroundColour = nBackgroundColour;
    }
    
    /** Prints the Page object data to console */
    public void debugPrint() {
        System.out.println("Page " + number + ": \tBG Color = " + getPageColour());
        System.out.println("\t\t" + pageObjects.size() + " object(s).");
        
        for(int i = 0; i < pageObjects.size(); i++) {
            pageObjects.get(i).debugPrint();
        }
    }
}
