package teacheasy.certificate;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import teacheasy.data.Lesson;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class CertificateWindow  {
    private WebView webView;
    private WebEngine webEngine;
    private VBox root;
    private Scene scene;
    
    private Application appRef;
    
    private Screen screen;
    
    private Lesson lesson;
    private int marks;
    
    private Button printBtn;
    
    public CertificateWindow(Application nAppRef, Lesson nLesson, int nMarks) {
        this.appRef = nAppRef;
        this.lesson = nLesson;
        this.marks = nMarks;
        
        Stage stage = new Stage();
        
        screen = Screen.getPrimary();
        
        /* Initialise the stage */
        webView = new WebView();
        webView.setMinWidth(screen.getBounds().getWidth());
        webView.setMinHeight(screen.getBounds().getHeight() - 150);
        webEngine = webView.getEngine();
        
        root = new VBox();
        
        File html = buildHtml();
        System.out.println(html.getAbsolutePath());
        
        webEngine.load(CertificateWindow.class.getResource("Certificate_Template.html").toExternalForm());
        
        scene = new Scene(root);

        printBtn = new Button("Print");
        printBtn.setId("print");
        printBtn.setOnAction(new PrintButtonHandler());
        
        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(printBtn);
        buttonBox.setAlignment(Pos.CENTER);
        
        root.getChildren().addAll(webView, buttonBox);

        stage.setTitle("Certificate");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setWidth(screen.getBounds().getWidth());
        stage.setHeight(screen.getBounds().getHeight());
        stage.setScene(scene);
        stage.show();
    }
    
    public File buildHtml() {
        File templateFile = new File("Certificate_Template.html");
        File outputHtml = new File("Output_Html");
        
        BufferedReader input;
        BufferedWriter output;
        
        try {
            input = new BufferedReader(new FileReader(templateFile));
            output = new BufferedWriter(new FileWriter(outputHtml));
            String line = null;
            
            while((line = input.readLine()) != null) {
                String outputLine;
                
                if(line.contains("LESSONTITLE")) {
                    int index = line.indexOf("LESSONTITLE");
                    outputLine = new String(line.substring(0, index) + 
                                            lesson.lessonInfo.getLessonName() + 
                                            line.substring(index + 11));
                } else if(line.contains("TEACHERNAME")) {
                    int index = line.indexOf("TEACHERNAME");
                    outputLine = new String(line.substring(0, index) + 
                                            lesson.lessonInfo.getAuthor() + 
                                            line.substring(index + 11));
                } else if(line.contains("DATE")) {
                    int index = line.indexOf("DATE");
                    outputLine = new String(line.substring(0, index) + 
                                            "date" + 
                                            line.substring(index + 4));
                } else if(line.contains("STUDENTNAME")) {
                    int index = line.indexOf("STUDENTNAME");
                    outputLine = new String(line.substring(0, index) + 
                                            "Student Name" + 
                                            line.substring(index + 11));
                } else if(line.contains("MARK")) {
                    int index = line.indexOf("MARK");
                    outputLine = new String(line.substring(0, index) + 
                                            marks + 
                                            line.substring(index + 4));
                } else if(line.contains("TOTAL")) {
                    int index = line.indexOf("TOTAL");
                    outputLine = new String(line.substring(0, index) + 
                                            lesson.lessonInfo.getTotalMarks() + 
                                            line.substring(index + 5));
                } else if(line.contains("PASSFAILMESSAGE")) {
                    String messageStr;
                    
                    if(marks >= lesson.gradeSettings.getPassBoundary()) {
                        messageStr = new String(lesson.gradeSettings.getPassMessage());
                    } else {
                        messageStr = new String(lesson.gradeSettings.getFailMessage());
                    }
                    
                    int index = line.indexOf("PASSFAILMESSAGE");
                    outputLine = new String(line.substring(0, index) + 
                                            messageStr + 
                                            line.substring(index + 15));
                } else {
                    outputLine = new String(line);
                }
                
                output.write(outputLine);
            }     
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return outputHtml;
    }
    
    public class PrintButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            printBtn.setVisible(false);
            
            WritableImage snapshot = scene.snapshot(null);
            
            BufferedImage bimage = new BufferedImage((int)screen.getBounds().getWidth(), (int)screen.getBounds().getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            bimage = SwingFXUtils.fromFXImage(snapshot, bimage);
            
            printBtn.setVisible(true);
            
            new Thread(new PrintRunnable(bimage)).start();
        }
    }
}
