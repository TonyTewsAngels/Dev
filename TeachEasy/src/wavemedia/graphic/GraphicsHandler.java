/** (c) Copyright by WaveMedia. */
package wavemedia.graphic;

import java.util.ArrayList;

import javafx.scene.Group;

/**
 * Class to handle multiple graphics at once. 
 * 
 * @author tjd511
 * @version 1.0 13/04/2015
 */
public class GraphicsHandler {

	private Group group;

	private ArrayList<Graphic> graphics;

	/** Constructor Method */
	public GraphicsHandler(Group group) {
		this.group = group;

		/* Initialise the list of all the graphics */
		this.graphics = new ArrayList<Graphic>();
	}

	/**
	 * Method creates a new graphic, and draws it on the group specified in the
	 * constructor.
	 * 
	 * @param graphicObject
	 *            a graphics object containing all the information about the
	 *            graphic to be drawn. Must be formed using the GraphicBuilder.
	 * @see {@link graphicsHandler.GraphicObject}
	 */
	public void createGraphic(GraphicObject graphicObject) {
		graphics.add(new Graphic(group));
		graphics.get(graphics.size() - 1).drawShape(graphicObject);
	}

	/**
	 * Method toggles the visibility of a graphic.
	 * 
	 * @param graphicNumber
	 *            the index of the graphic to be changed
	 * @param visible
	 *            boolean containing the state of the visibility of the graphic
	 */
	public void setVisible(int graphicNumber, boolean visible) {
		if (graphicNumber < graphics.size() && graphicNumber >= 0) {
			graphics.get(graphicNumber).setVisible(visible);
		}
	}

	/**
	 * Method to send a particular graphic to the back of the group.
	 * 
	 * @param graphicNumber
	 *            the index of the graphic to be sent to back
	 */
	public void sendToBack(int graphicNumber) {
		if (graphicNumber < graphics.size() && graphicNumber >= 0) {
			graphics.get(graphicNumber).toBack();
		}
	}

	/**
	 * Method to send a particular graphic to the front of the group.
	 * 
	 * @param graphicNumber
	 *            the index of the graphic to be sent to front
	 */
	public void sendToFront(int graphicNumber) {
		if (graphicNumber < graphics.size() && graphicNumber >= 0) {
			graphics.get(graphicNumber).toFront();
		}
	}

	/**
	 * Method gets the number of graphics.
	 * 
	 * @return the current number of instantiated graphics
	 */
	public int getGraphicCount() {
		return graphics.size();
	}

	/**
	 * Method clears the current array of graphics
	 */
	public void clearGraphics() {
		graphics.clear();
	}
}
