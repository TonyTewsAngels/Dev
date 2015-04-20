package teacheasy.runtime;

import javafx.geometry.Insets;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import teacheasy.data.Page;
import teacheasy.data.PageObject;
import teacheasy.render.Util;

public class PropertiesPane {
    /* The reference for the currently selected object */
    private PageObject selectedObject;
    
    /* The reference for the currently selected page */
    private Page selectedPage;
    
    /* The reference for the properties pane UI element */
    private VBox pane;
    
    private Label pageTitle;
    private Label selectionTitle;
    
    private ColorPicker backgroundColorPicker;
    
    public PropertiesPane(VBox nPane) {
        this.pane = nPane;
        
        pageTitle = new Label("");
        backgroundColorPicker = new ColorPicker();
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
        } else {
            pageTitle.setText("Page " + selectedPage.getNumber() + "\n");
            backgroundColorPicker.setDisable(false);
            backgroundColorPicker.setValue(Util.colorFromString(selectedPage.getPageColour()));
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
}
