/*
 * Daniel Berhe
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package learneasy.homepage;

import java.io.File;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * This class displays a dialog box prompting the user to choose a default
 * LearnEasy folder and stores the file path of recently opened lessons.
 * 
 * @author Daniel Berhe
 * @version 1.0 25/05/15
 *
 */
public class HomePage {

    /* Homepage */
    private Preferences preference;
    private String defaultFolder;
    private Button okButton, browseButton;
    private Label infoLabel, defaultLabel;
    private TextField fileAddress;
    private CheckBox checkBox;

    private HBox labelBox, filePathBox, okBox;

    private Stage dialogStage;

    final DirectoryChooser defaultFolderChooser;

    private String RecentlyOpened5;
    private String RecentlyOpened4;
    private String RecentlyOpened3;
    private String RecentlyOpened2;
    private String RecentlyOpened1;

    public HomePage() {
        
        setDefaultFolder(new File(System.getProperty("user.home")).toString());
        defaultFolderChooser = new DirectoryChooser();
        RecentlyOpened5 = "RecentlyOpened5";
        RecentlyOpened4 = "RecentlyOpened4";
        RecentlyOpened3 = "RecentlyOpened3";
        RecentlyOpened2 = "RecentlyOpened2";
        RecentlyOpened1 = "RecentlyOpened1";
    }

    public void setPreference() {
        
        /* variables for checking the existence of keys */
        boolean defaultFolderChosen = false;
        boolean userPromptKeyExists = false;

        /* Instantiate preferences */
        preference = Preferences.userRoot().node(this.getClass().getName());

        /* This code clears user preference. Please only uncomment during testing */
        /******************************************
        try {
            preference.clear();
        } catch (BackingStoreException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }*****************************************/

        try {
            String[] storedKeys = new String[preference.keys().length];
            storedKeys = preference.keys();
            if (storedKeys.length == 0) {
                /* Store default folder with key recentlyOpened */
                preference.put("recentlyOpened", getDefaultFolder());

                /*
                 * Store user choice for prompt window at the start of LearnEasy
                 */
                preference.putBoolean("showPrompt", true);

            } else {
                /* check the existence of keys */
                for (int i = 0; i < storedKeys.length; i++) {
                    if (storedKeys[i].equals("recentlyOpened")) {
                        defaultFolderChosen = true;

                    } else if (storedKeys[i].equals("showPrompt")) {
                        userPromptKeyExists = true;
                    }
                }
            }

        } catch (BackingStoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (!defaultFolderChosen) {
            /* Store default folder with key recentlyOpened */
            preference.put("recentlyOpened", getDefaultFolder());
        } else {
            /* if default folder not chosen assign set it to the user home */
            setDefaultFolder(preference.get("recentlyOpened", "user.home"));
        }

        if (!userPromptKeyExists) {
            /*
             * Store user choice for prompt window at the start of LearnEasy
             */
            preference.putBoolean("showPrompt", true);
        }

        /* Display the prompt window */
        defaultDirectoryPrompt();
    }

    /** A method to displays a dialog box for choosing 
     * a default folder
     */
    private void defaultDirectoryPrompt() {
        if (preference.getBoolean("showPrompt", false)) {
            createPromptWindow();
        }
    }

    /** A method that makes the prompt window */
    final void createPromptWindow() {

        infoLabel = new Label();
        infoLabel
                .setText("Please select a default folder\n\n");
        infoLabel.setFont(new Font("Calibri", 16));
        

        defaultLabel = new Label();
        defaultLabel.setText("Folder: ");
        defaultLabel.setFont(new Font("Calibri", 14));

        fileAddress = new TextField();
        fileAddress.setPrefWidth(290);
        fileAddress.setText(getDefaultFolder());

        /* Set up the "Browse" button */
        browseButton = new Button("Browse");
        browseButton.setId("Browse");
        browseButton.setOnAction(new ButtonEventHandler());

        /* Set up the check box that enables the user to stop the prompt message */
        checkBox = new CheckBox("Don't show me this again");
        checkBox.setId("dialog check box");
        checkBox.setIndeterminate(false);
        checkBox.setSelected(false);
        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,
                    Boolean old_val, Boolean new_val) {
                if (checkBox.isSelected()) {
                    preference.putBoolean("showPrompt", false);
                } else {
                    preference.putBoolean("showPrompt", true);
                }
            }
        });

        /* Set up the OK button */
        okButton = new Button("OK");
        okButton.setId("OK");
        okButton.setOnAction(new ButtonEventHandler());

        /* HBox to hold the message to the user */
        labelBox = new HBox();
        labelBox.setAlignment(Pos.BASELINE_LEFT);
        labelBox.getChildren().add(infoLabel);

        /*
         * HBox to hold the "Default" label, file path textField and the
         * "Browse" button
         */
        filePathBox = new HBox();
        filePathBox.setAlignment(Pos.BASELINE_LEFT);
        filePathBox.getChildren().addAll(defaultLabel, fileAddress,
                browseButton);

        /* HBox to hold the checkbox and the OK button */
        okBox = new HBox();
        okBox.setAlignment(Pos.BASELINE_LEFT);
        okBox.setSpacing(80);
        okBox.getChildren().addAll(checkBox, okButton);

        BorderPane border = new BorderPane();
        border.setPrefSize(400, 150);
        border.setTop(labelBox);
        border.setCenter(filePathBox);
        border.setBottom(okBox);

   /*     GridPane grid = new GridPane();
        grid.setPrefSize(400, 150);
        
        grid.add(infoLabel,0,0);
        grid.add(defaultLabel,0,1);
        grid.add(filePathBox,1,1);
        grid.add(browseButton,2,1);
        grid.add(border,0,0,2,0);*/
        
        
        /* Create the dialog box and draw on screen */
        dialogStage = new Stage();
        dialogStage.setScene(new Scene(border,400,100));
        dialogStage.showAndWait();
    }

    public Preferences getPreference() {
        return preference;
    }

    public void setPreference(Preferences preference) {
        this.preference = preference;
    }

    private static void configureDirectoryChooser(
            final DirectoryChooser fileChooser) {
        fileChooser.setTitle("Choose a Directory");
        fileChooser.setInitialDirectory(new File(System
                .getProperty("user.home")));
    }

    /** A method that stores and organises the recently opened lessons */
    public void setRecentlyOpened(String pathToLesson) {

        String[] storedKeys;
        boolean recentlyOpenedListExists = false;
        boolean pathAlreadyStored = false;
        int existingPathIndex = -1;
        try {
            /* Checking if there are already stored lessons */
            storedKeys = new String[preference.keys().length];
            storedKeys = preference.keys();
            for (int i = 0; i < storedKeys.length; i++) {
                if (storedKeys[i].equals(RecentlyOpened1)) {
                    recentlyOpenedListExists = true;
                }
 
                if (pathToLesson.equals(preference.get(storedKeys[i], "doesn't exist!"))) {
                    pathAlreadyStored = true;
                    existingPathIndex = i;
                }
            }
            /*This statement reorganises the list of stored lessons depending on how many recently
             * opened lessons are currently in the list. This is necessary to ensure that if a
             * lesson already in the list is opened, it it moved to the top of the list
             *  */
            if (recentlyOpenedListExists) {
                if (pathAlreadyStored) {
                    if (existingPathIndex == 2){
                        preference.put(storedKeys[existingPathIndex], pathToLesson);
                    } else if (existingPathIndex == 3){
                        preference.put(storedKeys[existingPathIndex], preference.get(storedKeys[existingPathIndex-1], "doesn't exist!"));
                        preference.put(storedKeys[existingPathIndex-1], pathToLesson);
                    } else if (existingPathIndex == 4) {
                        preference.put(storedKeys[existingPathIndex], preference.get(storedKeys[existingPathIndex-1], "doesn't exist!"));
                        preference.put(storedKeys[existingPathIndex-1], preference.get(storedKeys[existingPathIndex-2], "doesn't exist!"));
                        preference.put(storedKeys[existingPathIndex-2], pathToLesson);
                    } else if (existingPathIndex == 5) {
                        preference.put(storedKeys[existingPathIndex], preference.get(storedKeys[existingPathIndex-1], "doesn't exist!"));
                        preference.put(storedKeys[existingPathIndex-1], preference.get(storedKeys[existingPathIndex-2], "doesn't exist!"));
                        preference.put(storedKeys[existingPathIndex-2], preference.get(storedKeys[existingPathIndex-3], "doesn't exist!"));
                        preference.put(storedKeys[existingPathIndex-3], pathToLesson);
                    } else if (existingPathIndex == 6) {
                        preference.put(storedKeys[existingPathIndex], preference.get(storedKeys[existingPathIndex-1], "doesn't exist!"));
                        preference.put(storedKeys[existingPathIndex-1], preference.get(storedKeys[existingPathIndex-2], "doesn't exist!"));
                        preference.put(storedKeys[existingPathIndex-2], preference.get(storedKeys[existingPathIndex-3], "doesn't exist!"));
                        preference.put(storedKeys[existingPathIndex-3], preference.get(storedKeys[existingPathIndex-4], "doesn't exist!"));
                        preference.put(storedKeys[existingPathIndex-4], pathToLesson);
                    }
                
                } else {
                    /* This statement is executed if the recently opened lesson is not already
                     * in the list. The statement first checks the number of already stored
                     * lessons and pushes the new one on top of the list
                     *  */
                    if(!preference.get(RecentlyOpened4,"doesn't exist!").equals("doesn't exist!")){
                        preference.put(RecentlyOpened5, preference.get(
                            RecentlyOpened4, "doesn't exist!"));
                        preference.put(RecentlyOpened4, preference.get(
                                RecentlyOpened3, "doesn't exist!"));
                        preference.put(RecentlyOpened3, preference.get(
                                RecentlyOpened2, "doesn't exist!"));
                        preference.put(RecentlyOpened2, preference.get(
                                RecentlyOpened1, "doesn't exist!"));
                        preference.put(RecentlyOpened1, pathToLesson);
                    } else if(!preference.get(RecentlyOpened3,"doesn't exist!").equals("doesn't exist!")){
                        preference.put(RecentlyOpened4, preference.get(
                                    RecentlyOpened3, "doesn't exist!"));
                        preference.put(RecentlyOpened3, preference.get(
                                    RecentlyOpened2, "doesn't exist!"));
                        preference.put(RecentlyOpened2, preference.get(
                                    RecentlyOpened1, "doesn't exist!"));
                        preference.put(RecentlyOpened1, pathToLesson);
                    } else if (!preference.get(RecentlyOpened2,"doesn't exist!").equals("doesn't exist!")){
                        preference.put(RecentlyOpened3, preference.get(
                                RecentlyOpened2, "doesn't exist!"));
                        preference.put(RecentlyOpened2, preference.get(
                                RecentlyOpened1, "doesn't exist!"));
                        preference.put(RecentlyOpened1, pathToLesson);
                    } else {
                        preference.put(RecentlyOpened2, preference.get(
                                RecentlyOpened1, "doesn't exist!"));
                        preference.put(RecentlyOpened1, pathToLesson);
                    }
                    
                }
            } else {
                preference.put(RecentlyOpened1, pathToLesson);
            }
        } catch (BackingStoreException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    public String getDefaultFolder() {
        return defaultFolder;
    }

    public void setDefaultFolder(String defaultFolder) {
        this.defaultFolder = defaultFolder;
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
            if (id.equals("OK")) {
                setDefaultFolder(fileAddress.getText());
                dialogStage.close();
                preference.put("recentlyOpened", getDefaultFolder());
            } else if (id.equals("Browse")) {
                configureDirectoryChooser(defaultFolderChooser);
                File file = defaultFolderChooser.showDialog(dialogStage);
                if (file != null) {
                    fileAddress.setText(file.toString());
                }
            }
        }
    }
}
