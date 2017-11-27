package frontend.gui;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TextDialog {

  private Stage window;

  @FXML
  private TextArea content;

  public TextDialog(String title, String content) {
    this.window = new Stage();

    this.window.setTitle(title);

    // this window has to be exited to go back to other program
    this.window.initModality(Modality.APPLICATION_MODAL);

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("TextDialog.fxml"));
      loader.setController(this);
      Parent root = loader.load();

      this.set(content);

      Scene scene = new Scene(root);
      this.window.setScene(scene);

    } catch (IOException e) {
      // other wise dialog fails to load
    }

  }

  public void show() {
    this.window.show();
  }

  public void set(String text) {
    this.content.setText(text);
  }
}
