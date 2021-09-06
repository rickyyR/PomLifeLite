package org.rickyyr.pomlifelite;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Timer;

public class PomLifeLiteMain extends Application {
  public static void main(String[] args) {
    PomDiaryEntry pomDiaryEntry = new PomDiaryEntry();
    pomDiaryEntry.setEntryTitle("Sexer Entry");
    pomDiaryEntry.setDateAndStartTime();
    pomDiaryEntry.setEntryEndTime();
    System.out.println(pomDiaryEntry.getEntryTitle());
    System.out.println(pomDiaryEntry.getEntryDate());
    System.out.println("Start " + pomDiaryEntry.getEntryStartTime());
    System.out.println("End " + pomDiaryEntry.getEntryEndTime());
    //launch();
  }

  @Override
  public void start(Stage stage) throws IOException {
    // Helper objects and variables.
    final double[] xOffset = new double[1];
    final double[] yOffset = new double[1];
    // Create and set up a scene with fxml.
    FXMLLoader fxmlLoader = new FXMLLoader(PomLifeLiteMain.class.getResource("pomLifeLite_fxml.fxml"));
    Parent root = fxmlLoader.load();
    Scene scene = new Scene(root, Color.TRANSPARENT);
    // Mouse pressed/dragged event so one can move the window without windows decoration.
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
    // Set up and show the stage.
    stage.setTitle("Pom Life Lite");
    stage.initStyle(StageStyle.TRANSPARENT);
    stage.setScene(scene);
    stage.show();
  }
}