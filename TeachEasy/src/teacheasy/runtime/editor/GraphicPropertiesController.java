package teacheasy.runtime.editor;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import teacheasy.data.GraphicObject;
import teacheasy.render.RenderUtil;
import wavemedia.graphic.GraphicType;
import wavemedia.graphic.Shading;
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
    
    /* The combo boxes to hold the graphic and shading types */
    private ComboBox<String> typeProperty;
    private ComboBox<String> shadingProperty;
    
    /* The text fields for the different properties */
    private TextField xStartProperty;
    private TextField yStartProperty;
    private TextField xEndProperty;
    private TextField yEndProperty;
    private TextField rotationProperty;
    private TextField outlineThicknessProperty;
    
    /* The boolean properties */
    private CheckBox solidProperty;
    private CheckBox shadowProperty;
    
    /* The color properties */
    private ColorPicker graphicColorProperty;
    private ColorPicker lineColorProperty;
    private ColorPicker shadingColorProperty;
    
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
        
        /* Set up the type property field */
        typeProperty = PropertiesUtil.addSelectionField("type", "Type: ", typeProperty, graphicProperties, new SelectionPropertyChangedHandler());
        typeProperty.getItems().addAll(GraphicType.OVAL.toString(),
                                       GraphicType.RECTANGLE.toString(),
                                       GraphicType.LINE.toString(),
                                       GraphicType.ARROW.toString());
        
        shadingProperty = PropertiesUtil.addSelectionField("shading", "Shading: ", shadingProperty, graphicProperties, new SelectionPropertyChangedHandler());
        shadingProperty.getItems().addAll(Shading.NONE.toString(),
                                          Shading.HORIZONTAL.toString(),
                                          Shading.VERTICAL.toString(),
                                          Shading.CYCLIC.toString());
        
        /* Set up the position property fields */
        xStartProperty = PropertiesUtil.addPropertyField("xStart", "X Start: ", xStartProperty, graphicProperties, new PropertyChangedHandler());
        yStartProperty = PropertiesUtil.addPropertyField("yStart", "Y Start: ", yStartProperty, graphicProperties, new PropertyChangedHandler());
        xEndProperty = PropertiesUtil.addPropertyField("xEnd", "X End: ", xEndProperty, graphicProperties, new PropertyChangedHandler());
        yEndProperty = PropertiesUtil.addPropertyField("yEnd", "Y End: ", yEndProperty, graphicProperties, new PropertyChangedHandler());
        rotationProperty = PropertiesUtil.addPropertyField("rotation", "Rotation: ", rotationProperty, graphicProperties, new PropertyChangedHandler());
        outlineThicknessProperty = PropertiesUtil.addPropertyField("outlineThickness", "Outline Thickness: ", outlineThicknessProperty, graphicProperties, new PropertyChangedHandler());
        
        /* Set up the boolean property fields */
        solidProperty = PropertiesUtil.addBooleanField("solid", "Solid: ", solidProperty, graphicProperties, new BooleanPropertyChangedHandler());
        shadowProperty = PropertiesUtil.addBooleanField("shadow", "Shadow: ", shadowProperty, graphicProperties, new BooleanPropertyChangedHandler());
        
        /* Set up the color property fields */
        graphicColorProperty = PropertiesUtil.addColorField("graphicColor", "Graphic Color: ", graphicColorProperty, graphicProperties, new ColorPropertyChangedHandler());
        lineColorProperty = PropertiesUtil.addColorField("lineColor", "Outline Color: ", lineColorProperty, graphicProperties, new ColorPropertyChangedHandler());
        shadingColorProperty = PropertiesUtil.addColorField("shadingColor", "Shading Color: ", shadingColorProperty, graphicProperties, new ColorPropertyChangedHandler());
    }

    public void update(GraphicObject nGraphic) {
        if(selectedGraphic != null) {
            if(selectedGraphic.equals(nGraphic)) {
                update(true);
                return;
            }
        }
        
        selectedGraphic = nGraphic;
        
        update(false);
    }
    
    public void update(boolean soft) {
        if(selectedGraphic == null) {
            xStartProperty.setText("");
            yStartProperty.setText("");
            xEndProperty.setText("");
            yEndProperty.setText("");
            rotationProperty.setText("");
            outlineThicknessProperty.setText("");
            solidProperty.setSelected(false);
            shadowProperty.setSelected(false);
            graphicColorProperty.setValue(Color.WHITE);
            lineColorProperty.setValue(Color.WHITE);
            typeProperty.setValue("");
            shadingProperty.setValue("");
        } else {
            xStartProperty.setText(String.valueOf(selectedGraphic.getXStart()));
            yStartProperty.setText(String.valueOf(selectedGraphic.getYStart()));
            xEndProperty.setText(String.valueOf(selectedGraphic.getXEnd()));
            yEndProperty.setText(String.valueOf(selectedGraphic.getYEnd()));
            rotationProperty.setText(String.valueOf(selectedGraphic.getRotation()));
            outlineThicknessProperty.setText(String.valueOf(selectedGraphic.getOutlineThickness()));
            solidProperty.setSelected(selectedGraphic.isSolid());
            shadowProperty.setSelected(selectedGraphic.isShadow());
            graphicColorProperty.setValue(RenderUtil.colorFromString(selectedGraphic.getGraphicColour()));
            lineColorProperty.setValue(RenderUtil.colorFromString(selectedGraphic.getLineColor()));
            typeProperty.setValue(selectedGraphic.getGraphicType().toString());
            shadingProperty.setValue(selectedGraphic.getShading().toString());
        }
        
        if(!soft) {
            graphicColorProperty.fireEvent(new ActionEvent(graphicColorProperty, graphicColorProperty));
            lineColorProperty.fireEvent(new ActionEvent(lineColorProperty, lineColorProperty));
            shadingColorProperty.fireEvent(new ActionEvent(shadingColorProperty, shadingColorProperty));
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
                case "rotation":
                    selectedGraphic.setRotation(PropertiesUtil.validateFloat(source.getText(), selectedGraphic.getRotation()));
                    break;
                case "outlineThickness":
                    selectedGraphic.setOutlineThickness(PropertiesUtil.validateFloat(source.getText(), selectedGraphic.getOutlineThickness()));
                    break;
                default:
                    break;
            }
            
            update(false);
            parent.redraw();
        }
    }
    
    public class BooleanPropertyChangedHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            if(selectedGraphic == null) {
                return;
            }
            
            CheckBox source = (CheckBox)e.getSource();
            
            switch(source.getId()) {
                case "solid":
                    selectedGraphic.setSolid(source.isSelected());
                    break;
                case "shadow":
                    selectedGraphic.setShadow(source.isSelected());
                    break;
                default:
                    break;
            }
            
            update(false);
            parent.redraw();
        }
    }
    
    public class ColorPropertyChangedHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            if(selectedGraphic == null) {
                return;
            }
            
            ColorPicker source = (ColorPicker)e.getSource();
            
            switch(source.getId()) {
                case "graphicColor":
                    selectedGraphic.setGraphicColor(RenderUtil.stringFromColor(source.getValue()));
                    break;
                case "lineColor":
                    selectedGraphic.setLineColor(RenderUtil.stringFromColor(source.getValue()));
                    break;
                case "shadingColor":
                    selectedGraphic.setShadingColor(RenderUtil.stringFromColor(source.getValue()));
                    break;
                default:
                    break;
            }
            
            parent.redraw();
        }
    }
    
    public class SelectionPropertyChangedHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            if(selectedGraphic == null) {
                return;
            }
            
            ComboBox<String> source = (ComboBox<String>)e.getSource();
            
            switch(source.getId()) {
                case "type":
                    selectedGraphic.setType(GraphicType.valueOf(source.getValue()));
                    break;
                case "shading":
                    selectedGraphic.setShading(Shading.valueOf(source.getValue()));
                    break;
                default:
                    break;
            }
            
            update(false);
            parent.redraw();
        }
    }
}
