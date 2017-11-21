package frontend.gui;

import java.io.File;
import javax.naming.NoInitialContextException;
import javafx.event.ActionEvent;

/**
 * This is a controller to handle all action events from MenuBar
 */
public class MenuBarController extends Controller {

  /**
   * Constructs a the menu bar controller
   * 
   * @param mainView the main view used by the application
   * @param backendService the backend service used by the application
   */
  public MenuBarController(MainView mainView, BackendService backendService) {
    super(mainView, backendService);
  }

  /**
   * Handler to open directory
   * 
   * @param e the action event
   */
  public void openDirectory(ActionEvent e) {
    this.openDirectory(false);
  }

  /**
   * Handler to open directory recursivley
   * 
   * @param e the action event
   */
  public void openDirectoryRecursively(ActionEvent e) {
    this.openDirectory(true);
  }

  /**
   * Helper to openDirectory
   * 
   * @param recursive true if it's recursive false other wise
   */
  private void openDirectory(boolean recursive) {
    File file = this.getMainView().openDirectoryChooser();

    if (file != null) {
      String directory = file.getAbsolutePath();

      this.getBackendService().resetBackendService(directory, recursive);
      this.getMainView().getListViewController()
          .setItems(this.getBackendService().getPictureManager().getPictures());
    }
  }

  /**
   * Handler to open log. Opens and displays the log
   * 
   * @param e the action event
   */
  public void openLog(ActionEvent e) {
    String logs = this.getBackendService().getCommandManager().getLogs();
    if (logs != "") {
      TextDialog dialog = new TextDialog(logs);
      dialog.setTitle("Rename logs");
      dialog.show();
    }
  }

  /**
   * Handler for save. Saves the current state of the program.
   * 
   * @param e the action event
   */
  public void save(ActionEvent e) {
    this.getBackendService().save();
  }

  /**
   * Handler for undo. Undos the most recent change.
   * 
   * @param e the action event
   */
  public void undo(ActionEvent e) {
    try {
      this.getBackendService().getCommandManager().undoRecentCommand();
    } catch (NoInitialContextException e1) {
    }
  }

  /**
   * Handler for redo. Redos the most recent change
   * 
   * @param e the action event
   */
  public void redo(ActionEvent e) {
    try {
      this.getBackendService().getCommandManager().redoRecentCommand();
    } catch (NoInitialContextException e1) {
    }
  }
}
