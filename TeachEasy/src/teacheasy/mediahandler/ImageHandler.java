/*
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
 * @Authors: Daniel Berhe & Jake Ransom
 * @Verion:  1.0 23/02/2015
 * 
 * */
public class ImageHandler {
	private Group group;
	private ArrayList<ImageView> images;

	public ImageHandler(Group nGroup){
		group = nGroup;
		images = new ArrayList<ImageView>();
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
			                double locationY, double widthSize,
			                double heightSize, double rotationDegree,
			                boolean scaleBased) {
		double imageWidth;
		double imageHeight;
		
		Image image;

		if (imageName.startsWith("http")) {
			/*File is a web resource, check that it exists*/
			if(!mediaExists(imageName)) {
				/*Add a label to notify user that media was unavailable*/
                Label label = new Label("Media Unavailable");
                label.relocate(locationX, locationY);
                group.getChildren().add(label);
                
                /*return to halt creation of image*/
                return;
			}
			
			image = new Image("URL:" + imageName);
			
		} else {
			
			/*File is a local Resource*/
			image = new Image("file:" + imageName);
			
		}
		
		imageWidth = image.getWidth();
		imageHeight = image.getHeight();
		
		ImageView imageView = new ImageView(image);
		imageView = new ImageView();
		imageView.setImage(image);
		
		if(scaleBased) {
		    imageView.setFitWidth(imageWidth * widthSize);
            imageView.setFitHeight(imageHeight *heightSize);
		} else {
		    imageView.setFitWidth(widthSize);
		    imageView.setFitHeight(heightSize);
		}
		imageView.setRotate(rotationDegree);
		imageView.relocate(locationX, locationY);
		
		images.add(imageView);
		
		group.getChildren().add(imageView);
	}
	
	public Image getImage(int imageID) {
	    if(imageID >= 0 && imageID < images.size()) {
	        return images.get(imageID).getImage();
	    }
	    
	    return null;
	}
	
	public ArrayList<ImageView> getImages() {
	    return images;
	}
	
	public void removeImage(int imageID) {
	    if(imageID >= 0 && imageID < images.size()) {
	        group.getChildren().remove(images.get(imageID));
	        images.remove(imageID);
	    }
	}
	
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
        }
        catch (Exception e) {
           return false;
        }
    }
    
    public void debugPrint() {
        System.out.println("### Image Handler ###");
        System.out.println("Handling: " + images.size());
    }
}
