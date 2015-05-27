/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.runtime;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import learneasy.homePage.HomePage;
import learneasy.trackProgress.ProgressTracker;
import teacheasy.data.*;
import teacheasy.main.LearnEasyClient;
import teacheasy.render.Renderer;
import teacheasy.xml.*;
import teacheasy.xml.util.XMLNotification;
import teacheasy.xml.util.XMLNotification.Level;

/**
 * This class encapsulates the current state of the application and all its
 * data. The methods in this class are called when events occur in the top level
 * class which incorporates the GUI.
 * 
 * @author amj523
 * @version 1.0 20 Feb 2015
 */
public class RunTimeData {
    /* Parent Reference */
    private LearnEasyClient parent;

    /* */
    private Group group;
    private Rectangle2D bounds;
    private Stage dialogStage;

    /* Class level variables */
    private int pageCount;
    private int currentPage;
    private boolean lessonOpen;
    private boolean hideDialog = false;
    private boolean pageDirection;
    /* Current Lesson */
    private Lesson lesson;

    private ProgressTracker progressTracker;

    /* XML Handler */
    private XMLHandler xmlHandler;

    /* Renderer */
    private Renderer renderer;
    
    /*Initialise HomePage class*/
    HomePage homePage;
    
   
    /** Constructor method */
    public RunTimeData(Group nGroup, Rectangle2D nBounds,
            LearnEasyClient nParent) {
        /* Set the class level variables */
        this.group = nGroup;
        this.bounds = nBounds;
        this.parent = nParent;

        /* Instantiate class level variables */
        this.pageCount = 0;
        this.currentPage = 0;
        this.lessonOpen = false;
        
        pageDirection = false;
        
      
        /* Instantiate an empty lesson */
        this.lesson = new Lesson();

        /* Instantiate the xml handler */
        this.xmlHandler = new XMLHandler();

        /* Instantiate the renderer */
        renderer = new Renderer(group, bounds);
        
        homePage = new HomePage();
        homePage.setPreference();
        
       
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
        if (nPage >= pageCount) {
            this.currentPage = pageCount - 1;
        } else if (nPage < 0) {
            this.currentPage = 0;
        } else {
            this.currentPage = nPage;
        }
    }

    /** Move to the next page */
    public void nextPage() {
        if (currentPage < pageCount - 1) {
            collatePageMarks();
            progressTracker.setVisitedPages(currentPage);
            currentPage++;
            redraw(group, bounds);
            if(progressTracker.pageStatus(currentPage)){
                /* grey out the available marks */
                renderer.answerBoxHandler.DisableAllAnswerBoxes();
                renderer.multipleChoiceHandler.DisableAllMultipleChoices();
            }
        }
    }

    public boolean checkPageCompleted() {
        /* Check to see if user has attempted all questions */
        if ((attemptedAllAvailableMarks()) || (hideDialog)) {
            return true;
        } else {

            /* Display dialog box */
            displayWarning();

            return false;
        }
    }

 

    /**
     * A method to count the marks on a page and pass them to progress tracking
     * class
     */
    private void collatePageMarks() {
        int pageMarks = 0;

        pageMarks += renderer.answerBoxHandler.currentPageMarks();
        pageMarks += renderer.multipleChoiceHandler.totalPageMarks();

       // progressTracker.collateMarks(pageMarks);
        progressTracker.inidividualPageMarks(pageMarks, currentPage);
    }

    /**
     * Informs user that not all questions have been attempted and gives them
     * the option of either continuing with the lesson or completing the
     * questions
     */
    private void displayWarning() {

        /* Create buttons */
        Button yesButton = new Button("Yes");
        yesButton.setId("yes");
        yesButton.setOnAction(new ButtonEventHandler());

        Button noButton = new Button("No");
        noButton.setId("no");
        noButton.setOnAction(new ButtonEventHandler());

        /* Check box giving user the option to never see warning again */
        final CheckBox dialogCheck = new CheckBox("Don't show me this again");
        dialogCheck.setId("dialog check box");
        dialogCheck.setIndeterminate(false);
        dialogCheck.setSelected(false);
        /* What to do if the check box is selected */
        dialogCheck.selectedProperty().addListener(
                new ChangeListener<Boolean>() {
                    public void changed(ObservableValue<? extends Boolean> ov,
                            Boolean old_val, Boolean new_val) {
                        if (dialogCheck.isSelected()) {
                            hideDialog = true;
                        }
                    }
                });

        /* Create the dialog box and draw on screen */
        dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage
                .setScene(new Scene(
                        VBoxBuilder
                                .create()
                                .children(
                                        new Text(
                                                "You haven't attempted every mark available on this page yet!"
                                                        + "\nAre you sure you want to continue to the next page without"
                                                        + " attempting them? \nYou won't be able to attempt them later."),
                                        yesButton, noButton, dialogCheck)
                                .alignment(Pos.CENTER).padding(new Insets(5))
                                .build()));
        dialogStage.show();
    }

    /**
     * Button Event Handler Class
     */
    public class ButtonEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            /* Get the button that was pressed */
            Button button = (Button) e.getSource();

            /* Get the id of the button pressed */
            String id = button.getId();

            /* Act according to id */
            /* If user wants to continue, go to next page */
            if (id.equals("yes")) {
            	System.out.println("yes");
            	
                dialogStage.close();
                
                if (isPageDirection()) {
                	nextPage();
                } else {
                	prevPage();
                }
                
                parent.updateUI();
            }
            /* If user wants to attempt questions close the dialog */
            else if (id.equals("no")) {
                dialogStage.close();
            }
        }
    }

    /** Checks if all marks available on the current page have been attempted */
    private boolean attemptedAllAvailableMarks() {

        if ((!renderer.answerBoxHandler.allBoxesDisabled())
                || (!renderer.multipleChoiceHandler
                        .allMultipleChoicesDisabled())) {
            return false;
        } else {
            return true;
        }
    }

    /** Move to the previous page */
    public void prevPage() {

        if (currentPage > 0) {
        	progressTracker.setVisitedPages(currentPage);
            currentPage--;
            redraw(group, bounds);

            if (progressTracker.pageStatus(currentPage)) {
                /* grey out the available marks */
                renderer.answerBoxHandler.DisableAllAnswerBoxes();
                renderer.multipleChoiceHandler.DisableAllMultipleChoices();
            }

        }
    }

    /** Check if there is a next page */
    public boolean isNextPage() {
        if (currentPage < pageCount - 1) {
            return true;
        } else {
            return false;
        }
    }

    /** Check if there is a previous page */
    public boolean isPrevPage() {
        if (currentPage > 0) {
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
    public boolean openLesson() {
        /* Create a file chooser */
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new ExtensionFilter("XML Files", "*.xml"));
        fileChooser.setInitialDirectory(new File(System
                .getProperty("user.home")));

        /* Set the initial directory to the recent read path */
        /*if (xmlHandler.getRecentReadPath() != null) {
            fileChooser.setInitialDirectory(new File(new File(xmlHandler
                    .getRecentReadPath()).getParent()));
        }*/
        
        
        /* Set the initial directory to the recent read path */
        fileChooser.setInitialDirectory(new File(homePage.getPreference().get("recentlyOpened", xmlHandler
                .getRecentReadPath())));
        
        /* Set the initial directory to the recent read path */
        fileChooser.setInitialDirectory(new File(homePage.getPreference().get("recentlyOpened", xmlHandler
                .getRecentReadPath())));
       

        /* Get the file to open */
        File file = fileChooser.showOpenDialog(new Stage());

        /* Check that the file is not null */
        if (file == null) {
            return false;
        }
        
        homePage.setRecentlyOpened(file.toString());

        /* Set the recent read Path */
        xmlHandler.setRecentReadPath(file.getAbsolutePath());

        /* Parse the file */
        ArrayList<XMLNotification> errorList = xmlHandler.parseXML2(file
                .getAbsolutePath());

        /* If any errors were found during parsing, do not load the lesson */
        if (errorList.size() > 0) {
            for (int i = 0; i < errorList.size(); i++) {
                System.out.println(errorList.get(i));
            }
        }

        if (XMLNotification.countLevel(errorList, Level.ERROR) > 0) {
            return false;
        }

        /* Get the lesson data */
        lesson = xmlHandler.getLesson();
        lesson.debugPrint();

        /* Open the lesson */
        setPageCount(lesson.pages.size());
        setCurrentPage(0);
        setLessonOpen(true);
        
        /*This needs moving somewhere more appropriate!*/
        progressTracker = new ProgressTracker(pageCount);
        
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
        if (isLessonOpen()) {
            /* Render the current page */
            renderer.renderPage(lesson.pages.get(currentPage));
        } else {
            /* Render the no lesson loaded screen */
            renderer.renderUnLoaded();
            displayRecentlyOpenedLessons();
            listAvailableLessons();
        }
    }

	public boolean isPageDirection() {
		return pageDirection;
	}

	public void setPageDirection(boolean pageDirection) {
		this.pageDirection = pageDirection;
	}
	
	/** Open a lesson file from hyperlinks */
    public boolean openLessonFromHyplink(String filePath) {
       
        File file = new File(filePath);
        
        homePage.setRecentlyOpened(file.toString());
        System.out.println("being called from openLessonFromHyplink");
         
        /* Parse the file */
        ArrayList<XMLNotification> errorList = xmlHandler.parseXML2(file
                .getAbsolutePath());

        /* If any errors were found during parsing, do not load the lesson */
        if (errorList.size() > 0) {
            for (int i = 0; i < errorList.size(); i++) {
                System.out.println(errorList.get(i));
            }
        }

        if (XMLNotification.countLevel(errorList, Level.ERROR) > 0) {
            return false;
        }

        /* Get the lesson data */
        lesson = xmlHandler.getLesson();
        lesson.debugPrint();

        /* Open the lesson */
        setPageCount(lesson.pages.size());
        setCurrentPage(0);
        setLessonOpen(true);
        
        /*This needs moving somewhere more appropriate!*/
        progressTracker = new ProgressTracker(pageCount);
        
        redraw(group, bounds);
        
        parent.updateUI();
        
        return true;
    }
    
    /** This method checks the storage of recently opened lessons
     * and lists them on the screen in the form of hyperlinks */
    private void displayRecentlyOpenedLessons(){
        
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        
        StringBuilder stringBuilder = new StringBuilder();
        List<Hyperlink> recentlyOpenedList = new ArrayList<Hyperlink>();
        
        Label recentLessonsLabel = new Label("Recently opened: ");
        recentLessonsLabel.setFont(new Font("Calibri", 25));
        vbox.getChildren().add(recentLessonsLabel);
        
        for (int i = 0; i < 4; i++){
            stringBuilder.append("RecentlyOpened"+ (i+1));
            if(!homePage.getPreference().get(stringBuilder.toString(), "doesn't exist!").equals("doesn't exist!")){
                recentlyOpenedList.add(new Hyperlink(homePage.getPreference().get(stringBuilder.toString(), "doesn't exist!")));
                recentlyOpenedList.get(i).setFont(new Font("Calibri", 14));
                recentlyOpenedList.get(i).setId(stringBuilder.toString());
                recentlyOpenedList.get(i).setOnAction(new HyperlinkHandler(recentlyOpenedList.get(i)));
                vbox.getChildren().add(recentlyOpenedList.get(i));
                stringBuilder.setLength(0);
            }
        }
        
        vbox.relocate(500, 0);
        group.getChildren().add(vbox);
    }
    
    /** Checks the default folder for files ending with .xml and lists them on 
     * the screen in the form of hyperlinks */
    private void listAvailableLessons(){
        File file = new File(homePage.getDefaultFolder());
        File[] listOfLessons = file.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".xml");
            }
        });
        
        /* Arraylist of hyperlinks */
        List<Hyperlink> availableLessonLinks = new ArrayList<Hyperlink>();
        
        /* A box to hold the list and the title of the list */
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        
        /* Setting up the title of the list */
        Label availableLessonsLabel = new Label("Available lessons: ");
        availableLessonsLabel.setFont(new Font("Calibri", 25));
        vbox.getChildren().add(availableLessonsLabel);
        
        /* Converts all the .xml files in default folder to hyperlinks and
         * adds them the vbox */
        for(int i = 0; i < listOfLessons.length; i++){
            availableLessonLinks.add(new Hyperlink(listOfLessons[i].getAbsolutePath()));
            availableLessonLinks.get(i).setId("Available lesson");
            availableLessonLinks.get(i).setOnAction(new HyperlinkHandler(availableLessonLinks.get(i)));
            availableLessonLinks.get(i).setFont(new Font("Calibri", 14));
            vbox.getChildren().add(availableLessonLinks.get(i));
        }
        
        /* Place the list on the top left corner of the learnEasy screen */
        vbox.relocate(0, 0);
        group.getChildren().add(vbox);
        
    }
    
    
    /** A handler for the hyperlinks */
    public class HyperlinkHandler implements EventHandler<ActionEvent> {
        private Hyperlink hl;
        
        public HyperlinkHandler(Hyperlink nHl) {
            this.hl = nHl;
        }
        
        public void setFirstHyperlink(Hyperlink nHl) {
            this.hl = nHl;
        }
        
        @Override
        public void handle(ActionEvent e) {
            /* get the ID of the hyperlink */
            String id = hl.getId();
            System.out.println("the id is: " + id);

            if(!hl.equals("doesn't exist!")){
                if(id.equals("RecentlyOpened1")){
                openLessonFromHyplink(homePage.getPreference().get("RecentlyOpened1", "doesn't exist!"));
                } else if (id.equals("RecentlyOpened2")){
                    openLessonFromHyplink(homePage.getPreference().get("RecentlyOpened2", "doesn't exist!"));
                } else if (id.equals("RecentlyOpened3")){
                    openLessonFromHyplink(homePage.getPreference().get("RecentlyOpened3", "doesn't exist!"));
                } else if (id.equals("RecentlyOpened4")){
                    openLessonFromHyplink(homePage.getPreference().get("RecentlyOpened4", "doesn't exist!"));
                } else if (id.equals("RecentlyOpened5")){
                    openLessonFromHyplink(homePage.getPreference().get("RecentlyOpened5", "doesn't exist!"));
                } 
             } if (id.equals("Available lesson")){
                 openLessonFromHyplink(hl.getText());
             }
        }
    }
}
