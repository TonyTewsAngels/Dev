/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.runtime;

import java.io.File;
import java.util.ArrayList;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import teacheasy.data.*;
import teacheasy.render.Renderer;
import teacheasy.xml.*;

/**
 * This class encapsulates the current state of the
 * application and all its data. The methods in this
 * class are called when events occur in the top level
 * class which incorporates the GUI.
 * 
 * @author amj523
 * @version 1.0 20 Feb 2015
 */
public class RunTimeData {
    /* Class level variables */
    private int pageCount;
    private int currentPage;
    private boolean lessonOpen;
    
    /* Current Lesson */
    private Lesson lesson;
    
    /* XML Handler */
    private XMLHandler xmlHandler;
    
    /* Renderer */
    private Renderer renderer;

    /** Constructor method */
    public RunTimeData(Group nGroup, Rectangle2D bounds) {
        /* Instantiate class level variables */
        this.pageCount = 0;
        this.currentPage = 0;
        this.lessonOpen = false;
        
        /* Instantiate an empty lesson */
        this.lesson = new Lesson();
        
        /* Instantiate the xml handler */
        this.xmlHandler = new XMLHandler();
    }
    
    /** Get the current page */
    public Page getCurrentPage() {
        return lesson.pages.get(currentPage);
    }
    
    /** Get the number of current page */
    public int getCurrentPageNumber() {
        return currentPage;
    }
    
    /** Set the current page number */
    public void setCurrentPage(int nPage) {
        if(nPage >= pageCount) {
            this.currentPage = pageCount - 1;
        } else if(nPage < 0) {
            this.currentPage = 0;
        } else {
            this.currentPage = nPage;
        }
    }
    
    /** Move to the next page */
    public void nextPage() {        
        if(currentPage < pageCount - 1) {
            currentPage++;
        }
    }
    
    /** Move to the previous page */
    public void prevPage() {        
        if(currentPage > 0) {
            currentPage--;
        }
    }
    
    /** Check if there is a next page */
    public boolean isNextPage() {
        if(currentPage < pageCount - 1) {
            return true;
        } else {
            return false;
        }
    }
    
    /** Check if there is a previous page */
    public boolean isPrevPage() {
        if(currentPage > 0) {
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
    
    /** Get the current lesson object */
    public Lesson getLesson() {
        return lesson;
    }
    
    /** Open a lesson file */
    public void openLesson(File file) {
        /* Check that the file is not null */
        if(file == null) {
            return;
        }
        
        /* Parse the file */
        ArrayList<String> errorList = xmlHandler.parseXML(file.getAbsolutePath());
        
        /* If any errors were found during parsing, do not load the lesson */
        if(errorList.size() > 0) {            
            return;
        }
        
        /* Get the lesson data */
        lesson = xmlHandler.getLesson();
        
        /* Open the lesson */
        setPageCount(lesson.pages.size());
        setLessonOpen(true);
    }
    
    /** Close the current lesson */
    public void closeLesson() {
        /* Set the lesson open flag to false */
        setLessonOpen(false);
        
        /* Set the lesson to an empty lesson */
        lesson = new Lesson();
    }
    
    /** Redraw the content */
    public void redraw(Group group, Rectangle2D bounds) {
        /* Clear the previous content */
        group.getChildren().removeAll(group.getChildren());
        
        /* Draw background */
        group.getChildren().add(new Rectangle(bounds.getMaxX(), bounds.getMaxY()- 100, Color.WHITE));
        
        /* If a lesson is open, render the current page. */
        if(isLessonOpen()) {
            /* Create title */
            Text title = new Text(lesson.lessonInfo.getLessonName());
            title.relocate(5, 0);
            
            /* Create Page Number */
            Text pageNumber = new Text("Page: " + (currentPage + 1));
            pageNumber.relocate(5, 20);
            
            /* Draw the objects on the page*/
            for(int i = 0; i < getCurrentPage().pageObjects.size(); i++) {
                Text objectName = new Text(getCurrentPage().pageObjects.get(i).getType().toString());
                objectName.relocate(5, 100 + i * 20);
                group.getChildren().add(objectName);
            }
            
            /* Add the elements to the content pane */
            group.getChildren().addAll(title, pageNumber);
            
        } else {
            /* Draw welcome message */
            group.getChildren().add(new Text("Begin by opening a lesson!"));
        }
    }
}
