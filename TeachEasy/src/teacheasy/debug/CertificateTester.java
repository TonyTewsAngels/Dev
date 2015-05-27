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
        new CertificateWindow(this, new Lesson(), 10);
    }
    
    public static void main(String args[]) {
        launch(args);
    }
}
