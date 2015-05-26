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

public class GraphicXMLHandler extends DefaultHandler{
    private Lesson lesson;
    private Page page;
    private GraphicObject graphic;
    
    private ArrayList<XMLNotification> errorList;
    private XMLReader xmlReader;
    private DefaultHandler parent;
    
    public GraphicXMLHandler(XMLReader nXMLReader, DefaultHandler nParent, Lesson nLesson, Page nPage, ArrayList<XMLNotification> nErrorList, Attributes multipleChoiceAttrs) {
        this.xmlReader = nXMLReader;
        this.parent = nParent;
        
        this.lesson = nLesson;
        this.page = nPage;
        this.errorList = nErrorList;
        
        graphic = new GraphicObject(GraphicType.LINE, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, null, true, null, 1.0f, false, 0.0f, 0.0f);
        
        multipleChoiceStart(multipleChoiceAttrs);
    }
    
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
    
    public void endHandler() {
        page.pageObjects.add(graphic);
        
        xmlReader.setContentHandler(parent);
    }
    
    public void handleShading(Attributes attrs) {
        String shadingColor = attrs.getValue("shadingcolor");
        
        shadingColor = XMLUtil.checkColor(shadingColor, graphic.getGraphicColour(), Level.ERROR, errorList, 
                "Page " + lesson.pages.size() + ", Object " + page.getObjectCount() +" (Graphic) Shading color ");
        
        graphic.setShadingColor(shadingColor);
    }
    
    public void multipleChoiceStart(Attributes attrs) {
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
        
        GraphicType type;
        
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
        
        graphic = new GraphicObject(type, xStart, yStart, xEnd, yEnd, rotation, graphicColorStr, solid, lineColorStr, outlineThickness, shadow, startTime, duration);
    }
}
