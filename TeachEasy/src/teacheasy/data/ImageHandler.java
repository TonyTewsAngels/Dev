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


public class ImageHandler extends Application {
 @Override public void start(Stage stage) {

	// Load Image
	Image image1 = new Image("file:catderp.jpg");
	Image image2 = new Image("file:dog.jpg");
	
	//display Image 
	ImageView iv1 = new ImageView(image1);
	iv1.setImage(image1);
	iv1.setFitWidth(500);
    iv1.setPreserveRatio(true);
    iv1.setSmooth(true);
    iv1.relocate(500, 0);

	
	ImageView iv2 = new ImageView(image2);
	iv2.setImage(image2);
	iv2.setFitWidth(500);
    iv2.setPreserveRatio(true);
    iv2.setSmooth(true);
    iv2.relocate(0, 0);
    
    ImageView iv3 = new ImageView(image1);
	iv3.setImage(image1);
	iv3.setFitWidth(500);
    iv3.setPreserveRatio(true);
    iv3.setSmooth(true);
    iv3.relocate(0, 370);
    
    
    ImageView iv4 = new ImageView(image2);
	iv4.setImage(image2);
	iv4.setFitWidth(500);
    iv4.setPreserveRatio(true);
    iv4.setSmooth(true);
    iv4.relocate(500, 280);

	
	stage.setTitle("Image View Test");
    Group root = new Group();
    Scene scene = new Scene(root);
    scene.setFill(Color.BLACK);
    //HBox box = new HBox();
    /*box.getChildren().add(iv1);
    box.getChildren().add(iv2);
    box.getChildren().add(iv3);*/
    //VBox box = new VBox();
    /*box.getChildren().add(iv1);
    box.getChildren().add(iv2);
    box.getChildren().add(iv3);*/
    root.getChildren().addAll(iv1, iv2, iv3, iv4);
    stage.setWidth(415);
    stage.setHeight(200);
    stage.setScene(scene); 
    stage.sizeToScene(); 
    stage.show();
	}
	
	public static void main(String[] args){
		launch();
	}
}