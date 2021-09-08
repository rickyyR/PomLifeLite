module org.rickyyr.pomlifelite {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;
  requires javafx.media;
  requires java.desktop;
  requires com.google.gson;


  opens org.rickyyr.pomlifelite to javafx.fxml, com.google.gson, javafx.base;
  exports org.rickyyr.pomlifelite;
}