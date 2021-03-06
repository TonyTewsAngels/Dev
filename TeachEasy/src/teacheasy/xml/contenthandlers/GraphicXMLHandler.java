/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved. 
 */
package teacheasy.xml.contenthandlers;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import teacheasy.data.GraphicObject;
import wavemedia.graphic.*;
import teacheasy.data.Lesson;
import teacheasy.data.Page;
import teacheasy.xml.XMLElement;
import teacheasy.xml.util.XMLNotification;
import teacheasy.xml.util.XMLUtil;
import teacheasy.xml.util.XMLNotification.*;

/**
 * The XML content handler for a graphic element.
 * 
 * @author  Alistair Jewers
 * @version 1.0 Apr 12 2015 
 */
public class GraphicXMLHandler extends DefaultHandler{
    
    /* Data Objects */
    private Lesson lesson;
    private Page page;
    private GraphicObject graphic;
    
    /** List of warnings and errors */
    private ArrayList<XMLNotification> errorList;
    
    /** Reference to the xml reader */
    private XMLReader xmlReader;
    
    /** The handler that activated this one */
    private DefaultHandler parent;
    
    /**
     * Constructor. 
     * 
     * @param nXMLReader The XML reader reference.
     * @param nParent The parent handler.
     * @param nLesson The lesson being constructed.
     * @param nPage The page being constructed.
     * @param nErrorList The full error and warnings list.
     * @param graphicAttrs The attributes of this graphics element.
     */
    public GraphicXMLHandler(XMLReader nXMLReader, DefaultHandler nParent, Lesson nLesson, Page nPage, ArrayList<XMLNotification> nErrorList, Attributes graphicAttrs) {
        /* Set references */
        this.xmlReader = nXMLReader;
        this.parent = nParent;
        this.lesson = nLesson;
        this.page = nPage;
        this.errorList = nErrorList;
        
        /* Create an empty graphic data object */
        graphic = new GraphicObject(GraphicType.LINE, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, null, true, null, 1.0f, false, 0.0f, 0.0f);
        
        /* Start constructing the graphic object */
        graphicStart(graphicAttrs);
    }
    
    /**
     * Called when the start of an XML element is found.
     */
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        switch(XMLElement.check(qName.toUpperCase())) {
            case CYCLICSHADING:
                graphic.setShading(Shading.CYCLIC);
                handleShading(attrs);
                break;
            case HORIZONTALSHADING:
                graphic.setShading(Shading.HORIZONTAL);
                handleShading(attrs);
                break;
            case VERTICALSHADING:
                graphic.setShading(Shading.VERTICAL);
                handleShading(attrs);
                break;
            default:
                break;
        }
    }
    
    /**
     * Called when the end of an XML element is found.
     */
    public void endElement(String uri, String localName, String qName) {
        switch(XMLElement.check(qName.toUpperCase())) {
            /* If the image element has finished, return to the parent */
            case GRAPHIC:
                endHandler();
                break;            
            default:
                break;
        }
    }
    
    /**
     * Called when the handler is finished to pass control back to the parent handler.
     */
    public void endHandler() {
        /* Add the graphic data object to the page */
        page.pageObjects.add(graphic);
        
        /* Pass control back to the parent handler */
        xmlReader.setContentHandler(parent);
    }
    
    /**
     * Handles the shading XML element.
     * 
     * @param attrs The attributes found in the XML.
     */
    public void handleShading(Attributes attrs) {
        /* Attempt to parse the string */
        String shadingColor = attrs.getValue("shadingcolor");
        
        /* Check the color format */
        shadingColor = XMLUtil.checkColor(shadingColor, graphic.getGraphicColour(), Level.ERROR, errorList, 
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Graphic) Shading color ");
        
        /* Set the graphic shading color */
        graphic.setShadingColor(shadingColor);
    }
    
    /**
     * Handles the graphic XML element.
     * 
     * @param attrs The attributes found in the XML.
     */
    public void graphicStart(Attributes attrs) {
        /* Parse the strings from XML attributes */
        String xStartStr = attrs.getValue("xstart");
        String yStartStr = attrs.getValue("ystart");
        String xEndStr = attrs.getValue("xend");
        String yEndStr = attrs.getValue("yend");
        String typeStr = attrs.getValue("type");
        String solidStr = attrs.getValue("solid");
        String graphicColorStr = attrs.getValue("graphiccolor");
        String rotationStr = attrs.getValue("rotation");
        String shadowStr = attrs.getValue("shadow");
        String outlineThicknessStr = attrs.getValue("outlinethickness");
        String lineColorStr = attrs.getValue("linecolor");
        String startTimeStr = attrs.getValue("starttime");
        String durationStr = attrs.getValue("duration");
        
        /* Attempt to parse values, add errors as necessary */
        float xStart = XMLUtil.checkFloat(xStartStr, 0.0f, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Graphic) X Start ");
        
        float yStart = XMLUtil.checkFloat(yStartStr, 0.0f, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Graphic) Y Start ");
        
        float xEnd = XMLUtil.checkFloat(xEndStr, 0.0f, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Graphic) X End ");
        
        float yEnd = XMLUtil.checkFloat(yEndStr, 0.0f, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Graphic) Y End ");
        
        if(typeStr == null) {
            errorList.add(new XMLNotification(Level.ERROR,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Graphic) Type missing."));
            typeStr = new String("line");
        }
        
        /* Graphic type variable */
        GraphicType type;
        
        /* Attempt to parse the graphic type */
        try {
            type = GraphicType.valueOf(typeStr.toUpperCase()); 
        } catch(Exception e) {
            type = GraphicType.LINE;
        }
        
        boolean solid = XMLUtil.checkBool(solidStr, true, Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Graphic) Solid setting ");
        
        graphicColorStr = XMLUtil.checkColor(graphicColorStr, "#ffffffff", Level.ERROR, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Graphic) Color ");
        
        float rotation = XMLUtil.checkFloat(rotationStr, 0.0f, Level.WARNING, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Graphic) Rotation ");
        
        boolean shadow = XMLUtil.checkBool(shadowStr, false, Level.WARNING, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Graphic) Shadow setting ");
        
        lineColorStr = XMLUtil.checkColor(lineColorStr, graphicColorStr, Level.WARNING, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Graphic) Line Color ");
        
        float outlineThickness = XMLUtil.checkFloat(outlineThicknessStr, 0.5f, Level.WARNING, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Graphic) Line thickness ");
        
        float startTime = XMLUtil.checkFloat(startTimeStr, 0.0f, Level.WARNING, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Graphic) StartTime ");
        
        float duration = XMLUtil.checkFloat(durationStr, 0.0f, Level.WARNING, errorList,
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Graphic) Duration ");
        
        /* Construct the graphic object with the data parsed */
        graphic = new GraphicObject(type, xStart, yStart, xEnd, yEnd, rotation, graphicColorStr, solid, lineColorStr, outlineThickness, shadow, startTime, duration);
    }
}
