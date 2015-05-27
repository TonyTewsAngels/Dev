package teacheasy.debug;

import teacheasy.certificate.CertificateWindow;
import teacheasy.data.Lesson;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class CertificateTester extends Application {
    @Override
    public void start(Stage stage) {
        Lesson lesson = new Lesson();
        
        lesson.lessonInfo.setLessonName("A Really Dull Maths Lesson");
        lesson.lessonInfo.setAuthor("Some Dick Head");
        lesson.gradeSettings.setPassBoundary(1);
        lesson.gradeSettings.setPassMessage("Nice one m8");
        lesson.gradeSettings.setFailMessage("U suk");
        
        new CertificateWindow(this, lesson, 1);
    }
    
    public static void main(String args[]) {
        launch(args);
    }
}
