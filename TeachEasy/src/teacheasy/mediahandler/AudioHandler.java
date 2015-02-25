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
		
		progressSlider = new Slider();
		
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

		if (player.isAutoPlay() == true) {
			playPauseButton.setText("Pause");
		} else {
			playPauseButton.setText("Play");
		}

	
		playPauseButton.setPrefSize(200, 80);
		playPauseButton.relocate(100, 100);

		muteButton.setPrefSize(200, 80);
		muteButton.relocate(100, 182);

		
		volumeSlider.setMin(0);
		volumeSlider.setMax(100);
		volumeSlider.setValue(100);
		volumeSlider.setShowTickLabels(true);
		volumeSlider.setShowTickMarks(true);
		volumeSlider.setMajorTickUnit(10);
		volumeSlider.setBlockIncrement(5);
		volumeSlider.setSnapToTicks(true);
		volumeSlider.setOrientation(Orientation.VERTICAL);
		volumeSlider.setPrefSize(40, 170);
		volumeSlider.relocate(400, 100);

		/* ActionListener for the volume slider */
		volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0,
					Number arg1, Number arg2) {
				player.setVolume(volumeSlider.getValue() / 100.0);
			}

		});

		progressSlider.setMin(0);
		progressSlider.setMax(audio.getDuration().toMinutes());
		progressSlider.setValue(0);
		progressSlider.setShowTickMarks(false);
		progressSlider.setShowTickLabels(false);
		progressSlider.set
		progressSlider.setPrefSize(340, 40);
		progressSlider.relocate(100, 300);

		player.setOnReady(new Runnable() {
			@Override
			public void run() {
				duration = audio.getDuration();
				durationMillis = duration.toMillis();
				progressSlider.setMax(duration.toSeconds());
				System.out.println("Audio duration in Seconds: "
						+ progressSlider.getMax());
				System.out.println("Also: " + duration);
			}
		});
		progressSlider.setShowTickLabels(true);
		progressSlider.setShowTickMarks(true);
		// progressSlider.setMajorTickUnit(6000);

		progressSlider.valueProperty().addListener(
				new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> arg0,
							Number arg1, Number arg2) {
						Double currentValuePerCent = progressSlider.getValue() / 100.0;
						player.seek(Duration.millis(durationMillis
								* currentValuePerCent));
					}
				});
		
		player.currentTimeProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                updateValues();
            }
        });

		
		/* Add all parts to the group */
		group.getChildren().addAll(playPauseButton, muteButton, volumeSlider,
				progressSlider, audioView);

	}
	
	public void updateValues() {
		if (player.getTotalDuration() != null) {
            Platform.runLater(new Runnable() {
			public void run(){
				progressSlider.setDisable(duration.isUnknown());
	            if (!progressSlider.isDisabled()
	                    && duration.greaterThan(Duration.ZERO)
	                    && !progressSlider.isValueChanging()) {
	            	double currentTime = player.getCurrentTime().toMillis();
	        		double perCent = (currentTime / durationMillis);
	        		progressSlider.setValue(perCent * 100.0);
	            }
            }
            });
		}
	}
		
	
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
				muteButton.setText("Unmute");
			} else {
				volumeSlider.setValue(oldVolume);
				muteButton.setText("Mute");
			}
		}
	}
}