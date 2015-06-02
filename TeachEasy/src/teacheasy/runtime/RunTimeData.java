/*
 * Alistair Jewers & Daniel Berhe
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 */
package teacheasy.runtime;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.effect.DropShadow;
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
import learneasy.homepage.HomePage;
import learneasy.trackprogress.ProgressTracker;
import teacheasy.data.*;
import teacheasy.main.LearnEasyClient;
import teacheasy.main.PreviewWindow;
import teacheasy.render.RenderUtil;
import teacheasy.render.Renderer;
import teacheasy.xml.*;
import teacheasy.xml.util.XMLNotification;
import teacheasy.xml.util.XMLNotification.Level;

/**
 * Encapsulates the current state of an instance of the Learn Easy 
 * application and all its data. The methods in this class are
 * called when events occur in the top level class which 
 * incorporates the GUI.
 * 
 * @author Alistair Jewers
 * @author Daniel Berhe
 * @version 2.0 28 May 2015
 */
public class RunTimeData {
    
    /* Parent References */
    private LearnEasyClient parent;
    private PreviewWindow previewParent;

    /* JavaFX references */
    private Group group;
    private Rectangle2D bounds;
    private Stage dialogStage;

    /* Lesson control variables */
    private int pageCount;
    private int currentPage;
    private boolean lessonOpen;
    
    /* Warning dialog control variables */
    private boolean hideDialog = false;
    private boolean pageDirection;
    
    /* Current Lesson */
    private Lesson lesson;

    /* Progress tracking object */
    private ProgressTracker progressTracker;

    /* XML Handler */
    private XMLHandler xmlHandler;

    /* Renderer */
    private Renderer renderer;
    
    /*Initialise HomePage class*/
    HomePage homePage;
    
    /* Indicates whether this is running in preview mode*/
    private boolean previewMode;
   
    /**
     * Constructor method for the run time data in standard mode.
     * 
     * @param nGroup The group that lesson content is drawn on.
     * @param nBounds A rectangle describing the groups size.
     * @param nParent The parent client.
     */
    public RunTimeData(Group nGroup, Rectangle2D nBounds, LearnEasyClient nParent) {
        /* Nullify preview parent reference */
        this.previewParent = null;
        
        /* Set up the run time data in normal mode */
        setup(nGroup, nBounds, nParent, false);
    }
    
    /**
     * Alternative constructor for the run time data in preview
     * mode from within the Teach Easy application.
     * 
     * @param nGroup The group that lesson content is drawn on.
     * @param nBounds A rectangle describing the groups size.
     * @param nPreviewParent The preview window that instantiates this
     *                       instance of the run time data.
     * @param nLesson The lesson object to load in preview mode.
     */
    public RunTimeData(Group nGroup, Rectangle2D nBounds, PreviewWindow nPreviewParent, Lesson nLesson) {
        /* Set the preview window parent reference */
        this.previewParent = nPreviewParent;
        
        /* Set up the run time data in preview mode */
        setup(nGroup, nBounds, null, true);
        
        /* Open the lesson */
        lesson = nLesson;
        setPageCount(lesson.pages.size());
        setCurrentPage(0);
        setLessonOpen(true);
        
        /* Initialise the progress tracker */
        progressTracker = new ProgressTracker(pageCount);
        
        /* Re draw the page */
        redraw(group, bounds);
    }
    
    /**
     * Sets up the run time data when it is first instantiated. Operation varies based
     * on mode.
     * 
     * @param nGroup The group on which to draw pages.
     * @param nBounds A rectangle describing the size of the drawing area.
     * @param nParent The learn easy client that instantiates this run time data.
     * @param nPreviewMode Indicates whether this is a preview mode run.
     */
    public void setup(Group nGroup, Rectangle2D nBounds, LearnEasyClient nParent, boolean nPreviewMode) {
        /* Set the class level variables */
        this.group = nGroup;
        this.bounds = nBounds;
        this.parent = nParent;
        
        /* Preview mode */
        this.previewMode = nPreviewMode;

        /* Instantiate class level variables */
        this.pageCount = 0;
        this.currentPage = 0;
        this.lessonOpen = false;
        
        /* Initialise the page direction setting */
        pageDirection = false;

        /* Instantiate an empty lesson */
        this.lesson = new Lesson();

        /* Instantiate the xml handler */
        this.xmlHandler = new XMLHandler();

        /* Instantiate the renderer */
        renderer = new Renderer(group, bounds);
        
        /* Create the home page object */
        homePage = new HomePage();
        
        /* If this is preview mode don't call the preferences code */
        if(!previewMode) {
            homePage.setPreference();
        }
        
        /* Re-draw the page */        
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
        if (nPage >= pageCount) {
            this.currentPage = pageCount - 1;
        } else if (nPage < 0) {
            this.currentPage = 0;
        } else {
            this.currentPage = nPage;
        }
    }

    /** Moves to the next page */
    public void nextPage() {
        if (currentPage < pageCount - 1) {
            /* Tally up the marks */
            collatePageMarks();
            
            /* Track progress */
            progressTracker.setVisitedPages(currentPage);
            
            /* Move forward a page */
            currentPage++;
            
            /* Redraw the new page */
            redraw(group, bounds);
            
            /* Check if this page has been visited before */
            if(progressTracker.pageStatus(currentPage)){
                /* Disable the questions */
                renderer.answerBoxHandler.DisableAllAnswerBoxes();
                renderer.multipleChoiceHandler.DisableAllMultipleChoices();
            }
        }
    }

    /**
     * Checks if a page has been completed fully.
     */
    public boolean checkPageCompleted() {
        /* Check to see if user has attempted all questions */
        if (attemptedAllAvailableMarks() || hideDialog) {
            /* All questions attempted, return true */
            return true;
        } else {
            /* Display dialog box */
            displayWarning();

            /* Page not complete, return false */
            return false;
        }
    }

    /**
     * A method to count the marks on a page and pass them to progress tracking
     * class.
     */
    public void collatePageMarks() {
        /* Running total */
        int pageMarks = 0;

        /* Add marks from answer boxes and multiple choice */
        pageMarks += renderer.answerBoxHandler.currentPageMarks();
        pageMarks += renderer.multipleChoiceHandler.totalPageMarks();

        /* Save the marks obtained on this page */
        progressTracker.inidividualPageMarks(pageMarks, currentPage);
    }

    /**
     * Informs user that not all questions have been attempted and gives them
     * the option of either continuing with the lesson or completing the
     * questions.
     */
    private void displayWarning() {

        /* Create buttons */
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");
        
        /* Set button IDs */
        yesButton.setId("yes");
        noButton.setId("no");
        
        /* Set button action handlers */
        yesButton.setOnAction(new ButtonEventHandler());
        noButton.setOnAction(new ButtonEventHandler());

        /* Check box giving user the option to never see warning again */
        final CheckBox dialogCheck = new CheckBox("Don't show me this again");
        dialogCheck.setId("dialog check box");
        dialogCheck.setIndeterminate(false);
        dialogCheck.setSelected(false);
        
        /* If checkbox is selected, disable the warning dialog */
        dialogCheck.selectedProperty().addListener(
                new ChangeListener<Boolean>() {
                    public void changed(ObservableValue<? extends Boolean> ov,
                            Boolean old_val, Boolean new_val) {
                        hideDialog = dialogCheck.isSelected();
                    }
                });

        /* Create the dialog box */
        dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setScene(new Scene(VBoxBuilder.create().children(
                new Text("You haven't attempted every mark available on this page yet!" +
                         "\nAre you sure you want to continue to the next page without" +
                         " attempting them? \nYou won't be able to attempt them later."),
                yesButton, noButton, dialogCheck)                         
                .alignment(Pos.CENTER).padding(new Insets(5))
                .build()));
        
        /* Show the warning dialog box */
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
            if (id.equals("yes")) {
            	/* Yes pressed, close dialog */
                dialogStage.close();
                
                /* Go to next page */
                if (isPageDirection()) {
                	nextPage();
                } else {
                	prevPage();
                }
                
                /* Update the UI of the application */
                updateParentUI();
            } else if (id.equals("no")) {
                /* No pressed, close dialog */
                dialogStage.close();
            }
        }
    }

    /** Checks if all marks available on the current page have been attempted */
    private boolean attemptedAllAvailableMarks() {
        if ((!renderer.answerBoxHandler.allBoxesDisabled()) || 
            (!renderer.multipleChoiceHandler.allMultipleChoicesDisabled())) {
            
            /* Some questions are not disabled, hence marks are available */
            return false;
        } else {
            /* All questions disabled, no more marks available */
            return true;
        }
    }

    /** Moves to the previous page */
    public void prevPage() {
        /* Check that there is a previous page to move to */
        if (currentPage > 0) {
            /* Track progress */
        	progressTracker.setVisitedPages(currentPage);
        	
        	/* Move back a page */
            currentPage--;
            
            /* Re-draw the page */
            redraw(group, bounds);

            /* Check if this page has been visited before disable*/
            if (progressTracker.pageStatus(currentPage)) {
                /* Disable all questions */
                renderer.answerBoxHandler.DisableAllAnswerBoxes();
                renderer.multipleChoiceHandler.DisableAllMultipleChoices();
            }
        }
    }

    /** Checks if there is a next page */
    public boolean isNextPage() {
        if (currentPage < pageCount - 1) {
            return true;
        } else {
            return false;
        }
    }

    /** Checks if there is a previous page */
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
    public void openLesson() {
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
            return;
        }
        
        homePage.setRecentlyOpened(file.toString());

        /* Set the recent read Path */
        xmlHandler.setRecentReadPath(file.getAbsolutePath());

        /* Parse the file */
        ArrayList<XMLNotification> errorList = xmlHandler.parseXML(file
                .getAbsolutePath());

        /* If any errors were found during parsing, do not load the lesson */
        if (errorList.size() > 0) {
            new XMLErrorWindow(errorList, null, this);
            return;
        }

        /* Get the lesson data */
        lesson = xmlHandler.getLesson();

        /* Open the lesson */
        setPageCount(lesson.pages.size());
        setCurrentPage(0);
        setLessonOpen(true);
        
        /*This needs moving somewhere more appropriate!*/
        progressTracker = new ProgressTracker(pageCount);
        
        redraw(group, bounds);
        return;
    }
    
    public void forceOpenLesson() {
        /* Get the lesson data */
        lesson = xmlHandler.getLesson();

        /* Open the lesson */
        setPageCount(lesson.pages.size());
        setCurrentPage(0);
        setLessonOpen(true);
        
        /*This needs moving somewhere more appropriate!*/
        progressTracker = new ProgressTracker(pageCount);
        
        redraw(group, bounds);
        
        parent.updateUI();
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
            
            
            Label title = new Label("Learn Easy");
            title.setFont(new Font("Calibri", 46));
            
            Rectangle box = new Rectangle(RenderUtil.LE_WIDTH - 300, RenderUtil.LE_HEIGHT - 200, Color.LIGHTBLUE);
            box.relocate(150, 100);
            box.setEffect(new DropShadow());
            
            group.getChildren().addAll(box, title);
            
            title.relocate((RenderUtil.LE_WIDTH/2) - 120, 125);
            
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
        
        /* Add it to the list of recently opened */
        homePage.setRecentlyOpened(file.toString());
         
        /* Parse the file */
        ArrayList<XMLNotification> errorList = xmlHandler.parseXML(file
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
        
        /* Initialise the progress tracker */
        progressTracker = new ProgressTracker(pageCount);
        
        redraw(group, bounds);
        
        updateParentUI();
        
        return true;
    }
    
    /** This method checks the storage of recently opened lessons
     * and lists them on the screen in the form of hyperlinks */
    private void displayRecentlyOpenedLessons(){
        if(previewMode) {
            return;
        }
        
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
        
        vbox.relocate(675, 265);
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
            
            if(i > 8) {
                i = listOfLessons.length;
            }
        }
        
        /* Place the list on the top left corner of the learnEasy screen */
        vbox.relocate(175, 265);
        group.getChildren().add(vbox);
        
    }
    
    private void updateParentUI() {
        if(parent != null) {
            parent.updateUI();
        } else if(previewParent != null) {
            previewParent.updateUI();
        }
    }
    
    public ProgressTracker getProgress() {
        return progressTracker;
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
