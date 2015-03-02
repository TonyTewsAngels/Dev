package teacheasy.data;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;

import teacheasy.data.multichoice.Answer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//create and array list of correct and incorrect answers  


public class multipleChoice{
	
	private Group group ;
	private int i;
	private int index;

	ArrayList<CheckBox> cB;
    
	public multipleChoice (ArrayList<Answer> answers, int Defaultpadding , int spacing ){
		
		answers.get(i).getText();
	    answers.get(index).isCorrect();
	    this.cB = new ArrayList();
	    
	    for (i = 0; i < answers.size();i++ ){
	    	
	    	CheckBox temp = new CheckBox(answers.get(i).getText());
	    	cB.add(temp);
	       // cB.setText(answers.get(i).getText());
	    	 
	    }
	    //set orientation 
	    
	    // vertical position
	    VBox verticalPosition = new VBox(spacing);
        verticalPosition.setPadding(new Insets(Defaultpadding));
        verticalPosition.getChildren().addAll(cB);
 
        
		// horizontal position 
        HBox horizontalPosition = new HBox(spacing);
		horizontalPosition.setPadding( new Insets (Defaultpadding));
		horizontalPosition.getChildren().addAll(cB);
		
	}

	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
	}

	
	public void checkBoxes(String answers ){     
	}
	
	
	
	public static void main(String[] args) {
	        //launch(args);
	        
	    }
	
}
