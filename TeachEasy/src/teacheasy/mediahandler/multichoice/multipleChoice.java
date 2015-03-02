package teacheasy.mediahandler.multichoice;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;

import teacheasy.data.MultipleChoiceObject.MultiChoiceType;
import teacheasy.data.MultipleChoiceObject.Orientation;
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
		horizontalPosition.setPadding( new Insets (defaultPadding));

		switch (type){

		case CHECKBOX:
			//Creation of CheckBox's 
			for (i = 0; i < answers.size();i++ ){
				CheckBox temp = new CheckBox(answers.get(i).getText());
				cB.add(new MChoiceCheckBox(temp, answers.get(i).isCorrect()));
			}
			if (orientation == Orientation.VERTICAL){
				for(i= 0; i < cB.size(); i++ ){
					verticalPosition.getChildren().add(cB.get(i).getcB());
				}
			}else 

				for(i= 0; i < cB.size(); i++ ){
					horizontalPosition.getChildren().add(cB.get(i).getcB());
				}

			break;

		case RADIO:
			ToggleGroup tGroup = new ToggleGroup();
			//creation of radio buttons 
			for(i = 0; i < answers.size(); i++ ){
				RadioButton tempRb = new RadioButton(answers.get(i).getText());
				tempRb.setToggleGroup(tGroup);
				rB.add(new MChoiceRadio(tempRb, answers.get(i).isCorrect()));
			}

			if( orientation == Orientation.VERTICAL)

				// creating multiple vertical radio buttons  
				for(i= 0; i < rB.size(); i++ ){
					verticalPosition.getChildren().add(rB.get(i).getrB());
				}else 

					// create multiple horizontal radio buttons 
					for(i =0; i< rB.size();i++){
						horizontalPosition.getChildren().add(rB.get(i).getrB());
					}

			break;
		case DROPDOWNLIST:
			break;

		}











		//if statement

		//set orientation 



		// creating multiple vertical check boxes 








		//create multiple horizontal check boxes 



		// To be Able to choose Only one radio option



	}


	public void checkCorrect (){     

	}



	public static void main(String[] args) {
		//launch(args);

	}

}
