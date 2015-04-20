package teacheasy.runtime;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import teacheasy.data.Page;
import teacheasy.data.PageObject;

public class PropertiesPane {
    /* The reference for the currently selected object */
    private PageObject selectedObject;
    
    /* The reference for the currently selected page */
    private Page selectedPage;
    
    /* The reference for the properties pane UI element */
    private VBox pane;
    
    private Label pageTitle;
    private Label selectionTitle;
    
    public PropertiesPane(VBox nPane) {
        this.pane = nPane;
        
        pageTitle = new Label("");
        selectionTitle = new Label("");
        
        pane.getChildren().addAll(pageTitle, selectionTitle);
        
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
            pageTitle.setText("No Page Selected");
        } else {
            pageTitle.setText("Page " + selectedPage.getNumber());
        }
        
        if(selectedObject == null) {
            selectionTitle.setText("No Object Selected");
        } else {
            selectionTitle.setText("" + selectedObject.getType().toString());
        }
    }
}
