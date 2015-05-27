/*
 * Copyright (C) 2015 Sofia Software Solutions
 */
package teacheasy.debug;

import java.util.ArrayList;

import teacheasy.data.multichoice.Answer;
import teacheasy.mediahandler.AnswerBoxHandler;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * A simple class to test the TestAnswerBoxHandler
 * 
 * @author Daniel Berhe
 * @version 1.0
 * 
 */
public class TestAnswerBoxHandler extends Application {

	AnswerBoxHandler answerBoxHandler;

	public void start(Stage stage) {

		Group group = new Group();
		Scene scene = new Scene(group);
		scene.setFill(Color.LIGHTBLUE);
		
		ArrayList<Answer> answers = new ArrayList<Answer>();
		answers.add(new Answer("One", true));
		answers.add(new Answer("Two", true));
		answers.add(new Answer("Three", true));
		
		answerBoxHandler = new AnswerBoxHandler(group);
		answerBoxHandler.createAnswerBox(0, 130, 5, true, 5, false, 0.0f, 0.0f, answers);

		stage.setTitle("Answer Box Test");
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}
