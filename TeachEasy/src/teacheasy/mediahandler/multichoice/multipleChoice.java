package teacheasy.mediahandler.multichoice;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.event.ChangeListener;

import teacheasy.data.MultipleChoiceObject.MultiChoiceType;
import teacheasy.data.MultipleChoiceObject.Orientation;
import teacheasy.data.multichoice.Answer;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
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
import javafx.stage.Stage;

//create and array list of correct and incorrect answers  


public class multipleChoice{

	private Group group ;
	ArrayList<MChoiceCheckBox> cB;
	ArrayList<MChoiceRadio> rB; 



	public multipleChoice (Group nGroup , ArrayList<Answer>answers, int defaultPadding, 
			int spacing, MultiChoiceType type, Orientation orientation ) {

		this.group = nGroup;
		cB = new ArrayList<MChoiceCheckBox>();
		rB = new ArrayList<MChoiceRadio>();

		// vertical position
		VBox verticalPosition = new VBox(spacing);
		verticalPosition.setPadding(new Insets(defaultPadding));

		// horizontal position 
		HBox horizontalPosition = new HBox(spacing);
		horizontalPosition.setPadding(new Insets (defaultPadding));

		//initialise mark button
		Button markButton = new Button("Mark");

		// To be Able to choose Only one radio option
		ToggleGroup tGroup = new ToggleGroup();

		switch (type){

		case CHECKBOX:
			//Creation of CheckBox's 
			for (int i = 0; i < answers.size();i++ ){
				CheckBox temp = new CheckBox(answers.get(i).getText());
				cB.add(new MChoiceCheckBox(temp, answers.get(i).isCorrect()));
			} 
			/*check which box has been selected 
			    				    set orientation 
			    			        creating multiple vertical check boxes */
			if (orientation == Orientation.VERTICAL){
				for(int i= 0; i < cB.size(); i++ ){
					verticalPosition.getChildren().add(cB.get(i).getcB());					
				}
				verticalPosition.getChildren().add(markButton);
				group.getChildren().add(verticalPosition);
			}else {
				//create multiple horizontal check boxes
				for(int i = 0; i < cB.size(); i++ ){
					horizontalPosition.getChildren().add(cB.get(i).getcB());
				}
			horizontalPosition.getChildren().add(markButton);
			group.getChildren().add(horizontalPosition);
		    }

			markButton.setOnAction(new EventHandler<ActionEvent>(){
				@Override 
				public void handle (ActionEvent event){
					boolean correctCheckBoxAnswer = Check();
					if (correctCheckBoxAnswer){
						System.out.println("Thats Great ");
					}else 
						System.out.println("Try again");
				}

			});

			break;

		case RADIO:
			//creation of radio buttons 
			for(int i = 0; i < answers.size(); i++ ){
				RadioButton tempRb = new RadioButton(answers.get(i).getText());
				tempRb.setToggleGroup(tGroup);
				rB.add(new MChoiceRadio(tempRb, answers.get(i).isCorrect()));
			}

			//set orientation 
			if( orientation == Orientation.VERTICAL){
				// creating multiple vertical radio buttons  
				for(int i= 0; i < rB.size(); i++ ){
					verticalPosition.getChildren().add(rB.get(i).getrB());
				}
				
				verticalPosition.getChildren().add(markButton);
				group.getChildren().add(verticalPosition);
			} else { 
				// create multiple horizontal radio buttons 
				for(int i =0; i< rB.size();i++){
					horizontalPosition.getChildren().add(rB.get(i).getrB());
				}
				
				horizontalPosition.getChildren().add(markButton);
				group.getChildren().add(horizontalPosition);
			}

			markButton.setOnAction(new EventHandler<ActionEvent>(){
				@Override 
				public void handle (ActionEvent event){
					boolean correctRadioAnswer = RadioCheck();
					if (correctRadioAnswer){
						System.out.println("thats awesome ");
					}else 
						System.out.println("you need more help");
				}
			});
			break;

		case DROPDOWNLIST:
			break;


		}


		//horizontalPosition.getChildren().add(markButton);


		/* markButton.setOnAction(new EventHandler<ActionEvent>(){
		        	@Override 
		        	public void handle (ActionEvent event){
		        		boolean correctCheckBoxAnswer = Check();
		        		boolean correctRadioAnswer = RadioCheck();

		        		     if (correctCheckBoxAnswer){
		        			 System.out.println("Thats Great ");
		        		     }else 
		        			 System.out.println("Try again");

		        	         if (correctRadioAnswer){
		        		     System.out.println("thats awesome ");
		        	         }else 
		        		     System.out.println("you need more help");
		                     }
		        });*/

	}

	public boolean Check(){

		for(int i = 0; i < cB.size(); i++ ){

			//checks if the check boxes have been selected 
			if(cB.get(i).getcB().isSelected()){

				/*checks if the right answer has been selected and returns false 
				if the right answer has not been selected */
				if(!cB.get(i).isCorrect()){
					return false;
				}
				/*if the right answer has then been */
			} else {
				if(cB.get(i).isCorrect()){
					return false;
				}
			}
		}

		return true;
	}

	public boolean RadioCheck(){
		/* */
		for(int i = 0; i < rB.size(); i++){
			/* */
			if(rB.get(i).getrB().isSelected()){
				/* */
				if (!rB.get(i).isCorrect()){
					return false;
				}
			}
		}
		
		/* */
		return true; 
	}


	public static void main(String[] args) {
		//launch(args);

	}

}
