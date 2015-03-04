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

	AnswerBoxHandler answerBox;



	public void start(Stage stage) {

		answerBox = new AnswerBoxHandler(100, 200, 10, false,
				"monday~tuesday~wednesday", 3);

		Group root = new Group();
		Scene scene = new Scene(root);
		scene.setFill(Color.LIGHTBLUE);
		root.getChildren().addAll(answerBox.answerBox[0]);

		stage.setTitle("Answer Box Test");
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}
