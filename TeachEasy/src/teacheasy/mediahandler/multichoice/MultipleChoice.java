/*
 * Emmanuel Olutayo
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.mediahandler.multichoice;

import java.util.ArrayList;
import java.util.List;

import teacheasy.data.MultipleChoiceObject.MultiChoiceType;
import teacheasy.data.MultipleChoiceObject.Orientation;
import teacheasy.data.multichoice.Answer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * A class to create check boxes and radio buttons 
 * 
 * @author	Emmanuel Olutayo
 * @version	1.0 07 Mar 2015
 */
public class MultipleChoice{
    /* Group reference */
	private Group group ;
	
	/* Lists of the check boxes and radio buttons */
	List<MChoiceCheckBox> cB;
	List<MChoiceRadio> rB;
	
	/* Data */
	MultiChoiceType type;
	Orientation orientation;
	
	/** Constructor */
	public MultipleChoice (Group nGroup, double xStart, double yStart, ArrayList<Answer>answers, MultiChoiceType nType, Orientation nOrientation ) {

	    /* Set the group reference */
		this.group = nGroup;
		
		/* Instantiate the array lists */
		cB = new ArrayList<MChoiceCheckBox>();
		rB = new ArrayList<MChoiceRadio>();
		
		/* Set the type variable */
		this.type = nType;

		/* VBox to hold the answers if vertically oriented */
		VBox verticalPosition = new VBox(10);
		verticalPosition.setPadding(new Insets(10));

		/* HBox to hold the answers if horizontally oriented */
		HBox horizontalPosition = new HBox(10);
		horizontalPosition.setPadding(new Insets (10));

		/* The button to mark the question */
		Button markButton = new Button("Mark");

		/* Toggle group used to allow only one radio button to be selected */
		ToggleGroup tGroup = new ToggleGroup();

		switch (nType){
    		case CHECKBOX:
    			/* Create the check boxes */
    			for (int i = 0; i < answers.size();i++ ){
    				CheckBox temp = new CheckBox(answers.get(i).getText());
    				cB.add(new MChoiceCheckBox(temp, answers.get(i).isCorrect()));
    			} 
    			
    			/* Check the orientation and add the check boxes appropriately */
    			if (nOrientation == Orientation.VERTICAL){
    				for(int i= 0; i < cB.size(); i++ ){
    					verticalPosition.getChildren().add(cB.get(i).getCheckBox());					
    				}
    				
    				verticalPosition.getChildren().add(markButton);
    				verticalPosition.relocate(xStart, yStart);
    				group.getChildren().add(verticalPosition);
    			}else {
    				for(int i = 0; i < cB.size(); i++ ){
    					horizontalPosition.getChildren().add(cB.get(i).getCheckBox());
    				}
    				
    				horizontalPosition.getChildren().add(markButton);
    				horizontalPosition.relocate(xStart, yStart);
    				group.getChildren().add(horizontalPosition);
    			}
    			
    			/* Set the mark buttons action */
    			markButton.setOnAction(new MultipleChoiceCheckHandler());
    			
    			break;
    			
    		case RADIO:
    			/* Create the radio buttons */
    			for(int i = 0; i < answers.size(); i++ ){
    				RadioButton tempRb = new RadioButton(answers.get(i).getText());
    				tempRb.setToggleGroup(tGroup);
    				rB.add(new MChoiceRadio(tempRb, answers.get(i).isCorrect()));
    			}
    
    			/* Check the orientation and add the radio buttons appropriately */
    			if( nOrientation == Orientation.VERTICAL){
    				for(int i= 0; i < rB.size(); i++ ){
    					verticalPosition.getChildren().add(rB.get(i).getRadioButton());
    				}
    
    				verticalPosition.getChildren().add(markButton);
    				verticalPosition.relocate(xStart, yStart);
    				group.getChildren().add(verticalPosition);
    			} else { 
    				for(int i =0; i< rB.size();i++){
    					horizontalPosition.getChildren().add(rB.get(i).getRadioButton());
    				}
    
    				horizontalPosition.getChildren().add(markButton);
    				horizontalPosition.relocate(xStart, yStart);
    				group.getChildren().add(horizontalPosition);
    			}
    			
    			/* Set the mark buttons action */
    			markButton.setOnAction(new MultipleChoiceCheckHandler());
    			
    			break;
    
    		case DROPDOWNLIST:
    			break;
		}
	}

	public boolean Check(){
	    /* Check the type */
	    switch(type) {
	        case CHECKBOX:
	            /* Check the check boxes */
	            return CheckBoxCheck();
	        case RADIO:
	            /* Check the radio buttons */
	            return RadioCheck();
	        case DROPDOWNLIST:
	            /* TODO */
	            return false;
            default:
                return false;
	    }
	}
	
	public boolean CheckBoxCheck() {
	    /* Loop through all the check boxes */
	    for(int i = 0; i < cB.size(); i++ ){
	        /* If this box is ticked and is incorrect return false */
            if(cB.get(i).getCheckBox().isSelected() && !cB.get(i).isCorrect()){
                return false;
            } 
            
            /* If this box is not ticked but the answer is correct return false */
            if(!cB.get(i).getCheckBox().isSelected() && cB.get(i).isCorrect()) {
                return false;
            }
        }
	    
	    /* If all boxes are correct return true */
	    return true;
	}

	public boolean RadioCheck(){
		/* Loop through all the radio buttons */
		for(int i = 0; i < rB.size(); i++){
		    /* If this is a correct answer and its selected return true */
			if(rB.get(i).isCorrect() && rB.get(i).getRadioButton().isSelected()){
				return true;
			}
		}
		
		/* A correct answer was not selected, return false */
		return false;
	}
	
	/**
	 * This class handles the mark button's action
	 */
	public class MultipleChoiceCheckHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent arg0) {
            if (Check()){
                System.out.println("Thats Great ");
            }else 
                System.out.println("Try again");
        }
	}

}
