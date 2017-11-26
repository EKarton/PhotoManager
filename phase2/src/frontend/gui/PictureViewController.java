package frontend.gui;

import java.io.IOException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

// https://docs.oracle.com/javafx/2/fxml_get_started/custom_control.htm

public class PictureViewController extends BorderPane {
  private StringProperty name;

  @FXML
  private Label nameLabel;

  public PictureViewController() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PictureView.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
//     this.name = new SimpleStringProperty("Hello");
//     this.nameLabel.textProperty().bindBidirectional(this.name);
  }

  public void setLabelText(String text) {
    System.out.println("here");
//    this.name.set(t);
    this.nameLabel.setText("testing");
    this.nameLabel.layout();
  }

  public StringProperty nameProperty() {
    return name;
  }


}
