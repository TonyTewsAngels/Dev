/*
 * Copyright (C) 2015 Sofia Software Solutions
 */
package teacheasy.debug;

import teacheasy.data.AnswerBoxHandler;
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
		answerBoxHandler = new AnswerBoxHandler(group);
		answerBoxHandler.createAnswerBox(0, 130, 10, true, "monday~tuesday~wednesday",
				5);
		answerBoxHandler.createAnswerBox(0, 160, 10, false, "leeds~york~london", 5);
		answerBoxHandler.createAnswerBox(0, 190, 10, true, "yellow~blue~green", 15);
		answerBoxHandler.createAnswerBox(0, 220, 10, true, "left~right~middle", 5);
		
		stage.setTitle("Answer Box Test");
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}
