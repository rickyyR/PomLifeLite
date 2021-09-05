package org.rickyyr.pomlifelite;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.util.Duration;

import java.io.File;
import java.io.IOException;


public class PomLifeLiteController {
  // FXML linked objects.
  @FXML
  protected Label pomCountDisplay;
  @FXML
  protected TextField pomTitleField = new TextField();
  @FXML
  protected Button startPauseButton = new Button();
  @FXML
  protected Button diaryStopButton = new Button();
  @FXML
  protected Label clock = new Label();
  // Objects used in this Controller class.
  private Media notificationSound = new Media(new File("src/main/resources/org/rickyyr/pomlifelite/pauseBell.wav").toURI().toString());
  private MediaPlayer notificationPlayer = new MediaPlayer(this.notificationSound);
  private PomTimer pomTimer = new PomTimer();
  private final double[] xOffset = new double[1];
  private final double[] yOffset = new double[1];
  // Objects for handling the Diary scene
  private Stage stage;
  private Scene scene;
  private Parent root;
  private void setupScene(Scene scene, double[] xOffset, double[] yOffset) {
    scene.setFill(Color.TRANSPARENT);
    scene.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        xOffset[0] = event.getSceneX();
        yOffset[0] = event.getSceneY();
      }
    });
    scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        stage.setX(event.getScreenX() - xOffset[0]);
        stage.setY(event.getScreenY() - yOffset[0]);
      }
    });
  }
  // Method to switch from Diary to Timer Scene.
  @FXML
  public void switchToTimer(ActionEvent event) throws IOException {
    root = FXMLLoader.load(getClass().getResource(("pomLifeLite_fxml.fxml")));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    this.setupScene(this.scene, this.xOffset, this.yOffset);
    stage.setScene(scene);
    stage.show();
  }
  // Method to switch from Timer to Diary Scene.
  @FXML
  public void switchToDiary() throws IOException {
    root = FXMLLoader.load(getClass().getResource(("pomLifeLiteDiary_fxml.fxml")));
    stage = (Stage) this.diaryStopButton.getScene().getWindow();
    scene = new Scene(root);
    this.setupScene(this.scene, this.xOffset, this.yOffset);
    stage.setScene(scene);
    stage.show();
  }
  // Constructor.
  public PomLifeLiteController() {
    this.pauseTimer.setCycleCount(Animation.INDEFINITE);
    this.runTimer.setCycleCount(Animation.INDEFINITE);
    this.notificationPlayer.setOnEndOfMedia(new Runnable() {
      @Override
      public void run() {
          getNotificationPlayer().stop();
        }
    });
  } // << close Constructor
  // The main functionality method to run a COUNTDOWN.
  private Timeline runTimer = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {
      this.pomTimer.countDown();
      this.clock.setText(this.pomTimer.getRemaining());
      if(this.pomTimer.getIsRunning() == false) {
        this.notificationPlayer.play();
        this.clock.setText(this.pomTimer.getRemainingPauseTime());
        this.pomCountDisplay.setText("PAUSE");
        this.startPauseButton.setText("▶");
        this.startPauseButton.setOnAction(action -> startPauseCounter());
        this.getRuntimer().stop();
      }
  }));
  // The main functionality method to run a PAUSE.
  private Timeline pauseTimer = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {
    this.pomTimer.runPause();
    this.clock.setText(this.pomTimer.getRemainingPauseTime());
    if(pomTimer.getIsRunning() == false) {
      this.notificationPlayer.play();
      this.clock.setText(this.pomTimer.getRemaining());
      this.pomCountDisplay.setText(this.pomTimer.pomCountTostring());
      this.startPauseButton.setText("▶");
      this.startPauseButton.setOnAction(action -> resumeTimer());
      this.getPauseTimer().stop();
    }
  }));
  // Get methods so MediaPlayer and Timeline can stop themselves
  private MediaPlayer getNotificationPlayer() {
    return this.notificationPlayer;
  }
  private Timeline getRuntimer() { return this.runTimer; }
  private Timeline getPauseTimer() {return this.pauseTimer; }
  // Button functionality methods
  @FXML
  protected void startTimer() {
    this.pomTimer.setON();
    this.runTimer.play();
    this.startPauseButton.setText("⏸");
    this.startPauseButton.setOnAction(action -> pauseTimer());
    this.diaryStopButton.setText("■");
    this.diaryStopButton.setOnAction(action -> stopTimer());
  }
  @FXML
  protected void pauseTimer() {
    this.runTimer.stop();
    this.pomTimer.setOFF();
    this.startPauseButton.setText("▶");
    this.startPauseButton.setOnAction(action -> resumeTimer());
  }
  @FXML
  protected void resumeTimer() {
    this.pomTimer.setON();
    this.runTimer.play();
    this.startPauseButton.setText("⏸");
    this.startPauseButton.setOnAction(action -> pauseTimer());
  }
  @FXML
  protected void stopTimer() {
    this.runTimer.stop();
    this.pomTimer.setOFF();
    this.pomTimer.resetTimer();
    this.pomTimer.resetPomCount();
    this.clock.setText(this.pomTimer.getRemaining());
    this.pomCountDisplay.setText(this.pomTimer.pomCountTostring());
    this.diaryStopButton.setText("Diary");
    this.diaryStopButton.setOnAction(action -> {
      try {
        switchToDiary();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    this.startPauseButton.setText("▶");
    this.startPauseButton.setOnAction(action -> startTimer());
  }
  protected void startPauseCounter() {
    this.pomTimer.setON();
    this.getPauseTimer().play();
  }
  // Exit method for the custom X button
  @FXML
  protected void stopProgramm() {
    System.exit(0);
  }

}