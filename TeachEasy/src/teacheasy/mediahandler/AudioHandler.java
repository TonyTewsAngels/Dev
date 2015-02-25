/*
 * Alex Cash
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.mediahandler;

import java.io.File;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This class is the handler for audio
 * 
 * @author Alex Cash
 * @version 1.2 24 Feb 2015
 */

public class AudioHandler {
	/* reference to the group */
	Group group;
	
	/* MediaPlayer, Buttons and Sliders*/
	MediaPlayer player;
	Button playPauseButton, muteButton;
	Slider volumeSlider, progressSlider;
	VolumeListener volumeListener;
	ProgressListener progressListener;
	
	/* Variables */
	double oldVolume;
	Duration duration;
	double durationMillis;

	public AudioHandler(Group nGroup){
		/* Set the group reference */
		this.group = nGroup;
		
		/* Two buttons with default text */
		playPauseButton = new Button ("Play");
		playPauseButton.setOnAction(new playHandler());
		
		muteButton = new Button("Mute");
		muteButton.setOnAction(new muteHandler());
		
		/* Sliders for volume and progress */
		volumeSlider = new Slider();
		volumeSlider.setMin(0);
		volumeSlider.setMax(100);
		volumeSlider.setValue(100);
		volumeSlider.setMajorTickUnit(10);
		volumeSlider.setBlockIncrement(5);
		volumeSlider.setShowTickLabels(true);
		volumeSlider.setShowTickMarks(true);
		volumeSlider.setSnapToTicks(true);
		volumeSlider.setOrientation(Orientation.VERTICAL);
		volumeListener = new VolumeListener();
		volumeSlider.valueProperty().addListener(volumeListener);
		
		
		
		progressSlider = new Slider();
		progressSlider.setMin(0);
		progressSlider.setValue(0);
		progressSlider.setShowTickLabels(true);
		progressSlider.setShowTickMarks(true);
		progressSlider.setSnapToTicks(false);
		progressSlider.setOrientation(Orientation.HORIZONTAL);
		progressListener = new ProgressListener();
		progressSlider.valueProperty().addListener(progressListener);
		
		
	}
	
	public void createAudio(Group group, File sourceFile, boolean autoPlay) {
		/* Reformat the file to a string to be used as a Media object */
		final Media audio = new Media(sourceFile.toURI().toString());

		/* Create the MediaPlayer using the chosen file */
		player = new MediaPlayer(audio);

		/* Set its autoPlay value to whatever it was called with */
		player.setAutoPlay(autoPlay);

		/* Create the MediaView using the "player" MediaPlayer */
		MediaView audioView = new MediaView(player);

		/* Set the play/pause button text based on autoPlay value */
		if (player.isAutoPlay() == true) {
			playPauseButton.setText("Pause");
		} else {
			playPauseButton.setText("Play");
		}

		/* Set size and location of the play/pause button */
		playPauseButton.setPrefSize(200, 80);
		playPauseButton.relocate(100, 100);

		/* Set the size and location of the mute button */
		muteButton.setPrefSize(200, 80);
		muteButton.relocate(100, 182);
		
		/* Set the size and location of the volume slider */
		volumeSlider.setPrefSize(40, 170);
		volumeSlider.relocate(400, 100);


		/* Set the size and location of the progress slider */
		progressSlider.setPrefSize(340, 40);
		progressSlider.relocate(100, 300);

		/* Once the player is ready, get the information about the duration of the audio file */
		/* Use this to set the max value of the progress slider */
		player.setOnReady(new Runnable() {
			@Override
			public void run() {
				duration = audio.getDuration();
				durationMillis = duration.toMillis();
				progressSlider.setMax(duration.toSeconds());
			}
		});


	
//		player.currentTimeProperty().addListener(new InvalidationListener() {
//            public void invalidated(Observable ov) {
//                updateProgress();
//            }
//        });

		
		/* Add all parts to the group */
		group.getChildren().addAll(playPauseButton, muteButton, volumeSlider,
				progressSlider, audioView);

	}
	
	
	
//	public void updateProgress() {
//		if (player.getTotalDuration() != null) {
//            Platform.runLater(new Runnable() {
//			public void run(){
//				progressSlider.setDisable(duration.isUnknown());
//	            if (!progressSlider.isDisabled()
//	                    && duration.greaterThan(Duration.ZERO)
//	                    && !progressSlider.isValueChanging()) {
//	            	double currentTime = player.getCurrentTime().toMillis();
//	        		double perCent = (currentTime / durationMillis);
//	        		progressSlider.setValue(perCent * 100.0);
//	            }
//            }
//            });
//		}
//	}
		
	
	public class playHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if ("Pause".equals(playPauseButton.getText())) {
				player.pause();
				playPauseButton.setText("Play");
			} else {
				player.play();
				playPauseButton.setText("Pause");
			}
		}
	}

	public class muteHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if ("Mute".equals(muteButton.getText())) {				
				oldVolume = volumeSlider.getValue();				
				volumeSlider.setValue(0);
				player.setMute(true);
				muteButton.setText("Unmute");
			} else {
				volumeSlider.setValue(oldVolume);
				player.setMute(false);
				muteButton.setText("Mute");
			}
		}
	}
	
	public class VolumeListener implements ChangeListener<Number>{
		@Override 
		public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
			player.setVolume(volumeSlider.getValue() / 100.0);		
		}
	}
	
	public class ProgressListener implements ChangeListener<Number>{
		@Override 
		public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
			Double currentValuePerCent = progressSlider.getValue() / 100.0;
			player.seek(Duration.millis(durationMillis * currentValuePerCent));					
		}
	}
}