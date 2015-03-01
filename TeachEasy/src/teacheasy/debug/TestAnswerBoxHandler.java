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
	AnswerBoxHandler answerBox2;

	boolean correctAnswer;

	public void start(Stage stage) {

		answerBox = new AnswerBoxHandler("monday-tuesday-wednesday");
		answerBox.createAnswerBox(50, 50, 15, true);
		correctAnswer = false;
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

		answerBox2 = new AnswerBoxHandler("york-leeds-london");
		answerBox2.createAnswerBox(100, 100, 15, false);
		correctAnswer = false;
		answerBox2.answerField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)
						&& answerBox2.answerField.isEditable() == true) {
					correctAnswer = answerBox2.answerChecker(10);
					System.out.println(correctAnswer);
					System.out.println(answerBox2.marks);
				}
			}
		});
		
		Group root = new Group();
		Scene scene = new Scene(root);
		scene.setFill(Color.LIGHTBLUE);
		root.getChildren()
				.addAll(answerBox.answerField, answerBox2.answerField);

		stage.setTitle("Answer Box Test");
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}
