package org.rickyyr.pomlifelite;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PomLifeLiteDiaryController implements Initializable {

  private ObjectIOHelper objectIOHelper = new ObjectIOHelper("pomDiary.json");
  private ObservableList<PomDiaryEntry> observableList;
  @FXML
  protected Button backToTimerButton;
  @FXML
  protected TableView<PomDiaryEntry> tableView;
  // Objects for handling the Diary scene
  private final double[] xOffset = new double[1];
  private final double[] yOffset = new double[1];
  private Stage stage;
  private Scene scene;
  private Parent root;

  public PomLifeLiteDiaryController() {

  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.observableList =  FXCollections.observableArrayList(this.objectIOHelper.getFileContentsAsList(PomDiaryEntry[].class));
    this.tableView.setItems(this.observableList);
  }


  // method to make the Timer moveable after switching scenes.
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
  public void switchToTimer(ActionEvent event)  {
    try {
      this.root = FXMLLoader.load(getClass().getResource(("pomLifeLite_fxml.fxml")));
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    this.scene = new Scene(root);
    this.setupScene(this.scene, this.xOffset, this.yOffset);
    this.stage.setScene(scene);
    this.stage.show();
  }


}
