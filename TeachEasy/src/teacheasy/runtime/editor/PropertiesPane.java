package teacheasy.runtime.editor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import teacheasy.data.AudioObject;
import teacheasy.data.GraphicObject;
import teacheasy.data.ImageObject;
import teacheasy.data.MultipleChoiceObject;
import teacheasy.data.Page;
import teacheasy.data.PageObject;
import teacheasy.data.TextObject;
import teacheasy.data.VideoObject;
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
    
    private TextPropertiesController textPropertiesController;
    private ImagePropertiesController imagePropertiesController;
    private VideoPropertiesController videoPropertiesController;
    private AudioPropertiesController audioPropertiesController;
    private GraphicPropertiesController graphicPropertiesController;
    private MultipleChoicePropertiesController multipleChoicePropertiesController;
    
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
        
        textPropertiesController = new TextPropertiesController(this);
        imagePropertiesController = new ImagePropertiesController(this);
        videoPropertiesController = new VideoPropertiesController(this);
        audioPropertiesController = new AudioPropertiesController(this);
        graphicPropertiesController = new GraphicPropertiesController(this);
        multipleChoicePropertiesController = new MultipleChoicePropertiesController(this);
        
        currentController = new VBox();
        
        /* Initialise a blank page title label */
        pageTitle = new Label("");
        
        /* Initialise a blank selection title label */
        selectionTitle = new Label("");
        
        /* Add the page title to the properties pane */
        pane.getChildren().add(pageTitle);
        
        /* Add the background color picker to the properties pane */
        backgroundColorPicker = PropertiesUtil.addColorField("bgColor", "Background Color: ", backgroundColorPicker, pane, new BackgroundColorChangeHandler());
        
        /* Add the selected object parts of the properties pane */
        pane.getChildren().addAll(selectionTitle, currentController);
        
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
                case TEXT:
                    textPropertiesController.update((TextObject)selectedObject);
                    currentController = textPropertiesController.getTextProperties();
                    break;  
                case IMAGE:
                    imagePropertiesController.update((ImageObject)selectedObject);
                    currentController = imagePropertiesController.getImageProperties();
                    break;
                case VIDEO:
                    videoPropertiesController.update((VideoObject)selectedObject);
                    currentController = videoPropertiesController.getVideoProperties();
                    break;
                case AUDIO:
                    audioPropertiesController.update((AudioObject)selectedObject);
                    currentController = audioPropertiesController.getAudioProperties();
                    break;
                case GRAPHIC:
                    graphicPropertiesController.update((GraphicObject)selectedObject);
                    currentController = graphicPropertiesController.getGraphicProperties();
                    break;
                case MULTIPLE_CHOICE:
                    multipleChoicePropertiesController.update((MultipleChoiceObject)selectedObject);
                    currentController = multipleChoicePropertiesController.getMultipleChoiceProperties();
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
        } else {
            /* Update the page title */
            pageTitle.setText("Page " + (selectedPage.getNumber()+1) + "\n");
            
            /* Update the background color component */
            backgroundColorPicker.setDisable(false);
            backgroundColorPicker.setValue(Util.colorFromString(selectedPage.getPageColour()));
        }
        
        backgroundColorPicker.fireEvent(new ActionEvent(backgroundColorPicker, backgroundColorPicker));
        
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
