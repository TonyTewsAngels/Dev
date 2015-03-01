/*
 *
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 */
package teacheasy.mediahandler;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**This class inserts an image with specified image name, size, location(x and y) and rotation degrees.
 * 
 * @Authors: Daniel Berhe & Jake Ransom
 * @Verion:  1.0 23/02/2015
 * 
 * */
public class ImageHandler {


	private Group group;

	public ImageHandler(Group nGroup){
		group = nGroup;
	}

	/**
	 * 
	 * @param imageName
	 *            : name of the image as saved on disk
	 * @param locationX
	 *            : desired x location of the image
	 * @param locationY
	 *            : desired y location of the image
	 * @param widthSize
	 *            : actual image with multiplier. Eg, size 2 is twice as big as
	 *            the original image
	 * @param heightSize: actual image height multiplier
	 * @param rotationDegree
	 *            : to rotate the image in clockwise direction
	 * @param group
	 *            : group layout
	 */
	public void insertImage(String imageName, double locationX,
			double locationY, double widthSize, double heightSize, double rotationDegree) {
		double imageWidth;
		double imageHeight;

		Image image = new Image("file:" + imageName);
		imageWidth = image.getWidth();
		imageHeight = image.getHeight();
		ImageView imageView = new ImageView(image);
		imageView = new ImageView();
		imageView.setImage(image);
		imageView.setFitWidth(imageWidth * widthSize);
        imageView.setFitHeight(imageHeight *heightSize);
		imageView.setRotate(rotationDegree);
		imageView.relocate(locationX, locationY);
		group.getChildren().add(imageView);
	}
}
