/*
 * Samuel Raeburn
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.test;

import teacheasy.mediahandler.answerbox.AnswerBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import com.sun.javafx.scene.control.behavior.TextFieldBehavior;
import com.sun.javafx.scene.control.skin.TextFieldSkin;

/**
 * This class is used to manually test the
 * answer box handler
 * 
 * @author Samuel Raeburn
 * @version 1.0 07 Mar 2015
 */

public class AnswerBoxTest {
	
	/*This method creates a new answer box handler so that it can be manually tested*/
	void createAnswerBoxHandler() {
		AnswerBox testAnswerBox = new AnswerBox(50, 50, 10, true, "dog", 0, false, null);
	}
}

