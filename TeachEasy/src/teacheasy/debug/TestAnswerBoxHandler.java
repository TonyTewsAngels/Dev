/*
 * Copyright (C) 2015 Sofia Software Solutions
 */
package teacheasy.debug;

import teacheasy.data.AnswerBoxHandler;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * 
 * @author Daniel Berhe
 * @version 1.0
 * 
 *          A simple class to test the TestAnswerBoxHandler
 */
public class TestAnswerBoxHandler extends Application {

	AnswerBoxHandler answerBox;
	boolean correctAnswer;

	public void start(Stage stage) {

		answerBox = new AnswerBoxHandler("monday-tuesday-wednesday");
		answerBox.createAnswerBox(50, 50, 5, false);
		correctAnswer = true;
		answerBox.answerField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)
						&& answerBox.answerField.isEditable() == true) {
					correctAnswer = answerBox.answerChecker(10);
					System.out.println(correctAnswer);
					System.out.println(answerBox.marks);
				}

			}
		});
		Group root = new Group();
		Scene scene = new Scene(root);
		scene.setFill(Color.LIGHTBLUE);
		HBox box = new HBox();
		box.setPrefHeight(400);
		box.setPrefWidth(300);
		box.getChildren().addAll(answerBox.answerField);
		root.getChildren().add(box);

		stage.setTitle("Answer Box Test");
		stage.setWidth(415);
		stage.setHeight(200);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	}

	public static void main(String[] args) {
		Application.launch();
	}
}
