/** (c) Copyright by WaveMedia. */
package wavemedia.graphic;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.*;

/**
 * Class for handling shapes on a javafx group.
 * 
 * @author tjd511, jod512, lmo1024
 * @version 1.0 11/03/2015
 */
public class Graphic {

	/*
	 * Constants for where in the string "#00112233" the A, R, G and B portions
	 * of the color string are.
	 */
	private final int alphaStartChar = 1;
	private final int rStartChar = 3;
	private final int gStartChar = 5;
	private final int bStartChar = 7;
	private final int alphaEndChar = 3;
	private final int rEndChar = 5;
	private final int gEndChar = 7;
	private final int bEndChar = 9;

	/* The group on which the shapes are to be drawn onto */
	private Group group;

	/*
	 * Contain the shapes that have been drawn. Using two as arrow uses two
	 * shapes, one for the line and one for the arrowhead.
	 */
	private Shape shape;
	private Shape shape2;

	/**
	 * Constructor for the graphicsHandler.
	 * 
	 * @param group
	 *            The group that all of the graphics are to be drawn to.
	 */
	public Graphic(Group group) {
		this.group = group;
	}

	/**
	 * Method for drawing a graphic on the screen.
	 * 
	 * @param graphic
	 *            a graphics object containing all the information about the
	 *            graphic to be drawn. Must be formed using the GraphicBuilder.
	 * @see {@link graphicsHandler.Graphic}
	 */
	public void drawShape(GraphicHandlerObject graphic) {
		GraphicType graphicType = graphic.getGraphic();
		float xStartPos = graphic.getXStartPos();
		float yStartPos = graphic.getYStartPos();
		String color = graphic.getColor();
		float xEndPos = graphic.getXEndPos();
		float yEndPos = graphic.getYEndPos();
		boolean solid = graphic.isSolid();
		String outlineColor = graphic.getOutlineColor();
		float outlineThickness = graphic.getOutlineThickness();
		float rotation = graphic.getRotation();
		float radius = graphic.getRadius();
		float arcWidth = graphic.getArcWidth();
		float arcHeight = graphic.getArcHeight();
		float size = graphic.getSize();
		float width = graphic.getWidth();
		float height = graphic.getHeight();
		int numberOfSides = graphic.getNumberOfSides();
		int numberOfPoints = graphic.getNumberOfPoints();
		float x1 = graphic.getX1();
		float x2 = graphic.getX2();
		float x3 = graphic.getX3();
		float y1 = graphic.getY1();
		float y2 = graphic.getY2();
		float y3 = graphic.getY3();
		ArrayList<Float> xCoordinates = graphic.getXCoordinates();
		ArrayList<Float> yCoordinates = graphic.getYCoordinates();
		float arcAngle = graphic.getArcAngle();
		float length = graphic.getLength();
		Shadow shadowType = graphic.getShadow();
		Shading shadingType = graphic.getShadingType();
		ArrayList<Stop> shadingStops = graphic.getStops();

		switch (graphicType) {
		case ARC:
			drawArc(xStartPos, yStartPos, width, height, arcAngle, length, color, solid, outlineColor,
					outlineThickness, shadowType, rotation, shadingType, shadingStops);
			break;
		case ARROW:
			drawArrow(xStartPos, yStartPos, xEndPos, yEndPos, color, shadingType, shadingStops);
			break;
		case CHORD:
			drawChord(xStartPos, yStartPos, width, height, arcAngle, length, color, solid, outlineColor,
					outlineThickness, shadowType, rotation, shadingType, shadingStops);
			break;
		case CIRCLE:
			drawCircle(xStartPos, yStartPos, radius, color, solid, outlineColor, outlineThickness, shadowType,
					shadingType, shadingStops);
			break;
		case EQUITRIANGLE:
			drawEquiTriangle(xStartPos, yStartPos, length, color, solid, outlineColor, outlineThickness, shadowType,
					rotation, shadingType, shadingStops);
			break;
		case LINE:
			drawLine(xStartPos, yStartPos, xEndPos, yEndPos, color, width, shadingType, shadingStops);
			break;
		case OVAL:
			drawOval(xStartPos, yStartPos, xEndPos, yEndPos, color, solid, outlineColor, outlineThickness, shadowType,
					rotation, shadingType, shadingStops);
			break;
		case POLYGON:
			Double[] combinedCoordinates;
			combinedCoordinates = new Double[xCoordinates.size() * 2];
			for (int i = 0; i < xCoordinates.size(); i++) {
				combinedCoordinates[i * 2] = Double.valueOf(xCoordinates.get(i));
				combinedCoordinates[i * 2 + 1] = Double.valueOf(yCoordinates.get(i));
			}

			drawPolygon(combinedCoordinates, xStartPos, yStartPos, color, solid, outlineColor, outlineThickness,
					shadowType, rotation, shadingType, shadingStops);
			break;
		case RECTANGLE:
			drawRectangle(xStartPos, yStartPos, xEndPos, yEndPos, arcWidth, arcHeight, color, solid, outlineColor,
					outlineThickness, shadowType, rotation, shadingType, shadingStops);
			break;
		case REGULARPOLYGON:
			drawRegularPolygon(xStartPos, yStartPos, width, height, numberOfSides, color, solid, outlineColor,
					outlineThickness, shadowType, rotation, shadingType, shadingStops);
			break;
		case SQUARE:
			drawSquare(xStartPos, yStartPos, length, color, solid, outlineColor, outlineThickness, shadowType,
					rotation, shadingType, shadingStops);
			break;
		case STAR:
			drawStar(xStartPos, yStartPos, numberOfPoints, size, color, solid, outlineColor, outlineThickness,
					shadowType, rotation, shadingType, shadingStops);
			break;
		case TRIANGLE:
			drawTriangle(x1, y1, x2, y2, x3, y3, color, solid, outlineColor, outlineThickness, shadowType, rotation,
					shadingType, shadingStops);
			break;
		default:
			break;

		}

	}

	/**
	 * Method to set the visibility of the shape.
	 * 
	 * @param visible
	 *            boolean containing if the shape should be visible or not
	 */
	public void setVisible(boolean visible) {
		if (shape != null) {
			shape.setVisible(visible);
		}

		if (shape2 != null) {
			shape2.setVisible(visible);
		}
	}

	/** Method to send the shape to the back of the group. */
	public void toBack() {
		if (shape2 != null) {
			shape2.toBack();
		}

		if (shape != null) {
			shape.toBack();
		}
	}

	/** Method to send the shape to the back of the group. */
	public void toFront() {
		if (shape != null) {
			shape.toFront();
		}

		if (shape2 != null) {
			shape2.toFront();
		}
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
	 * @param shadingStops
	 *            ArrayList of stops that contains the information for the
	 *            shading colors.
	 */
	private void colorShape(Shape shape, boolean solid, String shapeColor, String outlineColor, float outlineThickness,
			Shadow shadowType, Shading shadingType, ArrayList<Stop> shadingStops) {
		/* Set the outline parameters */
		shape.setStroke(convertStringToColor(outlineColor));
		shape.setStrokeWidth(outlineThickness);

		if (solid) {
			/*
			 * Switch to set different types of shading using the shadingStops
			 * created above
			 */
			switch (shadingType) {
			case CYCLIC:
				shape.setFill(new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE, shadingStops));
				break;
			case HORIZONTAL:
				/* If statement to handle shaded lines */
				if (shape.getClass().equals(new Line().getClass())) {
					shape.setStroke(new LinearGradient(0, 0.5, 1, 0.5, true, CycleMethod.NO_CYCLE, shadingStops));
				} else {
					shape.setFill(new LinearGradient(0, 0.5, 1, 0.5, true, CycleMethod.NO_CYCLE, shadingStops));
				}
				break;
			case VERTICAL:
				shape.setFill(new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE, shadingStops));
				break;
			case NONE:
				/* Falls through */
			default:
				shape.setFill(convertStringToColor(shapeColor));
				break;
			}
		} else {
			shape.setFill(Color.TRANSPARENT);
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
	 * @param shadingStops
	 *            ArrayList of stops that contains the information for the
	 *            shaded colors.
	 */
	private void drawOval(float xStartPos, float yStartPos, float xEndPos, float yEndPos, String ovalColor,
			boolean solid, String outlineColor, float outlineThickness, Shadow shadowType, float rotation,
			Shading shadingType, ArrayList<Stop> shadingStops) {
		float xCenter = (xEndPos + xStartPos) / 2;
		float yCenter = (yEndPos + yStartPos) / 2;
		float xRad = (xEndPos - xStartPos) / 2;
		float yRad = (yEndPos - yStartPos) / 2;

		Ellipse oval = new Ellipse(xCenter, yCenter, xRad, yRad);
		oval.setRotate(rotation);

		colorShape(oval, solid, ovalColor, outlineColor, outlineThickness, shadowType, shadingType, shadingStops);

		shape = oval;

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
	 * @param shadingStops
	 *            ArrayList of stops that contains the information for the
	 *            shaded colors.
	 */
	private void drawCircle(float xStartPos, float yStartPos, float radius, String circleColor, boolean solid,
			String outlineColor, float outlineThickness, Shadow shadowType, Shading shadingType,
			ArrayList<Stop> shadingStops) {
		float xCenter = (xStartPos + radius);
		float yCenter = (yStartPos + radius);

		Circle circle = new Circle(xCenter, yCenter, radius);

		colorShape(circle, solid, circleColor, outlineColor, outlineThickness, shadowType, shadingType, shadingStops);

		shape = circle;

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
	 *            the vertical diameter of the arc at the four corners of the
	 *            rectangle
	 * @param arcHeight
	 *            the horizontal diameter of the arc at the four corners of the
	 *            rectangle
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
	 * @param shadingStops
	 *            ArrayList of stops that contains the information for the
	 *            shaded colors.
	 */
	private void drawRectangle(float xStartPos, float yStartPos, float xEndPos, float yEndPos, float arcWidth,
			float arcHeight, String rectangleColor, boolean solid, String outlineColor, float outlineThickness,
			Shadow shadowType, float rotation, Shading shadingType, ArrayList<Stop> shadingStops) {
		Rectangle rectangle = new Rectangle(xStartPos, yStartPos, xEndPos - xStartPos, yEndPos - yStartPos);
		rectangle.setRotate(rotation);
		rectangle.setArcWidth(arcWidth);
		rectangle.setArcHeight(arcHeight);

		colorShape(rectangle, solid, rectangleColor, outlineColor, outlineThickness, shadowType, shadingType,
				shadingStops);

		shape = rectangle;

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
	 * @param shadingStops
	 *            ArrayList of stops that contains the information for the
	 *            shaded colors.
	 */
	private void drawSquare(float xStartPos, float yStartPos, float length, String squareColor, boolean solid,
			String outlineColor, float outlineThickness, Shadow shadowType, float rotation, Shading shadingType,
			ArrayList<Stop> shadingStops) {
		drawRectangle(xStartPos, yStartPos, xStartPos + length, yStartPos + length, 0, 0, squareColor, solid,
				outlineColor, outlineThickness, shadowType, rotation, shadingType, shadingStops);
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
	 * @param shadingStops
	 *            ArrayList of stops that contains the information for the
	 *            shaded colors.
	 */
	private void drawLine(float xStartPos, float yStartPos, float xEndPos, float yEndPos, String lineColor,
			float thickness, Shading shadingType, ArrayList<Stop> shadingStops) {
		Line line = new Line(xStartPos, yStartPos, xEndPos, yEndPos);

		if (shadingType != Shading.NONE)
			shadingType = Shading.HORIZONTAL;

		colorShape(line, true, lineColor, lineColor, thickness, Shadow.NONE, shadingType, shadingStops);

		shape = line;

		group.getChildren().add(line);
	}

	/**
	 * Method to draw an arrow.
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
	 * @param shadingStops
	 *            ArrayList of stops that contains the information for the
	 *            shaded colors.
	 */
	private void drawArrow(float xStartPos, float yStartPos, float xEndPos, float yEndPos, String arrowColor,
			Shading shadingType, ArrayList<Stop> shadingStops) {

		int arrowHeadSide = 15;
		float arrowHeadHeight = (float) (arrowHeadSide * (Math.sqrt(3) / (2.0)));

		/* Calculate the angle of rotation for the arrow */
		float dx = Math.abs(xEndPos - xStartPos);
		float dy = Math.abs(yEndPos - yStartPos);
		int length = (int) Math.sqrt((dx * dx + dy * dy));

		/* Calculate the x and y positions of the corners of the arrowhead */
		double x1 = xEndPos;
		double y1 = yEndPos - (arrowHeadHeight / 2);
		double x2 = xEndPos + (arrowHeadSide / 2);
		double y2 = yEndPos + (arrowHeadHeight / 2);
		double x3 = xEndPos - (arrowHeadSide / 2);
		double y3 = yEndPos + (arrowHeadHeight / 2);

		/* Declare the new line for the line of the arrow */
		Line arrow = new Line(xStartPos, yStartPos, xEndPos, yEndPos);

		/* Define a new polygon for the arrowhead */
		Polygon arrowHead = new Polygon();

		arrowHead.getPoints().addAll(new Double[] { x1, y1, x2, y2, x3, y3 });

		float rotation = 0;
		if ((xEndPos > xStartPos) && (yEndPos > yStartPos)) { // bottom right
			rotation = (float) (90 + Math.toDegrees(Math.asin(dy / length)));
		} else if ((xEndPos == xStartPos) && (yEndPos > yStartPos)) {
			rotation = 180;
		} else if ((xEndPos < xStartPos) && (yEndPos == yStartPos)) {
			rotation = 270;
		} else if ((xEndPos > xStartPos) && (yEndPos == yStartPos)) {
			rotation = 90;
		} else if ((xEndPos < xStartPos) && (yEndPos > yStartPos)) { // bottom
																		// left
			rotation = (float) (180 + Math.toDegrees(Math.asin(dx / length)));
		} else if ((xEndPos < xStartPos) && (yEndPos < yStartPos)) { // top
																		// right
			rotation = (float) (270 + Math.toDegrees(Math.asin(dy / length)));
		} else if ((xEndPos > xStartPos) && (yEndPos < yStartPos)) { // top left
			rotation = (float) (Math.toDegrees(Math.asin(dx / length)));
		}

		arrowHead.setRotate(rotation);

		/* Generate shadingStops for if a shading type is to be used. */
		if (shadingType != Shading.NONE) {
			arrow.setStroke(new LinearGradient(0, 0.5, 1, 0.5, true, CycleMethod.NO_CYCLE, shadingStops));
			arrowHead.setFill(new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE, shadingStops));
		} else {
			arrow.setStroke(convertStringToColor(arrowColor));
			arrowHead.setFill(convertStringToColor(arrowColor));
		}

		shape = arrow;
		shape2 = arrowHead;

		group.getChildren().add(arrow);
		group.getChildren().add(arrowHead);
	}

	/**
	 * Method to draw an equilateral triangle.
	 * 
	 * @param xStartPos
	 *            x coordinate of the top left corner of the boundary box around
	 *            the triangle
	 * @param yStartPos
	 *            y coordinate of the top left corner of the boundary box around
	 *            the triangle
	 * @param length
	 *            the side length of the triangle
	 * @param equiTriangleColor
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
	 * @param shadingStops
	 *            ArrayList of stops that contains the information for the
	 *            shaded colors.
	 */
	private void drawEquiTriangle(float xStartPos, float yStartPos, float length, String equiTriangleColor,
			boolean solid, String outlineColor, float outlineThickness, Shadow shadowType, float rotation,
			Shading shadingType, ArrayList<Stop> shadingStops) {
		/* Calculate new coordinates for all the corners */

		/* Bottom left */
		float x1 = xStartPos;
		float y1 = (float) (yStartPos + (length * (Math.sqrt(3) / (2.0))));

		/* Top */
		float x2 = xStartPos + (length / 2);
		float y2 = yStartPos;

		/* Bottom right */
		float x3 = xStartPos + length;
		float y3 = (float) (yStartPos + (length * (Math.sqrt(3) / (2.0))));

		drawTriangle(x1, y1, x2, y2, x3, y3, equiTriangleColor, solid, outlineColor, outlineThickness, shadowType,
				rotation, shadingType, shadingStops);
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
	 * @param shadingStops
	 *            ArrayList of stops that contains the information for the
	 *            shaded colors.
	 */
	private void drawTriangle(float x1, float y1, float x2, float y2, float x3, float y3, String triangleColor,
			boolean solid, String outlineColor, float outlineThickness, Shadow shadowType, float rotation,
			Shading shadingType, ArrayList<Stop> shadingStops) {
		Polygon triangle = new Polygon();
		colorShape(triangle, solid, triangleColor, outlineColor, outlineThickness, shadowType, shadingType,
				shadingStops);
		triangle.getPoints().addAll(
				new Double[] { (double) x1, (double) y1, (double) x2, (double) y2, (double) x3, (double) y3 });
		triangle.setRotate(rotation);

		shape = triangle;

		group.getChildren().add(triangle);
	}

	/**
	 * Method to draw a n sided regular polygon.
	 * 
	 * @param xStartPos
	 *            the x coordinate of the polygon
	 * @param yStartPos
	 *            the y coordinate of the polygon
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
	 * @param shadingStops
	 *            ArrayList of stops that contains the information for the
	 *            shaded colors.
	 */
	private void drawRegularPolygon(float xStartPos, float yStartPos, float width, float height, int numberOfSides,
			String regPolColor, boolean solid, String outlineColor, float outlineThickness, Shadow shadowType,
			float rotation, Shading shadingType, ArrayList<Stop> shadingStops) {
		float centerXPoint = xStartPos + width / 2;
		float centerYPoint = yStartPos + height / 2;

		Polygon regPolygon = new Polygon();
		colorShape(regPolygon, solid, regPolColor, outlineColor, outlineThickness, shadowType, shadingType,
				shadingStops);
		float radius = (float) (Math.min(0.5 * width, height * 0.5));
		double z;
		for (int i = 0; i < numberOfSides; i++) {
			z = ((i * 2 * Math.PI) / numberOfSides);
			regPolygon.getPoints().add((double) (Math.round((centerXPoint + (radius) * Math.sin(z)) + (radius))));
			regPolygon.getPoints().add((double) (Math.round((centerYPoint + (-radius) * Math.cos(z)) + (radius))));

		}
		regPolygon.setRotate(rotation);

		shape = regPolygon;

		group.getChildren().add(regPolygon);
	}

	/**
	 * Method to draw a polygon of any number of sides.
	 * 
	 * @param points
	 *            an array of all of the coordinates of the polygon. [x1, y1 ...
	 *            xN, yN]
	 * @param xStartPos
	 *            the x position of the polygon. All x coordinates are relative
	 *            to this position
	 * @param yStartPos
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
	 * @param shadingStops
	 *            ArrayList of stops that contains the information for the
	 *            shaded colors.
	 */
	private void drawPolygon(Double[] points, float xStartPos, float yStartPos, String polygonColor, boolean solid,
			String outlineColor, float outlineThickness, Shadow shadowType, float rotation, Shading shadingType,
			ArrayList<Stop> shadingStops) {
		Polygon polygon = new Polygon();
		colorShape(polygon, solid, polygonColor, outlineColor, outlineThickness, shadowType, shadingType, shadingStops);
		for (int i = 0; i < points.length; i++) {
			points[i] += xStartPos;
			i++;
			points[i] += yStartPos;
		}
		polygon.getPoints().addAll(points);

		shape = polygon;

		group.getChildren().add(polygon);
	}

	/**
	 * Method to draw a star of n points
	 * 
	 * @param xStartPos
	 *            the top left x position of the star
	 * @param yStartPos
	 *            the top left y position of the star
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
	 * @param shadingStops
	 *            ArrayList of stops that contains the information for the
	 *            shaded colors.
	 */
	private void drawStar(float xStartPos, float yStartPos, int numberOfPoints, float size, String starColor,
			boolean solid, String outlineColor, float outlineThickness, Shadow shadowType, float rotation,
			Shading shadingType, ArrayList<Stop> shadingStops) {
		int a = 0;
		double z = 0;
		Polygon star = new Polygon();
		colorShape(star, solid, starColor, outlineColor, outlineThickness, shadowType, shadingType, shadingStops);

		while (a < ((2 * numberOfPoints) + 1)) {
			z = ((a * Math.PI) / numberOfPoints);
			if ((a % 2 == 0)) {
				star.getPoints().add((double) Math.round(((size / 2) * Math.sin(z)) + xStartPos + (size / 2)));
				star.getPoints().add((double) Math.round(((-size / 2) * Math.cos(z)) + yStartPos + (size / 2)));
			} else {
				star.getPoints().add(
						(double) Math.round(((size / (2 * Math.pow(numberOfPoints, 0.5))) * Math.sin(z)) + xStartPos
								+ (size / 2)));
				star.getPoints().add(
						(double) Math.round(((-size / (2 * Math.pow(numberOfPoints, 0.5))) * Math.cos(z)) + yStartPos
								+ (size / 2)));
			}
			a++;
		}

		shape = star;

		group.getChildren().add(star);
	}

	/**
	 * Method to draw a chord.
	 * 
	 * @param xStartPos
	 *            the top left x position of the chord
	 * @param yStartPos
	 *            the top left y position of the chord
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
	 * @param shadingStops
	 *            ArrayList of stops that contains the information for the
	 *            shaded colors.
	 */
	private void drawChord(float xStartPos, float yStartPos, float width, float height, float arcAngle, float length,
			String chordColor, boolean solid, String outlineColor, float outlineThickness, Shadow shadowType,
			float rotation, Shading shadingType, ArrayList<Stop> shadingStops) {
		/* Calculate center coordinates from inputs */
		float centerXPos = xStartPos + width;
		float centerYPos = yStartPos + height;

		Arc chord = new Arc(centerXPos, centerYPos, width, height, arcAngle, length);
		chord.setType(ArcType.CHORD);
		colorShape(chord, solid, chordColor, outlineColor, outlineThickness, shadowType, shadingType, shadingStops);

		shape = chord;

		group.getChildren().add(chord);

	}

	/**
	 * Method to draw a arc.
	 * 
	 * @param xStartPos
	 *            the top left x position of the arc
	 * @param yStartPos
	 *            the top left y position of the arc
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
	 * @param shadingStops
	 *            ArrayList of stops that contains the information for the
	 *            shaded colors.
	 */
	private void drawArc(float xStartPos, float yStartPos, float width, float height, float arcAngle, float length,
			String arcColor, boolean solid, String outlineColor, float outlineThickness, Shadow shadowType,
			float rotation, Shading shadingType, ArrayList<Stop> shadingStops) {
		/* Calculate center coordinates from inputs */
		float centerXPos = xStartPos + width;
		float centerYPos = yStartPos + height;

		Arc arc = new Arc(centerXPos, centerYPos, width, height, arcAngle, length);
		arc.setType(ArcType.ROUND);
		colorShape(arc, solid, arcColor, outlineColor, outlineThickness, shadowType, shadingType, shadingStops);

		shape = arc;

		group.getChildren().add(arc);

	}

	/**
	 * @param colorString
	 *            9 digit hex string, starting with a #, describing a ARGB
	 *            color.
	 * @return new color made from the string entered
	 */
	private Color convertStringToColor(String colorString) {
		double a = Integer.parseInt(colorString.substring(alphaStartChar, alphaEndChar), 16) / 255.0;
		double r = Integer.parseInt(colorString.substring(rStartChar, rEndChar), 16) / 255.0;
		double b = Integer.parseInt(colorString.substring(gStartChar, gEndChar), 16) / 255.0;
		double g = Integer.parseInt(colorString.substring(bStartChar, bEndChar), 16) / 255.0;

		return new Color(r, g, b, a);
	}
}
