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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

import teacheasy.data.*;
import teacheasy.render.Renderer;
import teacheasy.xml.*;
import teacheasy.xml.util.XMLNotification;
import teacheasy.xml.util.XMLNotification.Level;

/**
 * This class encapsulates the current state of the
 * editor application and all its data. The methods in 
 * this class are called when events occur in the top 
 * level class which incorporates the GUI.
 * 
 * @author amj523
 * @version 1.0 19 Apr 2015
 */
public class EditorRunTimeData {
    /* */
    private Group group;
    private Rectangle2D bounds;
    
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
    public EditorRunTimeData(Group nGroup, Rectangle2D nBounds) {
        /* Set the class level variables */
        this.group = nGroup;
        this.bounds = nBounds;
        
        /* Instantiate class level variables */
        this.pageCount = 0;
        this.currentPage = 0;
        this.lessonOpen = false;
        
        /* Instantiate an empty lesson */
        this.lesson = new Lesson();
        
        /* Instantiate the xml handler */
        this.xmlHandler = new XMLHandler();
        
        /* Instantiate the renderer */
        renderer = new Renderer(group, bounds);
        
        redraw(group, bounds);
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
            redraw(group, bounds);
        }
    }
    
    /** Move to the previous page */
    public void prevPage() {        
        if(currentPage > 0) {
            currentPage--;
            redraw(group, bounds);
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
    
    public void newLesson() {
        /* Clear the page */
        renderer.clearPage();
        
        /* Set the lesson open flag to true */
        setLessonOpen(true);
        
        /* Set the lesson to an empty lesson */
        lesson = new Lesson();
        
        lesson.pages.add(new Page(0, "#ffffffff"));
        
        /* Set the page count */
        setPageCount(lesson.pages.size());
        
        /* Set the current page back to zero */
        setCurrentPage(0);
        
        redraw(group, bounds);
    }
    
    /** Open a lesson file */
    public boolean openLesson() {
        /* Create a file chooser */
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new ExtensionFilter("XML Files", "*.xml"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        
        /* Set the initial directory to the recent read path */
        if(xmlHandler.getRecentReadPath() != null) {
            fileChooser.setInitialDirectory(new File(new File(xmlHandler.getRecentReadPath()).getParent()));
        }
        
        /* Get the file to open */
        File file = fileChooser.showOpenDialog(new Stage());
        
        /* Check that the file is not null */
        if(file == null) {
            return false;
        }
        
        /* Set the recent read Path */
        xmlHandler.setRecentReadPath(file.getAbsolutePath());
        
        /* Parse the file */
        ArrayList<XMLNotification> errorList = xmlHandler.parseXML2(file.getAbsolutePath());
        
        /* If any errors were found during parsing, do not load the lesson */
        if(errorList.size() > 0) {   
            for(int i = 0; i < errorList.size(); i++) {
                System.out.println(errorList.get(i));
            }
        }
        
        if(XMLNotification.countLevel(errorList, Level.ERROR) > 0) {   
            return false;
        }
        
        /* Get the lesson data */
        lesson = xmlHandler.getLesson();
        lesson.debugPrint();
        
        /* Open the lesson */
        setPageCount(lesson.pages.size());
        setCurrentPage(0);
        setLessonOpen(true);
        redraw(group, bounds);
        
        return true;
    }
    
    /** Close the current lesson */
    public void closeLesson() {
        /* Clear the page */
        renderer.clearPage();
        
        /* Set the lesson open flag to false */
        setLessonOpen(false);
        
        /* Set the lesson to an empty lesson */
        lesson = new Lesson();
        
        /* Set the page count back to zero */
        setPageCount(0);
        
        /* Set the current page back to zero */
        setCurrentPage(0);
        
        redraw(group, bounds);
    }
    
    /** Redraw the content */
    public void redraw(Group group, Rectangle2D bounds) {        
        if(isLessonOpen()) {
            /* Render the current page */
            renderer.renderPage(lesson.pages.get(currentPage));
        } else {
            /* Render the no lesson loaded screen */
            renderer.renderUnLoaded();
        }
    }
}
