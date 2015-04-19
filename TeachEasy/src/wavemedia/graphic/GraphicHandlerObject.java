/** (c) Copyright by WaveMedia. */
package wavemedia.graphic;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;

/**
 * Graphic object class for passing to the graphics handler to draw graphics to
 * screen.
 * 
 * @author tjd511
 * @version 1.0 11/03/2015
 */
public class GraphicHandlerObject {
	protected final static float defaultSize = 200;

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

	/* Variables containing all relevant data about shapes. */
	private GraphicType graphic;
	private float xStartPos;
	private float yStartPos;
	private String color;
	private float xEndPos;
	private float yEndPos;
	private boolean solid;
	private String outlineColor;
	private float outlineThickness;
	private float rotation;
	private float radius;
	private float arcWidth;
	private float arcHeight;
	private float size;
	private float width;
	private float height;
	private int numberOfSides;
	private int numberOfPoints;
	private float x1;
	private float x2;
	private float x3;
	private float y1;
	private float y2;
	private float y3;
	private ArrayList<Float> xCoordinates;
	private ArrayList<Float> yCoordinates;
	private float arcAngle;
	private float length;
	private Shadow shadow;
	private Shading shadingType;
	private ArrayList<Stop> stops = new ArrayList<Stop>();

	/* Constructor must be called using the builder */
	private GraphicHandlerObject(GraphicBuilder builder) {
		graphic = builder.graphic;
		xStartPos = builder.xStartPos;
		yStartPos = builder.yStartPos;
		color = builder.color;
		xEndPos = builder.xEndPos;
		yEndPos = builder.yEndPos;
		solid = builder.solid;
		outlineColor = builder.outlineColor;
		outlineThickness = builder.outlineThickness;
		rotation = builder.rotation;
		radius = builder.radius;
		arcWidth = builder.arcWidth;
		arcHeight = builder.arcHeight;
		size = builder.size;
		width = builder.width;
		height = builder.height;
		numberOfSides = builder.numberOfSides;
		numberOfPoints = builder.numberOfPoints;
		x1 = builder.x1;
		x2 = builder.x2;
		x3 = builder.x3;
		y1 = builder.y1;
		y2 = builder.y2;
		y3 = builder.y3;
		xCoordinates = builder.xCoordinates;
		yCoordinates = builder.yCoordinates;
		arcAngle = builder.arcAngle;
		length = builder.length;
		shadow = builder.shadow;
		shadingType = builder.shadingType;
		ArrayList<String> shadingColors = builder.shadingColors;
		ArrayList<Float> stopOffset = builder.offsets;

		/* Populate an array list of stops from the already verified data */
		for (int i = 0; i < shadingColors.size(); i++) {
			if (verifyColor(shadingColors.get(i))) {
				stops.add(new Stop((double) stopOffset.get(i), convertStringToColor(shadingColors.get(i))));
			}
		}
	}

	/**
	 * @return an enum containing information about the current type of graphic
	 */
	public GraphicType getGraphic() {
		return graphic;
	}

	/**
	 * @return the top left x coordinate of the shape
	 */
	public float getXStartPos() {
		return xStartPos;
	}

	/**
	 * @return the top left y coordinate of the shape
	 */
	public float getYStartPos() {
		return yStartPos;
	}

	/**
	 * @return a 8 bit ARGB string value of the color of the shape, preceded by
	 *         a #
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @return the x end coordinate of the shape
	 */
	public float getXEndPos() {
		return xEndPos;
	}

	/**
	 * @return the y end coordinate of the shape
	 */
	public float getYEndPos() {
		return yEndPos;
	}

	/**
	 * @return boolean containing if the shape should be solid or not
	 */
	public boolean isSolid() {
		return solid;
	}

	/**
	 * @return a 8 bit ARGB string value of the color of the outline of the
	 *         shape, preceded by a #
	 */
	public String getOutlineColor() {
		return outlineColor;
	}

	/**
	 * @return the thickness of the outline of the shape
	 */
	public float getOutlineThickness() {
		return outlineThickness;
	}

	/**
	 * @return the angle in degrees through which the shape should be rotated
	 */
	public float getRotation() {
		return rotation;
	}

	/**
	 * @return the radius of the shape
	 */
	public float getRadius() {
		return radius;
	}

	/**
	 * @return the arc width of the shape
	 */
	public float getArcWidth() {
		return arcWidth;
	}

	/**
	 * @return the arc height of the shape
	 */
	public float getArcHeight() {
		return arcHeight;
	}

	/**
	 * @return the size of the shape
	 */
	public float getSize() {
		return size;
	}

	/**
	 * @return the width of the shape
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * @return the height of the shape
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * @return the number of sides of the regular polygon
	 */
	public int getNumberOfSides() {
		return numberOfSides;
	}

	/**
	 * @return the number of points on the star
	 */
	public int getNumberOfPoints() {
		return numberOfPoints;
	}

	/**
	 * @return the x coordinate of the 1st point on the triangle
	 */
	public float getX1() {
		return x1;
	}

	/**
	 * @return the x coordinate of the 2st point on the triangle
	 */
	public float getX2() {
		return x2;
	}

	/**
	 * @return the x coordinate of the 3st point on the triangle
	 */
	public float getX3() {
		return x3;
	}

	/**
	 * @return the y coordinate of the 1st point on the triangle
	 */
	public float getY1() {
		return y1;
	}

	/**
	 * @return the y coordinate of the 2st point on the triangle
	 */
	public float getY2() {
		return y2;
	}

	/**
	 * @return the y coordinate of the 3st point on the triangle
	 */
	public float getY3() {
		return y3;
	}

	/**
	 * @return the x coordinates of the corners of the polygon
	 */
	public ArrayList<Float> getXCoordinates() {
		return xCoordinates;
	}

	/**
	 * @return the y coordinates of the corners of the polygon
	 */
	public ArrayList<Float> getYCoordinates() {
		return yCoordinates;
	}

	/**
	 * @return the arc angle of the shape
	 */
	public float getArcAngle() {
		return arcAngle;
	}

	/**
	 * @return the length of the shape
	 */
	public float getLength() {
		return length;
	}

	/**
	 * @return an enum containing the amount of shadow the graphic should be
	 *         drawn with
	 */
	public Shadow getShadow() {
		return shadow;
	}

	/**
	 * @return an enum describing the type of shading of the shape
	 */
	public Shading getShadingType() {
		return shadingType;
	}

	/**
	 * @return an arraylist contaning all of the stops to be used in shading
	 */
	public ArrayList<Stop> getStops() {
		return stops;
	}

	/**
	 * Method to check validity of any color string
	 * 
	 * @param color
	 *            string to be verified
	 */
	protected static boolean verifyColor(String color) {
		/* Checking that color is a 8 digit long hex string */
		return (color.matches("^([#]([0-9a-fA-F]{8}))$"));
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

	/**
	 * Builder for all graphics objects.
	 * 
	 * @author tjd511
	 * @version 0.2 10/03/2015
	 */
	public static class GraphicBuilder {
		/* Required values for all graphics */
		private final GraphicType graphic;
		private final float xStartPos;
		private final float yStartPos;

		/* Optional values for graphics */
		private String color = "#ff000000";
		private float xEndPos;
		private float yEndPos;
		private boolean solid = true;
		private String outlineColor = "#00000000";
		private float outlineThickness = 1;
		private float rotation;
		private float radius = defaultSize;
		private float arcWidth;
		private float arcHeight;
		private float size = defaultSize;
		private float width = defaultSize;
		private float height = defaultSize;
		private int numberOfSides;
		private int numberOfPoints;
		private float x1;
		private float x2;
		private float x3;
		private float y1;
		private float y2;
		private float y3;
		private ArrayList<Float> xCoordinates;
		private ArrayList<Float> yCoordinates;
		private float arcAngle;
		private float length;
		private Shadow shadow = Shadow.NONE;
		private Shading shadingType = Shading.NONE;
		private ArrayList<String> shadingColors;
		private ArrayList<Float> offsets;

		/**
		 * Constructor for GraphicBuilder takes the 3 required values for each
		 * shape.
		 * 
		 * @param graphic
		 *            an enum of type GraphicsType that contains the type of
		 *            shape being described.
		 * @param xStartPos
		 *            the x coordinate of the top left position of the shape.
		 * @param yStartPos
		 *            the y coordinate of the top left position of the shape.
		 */
		public GraphicBuilder(GraphicType graphic, float xStartPos, float yStartPos) {
			shadingColors = new ArrayList<String>();
			offsets = new ArrayList<Float>();
			xCoordinates = new ArrayList<Float>();
			yCoordinates = new ArrayList<Float>();

			this.graphic = graphic;
			this.xStartPos = xStartPos;
			this.yStartPos = yStartPos;

			this.xEndPos = xStartPos + defaultSize;
			this.yEndPos = yStartPos + defaultSize;
		}

		/**
		 * Method sets the color of the graphic
		 * 
		 * @param color
		 *            a #, followed by a 8 bit hex string of format ARGB
		 */
		public GraphicBuilder color(String color) {
			if (verifyColor(color)) {
				this.color = color;
			}
			return this;
		}

		/**
		 * Method sets the x end coordinate of the graphic
		 * 
		 * @param xEndPos
		 *            the ending x coordinate of the graphic
		 */
		public GraphicBuilder xEndPos(Float xEndPos) {
			this.xEndPos = xEndPos;
			return this;
		}

		/**
		 * Method sets the y end coordinate of the graphic
		 * 
		 * @param yEndPos
		 *            the ending y coordinate of the graphic
		 */
		public GraphicBuilder yEndPos(Float yEndPos) {
			this.yEndPos = yEndPos;
			return this;
		}

		/**
		 * Method sets if the shape is solid or not
		 * 
		 * @param solid
		 *            a boolean containing if the shape is solid or not
		 */
		public GraphicBuilder solid(boolean solid) {
			this.solid = solid;
			return this;
		}

		/**
		 * Method sets the outline color of the graphic
		 * 
		 * @param outlineColor
		 *            a #, followed by a 8 bit hex string of format ARGB
		 */
		public GraphicBuilder outlineColor(String outlineColor) {
			if (verifyColor(outlineColor)) {
				this.outlineColor = outlineColor;
			}
			return this;
		}

		/**
		 * Method sets the outline thickness of the shape
		 * 
		 * @param outlineThickness
		 *            a float for the thickness of the outline
		 */
		public GraphicBuilder outlineThickness(float outlineThickness) {
			if (outlineThickness <= 0) {
				this.outlineThickness = outlineThickness;
			}
			return this;
		}

		/**
		 * Method sets the amount of rotation around the center for the shape
		 * 
		 * @param rotation
		 *            the amount of rotation around the center in degrees
		 */
		public GraphicBuilder rotation(float rotation) {
			this.rotation = rotation;
			return this;
		}

		/**
		 * Method sets the radius of the shape
		 * 
		 * @param radius
		 *            the radius of the shape
		 */
		public GraphicBuilder radius(float radius) {
			this.radius = radius;
			return this;
		}

		/**
		 * Method sets the arc width of the shape
		 * 
		 * @param arcWidth
		 *            the arc width of the shape
		 */
		public GraphicBuilder arcWidth(float arcWidth) {
			this.arcWidth = arcWidth;
			return this;
		}

		/**
		 * Method sets the arc height of the shape
		 * 
		 * @param arcHeight
		 *            the arc height of the shape
		 */
		public GraphicBuilder arcHeight(float arcHeight) {
			this.arcHeight = arcHeight;
			return this;
		}

		/**
		 * Method sets the size of the shape
		 * 
		 * @param size
		 *            the size of the shape
		 */
		public GraphicBuilder size(float size) {
			this.size = size;
			return this;
		}

		/**
		 * Method sets the width of the shape
		 * 
		 * @param width
		 *            the width of the shape
		 */
		public GraphicBuilder width(float width) {
			this.width = width;
			return this;
		}

		/**
		 * Method sets the height of the shape
		 * 
		 * @param height
		 *            the height of the shape
		 */
		public GraphicBuilder height(float height) {
			this.height = height;
			return this;
		}

		/**
		 * Method sets the number of sides of the regular polygon
		 * 
		 * @param numberOfSides
		 *            the number of sides
		 */
		public GraphicBuilder numberOfSides(int numberOfSides) {
			this.numberOfSides = numberOfSides;
			return this;
		}

		/**
		 * The number of points on the star
		 * 
		 * @param numberOfPoints
		 *            the number of points on the star
		 */
		public GraphicBuilder numberOfPoints(int numberOfPoints) {
			this.numberOfPoints = numberOfPoints;
			return this;
		}

		/**
		 * Method sets all the coordinates of the triangle.
		 * 
		 * @param x1
		 *            the x coordinate of the first point of the triangle
		 * @param y1
		 *            the y coordinate of the first point of the triangle
		 * @param x2
		 *            the x coordinate of the second point of the triangle
		 * @param y2
		 *            the y coordinate of the second point of the triangle
		 * @param x3
		 *            the x coordinate of the third point of the triangle
		 * @param y3
		 *            the y coordinate of the third point of the triangle
		 */
		public GraphicBuilder triangleCoordinates(float x1, float y1, float x2, float y2, float x3, float y3) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
			this.x3 = x3;
			this.y3 = y3;
			return this;
		}

		/**
		 * Method sets the coordinates of one of the corner of the polygon.
		 * 
		 * @param xCoordinate
		 *            the x coordinate of the corner
		 * @param yCoordinate
		 *            the y coordinate of the corner
		 */
		public GraphicBuilder polygonCoordinate(float xCoordinate, float yCoordinate) {
			xCoordinates.add(xCoordinate);
			yCoordinates.add(yCoordinate);
			return this;
		}

		/**
		 * Method sets the arc angle of the shape
		 * 
		 * @param arcAngle
		 *            the arc angle of the shape in degrees
		 */
		public GraphicBuilder arcAngle(float arcAngle) {
			this.arcAngle = arcAngle;
			return this;
		}

		/**
		 * Method sets the length of the shape
		 * 
		 * @param length
		 *            the length of the shape
		 */
		public GraphicBuilder length(float length) {
			this.length = length;
			return this;
		}

		/**
		 * Method sets the amount of shadow to be applied to each shape.
		 * 
		 * @param shadow
		 *            an enum containing the amount of shadow.
		 * @see {@link graphicsHandler.Shadow}
		 */
		public GraphicBuilder shadow(Shadow shadow) {
			this.shadow = shadow;
			return this;
		}

		/**
		 * Method sets the type of shading to be applied to the shape.
		 * 
		 * @param shadingType
		 *            an enum containing the type of shading to be applied to
		 *            the shape.
		 * 
		 * @see {@link graphicsHandler.Shading}
		 */
		public GraphicBuilder shadingType(Shading shadingType) {
			this.shadingType = shadingType;
			return this;
		}

		/**
		 * Method sets one of the shading values for the shape.
		 * 
		 * @param shadingColor
		 *            a #, followed by a 8 bit hex string of format ARGB
		 * @param offset
		 *            the relative distance through the shape that the color
		 *            should appear.
		 * @see {@link graphicsHandler.Shading}
		 */
		public GraphicBuilder shadingElement(String shadingColor, float offset) {
			if (verifyColor(shadingColor) && (offset >= 0) && (offset <= 1)) {
				shadingColors.add(shadingColor);
				offsets.add(offset);
			}
			return this;
		}

		/**
		 * Method builds the graphics object.
		 * 
		 * Defaults:
		 * 
		 * Color: "#ff000000". Outline color: "#00000000". Outline thickness: 1.
		 * Shading: Shading.NONE. Shadow: Shadow.NONE.
		 * 
		 * @return a graphics object containing all the paramaters about the
		 *         graphic.
		 */
		public GraphicHandlerObject build() {
			return new GraphicHandlerObject(this);
		}
	}
}
