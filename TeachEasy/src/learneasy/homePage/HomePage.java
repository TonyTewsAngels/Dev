/*
 * Daniel Berhe
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package learneasy.homePage;

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
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * This class displays a dialog box prompting the user to choose a default
 * LearnEasy folder.
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

    private String fifthRecentlyOpened;
    private String fourthRecentlyOpened;
    private String thirdRecentlyOpened;
    private String secondRecentlyOpened;
    private String firstRecentlyOpened;

    public HomePage() {

        defaultFolder = "C:/Users/Daniel/git/Dev/TeachEasy";
        defaultFolderChooser = new DirectoryChooser();
        fifthRecentlyOpened = "fifthRecentlyOpened";
        fourthRecentlyOpened = "fourthRecentlyOpened";
        thirdRecentlyOpened = "thirdRecentlyOpened";
        secondRecentlyOpened = "secondRecentlyOpened";
        firstRecentlyOpened = "firstRecentlyOpened";
    }

    public void setPreference() {

        boolean recentlyOpenedKeyExists = false;
        boolean userPromptKeyExists = false;

        /* Instantiate preferences */
        preference = Preferences.userRoot().node(this.getClass().getName());

        try {
            preference.clear();
        } catch (BackingStoreException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            String[] storedKeys = new String[preference.keys().length];
            storedKeys = preference.keys();
            System.out.println("length is: " + storedKeys.length);
            if (storedKeys.length == 0) {
                /* Store default folder with key recentlyOpened */
                preference.put("recentlyOpened", defaultFolder);

                /*
                 * Store user choice for prompt window at the start of LearnEasy
                 */
                preference.putBoolean("showPrompt", true);

            } else {
                for (int i = 0; i < storedKeys.length; i++) {
                    if (storedKeys[i].equals("recentlyOpened")) {
                        recentlyOpenedKeyExists = true;

                    } else if (storedKeys[i].equals("showPrompt")) {
                        userPromptKeyExists = true;
                    }
                }
            }

        } catch (BackingStoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (!recentlyOpenedKeyExists) {
            /* Store default folder with key recentlyOpened */
            preference.put("recentlyOpened", defaultFolder);
        } else {
            defaultFolder = preference.get("recentlyOpened", "user.home");
        }

        if (!userPromptKeyExists) {
            /*
             * Store user choice for prompt window at the start of LearnEasy
             */
            preference.putBoolean("showPrompt", true);
        }

        defaultDirectoryPrompt();
    }

    private void defaultDirectoryPrompt() {
        if (preference.getBoolean("showPrompt", false)) {
            createPromptWindow();
        }
    }

    final void createPromptWindow() {

        infoLabel = new Label();
        infoLabel
                .setText("Having default folder simplifies lesson loading.\nPlease choose a default folder\n\n\n");

        defaultLabel = new Label();
        defaultLabel.setText("Default: ");

        fileAddress = new TextField();
        fileAddress.setPrefWidth(290);
        fileAddress.setText(defaultFolder);

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

        /* Create the dialog box and draw on screen */
        dialogStage = new Stage();
        dialogStage.setScene(new Scene(border));
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

    public void setRecentlyOpened(String pathToLesson) {

        String[] storedKeys;
        boolean recentlyOpenedListExists = false;
        boolean pathAlreadyStored = false;
        int existingPathIndex = -1;
        try {
            storedKeys = new String[preference.keys().length];
            storedKeys = preference.keys();
            for (int i = 0; i < storedKeys.length; i++) {
                System.out.println("The keys are: "+ storedKeys[i] + "@ " + storedKeys.length);
                if (storedKeys[i].equals(firstRecentlyOpened)) {
                    recentlyOpenedListExists = true;
                    System.out.println("The length is: " + i);
                }
 
                if (pathToLesson.equals(preference.get(storedKeys[i], "doesn't exist!"))) {
                    pathAlreadyStored = true;
                    existingPathIndex = i;
                }
            }
            if (recentlyOpenedListExists) {
                if (pathAlreadyStored) {
                    System.out.println("Getting here!!!");
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
                    if(!preference.get(fourthRecentlyOpened,"doesn't exist!").equals("doesn't exist!")){
                        preference.put(fifthRecentlyOpened, preference.get(
                            fourthRecentlyOpened, "doesn't exist!"));
                        preference.put(fourthRecentlyOpened, preference.get(
                                thirdRecentlyOpened, "doesn't exist!"));
                        preference.put(thirdRecentlyOpened, preference.get(
                                secondRecentlyOpened, "doesn't exist!"));
                        preference.put(secondRecentlyOpened, preference.get(
                                firstRecentlyOpened, "doesn't exist!"));
                        preference.put(firstRecentlyOpened, pathToLesson);
                    } else if(!preference.get(thirdRecentlyOpened,"doesn't exist!").equals("doesn't exist!")){
                        preference.put(fourthRecentlyOpened, preference.get(
                                    thirdRecentlyOpened, "doesn't exist!"));
                        preference.put(thirdRecentlyOpened, preference.get(
                                    secondRecentlyOpened, "doesn't exist!"));
                        preference.put(secondRecentlyOpened, preference.get(
                                    firstRecentlyOpened, "doesn't exist!"));
                        preference.put(firstRecentlyOpened, pathToLesson);
                    } else {
                        preference.put(thirdRecentlyOpened, preference.get(
                                secondRecentlyOpened, "doesn't exist!"));
                        preference.put(secondRecentlyOpened, preference.get(
                                firstRecentlyOpened, "doesn't exist!"));
                        preference.put(firstRecentlyOpened, pathToLesson);
                    }
                    
                }
            } else {
                preference.put(firstRecentlyOpened, pathToLesson);
            }
        } catch (BackingStoreException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

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
                defaultFolder = fileAddress.getText();
                dialogStage.close();
                preference.put("recentlyOpened", defaultFolder);
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
