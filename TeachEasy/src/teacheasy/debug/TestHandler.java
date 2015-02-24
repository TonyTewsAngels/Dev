package teacheasy.debug;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;

public class TestHandler {
    public TestHandler(Group group) {
        Button button = new Button("Button");
        button.relocate(100, 100);
        button.setOnAction(new TestEventHandler());
        
        group.getChildren().add(button);
    }
    
    public void addButton(Group group, double x, double y) {
        Button button = new Button("Button");
        button.relocate(x, y);
        button.setOnAction(new TestEventHandler());
        
        group.getChildren().add(button);
    }
    
    /**
     * Event Handler Class
     */
    public class TestEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            System.out.println("Test Button Pressed");
        }
    }
}
