package teacheasy.certificate;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
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
    private Group root;
    
    public CertificateWindow() {
        Stage stage = new Stage();
        
        Screen screen = Screen.getPrimary();
        
        /* Initialise the stage */
        webView = new WebView();
        webEngine = webView.getEngine();
        
        root = new Group();
        
        webEngine.load(CertificateWindow.class.getResource("Certificate_Template.html").getPath());
        
        Scene scene = new Scene(root);
        
        root.getChildren().add(webView);

        stage.setTitle("Certificate");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setWidth(screen.getBounds().getWidth());
        stage.setHeight(screen.getBounds().getHeight());
        stage.setScene(scene);
        stage.show();
    }
}
