package frontend.gui.windows;

import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * A window for displaying a list view of items and letting the user select from it
 *
 * @param <T>
 */
public class SelectionWindow<T> {
  
  /** The stage of this pop up window */
  private Stage window;
  
  /** The items being displayed */
  @FXML
  private ListView<T> items;
  
  /** The button for entering your selection */
  @FXML
  private Button enterButton;
  
  /**
   * Constructs the selection window. Loads the view from the fxml file.
   * 
   * @param owner the window that created this pop up
   * @param title the title of this window
   * @param enter the text to put in the enter button
   * @param items the items to display
   */
  public SelectionWindow(Window owner, String title, String enter, List<T> items) {
    this.window = new Stage();

    this.window.setTitle(title);
    this.window.initOwner(owner);

    // this window has to be exited to go back to other program
    this.window.initModality(Modality.APPLICATION_MODAL);

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/SelectionWindow.fxml"));
      loader.setController(this);
      Parent root = loader.load();

      this.window.setTitle(title);

      enterButton.setText(enter);

      this.items.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
      this.items.getItems().setAll(items);

      Scene scene = new Scene(root);
      this.window.setScene(scene);

    } catch (IOException e) {
      // other wise dialog fails to load
    }

  }
  
  /**
   * Closes the window when the enter button is pressed
   */
  @FXML
  public void enter() {
    this.window.close();
  }

  /**
   * Show the pop up window and wait for the selection and then return it
   * 
   * @return the selected items
   */
  public List<T> show() {
    this.window.showAndWait();

    return this.items.getSelectionModel().getSelectedItems();
  }
}
