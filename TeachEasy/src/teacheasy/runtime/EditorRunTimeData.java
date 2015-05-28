/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.runtime;

import java.io.File;
import java.util.ArrayList;

import javafx.geometry.Bounds;
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
    /* Class level references */
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
    
    /* Properties Pane Controller */
    private PropertiesPane propertiesPane;
    
    /* Mouse controller */
    private MouseController mouseController;
    
    /* Clip board */
    private Page clipboard;
    
    /* Reference to UI */
    private TeachEasyClient parent;

    /** Constructor method */
    public EditorRunTimeData(Group nGroup, Rectangle2D nBounds, VBox propPaneBox, TeachEasyClient nParent) {
        /* Set the class level variables */
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
        
        mouseController = new MouseController();
        
        clipboard = new Page(0, "#00000000");
        
        /* Draw the page */
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
    
    /** Move to the previous page */
    public void prevPage() {        
        if(currentPage > 0) {
            currentPage--;
            
            propertiesPane.update(lesson.pages.get(currentPage), null);
            propertiesPane.lateUpdate();
            
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
        
        /* */
        propertiesPane.update(lesson.pages.get(currentPage), null);
        
        /* */
        redraw(group, bounds);
        
        new LessonInfoWindow(lesson, parent);
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
        
        propertiesPane.update(lesson.pages.get(currentPage), null);
        
        redraw(group, bounds);
        
        return true;
    }
    
    /** Save a lesson file */
    public void saveLesson() {
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
        
        if(file == null) {
            return;
        }
        
        String filePath = file.getAbsolutePath();
        
        if(!filePath.endsWith(".xml")) {
            filePath = new String(filePath + ".xml");
        }
        
        /* Set the recent read Path */
        xmlHandler.setRecentWritePath(file.getAbsolutePath());
        
        /* Write the file */
        xmlHandler.writeXML(lesson, filePath);
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
    
    /** Add a new page to the current lesson */
    public void newPage() {
        lesson.pages.add(new Page(lesson.pages.size(), "#ffffffff"));
        
        setPageCount(lesson.pages.size());
        setCurrentPage(lesson.pages.size() - 1);
    }
    
    /** Remove the current page */
    public void removePage() {
        if(!isLessonOpen()) {
            return;
        }
        
        if(lesson.pages.size() <= 1) {
            lesson.pages.get(currentPage).pageObjects.clear();
            return;
        }
        
        lesson.pages.remove(currentPage);
        
        setPageCount(lesson.pages.size());
        setCurrentPage(0);
    }
    
    /** Add a new object to the current page */
    public void newObject(PageObjectType type) {
        if(!isLessonOpen()) {
            return;
        }
        
        NewObjectController.addObject(lesson.pages.get(currentPage), type);
        
        if(lesson.pages.get(currentPage).pageObjects.size() == 0) {
            return;
        }
        
        propertiesPane.update(lesson.pages.get(currentPage),
                              lesson.pages.get(currentPage).pageObjects.get(lesson.pages.get(currentPage).pageObjects.size() - 1));
        
        redraw();
    }
    
    /** Remove the current object from the page */
    public void removeObject() {
        if(!isLessonOpen() || propertiesPane.getSelectedObject() == null) {
            return;
        }
        
        int index = lesson.pages.get(currentPage).pageObjects.indexOf(propertiesPane.getSelectedObject());
        
        if(index == -1 || index == lesson.pages.get(currentPage).pageObjects.size()) {
            return;
        }
        
        lesson.pages.get(currentPage).pageObjects.remove(index);
        
        propertiesPane.update(lesson.pages.get(currentPage), null);
        
        redraw();
    }
    
    /** Move selection to the next object */
    public void nextObject() {
        int index;
        
        if(lesson.pages.get(currentPage).pageObjects.size() == 0) {
            return;
        }
        
        if(propertiesPane.getSelectedObject() == null) {
            index = -1;
        } else {
            index = lesson.pages.get(currentPage).pageObjects.indexOf(propertiesPane.getSelectedObject());  
        }

        index++;
        
        if(index == lesson.pages.get(currentPage).pageObjects.size()) {
            index = 0;
        }
        
        propertiesPane.update(lesson.pages.get(currentPage), lesson.pages.get(currentPage).pageObjects.get(index));
    }
    
    /** Copy an object to the clipboard */
    public void copyObject() {
        if(propertiesPane.getSelectedObject() == null) {
            System.out.println("No Copy");
            return;
        }
        
        System.out.println("Copy");
        
        clipboard.pageObjects.clear();
        
        NewObjectController.copyObject(clipboard, propertiesPane.getSelectedObject());
        
        System.out.println(clipboard.pageObjects.size());
    }
    
    /** Paste an object from the clipboard */
    public void pasteObject() {
        if(clipboard.pageObjects.size() < 1) {
            System.out.println("No paste");
            return;
        }
        
        System.out.println("Paste");
        
        NewObjectController.copyObject(lesson.pages.get(currentPage), clipboard.pageObjects.get(0));
        
        int index = lesson.pages.get(currentPage).pageObjects.size() - 1;
        propertiesPane.update(lesson.pages.get(currentPage), lesson.pages.get(currentPage).pageObjects.get(index));
        
        redraw();
    }
    
    /** Mouse Pressed in the page area */
    public void mousePressed(float relX, float relY, boolean onGroup) {
        if(!isLessonOpen()) {
            return;
        }
        
        mouseController.mousePressed(lesson.pages.get(currentPage), propertiesPane, relX, relY, onGroup);
        
        /* Render the selection box */
        renderer.renderSelection(propertiesPane.getSelectedObject());
    }
    
    /** Mouse released in the page area */
    public void mouseReleased(float relX, float relY, boolean onGroup) {
        if(!isLessonOpen()) {
            return;
        }
        
        if(mouseController.mouseReleased(lesson.pages.get(currentPage), propertiesPane, relX, relY, onGroup)) {
            propertiesPane.update(lesson.pages.get(currentPage), propertiesPane.getSelectedObject());
            redraw();
        } else if(onGroup) {
            propertiesPane.lateUpdate();
        }
        
        /* Render the selection box */
        renderer.renderSelection(propertiesPane.getSelectedObject());
    }
    
    /** Redraw the content */
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
            renderer.renderSelection(propertiesPane.getSelectedObject());
        } else {
            /* Render the no lesson loaded screen */
            renderer.renderUnLoaded();
        }
    }
}
