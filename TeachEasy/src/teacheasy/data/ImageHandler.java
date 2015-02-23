package teacheasy.data;

import javafx.application.Application;
//import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class ImageHandler {
	
	private ImageView iv1;
	double targetLocationX ;
	double targetLocationY;
	
	
    public void start(Stage stage) {
		stage.setTitle("Drag & Drop trial");
		Image image1 = new Image("file:cat.jpg");
		Image image2 = new Image("file:dog.jpg");
	
	    
		/*ImageView source = new ImageView(image1);
	    ImageView target = new ImageView(image2);
		target.relocate(200, 200);
		source.setRotationAxis(Rotate.Y_AXIS);*/
	
		Group root = new Group();
		Scene scene = new Scene(root, 800, 800);
		scene.setFill(Color.LIGHTBLUE);
		insertImage("cat.jpg", 200,200,300,0,root);
  
        //root.getChildren().add(source);
        // root.getChildren().add(target);
        stage.setScene(scene);
        stage.show();
        
        
    }

	private void insertImage(String imageName,double locationX, double locationY, int size, double rotationDegree,
			 			     Group group){
		
		Image image = new Image("file:" + imageName);
		
		iv1 = new ImageView(image);
		iv1 = new ImageView();
		iv1.setImage(image);
		iv1.setFitWidth(size);
		iv1.setPreserveRatio(true);
		iv1.setRotate(rotationDegree);
		iv1.relocate(locationX, locationY);
		group.getChildren().add(iv1);
	}

	
	
}
	