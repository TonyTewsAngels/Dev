package teacheasy.debug;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class EventHandlerTestDummyGUI extends Application {
    TestHandler testHandler;
    
    @Override
    public void start(Stage primaryStage) {       
        /* Instantiate the scene and group */
        Group group = new Group();
        Scene scene = new Scene(group, 500, 500);
        
        /* Setup the window */
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        
        testHandler = new TestHandler(group);
        
        testHandler.addButton(group, 200, 200);
        
        /* Show the window */
        primaryStage.show(); 
    }
    
    public static void main(String args[]) {
        launch();
    }
}
