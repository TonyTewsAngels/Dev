/*
 * Copyright (C) 2015 Sofia Software Solutions
 */
package teacheasy.test;

import teacheasy.mediahandler.AnswerBoxHandler;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * A simple class to test the TestAnswerBoxHandler
 * 
 * @author Sam Raeburn
 * @version 1.0
 * 
 */
public class AnswerBoxTest extends Application {

	AnswerBoxHandler answerBoxHandler;

	public void start(Stage stage) {

		/* Create a window to display answer box in */
		Group group = new Group();
		Scene scene = new Scene(group, 800, 800);
		scene.setFill(Color.LIGHTBLUE);
		
		/* Create answer box*/
		answerBoxHandler = new AnswerBoxHandler(group);
		answerBoxHandler.createAnswerBox(0, 0, 10, true,
				"cat", 5, true);

		stage.setTitle("Answer Box Test");
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}
