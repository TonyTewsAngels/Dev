/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.certificate;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import teacheasy.data.Lesson;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * This class creates a certificate for a completed lesson. Information is
 * pulled from the document info and inserted into an HTML certificate template
 * which is then made visible. A button is provided to print the certificate.
 * 
 * @author  Alistair Jewers
 * @version 1.0 27 May 2015
 */
public class CertificateWindow  {
    /* JavaFX HTML Components */
    private WebView htmlView;
    private WebEngine htmlEngine;
    
    /* JavaFX Components */
    private Screen screen;
    private Scene scene;
    private VBox root;
    private Button printBtn;
    
    /* Lesson data object */
    private Lesson lesson;
    
    /* Marks achieved */
    private int marks;

    /**
     * Constructor method. Creates the certificate window, 
     * filling in the necessary information.
     * 
     * @param nLesson The lesson the certificate is for.
     * @param nMarks The number of marks the student gained during the lesson.
     */
    public CertificateWindow(Lesson nLesson, int nMarks) {
        /* Set references */
        this.lesson = nLesson;
        this.marks = nMarks;
        
        /* Instantiate a stage */
        Stage stage = new Stage();
        
        /* Get screen info */
        screen = Screen.getPrimary();
        
        /* Initialise the HTML viewer */
        htmlView = new WebView();
        htmlView.setMinWidth(screen.getBounds().getWidth());
        htmlView.setMinHeight(screen.getBounds().getHeight() - 150);
        htmlEngine = htmlView.getEngine();
        
        /* Instantiate the root element */
        root = new VBox();
        
        /* Call the method to construct the html file */
        File html = buildHtml();
        
        /* Get the file path of the constructed HTML */
        String path = new String("file:" + html.getAbsolutePath().replaceAll("\\\\", "/"));

        /* Load the certificate into the viewer */
        htmlEngine.load(path);
        
        /* Instantiate the scene */
        scene = new Scene(root);
        
        /* Set up the print button */
        printBtn = new Button("Print");
        printBtn.setId("print");
        printBtn.setOnAction(new PrintButtonHandler());
        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(printBtn);
        buttonBox.setAlignment(Pos.CENTER);
        
        /* Add the view and button to the scene */
        root.getChildren().addAll(htmlView, buttonBox);

        /* Set up the stage */
        stage.setTitle("Certificate");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setWidth(screen.getBounds().getWidth());
        stage.setHeight(screen.getBounds().getHeight());
        stage.setScene(scene);
        
        /* Delete the file */
        html.delete();
        
        /* Display the window */
        stage.show();
    }
    
    /**
     * Builds an html file based on the template and the given lesson.
     * 
     * @return A new HTML file object of the certificate.
     */
    public File buildHtml() {
        /* Load the template file */
        File templateFile = new File("Certificate_Template.html");
        
        /* Create the output file */
        File outputHtml = new File("Output_Html.html");
        
        /* Reader and writer */
        BufferedReader input;
        BufferedWriter output;
        
        try {
            /* Create streams */
            input = new BufferedReader(new FileReader(templateFile));
            output = new BufferedWriter(new FileWriter(outputHtml));
            
            /* Strings to hold the current input and output lines */
            String line = null;
            String outputLine;
            
            /* 
             * While there are lines remaining, check for tags and 
             * replace with information.
             */
            while((line = input.readLine()) != null) { 
                if(line.contains("LESSONTITLE")) {
                    /* Replace the LESSONTITLE tag with the lesson title */
                    int index = line.indexOf("LESSONTITLE");
                    outputLine = new String(line.substring(0, index) + " " +
                                            lesson.lessonInfo.getLessonName() + 
                                            line.substring(index + 11));
                    
                } else if(line.contains("TEACHERNAME")) {
                    /* Replace the TEACHERNAME tag with the author of the lesson */
                    int index = line.indexOf("TEACHERNAME");
                    outputLine = new String(line.substring(0, index) + " " +
                                            lesson.lessonInfo.getAuthor() + 
                                            line.substring(index + 11));
                    
                } else if(line.contains("DATE")) {
                    /* Replace the DATE tag with the current date */
                    String date = new SimpleDateFormat("dd-MM-yyy").format(new Date());
                    int index = line.indexOf("DATE");
                    outputLine = new String(line.substring(0, index) + " " +
                                            date + 
                                            line.substring(index + 4));
                    
                } else if(line.contains("MARK")) {
                    /* Replace the MARK tag with the mark the student achieved */
                    int index = line.indexOf("MARK");
                    outputLine = new String(line.substring(0, index) + " " +
                                            marks + 
                                            line.substring(index + 4));
                    
                } else if(line.contains("TOTAL")) {
                    /* Replace the TOTAL tag with the total marks for this lesson */
                    int index = line.indexOf("TOTAL");
                    outputLine = new String(line.substring(0, index) + " " + 
                                            lesson.lessonInfo.getTotalMarks() + 
                                            line.substring(index + 5));
                    
                } else if(line.contains("PASSFAILMESSAGE")) {
                    /* A string to hold the message */
                    String messageStr;
                    
                    /* Get the appropriate string for the students mark (pass or fail)*/
                    if(marks >= lesson.gradeSettings.getPassBoundary()) {
                        messageStr = new String(lesson.gradeSettings.getPassMessage());
                    } else {
                        messageStr = new String(lesson.gradeSettings.getFailMessage());
                    }
                    
                    /* Replace the PASSFAILMESSAGE tag with the appropriate message */
                    int index = line.indexOf("PASSFAILMESSAGE");
                    outputLine = new String(line.substring(0, index) + " " + 
                                            messageStr + 
                                            line.substring(index + 15));
                } else {
                    /* If no tag is found output the line as-is */
                    outputLine = new String(line);
                }
                
                /* Write the line to the output file */
                output.write(outputLine);
            }
            
            /* Close the streams */
            input.close();
            output.close();
        } catch (IOException e) {
            /* Print any exceptions caught to the console */
            e.printStackTrace();
        }
        
        /* Return the new html file */
        return outputHtml;
    }
    
    /**
     * Handles presses on the print button by taking a snapshot of
     * the screen and printing it.
     */
    public class PrintButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            /* Hide the print button */
            printBtn.setVisible(false);
            
            /* Take a snapshot of the screen */
            WritableImage snapshot = scene.snapshot(null);
            
            /* Convert the snapshot to a Swing Buffered Image */
            BufferedImage bimage = new BufferedImage((int)screen.getBounds().getWidth(), (int)screen.getBounds().getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            bimage = SwingFXUtils.fromFXImage(snapshot, bimage);
            
            /* Show the print button again */
            printBtn.setVisible(true);
            
            /* Print the buffered image */
            new Thread(new PrintRunnable(bimage)).start();
        }
    }
}
