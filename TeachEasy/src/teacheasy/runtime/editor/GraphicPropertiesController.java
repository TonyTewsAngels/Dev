package teacheasy.runtime.editor;

import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import teacheasy.data.GraphicObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

/**
 * Encapsulates functionality relating to editor
 * functionality for Video objects.
 * 
 * @author Alistair Jewers
 * @version 1.0 Apr 21 2015
 */
public class GraphicPropertiesController {
    /* Reference to the properties pane responsible for this controller */
    private PropertiesPane parent;
    
    /* Currently selected image */
    private GraphicObject selectedGraphic;
    
    /* The UI element to contain the editable properties */
    private VBox graphicProperties;
    
    /* The text fields for the different properties */
    private TextField xStartProperty;
    private TextField yStartProperty;
    private TextField xEndProperty;
    private TextField yEndProperty;
    
    /**
     * Constructor. 
     * 
     * @param nParent The properties pane responsible for this controller.
     */
    public GraphicPropertiesController(PropertiesPane nParent) {
        /* Set the parent reference */
        this.parent = nParent;
        
        /* Set the selected object null */
        selectedGraphic = null;
        
        /* Set up the UI container */
        graphicProperties = new VBox();
        graphicProperties.setSpacing(5);
        graphicProperties.setPadding(new Insets(5));
        
        /* Set up the property fields */
        xStartProperty = PropertiesUtil.addPropertyField("xStart", "X Start: ", xStartProperty, graphicProperties, new PropertyChangedHandler());
        yStartProperty = PropertiesUtil.addPropertyField("yStart", "Y Start: ", yStartProperty, graphicProperties, new PropertyChangedHandler());
        xEndProperty = PropertiesUtil.addPropertyField("xEnd", "X End: ", xEndProperty, graphicProperties, new PropertyChangedHandler());
        yEndProperty = PropertiesUtil.addPropertyField("yEnd", "Y End: ", yEndProperty, graphicProperties, new PropertyChangedHandler());
    }

    public void update(GraphicObject nGraphic) {
        selectedGraphic = nGraphic;
        
        update();
    }
    
    public void update() {
        if(selectedGraphic == null) {
            xStartProperty.setText("");
            yStartProperty.setText("");
            xEndProperty.setText("");
            yEndProperty.setText("");
        } else {
            xStartProperty.setText(String.valueOf(selectedGraphic.getXStart()));
            yStartProperty.setText(String.valueOf(selectedGraphic.getYStart()));
            xEndProperty.setText(String.valueOf(selectedGraphic.getXEnd()));
            yEndProperty.setText(String.valueOf(selectedGraphic.getYEnd()));
        }
    }
    
    public VBox getGraphicProperties() {
        return graphicProperties;
    }

    public class PropertyChangedHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            if(selectedGraphic == null) {
                return;
            }
            
            TextField source = (TextField)e.getSource();
            
            switch(source.getId()) {
                case "xStart":
                    selectedGraphic.setXStart(PropertiesUtil.validatePosition(source.getText(), selectedGraphic.getXStart()));
                    break;
                case "yStart":
                    selectedGraphic.setYStart(PropertiesUtil.validatePosition(source.getText(), selectedGraphic.getYStart()));
                    break;
                case "xEnd":
                    selectedGraphic.setXEnd(PropertiesUtil.validatePosition(source.getText(), selectedGraphic.getXEnd()));
                    break;
                case "yEnd":
                    selectedGraphic.setYEnd(PropertiesUtil.validatePosition(source.getText(), selectedGraphic.getYEnd()));
                    break;
                default:
                    break;
            }
            
            update();
            parent.redraw();
        }
    }
}
