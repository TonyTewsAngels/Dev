package teacheasy.debug;

import teacheasy.certificate.CertificateWindow;
import teacheasy.data.Lesson;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * Test class for certificates.
 * 
 * @author Alistair Jewers
 * @version 1.0 27 May 2015
 */
public class CertificateTester extends Application {
    @Override
    public void start(Stage stage) {
        /* Initialise a lesson */
        Lesson lesson = new Lesson();
        
        /* Set some values */
        lesson.lessonInfo.setLessonName("A Maths Lesson");
        lesson.lessonInfo.setAuthor("Mr Anderson");
        lesson.lessonInfo.setTotalMarks(25);
        lesson.gradeSettings.setPassBoundary(20);
        lesson.gradeSettings.setPassMessage("Well done on passing the lesson!");
        lesson.gradeSettings.setFailMessage("Some more work is necessary.");
        
        /* Create a certificate */
        new CertificateWindow(lesson, 22);
    }
    
    public static void main(String args[]) {
        launch(args);
    }
}
