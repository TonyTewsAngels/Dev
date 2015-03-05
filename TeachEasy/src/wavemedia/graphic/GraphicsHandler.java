package wavemedia.graphic;

import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.*;

/**
 * Class for handling shapes and graphs on a javafx group.
 * 
 * @author tjd511, jod512, lmo1024
 * @version 0.4 02/03/2015
 */
public class GraphicsHandler {

    private Group group;

    /**
     * Constructor for the graphicsHandler.
     * 
     * @param group
     *            The group that all of the graphics are to be drawn to.
     */
    public GraphicsHandler(Group group) {

        // TODO Auto-generated constructor stub
        this.group = group;
    }

    /**
     * Method to set the color of any shape that is passed to it. Sets the
     * stroke of lines, and the fill of any other shapes. Also handles shading,
     * and shadow (for all shapes apart from line).
     * 
     * @param shape
     *            the shape that is to be colored.
     * @param solid
     *            boolean value of if the shape is an outline or a solid shape
     * @param shapeColor
     *            color of the shape
     * @param outlineColor
     *            color of the shape outline
     * @param outlineThickness
     *            thickness of the shape outline
     * @param shadowType
     *            enum for type of shadow. Options: Shadow.NONE, Shadow.LIGHT,
     *            Shadow.NORMAL and Shadow.HEAVY.
     * @param shadingType
     *            enum for type of shading. Options: Shading.NONE,
     *            Shading.CYCLIC, Shading.HORIZONTAL, Shading.VERTICAL.
     * @param shadingColors
     *            varargs of type Color that contains all of the shapes shaded
     *            colors.
     */
    private void colorShape(Shape shape, boolean solid, Color shapeColor, Color outlineColor, double outlineThickness,
            Shadow shadowType, Shading shadingType, Color... shadingColors) {
        /* Set the outline parameters */
        shape.setStroke(outlineColor);
        shape.setStrokeWidth(outlineThickness);

        if (solid) {
            Stop[] stops = null;
            /* Code to generate stops for if a shading type is to be used. */
            if (shadingType != Shading.NONE) {
                /*
                 * Builds an array of stops evenly spaced through the shape
                 * consisting of the varargs of shadingColors. The last color in
                 * the varargs is the outer shading color for radial gradient.
                 */
                stops = new Stop[shadingColors.length + 1];
                /* Add the shape color as the central color */
                stops[0] = new Stop(0, shapeColor);
                /* Loop through the varargs adding the colors to the stop array */
                for (int i = 1; i <= shadingColors.length; i++)
                    stops[i] = (new Stop((float) i / shadingColors.length, shadingColors[i - 1]));
            }

            /*
             * Switch to set different types of shading using the stops created
             * above
             */
            switch (shadingType) {
            case CYCLIC:
                shape.setFill(new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE, stops));
                break;
            case HORIZONTAL:
                /* If statement to handle shaded lines */
                if (shape.getClass().equals(new Line().getClass())) {
                    shape.setStroke(new LinearGradient(0, 0.5, 1, 0.5, true, CycleMethod.NO_CYCLE, stops));
                } else {
                    shape.setFill(new LinearGradient(0, 0.5, 1, 0.5, true, CycleMethod.NO_CYCLE, stops));
                }
                break;
            case VERTICAL:
                shape.setFill(new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE, stops));
                break;
            case NONE:
                /* Falls through */
            default:
                shape.setFill(shapeColor);
                break;
            }
        }

        /* Switch for the different allowed types of shadow */
        switch (shadowType) {
        case LIGHT:
            shape.setEffect(new DropShadow(10, 2, 2, Color.BLACK));
            break;
        case NORMAL:
            shape.setEffect(new DropShadow(20, 3, 3, Color.BLACK));
            break;
        case HEAVY:
            shape.setEffect(new DropShadow(30, 5, 5, Color.BLACK));
            break;
        case NONE:
            break;
        default:
            /* Print to catch any types added to the ENUM */
            System.err.println("Shadow type not recognised.");
            break;
        }

    }

    /**
     * Method to draw an oval.
     * 
     * @param xStartPos
     *            x coordinate of the top left corner of the bounding box around
     *            the oval
     * @param yStartPos
     *            y coordinate of the top left corner of the bounding box around
     *            the oval
     * @param xEndPos
     *            x coordinate of the bottom right corner of the bounding box
     *            around the oval
     * @param yEndPos
     *            y coordinate of the bottom right corner of the bounding box
     *            around the oval
     * @param ovalColor
     *            color of the oval
     * @param solid
     *            boolean value of if the shape is an outline or a solid shape
     * @param outlineColor
     *            color of the shape outline
     * @param outlineThickness
     *            thickness of the shape outline
     * @param shadowType
     *            enum for type of shadow. Options: Shadow.NONE, Shadow.LIGHT,
     *            Shadow.NORMAL and Shadow.HEAVY.
     * @param rotation
     *            amount of rotation about the center in degrees
     * @param shadingType
     *            enum for type of shading. Options: Shading.NONE,
     *            Shading.CYCLIC, Shading.HORIZONTAL, Shading.VERTICAL.
     * @param shadingColors
     *            varargs of type Color that contains all of the shapes shaded
     *            colors.
     */
    public void drawOval(float xStartPos, float yStartPos, float xEndPos, float yEndPos, Color ovalColor,
            boolean solid, Color outlineColor, double outlineThickness, Shadow shadowType, float rotation,
            Shading shadingType, Color... shadingColors) {
        double xCenter = (xEndPos + xStartPos) / 2;
        double yCenter = (yEndPos + yStartPos) / 2;
        double xRad = (xEndPos - xStartPos) / 2;
        double yRad = (yEndPos - yStartPos) / 2;

        Ellipse oval = new Ellipse(xCenter, yCenter, xRad, yRad);
        oval.setRotate(rotation);

        colorShape(oval, solid, ovalColor, outlineColor, outlineThickness, shadowType, shadingType, shadingColors);

        group.getChildren().add(oval);
    }

    /**
     * Method to draw a Circle.
     * 
     * @param xStartPos
     *            x coordinate of the top left corner of the bounding box around
     *            the circle
     * @param yStartPos
     *            y coordinate of the top left corner of the bounding box around
     *            the circle
     * @param radius
     *            the radius of the circle
     * @param circleColor
     *            color of the circle
     * @param solid
     *            boolean value of if the shape is an outline or a solid shape
     * @param outlineColor
     *            color of the shape outline
     * @param outlineThickness
     *            thickness of the shape outline
     * @param shadowType
     *            enum for type of shadow. Options: Shadow.NONE, Shadow.LIGHT,
     *            Shadow.NORMAL and Shadow.HEAVY.
     * @param shadingType
     *            enum for type of shading. Options: Shading.NONE,
     *            Shading.CYCLIC, Shading.HORIZONTAL, Shading.VERTICAL.
     * @param shadingColors
     *            varargs of type Color that contains all of the shapes shaded
     *            colors.
     */
    public void drawCircle(float xStartPos, float yStartPos, float radius, Color circleColor, boolean solid,
            Color outlineColor, double outlineThickness, Shadow shadowType, Shading shadingType, Color... shadingColors) {
        double xCenter = (xStartPos + radius);
        double yCenter = (yStartPos + radius);

        Circle circle = new Circle(xCenter, yCenter, radius);

        colorShape(circle, solid, circleColor, outlineColor, outlineThickness, shadowType, shadingType, shadingColors);

        group.getChildren().add(circle);
    }

    /**
     * Method to draw a rectangle.
     * 
     * @param xStartPos
     *            x coordinate of the top left corner of the rectangle
     * @param yStartPos
     *            y coordinate of the top left corner of the rectangle
     * @param xEndPos
     *            x coordinate of the bottom right corner of the rectangle
     * @param yEndPos
     *            y coordinate of the bottom right corner of the rectangle
     * @param arcWidth
     *            //TODO
     * @param arcHeight
     *            //TODO
     * @param rectangleColor
     *            color of the rectangle
     * @param solid
     *            boolean value of if the shape is an outline or a solid shape
     * @param outlineColor
     *            color of the shape outline
     * @param outlineThickness
     *            thickness of the shape outline
     * @param shadowType
     *            enum for type of shadow. Options: Shadow.NONE, Shadow.LIGHT,
     *            Shadow.NORMAL and Shadow.HEAVY.
     * @param rotation
     *            amount of rotation about the center in degrees
     * @param shadingType
     *            enum for type of shading. Options: Shading.NONE,
     *            Shading.CYCLIC, Shading.HORIZONTAL, Shading.VERTICAL.
     * @param shadingColors
     *            varargs of type Color that contains all of the shapes shaded
     *            colors.
     */
    public void drawRectangle(float xStartPos, float yStartPos, float xEndPos, float yEndPos, float arcWidth,
            float arcHeight, Color rectangleColor, boolean solid, Color outlineColor, double outlineThickness,
            Shadow shadowType, float rotation, Shading shadingType, Color... shadingColors) {
        /*
         * TODO rounded corners (look up arkheight and arkwidth javafx)
         */
        Rectangle rectangle = new Rectangle(xStartPos, yStartPos, xEndPos - xStartPos, yEndPos - yStartPos);
        rectangle.setRotate(rotation);

        colorShape(rectangle, solid, rectangleColor, outlineColor, outlineThickness, shadowType, shadingType,
                shadingColors);

        group.getChildren().add(rectangle);

    }

    /**
     * Method to draw a square.
     * 
     * @param xStartPos
     *            x coordinate of the top left corner of the square
     * @param yStartPos
     *            y coordinate of the top left corner of the square
     * @param length
     *            the length of each side of the square
     * @param squareColor
     *            color of the square
     * @param solid
     *            boolean value of if the shape is an outline or a solid shape
     * @param outlineColor
     *            color of the shape outline
     * @param outlineThickness
     *            thickness of the shape outline
     * @param shadowType
     *            enum for type of shadow. Options: Shadow.NONE, Shadow.LIGHT,
     *            Shadow.NORMAL and Shadow.HEAVY.
     * @param rotation
     *            amount of rotation about the center in degrees
     * @param shadingType
     *            enum for type of shading. Options: Shading.NONE,
     *            Shading.CYCLIC, Shading.HORIZONTAL, Shading.VERTICAL.
     * @param shadingColors
     *            varargs of type Color that contains all of the shapes shaded
     *            colors.
     */
    public void drawSquare(float xStartPos, float yStartPos, float length, Color squareColor, boolean solid,
            Color outlineColor, double outlineThickness, Shadow shadowType, float rotation, Shading shadingType,
            Color... shadingColors) {
        /*
         * TODO rounded corners (look up arkheight and arkwidth javafx)
         */
        drawRectangle(xStartPos, yStartPos, xStartPos + length, yStartPos + length, 0, 0, squareColor, solid,
                outlineColor, outlineThickness, shadowType, rotation, shadingType, shadingColors);
    }

    /**
     * Method to draw a line.
     * 
     * @param xStartPos
     *            x coordinate of the top left corner of the bounding box around
     *            the oval
     * @param yStartPos
     *            y coordinate of the top left corner of the bounding box around
     *            the oval
     * @param xEndPos
     *            x coordinate of the bottom right corner of the bounding box
     *            around the oval
     * @param yEndPos
     *            y coordinate of the bottom right corner of the bounding box
     *            around the oval
     * @param lineColor
     *            color of the line
     * @param thickness
     *            thickness of the line
     * @param shadingType
     *            enum for type of shading. Options: Shading.NONE,
     *            Shading.CYCLIC, Shading.HORIZONTAL, Shading.VERTICAL. If a
     *            shading type is selected, shading will default to
     *            Shading.HORIZONTAL.
     * @param shadingColors
     *            varargs of type Color that contains all of the shapes shaded
     *            colors.
     */
    public void drawLine(float xStartPos, float yStartPos, float xEndPos, float yEndPos, Color lineColor,
            double thickness, Shading shadingType, Color... shadingColors) {
        Line line = new Line(xStartPos, yStartPos, xEndPos, yEndPos);

        if (shadingType != Shading.NONE)
            shadingType = Shading.HORIZONTAL;

        colorShape(line, true, lineColor, lineColor, thickness, Shadow.NONE, shadingType, shadingColors);

        group.getChildren().add(line);
    }

    /**
     * Method to draw an oval.
     * 
     * @param xStartPos
     *            x coordinate of the start of the arrow
     * @param yStartPos
     *            y coordinate of the start of the arrow
     * @param xEndPos
     *            x coordinate of the end of the arrow
     * @param yEndPos
     *            y coordinate of the end of the arrow
     * @param arrowColor
     *            color of the arrow
     * @param shadingType
     *            enum for type of shading. Options: Shading.NONE,
     *            Shading.CYCLIC, Shading.HORIZONTAL, Shading.VERTICAL.
     * @param shadingColors
     *            varargs of type Color that contains all of the shapes shaded
     *            colors.
     */
    public void drawArrow(float xStartPos, float yStartPos, float xEndPos, float yEndPos, Color arrowColor,
            Shading shadingType, Color... shadingColors) {

        int arrowHeadSide = 15;
        double arrowHeadHeight = (arrowHeadSide * (Math.sqrt(3) / (2.0)));

        // to work out the angle of rotation
        double dx = Math.abs(xEndPos - xStartPos);
        double dy = Math.abs(yEndPos - yStartPos);
        int length = (int) Math.sqrt((dx * dx + dy * dy));

        double x1 = xEndPos;
        double y1 = yEndPos - (arrowHeadHeight / 2);
        double x2 = xEndPos + (arrowHeadSide / 2);
        double y2 = yEndPos + (arrowHeadHeight / 2);
        double x3 = xEndPos - (arrowHeadSide / 2);
        double y3 = yEndPos + (arrowHeadHeight / 2);
        ;

        // new arrow
        Line arrow = new Line(xStartPos, yStartPos, xEndPos, yEndPos);
        // arrow.setFill(arrowColor);
        // arrow.setStroke(arrowColor);

        Polygon arrowHead = new Polygon();
        // arrowHead.setFill(arrowColor);
        arrowHead.getPoints().addAll(new Double[] { x1, y1, x2, y2, x3, y3 });

        double rotation = 0.0;
        if ((xEndPos > xStartPos) && (yEndPos > yStartPos)) { // bottom right
            rotation = (90 + Math.toDegrees(Math.asin(dy / length)));
        } else if ((xEndPos == xStartPos) && (yEndPos > yStartPos)) {
            rotation = 180;
        } else if ((xEndPos < xStartPos) && (yEndPos == yStartPos)) {
            rotation = 270;
        } else if ((xEndPos > xStartPos) && (yEndPos == yStartPos)) {
            rotation = 90;
        } else if ((xEndPos < xStartPos) && (yEndPos > yStartPos)) { // bottom
                                                                        // left
            rotation = (180 + Math.toDegrees(Math.asin(dx / length)));
        } else if ((xEndPos < xStartPos) && (yEndPos < yStartPos)) { // top
                                                                        // right
            rotation = (270 + Math.toDegrees(Math.asin(dy / length)));
        } else if ((xEndPos > xStartPos) && (yEndPos < yStartPos)) { // top left
            rotation = (Math.toDegrees(Math.asin(dx / length)));
        }

        arrowHead.setRotate(rotation);

        Stop[] stops = null;
        /* Code to generate stops for if a shading type is to be used. */
        if (shadingType != Shading.NONE) {
            /*
             * Builds an array of stops evenly spaced through the shape
             * consisting of the varargs of shadingColors. The last color in the
             * varargs is the outer shading color.
             */
            stops = new Stop[shadingColors.length + 1];
            /* Add the shape color as the central color */
            stops[0] = new Stop(0, arrowColor);
            /* Loop through the varargs adding the colors to the stop array */
            for (int i = 1; i <= shadingColors.length; i++)
                stops[i] = (new Stop((float) i / shadingColors.length, shadingColors[i - 1]));
            arrow.setStroke(new LinearGradient(0, 0.5, 1, 0.5, true, CycleMethod.NO_CYCLE, stops));
            arrowHead.setFill(new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE, stops));
        } else {
            arrow.setStroke(arrowColor);
            arrowHead.setFill(arrowColor);
        }

        group.getChildren().add(arrow);
        group.getChildren().add(arrowHead);
    }

    public void drawEquiTriangle() {
        /*
         * TODO Write a method that draws an equilateral triangle depending on
         * params passed.
         */

    }

    /**
     * Method to draw a triangle.
     * 
     * @param x1
     *            x coordinate of the first point on the triangle
     * @param y1
     *            y coordinate of the first point on the triangle
     * @param x2
     *            x coordinate of the second point on the triangle
     * @param y2
     *            y coordinate of the second point on the triangle
     * @param x3
     *            x coordinate of the third point on the triangle
     * @param y3
     *            y coordinate of the third point on the triangle
     * @param triangleColor
     *            color of the triangle
     * @param solid
     *            boolean value of if the shape is an outline or a solid shape
     * @param outlineColor
     *            color of the shape outline
     * @param outlineThickness
     *            thickness of the shape outline
     * @param shadowType
     *            enum for type of shadow. Options: Shadow.NONE, Shadow.LIGHT,
     *            Shadow.NORMAL and Shadow.HEAVY.
     * @param rotation
     *            amount of rotation about the center in degrees
     * @param shadingType
     *            enum for type of shading. Options: Shading.NONE,
     *            Shading.CYCLIC, Shading.HORIZONTAL, Shading.VERTICAL.
     * @param shadingColors
     *            varargs of type Color that contains all of the shapes shaded
     *            colors.
     */
    public void drawTriangle(double x1, double y1, double x2, double y2, double x3, double y3, Color triangleColor,
            boolean solid, Color outlineColor, double outlineThickness, Shadow shadowType, float rotation,
            Shading shadingType, Color... shadingColors) {
        /*
         * TODO Write a method that draws a triangle depending on params passed.
         * Takes 3 sets of coordinates so any triangle can be made.
         */
        Polygon triangle = new Polygon();
        colorShape(triangle, solid, triangleColor, outlineColor, outlineThickness, shadowType, shadingType,
                shadingColors);
        triangle.getPoints().addAll(new Double[] { x1, y1, x2, y2, x3, y3 });
        triangle.setRotate(rotation);

        group.getChildren().add(triangle);
    }

    /**
     * Method to draw a n sided regular polygon.
     * 
     * @param xPos
     *            the x coordinate of the polygon //TODO
     * @param yPos
     *            the y coordinate of the polygon //TODO
     * @param width
     *            the width of the shape
     * @param height
     *            the height of the shape
     * @param numberOfSides
     *            the number of sides of the shape
     * @param regPolygonColor
     *            color of the regular polygon
     * @param solid
     *            boolean value of if the shape is an outline or a solid shape
     * @param outlineColor
     *            color of the shape outline
     * @param outlineThickness
     *            thickness of the shape outline
     * @param shadowType
     *            enum for type of shadow. Options: Shadow.NONE, Shadow.LIGHT,
     *            Shadow.NORMAL and Shadow.HEAVY.
     * @param rotation
     *            amount of rotation about the center in degrees
     * @param shadingType
     *            enum for type of shading. Options: Shading.NONE,
     *            Shading.CYCLIC, Shading.HORIZONTAL, Shading.VERTICAL.
     * @param shadingColors
     *            varargs of type Color that contains all of the shapes shaded
     *            colors.
     */
    public void drawRegularPolygon(double xPos, double yPos, float width, float height, int numberOfSides,
            Color regPolColor, boolean solid, Color outlineColor, double outlineThickness, Shadow shadowType,
            float rotation, Shading shadingType, Color... shadingColors) {
        Polygon regPolygon = new Polygon();
        colorShape(regPolygon, solid, regPolColor, outlineColor, outlineThickness, shadowType, shadingType,
                shadingColors);
        double radius = (double) (Math.min(0.5 * width, height * 0.5));
        double z;
        for (int i = 0; i < numberOfSides; i++) {
            z = ((i * 2 * Math.PI) / numberOfSides);
            regPolygon.getPoints().add(((double) Math.round((xPos + (radius) * Math.sin(z)) + (radius))));
            regPolygon.getPoints().add(((double) Math.round((yPos + (-radius) * Math.cos(z)) + (radius))));

        }
        group.getChildren().add(regPolygon);
    }

    /**
     * Method to draw a polygon of any number of sides.
     * 
     * @param points
     *            an array of all of the coordinates of the polygon. [x1, y1 ...
     *            xN, yN]
     * @param x
     *            the x position of the polygon. All x coordinates are relative
     *            to this position
     * @param y
     *            the y position of the polygon. All y coordinates are relative
     *            to this position
     * @param polygonColor
     *            color of the polygon
     * @param solid
     *            boolean value of if the shape is an outline or a solid shape
     * @param outlineColor
     *            color of the shape outline
     * @param outlineThickness
     *            thickness of the shape outline
     * @param shadowType
     *            enum for type of shadow. Options: Shadow.NONE, Shadow.LIGHT,
     *            Shadow.NORMAL and Shadow.HEAVY.
     * @param rotation
     *            amount of rotation about the center in degrees
     * @param shadingType
     *            enum for type of shading. Options: Shading.NONE,
     *            Shading.CYCLIC, Shading.HORIZONTAL, Shading.VERTICAL.
     * @param shadingColors
     *            varargs of type Color that contains all of the shapes shaded
     *            colors.
     */
    public void drawPolygon(Double points[], float x, float y, Color polygonColor, boolean solid, Color outlineColor,
            double outlineThickness, Shadow shadowType, float rotation, Shading shadingType, Color... shadingColors) {
        Polygon polygon = new Polygon();
        colorShape(polygon, solid, polygonColor, outlineColor, outlineThickness, shadowType, shadingType, shadingColors);
        for (int i = 0; i < points.length; i++) {
            points[i] += x;
            i++;
            points[i] += y;
        }
        polygon.getPoints().addAll(points);
        group.getChildren().add(polygon);
    }

    /**
     * Method to draw a star of n points
     * 
     * @param midX
     *            the middle x position of the star
     * @param midY
     *            the middle y position of the star
     * @param numberOfPoints
     *            the number of points of the star
     * @param starColor
     *            color of the star
     * @param solid
     *            boolean value of if the shape is an outline or a solid shape
     * @param outlineColor
     *            color of the shape outline
     * @param outlineThickness
     *            thickness of the shape outline
     * @param shadowType
     *            enum for type of shadow. Options: Shadow.NONE, Shadow.LIGHT,
     *            Shadow.NORMAL and Shadow.HEAVY.
     * @param rotation
     *            amount of rotation about the center in degrees
     * @param shadingType
     *            enum for type of shading. Options: Shading.NONE,
     *            Shading.CYCLIC, Shading.HORIZONTAL, Shading.VERTICAL.
     * @param shadingColors
     *            varargs of type Color that contains all of the shapes shaded
     *            colors.
     */
    public void drawStar(double midX, double midY, int numberOfPoints, float size, Color starColor, boolean solid,
            Color outlineColor, double outlineThickness, Shadow shadowType, float rotation, Shading shadingType,
            Color... shadingColors) {
        int a = 0;
        double z = 0;

        Polygon star = new Polygon();
        colorShape(star, solid, starColor, outlineColor, outlineThickness, shadowType, shadingType, shadingColors);

        while (a < ((2 * numberOfPoints) + 1)) {
            z = ((a * Math.PI) / numberOfPoints);
            if ((a % 2 == 0)) {
                star.getPoints().add((double) Math.round(((size / 2) * Math.sin(z)) + midX + (size / 2)));
                star.getPoints().add((double) Math.round(((-size / 2) * Math.cos(z)) + midY + (size / 2)));
            } else {
                star.getPoints().add(
                        (double) Math.round(((size / (2 * Math.pow(numberOfPoints, 0.5))) * Math.sin(z)) + midX
                                + (size / 2)));
                star.getPoints().add(
                        (double) Math.round(((-size / (2 * Math.pow(numberOfPoints, 0.5))) * Math.cos(z)) + midY
                                + (size / 2)));
            }
            a++;
        }

        group.getChildren().add(star);
    }

    /**
     * Method to draw a chord.
     * 
     * @param centerX
     *            the center x position of the chord
     * @param centerY
     *            the center y position of the chord
     * @param width
     *            the width of the chord
     * @param height
     *            the height of the chord
     * @param arcAngle
     *            the arcAngle of the chord
     * @param length
     *            the length of the chord
     * @param chordColor
     *            color of the chord
     * @param solid
     *            boolean value of if the shape is an outline or a solid shape
     * @param outlineColor
     *            color of the shape outline
     * @param outlineThickness
     *            thickness of the shape outline
     * @param shadowType
     *            enum for type of shadow. Options: Shadow.NONE, Shadow.LIGHT,
     *            Shadow.NORMAL and Shadow.HEAVY.
     * @param rotation
     *            amount of rotation about the center in degrees
     * @param shadingType
     *            enum for type of shading. Options: Shading.NONE,
     *            Shading.CYCLIC, Shading.HORIZONTAL, Shading.VERTICAL.
     * @param shadingColors
     *            varargs of type Color that contains all of the shapes shaded
     *            colors.
     */
    public void drawChord(float centerX, float centerY, float width, float height, float arcAngle, float length,
            Color chordColor, boolean solid, Color outlineColor, double outlineThickness, Shadow shadowType,
            float rotation, Shading shadingType, Color... shadingColors) {

        Arc chord = new Arc(centerX, centerY, width, height, arcAngle, length);
        chord.setType(ArcType.CHORD);
        colorShape(chord, solid, chordColor, outlineColor, outlineThickness, shadowType, shadingType, shadingColors);

        group.getChildren().add(chord);

    }

    /**
     * Method to draw a arc.
     * 
     * @param centerX
     *            the center x position of the arc
     * @param centerY
     *            the center y position of the arc
     * @param width
     *            the width of the arc
     * @param height
     *            the height of the arc
     * @param arcAngle
     *            the arcAngle of the arc
     * @param length
     *            the length of the arc
     * @param arcColor
     *            color of the arc
     * @param solid
     *            boolean value of if the shape is an outline or a solid shape
     * @param outlineColor
     *            color of the shape outline
     * @param outlineThickness
     *            thickness of the shape outline
     * @param shadowType
     *            enum for type of shadow. Options: Shadow.NONE, Shadow.LIGHT,
     *            Shadow.NORMAL and Shadow.HEAVY.
     * @param rotation
     *            amount of rotation about the center in degrees
     * @param shadingType
     *            enum for type of shading. Options: Shading.NONE,
     *            Shading.CYCLIC, Shading.HORIZONTAL, Shading.VERTICAL.
     * @param shadingColors
     *            varargs of type Color that contains all of the shapes shaded
     *            colors.
     */
    public void drawArc(float centerX, float centerY, float width, float height, float arcAngle, float length,
            Color arcColor, boolean solid, Color outlineColor, double outlineThickness, Shadow shadowType,
            float rotation, Shading shadingType, Color... shadingColors) {

        Arc arc = new Arc(centerX, centerY, width, height, arcAngle, length);
        arc.setType(ArcType.ROUND);
        colorShape(arc, solid, arcColor, outlineColor, outlineThickness, shadowType, shadingType, shadingColors);

        group.getChildren().add(arc);

    }

    public void drawPieChart(float x, float y, double r, float n, Double digits[], String names[], Color colors) {
        /*
         * TODO Write a method that draws a pie chart, varying upon input of 2
         * arrays, one of name of input and one of numbers in input. Toggle key,
         * and maybe array of colors for sections?
         */

    }

    public void drawBarChart() {
        /*
         * TODO Write a method that draws a bar chart, varying upon input of 2
         * arrays, one of name of input and one of numbers in input. Toggle key,
         * and maybe array of colors for sections? Color schemes?
         */
    }

}
