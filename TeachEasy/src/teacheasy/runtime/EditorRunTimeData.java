/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved. 
 */
package teacheasy.runtime;

import java.io.File;
import java.util.ArrayList;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

import teacheasy.data.*;
import teacheasy.data.PageObject.PageObjectType;
import teacheasy.main.TeachEasyClient;
import teacheasy.render.Renderer;
import teacheasy.runtime.editor.LessonInfoWindow;
import teacheasy.runtime.editor.MouseController;
import teacheasy.runtime.editor.NewObjectController;
import teacheasy.runtime.editor.PropertiesPane;
import teacheasy.runtime.editor.TemplateController;
import teacheasy.runtime.editor.TemplateController.TemplateType;
import teacheasy.xml.*;
import teacheasy.xml.util.XMLNotification;
import teacheasy.xml.util.XMLNotification.Level;

/**
 * Encapsulates the current state of the editor 
 * application and all its data. The methods in 
 * this class are called when events occur in the top 
 * level class which incorporates the GUI.
 * 
 * @author  Alistair Jewers
 * @version 1.0 19 Apr 2015
 */
public class EditorRunTimeData {
    
    /* JavaFX references */
    private Group group;
    private Rectangle2D bounds;
    
    /* Lesson control variables */
    private int pageCount;
    private int currentPage;
    private boolean lessonOpen;
    
    /* Current Lesson */
    private Lesson lesson;
    
    /* XML Handler */
    private XMLHandler xmlHandler;
    
    /* Renderer */
    private Renderer renderer;
    
    /* Properties Pane Controller */
    private PropertiesPane propertiesPane;
    
    /* Mouse controller */
    private MouseController mouseController;
    
    /* Template Controller */
    private TemplateController templateController;
    
    /* Clip board */
    private Page clipboard;
    
    /* Reference to UI */
    private TeachEasyClient parent;

    /**
     * Constructor method to create the editor run time data.
     * 
     * @param nGroup The group the lessons are drawn on.
     * @param nBounds A rectangle that describes the size of the page area.
     * @param propPaneBox The container for the properties pane.
     * @param nParent The parent application that instantiated this instance.
     */
    public EditorRunTimeData(Group nGroup, Rectangle2D nBounds, VBox propPaneBox, TeachEasyClient nParent) {
        /* Set the class level references */
        this.group = nGroup;
        this.bounds = nBounds;
        this.parent = nParent;
        
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
        
        /* Instantiate the properties pane conroller */
        propertiesPane = new PropertiesPane(propPaneBox, this);
        
        /* Instantiate the mouse controller */
        mouseController = new MouseController(renderer);
        
        /* Instantiate the template controller */
        templateController = new TemplateController();
        
        /* Instantiate the clip board page */
        clipboard = new Page(0, "#00000000");
        
        /* Draw the page */
        redraw(group, bounds);
    }
    
    /** Gets the current page */
    public Page getCurrentPage() {
        return lesson.pages.get(currentPage);
    }
    
    /** Gets the number of current page */
    public int getCurrentPageNumber() {
        return currentPage;
    }
    
    /** Sets the current page number */
    public void setCurrentPage(int nPage) {
        if(nPage >= pageCount) {
            this.currentPage = pageCount - 1;
        } else if(nPage < 0) {
            this.currentPage = 0;
        } else {
            this.currentPage = nPage;
        }
        
        if(isLessonOpen()) {
            propertiesPane.update(lesson.pages.get(currentPage), null);
        } else {
            propertiesPane.update(null, null);
        }
        
        redraw(group, bounds);
    }
    
    /** Move to the next page */
    public void nextPage() {        
        if(currentPage < pageCount - 1) {
            currentPage++;
            
            propertiesPane.update(lesson.pages.get(currentPage), null);
            propertiesPane.lateUpdate();
            
            redraw(group, bounds);
        }
    }
    
    /** Moves to the previous page */
    public void prevPage() {        
        if(currentPage > 0) {
            currentPage--;
            
            propertiesPane.update(lesson.pages.get(currentPage), null);
            propertiesPane.lateUpdate();
            
            redraw(group, bounds);
        }
    }
    
    /** Checks if there is a next page */
    public boolean isNextPage() {
        if(currentPage < pageCount - 1) {
            return true;
        } else {
            return false;
        }
    }
    
    /** Checks if there is a previous page */
    public boolean isPrevPage() {
        if(currentPage > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    /** Gets the page count */
    public int getPageCount() {
        return pageCount;
    }
    
    /** Sets the page count */
    public void setPageCount(int nPageCount) {
        this.pageCount = nPageCount;
    }
    
    /** Checks if there is an open lesson */
    public boolean isLessonOpen() {
        return lessonOpen;
    }
    
    /** Sets the lesson open flag */
    public void setLessonOpen(boolean flag) {
        lessonOpen = flag;
    }
    
    /** Gets the current lesson object */
    public Lesson getLesson() {
        return lesson;
    }
    
    /** Creates a new empty lesson */
    public void newLesson() {
        /* Clear the page */
        renderer.clearPage();
        
        /* Set the lesson open flag to true */
        setLessonOpen(true);
        
        /* Set the lesson to an empty lesson */
        lesson = new Lesson();
        
        /* Add a blank page */
        lesson.pages.add(new Page(0, "#ffffffff"));
        
        /* Set the page count */
        setPageCount(lesson.pages.size());
        
        /* Set the current page back to zero */
        setCurrentPage(0);
        
        /* Update the properties pane */
        propertiesPane.update(lesson.pages.get(currentPage), null);
        
        /* Redraw the page */
        redraw(group, bounds);
        
        /* Launch the lesson info window */
        new LessonInfoWindow(lesson, parent);
    }
    
    /** Opens a lesson file */
    public void openLesson() {        
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
            /* Instantiate an error list */
            ArrayList<XMLNotification> errorList = new ArrayList<XMLNotification>();
            
            /* Add the file not found error */
            errorList.add(new XMLNotification(Level.ERROR, "File Not Found"));
            
            /* Launch the error window */
            new XMLErrorWindow(errorList, this, null);
            
            /* Return to cancel */
            return;
        }
        
        /* Set the recent read Path */
        xmlHandler.setRecentReadPath(file.getAbsolutePath());
        
        /* Parse the file and get the error list */
        ArrayList<XMLNotification> errorList = xmlHandler.parseXML(file.getAbsolutePath());
        
        /* If any errors were found during parsing, do not load the lesson */
        if(errorList.size() > 0) {   
            /* Launch the error window */
            new XMLErrorWindow(errorList, this, null);
            
            /* Return to cancel */
            return;
        }
        
        /* Get the lesson data */
        lesson = xmlHandler.getLesson();
        
        /* Open the lesson */
        setPageCount(lesson.pages.size());
        setCurrentPage(0);
        setLessonOpen(true);
        
        /* Update the properties pane with the new page */
        propertiesPane.update(lesson.pages.get(currentPage), null);
        
        /* Redraw the page */
        redraw(group, bounds);
    }
    
    /** Forces a lesson to open after warnings were found */
    public void forceOpenLesson() {
        /* Get the lesson data */
        lesson = xmlHandler.getLesson();
        
        /* Open the lesson */
        setPageCount(lesson.pages.size());
        setCurrentPage(0);
        setLessonOpen(true);
        
        /* Update the properties pane */
        propertiesPane.update(lesson.pages.get(currentPage), null);
        
        /* Re draw the page */
        redraw(group, bounds);
        
        /* Update the application UI */
        parent.updateUI();
    }
    
    /** Saves a lesson file */
    public void saveLesson() {
        /* Cancel if no lesson is open */
        if(!isLessonOpen()) {
            return;
        }
        
        /* Create a file chooser */
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new ExtensionFilter("XML Files", "*.xml"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        
        /* Set the initial directory to the recent read path */
        if(xmlHandler.getRecentWritePath() != null) {
            fileChooser.setInitialDirectory(new File(new File(xmlHandler.getRecentWritePath()).getParent()));
        } else if(xmlHandler.getRecentReadPath() != null) {
            fileChooser.setInitialDirectory(new File(new File(xmlHandler.getRecentReadPath()).getParent()));
        }
        
        /* Get the file to save */
        File file = fileChooser.showSaveDialog(new Stage());
        
        /* Cancel if no path is selected */
        if(file == null) {
            return;
        }
        
        /* Convert the path to a string */
        String filePath = file.getAbsolutePath();
        
        /* If the path does not end with .xml, add the filetype */
        if(!filePath.endsWith(".xml")) {
            filePath = new String(filePath + ".xml");
        }
        
        /* Set the recent read Path */
        xmlHandler.setRecentWritePath(file.getAbsolutePath());
        
        /* Write the file */
        xmlHandler.writeXML(lesson, filePath);
    }
    
    /** Closes the current lesson */
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
        
        /* Re draw the page */
        redraw(group, bounds);
    }
    
    /** Adds a new page to the current lesson */
    public void newPage(TemplateType template) {
        /* Add a */
        lesson.pages.add(currentPage + 1, new Page(lesson.pages.size(), "#ffffffff"));
        
        if(template != null) {
            templateController.ApplyTemplate(lesson.pages.get(currentPage + 1), lesson.defaultSettings, template);
        }
        
        setPageCount(lesson.pages.size());
        setCurrentPage(currentPage + 1);
    }
    
    /** Removes the current page */
    public void removePage() {
        /* If no lesson is open cancel */
        if(!isLessonOpen()) {
            return;
        }
        
        /* If there is only one page clear it */
        if(lesson.pages.size() <= 1) {
            lesson.pages.get(currentPage).pageObjects.clear();
            setCurrentPage(0);
            redraw();
            return;
        }
        
        /* Remove the page */
        lesson.pages.remove(currentPage);
        
        /* Reset page count */
        setPageCount(lesson.pages.size());
        
        /* Page to move to */
        int moveToPage = currentPage;
        
        /* Check its in range */
        if(moveToPage >= lesson.pages.size()) {
            moveToPage = lesson.pages.size() - 1;
        }
        
        /* Set the page */
        setCurrentPage(moveToPage);
        
        /* Re draw the page */
        redraw();
    }
    
    /** Add a new object to the current page */
    public void newObject(PageObjectType type) {
        /* If no lesson is open cancel */
        if(!isLessonOpen()) {
            return;
        }
        
        /* Call the add object method with the type */
        NewObjectController.addObject(lesson.pages.get(currentPage), type);
        
        /* If the page is empty cancel */
        if(lesson.pages.get(currentPage).pageObjects.size() == 0) {
            return;
        }
        
        /* Update the properties pane to select the top object */
        propertiesPane.update(lesson.pages.get(currentPage),
                              lesson.pages.get(currentPage).pageObjects.get(lesson.pages.get(currentPage).pageObjects.size() - 1));
        
        /* Redraw the page */
        redraw();
    }
    
    /** Remove the current object from the page */
    public void removeObject() {
        /* If no lesson is oped or the selected object is null, cancel */
        if(!isLessonOpen() || propertiesPane.getSelectedObject() == null) {
            return;
        }
        
        /* Get the index of the selected object */
        int index = lesson.pages.get(currentPage).pageObjects.indexOf(propertiesPane.getSelectedObject());
        
        /* If the index is invalid return */
        if(index == -1 || index == lesson.pages.get(currentPage).pageObjects.size()) {
            return;
        }
        
        /* Remove the page */
        lesson.pages.get(currentPage).pageObjects.remove(index);
        
        /* Update the properties pane */
        propertiesPane.update(lesson.pages.get(currentPage), null);
        
        /* Re draw the page */
        redraw();
    }
    
    /** Move selection to the next object */
    public void nextObject() {
        int index;
        
        /* If there are no objects, cancel */
        if(lesson.pages.get(currentPage).pageObjects.size() == 0) {
            return;
        }
        
        /* Check if no object is selected */
        if(propertiesPane.getSelectedObject() == null) {
            /* Set index to minus 1*/
            index = -1;
        } else {
            /* Set index to the index of the selected object */
            index = lesson.pages.get(currentPage).pageObjects.indexOf(propertiesPane.getSelectedObject());  
        }

        /* Step the index forward */
        index++;
        
        /* Wrap at the last object */
        if(index == lesson.pages.get(currentPage).pageObjects.size()) {
            index = 0;
        }
        
        /* Update with the new selection */
        propertiesPane.update(lesson.pages.get(currentPage), lesson.pages.get(currentPage).pageObjects.get(index));
    }
    
    /** Copy an object to the clipboard */
    public void copyObject() {
        /* If no object is selected cancel */
        if(propertiesPane.getSelectedObject() == null) {
            return;
        }
        
        /* Clear the clipboard */
        clipboard.pageObjects.clear();
        
        /* Copy the selected object to the clipboard */
        NewObjectController.copyObject(clipboard, propertiesPane.getSelectedObject());
    }
    
    /** Paste an object from the clipboard */
    public void pasteObject() {
        /* If the clip board is empty, cancel */
        if(clipboard.pageObjects.size() < 1) {
            return;
        }
        
        /* Copy the object from the clipboard to the current page */
        NewObjectController.copyObject(lesson.pages.get(currentPage), clipboard.pageObjects.get(0));
        
        /* Update selection to the latest object */
        int index = lesson.pages.get(currentPage).pageObjects.size() - 1;
        propertiesPane.update(lesson.pages.get(currentPage), lesson.pages.get(currentPage).pageObjects.get(index));
        
        /* Redraw the page */
        redraw();
    }
    
    /** Called when the mouse is pressed */
    public void mousePressed(float relX, float relY, boolean onGroup) {
        /* If no lesson is open, ignore */
        if(!isLessonOpen()) {
            return;
        }
        
        /* Call the moouse controller press method with the page, location and other parameters */
        mouseController.mousePressed(lesson.pages.get(currentPage), propertiesPane, relX, relY, onGroup);
        
        /* Render the selection box */
        renderer.renderSelection(propertiesPane.getSelectedObject(), lesson.pages.get(currentPage), false);
    }
    
    /** Called when the mouse is released */
    public void mouseReleased(float relX, float relY, boolean onGroup) {
        /* If no lesson is open, ignore */
        if(!isLessonOpen()) {
            return;
        }
        
        /* Call the mouse controller release method */
        if(mouseController.mouseReleased(lesson.pages.get(currentPage), propertiesPane, relX, relY, onGroup)) {
            /* Update the properties pane */
            propertiesPane.update(lesson.pages.get(currentPage), propertiesPane.getSelectedObject());
            
            /* Re draw the page */
            redraw();
        } else if(onGroup) {
            /* Call the late update on the properties pane */
            propertiesPane.lateUpdate();
        }
        
        /* Render the selection box */
        renderer.renderSelection(propertiesPane.getSelectedObject(), lesson.pages.get(currentPage), false);
    }
    
    /** Called when the mouse is moved */
    public void mouseMoved(float relX, float relY, boolean onGroup) {
        /* If no lesson is open or the mouse was off the page, ignore */
        if(!isLessonOpen() || !onGroup) {
            return;
        }
        
        /* Check the mouse position and render the hover indicator */
        renderer.renderSelection(mouseController.mouseMoved(lesson.pages.get(currentPage), relX, relY), lesson.pages.get(currentPage), true);
    }
    
    /** Redraw the page */
    public void redraw(Group group, Rectangle2D bounds) {        
        redraw();
    }
    
    /** Redraw the content */
    public void redraw() {        
        if(isLessonOpen()) {
            /* Update the total marks */
            lesson.lessonInfo.setTotalMarks(lesson.getTotalMarks());
            
            /* Render the current page */
            renderer.renderPage(lesson.pages.get(currentPage));
            
            /* Render the selection box */
            renderer.renderSelection(propertiesPane.getSelectedObject(), lesson.pages.get(currentPage), false);
        } else {
            /* Render the no lesson loaded screen */
            renderer.renderUnLoaded();
        }
    }
}
