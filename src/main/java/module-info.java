module org.rickyyr.pomlifelite {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;
  requires javafx.media;
  requires java.desktop;


  opens org.rickyyr.pomlifelite to javafx.fxml;
  exports org.rickyyr.pomlifelite;
}