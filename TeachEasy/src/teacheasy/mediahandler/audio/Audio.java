/*
 * Alex Cash
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package teacheasy.mediahandler.audio;

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
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

public class Audio {
	/* reference to the group */
	Group group;
	
	/* MediaPlayer, Buttons and Sliders*/
	MediaPlayer player;
	Button playPauseButton, muteButton, controlsButton;
	Slider volumeSlider, progressSlider;
	VolumeListener volumeListener;
	SeekListener seekListener;
	Label elapsedLabel, remainingLabel;
	VBox mediaControlsVBox, timeProgressVbox, elapsedLabelVBox, remainingLabelVBox;
	HBox playSeekHbox, volumeMuteHBox, timeLabelsHBox;
	
	/* Variables */
	double oldVolume;
	Duration duration;
	double durationMillis;
	boolean pausedByButton;
	int roundedDuration, durationMins, durationSecs, durationSeconds, roundedCurrentTime, currentTimeMins, currentTimeSecs;
	int buttonWidth = 70;
	int buttonHeight = 40;

	public Audio(Group nGroup, double x, double y, double width, File sourceFile, boolean autoPlay, boolean visibleControls, boolean playButtonOnly){
		/* Set the group reference */
		this.group = nGroup;
		
		mediaControlsVBox = new VBox();
		mediaControlsVBox.setAlignment(Pos.CENTER);
		mediaControlsVBox.setSpacing(10);
		mediaControlsVBox.relocate(x, y);
		
		playSeekHbox = new HBox();
		playSeekHbox.setAlignment(Pos.CENTER_LEFT);
		playSeekHbox.setSpacing(5);
		
		volumeMuteHBox = new HBox();
		volumeMuteHBox.setAlignment(Pos.CENTER_LEFT);
		volumeMuteHBox.setSpacing(5);
		
		timeProgressVbox = new VBox();
		timeProgressVbox.setAlignment(Pos.CENTER);
		timeProgressVbox.setSpacing(0);
		
		timeLabelsHBox = new HBox();
		timeLabelsHBox.setAlignment(Pos.CENTER);
		timeLabelsHBox.setSpacing(5);
		
		elapsedLabelVBox = new VBox();
		elapsedLabelVBox.setAlignment(Pos.CENTER_LEFT);
		elapsedLabelVBox.setSpacing(10);

		remainingLabelVBox = new VBox();
		remainingLabelVBox.setAlignment(Pos.CENTER_RIGHT);
		remainingLabelVBox.setSpacing(10);		
//		
		
		mediaControlsVBox.getChildren().addAll(playSeekHbox, volumeMuteHBox);
		
		
		/* playPause button with default text */
		playPauseButton = new Button ("Play");
		playPauseButton.setOnAction(new playHandler());
		/* Set size and location of the play/pause button */
		playPauseButton.setPrefSize(buttonWidth, buttonHeight);
//		playPauseButton.relocate(5, 35);
		
		
		/* Mute button with default text */
		muteButton = new Button("Mute");
		muteButton.setOnAction(new muteHandler());
		/* Set the size and location of the mute button */
		muteButton.setPrefSize(buttonWidth, buttonHeight);
//		muteButton.relocate(345, 80);
		
		
		/* Controls button with default text */
		controlsButton = new Button("Controls");
		controlsButton.setOnAction(new controlsHandler());		
		/* Set the size and location of the controls button */
		controlsButton.setPrefSize(buttonWidth, buttonHeight);
//		controlsButton.relocate(5, 80);
		
		
		/* Create new listener for the volume value */
		volumeListener = new VolumeListener();
		/* Define the volume slider */
		volumeSlider = new Slider();
		volumeSlider.setMin(0);
		volumeSlider.setMax(100);
		volumeSlider.setValue(100);
		volumeSlider.setMajorTickUnit(10);
		volumeSlider.setBlockIncrement(5);
		volumeSlider.setShowTickLabels(true);
		volumeSlider.setShowTickMarks(true);
		volumeSlider.setSnapToTicks(true);
		volumeSlider.setOrientation(Orientation.HORIZONTAL);		
		volumeSlider.valueProperty().addListener(volumeListener);
		/* Set the size and location of the volume slider */
		volumeSlider.setPrefSize(width - (2 * buttonWidth), 5);
//		volumeSlider.relocate(80, 80);
				
		
		/* Create new listener for the progress bar/slider */ 
		seekListener = new SeekListener();
		/* Create the slider for the progress bar */
		progressSlider = new Slider();
		progressSlider.setMin(0);
		progressSlider.setMax(100);
		progressSlider.setValue(0);
		progressSlider.setBlockIncrement(5);
		progressSlider.setShowTickLabels(false);
		progressSlider.setShowTickMarks(false);
		progressSlider.setSnapToTicks(false);
		progressSlider.setOrientation(Orientation.HORIZONTAL);
		progressSlider.valueProperty().addListener(seekListener);
		progressSlider.setOnMousePressed(new AudioSeekHandler());
		progressSlider.setOnMouseReleased(new AudioSeekHandler());	
		/* Set the size and location of the progress slider */
		progressSlider.setPrefSize(width-buttonWidth, 5);
//		progressSlider.relocate(80, 40);
		
		
		/* Duration labels */
		elapsedLabel = new Label();
		remainingLabel = new Label();
		elapsedLabel.relocate(80, 35);
		remainingLabel.relocate(380, 35);		

		/* Reformat the file to a string to be used as a Media object */
		final Media audio = new Media(sourceFile.toURI().toString());

		/* Create the MediaPlayer using the chosen file */
		player = new MediaPlayer(audio);

		/* Set its autoPlay value to true if there are no buttons */
		if (visibleControls == false) {
			player.setAutoPlay(true);
		} else {
			player.setAutoPlay(autoPlay);
		}

		/* Create the MediaView using the "player" MediaPlayer */
		MediaView audioView = new MediaView(player);

		/* Set the play/pause button text based on autoPlay value */
		if (player.isAutoPlay() == true) {
			playPauseButton.setText("Pause");
		} else {
			playPauseButton.setText("Play");
		}


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
				
				/* Set the sizes of the label VBoxes once the other boxes are initialised */
				double timeLabelSize = (timeLabelsHBox.getWidth()/2);
				elapsedLabelVBox.setMinWidth(timeLabelSize);
				remainingLabelVBox.setMinWidth(timeLabelSize);
				

				elapsedLabel.setText("00:00");
				remainingLabel.setText(String.format("-%02d:%02d",durationMins, durationSecs));
			}
		});


		player.currentTimeProperty().addListener(new progressUpdater());

		group.getChildren().addAll(mediaControlsVBox);
		
		/* Add relevant parts to the group depending on bool switches in call */		
		if (visibleControls == true && playButtonOnly == false){	//Add all visible controls				
			elapsedLabelVBox.getChildren().addAll(elapsedLabel);
			remainingLabelVBox.getChildren().addAll(remainingLabel);	
			timeLabelsHBox.getChildren().addAll(elapsedLabelVBox, remainingLabelVBox);
			timeProgressVbox.getChildren().addAll(timeLabelsHBox, progressSlider);
			playSeekHbox.getChildren().addAll(playPauseButton, timeProgressVbox);
			volumeMuteHBox.getChildren().addAll(controlsButton, volumeSlider, muteButton);
			group.getChildren().addAll(audioView);
		} else if (visibleControls == true && playButtonOnly == true) {		//Add just the play/pause button
			playSeekHbox.getChildren().addAll(playPauseButton);
			group.getChildren().addAll(audioView);
		} else {
			group.getChildren().addAll(audioView); //Add no visible controls
		}

	}
	
	/** Programmatically play this video */
    public void play() {
        player.play();
    }
    
    /** Programmatically pause this video */
    public void pause() {
    	player.pause();
    }
    
    public void stop() {
    	player.stop();
    }
    
    /** Programmatically dispose of this video */
    public void dispose() {
    	player.dispose();
    }
	
	public class progressUpdater implements ChangeListener<Duration> {		
		@Override
        public void changed(ObservableValue<? extends Duration> arg0,
                            Duration arg1, Duration arg2) {			
			double currentTime = player.getCurrentTime().toSeconds();
			int elapsedTimeSecs = (int) Math.round(currentTime);
			int elapsedTimeMins = elapsedTimeSecs / 60;
			
			double perCent = (currentTime / duration.toSeconds());
			
			if (!seekListener.isEnabled()) {
				progressSlider.setValue(perCent * 100.0);
				
				elapsedLabel.setText(String.format("%02d:%02d",elapsedTimeMins, elapsedTimeSecs - elapsedTimeMins*60));
				remainingLabel.setText(String.format("-%02d:%02d",durationMins-elapsedTimeMins, (roundedDuration-elapsedTimeSecs)%60));
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
			} else {
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
	
	public class controlsHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (volumeMuteHBox.getChildren().contains(volumeSlider)) {
				timeProgressVbox.getChildren().remove(progressSlider);
				volumeMuteHBox.getChildren().remove(volumeSlider);
				volumeMuteHBox.getChildren().remove(muteButton);
				elapsedLabelVBox.getChildren().remove(elapsedLabel);
				remainingLabelVBox.getChildren().remove(remainingLabel);
			} else {			
				elapsedLabelVBox.getChildren().add(elapsedLabel);
				remainingLabelVBox.getChildren().add(remainingLabel);
				timeProgressVbox.getChildren().add(progressSlider);
				volumeMuteHBox.getChildren().add(volumeSlider);
				volumeMuteHBox.getChildren().add(muteButton);

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
				audioSeek(new_val.doubleValue());
				
			}
		}
	}
	
	public void audioSeek(double percentSeek) {
		Double currentValuePerCent = (percentSeek / 100);
		player.seek(Duration.millis(durationMillis * currentValuePerCent));
	}


}