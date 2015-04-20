package teacheasy.runtime;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import teacheasy.data.Page;
import teacheasy.data.PageObject;
import teacheasy.render.Util;

public class PropertiesPane {
    /* A reference to the editor runtime instance that contains this properties pane */
    private EditorRunTimeData editorRuntime;
    
    /* The reference for the currently selected object */
    private PageObject selectedObject;
    
    /* The reference for the currently selected page */
    private Page selectedPage;
    
    /* The reference for the properties pane UI element */
    private VBox pane;
    
    /* Labels */
    private Label pageTitle;
    private Label selectionTitle;
    
    /* UI Element to change the page background color */
    private ColorPicker backgroundColorPicker;
    
    public PropertiesPane(VBox nPane, EditorRunTimeData parent) {
        this.pane = nPane;
        this.editorRuntime = parent;
        
        pageTitle = new Label("");
        
        backgroundColorPicker = new ColorPicker();
        backgroundColorPicker.setOnAction(new BackgroundColorChangeHandler());
        
        HBox backgroundColor = new HBox();
        backgroundColor.getChildren().addAll(new Label("Background Color: "), backgroundColorPicker);
        backgroundColor.setSpacing(10);
        
        
        selectionTitle = new Label("");
        
        pane.getChildren().addAll(pageTitle, backgroundColor, selectionTitle);
        
        pane.setSpacing(10);
        
        this.selectedObject = null;
        this.selectedPage = null;
        
        update();
    }
    
    public void update(Page nSelectedPage, PageObject nSelectedObject) {
        this.selectedPage = nSelectedPage;
        this.selectedObject = nSelectedObject;
        
        update();
    }
    
    public void update() {
        if(selectedPage == null) {
            pageTitle.setText("No Page Selected \n");
            backgroundColorPicker.setDisable(true);
            backgroundColorPicker.setValue(Util.colorFromString("#ffffffff"));
            backgroundColorPicker.fireEvent(new ActionEvent(backgroundColorPicker, backgroundColorPicker));
        } else {
            pageTitle.setText("Page " + selectedPage.getNumber() + "\n");
            backgroundColorPicker.setDisable(false);
            backgroundColorPicker.setValue(Util.colorFromString(selectedPage.getPageColour()));
            backgroundColorPicker.fireEvent(new ActionEvent(backgroundColorPicker, backgroundColorPicker));
        }
        
        if(selectedObject == null) {
            selectionTitle.setText("No Object Selected \n");
        } else {
            selectionTitle.setText("" + selectedObject.getType().toString() + "\n");
        }
    }
    
    public Page getSelectedPage() {
        return selectedPage;
    }
    
    public PageObject getSelectedObject() {
        return selectedObject;
    }
    
    public class BackgroundColorChangeHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent arg0) {
            if(selectedPage != null) {                
                selectedPage.setPageColour(Util.stringFromColor(backgroundColorPicker.getValue()));
                editorRuntime.redraw();
            }
        }
        
    }
}
