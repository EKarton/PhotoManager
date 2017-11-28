package frontend.gui.customcontrols;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;

/** A dialog window that displays text */
public class TextDialog {

  /** This pop up window's stage */
  private Stage window;

  /** The text area displaying the content of the dialog box */
  @FXML
  private TextArea content;

  /**
   * Constructs the text dialog. Loads the view from the fxml file.
   * 
   * @param title the title of the dialog box
   * @param content the content of the dialog box, i.e. the text being displayed
   */
  public TextDialog(String title, String content) {
    this.window = new Stage();

    this.window.setTitle(title);

    // this window has to be exited to go back to other program
    this.window.initModality(Modality.APPLICATION_MODAL);

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/TextDialog.fxml"));
      loader.setController(this);
      Parent root = loader.load();

      this.set(content);

      Scene scene = new Scene(root);
      this.window.setScene(scene);

    } catch (IOException e) {
      // other wise dialog fails to load
    }

  }

  /**
   * Show the dialog pop up window
   */
  public void show() {
    this.window.show();
  }

  /**
   * Sets the content
   * 
   * @param text the content
   */
  public void set(String text) {
    this.content.setText(text);
  }
}
