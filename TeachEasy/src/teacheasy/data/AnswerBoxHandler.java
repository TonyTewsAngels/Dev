/*
 * Copyright (C) 2015 Sofia Software Solutions
 */
package teacheasy.data;

import java.awt.TextField;



/**
 * 
 * @author Daniel Berhe and Jake Ransom
 * @version 1.0
 *
 */
public class AnswerBoxHandler {
	
	public AnswerBoxHandler(){
		
	}

	
	public void createAnswerBox (int nXStart, int nYStart, int nCharacterLimit,
			   int nMarks, String nCorrectAnswers, boolean nRetry) {
		
		TextField answerField = new TextField();
		answerField.setLocation(nXStart, nYStart);
		answerField.setColumns(nCharacterLimit);
	
		
	}
	
}
