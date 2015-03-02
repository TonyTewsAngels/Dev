package teacheasy.data;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;

import teacheasy.data.multichoice.Answer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//create and array list of correct and incorrect answers  


public class multipleChoice{
	
	private Group group ;
	private int i;
	private int index;

	ArrayList<CheckBox> cB;
	ArrayList<RadioButton> rB; 
    
	public multipleChoice (Group nGroup , ArrayList<Answer>answers, int defaultPadding , int spacing ){
		
		this.group = nGroup;
		
		answers.get(i).getText();
	    answers.get(index).isCorrect();
	    
	    this.cB = new ArrayList();
	    
	    this.rB = new ArrayList();
	    
	    ToggleGroup tGroup = new ToggleGroup();
	    
	    //Creation of CheckBox's 
	    for (i = 0; i < answers.size();i++ ){
	    	CheckBox temp = new CheckBox(answers.get(i).getText());
	    	cB.add(temp);
	    }
	    
	    //creation of radio buttons 
	    for(i = 0; i < answers.size(); i++ ){
	    	RadioButton tempRB = new RadioButton(answers.get(i).getText());
	    	tempRB.setToggleGroup(tGroup);
	    	rB.add(tempRB);
	    }
	    

	    
	    
	    
	    
	    
	    //set orientation 
	    
	    // vertical position
	    VBox verticalPosition = new VBox(spacing);
        verticalPosition.setPadding(new Insets(defaultPadding));
        verticalPosition.getChildren().addAll(cB);
        verticalPosition.getChildren().addAll(rB);
 
        
		// horizontal position 
        HBox horizontalPosition = new HBox(spacing);
		horizontalPosition.setPadding( new Insets (defaultPadding));
		horizontalPosition.getChildren().addAll(cB);
		horizontalPosition.getChildren().addAll(rB);
		
		// To be Able to choose Only one radio option
		
		
		
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
