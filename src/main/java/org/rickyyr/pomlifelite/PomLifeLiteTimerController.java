package org.rickyyr.pomlifelite;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class PomLifeLiteTimerController implements Initializable {
  // FXML linked objects.
  @FXML
  protected Label pomCountDisplay;
  @FXML
  protected TextField pomTitleField;
  @FXML
  protected Button startPauseButton;
  @FXML
  protected Button diaryStopButton;
  @FXML
  protected Label clock;
  // Objects used in this Controller class.
  private final JsonListHelper<PomDiaryEntry> jsonListHelper = new JsonListHelper<>("pomDiary.json");
  private PomDiaryEntry currentEntry;
  private final Media notificationSound = new Media(getClass().getResource("pauseBell.wav").toURI().toString());
  private final MediaPlayer notificationPlayer = new MediaPlayer(this.notificationSound);
  private final PomTimer pomTimer = new PomTimer();
  // Objects for handling the Diary scene
  private final double[] xOffset = new double[1];
  private final double[] yOffset = new double[1];
  private Stage stage;

  public PomLifeLiteTimerController() throws URISyntaxException {
  }
  // method to make the diary moveable after swtiching scenes.
  private void setupScene(Scene scene, double[] xOffset, double[] yOffset) {
    scene.setFill(Color.TRANSPARENT);
    scene.setOnMousePressed(event -> {
      xOffset[0] = event.getSceneX();
      yOffset[0] = event.getSceneY();
    });
    scene.setOnMouseDragged(event -> {
      stage.setX(event.getScreenX() - xOffset[0]);
      stage.setY(event.getScreenY() - yOffset[0]);
    });
  }
  // Method to switch from Timer to Diary Scene.
  @FXML
  public void switchToDiary() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource(("pomLifeLiteDiary_fxml.fxml")));
    stage = (Stage) this.diaryStopButton.getScene().getWindow();
    Scene scene = new Scene(root);
    this.setupScene(scene, this.xOffset, this.yOffset);
    stage.setScene(scene);
    stage.show();
  }
  // Initialize
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.pauseTimer.setCycleCount(Animation.INDEFINITE);
    this.runTimer.setCycleCount(Animation.INDEFINITE);
    this.notificationPlayer.setOnEndOfMedia(() -> getNotificationPlayer().stop());
  }
  // The main functionality method to run a COUNTDOWN.
  private final Timeline runTimer = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {
      this.pomTimer.countDown();
      this.clock.setText(this.pomTimer.getRemaining());
      if(!this.pomTimer.getIsRunning()) {
        this.notificationPlayer.play();
        this.clock.setText(this.pomTimer.getRemainingPauseTime());
        this.pomCountDisplay.setText("PAUSE");
        this.startPauseButton.setText("▶");
        this.startPauseButton.setOnAction(action -> startPauseCounter());
        this.getRuntimer().stop();
      }
  }));
  // The main functionality method to run a PAUSE.
  private final Timeline pauseTimer = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {
    this.pomTimer.runPause();
    this.clock.setText(this.pomTimer.getRemainingPauseTime());
    if(!pomTimer.getIsRunning()) {
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
  protected void startTimer() throws IOException {
    if(this.pomTitleField.getText().equals("")) {
      this.pomTitleField.setText("Pom Session");
    }
    this.currentEntry = new PomDiaryEntry(this.pomTitleField.getText());
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
    this.currentEntry.setEntryEndTime();
    this.jsonListHelper.addObjectToList(this.currentEntry);
    this.currentEntry = null;
    this.runTimer.stop();
    this.pomTimer.setOFF();
    this.pomTimer.resetTimer();
    this.pomTimer.resetPomCount();
    this.clock.setText(this.pomTimer.getRemaining());
    this.pomTitleField.setText("");
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
    this.startPauseButton.setOnAction(action -> {
      try {
        startTimer();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
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