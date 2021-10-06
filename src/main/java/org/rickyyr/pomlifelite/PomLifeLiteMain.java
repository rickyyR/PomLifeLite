package org.rickyyr.pomlifelite;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class PomLifeLiteMain extends Application {
  public static void main(String[] args) {
    launch();
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
    // Mouse pressed/dragged event to move the window without windows decoration.
    scene.setOnMousePressed(event -> {
      xOffset[0] = event.getSceneX();
      yOffset[0] = event.getSceneY();
    });
    scene.setOnMouseDragged(event -> {
      stage.setX(event.getScreenX() - xOffset[0]);
      stage.setY(event.getScreenY() - yOffset[0]);
    });
    // Set up and show the stage.
    stage.setTitle("Pom Life Lite");
    stage.initStyle(StageStyle.TRANSPARENT);
    stage.setScene(scene);
    stage.show();
  }
}