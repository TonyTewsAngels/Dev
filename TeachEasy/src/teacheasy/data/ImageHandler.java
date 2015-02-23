/*
 * This class inserts an image with specified image name, size, location(x and y) and rotation degrees.
 *
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * @Authors: Daniel Berhe & Jake Ransom
 * @Verion:  1.0 23/02/2015
 */
package teacheasy.data;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ImageHandler {

	private ImageView iv1;

	public void start(Stage stage) {

		Group root = new Group();
		Scene scene = new Scene(root, 800, 800);

		insertImage("cat.jpg", 200, 200, 300, 90, root);

		stage.setScene(scene);
		stage.show();

	}

	/**
	 * 
	 * @param imageName
	 *            : name of the image as saved on disk
	 * @param locationX
	 *            : desired x location of the image
	 * @param locationY
	 *            : desired y location of the image
	 * @param size
	 *            : actual image size multiplier. Eg, size 2 is twice as big as
	 *            the original image
	 * @param rotationDegree
	 *            : to rotate the image in clockwise direction
	 * @param group
	 *            : group layout
	 */
	private void insertImage(String imageName, double locationX,
			double locationY, double size, double rotationDegree, Group group) {
		double imageWidth;

		Image image = new Image("file:" + imageName);
		imageWidth = image.getWidth();
		iv1 = new ImageView(image);
		iv1 = new ImageView();
		iv1.setImage(image);
		iv1.setFitWidth(imageWidth * size);
		iv1.setPreserveRatio(true);
		iv1.setRotate(rotationDegree);
		iv1.relocate(locationX, locationY);
		group.getChildren().add(iv1);
	}
}
