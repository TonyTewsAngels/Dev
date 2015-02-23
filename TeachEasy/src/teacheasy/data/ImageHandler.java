/*
 * AlexCash
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.data;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A class to handle Image requests
 * 
 * @author ac1054
 * @version 1.3 23 Feb 2015
 */
public class ImageHandler {

	public ImageView getImage(File sourceFile,double xStart, double yStart, float xScale, float yScale, int rotation){
		boolean preserveRatio;
		
		Image imageFile = new Image(sourceFile.toURI().toString());
		double h = imageFile.getHeight();
		double w = imageFile.getWidth();
		
		double requestedHeight = h*xScale;
		double requestedWidth = w*yScale;
		
		if ((requestedHeight == h)&&(requestedWidth == w)){
			preserveRatio = true;
		}else {
			preserveRatio = false;
		}
		
		Image image = new Image(sourceFile.toURI().toString(), requestedWidth, requestedHeight, preserveRatio, true);
		ImageView selectedImage = new ImageView();
		selectedImage.setImage(image);
		selectedImage.setRotate(rotation);
		selectedImage.setX(xStart);
		selectedImage.setY(yStart);
		
		return selectedImage;
	}

	// /** Constructor Method */
	// public ImageHandler () {
	//
	// }

}
