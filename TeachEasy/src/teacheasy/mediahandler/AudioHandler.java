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
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
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
	SeekListener seekListener;
	Label elapsedLabel, remainingLabel;
	
	/* Variables */
	double oldVolume;
	Duration duration;
	double durationMillis;
	boolean pausedByButton;
	int roundedDuration, durationMins, durationSecs, roundedCurrentTime, currentTimeMins, currentTimeSecs;

	public AudioHandler(Group nGroup){
		/* Set the group reference */
		this.group = nGroup;
		
		/* Two buttons with default text */
		playPauseButton = new Button ("Play");
		playPauseButton.setOnAction(new playHandler());
		
		muteButton = new Button("Mute");
		muteButton.setOnAction(new muteHandler());
		
		/* Create new listener for the volume value */
		volumeListener = new VolumeListener();
		/* Create the volume slider */
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
		volumeSlider.valueProperty().addListener(volumeListener);
				
		
		/* Create new listener for the progress bar/slider */ 
		seekListener = new SeekListener();
		/* Create the slider for the progress bar */
		progressSlider = new Slider();
		progressSlider.setMin(0);
		progressSlider.setMax(100);
		progressSlider.setValue(0);
		progressSlider.setBlockIncrement(10);
		progressSlider.setShowTickLabels(false);
		progressSlider.setShowTickMarks(false);
		progressSlider.setSnapToTicks(false);
		progressSlider.setOrientation(Orientation.HORIZONTAL);
		progressSlider.valueProperty().addListener(seekListener);
		progressSlider.setOnMousePressed(new AudioSeekHandler());
		progressSlider.setOnMouseReleased(new AudioSeekHandler());	
		
		elapsedLabel = new Label();
		remainingLabel = new Label();
		
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
		playPauseButton.setPrefSize(60, 40);
		playPauseButton.relocate(5, 130);

		/* Set the size and location of the mute button */
		muteButton.setPrefSize(60, 40);
		muteButton.relocate(435, 130);
		
		/* Set the size and location of the volume slider */
		volumeSlider.setPrefSize(40, 170);
		volumeSlider.relocate(505, 5);


		/* Set the size and location of the progress slider */
		progressSlider.setPrefSize(340, 40);
		progressSlider.relocate(80, 160);
		
		elapsedLabel.relocate(90, 140);
		remainingLabel.relocate(400, 140);

		/* Once the player is ready, get the information about the duration of the audio file */
		/* Use this to set the max value of the progress slider */
		player.setOnReady(new Runnable() {
			@Override
			public void run() {
				duration = audio.getDuration();
				durationMillis = duration.toMillis();
				roundedDuration = (int)Math.round(duration.toSeconds());
				durationMins = roundedDuration /60;
				durationSecs = roundedDuration - (60*durationMins);
				
				System.out.println(+durationMins);
				System.out.println(":"+durationSecs);
				String durationString = String.format("%d:%d" durationMins, durationSecs);
				elapsedLabel.setText("0");
				remainingLabel.setText("%d:%d"+durationMins +durationSecs);
			}
		});


		player.currentTimeProperty().addListener(new progressUpdater());

		
		/* Add all parts to the group */
		group.getChildren().addAll(playPauseButton, muteButton, volumeSlider,
				progressSlider, elapsedLabel, remainingLabel, audioView);

	}
	
	
	public class progressUpdater implements ChangeListener<Duration> {		
		@Override
        public void changed(ObservableValue<? extends Duration> arg0,
                            Duration arg1, Duration arg2) {			
			double currentTime = player.getCurrentTime().toSeconds();
			double perCent = (currentTime / duration.toSeconds());
			if (!seekListener.isEnabled()) {
				progressSlider.setValue(perCent * 100.0);
				roundedCurrentTime = (int)Math.round(currentTime);
				
				elapsedLabel.setText(String.valueOf(roundedCurrentTime));
				remainingLabel.setText(String.valueOf(roundedDuration - roundedCurrentTime));
			}
        }		
	}
		
	public class AudioSeekHandler implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent e) {
			if(e.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
				/* Enable the progress bar to be used for scanning */				
				player.pause();
				seekListener.setEnabled(true);
			}else if (e.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
				/* Disable the use of progress bar for scanning*/
				if (pausedByButton == false) {
					player.play();
				}
				seekListener.setEnabled(false);		
			}
		}
	}
	public class playHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (player.getStatus().equals(Status.PLAYING)) {
				player.pause();
				playPauseButton.setText("Play");
				pausedByButton = true;
			} else if (player.getStatus().equals(Status.PAUSED)) {
				player.play();
				playPauseButton.setText("Pause");
				pausedByButton = false;
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
	
	public class SeekListener implements ChangeListener<Number>{
		private boolean enable;
		
		public void setEnabled(boolean isEnabled) {
			enable = isEnabled;
		}
		
		public boolean isEnabled() {
			return enable;
		}
		
		@Override 
		public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
			if (enable) {
				Double currentValuePerCent = (progressSlider.getValue() / 100);
				player.seek(Duration.millis(durationMillis * currentValuePerCent));
			}
		}
	}
}