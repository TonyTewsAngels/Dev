/*
 * Daniel Berhe & Jake Ransom
 *
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 */
package teacheasy.mediahandler;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**This class inserts an image with specified image name, size, location(x and y) and rotation degrees.
 * 
 * @Author Daniel Berhe 
 * @author Jake Ransom
 * @Verion 1.0 23 Feb 2015
 * 
 */
public class ImageHandler {
    
    /* Reference to the group to add images to */
	private Group group;
	
	/* Array list of the image being handled */
	private ArrayList<ImageView> images;

	/**
	 * Constructor for the image handler.
	 * 
	 * @param nGroup The group to add images to.
	 */
	public ImageHandler(Group nGroup){
	    /* Set up the group reference */
		group = nGroup;
		
		/* Instantiate the list of images */
		images = new ArrayList<ImageView>();
	}

	/**
	 * Adds a new image to the group.
	 * 
	 * @param imageName Name of the image as saved on disk.
	 * @param locationX Desired x axis position of the image.
	 * @param locationY Desired y axis position of the image.
	 * @param widthSize Actual image width multiplier. Eg, size 
	 *                  2 is twice as big as the original image.
	 * @param heightSize Actual image height multiplier.
	 * @param rotationDegree Rotation of the image in degrees.
	 * @param scaleBased Indicates if the size parameters are 
	 *                   scale based or absolute.
	 */
	public void insertImage(String imageName, double locationX,
			                double locationY, double widthSize,
			                double heightSize, double rotationDegree,
			                boolean scaleBased) {
	    /* Size parameters */
		double imageWidth;
		double imageHeight;
		
		/* Declare an image object */
		Image image;

		/* Check if the source is online or local */
		if (imageName.startsWith("http")) {
			/*File is a web resource, check that it exists*/
			if(!mediaExists(imageName)) {
				/* Media is not accessible, add a label to notify user */
                Label label = new Label("Media Unavailable");
                label.relocate(locationX, locationY);
                group.getChildren().add(label);
                
                /* Return immediately to halt creation of image */
                return;
			}
			
			/* Instantiate the image */
			image = new Image("URL:" + imageName);
		} else {
			/*File is a local Resource*/
			image = new Image("file:" + imageName);
		}
		
		/* Get size information */
		imageWidth = image.getWidth();
		imageHeight = image.getHeight();
		
		/* Create an image view */
		ImageView imageView = new ImageView(image);
		imageView = new ImageView();
		imageView.setImage(image);
		
		/* Check if the image is being drawn based on scale */
		if(scaleBased) {
		    imageView.setFitWidth(imageWidth * widthSize);
            imageView.setFitHeight(imageHeight *heightSize);
		} else {
		    imageView.setFitWidth(widthSize);
		    imageView.setFitHeight(heightSize);
		}
		
		/* Apply rotation and location */
		imageView.setRotate(rotationDegree);
		imageView.relocate(locationX, locationY);
		
		/* Add the image view to the list */
		images.add(imageView);
		
		/* Add the image to the group */
		group.getChildren().add(imageView);
	}
	
	/**
	 * Gets a specific image from the handler.
	 * 
	 * @param imageID The ID of the image to get.
	 * @return The selected image object if it exists.
	 */
	public Image getImage(int imageID) {
	    if(imageID >= 0 && imageID < images.size()) {
	        return images.get(imageID).getImage();
	    }
	    
	    return null;
	}
	
	/**
	 * Gets the array list of image views.
	 * 
	 * @return An array list of all the image views being handled.
	 */
	public ArrayList<ImageView> getImages() {
	    return images;
	}
	
	/**
	 * Removes a specified image from the group.
	 * 
	 * @param imageID The ID of the image to remove.
	 */
	public void removeImage(int imageID) {
	    if(imageID >= 0 && imageID < images.size()) {
	        group.getChildren().remove(images.get(imageID));
	        images.remove(imageID);
	    }
	}
	
	/**
	 * Clear all the images.
	 */
	public void clearImages() {
	    images.clear();
	}
	
    /** 
     * Utility function, checks if an online image exists
     * 
     * @param url The URL to check
     */
    private static boolean mediaExists(String url){
        try {
          /* Do not follow redirects */
          HttpURLConnection.setFollowRedirects(false);
          
          /* Open a connection to the media */
          HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
          
          /* Use a head only request to just retrieve metadata */
          con.setRequestMethod("HEAD");
          
          /* If a response code of 200 (okay) is received, the media is available */
          return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            /* Media unavailabe, return false */
            return false;
        }
    }
    
    /**
     * Prints information about the objects being handled to the 
     * console for debugging purposes.
     */
    public void debugPrint() {
        System.out.println("### Image Handler ###");
        System.out.println("Handling: " + images.size());
    }
}
