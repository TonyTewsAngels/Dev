package teacheasy.runtime.editor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import teacheasy.data.ImageObject;
import teacheasy.data.Page;
import teacheasy.data.PageObject;
import teacheasy.render.Util;
import teacheasy.runtime.EditorRunTimeData;

/**
 * Encapsulates functionality relating to the properties pane of
 * the editor. Displays values related to the selected page and
 * allows their modification. Contains classes to encapsulate editor
 * functionality for specific object types.
 * 
 * @author Alistair Jewers
 * @version 1.0 Apr 21 2015
 */
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
    
    private ImagePropertiesController imagePropertiesController;
    
    private VBox currentController;
    
    /**
     * Constructor.
     * 
     * @param nPane The UI node for the pane. Type VBox.
     * 
     * @param parent The runtime data for the editor that controls
     * this properties pane.
     * 
     */
    public PropertiesPane(VBox nPane, EditorRunTimeData parent) {
        /* Initialise references */
        this.pane = nPane;
        this.editorRuntime = parent;
        
        imagePropertiesController = new ImagePropertiesController(this);
        
        currentController = new VBox();
        
        /* Initialise a blank page title label */
        pageTitle = new Label("");
        
        /* Set up the color picer for the slide background color */
        backgroundColorPicker = new ColorPicker();
        backgroundColorPicker.setOnAction(new BackgroundColorChangeHandler());
        
        /* Set up the row in the properties pane for the background color */
        HBox backgroundColor = new HBox();
        backgroundColor.getChildren().addAll(new Label("Background Color: "), backgroundColorPicker);
        backgroundColor.setSpacing(10);
        
        /* Initialise a blank selection title label */
        selectionTitle = new Label("");
        
        /* Add the new components to the properties pane */
        pane.getChildren().addAll(pageTitle, backgroundColor, selectionTitle, currentController);
        
        /* Set the pane's spacing */
        pane.setSpacing(10);
        
        /* Initialise the objects to act on to null */
        this.selectedObject = null;
        this.selectedPage = null;
        
        /* Update the labels and controls */
        update();
    }
    
    /**
     * Updates the properties pane with changes to the selection.
     * 
     * @param nSelectedPage The currently selected page. Can be null if no lesson is open.
     * @param nSelectedObject The currently selected object on the page. Can be null if no object is selected.
     */
    public void update(Page nSelectedPage, PageObject nSelectedObject) {
        this.selectedPage = nSelectedPage;
        this.selectedObject = nSelectedObject;
        
        pane.getChildren().remove(currentController);
        
        if(selectedObject != null) {
            switch(selectedObject.getType()) {
                case IMAGE:
                    imagePropertiesController.update((ImageObject)selectedObject);
                    currentController = imagePropertiesController.getImageProperties();
                    break;
                default:
                    currentController = new VBox();
                    break;
            }
        } else {
            currentController = new VBox();
        }

        pane.getChildren().add(currentController);
        
        update();
    }
    
    /**
     * Updates the properties pane content without changing the selection.
     */
    public void update() {
        /* Update the page options */
        if(selectedPage == null) {
            /* Update the page title */
            pageTitle.setText("No Page Selected \n");
            
            /* Update the background color component */
            backgroundColorPicker.setDisable(true);
            backgroundColorPicker.setValue(Util.colorFromString("#ffffffff"));
            backgroundColorPicker.fireEvent(new ActionEvent(backgroundColorPicker, backgroundColorPicker));
        } else {
            /* Update the page title */
            pageTitle.setText("Page " + selectedPage.getNumber() + "\n");
            
            /* Update the background color component */
            backgroundColorPicker.setDisable(false);
            backgroundColorPicker.setValue(Util.colorFromString(selectedPage.getPageColour()));
            backgroundColorPicker.fireEvent(new ActionEvent(backgroundColorPicker, backgroundColorPicker));
        }
        
        /* Update the object options */
        if(selectedObject == null) {
            selectionTitle.setText("No Object Selected \n");
        } else {
            selectionTitle.setText("" + selectedObject.getType().toString() + "\n");
        }
    }
    
    
    /**
     * Retrieve the selected page.
     * 
     * @return The selected page.
     */
    public Page getSelectedPage() {
        return selectedPage;
    }
    
    /**
     * Retrieve the selected object on the page.
     * 
     * @return The selected page object.
     */
    public PageObject getSelectedObject() {
        return selectedObject;
    }
    
    public void redraw() {
        editorRuntime.redraw();
    }
    
    /**
     * Event handler to handle changes to the background color.
     */
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
